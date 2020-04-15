package edu.pitt.api.neo4j.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Neo4jRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}
