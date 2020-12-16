package com.example.myapplication;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> getStringScalar(@Field("Fullname") String Fullname, @Field("email")
            String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> getAuthUser(@Field("email") String email, @Field("password") String password);
    

}
