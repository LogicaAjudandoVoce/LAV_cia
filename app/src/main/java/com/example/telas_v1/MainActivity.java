package com.example.telas_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telas_v1.fragmentosmenu.perfil.MenuPerfil;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

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
