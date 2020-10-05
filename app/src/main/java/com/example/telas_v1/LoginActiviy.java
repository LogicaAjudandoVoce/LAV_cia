package com.example.telas_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_v1.metodosusers.*;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActiviy extends AppCompatActivity {

    //tudo ok,dia: 40/09/2020 ás 00:24

    private TextInputEditText txtEmail, txtPassword;
    private Button btnEntrar;
    private MetodosUsers metodosUsers;
    private TextView txtEsqueci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        metodosUsers = new MetodosUsers();
        txtEmail = findViewById(R.id.txtEmailList);
        txtPassword = findViewById(R.id.txtPassword);
        btnEntrar = findViewById(R.id.bt_entrar);
        txtEsqueci = findViewById(R.id.txtEsquecer);

        if (FirebaseAuth.getInstance().getUid()!=null) FirebaseAuth.getInstance().signOut();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodosUsers.autenticarUsuario(getApplicationContext(), txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });

        txtEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActiviy.this, RecuperarSenhaActivity.class));
            }
        });
    }

    public void voltar(View view) {
        Intent voltar = new Intent(this, MainActivity.class);
        voltar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(voltar);
    }


    public void close(View view) {
        ((InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }
}
