package com.example.telas_v1.activitys.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.users.myperfil.MyPerfilTrabalhadorActivity;
import com.example.telas_v1.models.AdapterList;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFotosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> urls;
    private AdapterList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fotos);
        iniciarComponentes();

        adapter = new AdapterList(getApplicationContext(), urls);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void iniciarComponentes(){
        urls = new ArrayList<>();
        urls = (List<String>) getIntent().getExtras().getSerializable("urls");
        recyclerView = findViewById(R.id.recyclerView);
}
}