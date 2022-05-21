package com.restapi.automation;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Log4j
public class BaseTest extends AutomationApplicationTests{

    @Value("${restapi.baseUrl}")
    public String baseUrl;

    @Value("${restapi.register}")
    public String registerEndpoint;

    @Value("${restapi.login}")
    public String loginEndpoint;

    @Value("${restapi.reset}")
    public String resetEndpoint;

    @Autowired
    public RequestUtils requestUtils;

    @Autowired
    public Gson gson;

    public static final int SUCCESS_CODE = 200;
    public static final int NOT_FOUND = 404;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;



    public Map<String, ?> getHeaders(String xApiKey, String postmanToken){
        Map<String, String> headers = new HashMap<>();
        headers.put("x-api-key", xApiKey);
        headers.put("Postman-Token", postmanToken);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public Map<String, ?> getLoginHeaders(){
        return getHeaders(Constants.regXApiKey, Constants.loginPostmanToken);
    }

    public Map<String, ?> getRegisterHeaders(){
        return getHeaders(Constants.regXApiKey, Constants.regPostmanToken);
    }

    public Map<String, ?> getResetHeaders(){
        return getHeaders(Constants.resetXApiKey, Constants.loginPostmanToken);
    }
}
