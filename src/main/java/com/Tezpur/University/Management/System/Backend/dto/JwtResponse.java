package com.Tezpur.University.Management.System.Backend.dto;


import lombok.Data;

@Data
public class JwtResponse {

    private String jwtToken;
    private String role;
    private Long ids;
}
