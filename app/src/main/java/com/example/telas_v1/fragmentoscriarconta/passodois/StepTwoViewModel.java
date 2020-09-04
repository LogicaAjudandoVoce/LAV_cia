package com.example.telas_v1.fragmentoscriarconta.passodois;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepTwoViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public StepTwoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}