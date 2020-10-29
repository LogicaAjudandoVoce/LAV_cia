package com.example.telas_v1.fragmentosmenu.perfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.mensagens.ConversasActivity;
import com.example.telas_v1.startactivitys.LoginActiviy;
import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.FotoLista;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;
import java.util.UUID;

public class MenuPerfil extends Fragment {

    private MenuPerfilViewModel menuperfil_viewmodel;
    private UserTrabalhador trabalhador;
    private UserCliente cliente;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menuperfil_viewmodel = ViewModelProviders.of(this).get(MenuPerfilViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        MetodosUsers metodosUsers = new MetodosUsers();
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                }
            }
        });

        FloatingActionButton btnLogOut = root.findViewById(R.id.bt_sairperfil);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActiviy.class));
                getActivity().finish();
            }
        });

        FloatingActionButton btnConversas = root.findViewById(R.id.btnConversas);
        btnConversas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConversasActivity.class);
                if (cliente!=null) intent.putExtra("meC", cliente);
                if (trabalhador!=null) intent.putExtra("meT", trabalhador);
                startActivity(intent);
            }
        });
        return root;
    }

    public static MenuPerfil newInstance() {
        return new MenuPerfil();
    }
}