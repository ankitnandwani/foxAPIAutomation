package com.restapi.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestUtils {
    public Response sendPostRequest(String url, Map<String, ?> headers, Object body){
        return RestAssured.given().headers(headers).log().all().body(body).post(url);
    }
}
