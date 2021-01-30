package com.android.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static String TAG = "ValidationUtils";
    private static ValidationUtils instance;

    public static final String validate_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";
    public static final String validate_ifsccode = "^[A-Za-z]{4}0[A-Z0-9a-z]{6}$";
    public static final String validate_pancard = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

    private ValidationUtils() {
    }

    public static ValidationUtils getInstance() {
        if (instance == null) {
            instance = new ValidationUtils();
        }
        return instance;
    }

    /**
     * postal code  - set max length
     *
     * @param view
     * @param maxLength
     */
    public static void setMaxLength(View view, int maxLength) {
        try {
            if (view instanceof EditText)
                ((EditText) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            else if (view instanceof TextView)
                ((TextView) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * validate postal code
     *
     * @param value
     * @return
     */
    public static boolean isValidPattern(String value, String validatePattern) {
        try {
            Pattern pattern = Pattern.compile(validatePattern);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * validate phone
     *
     * @param value
     * @return
     */
    public static boolean isValidPhone(String value) {
        CharSequence inputStr = value;
        if (inputStr.length() >= 10) {
            return true;
        } else {
            return false;
        }

//        return Patterns.PHONE.matcher(value).matches();
    }

    /**
     * validate email
     *
     * @param value
     * @return
     */
    public static boolean isValidEmail(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    /**
     * validate password
     *
     * @param value
     * @return
     */
    public static boolean isValidPassword(String value) {
        if (value.length() < 6) {
            return false;
        }
        /*if (!value.matches(".*\\d.*")) {
            return false;
        }
        if (!value.matches(".*[A-Z].*")) {
            return false;
        }
        if (!value.matches(".*[a-z].*")) {
            return false;
        }
        if (!value.matches(".*[^a-zA-Z0-9 ].*")) {
            return false;
        }*/

        return true;
    }

    /**
     * validate password
     *
     * @param value
     * @return
     */
    public static boolean isValidPincode(String value) {
        if (value.length() < 6) {
            return false;
        }

        return true;
    }

    /**
     * validate password
     *
     * @param value
     * @return
     */
    public static boolean isValidAadharCard(String value) {
        if (value.length() < 12) {
            return false;
        }

        return true;
    }

    /**
     * check empty field
     */
    public static class EmptyTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        private EditText editText;
        private String errorMessage;

        public EmptyTextWatcher(TextInputLayout textInputLayout, EditText editText, String errorMessage) {
            this.textInputLayout = textInputLayout;
            this.editText = editText;
            this.errorMessage = errorMessage;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textInputLayout.setError(errorMessage);
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check empty field
     */
    public static class EmptyTextViewTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        private TextView textView;
        private String errorMessage;

        public EmptyTextViewTextWatcher(TextInputLayout textInputLayout, TextView textView, String errorMessage) {
            this.textInputLayout = textInputLayout;
            this.textView = textView;
            this.errorMessage = errorMessage;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                textInputLayout.setError(errorMessage);
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check email is valid or not
     */
    public static class EmailTextWatcher implements TextWatcher {
        private Activity mActivity;
        private TextInputLayout textInputLayout;
        private EditText editText;

        public EmailTextWatcher(Activity mActivity, TextInputLayout textInputLayout, EditText editText) {
            this.mActivity = mActivity;
            this.textInputLayout = textInputLayout;
            this.editText = editText;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_email));
            } else if (!isValidEmail(editText.getText().toString().trim())) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_valid_email));
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * check phone valid or not
     */
    public static class PhoneTextWatcher implements TextWatcher {
        private Activity mActivity;
        private TextInputLayout textInputLayout;
        private EditText editText;

        public PhoneTextWatcher(Activity mActivity, TextInputLayout textInputLayout, EditText editText) {
            this.mActivity = mActivity;
            this.textInputLayout = textInputLayout;
            this.editText = editText;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(editText.getText().toString().trim()) &&
                    editText.getText().toString().trim().substring(0, 1).equals("1")) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_valid_digit_phone));
            } else if (!isValidPhone(editText.getText().toString().trim())) {
                textInputLayout.setError(mActivity.getResources().getString(R.string.error_enter_valid_phone_number));
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
    }
}
