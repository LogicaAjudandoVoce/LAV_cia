package com.example.telas_v1.users.myperfil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.users.FotoLista;
import com.example.telas_v1.users.users.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MyPerfilTrabalhadorActivity extends AppCompatActivity {

    private UserTrabalhador userT;
    private ImageView imgFundo, imgFoto;
    private FloatingActionButton btnList;
    private TextInputEditText txtSobreMim, txtContatos;
    private TextView txt1, txt2;
    private Spinner prof, prof1, prof2;
    private Uri uri;
    private GroupAdapter adapter;
    private boolean aux=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perfil_trabalhador);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        userT = getIntent().getExtras().getParcelable("meT");
        imgFundo = findViewById(R.id.imgPerfil);
        imgFoto = findViewById(R.id.imgFoto);
        btnList = findViewById(R.id.btnList);
        txt1 = findViewById(R.id.txtCampo2);
        txt2 = findViewById(R.id.textView47);

        imgFoto.setEnabled(false);
        imgFundo.setEnabled(false);
        btnList.setEnabled(false);

        adapter = new GroupAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        iniciarComponentes();
        listarFotos();
    }

    private void iniciarComponentes(){
        TextView txtNome = findViewById(R.id.txtNome);
        txtSobreMim = findViewById(R.id.txtSobreMim);
        txtContatos = findViewById(R.id.txtContatos);
        prof = findViewById(R.id.profUm);
        prof1 = findViewById(R.id.profDois);
        prof2 = findViewById(R.id.profTres);

        txtNome.setText(userT.getNome());
        txtSobreMim.setText(userT.getSobreMim());
        txtContatos.setText(userT.getContatos());
        Picasso.get().load(userT.getUrlFotoFundo()).into(imgFundo);
        Picasso.get().load(userT.getUrlFotoPerfil()).into(imgFoto);

        List<String> profs = Arrays.asList(getResources().getStringArray(R.array.profs));
        prof.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, profs));
        prof1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, profs));
        prof2.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, profs));

        prof.setEnabled(false);
        prof1.setEnabled(false);
        prof2.setEnabled(false);
    }

    public void listarFotos(){
        FirebaseFirestore.getInstance()
                .collection("listaImagens")
                .whereEqualTo("link", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE","Listar Fotos My Perfil Trab: "+e.getMessage(), e);
                        }
                        else {
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                adapter.add(new FotoListaView(doc.get("id").toString()));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void editUser(View view){
       final FloatingActionButton btnEdit = findViewById(R.id.btnEdit);
        if (!aux) {
            btnEdit.setImageResource(R.drawable.ic_baseline_done_24);
            imgFoto.setEnabled(true);
            imgFundo.setEnabled(true);
            txtSobreMim.setEnabled(true);
            prof.setEnabled(true);
            prof1.setEnabled(true);
            prof2.setEnabled(true);
            txtContatos.setEnabled(true);
            btnList.setImageResource(R.drawable.ic_floating);
            btnList.setEnabled(true);
            aux=!aux;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("O que deseja?")
                    .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String sobre = txtSobreMim.getText().toString().trim();
                                    String contatos = txtContatos.getText().toString().trim();

                                    if (validar()) {
                                        userT.setSobreMim(sobre);
                                        userT.setContatos(contatos);
                                        userT.setProfUm(prof.getSelectedItem().toString());
                                        userT.setProfDois(prof1.getSelectedItem().toString());
                                        userT.setProfTres(prof2.getSelectedItem().toString());
                                        FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("TESTE", "Atualizar My Trab: "+e.getMessage(), e);
                                            }
                                        });
                                        btnEdit.setImageResource(R.drawable.ic_editar);
                                        imgFoto.setEnabled(false);
                                        imgFundo.setEnabled(false);
                                        txtSobreMim.setEnabled(false);
                                        txtContatos.setEnabled(false);
                                        prof.setEnabled(false);
                                        prof1.setEnabled(false);
                                        prof2.setEnabled(false);
                                        btnList.setImageResource(R.drawable.ic_expand_more);
                                        btnList.setEnabled(false);
                                        aux=!aux;
                                        txt1.setVisibility(View.INVISIBLE);
                                        txt2.setVisibility(View.INVISIBLE);
                                    } else {
                                        txt1.setVisibility(View.VISIBLE);
                                        txt2.setVisibility(View.VISIBLE);
                                    }
                                }
                    }).setNegativeButton("Reverter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    iniciarComponentes();
                    btnEdit.setImageResource(R.drawable.ic_editar);
                    imgFoto.setEnabled(false);
                    imgFundo.setEnabled(false);
                    txtSobreMim.setEnabled(false);
                    txtContatos.setEnabled(false);
                    prof.setEnabled(false);
                    prof1.setEnabled(false);
                    prof2.setEnabled(false);
                    btnList.setImageResource(R.drawable.ic_expand_more);
                    btnList.setEnabled(false);
                    aux=!aux;
                }
            }).setNeutralButton("Cancelar", null)
                    .create().show();
        }
    }

    public void selecionarFoto(final View view){
        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (view.getId()==R.id.btnList){
            startActivityForResult(intent, 2);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("O que deseja fazer?")
                .setPositiveButton("Alterar Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (view.getId()==R.id.imgPerfil){
                            startActivityForResult(intent, 0);
                        }else{
                            Log.d("TESTE", "Perfil 1");
                            startActivityForResult(intent, 1);
                        }
                    }
                })
                .setNegativeButton("Excluir Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (view.getId()==R.id.imgPerfil){
                            userT.setUrlFotoFundo("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios?alt=media&token=ee9d410b-3c49-4b8a-b03f-2670f2bfb105");
                            FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TESTE", "Excluir Foto Perfil Trab: "+e.getMessage(), e);
                                }
                            });
                            Picasso.get().load(userT.getUrlFotoFundo()).into(imgFundo);
                        }else{
                            userT.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F2e3534fb-21e2-429c-8f1c-ab8f0f5165ee?alt=media&token=0e3b8518-22ab-4ff2-bc13-367059352e92");
                            FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TESTE", "Excluir Foto Fundo Trab: "+e.getMessage(), e);
                                }
                            });
                            Picasso.get().load(userT.getUrlFotoPerfil()).into(imgFoto);
                        }
                        Toast.makeText(getApplicationContext(), "Exlcuindo...", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Cancelar", null)
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)uri = data.getData();
        if (requestCode==0 && resultCode==RESULT_OK){
            salvarFotoFundo();
        }else if (requestCode==1 && resultCode==RESULT_OK){
            Log.d("TESTE", "Perfil");
            salvarFotoPerfil();
        }else if (requestCode==2 && resultCode==RESULT_OK){
            salvarFotoLista();
        }
    }

    private void salvarFotoPerfil() {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/perfilImages/"+FirebaseAuth.getInstance().getUid());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TESTE", "Entrou tambem");
                        userT.setUrlFotoPerfil(uri.toString());
                        FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Salvar Foto Perfil Trab: "+e.getMessage(), e);
                            }
                        });
                        Picasso.get().load(userT.getUrlFotoPerfil()).into(imgFoto);
                    }
                });
            }
        });
        Toast.makeText(this, "Alterando Foto...", Toast.LENGTH_LONG).show();
    }

    private void salvarFotoFundo() {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/fundoImages/"+FirebaseAuth.getInstance().getUid());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        userT.setUrlFotoFundo(uri.toString());
                        FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Salvar Foto Fundo Trab: "+e.getMessage(), e);
                            }
                        });
                        Picasso.get().load(userT.getUrlFotoFundo()).into(imgFundo);
                    }
                });
            }
        });
        Toast.makeText(this, "Alterando Foto...", Toast.LENGTH_LONG).show();
    }

    private void salvarFotoLista() {
        final String id = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/listaImages/"+FirebaseAuth.getInstance().getUid()+"/"+id);
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FotoLista fotoLista = new FotoLista();
                        fotoLista.setLink(FirebaseAuth.getInstance().getUid());
                        fotoLista.setId(uri.toString());
                        FirebaseFirestore.getInstance().collection("listaImagens").document(id).set(fotoLista).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Salvar Foto List Trab: "+e.getMessage(), e);
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Espere um momento...", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private class FotoListaView extends Item<ViewHolder> {

        private final String fotoLista;

        private FotoListaView(String fotoLista) {
            this.fotoLista = fotoLista;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView img = viewHolder.itemView.findViewById(R.id.imgList);
            TextView txtAdd = findViewById(R.id.txtInfo);

            if (fotoLista!=null){
                Log.d("TESTE", fotoLista);
                Picasso.get().load(fotoLista).resize(400, 300).into(img);
                txtAdd.setText("");
            }
            else txtAdd.setText("Adicione suas fotos aqui");
        }

        @Override
        public int getLayout() {
            return R.layout.item_foto_list;
        }
    }

    private boolean validar(){
        String sobre = txtSobreMim.getText().toString().trim();
        String contatos = txtContatos.getText().toString().trim();

        if (!contatos.isEmpty() && !sobre.isEmpty() && prof.getSelectedItemPosition()!=0 || prof1.getSelectedItemPosition()!=0 || prof2.getSelectedItemPosition()!=0 ) {
            return true;
        }
        return false;
    }
}