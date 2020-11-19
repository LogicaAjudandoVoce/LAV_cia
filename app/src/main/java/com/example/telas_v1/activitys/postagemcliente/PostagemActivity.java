package com.example.telas_v1.activitys.postagemcliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.fragmentos.fragmentosmenu.*;
import com.example.telas_v1.activitys.mensagens.ChatActivity;
import com.example.telas_v1.models.Postagem;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PostagemActivity extends AppCompatActivity {

    private Postagem postagem;
    private String forma;
    private UserCliente cliente = new UserCliente();
    private UserTrabalhador trabalhador = new UserTrabalhador();
    private List<String> voluntarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);

        iniciar();

    }

    private void iniciar(){
        postagem = getIntent().getExtras().getParcelable("post");
        forma = getIntent().getExtras().getString("forma");
        trabalhador = getIntent().getExtras().getParcelable("meT");
        verificarUserPost();
        TextView txtTitulo = findViewById(R.id.txtPostTitle);
        TextView txtNome = findViewById(R.id.txtNomeAutor);
        TextView txtEmail = findViewById(R.id.txtEmailAutor);
        TextView txtDescricao = findViewById(R.id.txtDescricaoPostagem);
        TextView txtPreco = findViewById(R.id.txtPrecoPostagem);
        TextView txtData = findViewById(R.id.txtDataPostagem);
        Button btnChat = findViewById(R.id.btnChat);
        Button btnBack = findViewById(R.id.btnVoltar);
        Button btnContrato = findViewById(R.id.btnContrato);

        if (postagem.getVoluntarios()!=null){
            for (String id: postagem.getVoluntarios()){
                if (id.equals(trabalhador.getId())){
                    btnContrato.setVisibility(View.INVISIBLE);
                }
            }
        }

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
                Intent intent = new Intent(PostagemActivity.this, ChatActivity.class);
                intent.putExtra("forma", "post");
                intent.putExtra("toCliente", cliente);
                intent.putExtra("meTrabalhador", trabalhador);
                startActivity(intent);
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

    public void solicitarContrato(View view){
        if (postagem.getVoluntarios()==null) voluntarios = new ArrayList<>();
        else voluntarios = postagem.getVoluntarios();
        voluntarios.add(trabalhador.getId());
        postagem.setVoluntarios(voluntarios);
        FirebaseFirestore.getInstance().collection("postagens").document(postagem.getIdPost()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostagemActivity.this, "Contrato Solicitado! Aguarde até ser respondido na área de contratos..", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TESTE", "Solicitar Voluntario: "+e.getMessage(), e);
            }
        });
    }
}