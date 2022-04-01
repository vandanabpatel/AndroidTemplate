package com.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.R;
import com.android.databinding.ActivityInputBinding;
import com.android.utils.IntentUtils;

public class InputActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = InputActivity.this;
    private ActivityInputBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_input);

        layoutBinding.btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("number", Integer.parseInt(layoutBinding.etNumber.getText().toString()));

                IntentUtils.getInstance().navigateToNextActivity(mActivity,
                        null,
                        SecondActivity.class,
                        bundle,
                        null);
            }
        });
    }
}