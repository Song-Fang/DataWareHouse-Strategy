package edu.pitt.api.Mongo.models;

import org.springframework.security.core.GrantedAuthority;

public enum MongoRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }
}
