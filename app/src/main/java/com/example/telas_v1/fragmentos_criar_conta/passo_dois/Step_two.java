package com.example.telas_v1.fragmentos_criar_conta.passo_dois;

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
import com.example.telas_v1.fragmentos_criar_conta.passo_dois.Step_two;
import com.example.telas_v1.fragmentos_criar_conta.passo_um.Step_one;
import com.example.telas_v1.fragmentos_criar_conta.passo_um.Step_one_ViewModel;

public class Step_two extends Fragment {

    private Step_two_ViewModel step_two_viewModel;
    Button btnVoltar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_two_viewModel = ViewModelProviders.of(this).get(Step_two_ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_two,container, false);


        btnVoltar = root.findViewById(R.id.bt_voltar_fragmento2);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.paipai, new Step_one());
                ft.commit();
            }
        });


        return root;
    }
}
