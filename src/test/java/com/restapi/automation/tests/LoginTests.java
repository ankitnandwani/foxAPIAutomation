package com.restapi.automation.tests;

import com.restapi.automation.BaseTest;
import com.restapi.automation.Errors;
import com.restapi.automation.dataProviders.TestDataProvider;
import com.restapi.automation.dto.ErrorResponse;
import com.restapi.automation.dto.RegisterRequest;
import com.restapi.automation.dto.RegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class LoginTests extends BaseTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyValidLogin(RegisterRequest request){
        //Register new user
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);

        //Login with same user
        var req = RegisterRequest.builder().email(request.getEmail()).password(request.getPassword()).build();
        var loginResp = requestUtils.sendPostRequest(baseUrl+loginEndpoint, getLoginHeaders(), req);
        var responseString = loginResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(loginResp.getStatusCode(), SUCCESS_CODE);
        var loginResponse = gson.fromJson(responseString, RegisterResponse.class);
        Assert.assertEquals(loginResponse.getEmail(), request.getEmail());
        Assert.assertEquals(loginResponse.getGender(), request.getGender());
        Assert.assertEquals(loginResponse.getFirstName(), request.getFirstName());
        Assert.assertEquals(loginResponse.getLastName(), request.getLastName());
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getNonExistentEmail")
    public void verifyLoginWithNonRegisteredEmail(RegisterRequest request){
        var loginResp = requestUtils.sendPostRequest(baseUrl+loginEndpoint, getLoginHeaders(), request);
        var responseString = loginResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(loginResp.getStatusCode(), UNAUTHORIZED);
        var loginResponse = gson.fromJson(responseString, ErrorResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), Errors.ERROR_INVALID_LOGIN_ID);
        Assert.assertEquals(loginResponse.getDetail(), Errors.ERROR_INVALID_LOGIN_CREDS);
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyLoginWithCorrectEmailButInvalidPassword(RegisterRequest request) {
        //Register new user
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);

        //Login with incorrect password
        var req = RegisterRequest.builder().email(request.getEmail()).password(request.getPassword()+"a").build();
        var loginResp = requestUtils.sendPostRequest(baseUrl+loginEndpoint, getLoginHeaders(), req);
        var responseString = loginResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(loginResp.getStatusCode(), UNAUTHORIZED);
        var loginResponse = gson.fromJson(responseString, ErrorResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), Errors.ERROR_INVALID_LOGIN_ID);
        Assert.assertEquals(loginResponse.getDetail(), Errors.ERROR_INVALID_LOGIN_CREDS);
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyLoginWithCorrectEmailButBlankPassword(RegisterRequest request) {
        //Register new user
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);

        //Login with incorrect password
        var req = RegisterRequest.builder().email(request.getEmail()).password("").build();
        var loginResp = requestUtils.sendPostRequest(baseUrl+loginEndpoint, getLoginHeaders(), req);
        var responseString = loginResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(loginResp.getStatusCode(), UNAUTHORIZED);
        var loginResponse = gson.fromJson(responseString, ErrorResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), Errors.ERROR_INVALID_LOGIN_ID);
        Assert.assertEquals(loginResponse.getDetail(), Errors.ERROR_INVALID_LOGIN_CREDS);
    }


}
