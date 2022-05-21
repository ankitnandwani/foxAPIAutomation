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
public class RegistrationTests extends BaseTest {



    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyRegistrationWithValidData(RegisterRequest request){
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);
        var responseString = response.getBody().asString();
        log.info("Response : " + responseString);
        var registerResponse = gson.fromJson(responseString, RegisterResponse.class);
        Assert.assertEquals(registerResponse.getEmail(), request.getEmail());
        Assert.assertEquals(registerResponse.getGender(), request.getGender());
        Assert.assertEquals(registerResponse.getFirstName(), request.getFirstName());
        Assert.assertEquals(registerResponse.getLastName(), request.getLastName());
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getInvalidData")
    public void verifyRegistrationWithInvalidData(RegisterRequest request){
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST);
        var responseString = response.getBody().asString();
        log.info("Response : " + responseString);
        var registerResponse = gson.fromJson(responseString, ErrorResponse.class);
        Assert.assertEquals(registerResponse.getMessage(), Errors.ERROR_INVALID_PARAMETERS);
        Assert.assertEquals(registerResponse.getDetail(), Errors.DETAIL_INVALID_PARAMETERS);
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getBlankData")
    public void verifyRegistrationWithBlankData(RegisterRequest request){
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST);
        var responseString = response.getBody().asString();
        log.info("Response : " + responseString);
        var registerResponse = gson.fromJson(responseString, ErrorResponse.class);
        Assert.assertEquals(registerResponse.getMessage(), Errors.ERROR_INVALID_PARAMETERS);
        Assert.assertEquals(registerResponse.getDetail(), Errors.ERROR_EMAIL_REQUIRED);
    }


}
