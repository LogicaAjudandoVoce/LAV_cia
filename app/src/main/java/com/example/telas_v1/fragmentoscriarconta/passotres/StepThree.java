package com.example.telas_v1.fragmentoscriarconta.passotres;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.telas_v1.fragmentoscriarconta.passoquatro.StepFour;
import com.google.android.material.textfield.TextInputEditText;

public class StepThree extends Fragment {

    ConstraintLayout LyFundo;
    Button btAvancar, btRetornar, btVoltar;
    TextInputEditText txtEmail, txtEmailConf;
    TextView txtError;

    //tudo ok,dia: 40/09/2020 ás 00:24
    private StepThreeViewModel step_three_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_three_viewModel = ViewModelProviders.of(this).get(StepThreeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_three, container, false);


        //Setando
        LyFundo      = root.findViewById(R.id.ly_fundo_tres);
        btAvancar    = root.findViewById(R.id.bt_avancar);
        btRetornar   = root.findViewById(R.id.bt_retornar);
        btVoltar     = root.findViewById(R.id.bt_voltar_frag_dois);
        txtEmail     = root.findViewById(R.id.txt_email);
        txtEmailConf = root.findViewById(R.id.txt_email_confirmar);
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                Fragment fragment = new StepFour();
                fragmentTransacion.replace(R.id.paipai,fragment).addToBackStack(null).commit();
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
                if(txtEmail.getText().equals("") && txtEmailConf.getText().equals("")){
                    txtError.setVisibility(View.INVISIBLE);
                }else{
                    if(txtEmail.getText().equals(txtEmailConf.getText())){
                        txtError.setVisibility(View.INVISIBLE);
                    }else {
                        txtError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Para esconder o teclado no fragment



        return root;
    }

    //bt voltar nativo
    public void onBackPressed(){
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}