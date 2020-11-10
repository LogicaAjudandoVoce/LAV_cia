package com.example.telas_v1.activitys.users.otherperfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.mensagens.ChatActivity;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class PerfilTrabalhadorActivity extends AppCompatActivity {

    private UserCliente meC;
    private UserTrabalhador toT;
    private GroupAdapter adapter;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pefil_trabalhador);

        meC = getIntent().getExtras().getParcelable("meC");
        toT = getIntent().getExtras().getParcelable("toT");
        final String forma = getIntent().getExtras().getString("forma");


        ImageView btnVoltar = findViewById(R.id.btnVoltar);
        ImageView imgFundo = findViewById(R.id.imgBackPerfil);
        ImageView imgPerfil = findViewById(R.id.imgPerfil);
        TextView txtNome = findViewById(R.id.txtNome);
        TextView txtProfissao = findViewById(R.id.txtProfissao);
        TextView txtSobreMim = findViewById(R.id.txtSobreMim);
        TextView txtValor = findViewById(R.id.txtPreco);
        TextView txtContato = findViewById(R.id.txtContato);
        txtInfo = findViewById(R.id.txtInfo);
        FloatingActionButton btnAvaliar = findViewById(R.id.btnAvaliar);
        FloatingActionButton btnChat = findViewById(R.id.btnChat);
        RecyclerView tcView = findViewById(R.id.rcView);

        adapter = new GroupAdapter();
        tcView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PerfilTrabalhadorActivity.this, LinearLayoutManager.HORIZONTAL, false);
        tcView.setLayoutManager(layoutManager);


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        Picasso.get().load(toT.getUrlFotoFundo()).fit().into(imgFundo);
        Picasso.get().load(toT.getUrlFotoPerfil()).into(imgPerfil);
        txtNome.setText(toT.getNome());
        txtValor.setText(String.valueOf(toT.getMyPreco()));
        txtSobreMim.setText(toT.getSobreMim());
        txtContato.setText(toT.getContatos());
        listarFotos();

        if (forma.equals("chatJa")){
            btnChat.setVisibility(View.INVISIBLE);
            btnChat.setEnabled(false);
        }
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilTrabalhadorActivity.this, ChatActivity.class);
                intent.putExtra("meCliente", meC);
                intent.putExtra("toTrabalhador", toT);
                startActivity(intent);
            }
        });
    }

    private void listarFotos(){
        FirebaseFirestore.getInstance()
                .collection("listaImagens")
                .whereEqualTo("link", toT.getId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE","Listar Foto Other Perfil Trab: "+e.getMessage(), e);
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

}