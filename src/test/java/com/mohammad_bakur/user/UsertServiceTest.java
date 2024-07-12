package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsertServiceTest {

    private UserService underTest;
    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userDAO);
    }

    @Test
    void getAllClients() {
        //When
        underTest.getAllClients();
        //Then
        verify(userDAO).selectAllClients();
    }

    @Test
    void canGetClient() {
        //Given
        int id = 1;
        Usert usert = new Usert(
                id, "moo", "moo@", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(usert));
        //When

        Usert actual = underTest.getClient(id);

        //Then
        assertThat(actual).isEqualTo(usert);
    }

    @Test
    void willThrowWhenGetClientReturnEmptyOptional() {
        //Given
        int id = 1;

        when(userDAO.selectClientById(id)).thenReturn(Optional.empty());
        //When
        //Then

        assertThatThrownBy(()-> underTest.getClient(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "User with id [%s] not found".formatted(id));
    }

    @Test
    void addClient() {
        // Given
        String email = "alex@gmail.com";

        when(userDAO.existsClientWithEmail(email)).thenReturn(false);

        UserRegistrationRequest request = new UserRegistrationRequest(
                "Alex", email, 19
        );

        // When
        underTest.addClient(request);

        // Then
        ArgumentCaptor<Usert> ClientArgumentCaptor = ArgumentCaptor.forClass(
                Usert.class
        );

        verify(userDAO).insertClient(ClientArgumentCaptor.capture());

        Usert capturedUsert = ClientArgumentCaptor.getValue();

        assertThat(capturedUsert.getId()).isNull();
        assertThat(capturedUsert.getName()).isEqualTo(request.name());
        assertThat(capturedUsert.getEmail()).isEqualTo(request.email());
        assertThat(capturedUsert.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingAClient() {
        // Given
        String email = "alex@gmail.com";

        when(userDAO.existsClientWithEmail(email)).thenReturn(true);

        UserRegistrationRequest request = new UserRegistrationRequest(
                "Alex", email, 19
        );

        // When
        assertThatThrownBy(() -> underTest.addClient(request))
                .isInstanceOf(ResourceDuplicatedException.class)
                .hasMessage("email already taken");

        // Then
        verify(userDAO, never()).insertClient(any());
    }

    @Test
    void deleteClientById() {
        // Given
        int id = 10;

        when(userDAO.existsClientWithId(id)).thenReturn(true);

        // When
        underTest.deleteClientById(id);

        // Then
        verify(userDAO).deleteClientById(id);
    }

    @Test
    void willThrowDeleteClientByIdNotExists() {
        // Given
        int id = 10;

        when(userDAO.existsClientWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteClientById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Client with id [%s] not found".formatted(id));

        // Then
        verify(userDAO, never()).deleteClientById(id);
    }

    @Test
    void canUpdateAllClientsProperties() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        String newEmail = "alexandro@amigoscode.com";

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "Alexandro", newEmail, 23);

        when(userDAO.existsClientWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Usert> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Usert.class);

        verify(userDAO).updateClient(ClientArgumentCaptor.capture());
        Usert capturedUsert = ClientArgumentCaptor.getValue();

        assertThat(capturedUsert.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedUsert.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedUsert.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnlyClientName() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "Alexandro", null, null);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Usert> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Usert.class);

        verify(userDAO).updateClient(ClientArgumentCaptor.capture());
        Usert capturedUsert = ClientArgumentCaptor.getValue();

        assertThat(capturedUsert.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedUsert.getAge()).isEqualTo(Usert.getAge());
        assertThat(capturedUsert.getEmail()).isEqualTo(Usert.getEmail());
    }

    @Test
    void canUpdateOnlyClientEmail() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        String newEmail = "alexandro@amigoscode.com";

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                null, newEmail, null);

        when(userDAO.existsClientWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Usert> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Usert.class);

        verify(userDAO).updateClient(ClientArgumentCaptor.capture());
        Usert capturedUsert = ClientArgumentCaptor.getValue();

        assertThat(capturedUsert.getName()).isEqualTo(Usert.getName());
        assertThat(capturedUsert.getAge()).isEqualTo(Usert.getAge());
        assertThat(capturedUsert.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyClientAge() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                null, null, 22);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Usert> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Usert.class);

        verify(userDAO).updateClient(ClientArgumentCaptor.capture());
        Usert capturedUsert = ClientArgumentCaptor.getValue();

        assertThat(capturedUsert.getName()).isEqualTo(Usert.getName());
        assertThat(capturedUsert.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedUsert.getEmail()).isEqualTo(Usert.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateClientEmailWhenAlreadyTaken() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        String newEmail = "alexandro@amigoscode.com";

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                null, newEmail, null);

        when(userDAO.existsClientWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateClient(id, updateRequest))
                .isInstanceOf(ResourceDuplicatedException.class)
                .hasMessage("email already taken");

        // Then
        verify(userDAO, never()).updateClient(any());
    }

    @Test
    void willThrowWhenClientUpdateHasNoChanges() {
        // Given
        int id = 10;
        Usert Usert = new Usert(
                id, "Alex", "alex@gmail.com", 19
        );
        when(userDAO.selectClientById(id)).thenReturn(Optional.of(Usert));

        UserUpdateRequest updateRequest = new UserUpdateRequest(
                Usert.getName(), Usert.getEmail(), Usert.getAge());

        // When
        assertThatThrownBy(() -> underTest.updateClient(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(userDAO, never()).updateClient(any());
    }
}