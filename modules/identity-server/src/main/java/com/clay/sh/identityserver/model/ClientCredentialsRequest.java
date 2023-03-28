package com.clay.sh.identityserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCredentialsRequest {
    private String clientId;
    private String clientSecret;
}
