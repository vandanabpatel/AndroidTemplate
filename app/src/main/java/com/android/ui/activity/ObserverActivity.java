package com.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.android.R;
import com.android.databinding.ActivityObserverBinding;
import com.android.model.object.DataViewModel;
import com.android.model.object.ViewModel;
import com.android.ui.BaseAppCompatActivity;
import com.android.utils.Utility;

public class ObserverActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = ObserverActivity.this;
    private ActivityObserverBinding layoutBinding;

    private DataViewModel field = new DataViewModel();
    private ViewModel object_view = new ViewModel();

    private boolean isKeyboardTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer);
        layoutBinding.setListener(this);
        layoutBinding.setField(field);
        layoutBinding.setView(object_view);

//        layoutBinding.tvUsername.setText(DataViewModel.username.get());

        initListener();
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
            case R.id.btn_update:
//                UserDataModel object_store = new UserDataModel();
//                object_store.setDisplayName("vandana");
//                field.store.add(object_store);
                field.firstName.set("vandana");
                break;
        }
    }

    /**
     * initialize listener
     */
    private void initListener() {
        layoutBinding.btnUpdate.setOnClickListener(this);

        layoutBinding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DataViewModel.firstName.set(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}