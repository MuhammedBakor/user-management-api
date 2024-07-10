package com.mohammad_bakur.client;

import com.mohammad_bakur.client.models.Client;
import com.mohammad_bakur.client.requests.ClientRegistrationRequest;
import com.mohammad_bakur.client.requests.ClientUpdateRequest;
import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    private ClientService underTest;
    @Mock
    private ClientDAO clientDAO;

    @BeforeEach
    void setUp() {
        underTest = new ClientService(clientDAO);
    }

    @Test
    void getAllClients() {
        //When
        underTest.getAllClients();
        //Then
        verify(clientDAO).selectAllClients();
    }

    @Test
    void canGetClient() {
        //Given
        int id = 1;
        Client client = new Client(
                id, "moo", "moo@", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(client));
        //When

        Client actual = underTest.getClient(id);

        //Then
        assertThat(actual).isEqualTo(client);
    }

    @Test
    void willThrowWhenGetClientReturnEmptyOptional() {
        //Given
        int id = 1;

        when(clientDAO.selectClientById(id)).thenReturn(Optional.empty());
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

        when(clientDAO.existsClientWithEmail(email)).thenReturn(false);

        ClientRegistrationRequest request = new ClientRegistrationRequest(
                "Alex", email, 19
        );

        // When
        underTest.addClient(request);

        // Then
        ArgumentCaptor<Client> ClientArgumentCaptor = ArgumentCaptor.forClass(
                Client.class
        );

        verify(clientDAO).insertClient(ClientArgumentCaptor.capture());

        Client capturedClient = ClientArgumentCaptor.getValue();

        assertThat(capturedClient.getId()).isNull();
        assertThat(capturedClient.getName()).isEqualTo(request.name());
        assertThat(capturedClient.getEmail()).isEqualTo(request.email());
        assertThat(capturedClient.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingAClient() {
        // Given
        String email = "alex@gmail.com";

        when(clientDAO.existsClientWithEmail(email)).thenReturn(true);

        ClientRegistrationRequest request = new ClientRegistrationRequest(
                "Alex", email, 19
        );

        // When
        assertThatThrownBy(() -> underTest.addClient(request))
                .isInstanceOf(ResourceDuplicatedException.class)
                .hasMessage("email already taken");

        // Then
        verify(clientDAO, never()).insertClient(any());
    }

    @Test
    void deleteClientById() {
        // Given
        int id = 10;

        when(clientDAO.existsClientWithId(id)).thenReturn(true);

        // When
        underTest.deleteClientById(id);

        // Then
        verify(clientDAO).deleteClientById(id);
    }

    @Test
    void willThrowDeleteClientByIdNotExists() {
        // Given
        int id = 10;

        when(clientDAO.existsClientWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteClientById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Client with id [%s] not found".formatted(id));

        // Then
        verify(clientDAO, never()).deleteClientById(id);
    }

    @Test
    void canUpdateAllClientsProperties() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        String newEmail = "alexandro@amigoscode.com";

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                "Alexandro", newEmail, 23);

        when(clientDAO.existsClientWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Client> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientDAO).updateClient(ClientArgumentCaptor.capture());
        Client capturedClient = ClientArgumentCaptor.getValue();

        assertThat(capturedClient.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedClient.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedClient.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnlyClientName() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                "Alexandro", null, null);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Client> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientDAO).updateClient(ClientArgumentCaptor.capture());
        Client capturedClient = ClientArgumentCaptor.getValue();

        assertThat(capturedClient.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedClient.getAge()).isEqualTo(Client.getAge());
        assertThat(capturedClient.getEmail()).isEqualTo(Client.getEmail());
    }

    @Test
    void canUpdateOnlyClientEmail() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        String newEmail = "alexandro@amigoscode.com";

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                null, newEmail, null);

        when(clientDAO.existsClientWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Client> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientDAO).updateClient(ClientArgumentCaptor.capture());
        Client capturedClient = ClientArgumentCaptor.getValue();

        assertThat(capturedClient.getName()).isEqualTo(Client.getName());
        assertThat(capturedClient.getAge()).isEqualTo(Client.getAge());
        assertThat(capturedClient.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyClientAge() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                null, null, 22);

        // When
        underTest.updateClient(id, updateRequest);

        // Then
        ArgumentCaptor<Client> ClientArgumentCaptor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientDAO).updateClient(ClientArgumentCaptor.capture());
        Client capturedClient = ClientArgumentCaptor.getValue();

        assertThat(capturedClient.getName()).isEqualTo(Client.getName());
        assertThat(capturedClient.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedClient.getEmail()).isEqualTo(Client.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateClientEmailWhenAlreadyTaken() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        String newEmail = "alexandro@amigoscode.com";

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                null, newEmail, null);

        when(clientDAO.existsClientWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateClient(id, updateRequest))
                .isInstanceOf(ResourceDuplicatedException.class)
                .hasMessage("email already taken");

        // Then
        verify(clientDAO, never()).updateClient(any());
    }

    @Test
    void willThrowWhenClientUpdateHasNoChanges() {
        // Given
        int id = 10;
        Client Client = new Client(
                id, "Alex", "alex@gmail.com", 19
        );
        when(clientDAO.selectClientById(id)).thenReturn(Optional.of(Client));

        ClientUpdateRequest updateRequest = new ClientUpdateRequest(
                Client.getName(), Client.getEmail(), Client.getAge());

        // When
        assertThatThrownBy(() -> underTest.updateClient(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(clientDAO, never()).updateClient(any());
    }
}