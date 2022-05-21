package com.restapi.automation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetSuccessResponse {
    private String message;
    private String detail;
}
