package com.sahitya.banksampahsahitya.utils;

import com.sahitya.banksampahsahitya.rest.client.ApiClient;
import com.sahitya.banksampahsahitya.rest.service.ForgotPasswordService;

public class ForgotPasswordUtils {

    public static ForgotPasswordService getForgotPasswordService(){
        return ApiClient.getClient().create(ForgotPasswordService.class);
    }
}
