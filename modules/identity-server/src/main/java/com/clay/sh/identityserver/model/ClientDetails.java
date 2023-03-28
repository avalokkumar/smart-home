package com.clay.sh.identityserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetails {

    private String clientId;
    private String clientSecret;
    private Set<String> redirectUris;
    private Set<String> authorizedGrantTypes;
    private Set<String> scope;
    private Set<String> resourceIds;
    private List<GrantedAuthority> authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, Object> additionalInformation;

}
