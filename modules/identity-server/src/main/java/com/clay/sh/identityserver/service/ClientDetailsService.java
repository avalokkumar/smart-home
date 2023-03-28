package com.clay.sh.identityserver.service;

import com.clay.sh.identityserver.exception.ClientRegistrationException;
import com.clay.sh.identityserver.model.ClientDetails;

public interface ClientDetailsService {
    ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException;
}
