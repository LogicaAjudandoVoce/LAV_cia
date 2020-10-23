package com.example.telas_v1.startactivitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.telas_v1.fragmentoscriarconta.CreateActivity;
import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.users.FotoLista;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Uri uriFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getUid()!=null){
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        }
    }

    public void entrar(View view) {
        Intent entrar = new Intent(this, LoginActiviy.class);
        startActivity(entrar);
    }

    public void criar(View view) {
        Intent criar = new Intent(this, CreateActivity.class);
        startActivity(criar);
    }
}
