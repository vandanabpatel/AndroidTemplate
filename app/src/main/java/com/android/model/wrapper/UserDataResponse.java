package com.android.model.wrapper;

import com.android.model.ResponseModel;
import com.android.model.object.UserDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDataResponse extends ResponseModel {
    @Expose
    @SerializedName("payload")
    private ArrayList<UserDataModel> payload;

    public ArrayList<UserDataModel> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<UserDataModel> payload) {
        this.payload = payload;
    }
}
