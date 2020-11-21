package com.example.telas_v1.fragmentos.fragmentosmenu.contratar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.users.otherperfil.PerfilTrabalhadorActivity;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.Postagem;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class TrabalhadoresContratosActivity extends AppCompatActivity {

    private Postagem post;
    private MetodosUsers users;
    private UserCliente cliente;
    private GroupAdapter adapter;
    private UserTrabalhador trabalhador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabalhadores_contratos);

        inicar();
        clickAdapter();
    }

    private void inicar(){
        adapter = new GroupAdapter();
        post = getIntent().getExtras().getParcelable("post");
        cliente = getIntent().getExtras().getParcelable("meC");
        trabalhador = getIntent().getExtras().getParcelable("meT");
        users = new MetodosUsers();

        RecyclerView rcView = findViewById(R.id.rcViews);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(adapter);

        listar();
    }

    private void listar(){
        if (cliente!=null){
            if (post.getVoluntarios()!=null){
                users.listarTrabalhador(adapter, post);
            }
        }
    }

    private void clickAdapter(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull final Item item, @NonNull View view) {
                if (cliente!=null){
                    final MetodosUsers.ListarTrabalhadorView model = (MetodosUsers.ListarTrabalhadorView) item;
                    AlertDialog.Builder builder = new AlertDialog.Builder(TrabalhadoresContratosActivity.this);
                    builder.setTitle("O que deseja fazer?")
                            .setPositiveButton("Ver Perfil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(TrabalhadoresContratosActivity.this, PerfilTrabalhadorActivity.class);
                                    intent.putExtra("toT", model.userTrabalhador);
                                    intent.putExtra("meC", cliente);
                                    intent.putExtra("forma", "menu");
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Contratar Trabalhador", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    post.setIdContratado(model.userTrabalhador.getId());
                                    post.setStatus("Ativo");
                                    post.setUrlImgContratado(model.userTrabalhador.getUrlFotoPerfil());
                                    post.setNomeContratato(model.userTrabalhador.getNome());
                                    FirebaseFirestore.getInstance().collection("postagens").document(post.getIdPost()).set(post)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(TrabalhadoresContratosActivity.this, "Trabalhador Contratado!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("TESTE", "Erro Contratar Trabalhador: "+e.getMessage(), e);
                                        }
                                    });
                                }
                            })
                            .setNeutralButton("Cancelar", null).create().show();
                }
            }
        });
    }
}