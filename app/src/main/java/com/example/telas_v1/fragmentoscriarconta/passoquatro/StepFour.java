package com.example.telas_v1.fragmentoscriarconta.passoquatro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.telas_v1.LoginActiviy;
import com.example.telas_v1.R;
import com.example.telas_v1.classesuteis.BarraProgresso;
import com.example.telas_v1.classesuteis.MaskUtil;
import com.example.telas_v1.fragmentoscriarconta.passodois.StepTwoViewModel;
import com.example.telas_v1.fragmentoscriarconta.passotres.StepThree;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.core.content.ContextCompat.getSystemService;

public class StepFour extends Fragment{

    //tudo ok,dia: 40/09/2020 ás 00:24
    private StepFourViewModel step_four_viewModel;
    private ConstraintLayout LyFundo;
    private Button btAvancar, btRetornar, btVoltar;
    private TextInputEditText txtSenha, txtSenhaConf;
    private UserCliente userCliente;
    private UserTrabalhador userTrabalhador;
    private Bundle bundle;
    private MetodosUsers metodosUsers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_four_viewModel = ViewModelProviders.of(this).get(StepFourViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_four, container, false);

        metodosUsers = new MetodosUsers();
        userCliente = this.getArguments().getParcelable("cliente");
        userTrabalhador = this.getArguments().getParcelable("trabalhador");
        bundle = new Bundle();


        //Setando
        LyFundo      = root.findViewById(R.id.ly_fundo_quatro);
        btAvancar    = root.findViewById(R.id.bt_avancar);
        btRetornar   = root.findViewById(R.id.bt_retornar);
        btVoltar     = root.findViewById(R.id.bt_voltar);
        txtSenha     = root.findViewById(R.id.txt_senha);
        txtSenhaConf = root.findViewById(R.id.txt_senha_conf);

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
                String senha = txtSenha.getText().toString().trim();
                String senhaConf = txtSenhaConf.getText().toString().trim();

                if (!senha.isEmpty() && !senhaConf.isEmpty()) {
                    if (senha.equals(senhaConf)) {
                        if (senha.length()>=6) {
                            if (userCliente != null) {
                            userCliente.setSenha(txtSenha.getText().toString().trim());
                            metodosUsers.cadastrarUser(getActivity(), getContext(), userCliente, null);
                        } else {
                            userTrabalhador.setSenha(txtSenha.getText().toString().trim());
                            metodosUsers.cadastrarUser(getActivity(), getContext(), null, userTrabalhador);
                        }
                        }else Toast.makeText(getContext(), "A senha deve ter mais de 6 caracteres!", Toast.LENGTH_LONG).show();
                    }else Toast.makeText(getContext(), "Os campos precisam ser iguais!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
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