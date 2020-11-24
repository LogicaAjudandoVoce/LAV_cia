package com.example.telas_v1.models;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.telas_v1.R;

public class BarraProgresso {

    private Activity activity;
    private AlertDialog dialog;

    public BarraProgresso(Activity activity){
        this.activity = activity;
    }

    public void comecarProgresso(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.barra_progresso, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void cancelarDialog() {
        this.dialog.cancel();
    }
}
