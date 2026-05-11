package me.asunamyadmin.bank.security.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER,
    HEAD,
    SYSTEM;

    public SimpleGrantedAuthority getSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}