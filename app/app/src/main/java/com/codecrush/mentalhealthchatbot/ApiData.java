package com.codecrush.mentalhealthchatbot;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiData
{
    /*@FormUrlEncoded
    @POST("signup")
    Call<JsonObject> forSignUp(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("email") String email,
            @Field("password") String password
    );*/


    @POST("signup")
    Call<JsonObject> forSignUp(@Body RequestBody body);

    /*@FormUrlEncoded
    @POST("login")
    Call<JsonObject> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );*/

    @POST("login")
    Call<JsonObject> forLogin(@Body RequestBody body);


}
