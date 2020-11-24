package com.example.telas_v1.activitys.postagemcliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.users.otherperfil.PerfilTrabalhadorActivity;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.Postagem;
import com.example.telas_v1.models.PostagemAux;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class PostagemAtivaActivity extends AppCompatActivity {

    private UserCliente cliente;
    private UserTrabalhador trabalhador;
    private ImageView imgUser;
    private PostagemAux post;
    private String forma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem_ativa);
        iniciar();
    }

    private void iniciar(){
        cliente = getIntent().getExtras().getParcelable("meC");
        post = getIntent().getExtras().getParcelable("post");
        forma = getIntent().getExtras().getString("forma");
        if (forma!=null){
            Button btnEncerrar = findViewById(R.id.btnEncerrar);
            Button btnFInal = findViewById(R.id.btnConcluir);

            btnEncerrar.setVisibility(View.INVISIBLE);
            btnEncerrar.setEnabled(false);

            btnFInal.setVisibility(View.INVISIBLE);
            btnFInal.setEnabled(false);
        }
        TextView txtData= findViewById(R.id.textDataPost);
        TextView txtTitle= findViewById(R.id.txtTitlePost);
        TextView txtDesc= findViewById(R.id.txtDescPost);
        TextView txtNome= findViewById(R.id.txtNameUser);
        imgUser = findViewById(R.id.imgUser);

        txtData.setText(post.getData());
        txtTitle.setText(post.getTitulo());
        txtDesc.setText(post.getDescricao());
        txtNome.setText(post.getNomeContratato());
        Picasso.get().load(post.getUrlImgContratado()).into(imgUser);

        getTrabalhador();
    }

    private void getTrabalhador(){
        FirebaseFirestore.getInstance().collection("userTrabalhador").document(post.getIdContratado()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                trabalhador = documentSnapshot.toObject(UserTrabalhador.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TESTE", "Erro Abrir Perfil Post Ativo: "+e.getMessage(), e);
            }
        });
    }

    public void abrirPerfilTrab(View view){

        Intent intent = new Intent(PostagemAtivaActivity.this, PerfilTrabalhadorActivity.class);
        intent.putExtra("toT", trabalhador);
        intent.putExtra("meC", cliente);
        intent.putExtra("forma", "menu");
        startActivity(intent);
    }

    public void encerrarContrato(View view){
        int i=0;
        for (String vol: post.getVoluntarios()){
            if (vol.equals(trabalhador.getId())){
                post.getVoluntarios().remove(i);
            }
            i++;
        }
        post.setIdContratado(null);
        post.setNomeContratato(null);
        post.setUrlImgContratado(null);
        post.setStatus("Pendente");
        FirebaseFirestore.getInstance().collection("postagens").document(post.getIdPost()).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PostagemAtivaActivity.this, "Contrato Encerrado!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TESTE", "Erro Encerrar Contrato Cliente: " + e.getMessage(), e);
            }
        });
    }
    public void finalizarContrato(View view){
        post.setStatus("Finalizado");
        FirebaseFirestore.getInstance().collection("postagens").document(post.getIdPost()).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PostagemAtivaActivity.this, "Serviço Concluído!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TESTE", "Erro Concluir Serviço Cliente: " + e.getMessage(), e);
            }
        });
    }

    public void voltarContrato(View view){
        finish();
    }
}