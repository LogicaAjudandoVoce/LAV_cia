package com.example.telas_v1.fragmentos_criar_conta.passo_dois;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Step_two_ViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public Step_two_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}