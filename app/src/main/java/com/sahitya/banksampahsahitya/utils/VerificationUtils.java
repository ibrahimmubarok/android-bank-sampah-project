package com.sahitya.banksampahsahitya.utils;

import com.sahitya.banksampahsahitya.rest.client.ApiClient;
import com.sahitya.banksampahsahitya.rest.service.VerificationService;

public class VerificationUtils {

    public static VerificationService getVerificationService(){
        return ApiClient.getClient().create(VerificationService.class);
    }
}
