package com.android.constant;

public class ApiConstant {
    public static final String checkout_type = "registered";

    // response code
    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_CONNECTION_ERROR = -101;
    public static final int STATUS_CODE_400 = 400;

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_ERROR = "error";

    // http request method type
    public static final String UTF8 = "UTF-8";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    /**
     * mime type
     */
    public static final String MIME_IMAGE = "image";
    public static final String MIME_VIDEO = "video";
    public static final String MIME_AUDIO = "audio";

    // server
//    public static final String SERVER = BuildConfig.SERVER;
    public static final String API_V = "api/v1/";
    public static final String API_APP = "api/app/";

    // request header
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_APP_ID = "app-id";
    public static final String HEADER_APP_SECRET = "app-secret";
    public static final String HEADER_AUTH_TOKEN = "token_id";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // request header - content type
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_MULTIPART_FORM = "multipart/form-data";
    public static final String IF_NONE_MATCH = "If-None-Match";
    public static final String ETag = "ETag";

    // extra params
    public static final String DEFAULT_PARAM_LATLONG = "&latitude=%s&longitude=%s";
    public static final String DEFAULT_PARAM_LATLONG_MAP = "&map_lat=%s&map_long=%s";
    public static final String SEARCH = "&filter=%s";
    public static final String CATERING = "?catering=1";
    public static final String BILLING_DETAIL = "?billing_detail=true";
    public static final String FROM = "?from=%s";
}