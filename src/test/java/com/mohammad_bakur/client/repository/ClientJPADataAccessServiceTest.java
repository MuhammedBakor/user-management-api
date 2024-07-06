package com.mohammad_bakur.client.repository;

import com.mohammad_bakur.client.models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ClientJPADataAccessServiceTest {

    private ClientJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private ClientRepository clientRepository;


    @BeforeEach
    void setUp() {
        autoCloseable =
                MockitoAnnotations.openMocks(this);
        underTest = new ClientJPADataAccessService(clientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllClients() {
        //When
        underTest.selectAllClients();

        //Then
        Mockito.verify(clientRepository).findAll();
    }

    @Test
    void selectClientById() {
        //Given
        int id = 1;
        //When
        underTest.selectClientById(id);
        //Then
        Mockito.verify(clientRepository).findById(id);
    }

    @Test
    void insertClient() {
        //Given
        Client client = new Client(
                1, "","",19);
        //When
        underTest.insertClient(client);
        //Then
        Mockito.verify(clientRepository).save(client);
    }

    @Test
    void existsClientWithEmail() {
        //Given
        String email = "";

        //When
        underTest.existsClientWithEmail(email);

        //Then
        Mockito.verify(clientRepository).existsClientByEmail(email);
    }

    @Test
    void existsClientWithId() {
        //Given
        int id = 1;

        //When
        underTest.existsClientWithId(id);

        //Then
        Mockito.verify(clientRepository).existsClientById(id);
    }

    @Test
    void deleteClientById() {
        //Given
        int id = 1;

        //When
        underTest.deleteClientById(1);

        //Then
        Mockito.verify(clientRepository).deleteById(id);
    }

    @Test
    void updateClient() {
        //Given
        Client client = new Client(
                1, "","",19
        );

        //When
        underTest.updateClient(client);

        //Then
        Mockito.verify(clientRepository).save(client);
    }
}