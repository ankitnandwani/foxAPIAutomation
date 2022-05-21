package com.restapi.automation.dataProviders;

import com.restapi.automation.RandomUtils;
import com.restapi.automation.dto.RegisterRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider
    public static Object[][] getValidData(){

        return new Object[][]{{
            RegisterRequest.builder()
                .email(RandomUtils.randomString(12).toLowerCase() + "@fox.com")
                .password(RandomStringUtils.randomAlphanumeric(9))
                .gender(RandomUtils.randomGender())
                .firstName(RandomUtils.randomString(5))
                .lastName(RandomUtils.randomString(8))
                .build()}};
    }

    @DataProvider
    public static Object[][] getInvalidData(){

        return new Object[][]{{
                RegisterRequest.builder()
                        .email(RandomUtils.randomString(12).toLowerCase())
                        .password(RandomStringUtils.randomAlphanumeric(5))
                        .gender("a")
                        .firstName(" ")
                        .lastName(" ")
                        .build()}};
    }

    @DataProvider
    public static Object[][] getBlankData(){

        return new Object[][]{{
                RegisterRequest.builder()
                        .email("")
                        .password("")
                        .gender("")
                        .firstName("")
                        .lastName("")
                        .build()}};
    }

    @DataProvider
    public static Object[][] getNonExistentEmail(){

        return new Object[][]{{
                RegisterRequest.builder()
                        .email("nonexistant@email.com")
                        .build()}};
    }

    @DataProvider
    public static Object[][] getBlankEmail(){

        return new Object[][]{{
                RegisterRequest.builder()
                        .email("")
                        .build()}};
    }
}
