package com.acgsocial.configuration;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleCoverter implements Converter<Jwt, Collection<GrantedAuthority>> {


    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        Collection<GrantedAuthority> roles = List.of();
        if(! realmAccess.isEmpty()) {
            roles =  ((List<String>) realmAccess.get("roles"))
              .stream()
              .map(roleName -> "ROLE_" + roleName)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
        }

        Collection<GrantedAuthority> scopes =
          Arrays.stream(source.getClaims().get("scope").toString().split(" ")).toList()
                        .stream()
                        .map(scope -> "SCOPE_" + scope)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        roles.addAll(scopes);
        return roles;


    }

}
