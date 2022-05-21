package com.restapi.automation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String accessToken;
    private String tokenExpiration;
    private String accountType;
    private String device;
    private String deviceId;
    private String brand;
    private Boolean hasEmail;
    private Boolean hasSocialLogin;
    private Boolean isVerified;
    private String ipAddress;
    private String platform;
    private String profileId;
    private String userType;
    private String userAgent;
    private String dma;
    private String gender;
    private String viewerId;
    private String birthdate;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean newsLetter;

}
