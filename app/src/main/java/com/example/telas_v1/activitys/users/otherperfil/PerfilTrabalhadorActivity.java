package com.example.telas_v1.activitys.users.otherperfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.mensagens.*;
import com.example.telas_v1.activitys.users.ListFotosActivity;
import com.example.telas_v1.activitys.users.myperfil.MyPerfilClienteActivity;
import com.example.telas_v1.models.MetodosUsers;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PerfilTrabalhadorActivity extends AppCompatActivity {

    private UserCliente meC;
    private UserTrabalhador toT;
    private GroupAdapter adapter;
    private TextView txtInfo;
    private List<String> urls;
    private Button btnAvaliar;
    private ImageView btnVoltar;
    private FloatingActionButton btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pefil_trabalhador);

        iniciarComponetes();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        listarFotos();


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilTrabalhadorActivity.this, ChatActivity.class);
                intent.putExtra("meCliente", meC);
                intent.putExtra("toTrabalhador", toT);
                startActivity(intent);
            }
        });

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetodosUsers metodosUsers = new MetodosUsers();
                metodosUsers.avaliarUser(PerfilTrabalhadorActivity.this, null, toT);
            }
        });

    }


    public void iniciarComponetes(){
        meC = getIntent().getExtras().getParcelable("meC");
        toT = getIntent().getExtras().getParcelable("toT");
        final String forma = getIntent().getExtras().getString("forma");
        urls = new ArrayList<String>();

        btnVoltar = findViewById(R.id.btnVoltar);
        TextView txtAvaliar = findViewById(R.id.txtAvaliar);
        ImageView imgFundo = findViewById(R.id.imgBackPerfil);
        ImageView imgPerfil = findViewById(R.id.imgPerfil);
        TextView txtNome = findViewById(R.id.txtNome);
        TextView txtProfissao = findViewById(R.id.txtProfissao);
        TextView txtSobreMim = findViewById(R.id.txtSobreMim);
        TextView txtContato = findViewById(R.id.txtContato);
        TextView trab1 = findViewById(R.id.trab1);
        TextView trab2 = findViewById(R.id.trab2);
        TextView trab3 = findViewById(R.id.trab3);
        txtInfo = findViewById(R.id.txtInfo);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        FloatingActionButton btnHistorico = findViewById(R.id.btnHistorico);
        btnChat = findViewById(R.id.btnChat);
        RecyclerView tcView = findViewById(R.id.rcView);
        btnAvaliar = findViewById(R.id.btnAvaliar);

        trab1.setText(toT.getProfUm());
        trab2.setText(toT.getProfDois());
        trab3.setText(toT.getProfTres());
        if (toT.getStars()!=0)
            txtAvaliar.setText(String.valueOf(toT.getStars()/(float) toT.getCountStars()).substring(0, 3));

        ratingBar.setRating(toT.getStars()/toT.getCountStars());
        ratingBar.setFocusable(false);

        adapter = new GroupAdapter();
        tcView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.HORIZONTAL, false);
        tcView.setLayoutManager(gridLayoutManager);

        Picasso.get().load(toT.getUrlFotoFundo()).fit().into(imgFundo);
        Picasso.get().load(toT.getUrlFotoPerfil()).into(imgPerfil);
        txtNome.setText(toT.getNome());
        txtSobreMim.setText(toT.getSobreMim());
        txtContato.setText(toT.getContatos());

        if (forma.equals("chatJa")){
            btnChat.setVisibility(View.INVISIBLE);
            btnChat.setEnabled(false);
        }

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
            urls.add(fotoLista);
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

    public void abrirFotos(View view){
        if (!urls.isEmpty()) {
            Intent intent = new Intent(this, ListFotosActivity.class);
            intent.putExtra("toT", toT);
            intent.putExtra("urls", (Serializable) urls);
            intent.putExtra("me", "sim");
            startActivity(intent);
        }else
            Toast.makeText(this, "Não existem fotos para visualizar...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarComponetes();
    }
}