package com.example.tenkisen.ui.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalculatorViewModel() {

    }
    public LiveData<String> getText() {
        return mText;
    }
}
