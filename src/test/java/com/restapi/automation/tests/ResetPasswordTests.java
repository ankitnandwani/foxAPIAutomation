package com.restapi.automation.tests;

import com.restapi.automation.BaseTest;
import com.restapi.automation.Constants;
import com.restapi.automation.Errors;
import com.restapi.automation.dataProviders.TestDataProvider;
import com.restapi.automation.dto.RegisterRequest;
import com.restapi.automation.dto.ResetErrorResponse;
import com.restapi.automation.dto.ResetSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class ResetPasswordTests extends BaseTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyValidReset(RegisterRequest request){
        //Register new user
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);

        //Update Password
        var req = RegisterRequest.builder().email(request.getEmail()).build();
        var updateResp = requestUtils.sendPostRequest(baseUrl+resetEndpoint, getResetHeaders(), req);
        var responseString = updateResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(updateResp.getStatusCode(), SUCCESS_CODE);
        var resetResponse = gson.fromJson(responseString, ResetSuccessResponse.class);
        Assert.assertEquals(resetResponse.getMessage(), Constants.RESET_SUCCESS_MSG);
        Assert.assertEquals(resetResponse.getDetail(), Constants.RESET_SUCCESS_DETAILS);
    }

    //Looks like a bug. Response code is 400 but response is still {"message":"Reset Email Sent","detail":"Please check your inbox"}
    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getValidData")
    public void verifyRestrictionOfMultipleRequestsWithinShortDuration(RegisterRequest request){
        //Register new user
        var response = requestUtils.sendPostRequest(baseUrl+registerEndpoint, getRegisterHeaders(), request);
        Assert.assertEquals(response.getStatusCode(), SUCCESS_CODE);

        //Update Password
        var req = RegisterRequest.builder().email(request.getEmail()).build();
        var updateResp = requestUtils.sendPostRequest(baseUrl+resetEndpoint, getResetHeaders(), req);
        Assert.assertEquals(updateResp.getStatusCode(), SUCCESS_CODE);

        //Update Password again within short duration
        var updateRespAgain = requestUtils.sendPostRequest(baseUrl+resetEndpoint, getResetHeaders(), req);
        var responseString = updateResp.getBody().asString();
        log.info("Response : " + responseString);
        log.info("updateRespAgain getStatusCode : " + updateRespAgain.getStatusCode());
        Assert.assertEquals(updateRespAgain.getStatusCode(), BAD_REQUEST);
        var resetResponse = gson.fromJson(responseString, ResetErrorResponse.class);
        Assert.assertEquals(resetResponse.getErrorType(), Errors.ERROR_BAD_REQUEST);
        Assert.assertEquals(resetResponse.getErrorMessage(), Errors.ERROR_RATE_LIMIT_EXCEEDED);
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getNonExistentEmail")
    public void verifyResetForNonExistentEmail(RegisterRequest request){
        var updateResp = requestUtils.sendPostRequest(baseUrl+resetEndpoint, getResetHeaders(), request);
        var responseString = updateResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(updateResp.getStatusCode(), NOT_FOUND);
        var resetResponse = gson.fromJson(responseString, ResetErrorResponse.class);
        Assert.assertEquals(resetResponse.getErrorType(), Errors.ERROR_NOT_FOUND);
        Assert.assertEquals(resetResponse.getErrorMessage(), Errors.ERROR_USER_NOT_FOUND);
    }

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getBlankEmail")
    public void verifyResetForBlankEmail(RegisterRequest request){
        var updateResp = requestUtils.sendPostRequest(baseUrl+resetEndpoint, getResetHeaders(), request);
        var responseString = updateResp.getBody().asString();
        log.info("Response : " + responseString);
        Assert.assertEquals(updateResp.getStatusCode(), NOT_FOUND);
        var resetResponse = gson.fromJson(responseString, ResetErrorResponse.class);
        Assert.assertEquals(resetResponse.getErrorType(), Errors.ERROR_NOT_FOUND);
        Assert.assertEquals(resetResponse.getErrorMessage(), Errors.ERROR_LOOKING_FOR_USER);
    }
}
