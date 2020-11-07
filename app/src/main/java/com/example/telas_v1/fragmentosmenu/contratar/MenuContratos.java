package com.example.telas_v1.fragmentosmenu.contratar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.telas_v1.R;

public class MenuContratos extends Fragment {

    private MenuContratosViewModel menucontratos_viewmodel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menucontratos_viewmodel = ViewModelProviders.of(this).get(MenuContratosViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_contratos, container, false);

        return root;
    }

    public static MenuContratos newInstance() {
        return new MenuContratos();

    }
}
