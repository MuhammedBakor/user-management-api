package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Usert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class UsertJPADataAccessServiceTest {

    private UserJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        autoCloseable =
                MockitoAnnotations.openMocks(this);
        underTest = new UserJPADataAccessService(userRepository);
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
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void selectClientById() {
        //Given
        int id = 1;
        //When
        underTest.selectClientById(id);
        //Then
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    void insertClient() {
        //Given
        Usert usert = new Usert(
                1, "","",19);
        //When
        underTest.insertClient(usert);
        //Then
        Mockito.verify(userRepository).save(usert);
    }

    @Test
    void existsClientWithEmail() {
        //Given
        String email = "";

        //When
        underTest.existsClientWithEmail(email);

        //Then
        Mockito.verify(userRepository).existsClientByEmail(email);
    }

    @Test
    void existsClientWithId() {
        //Given
        int id = 1;

        //When
        underTest.existsClientWithId(id);

        //Then
        Mockito.verify(userRepository).existsClientById(id);
    }

    @Test
    void deleteClientById() {
        //Given
        int id = 1;

        //When
        underTest.deleteClientById(1);

        //Then
        Mockito.verify(userRepository).deleteById(id);
    }

    @Test
    void updateClient() {
        //Given
        Usert usert = new Usert(
                1, "","",19
        );

        //When
        underTest.updateClient(usert);

        //Then
        Mockito.verify(userRepository).save(usert);
    }
}