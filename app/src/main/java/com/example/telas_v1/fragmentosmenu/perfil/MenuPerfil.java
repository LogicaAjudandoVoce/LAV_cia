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
    private Button  btnMyFoto;
    private ImageView imgMyFoto, imgFotoFundo;
    private Uri uriFoto;
    private UserTrabalhador trabalhador;
    private UserCliente cliente;
    private MetodosUsers metodosUsers;
    private FloatingActionButton btnLogOut,btnEditarPerfil, btnAdicionarFoto, btnConversas;
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private TextView txtNome, txtAdd;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menuperfil_viewmodel = ViewModelProviders.of(this).get(MenuPerfilViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        metodosUsers = new MetodosUsers();
        verificarUser();

        btnMyFoto= root.findViewById(R.id.btnMyFoto);
        imgMyFoto= root.findViewById(R.id.imgMyFoto);
        imgFotoFundo= root.findViewById(R.id.imgFundo);
        btnAdicionarFoto = root.findViewById(R.id.btnAdicionarFoto);
        btnEditarPerfil = root.findViewById(R.id.btnEditarPerfil);
        btnConversas = root.findViewById(R.id.btnConversas);
        recyclerView = root.findViewById(R.id.rcView);
        txtNome = root.findViewById(R.id.txtNomeUser);
        txtAdd = root.findViewById(R.id.txtAdd);

        adapter = new GroupAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        btnMyFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFotoPerfil();
            }
        });

        btnLogOut = root.findViewById(R.id.bt_sairperfil);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActiviy.class);
                  startActivity(intent);
                getActivity().finish();
            }
        });

        imgFotoFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFotoFundo();
            }
        });

        btnAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarListaFotos();
            }
        });

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

    private void selecionarFotoPerfil(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("O que deseja fazer?")
                .setPositiveButton("Mudar Foto de Perfil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0);
                    }
                }).setNegativeButton("Excluir Foto de Perfil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cliente !=null){
                    cliente.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F716c1386-5b82-431c-a2ba-0e16cdeff750?alt=media&token=a48b95e4-7538-4c47-92e8-7f4576eba9c8");
                    FirebaseFirestore.getInstance().collection("userCliente").document(cliente.getId()).set(cliente);
                    btnMyFoto.setAlpha(1);
                }
                else if (trabalhador !=null){
                    trabalhador.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F2e3534fb-21e2-429c-8f1c-ab8f0f5165ee?alt=media&token=0e3b8518-22ab-4ff2-bc13-367059352e92");
                    FirebaseFirestore.getInstance().collection("userTrabalhador").document(trabalhador.getId()).set(trabalhador);
                    btnMyFoto.setAlpha(1);
                }
            }
        }).setNeutralButton("Cancelar", null).create().show();
    }

    private void selecionarFotoFundo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("O que deseja fazer?")
                .setPositiveButton("Mudar Foto de Fundo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                }).setNegativeButton("Excluir Foto de Fundo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cliente !=null){
                    cliente.setUrlFotoFundo("Nada");
                    FirebaseFirestore.getInstance().collection("userCliente").document(cliente.getId()).set(cliente);
                    imgFotoFundo.setImageResource(R.drawable.img_fundo);
                }
                else if (trabalhador !=null){
                    trabalhador.setUrlFotoFundo("Nada");
                    FirebaseFirestore.getInstance().collection("userTrabalhador").document(trabalhador.getId()).set(trabalhador);
                    imgFotoFundo.setImageResource(R.drawable.img_fundo);
                }
            }
        }).setNeutralButton("Cancelar", null).create().show();
    }

    private void selecionarListaFotos() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0 && resultCode==getActivity().RESULT_OK){
            uriFoto = data.getData();
            salvarFotoPerfilBanco();
        }
        else if (requestCode==1 && resultCode == getActivity().RESULT_OK){
            uriFoto = data.getData();
            salvarFotoFundoBanco();
        }
        else if (requestCode==2 && resultCode == getActivity().RESULT_OK){
            uriFoto = data.getData();
            salvarFotoListaBanco();
        }

    }

    private void salvarFotoListaBanco() {
        final String id = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/listaImages/"+FirebaseAuth.getInstance().getUid()+"/"+id);
        ref.putFile(uriFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FotoLista fotoLista = new FotoLista();
                        fotoLista.setId(FirebaseAuth.getInstance().getUid());
                        fotoLista.setFoto(uri.toString());
                        FirebaseFirestore.getInstance().collection("listaImgs/").document(id).set(fotoLista);
                    }
                });
            }
        });
        Toast.makeText(getContext(), "Espere um momento...", Toast.LENGTH_LONG).show();
    }

    private void salvarFotoPerfilBanco() {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/perfilImages/"+FirebaseAuth.getInstance().getUid());
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

    private void salvarFotoFundoBanco(){
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/fundoImages/"+FirebaseAuth.getInstance().getUid());
        ref.putFile(uriFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (cliente !=null){
                            cliente.setUrlFotoFundo(uri.toString());
                            FirebaseFirestore.getInstance().collection("userCliente").document(cliente.getId()).set(cliente);
                        }
                        else if (trabalhador !=null){
                            trabalhador.setUrlFotoFundo(uri.toString());
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
        txtNome.setText(trabalhador.getNome());
        Picasso.get().load(trabalhador.getUrlFotoPerfil()).into(imgMyFoto);
        btnMyFoto.setAlpha(0);
        if (trabalhador.getUrlFotoFundo()!=null && !trabalhador.getUrlFotoFundo().equals("Nada"))Picasso.get().load(trabalhador.getUrlFotoFundo()).fit().into(imgFotoFundo);

        FirebaseFirestore.getInstance().collection("listaImgs").whereEqualTo("id", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        adapter.add(new FotoListaView(doc.get("foto").toString()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void carregarCliente() {
        txtNome.setText(cliente.getNome());
        Picasso.get().load(cliente.getUrlFotoPerfil()).into(imgMyFoto);
        btnMyFoto.setAlpha(0);
        if (cliente.getUrlFotoFundo()!=null && !cliente.getUrlFotoFundo().equals("Nada"))Picasso.get().load(cliente.getUrlFotoFundo()).fit().into(imgFotoFundo);

        FirebaseFirestore.getInstance().collection("listaImgs").whereEqualTo("id", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        adapter.add(new FotoListaView(doc.get("foto").toString()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private class FotoListaView extends Item<ViewHolder>{

        private final String fotoLista;

        private FotoListaView(String fotoLista) {
            this.fotoLista = fotoLista;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView img = viewHolder.itemView.findViewById(R.id.imgList);

            if (fotoLista!=null){
                Picasso.get().load(fotoLista).resize(400, 300).into(img);
                txtAdd.setText("");
            }
            else txtAdd.setText("Adicione suas fotos aqui");
        }

        @Override
        public int getLayout() {
            return R.layout.item_foto_list;
        }
    }
}