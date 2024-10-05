package com.example.service.impl;

import java.util.Optional;

import com.example.entity.ClientEntity;
import com.example.entity.Role;
import com.example.exception.LoginException;
import com.example.exception.RegistrationException;
import com.example.repository.ClientRepository;
import com.example.service.ClientService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultClientService implements ClientService {
    private final ClientRepository clientRepository;
    private static final Role DEFAULT_ROLE = Role.COMMON_USER;

    @Autowired
    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void register(String clientId, String clientSecret, Role role) {
        // TODO cache is pretty good here
        if (clientRepository.findById(clientId).isPresent()) {
            throw new RegistrationException("Client with id: " + clientId + " already registered");
        }

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash, role == null ? DEFAULT_ROLE : role));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        if (optionalClientEntity.isEmpty()) {
            throw new LoginException("Client with id: " + clientId + " not found");
        }

        ClientEntity clientEntity = optionalClientEntity.get();
        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash())) {
            throw new LoginException("Secret is incorrect");
        }
    }

    @Override
    public Role getRole(String clientId) {
        // TODO cache is pretty good here
        return clientRepository.findById(clientId)
                .orElseThrow(
                        () -> new LoginException("Client with id: " + clientId + " not found")
                )
                .getRole();
    }
}
