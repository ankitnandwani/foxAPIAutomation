package com.restapi.automation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetErrorResponse {
    private String errorType;
    private String errorMessage;
}
