package com.example.telas_v1.fragmentoscriarconta.passoquatro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.telas_v1.LoginActiviy;
import com.example.telas_v1.R;
import com.example.telas_v1.classesuteis.MaskUtil;
import com.example.telas_v1.fragmentoscriarconta.passodois.StepTwoViewModel;
import com.example.telas_v1.fragmentoscriarconta.passotres.StepThree;
import com.google.android.material.textfield.TextInputEditText;

import static androidx.core.content.ContextCompat.getSystemService;

public class StepFour extends Fragment{

    //tudo ok,dia: 40/09/2020 ás 00:24
    private StepFourViewModel step_four_viewModel;
    ConstraintLayout LyFundo;
    Button btAvancar, btRetornar, btVoltar;
    TextInputEditText txtSenha, txtSenhaConf;
    TextView txtError;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_four_viewModel = ViewModelProviders.of(this).get(StepFourViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_four, container, false);

        //Setando
        LyFundo      = root.findViewById(R.id.ly_fundo_quatro);
        btAvancar    = root.findViewById(R.id.bt_avancar);
        btRetornar   = root.findViewById(R.id.bt_retornar);
        btVoltar     = root.findViewById(R.id.bt_voltar);
        txtSenha     = root.findViewById(R.id.txt_email);
        txtSenhaConf = root.findViewById(R.id.txt_email_confirmar);
        txtError     = root.findViewById(R.id.txt_error_tres);

        //bt retornar (seta)
        btRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //bt avancar
        btAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getActivity(), LoginActiviy.class);
                startActivity(login);
                getActivity().finish();
            }
        });

        //bt voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //teclado e bang
        LyFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        return root;
    }

    //bt voltar nativo
    public void onBackPressed(){
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}