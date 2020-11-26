package com.example.telas_v1.fragmentos.fragmentosmenu.perfil;

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
import android.widget.TextView;

import com.example.telas_v1.activitys.mensagens.ConversasActivity;
import com.example.telas_v1.activitys.postagemcliente.EditarPostagemActivity;
import com.example.telas_v1.activitys.startactivitys.*;
import com.example.telas_v1.R;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class MenuPerfil extends Fragment {

    private View root;
    private MenuPerfilViewModel menuperfil_viewmodel;
    private UserTrabalhador trabalhador;
    private UserCliente cliente;
    private GroupAdapter adapter;
    private TextView txtInfo;
    private MetodosUsers metodosUsers;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menuperfil_viewmodel = ViewModelProviders.of(this).get(MenuPerfilViewModel.class);
        root = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        iniciar();
        return root;
    }

    public static MenuPerfil newInstance() {
        return new MenuPerfil();
    }

    private void iniciar(){
        metodosUsers = new MetodosUsers();
        adapter = new GroupAdapter();
        RecyclerView rcView = root.findViewById(R.id.rcViews);
        txtInfo = root.findViewById(R.id.txtInfo);

        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getContext()));

        verificarUser();
        clickAdapter();
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                    metodosUsers.listarPostagens(adapter, cliente, txtInfo);
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                }
            }
        });
    }

    private void clickAdapter(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if (cliente!=null){
                    Intent intent = new Intent(getContext(), EditarPostagemActivity.class);
                    MetodosUsers.ListarPostagemViewModel model = (MetodosUsers.ListarPostagemViewModel) item;
                    intent.putExtra("post", model.postagem);
                    intent.putExtra("meC", cliente);
                    startActivity(intent);
                }else if (trabalhador!=null){

                }
            }
        });
    }
}