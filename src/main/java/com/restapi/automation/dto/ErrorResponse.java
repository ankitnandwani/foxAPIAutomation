package com.restapi.automation.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    @SerializedName("@context")
    private String context;
    @SerializedName("@type")
    private String type;
    private String status;
    private int statusCode;
    private int errorCode;
    private String message;
    private String detail;




}
