package com.example.telas_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.mensagens.ChatActivity;
import com.example.telas_v1.postagemcliente.Postagem;
import com.example.telas_v1.users.UserCliente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostagemActivity extends AppCompatActivity {

    private Postagem postagem;
    private TextView txtTitulo, txtDescricao, txtPreco, txtData, txtNome, txtEmail;
    private Button btnContrato, btnChat, btnBack;
    private String forma;
    private UserCliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);
        postagem = getIntent().getExtras().getParcelable("post");
        forma = getIntent().getExtras().getString("forma");
        verificarUserPost();

        txtTitulo = findViewById(R.id.txtPostTitle);
        txtNome = findViewById(R.id.txtNomeAutor);
        txtEmail = findViewById(R.id.txtEmailAutor);
        txtDescricao = findViewById(R.id.txtDescricaoPostagem);
        txtPreco = findViewById(R.id.txtPrecoPostagem);
        txtData = findViewById(R.id.txtDataPostagem);
        btnChat = findViewById(R.id.btnChat);
        btnContrato = findViewById(R.id.btnContrato);
        btnBack = findViewById(R.id.btnVoltar);

        txtTitulo.setText(postagem.getTitulo());
        txtNome.setText(postagem.getNomeAutor());
        txtEmail.setText(postagem.getEmail());
        txtDescricao.setText(postagem.getDescricao());
        txtPreco.setText(String.valueOf(postagem.getPreco()));
        txtData.setText(postagem.getData());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forma.equals("menu")){
                    startActivity(new Intent(PostagemActivity.this, MenuActivity.class));
                    finish();
                }
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PostagemActivity.this, ChatActivity.class);
                intent.putExtra("forma", "post");
                intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        });

        btnContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void verificarUserPost(){
        FirebaseFirestore.getInstance().collection("userCliente").document(postagem.getIdCliente()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cliente = documentSnapshot.toObject(UserCliente.class);
            }
        });
    }
}