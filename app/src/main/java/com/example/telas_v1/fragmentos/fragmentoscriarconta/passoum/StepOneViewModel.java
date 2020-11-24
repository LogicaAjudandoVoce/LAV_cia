package com.example.telas_v1.fragmentos.fragmentoscriarconta.passoum;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepOneViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public StepOneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}
