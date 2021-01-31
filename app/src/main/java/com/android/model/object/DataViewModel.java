package com.android.model.object;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataViewModel {
    public static ObservableField<String> firstName = new ObservableField<>();
    public static ObservableArrayList<UserDataModel> store = new ObservableArrayList<>();
}
