package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.AbstractTestContainers;
import com.mohammad_bakur.user.models.Usert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsertRepositoryTest extends AbstractTestContainers {

    @Autowired
    private UserRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    public void existsClientByEmail(){
//Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20);

        underTest.save(usert);

        //When
        var actual = underTest.existsClientByEmail(email);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    public void existsClientByEmailFailsWhenEmailNotPresent(){
//Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        var actual = underTest.existsClientByEmail(email);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    public void existsClientById(){
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20);

        underTest.save(usert);
        int id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();
        //When
        var actual = underTest.existsClientById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    public void existsClientByIdWhenIdNotPresent(){
        //Given
        int id = -1;

        //When
        var actual = underTest.existsClientById(id);

        //Then
        assertThat(actual).isFalse();
    }
}