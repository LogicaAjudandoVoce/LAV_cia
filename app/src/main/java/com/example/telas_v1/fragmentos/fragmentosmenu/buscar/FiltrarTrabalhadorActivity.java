package com.example.telas_v1.fragmentos.fragmentosmenu.buscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telas_v1.R;
import com.example.telas_v1.activitys.startactivitys.*;

public class FiltrarTrabalhadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_trabalhador);
    }

    public void voltar(View view){
        finish();
    }

    public void filtrarAnimais(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Animais");
        startActivity(intent);
        finish();
    }

    public void filtrarCabelos(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Cabelos");
        startActivity(intent);
        finish();
    }

    public void filtrarComidas(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Comidas");
        startActivity(intent);
        finish();
    }

    public void filtrarDetetizacao(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Dedetizacao");
        startActivity(intent);
        finish();
    }

    public void filtrarEletricista(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Eletricista");
        startActivity(intent);
        finish();
    }

    public void filtrarEncanador(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Encanador");
        startActivity(intent);
        finish();
    }

    public void filtrarEstudos(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Estudos");
        startActivity(intent);
        finish();
    }

    public void filtrarFaxineiro(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Faxineiro");
        startActivity(intent);
        finish();
    }

    public void filtrarJardineiro(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Jardineiro");
        startActivity(intent);
        finish();
    }

    public void filtrarManicure(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Manicure");
        startActivity(intent);
        finish();
    }

    public void filtrarMaquiagem(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Maquiagem");
        startActivity(intent);
        finish();
    }

    public void filtrarPedreiro(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Pedreiro");
        startActivity(intent);
        finish();
    }

    public void filtrarPintor(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Pintor");
        startActivity(intent);
        finish();
    }

    public void filtrarModa(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Modas");
        startActivity(intent);
        finish();
    }

    public void filtrarVenda(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Vendas");
        startActivity(intent);
        finish();
    }

    public void filtrarZelador(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filtragemTrab", "Zelador");
        startActivity(intent);
        finish();
    }


}