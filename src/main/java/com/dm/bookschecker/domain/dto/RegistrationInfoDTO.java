package com.dm.bookschecker.domain.dto;

import java.util.List;

import com.dm.bookschecker.domain.model.UserRole;

public class RegistrationInfoDTO {

    private long id;
    private String username;
    private String password;
    private List<UserRole> roles;

    public RegistrationInfoDTO() {
    }

    public RegistrationInfoDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
