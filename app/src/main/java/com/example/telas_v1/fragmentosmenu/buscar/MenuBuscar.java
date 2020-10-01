package com.example.telas_v1.fragmentosmenu.buscar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.telas_v1.R;

public class MenuBuscar extends Fragment {

    private MenuBuscarViewModel menubuscar_viewmodel;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menubuscar_viewmodel = ViewModelProviders.of(this).get(MenuBuscarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_buscar, container, false);
        return root;
    }

    public static MenuBuscar newInstance() {
        return new MenuBuscar();
    }

}