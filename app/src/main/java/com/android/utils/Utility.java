package com.android.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.android.BuildConfig;
import com.android.R;
import com.android.constant.ApiConstant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final String TAG = "Utility";

    @BindingAdapter("version")
    public static void bindVersionName(TextView textView, String value) {
        textView.setText(String.format(value, BuildConfig.VERSION_NAME));
    }

    @BindingAdapter("ripple")
    public static void bindRipple(View view, int res) {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            view.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, typedValue, true);
            view.setBackgroundResource(typedValue.resourceId);
        }
    }

    @BindingAdapter("dialogPadding")
    public static void bindHeight(LinearLayout view, float padding) {
        view.setLayoutParams(new FrameLayout.LayoutParams(
                (int) (DisplayUtils.getInstance().getWidth() * padding),
                (int) (DisplayUtils.getInstance().getHeight() * 0.85f)));
    }

    @BindingAdapter("dialogHeight")
    public static void bindDialogHeight(LinearLayout view, boolean isFillHeight) {
        view.setMinimumWidth(DisplayUtils.getInstance().getDialogWidth());
        view.setMinimumHeight(isFillHeight ? (int) (DisplayUtils.getInstance().getHeight() * 0.9f) :
                FrameLayout.LayoutParams.WRAP_CONTENT);
        /*view.setLayoutParams(new FrameLayout.LayoutParams(
                DisplayUtils.getInstance().getDialogWidth(),
                isFillHeight ? (int) (DisplayUtils.getInstance().getHeight() * 0.9f) :
                        FrameLayout.LayoutParams.WRAP_CONTENT));*/
    }

    /**
     * This method checks if the Network available on the device or not.
     *
     * @param mContext
     * @return true if network available, false otherwise
     */
    public static boolean isNetworkAvailable(Context mContext) {
        boolean connected = false;
        try {
            final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                connected = true;
            } else if (netInfo != null && netInfo.isConnected() &&
                    cm.getActiveNetworkInfo().isAvailable()) {
                connected = true;
            } else if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == ApiConstant.STATUS_CODE_SUCCESS) {
                        connected = true;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (cm != null) {
                final NetworkInfo[] netInfoAll = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfoAll) {
                    if ((ni.getTypeName().equalsIgnoreCase("WIFI") || ni
                            .getTypeName().equalsIgnoreCase("MOBILE"))
                            && ni.isConnected() && ni.isAvailable()) {
                        connected = true;
                        if (connected) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connected;
    }

    /**
     * check is user logged in
     */
    public static boolean isLoggedIn() {
        if (PreferenceUtils.getInstance().isLoggedIn() &&
                !TextUtils.isEmpty(PreferenceUtils.getInstance().getUsername()) &&
                !TextUtils.isEmpty(PreferenceUtils.getInstance().getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get application version
     *
     * @param mContext
     * @return
     */
    public static String getPackageName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * find your keyhash for facebook getFileFromContentProvider package name
     *
     * @param mContext
     */
    public static void getFacebookKeyHash(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(getPackageName(mContext), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * show keyboard
     *
     * @param mContext
     * @param view
     */
    public static void showSoftKeyboard(Context mContext, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide keyboard if visible
     *
     * @param mActivity
     * @param view
     */
    public static void hideSoftKeyboard(final Activity mActivity, View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mActivity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideSoftKeyboard(mActivity, innerView);
            }
        }
    }

    /**
     * hide keyboard if visible
     *
     * @param mActivity
     */
    public static void hideSoftKeyboard(Activity mActivity) {
        try {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            // Find the currently focused view, so we can grab the correct window token getFileFromContentProvider it.
            View view = mActivity.getCurrentFocus();
            // If no view currently has focus, create a new one, just so we can grab a window token getFileFromContentProvider it
            if (view == null) {
                view = new View(mActivity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * visible password hint
     *
     * @param view
     */
    public static void visiblePasswordHint(final View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0f);
            view.post(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(300).start();
                    ObjectAnimator.ofFloat(view, "translationY", -(view.getHeight() / 4), 0).setDuration(300).start();
                }
            });
        }
    }

    /**
     * focus on view
     *
     * @param editText
     */
    public static void requestSelection(EditText editText) {
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setSelection(editText.getText().toString().trim().length());
        }
    }

    /**
     * focus on view
     *
     * @param mActivity
     * @param view
     */
    public static void requestFocus(Activity mActivity, View view) {
        if (view.requestFocus()) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * multiline EditText make scrollable
     *
     * @param editText
     */
    public static void enableEditTextScolling(EditText editText) {
        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * get sub string getFileFromContentProvider source string
     *
     * @param sourceString
     * @param length
     * @return
     */
    public static String getlastSubString(String sourceString, int length) {
        try {
            int subStringLength = sourceString.length();
            if (subStringLength <= length) {
                return sourceString;
            }
            int startIndex = subStringLength - length;
            return sourceString.substring(startIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * decode source string into UTF-8
     *
     * @param sourceString
     * @return
     */
    public static String decodeString(String sourceString) {
        try {
            return URLDecoder.decode(sourceString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return sourceString;
        }
    }

    /**
     * encrypt source string to base64
     *
     * @param sourceString
     * @return
     */
    public static String encodeString(String sourceString) {
        return Base64.encodeToString(sourceString.getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
    }

    /**
     * check given url is http/https
     *
     * @param url
     * @return
     */
    public static boolean isHttpsUrl(String url) {
        if (URLUtil.isHttpsUrl(url)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check given string is url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (URLUtil.isValidUrl(url)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * convert source string to camelcase
     *
     * @param sourceString
     * @return
     */
    public static String convertStringToCamelLetter(String sourceString) {
        return sourceString.substring(0, 1).toUpperCase() + sourceString.substring(1).toLowerCase();
    }

    /**
     * source string make bold to given spanned text
     *
     * @param mContext
     * @param sourceString
     * @param spannedText
     * @return
     */
    public static Spannable spannableString(Context mContext, String sourceString, String spannedText) {
        if (sourceString == null && sourceString.trim().length() == 0) {
            return new SpannableString("");
        }

        Spannable ss = new SpannableString(sourceString.trim());
        Pattern pattern = Pattern.compile(Pattern.quote(spannedText));
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            ss.setSpan(new StyleSpan(Typeface.BOLD),
                    matcher.start(), matcher.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }

    /**
     * remove '_' getFileFromContentProvider source string and convert to camelcase
     *
     * @param sourceString
     * @return
     */
    public static String removeUnderscoreAndConvertToCamelLetter(String sourceString) {
        try {
            String[] parts = sourceString.split("_");
            String formattedValue = "";
            for (String part : parts) {
                formattedValue = formattedValue + " " + convertStringToCamelLetter(part);
            }

            return formattedValue;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * load JSON getFileFromContentProvider assets .json file
     *
     * @param mContext
     * @param sourceFile
     * @return
     */
    public static String loadJSONFromAsset(Context mContext, String sourceFile) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(sourceFile + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * json to pojo with deserializer
     *
     * @param jsonString
     * @param pojoClass
     * @param jsonDeserializer
     * @param pojoType
     * @return
     */
    public static Object jsonToPojoWithDeserializer(String jsonString, Class<?> pojoClass, JsonDeserializer<?> jsonDeserializer, Type pojoType) {
        return new GsonBuilder().registerTypeAdapter(pojoType, jsonDeserializer).create().fromJson(jsonString, pojoClass);
    }

    /**
     * json to pojo with type class
     *
     * @param jsonString
     * @param pojoType
     * @return
     */
    public static Object jsonToPojo(String jsonString, Type pojoType) {
        return new Gson().fromJson(jsonString, pojoType);
    }

    /**
     * json to pojo
     *
     * @param jsonString
     * @param pojoClass
     * @return
     */
    public static Object jsonToPojo(String jsonString, Class<?> pojoClass) {
        return new Gson().fromJson(jsonString, pojoClass);
    }

    /**
     * list convert to JsonArray
     *
     * @param object
     * @return
     */
    public static JsonArray objectToJsonArray(Object object) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(gson.toJson(object));
        return jsonElement.getAsJsonArray();
    }

    /**
     * list convert to JsonArray
     *
     * @param object
     * @return
     */
    public static JsonObject objectToJsonObject(Object object) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(gson.toJson(object));
        return jsonElement.getAsJsonObject();
    }

    /**
     * string convert to JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject stringToJsonObject(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new Gson().toJson(json));
        return jsonElement.getAsJsonObject();
    }

    /**
     * pojo to json
     *
     * @param pojoObject
     * @return
     */
    public static String pojoToJson(Object pojoObject) {
        if (pojoObject != null) {
            return new Gson().toJson(pojoObject);
        } else {
            return null;
        }
    }

    /**
     * string convert to camelcase
     *
     * @param value
     * @return
     */
    public static String toCamelCase(String value) {
        if (value == null) {
            return "";
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(value);
        final int length = builder.length();
        for (int i = 0; i < length; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    /**
     * expand view
     *
     * @param view
     */
    public static void expandView(final View view) {
        view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int) (targetHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }

    /**
     * collapse view
     *
     * @param view
     */
    public static void collapseView(final View view) {
        final int initialHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int) (initialHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }

    /**
     * double value conversion
     *
     * @param value
     * @return
     */
    public static String doubleValueConversionForPrice(float value) {
        try {
            if (value == 0) {
                return "";
            } else {
                return "$" + String.format("%.2f", value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * double value conversion
     */
    public static String doubleValueConversionForTotal(float value) {
        try {
            return "$" + String.format("%.2f", value);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * append comma
     *
     * @param sb
     * @return
     */
    public static StringBuilder appendComma(StringBuilder sb) {
        if (!TextUtils.isEmpty(sb.toString())) {
            sb.append(", ");
        }

        return sb;
    }

    /**
     * get value with comma seperated
     *
     * @param list
     * @return
     */
    public static String getValue(ArrayList<String> list) {
        StringBuilder sb_value = new StringBuilder();
        try {
            for (String value : list) {
                sb_value.append(value).append(",");
            }

            if (sb_value.length() > 1) {
                sb_value.deleteCharAt(sb_value.lastIndexOf(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb_value.toString();
    }
}