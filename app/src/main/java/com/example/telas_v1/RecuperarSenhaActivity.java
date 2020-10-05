package com.example.telas_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.telas_v1.classesuteis.BarraProgresso;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private TextInputEditText txtEmail;
    private Button btnAvancar;
    private BarraProgresso barraProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        txtEmail = findViewById(R.id.txtEmailList);
        btnAvancar = findViewById(R.id.btnAvancar);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                if (!email.isEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RecuperarSenhaActivity.this, "Acesse seu email e altere sua senha.", Toast.LENGTH_LONG).show();
                                barraProgresso = new BarraProgresso(RecuperarSenhaActivity.this);
                                barraProgresso.comecarProgresso();
                                finish();
                            }
                            else {
                                Toast.makeText(RecuperarSenhaActivity.this, "Não foi possível localizar seu email!", Toast.LENGTH_LONG).show();
                                barraProgresso.cancelarDialog();
                            }
                        }
                    });
                }else Toast.makeText(RecuperarSenhaActivity.this, "Informe seu email!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
