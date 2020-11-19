package com.example.telas_v1.fragmentos.fragmentosmenu.contratar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.telas_v1.R;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.groupie.GroupAdapter;

public class MenuContratos extends Fragment {

    private View root;
    private Spinner spn;
    private GroupAdapter adapter;
    private UserTrabalhador trabalhador;
    private UserCliente cliente;
    private MenuContratosViewModel menucontratos_viewmodel;
    private MetodosUsers metodosUsers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menucontratos_viewmodel = ViewModelProviders.of(this).get(MenuContratosViewModel.class);
        root = inflater.inflate(R.layout.fragment_menu_contratos, container, false);

        iniciar();

        Button btnFiltrar = root.findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trabalhador!=null){
                    filtrarTrab();
                }
            }
        });

        verificarUser();
        return root;
    }

    public static MenuContratos newInstance() {
        return new MenuContratos();
    }

    private void  iniciar(){
        metodosUsers = new MetodosUsers();
        RecyclerView rcView = root.findViewById(R.id.rcView);
        spn = root.findViewById(R.id.spn_contrat);
        adapter = new GroupAdapter();

        rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        rcView.setAdapter(adapter);
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){

                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador= userTrabalhador;
                    filtrarTrab();
                }
            }
        });
    }

    public void filtrarTrab(){
        if (spn.getSelectedItem().toString().equals("Contratos Voluntários")) {
            metodosUsers.listarPostagensVoluntariosTrab(adapter, FirebaseAuth.getInstance().getUid(), "Pendente");
        } else if (spn.getSelectedItem().toString().equals("Contratos Ativos")) {
            MetodosUsers metodosUsers = new MetodosUsers();
            metodosUsers.listarPostagensVoluntariosTrab(adapter, FirebaseAuth.getInstance().getUid(), "Ativo");
        }else{
            MetodosUsers metodosUsers = new MetodosUsers();
            metodosUsers.listarPostagensVoluntariosTrab(adapter, FirebaseAuth.getInstance().getUid(), "Finalizado");
        }
    }
}
