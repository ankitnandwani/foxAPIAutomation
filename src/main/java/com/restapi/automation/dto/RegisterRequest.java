package com.restapi.automation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String gender;
    private String firstName;
    private String lastName;
}
