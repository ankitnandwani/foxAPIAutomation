package com.restapi.automation;

public interface Errors {
    String ERROR_INVALID_PARAMETERS = "Invalid Parameters.";
    String DETAIL_INVALID_PARAMETERS = "Invalid argument: Invalid email";
    String ERROR_EMAIL_REQUIRED = "email is required";
    String ERROR_NOT_FOUND = "Not Found";
    String ERROR_USER_NOT_FOUND = "user not found";
    String ERROR_BAD_REQUEST = "Bad Request";
    String ERROR_RATE_LIMIT_EXCEEDED = "rate limit exceeded for request parameters";
    String ERROR_LOOKING_FOR_USER = "error while looking for user";
    String ERROR_INVALID_LOGIN_ID = "Invalid LoginId";
    String ERROR_INVALID_LOGIN_CREDS = "Invalid login credentials";

}
