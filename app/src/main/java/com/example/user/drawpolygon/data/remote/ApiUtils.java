package com.example.user.drawpolygon.data.remote;

public class ApiUtils {
    private ApiUtils(){}
    public static final String BASE_URL = "http://akampro.net";

    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
