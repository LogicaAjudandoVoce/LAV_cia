package com.example.telas_v1.fragmentos.fragmentosmenu.contratar;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.postagemcliente.PostagemActivity;
import com.example.telas_v1.activitys.postagemcliente.PostagemAtivaActivity;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class MenuContratos extends Fragment {

    private View root;
    private Spinner spn;
    private TextView txtInfo;
    private UserCliente cliente;
    private GroupAdapter adapter;
    private MetodosUsers metodosUsers;
    private UserTrabalhador trabalhador;
    private MenuContratosViewModel menucontratos_viewmodel;

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
                }else if (cliente!=null){
                    filtrarCliente();
                }
            }
        });

        verificarUser();
        cliclkAdapter();
        return root;
    }

    public static MenuContratos newInstance() {
        return new MenuContratos();
    }

    private void  iniciar(){
        metodosUsers = new MetodosUsers();
        RecyclerView rcView = root.findViewById(R.id.rcViews);
        spn = root.findViewById(R.id.spn_contrat);
        adapter = new GroupAdapter();

        rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        rcView.setAdapter(adapter);

        txtInfo = root.findViewById(R.id.txtInfo);
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                    filtrarCliente();
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
            txtInfo.setText("Nenhum contrato voluntário");
            metodosUsers.listarPostagensTrabalhador(adapter, FirebaseAuth.getInstance().getUid(), "Pendente", txtInfo);
        } else if (spn.getSelectedItem().toString().equals("Contratos Ativos")) {
            txtInfo.setText("Nenhum contrato ativos");
            metodosUsers.listarPostagensTrabalhador(adapter, FirebaseAuth.getInstance().getUid(), "Ativo", txtInfo);
        }else if (spn.getSelectedItem().toString().equals("Contratos Finalizados")){
            txtInfo.setText("Nenhum contrato finalizado");
            metodosUsers.listarPostagensTrabalhador(adapter, FirebaseAuth.getInstance().getUid(), "Finalizado", txtInfo);
        }
    }

    public void filtrarCliente(){
        if (spn.getSelectedItem().toString().equals("Contratos Voluntários")) {
            txtInfo.setText("Nenhum contrato voluntário");
            metodosUsers.listarPostagensCliente(adapter, "Pendente", cliente, txtInfo);
        } else if (spn.getSelectedItem().toString().equals("Contratos Ativos")) {
            txtInfo.setText("Nenhum contrato ativos");
            metodosUsers.listarPostagensCliente(adapter,  "Ativo", cliente, txtInfo);
        }else{
            txtInfo.setText("Nenhum contrato finalizado");
            metodosUsers.listarPostagensCliente(adapter, "Finalizado", cliente, txtInfo);
        }
    }

    private void cliclkAdapter(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if (cliente!=null){
                    if (spn.getSelectedItem().toString().equals("Contratos Voluntários")) {
                        abrirVoluntariosClientes(item);
                    } else if (spn.getSelectedItem().toString().equals("Contratos Ativos")) {
                        abrirAtivosClientes(item);
                    }else{
                        abrirFinalizadosClientes(item);
                    }
                }
                else if(trabalhador!=null){
                    Intent intent = new Intent(getContext(), PostagemActivity.class);
                    MetodosUsers.ListarPostagemViewModel model =(MetodosUsers.ListarPostagemViewModel) item;
                    intent.putExtra("post", model.postagem);
                    intent.putExtra("ativo", "ativo");
                    intent.putExtra("meT", trabalhador);
                    startActivity(intent);
                }
            }
        });
    }

    private void abrirVoluntariosClientes(Item item){
        Intent intent = new Intent(getContext(), TrabalhadoresContratosActivity.class);
        MetodosUsers.ListarPostagemViewModel model =(MetodosUsers.ListarPostagemViewModel) item;
        intent.putExtra("post", model.postagem);
        intent.putExtra("meC", cliente);
        startActivity(intent);
    }

    private void abrirAtivosClientes(Item item){
        Intent intent = new Intent(getContext(), PostagemAtivaActivity.class);
        MetodosUsers.ListarPostagensAtivas model =(MetodosUsers.ListarPostagensAtivas) item;
        intent.putExtra("post", model.postagem);
        intent.putExtra("meC", cliente);
        startActivity(intent);
    }

    private void abrirFinalizadosClientes(Item item){
        Intent intent = new Intent(getContext(), PostagemAtivaActivity.class);
        MetodosUsers.ListarPostagensAtivas model =(MetodosUsers.ListarPostagensAtivas) item;
        intent.putExtra("post", model.postagem);
        intent.putExtra("forma", "finalizado");
        intent.putExtra("meC", cliente);
        startActivity(intent);
    }
}
