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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NovaPostagemClienteActivity extends AppCompatActivity {

    private TextInputEditText txtTitulo, txtDescricao;
    private TextView txtRc;
    private EditText txtPreco;
    private RecyclerView rcView;
    private GroupAdapter adapter;
    private Button btnPost;
    private ImageView imgMapa;
    private List<Uri> uriFoto = new ArrayList<>();
    private Postagem postagem = new Postagem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem_cliente);

        postagem = getIntent().getParcelableExtra("loc");

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
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
            uriFoto.add(data.getData());
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
        String descricao= txtDescricao.getText().toString();
        double preco = Double.valueOf(txtPreco.getText().toString());

        if (!titulo.isEmpty() && !descricao.isEmpty() && preco>0 && uriFoto!=null){
            UUID uid = UUID.randomUUID();

            postagem.setIdCliente(FirebaseAuth.getInstance().getUid());
            postagem.setIdPost(uid.toString() );
            postagem.setTitulo(titulo);
            postagem.setDescricao(descricao);
            postagem.setPreco(preco);
            postagem.setUri(null);

            FirebaseFirestore.getInstance().collection("postagems").document(uid.toString()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(NovaPostagemClienteActivity.this, "Postagem concluída!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }else{
            Toast.makeText(this, "Preecha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    public void abrirMapa(View view){
        startActivity(new Intent(this, MapNewPostActivity.class));
    }

    private class PostViewHolder extends Item<ViewHolder>{
        private final Uri uri;

        public PostViewHolder(Uri uri) {
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