package com.example.telas_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.mensagens.ChatActivity;
import com.example.telas_v1.postagemcliente.Postagem;
import com.example.telas_v1.users.users.UserCliente;
import com.example.telas_v1.users.users.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostagemActivity extends AppCompatActivity {

    private Postagem postagem;
    private String forma;
    private UserCliente cliente = new UserCliente();
    private UserTrabalhador trabalhador = new UserTrabalhador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);
        postagem = getIntent().getExtras().getParcelable("post");
        forma = getIntent().getExtras().getString("forma");
        verificarUserPost();

        TextView txtTitulo = findViewById(R.id.txtPostTitle);
        TextView txtNome = findViewById(R.id.txtNomeAutor);
        TextView txtEmail = findViewById(R.id.txtEmailAutor);
        TextView txtDescricao = findViewById(R.id.txtDescricaoPostagem);
        TextView txtPreco = findViewById(R.id.txtPrecoPostagem);
        TextView txtData = findViewById(R.id.txtDataPostagem);
        Button btnChat = findViewById(R.id.btnChat);
        Button btnBack = findViewById(R.id.btnVoltar);

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
                FirebaseFirestore.getInstance().collection("userTrabalhador").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        trabalhador = documentSnapshot.toObject(UserTrabalhador.class);
                        Intent intent =  new Intent(PostagemActivity.this, ChatActivity.class);
                        intent.putExtra("forma", "post");
                        intent.putExtra("toCliente", cliente);
                        intent.putExtra("meTrabalhador", trabalhador);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TESTE", "Verificar Meu Trabalhador do Post: "+e.getMessage(), e);
                    }
                });
            }
        });

    }

    private void verificarUserPost(){
        FirebaseFirestore.getInstance().collection("userCliente").document(postagem.getIdCliente()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cliente = documentSnapshot.toObject(UserCliente.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TESTE", "Verificar Cliente do Post: "+e.getMessage(), e);
            }
        });
    }
}