package com.example.telas_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import com.example.telas_v1.metodosusers.*;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActiviy extends AppCompatActivity {

    //tudo ok,dia: 40/09/2020 ás 00:24

    private TextInputEditText txtEmail, txtPassword;
    private Button btnEntrar;
    private MetodosUsers metodosUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        metodosUsers = new MetodosUsers();
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnEntrar = findViewById(R.id.bt_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodosUsers.autenticarUsuario(getApplicationContext(), txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    public void voltar(View view) {
        finish();
    }


    public void close(View view) {
        ((InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }
}
