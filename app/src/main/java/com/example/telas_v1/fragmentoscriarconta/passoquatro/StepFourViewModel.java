package com.example.telas_v1.fragmentoscriarconta.passoquatro;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepFourViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public StepFourViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}