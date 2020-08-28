package com.example.telas_v1.fragmentos_criar_conta.passo_um;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Step_one_ViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public Step_one_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}
