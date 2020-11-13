package com.example.telas_v1.fragmentos.fragmentoscriarconta.passoquatro;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.telas_v1.R;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.material.textfield.TextInputEditText;

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
                                userCliente.setUrlFotoFundo("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios?alt=media&token=ee9d410b-3c49-4b8a-b03f-2670f2bfb105");
                                userCliente.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F716c1386-5b82-431c-a2ba-0e16cdeff750?alt=media&token=a48b95e4-7538-4c47-92e8-7f4576eba9c8");
                                userCliente.setSenha(txtSenha.getText().toString().trim());
                                userCliente.setStars(0);
                                userCliente.setCountStar(0);
                            metodosUsers.cadastrarUser(getActivity(), getContext(), userCliente, null);
                        } else {
                            userTrabalhador.setSenha(txtSenha.getText().toString().trim());
                            userTrabalhador.setMyPreco(0.00f);
                            userTrabalhador.setProfUm("Nenhum Selecionado");
                            userTrabalhador.setProfDois("Nenhum Selecionado");
                            userTrabalhador.setProfTres("Nenhum Selecionado");
                            userTrabalhador.setUrlFotoFundo("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios?alt=media&token=ee9d410b-3c49-4b8a-b03f-2670f2bfb105");
                            userTrabalhador.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F2e3534fb-21e2-429c-8f1c-ab8f0f5165ee?alt=media&token=0e3b8518-22ab-4ff2-bc13-367059352e92");
                            userTrabalhador.setStars(0);
                            userTrabalhador.setCountStars(0);
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