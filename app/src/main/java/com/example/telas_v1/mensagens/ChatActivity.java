package com.example.telas_v1.mensagens;

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
import com.example.telas_v1.users.PerfilTrabalhadorActivity;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

public class ChatActivity extends AppCompatActivity {

    private TextInputEditText txtMsn;
    private TextView txtNome;
    private ImageView imgPerfil;
    private ImageButton btnEnviar;
    private MetodosMensagens mensagens = new MetodosMensagens();
    private UserCliente meC, userC;
    private UserTrabalhador meT, userT;
    private GroupAdapter adapter;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView recyclerView = findViewById(R.id.rcView);
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
    }

    public void abrirPerfilTrabalhador(View view){
        if(meC!=null) {
            Intent intent = new Intent(ChatActivity.this, PerfilTrabalhadorActivity.class);
            intent.putExtra("toT", userT);
            intent.putExtra("meC", meC);
            intent.putExtra("forma", "chatJa");
            startActivity(intent);
        }
    }
}