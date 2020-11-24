package com.example.telas_v1.activitys.mensagens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.postagemcliente.PostagemActivity;
import com.example.telas_v1.activitys.users.myperfil.MyPerfilClienteActivity;
import com.example.telas_v1.activitys.users.otherperfil.PerfilTrabalhadorActivity;
import com.example.telas_v1.models.MetodosMensagens;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class ChatActivity extends AppCompatActivity {

    private EditText txtMsn;
    private Button btnVoltar;
    private TextView txtNome;
    private ImageView imgPerfil;
    private GroupAdapter adapter;
    private ImageButton btnEnviar;
    private UserCliente meC, userC;
    private UserTrabalhador meT, userT;
    private MetodosMensagens mensagens = new MetodosMensagens();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final RecyclerView recyclerView = findViewById(R.id.rcViews);
        txtMsn = findViewById(R.id.txtMensagem);
        txtNome = findViewById(R.id.txtNome);
        imgPerfil = findViewById(R.id.imgPerfil);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVoltar = findViewById(R.id.btnVoltar);

        meT = getIntent().getExtras().getParcelable("meTrabalhador");
        userC = getIntent().getExtras().getParcelable("toCliente");

        if (userC!=null) {
            txtNome.setText(userC.getNome());
            Picasso.get().load(userC.getUrlFotoPerfil()).into(imgPerfil);
        }

        meC = getIntent().getExtras().getParcelable("meCliente");
        userT = getIntent().getExtras().getParcelable("toTrabalhador");

        if (userT!=null) {
            txtNome.setText(userT.getNome());
            Picasso.get().load(userT.getUrlFotoPerfil()).into(imgPerfil);
        }
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (meT != null){
            mensagens.carregarMensagensTrabalhador(meT, userC, adapter);
        }else if (meC!=null){
            mensagens.carregarMensagensCliente(meC, userT, adapter);
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meC!=null)mensagens.enviarMensagemToTrabalhador(txtMsn, meC, userT);
                else if (meT!=null) mensagens.enviarMensagemToCliente(txtMsn, meT, userC);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abrirPost();
    }

    public void abrirPerfilTrabalhador(View view){
        if(meC!=null) {
            Intent intent = new Intent(ChatActivity.this, PerfilTrabalhadorActivity.class);
            intent.putExtra("toT", userT);
            intent.putExtra("meC", meC);
            intent.putExtra("forma", "chatJa");
            startActivity(intent);
        }
        else if (meT!=null){
            Intent intent = new Intent(this, MyPerfilClienteActivity.class);
            intent.putExtra("meC", userC);
            intent.putExtra("tipo", "chat");
            startActivity(intent);
        }
    }

    public void enviarPostagen(View view){
        if (meC!=null) {
            Intent intent = new Intent(this, EnviarPostagemChatActivity.class);
            intent.putExtra("meC", meC);
            startActivity(intent);
        }
    }

    private void abrirPost(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                if (meC != null) {
                    MetodosMensagens.MessageItemCliente model = (MetodosMensagens.MessageItemCliente) item;
                    if (model.message.getPost() != null) {
                        Intent intent = new Intent(getApplicationContext(), PostagemActivity.class);
                        intent.putExtra("user", "cliente");
                        intent.putExtra("post", model.message.getPost());
                        startActivity(intent);
                    }
                } else if (meT != null) {
                    MetodosMensagens.MessageItemTrabalhador model = (MetodosMensagens.MessageItemTrabalhador) item;
                    if (model.message.getPost() != null) {
                        Intent intent = new Intent(getApplicationContext(), PostagemActivity.class);
                        intent.putExtra("meT", meT);
                        intent.putExtra("user", "trab");
                        intent.putExtra("post", model.message.getPost());
                        startActivity(intent);
                    }
                }
            }
        });
    }
}