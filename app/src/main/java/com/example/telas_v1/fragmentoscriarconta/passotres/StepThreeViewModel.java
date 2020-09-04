package com.example.telas_v1.fragmentoscriarconta.passotres;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepThreeViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public StepThreeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}