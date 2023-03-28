package com.clay.sh.identityserver.service;

import com.clay.sh.identityserver.exception.ClientRegistrationException;
import com.clay.sh.identityserver.exception.NoSuchClientException;
import com.clay.sh.identityserver.model.ClientDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class InMemoryClientDetailsService implements ClientDetailsService {

    private final Map<String, ClientDetails> clients = new HashMap<>();

    public InMemoryClientDetailsService() {
        // Create some test clients
        ClientDetails client1 = ClientDetails.builder()
                .clientId("test-client-1")
                .clientSecret("$2a$10$YB8gDxknKsHf3qMbcDDOj.6A1v/70hbbiSKPwhn/CFkK8rtvGx1Hi")
                .authorizedGrantTypes(Set.of("client_credentials"))
                .scope(Set.of("read", "write"))
                .build();
        ClientDetails client2 = ClientDetails.builder()
                .clientId("test-client-2")
                .clientSecret("$2a$10$OZSv28ZqgzgKnOhyY3O7meUIQ4fv/KVt.c0RkKjVdq/Y0wBCZuV7i")
                .authorizedGrantTypes(Set.of("client_credentials"))
                .scope(Set.of("read"))
                .build();
        clients.put(client1.getClientId(), client1);
        clients.put(client2.getClientId(), client2);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (!clients.containsKey(clientId)) {
            throw new NoSuchClientException("No client with id " + clientId);
        }
        return clients.get(clientId);
    }
}
