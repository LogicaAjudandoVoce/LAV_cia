package com.example.telas_v1.activitys.users.myperfil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.models.UserCliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyPerfilClienteActivity extends AppCompatActivity {

    private UserCliente meC;
    private FloatingActionButton btnEdit;
    private TextInputEditText txtSobreMim, txtContatos;
    private ImageView imgFundo, imgPerfil;
    private Uri uri;
    private boolean ax=false;
    private Button btnAvaliar;
    private String first;
    private boolean back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perfil_cliente);

        meC = getIntent().getExtras().getParcelable("meC");
        first();
        iniciarComponetes();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetodosUsers metodosUsers = new MetodosUsers();
                metodosUsers.avaliarUser(MyPerfilClienteActivity.this, meC, null);
            }
        });

        Picasso.get().load(meC.getUrlFotoFundo()).into(imgFundo);
        Picasso.get().load(meC.getUrlFotoPerfil()).into(imgPerfil);
    }

    private void iniciarComponetes(){
        first = getIntent().getExtras().getString("first");
        String tipo = getIntent().getExtras().getString("tipo");
        RatingBar barra = findViewById(R.id.barraAvaliacao);
        TextView txtNome = findViewById(R.id.txtNomeClientePerfil);
        TextView txtAvaliar = findViewById(R.id.txtAvaliar);
        btnAvaliar = findViewById(R.id.btnAvaliarStars);
        imgFundo = findViewById(R.id.imgFundoCliente);
        imgPerfil = findViewById(R.id.imgFotoPerfilCliente);
        txtSobreMim = findViewById(R.id.txtSobreMimCliente);
        txtContatos = findViewById(R.id.txtContatosCliente);
        btnEdit = findViewById(R.id.btnEditCliente);
        barra.setFocusable(false);
        barra.setRating(meC.getStars()/meC.getCountStar());

        if (tipo!=null) {
            btnEdit.setVisibility(View.INVISIBLE);
            btnEdit.setEnabled(false);
            btnAvaliar.setVisibility(View.VISIBLE);
            btnAvaliar.setEnabled(true);
        }

        if (meC.getStars()!=0)
            txtAvaliar.setText(String.valueOf(meC.getStars()/(float) meC.getCountStar()).substring(0,3));

        imgFundo.setClickable(false);
        imgPerfil.setClickable(false);
        txtContatos.setText(meC.getContatos());
        txtNome.setText(meC.getNome());
        txtSobreMim.setText(meC.getSobremim());
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
                        ax=!ax;
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
                        ax=!ax;
                    }
                })
                .setNeutralButton("Cancelar", null).create().show();
    }

    public void alterarFoto(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("O que deseja fazer?")
                .setPositiveButton("Alterar Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        if (view.getId() == R.id.imgFotoPerfilCliente)
                            startActivityForResult(intent, 0);
                        else if(view.getId() == R.id.imgFundoCliente)
                            startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("Excluir Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (view.getId()==R.id.imgFundoCliente){
                            meC.setUrlFotoFundo("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios?alt=media&token=ee9d410b-3c49-4b8a-b03f-2670f2bfb105");
                            FirebaseFirestore.getInstance().collection("userCliente").document(meC.getId()).set(meC).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TESTE", "Excluir Foto Perfil Trab: "+e.getMessage(), e);
                                }
                            });
                        }else{
                            meC.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F716c1386-5b82-431c-a2ba-0e16cdeff750?alt=media&token=a48b95e4-7538-4c47-92e8-7f4576eba9c8");
                            FirebaseFirestore.getInstance().collection("userCliente").document(meC.getId()).set(meC).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TESTE", "Excluir Foto Fundo Trab: "+e.getMessage(), e);
                                }
                            });
                        }
                        Toast.makeText(getApplicationContext(), "Exlcuindo...", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Cancelar", null).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            uri = data.getData();
            if (requestCode==0) {
                salvarFotoPerfil();
            }
            else if (requestCode==1) {
                salvarFundoPerfil();
            }
        }
    }

    private void salvarFotoPerfil() {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/perfilImages/"+ FirebaseAuth.getInstance().getUid());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        meC.setUrlFotoPerfil(uri.toString());
                        FirebaseFirestore.getInstance().collection("userCliente").document(meC.getId()).set(meC).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Salvar Foto Perfil Cliente: "+e.getMessage(), e);
                            }
                        });
                    }
                });
            }
        });
        Toast.makeText(this, "Alterando Foto...", Toast.LENGTH_LONG).show();
    }

    private void salvarFundoPerfil() {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/fundoImages/"+FirebaseAuth.getInstance().getUid());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        meC.setUrlFotoFundo(uri.toString());
                        FirebaseFirestore.getInstance().collection("userCliente").document(meC.getId()).set(meC).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Salvar Foto Fundo Cliente: "+e.getMessage(), e);
                            }
                        });
                    }
                });
            }
        });
        Toast.makeText(this, "Alterando Foto...", Toast.LENGTH_LONG).show();
    }

    private void first(){
        first = getIntent().getExtras().getString("first");
        if (first!=null && meC.getUrlFotoFundo().equals("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios?alt=media&token=ee9d410b-3c49-4b8a-b03f-2670f2bfb105") && meC.getUrlFotoPerfil().equals("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F716c1386-5b82-431c-a2ba-0e16cdeff750?alt=media&token=a48b95e4-7538-4c47-92e8-7f4576eba9c8")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seja Bem Vindo!")
                    .setMessage("Primeiramente, edite seu usuários preenchendo o máixmo de informações" +
                            " que você puder. \n\n Clique no ícone de Editar parar inserir seus dados.")
                    .setPositiveButton("Vamos la!", null)
                    .create().show();}
    }

    @Override
    public void onBackPressed() {
        if (first!=null){
            if (!ax)
                if (txtSobreMim.getText().toString().isEmpty())
                    return;
        }else if (ax){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tem certeza?")
                    .setMessage("Você perderá tudo o que editou até aqui")
                    .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyPerfilClienteActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("Cancelar", null).create().show();
        }else{
            super.onBackPressed();
        }
    }

    public void voltarPerfilCliente(View view){
        if (first!=null){
            if (!ax)
                if (txtSobreMim.getText().toString().isEmpty())
                    return;
        }else if (ax){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tem certeza?")
                    .setMessage("Você perderá tudo o que editou até aqui")
                    .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setNegativeButton("Cancelar", null).create().show();
        }else{
            finish();
        }
    }
}