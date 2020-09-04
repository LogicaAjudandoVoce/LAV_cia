package com.example.telas_v1.fragmentoscriarconta.passoum;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.telas_v1.MainActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.fragmentoscriarconta.passodois.StepTwo;

public class StepOne extends Fragment {

    Button btnVoltar;
    Button btnClient;
    Button btnTrab;

    private StepOneViewModel step_one_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_one_viewModel = ViewModelProviders.of(this).get(StepOneViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_one,container, false);
        //NÃO MEXE AQUI EM CIMA




        //Botão Voltar
        btnVoltar = (Button) root.findViewById(R.id.bt_voltar_fragmento);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getContext(),MainActivity.class);
                startActivity(home);
            }
        });

        //botão Cliente & Trabalhador
        btnClient = (Button) root.findViewById(R.id.bt_client);
        btnTrab = (Button) root.findViewById(R.id.bt_trab);

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui é o cliente selecionado
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepTwo();
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
            }
        });

        btnTrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui é o trab selecionado
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepTwo();
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
            }
        });
        //NAO MEXE AQUI EMBAIXO
        return root;
    }
}
