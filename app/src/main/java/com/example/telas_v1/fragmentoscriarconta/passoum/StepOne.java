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

import com.example.telas_v1.startactivitys.MainActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.fragmentoscriarconta.passodois.StepTwo;
import com.example.telas_v1.users.users.UserCliente;
import com.example.telas_v1.users.users.UserTrabalhador;

public class StepOne extends Fragment {

    private Button btnVoltar;
    private Button btnClient;
    private Button btnTrab;
    private UserTrabalhador userTrabalhador;
    private UserCliente userCliente;
    private Bundle bundle;

    private StepOneViewModel step_one_viewModel;

    //tudo ok,dia: 40/09/2020 ás 00:24
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

        bundle = new Bundle();
        //botão Cliente & Trabalhador
        btnClient = (Button) root.findViewById(R.id.bt_client);
        btnTrab = (Button) root.findViewById(R.id.bt_trab);

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui é o cliente selecionado
                userCliente = new UserCliente();
                userCliente.setTipoUser("Cliente");
                bundle.putParcelable("cliente", userCliente);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepTwo();
                fragment.setArguments(bundle);
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
            }
        });

        btnTrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui é o trab selecionado
                userTrabalhador = new UserTrabalhador();
                userTrabalhador.setTipoUser("Trabalhador");
                bundle.putParcelable("trabalhador", userTrabalhador);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepTwo();
                fragment.setArguments(bundle);
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
            }
        });
        //NAO MEXE AQUI EMBAIXO
        return root;
    }
}
