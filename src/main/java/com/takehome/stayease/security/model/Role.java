package com.takehome.stayease.security.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public enum Role {


    ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_WRITE)),

    HOTEL_MANAGER(Set.of(Permission.MANAGER_READ, Permission.MANAGER_WRITE)),


    CUSTOMER(Set.of(Permission.CUSTOMER_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<? extends SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
