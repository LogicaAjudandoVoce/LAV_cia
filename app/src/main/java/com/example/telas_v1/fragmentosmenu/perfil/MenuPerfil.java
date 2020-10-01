package com.example.telas_v1.fragmentosmenu.perfil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.telas_v1.LoginActiviy;
import com.example.telas_v1.MenuActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.fragmentosmenu.buscar.MenuBuscar;
import com.example.telas_v1.fragmentosmenu.contratar.MenuContratosViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MenuPerfil extends Fragment {

    private MenuPerfilViewModel menuperfil_viewmodel;
    Button a;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menuperfil_viewmodel = ViewModelProviders.of(this).get(MenuPerfilViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        a = root.findViewById(R.id.bt_sairperfil);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActiviy.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return root;
    }

    public static MenuPerfil newInstance() {
        return new MenuPerfil();
    }
}