package com.example.telas_v1.users;

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
import com.example.telas_v1.mensagens.ChatActivity;
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
        ImageView imgFundo = findViewById(R.id.imgFundo);
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

        if (toT!=null){
            if (!toT.getUrlFotoFundo().equals("Nada")) {
                Picasso.get().load(toT.getUrlFotoFundo()).fit().into(imgFundo);
            }
            if (!toT.getUrlFotoPerfil().equals("Nada")) Picasso.get().load(toT.getUrlFotoPerfil()).into(imgPerfil);
            txtNome.setText(toT.getNome());
            txtValor.setText(String.valueOf(toT.getMyPreco()));
            listarFotos();
        };

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
        FirebaseFirestore.getInstance().collection("listaImgs").whereEqualTo("id", toT.getId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        adapter.add(new FotoListaView(doc.get("foto").toString()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private class FotoListaView extends Item<ViewHolder>{

        private final String fotoLista;

        private FotoListaView(String fotoLista) {
            this.fotoLista = fotoLista;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView img = viewHolder.itemView.findViewById(R.id.imgList);

            if (fotoLista!=null){
                Picasso.get().load(fotoLista).resize(400, 300).into(img);
                txtInfo.setText("");
            }
            else txtInfo.setText("Adicione suas fotos aqui");
        }

        @Override
        public int getLayout() {
            return R.layout.item_foto_list;
        }
    }

}