package com.example.telas_v1.fragmentoscriarconta.passoum;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepOneViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public StepOneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}
