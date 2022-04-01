package com.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.R;
import com.android.adapter.SquareAdapter;
import com.android.databinding.ActivitySecondBinding;
import com.android.model.DataModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements SquareAdapter.OnDataClickListener {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = SecondActivity.this;
    private ActivitySecondBinding layoutBinding;

    private SquareAdapter.OnDataClickListener onDataClickListener;
    private SquareAdapter adapter_data;
    private ArrayList<DataModel> list_data = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();

    private int max;
    int randomIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        onDataClickListener = this;

        // get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            max = extras.getInt("number");

            for (int c = 0; c < max; ++c) {
                indices.add(c);
                list_data.add(new DataModel(c, false));
            }
        }

        nextRandom();
    }

    @Override
    public void onItemClickListener(DataModel object) {
        nextRandom();
    }

    public void nextRandom() {
        for (int i = 0; i < list_data.size(); i++) {
            DataModel object_data = list_data.get(i);

            if (object_data.isSelect()) {
                object_data.setRed(true);
                object_data.setGray(false);
            } else if (object_data.isRed()) {
                object_data.setRed(false);
                object_data.setGray(true);
            }

            object_data.setSelect(false);
        }

        int arrIndex = (int) ((double) indices.size() * Math.random());
        if (indices.size() > 0) {
            randomIndex = indices.get(arrIndex);
            indices.remove(arrIndex);

            DataModel object_data = list_data.get(randomIndex);
            object_data.setSelect(true);

            Log.e(TAG, "random no::" + randomIndex);
        }

        fillAdapter_data();
    }

    private void fillAdapter_data() {
        if (list_data != null && list_data.size() > 0) {
            layoutBinding.rvData.setVisibility(View.VISIBLE);

            if (adapter_data == null) {
                adapter_data = new SquareAdapter(mActivity, onDataClickListener);
            }
            adapter_data.doRefresh(list_data);
            Log.e(TAG, pojoToJson(list_data));

            if (layoutBinding.rvData.getAdapter() == null) {
                layoutBinding.rvData.setHasFixedSize(false);
                layoutBinding.rvData.setLayoutManager(new GridLayoutManager(mActivity, 2));
                layoutBinding.rvData.setAdapter(adapter_data);
            }
        } else {
            layoutBinding.rvData.setVisibility(View.GONE);
        }
    }

    public String pojoToJson(Object pojoObject) {
        if (pojoObject != null) {
            return new Gson().toJson(pojoObject);
        } else {
            return null;
        }
    }
}