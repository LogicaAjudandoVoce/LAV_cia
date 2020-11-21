package com.example.telas_v1.activitys.postagemcliente;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.fragmentos.fragmentosmenu.*;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.Postagem;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hootsuite.nachos.NachoTextView;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.hootsuite.nachos.terminator.ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR;

public class NovaPostagemClienteActivity extends AppCompatActivity {

    private TextInputEditText txtTitulo, txtDescricao, txtDescricaoRapida;
    private Spinner spCateghoria;
    private TextView txtRc;
    private EditText txtPreco;
    private RecyclerView rcView;
    private GroupAdapter adapter;
    private Button btnPost;
    private ImageView imgMapa;
    private UUID uid = UUID.randomUUID();
    private List<String> uriFoto = new ArrayList<>();
    private List<Uri>uriAx = new ArrayList<>();
    private Postagem postagem = new Postagem();
    private MetodosUsers metodosUsers= new MetodosUsers();
    private UserCliente cliente;
    private double preco;
    private NachoTextView txtPalavrasChaves;

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

        iniciarComponentes();
    }

    private void iniciarComponentes(){
        postagem = getIntent().getParcelableExtra("loc");

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDescricaoRapida = findViewById(R.id.txtDescricaoRapida);
        txtRc = findViewById(R.id.txtRc);
        txtPreco = findViewById(R.id.trab1);
        imgMapa = findViewById(R.id.imgMapa);
        rcView = findViewById(R.id.rcViews);
        btnPost = findViewById(R.id.btnPostar);
        txtPalavrasChaves = findViewById(R.id.txtPalavrasChaves);
        spCateghoria = findViewById(R.id.spCategoria);

        List<String> filtros = Arrays.asList(getResources().getStringArray(R.array.filtros));
        spCateghoria.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, filtros));

        txtPalavrasChaves.addChipTerminator(' ', BEHAVIOR_CHIPIFY_TO_TERMINATOR);

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

        if (requestCode==0 && resultCode==RESULT_OK){
            Uri uri = data.getData();
            uriAx.add(uri);
            adapter.add(new PostViewHolder(uri.toString()));
            adapter.notifyDataSetChanged();
            txtRc.setText("");
        }
    }

    public void postar(View view){
        String titulo= txtTitulo.getText().toString();
        String mini = txtDescricaoRapida.getText().toString();
        String descricao= txtDescricao.getText().toString();
        if (txtPreco.getText().toString().isEmpty()) preco=0f;
         else preco = Double.valueOf(txtPreco.getText().toString());

        if (!titulo.isEmpty() && !descricao.isEmpty() && preco>0 && uriFoto!=null && mini!=null){
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String dataFormatada = formataData.format(data);
            List<String> keys = new ArrayList<>();

            postagem.setIdCliente(FirebaseAuth.getInstance().getUid());
            postagem.setIdPost(uid.toString() );
            postagem.setNomeAutor(cliente.getNome());
            postagem.setEmail(cliente.getEmail());
            postagem.setTitulo(titulo);
            for (String chip: txtPalavrasChaves.getChipValues()){
                keys.add(chip);
            }
            postagem.setKeys(keys);
            postagem.setMiniDescricao(mini);
            postagem.setDescricao(descricao);
            postagem.setPreco(preco);
            postagem.setStatus("Pendente");
            postagem.setFiltroFixo(spCateghoria.getSelectedItem().toString());
            postagem.setData(dataFormatada);

            int cont=0;
            for (Uri uuri: uriAx){
                cont++;
                int totalFotos= uriAx.size();
                uploadarFotos(uuri, totalFotos, cont);
            }
        }else{
            Toast.makeText(this, "Preecha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadarFotos(Uri uuri, final int totalFotos, int cont){
        String nmr = "imagem"+cont;
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/postagens/"+postagem.getIdPost()+"/"+nmr);
        ref.putFile(uuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri){
                        uriFoto.add(uri.toString());
                        if(totalFotos == uriFoto.size()){
                            postagem.setFotos(uriFoto);
                            FirebaseFirestore.getInstance().collection("postagens").document(postagem.getIdPost()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(NovaPostagemClienteActivity.this, MenuActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TESTE", "Enviar Postagem: "+e.getMessage(), e);
                                }
                            });
                        }
                    }
                });
            }
        });
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