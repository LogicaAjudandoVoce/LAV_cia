package com.example.telas_v1.fragmentoscriarconta.passodois;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.example.telas_v1.R;
import com.example.telas_v1.classesuteis.MaskUtil;
import com.example.telas_v1.fragmentoscriarconta.passotres.StepThree;
import com.example.telas_v1.users.users.UserCliente;
import com.example.telas_v1.users.users.UserTrabalhador;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class StepTwo extends Fragment{

    //tudo ok,dia: 40/09/2020 ás 00:24
    private StepTwoViewModel step_two_viewModel;
    TextInputEditText txtData,txtNome;
    Button btnVoltar,btnAvancar,btnRetorna;
    ConstraintLayout lyFundo;
    private UserCliente userCliente;
    private UserTrabalhador userTrabalhador;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_two_viewModel = ViewModelProviders.of(this).get(StepTwoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_two, container, false);

        userCliente = this.getArguments().getParcelable("cliente");
        userTrabalhador = this.getArguments().getParcelable("trabalhador");
        bundle= new Bundle();

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String nome = txtNome.getText().toString().trim();
                String data = txtData.getText().toString().trim();

                if (!nome.isEmpty() && !data.isEmpty()) {
                    try {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransacion = fragmentManager.beginTransaction();
                        Fragment fragment = new StepThree();
                        Date nasc = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                        LocalDate hoje = nasc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (Integer.valueOf(Period.between(hoje, LocalDate.now()).getYears()) >= 18) {
                            if (userCliente != null) {
                                userCliente.setNome(nome);
                                userCliente.setDataNasc(data);
                                bundle.putParcelable("cliente", userCliente);
                                fragment.setArguments(bundle);
                            } else {
                                userTrabalhador.setNome(nome);
                                userTrabalhador.setDataNasc(data);
                                bundle.putParcelable("trabalhador", userTrabalhador);
                                fragment.setArguments(bundle);
                            }
                            fragmentTransacion.replace(R.id.paipai, fragment).addToBackStack(null).commit();
                        } else {
                            Toast.makeText(getContext(), "Você nãp pode se cadastrar porque é menor de idade.", Toast.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
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
