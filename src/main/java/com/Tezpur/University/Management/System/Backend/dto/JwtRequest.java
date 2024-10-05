package com.Tezpur.University.Management.System.Backend.dto;


import lombok.Data;


@Data
public class JwtRequest {

    private String username;

    //    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Invalid Password !!")
    private String password;
}
