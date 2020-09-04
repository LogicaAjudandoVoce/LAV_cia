package com.example.telas_v1.fragmentoscriarconta.passodois;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepTwoViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public StepTwoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}