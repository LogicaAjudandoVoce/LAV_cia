package com.example.telas_v1.fragmentosmenu.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.telas_v1.LoginActiviy;
import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class MenuPerfil extends Fragment {

    private MenuPerfilViewModel menuperfil_viewmodel;
    private Button logOut, btnMyFoto;
    private ImageView imgMyFoto;
    private Uri uriFoto;
    private UserTrabalhador trabalhador;
    private UserCliente cliente;
    private MetodosUsers metodosUsers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menuperfil_viewmodel = ViewModelProviders.of(this).get(MenuPerfilViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        metodosUsers = new MetodosUsers();
        verificarUser();

        btnMyFoto= root.findViewById(R.id.btnMyFoto);
        imgMyFoto= root.findViewById(R.id.imgMyFoto);

        btnMyFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFotoPerfil();
            }
        });

        logOut = root.findViewById(R.id.bt_sairperfil);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActiviy.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return root;
    }

    public static MenuPerfil newInstance() {
        return new MenuPerfil();
    }

    private void selecionarFotoPerfil(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void salvarFotoPerfilBanco() {
        String id = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/perfilImages/"+id);
        ref.putFile(uriFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (cliente !=null){
                            cliente.setUrlFotoPerfil(uri.toString());
                            FirebaseFirestore.getInstance().collection("userCliente").document(cliente.getId()).set(cliente);
                        }
                        else if (trabalhador !=null){
                            trabalhador.setUrlFotoPerfil(uri.toString());
                            FirebaseFirestore.getInstance().collection("userTrabalhador").document(trabalhador.getId()).set(trabalhador);
                        }
                    }
                });
            }
        });
        Toast.makeText(getContext(), "Espere um momento...", Toast.LENGTH_LONG).show();
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                    carregarCliente();
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                    carregarTrabalhador();
                }
            }
        });
    }

    private void carregarTrabalhador() {
    }

    private void carregarCliente() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0){
            Log.d("TESTE", "ENTROU");
            uriFoto = data.getData();
            salvarFotoPerfilBanco();
        }
    }
}