package com.example.telas_v1.fragmentos.fragmentosmenu.perfil;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuPerfilViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public MenuPerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}
