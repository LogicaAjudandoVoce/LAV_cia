package com.example.telas_v1.fragmentoscriarconta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.telas_v1.R;
import com.example.telas_v1.fragmentoscriarconta.passoum.StepOne;

public class CreateActivity extends AppCompatActivity {

    //tudo ok,dia: 40/09/2020 ás 00:24
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.paipai, new StepOne());
        ft.commit();
    }

    public void voltar(View view) {
        finish();
    }

}
