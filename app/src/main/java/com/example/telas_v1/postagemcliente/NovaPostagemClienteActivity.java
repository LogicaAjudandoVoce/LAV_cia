package com.example.telas_v1.postagemcliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.fragmentosmenu.buscar.MenuBuscar;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NovaPostagemClienteActivity extends AppCompatActivity {

    private TextInputEditText txtTitulo, txtDescricao, txtDescricaoRapida;
    private TextView txtRc;
    private EditText txtPreco;
    private RecyclerView rcView;
    private GroupAdapter adapter;
    private Button btnPost;
    private ImageView imgMapa;
    private UUID uid;
    private List<String> uriFoto = new ArrayList<>();
    private List<Uri> uriAx = new ArrayList<>();
    private Postagem postagem = new Postagem();
    private MetodosUsers metodosUsers= new MetodosUsers();
    private UserCliente cliente;
    private double preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem_cliente);

        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {

            }
        });

        postagem = getIntent().getParcelableExtra("loc");

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDescricaoRapida = findViewById(R.id.txtDescricaoRapida);
        txtRc = findViewById(R.id.txtRc);
        txtPreco = findViewById(R.id.txtPreco);
        imgMapa = findViewById(R.id.imgMapa);
        rcView = findViewById(R.id.rcView);
        btnPost = findViewById(R.id.btnPostar);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postar();
            }
        });

        adapter = new GroupAdapter();
        rcView.setAdapter(adapter);
        rcView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcView.setLayoutManager(layoutManager);
    }

    public void voltar(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(NovaPostagemClienteActivity.this);
        builder.setTitle("Tem certeza?")
                .setMessage("Você tem certeza que deseja voltar? Perderá qualquer possível alteração!")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("Cancelar", null).create().show();
    }

    public void selecionarFoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0 && resultCode==NovaPostagemClienteActivity.RESULT_OK){
            uriFoto.add(data.getData().toString());
            uriAx.add(data.getData());
            Log.d("TESTE", String.valueOf(uriFoto.size()));
            adapter.clear();
            for (int i=0;i<uriFoto.size();i++){
                adapter.add(new PostViewHolder(uriFoto.get(i)));
            }
            adapter.notifyDataSetChanged();
            txtRc.setText("");
        }
    }

    public void postar(){
        String titulo= txtTitulo.getText().toString();
        String mini = txtDescricaoRapida.getText().toString();
        String descricao= txtDescricao.getText().toString();
        if (txtPreco.getText().toString().isEmpty()) preco=0f;
         else preco = Double.valueOf(txtPreco.getText().toString());

        if (!titulo.isEmpty() && !descricao.isEmpty() && preco>0 && uriFoto!=null && mini!=null){
            uid = UUID.randomUUID();
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String dataFormatada = formataData.format(data);

            postagem.setIdCliente(FirebaseAuth.getInstance().getUid());
            postagem.setIdPost(uid.toString() );
            postagem.setNomeAutor(cliente.getNome());
            postagem.setEmail(cliente.getEmail());
            postagem.setTitulo(titulo);
            postagem.setMiniDescricao(mini);
            postagem.setDescricao(descricao);
            postagem.setPreco(preco);
            postagem.setConclusao(false);
            postagem.setData(dataFormatada);
            uriFoto.clear();
            uploadarFotos(postagem);

            FirebaseFirestore.getInstance().collection("postagens").document(postagem.getIdPost()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = new Intent(NovaPostagemClienteActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "Preecha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadarFotos(Postagem postagem){
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/postagens/"+FirebaseAuth.getInstance().getUid()+"/"+uid.toString());
        for (int i=0;i<uriFoto.size();i++) {
            ref.putFile(uriAx.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uriFoto.add(uri.toString());
                        }
                    });
                }
            });
        }
    }

    public void abrirMapa(View view){
        startActivity(new Intent(this, MapNewPostActivity.class));
    }

    private class PostViewHolder extends Item<ViewHolder>{
        private final String uri;

        public PostViewHolder(String uri) {
            this.uri = uri;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView imageView = viewHolder.itemView.findViewById(R.id.imgPostNew);
            Picasso.get().load(uri).resize(400, 300).into(imageView);
        }

        @Override
        public int getLayout() {
            return R.layout.item_post_img;
        }
    }

}