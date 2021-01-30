package com.android.services;

import com.android.model.object.UserDataModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIInterface {
    @GET
    Call<ResponseBody> download(@Url String url);

    @GET
    Call<JsonObject> get(@Url String url);

    @GET
    Call<JsonObject> get(@Url String url, @QueryMap Map<String, String> params);

    @GET
    Call<JsonArray> getArray(@Url String url);

    @FormUrlEncoded
    @PUT
    Call<JsonObject> put(@Url String url,
                         @FieldMap Map<String, String> params);

    @POST
    Call<JsonObject> postJson(@Url String url,
                              @Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST
    Call<JsonObject> post(@Url String url,
                          @FieldMap Map<String, String> params);

    @DELETE
    Call<JsonObject> delete(@Url String url);

    @Multipart
    @POST
    Call<JsonObject> upload(@Url String url,
                            @PartMap Map<String, RequestBody> params,
                            @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<JsonObject> upload(@Url String url,
                            @PartMap Map<String, RequestBody> params,
                            @Part MultipartBody.Part[] file);

    @GET
    Call<ArrayList<UserDataModel>> getData(@Url String url);
}
