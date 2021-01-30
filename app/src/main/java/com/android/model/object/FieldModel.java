package com.android.model.object;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

public class FieldModel {
    public static ObservableField<String> firstName = new ObservableField<>();
    public static ObservableArrayList<UserDataModel> store = new ObservableArrayList<>();
}
