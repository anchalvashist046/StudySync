package com.Tezpur.University.Management.System.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserDto {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String address;

    private String about;

    private String program;

    private String designation;

    private String rollNumber;
    private String name;
    private String firstName;
    private String lastName;

    private String role;

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}