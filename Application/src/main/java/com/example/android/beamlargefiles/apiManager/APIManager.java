package com.example.android.beamlargefiles.apiManager;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class APIManager {

    public static JsonPlaceHolderApi getUserManagerService(Converter.Factory converterFactory)
    {

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl("http://smsmkt.smartsmssolution.co.in/api/");
    if(converterFactory != null ) {
            retrofitBuilder.addConverterFactory(converterFactory);
        }
        Retrofit retrofit = retrofitBuilder.build();
        JsonPlaceHolderApi userManagerService = retrofit.create(JsonPlaceHolderApi.class);
        return userManagerService;
    }

}
