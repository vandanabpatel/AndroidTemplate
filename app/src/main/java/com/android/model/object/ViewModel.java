package com.android.model.object;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.BR;

public class ViewModel extends BaseObservable {
    private String cardNumber;
    private String name = null;

    @Bindable
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        notifyPropertyChanged(BR.cardNumber);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
