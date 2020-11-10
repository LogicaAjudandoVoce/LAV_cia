package com.example.telas_v1.activitys.users.myperfil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.telas_v1.R;
import com.example.telas_v1.models.UserCliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MyPerfilClienteActivity extends AppCompatActivity {

    private UserCliente meC;
    private ImageView imgFundo, imgPerfil;
    private TextInputEditText txtSobreMim, txtContatos;
    private boolean ax=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perfil_cliente);

        meC = getIntent().getExtras().getParcelable("meC");
        iniciarComponetes();

    }

    private void iniciarComponetes(){
        imgFundo = findViewById(R.id.imgFundoCliente);
        imgPerfil = findViewById(R.id.imgFotoPerfilCliente);
        txtSobreMim = findViewById(R.id.txtSobreMimCliente);
        txtContatos = findViewById(R.id.txtContatosCliente);


        txtContatos.setText(meC.getSobremim());
        txtSobreMim.setText(meC.getContatos());
        Picasso.get().load(meC.getUrlFotoFundo()).into(imgFundo);
        Picasso.get().load(meC.getUrlFotoPerfil()).into(imgPerfil);
    }

    public void btnEdit(View view){
       final FloatingActionButton btnEdit = findViewById(R.id.btnEditCliente);
        if (!ax){
            btnEdit.setImageResource(R.drawable.ic_floating);
            imgFundo.setClickable(true);
            imgPerfil.setClickable(true);
            txtContatos.setEnabled(true);
            txtSobreMim.setEnabled(true);
            ax=!ax;
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("O que deseja fazer?")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        meC.setSobremim(txtSobreMim.getText().toString());
                        meC.setContatos(txtContatos.getText().toString());
                        FirebaseFirestore.getInstance().collection("userCliente").document(meC.getId()).set(meC);
                        btnEdit.setImageResource(R.drawable.ic_editar);
                        imgFundo.setClickable(false);
                        imgPerfil.setClickable(false);
                        txtContatos.setEnabled(false);
                        txtSobreMim.setEnabled(false);
                    }
                })
                .setNegativeButton("Restaurar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iniciarComponetes();
                        btnEdit.setImageResource(R.drawable.ic_editar);
                        imgFundo.setClickable(false);
                        imgPerfil.setClickable(false);
                        txtContatos.setEnabled(false);
                        txtSobreMim.setEnabled(false);
                }
                })
                .setNeutralButton("Cancelar", null).create().show();

        btnEdit.setImageResource(R.drawable.ic_editar);
        ax=!ax;
    }
}