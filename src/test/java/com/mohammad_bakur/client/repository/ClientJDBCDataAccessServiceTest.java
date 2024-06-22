package com.mohammad_bakur.client.repository;

import com.mohammad_bakur.AbstractTestContainers;
import com.mohammad_bakur.client.ClientRowMapper;
import com.mohammad_bakur.client.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClientJDBCDataAccessServiceTest extends AbstractTestContainers {

    private ClientJDBCDataAccessService clientJDBCDataAccessServiceUnderTest;
    private final ClientRowMapper clientRowMapper = new ClientRowMapper();

    @BeforeEach
    void setUp() {
        clientJDBCDataAccessServiceUnderTest = new ClientJDBCDataAccessService(
               getJdbcTemplate(), clientRowMapper
        );
    }

    @Test
    void selectAllClients() {
       //Given

        Client client = new Client(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() +"-"+ UUID.randomUUID(),
                20
        );
        clientJDBCDataAccessServiceUnderTest.insertClient(client);
        //When

        List<Client> actual = clientJDBCDataAccessServiceUnderTest.selectAllClients();

        //Then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectClientById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(1,
                FAKER.name().fullName(),
                email,
                20);

        clientJDBCDataAccessServiceUnderTest.insertClient(client);
        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();
        //When
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(
                c->{
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(client.getName());
                    assertThat(c.getEmail()).isEqualTo(client.getEmail());
                    assertThat(c.getAge()).isEqualTo(client.getAge());
                }
        );
    }

    @Test
    void willReturnEmptyWhenSelectClientById() {
    //Given
        int id = -1;
    //When
        var actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);
    // Then
        assertThat(actual).isEmpty();

    }

    @Test
    void insertClient() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(1,
                FAKER.name().fullName(),
                email,
                20);


        //When
        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        //Then
        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c->{
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(client.getName());
                    assertThat(c.getEmail()).isEqualTo(client.getEmail());
                    assertThat(c.getAge()).isEqualTo(client.getAge());
                }
        );

    }

    @Test
    void existsClientWithEmail() {
        //Given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Client client = new Client(
                name,
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);
        //When

        boolean actual = clientJDBCDataAccessServiceUnderTest.existsClientWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsClientWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = clientJDBCDataAccessServiceUnderTest.existsClientWithEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsClientWithId() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = clientJDBCDataAccessServiceUnderTest.existsClientWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsClientWithIdWillReturnFalseWhenIdNotPresent() {
        // Given
        int id = -1;

        // When
        var actual = clientJDBCDataAccessServiceUnderTest.existsClientWithId(id);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteClientById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        // When
        clientJDBCDataAccessServiceUnderTest.deleteClientById(id);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateClientName() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";

        // When age is name
        Client update = new Client();
        update.setId(id);
        update.setName(newName);

        clientJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName); // change
            assertThat(c.getEmail()).isEqualTo(client.getEmail());
            assertThat(c.getAge()).isEqualTo(client.getAge());
        });
    }

    @Test
    void updateClientEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client client = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();;

        // When email is changed
        Client update = new Client();
        update.setId(id);
        update.setEmail(newEmail);

        clientJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail); // change
            assertThat(c.getName()).isEqualTo(client.getName());
            assertThat(c.getAge()).isEqualTo(client.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client customer = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(customer);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 100;

        // When age is changed
        Client update = new Client();
        update.setId(id);
        update.setAge(newAge);

        clientJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

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
        Client client = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(client);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        // When update with new name, age and email
        Client update = new Client();
        update.setId(id);
        update.setName("foo");
        update.setEmail(UUID.randomUUID().toString());
        update.setAge(22);

        clientJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValue(update);
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Client customer = new Client(
                FAKER.name().fullName(),
                email,
                20
        );

        clientJDBCDataAccessServiceUnderTest.insertClient(customer);

        int id = clientJDBCDataAccessServiceUnderTest.selectAllClients()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Client::getId)
                .findFirst()
                .orElseThrow();

        // When update without no changes
        Client update = new Client();
        update.setId(id);

        clientJDBCDataAccessServiceUnderTest.updateClient(update);

        // Then
        Optional<Client> actual = clientJDBCDataAccessServiceUnderTest.selectClientById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

}