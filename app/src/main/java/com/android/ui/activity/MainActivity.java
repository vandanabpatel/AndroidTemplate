package com.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.R;
import com.android.adapter.UserDataAdapter;
import com.android.constant.ApiConstant;
import com.android.databinding.ActivityMainBinding;
import com.android.model.object.UserDataModel;
import com.android.services.APIClient;
import com.android.ui.BaseAppCompatActivity;
import com.android.utils.Utility;
import com.android.widget.ItemDecorationVertical;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener,
        UserDataAdapter.OnUserDataClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = MainActivity.this;
    private ActivityMainBinding layoutBinding;

    private UserDataAdapter.OnUserDataClickListener onUserDataClickListener;
    private UserDataAdapter adapter_data;
    private ArrayList<UserDataModel> list_data;

    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        onUserDataClickListener = this;

        initListener();
        request_data();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (isKeyboardTouch) {
            isKeyboardTouch = false;
            return;
        }
        Utility.hideSoftKeyboard(mActivity, layoutBinding.getRoot());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        isKeyboardTouch = true;
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        Utility.hideSoftKeyboard(mActivity);

        switch (v.getId()) {
            /*case R.id.btn_login:
                if (isValidate()) {
                    request_login();
                }
                break;*/
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCallClickListener(UserDataModel object) {

    }

    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (adapter_data != null) {
                    adapter_data.getFilter().filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * fill data list into adapter
     */
    private void fillAdapter_data() {
        if (list_data != null && list_data.size() > 0) {
            layoutBinding.rvData.setVisibility(View.VISIBLE);
            layoutBinding.llNoData.setVisibility(View.GONE);

            if (adapter_data == null) {
                adapter_data = new UserDataAdapter(mActivity, onUserDataClickListener);
                adapter_data.setOnItemClickListener(this);
            }
            adapter_data.doRefresh(list_data);

            if (layoutBinding.rvData.getAdapter() == null) {
                layoutBinding.rvData.setHasFixedSize(false);
                layoutBinding.rvData.setLayoutManager(new LinearLayoutManager(mActivity));
                layoutBinding.rvData.addItemDecoration(new ItemDecorationVertical(mActivity, R.dimen.list_padding, R.dimen.list_padding, R.dimen.list_padding));
                layoutBinding.rvData.setAdapter(adapter_data);
            }
        } else {
            layoutBinding.rvData.setVisibility(View.GONE);
            layoutBinding.llNoData.setVisibility(View.VISIBLE);
        }
    }

    /**
     * API call - data
     */
    public void request_data() {
        if (!isNetworkAvailable(mActivity, true)) {
            return;
        }

        showProgress(mActivity);
        Call call = APIClient.appInterface().getData(
                APIClient.RQ_DATA);
        call.enqueue(new Callback<ArrayList<UserDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDataModel>> call, Response<ArrayList<UserDataModel>> json) {
                hideProgress();
                try {
                    /*UserDataResponse response = (UserDataResponse) Utility.jsonToPojo(json.body().toString(), UserDataResponse.class);
                    if (json.isSuccessful() && response.getCode() == ApiConstant.STATUS_CODE_SUCCESS) {
                        list_feedback = response.getPayload();
                    } else {
                        showSnackbarError(mActivity, response.getMessage());
                    }*/

                    if (json.isSuccessful()) {
                        list_data = json.body();
                    } else {
                        // showSnackbarError(mActivity, response.getMessage());
                    }

                    fillAdapter_data();
                } catch (Exception e) {
                    e.printStackTrace();
                    showExceptionError(mActivity, json.code(), json.errorBody());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideProgress();
                showExceptionError(mActivity);
            }
        });
    }
}