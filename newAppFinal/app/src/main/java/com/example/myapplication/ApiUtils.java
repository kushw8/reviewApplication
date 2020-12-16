package com.example.myapplication;


public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.104:3001/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
