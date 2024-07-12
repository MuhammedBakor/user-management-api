package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.AbstractTestContainers;
import com.mohammad_bakur.user.UserRowMapper;
import com.mohammad_bakur.user.models.Usert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UsertJDBCDataAccessServiceTest extends AbstractTestContainers {

    private UserJDBCDataAccessService userJDBCDataAccessServiceUnderTest;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @BeforeEach
    void setUp() {
        userJDBCDataAccessServiceUnderTest = new UserJDBCDataAccessService(
               getJdbcTemplate(), userRowMapper
        );
    }

    @Test
    void selectAllClients() {
       //Given

        Usert usert = new Usert(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() +"-"+ UUID.randomUUID(),
                20
        );
        userJDBCDataAccessServiceUnderTest.insertClient(usert);
        //When

        List<Usert> actual = userJDBCDataAccessServiceUnderTest.selectAllClients();

        //Then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectClientById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(1,
                FAKER.name().fullName(),
                email,
                20);

        userJDBCDataAccessServiceUnderTest.insertClient(usert);
        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();
        //When
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(
                c->{
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(usert.getName());
                    assertThat(c.getEmail()).isEqualTo(usert.getEmail());
                    assertThat(c.getAge()).isEqualTo(usert.getAge());
                }
        );
    }

    @Test
    void willReturnEmptyWhenSelectClientById() {
    //Given
        int id = -1;
    //When
        var actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);
    // Then
        assertThat(actual).isEmpty();

    }

    @Test
    void insertClient() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(1,
                FAKER.name().fullName(),
                email,
                20);


        //When
        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        //Then
        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c->{
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(usert.getName());
                    assertThat(c.getEmail()).isEqualTo(usert.getEmail());
                    assertThat(c.getAge()).isEqualTo(usert.getAge());
                }
        );

    }

    @Test
    void existsClientWithEmail() {
        //Given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Usert usert = new Usert(
                name,
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);
        //When

        boolean actual = userJDBCDataAccessServiceUnderTest.existsClientWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsClientWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = userJDBCDataAccessServiceUnderTest.existsClientWithEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsClientWithId() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = userJDBCDataAccessServiceUnderTest.existsClientWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsClientWithIdWillReturnFalseWhenIdNotPresent() {
        // Given
        int id = -1;

        // When
        var actual = userJDBCDataAccessServiceUnderTest.existsClientWithId(id);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteClientById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // When
        userJDBCDataAccessServiceUnderTest.deleteClientById(id);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateClientName() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";

        // When age is name
        Usert update = new Usert();
        update.setId(id);
        update.setName(newName);

        userJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName); // change
            assertThat(c.getEmail()).isEqualTo(usert.getEmail());
            assertThat(c.getAge()).isEqualTo(usert.getAge());
        });
    }

    @Test
    void updateClientEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();;

        // When email is changed
        Usert update = new Usert();
        update.setId(id);
        update.setEmail(newEmail);

        userJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail); // change
            assertThat(c.getName()).isEqualTo(usert.getName());
            assertThat(c.getAge()).isEqualTo(usert.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert customer = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(customer);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 100;

        // When age is changed
        Usert update = new Usert();
        update.setId(id);
        update.setAge(newAge);

        userJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(newAge); // change
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void willUpdateAllPropertiesClient() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert usert = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(usert);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // When update with new name, age and email
        Usert update = new Usert();
        update.setId(id);
        update.setName("foo");
        update.setEmail(UUID.randomUUID().toString());
        update.setAge(22);

        userJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValue(update);
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Usert customer = new Usert(
                FAKER.name().fullName(),
                email,
                20
        );

        userJDBCDataAccessServiceUnderTest.insertClient(customer);

        int id = userJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Usert::getId)
                .findFirst()
                .orElseThrow();

        // When update without no changes
        Usert update = new Usert();
        update.setId(id);

        userJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Usert> actual = userJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

}