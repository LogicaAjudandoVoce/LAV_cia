package com.example.telas_v1.fragmentoscriarconta.passodois;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.example.telas_v1.R;
import com.example.telas_v1.classesuteis.MaskUtil;
import com.example.telas_v1.fragmentoscriarconta.passotres.StepThree;
import com.google.android.material.textfield.TextInputEditText;

import static androidx.core.content.ContextCompat.getSystemService;

public class StepTwo extends Fragment{

    //tudo ok,dia: 40/09/2020 ás 00:24
    private StepTwoViewModel step_two_viewModel;
    TextInputEditText txtData,txtNome;
    Button btnVoltar,btnAvancar,btnRetorna;
    ConstraintLayout lyFundo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_two_viewModel = ViewModelProviders.of(this).get(StepTwoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_two, container, false);


        btnVoltar  = root.findViewById(R.id.bt_voltar_fragmento2);
        btnAvancar = root.findViewById(R.id.bt_avancar);
        btnRetorna = root.findViewById(R.id.bt_retornar);
        txtData = root.findViewById(R.id.txt_data);
        txtNome = root.findViewById(R.id.txt_nome);
        lyFundo = root.findViewById(R.id.ly_fundo_dois);

        //bt voltar artificial
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //bt avançar
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepThree();
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
            }
        });

        //bt retornar
        btnRetorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //Máscara
        txtData.addTextChangedListener(MaskUtil.mask(txtData, "##/##/####"));

        //esconder teclado
        lyFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        return root;

    }


    //botão voltar nativo
    public void onBackPressed(){
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}
