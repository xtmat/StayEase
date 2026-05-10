package com.takehome.stayease.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

 
    ADMIN_READ("admin:read"),


    ADMIN_WRITE("admin:write"),

    MANAGER_READ("management:read"),

    MANAGER_WRITE("management:write"),

    CUSTOMER_READ("customer:read"),

    CUSTOMER_WRITE("customer:write");

    private final String permission;
}
