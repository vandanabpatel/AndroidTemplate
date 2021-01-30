package com.android.services;

import android.text.TextUtils;

import com.android.BuildConfig;
import com.android.constant.ApiConstant;
import com.android.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String RQ_GOOGLE_PLACE_BYID = "https://maps.googleapis.com/maps/api/place/details/json?key=%s";
    public static final String RQ_GOOGLE_PLACE_BYLATLNG = "https://maps.googleapis.com/maps/api/geocode/json?key=%s";

    public static final String RQ_DATA = "users/octocat/followers";

    public static APIInterface appInterface(boolean isMultipart) {
        OkHttpClient.Builder httpClient = getClientBuilder(isMultipart);
        return getClient(httpClient, BuildConfig.SERVER).create(APIInterface.class);
    }

    public static OkHttpClient.Builder getClientBuilder(boolean isMultipart) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(3, TimeUnit.MINUTES);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder request = original.newBuilder();

                if (isMultipart) {
                    request.addHeader(ApiConstant.HEADER_CONTENT_TYPE, ApiConstant.CONTENT_TYPE_MULTIPART_FORM);
                }
                if (!TextUtils.isEmpty(PreferenceUtils.getInstance().getAuthToken())) {
                    request.addHeader(ApiConstant.HEADER_AUTH_TOKEN, PreferenceUtils.getInstance().getAuthToken());
                }
                return chain.proceed(request.build());
            }
        });

        httpClient.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.IS_DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build());

        return httpClient;
    }

    public static Retrofit getClient(OkHttpClient.Builder httpClient, String baseUrl) {
        GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping();
        Gson gson = gsonBuilder.setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}