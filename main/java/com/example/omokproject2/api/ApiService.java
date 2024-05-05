package com.example.omokproject2.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/register")
    Call<ResponseForm> registerUser(@Body RegisterForm registerForm);

    @GET("user/check-id")
    Call<ResponseForm> checkId(@Query("userId")String userId);

    @GET("api/hello")
    Call<ResponseForm> hello();

    @POST("user/login")
    Call<ResponseForm> login(@Body LoginForm loginForm);

    @POST("gameData/save")
    Call<ResponseForm> saveGameData(@Body GameDataForm gameDataForm);
}
