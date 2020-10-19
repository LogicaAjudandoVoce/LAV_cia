package com.example.telas_v1.fragmentosmenu.buscar;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.telas_v1.PostagemActivity;
import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.postagemcliente.NovaPostagemClienteActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class MenuBuscar extends Fragment {

    private MenuBuscarViewModel menubuscar_viewmodel;
    private GroupAdapter adapter;
    private RecyclerView rcView;
    private MetodosUsers metodosUsers;
    private Button btnPesq;
    private EditText txtCidade, txtPreco;
    private Spinner tipoTrab;
    private UserCliente cliente;
    private UserTrabalhador trabalhador;
    private FloatingActionButton btnAddPost;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menubuscar_viewmodel = ViewModelProviders.of(this).get(MenuBuscarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_buscar, container, false);

        rcView = root.findViewById(R.id.rcView);
        txtCidade = root.findViewById(R.id.txtCidade);
        txtPreco = root.findViewById(R.id.txtPreco);
        tipoTrab = root.findViewById(R.id.tipoTrab);
        btnAddPost = root.findViewById(R.id.btnAddPost);

        metodosUsers = new MetodosUsers();
        adapter = new GroupAdapter();
        rcView.setHasFixedSize(true);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if (trabalhador!=null){
                    Intent intent = new Intent(getContext(), PostagemActivity.class);
                    MetodosUsers.ListarPostagemViewModel model =(MetodosUsers.ListarPostagemViewModel) item;
                    intent.putExtra("post", model.postagem);
                    intent.putExtra("forma", "menu");
                    startActivity(intent);
                }
            }
        });


        btnPesq = root.findViewById(R.id.btnPesq);
        btnPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente!=null) {
                    String cidade;
                    float preco;

                    if (txtPreco.getText().toString().isEmpty()) preco = 0;
                    else preco = Float.valueOf(txtPreco.getText().toString());
                    if (txtCidade.getText().toString().isEmpty()) cidade = "Qualquer";
                    else cidade = txtCidade.getText().toString();

                    metodosUsers.listarTrabalhador(adapter, tipoTrab.getSelectedItem().toString(), cidade, preco);
                }
            }
        });
        verificarUser();

        if (trabalhador!=null){
            btnAddPost.setVisibility(View.INVISIBLE);
            btnAddPost.setEnabled(false);
        }

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NovaPostagemClienteActivity.class));
            }
        });
        return root;
    }

    public static MenuBuscar newInstance() {
        return new MenuBuscar();
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                    metodosUsers.listarTrabalhador(adapter, tipoTrab.getSelectedItem().toString(), "Qualquer", 0);
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                    metodosUsers.listarPostagens(adapter);
                    btnAddPost.setVisibility(View.INVISIBLE);
                    btnAddPost.setEnabled(false);
                }
            }
        });
    }
}