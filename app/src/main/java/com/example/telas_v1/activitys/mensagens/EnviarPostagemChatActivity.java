package com.example.telas_v1.activitys.mensagens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.xwray.groupie.GroupAdapter;

public class EnviarPostagemChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    private UserCliente cliente;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_postagem_chat);
        inicar();
    }

    private void inicar(){
        cliente = getIntent().getExtras().getParcelable("meC");
        RecyclerView recyclerView = findViewById(R.id.rcViews);
        txtInfo = findViewById(R.id.txtInfo);
        adapter = new GroupAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MetodosUsers metodosUsers = new MetodosUsers();
        metodosUsers.listarPostagens(adapter, cliente, txtInfo);
    }

    public void voltarPostagem(View view){
        finish();
    }
}