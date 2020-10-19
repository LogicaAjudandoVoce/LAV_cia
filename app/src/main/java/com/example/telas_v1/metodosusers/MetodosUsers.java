package com.example.telas_v1.metodosusers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.telas_v1.postagemcliente.Postagem;
import com.example.telas_v1.startactivitys.LoginActiviy;
import com.example.telas_v1.fragmentosmenu.MenuActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.classesuteis.BarraProgresso;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class MetodosUsers {
    private BarraProgresso progresso;

    public void autenticarUsuario(final Context context, String email, String password, final ProgressBar barra){
        barra.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "Seja Bem Vindo!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Seu Email ou Senha estão Incorretos!", Toast.LENGTH_LONG).show();
                barra.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void cadastrarUser(final Activity activity, final Context context, final UserCliente userCliente, final UserTrabalhador userTrabalhador){
        progresso = new BarraProgresso(activity);
        progresso.comecarProgresso();
        if (userCliente!=null){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userCliente.getEmail(), userCliente.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userCliente.setId(task.getResult().getUser().getUid());
                        FirebaseFirestore.getInstance().collection("userCliente").document(userCliente.getId()).set(userCliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Tudo certo! Realize seu Login agora.", Toast.LENGTH_LONG).show();
                                activity.startActivity(new Intent(activity, LoginActiviy.class));
                                activity.finish();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TESTE", e.getMessage());
                                progresso.cancelarDialog();
                                Toast.makeText(context, "Ocorreu algum erro, tente novamente!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        try {
                            throw task.getException();
                        }catch(FirebaseAuthUserCollisionException e) {
                            progresso.cancelarDialog();
                            Toast.makeText(context, "Email já Cadastrado!", Toast.LENGTH_LONG).show();
                        } catch(Exception e) {
                        }
                    }
                }
            });

        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userTrabalhador.getEmail(), userTrabalhador.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                            userTrabalhador.setId(task.getResult().getUser().getUid());
                            FirebaseFirestore.getInstance().collection("userTrabalhador").document(userTrabalhador.getId()).set(userTrabalhador).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Tudo certo! Realize seu Login agora.", Toast.LENGTH_LONG).show();
                            activity.startActivity(new Intent(activity, LoginActiviy.class));
                            activity.finish();
                            }
                        });
                    }else{
                        try {
                            throw task.getException();
                        }catch (FirebaseAuthUserCollisionException e){
                            Toast.makeText(context, "Email já Cadastrado!", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                        }
                    }
                }
            });
        }
    }

    public void listarTrabalhador(final GroupAdapter adapter, final String tipoTrab, final String cidade, final float preco) {
        FirebaseFirestore.getInstance().collection("/userTrabalhador").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        UserTrabalhador userTrabalhador = doc.toObject(UserTrabalhador.class);
                        if (!tipoTrab.equals("Nenhum Selecionado")) {
                            if (!cidade.isEmpty()) {
//                            if (preco!=0){
//                                if (userTrabalhador.getMyPreco()<=preco && userTrabalhador.getCidade().equals(cidade))adapter.add(new ListarTrabalhadorView(userTrabalhador));
//                            }else{
//                                if (cidade.equals(userTrabalhador.getCidade()))adapter.add(new ListarTrabalhadorView(userTrabalhador));
//                            }
                            } else {
//                            if (userTrabalhador.getTrabalho().equals(tipoTrab)) adapter.add(new ListarTrabalhadorView(userTrabalhador));
                            }
                        } else if (!cidade.equals("Qualquer")) {
//                            if (preco!=0){
//                                if (userTrabalhador.getMyPreco()<=preco && userTrabalhador.getCidade().equals(cidade))adapter.add(new ListarTrabalhadorView(userTrabalhador));
//                            }else{
//                                if (cidade.equals(userTrabalhador.getCidade()))adapter.add(new ListarTrabalhadorView(userTrabalhador));
//                            }
                        } else if (preco != 0) {
                            if (userTrabalhador.getMyPreco() <= preco)
                                adapter.add(new ListarTrabalhadorView(userTrabalhador));
                        } else adapter.add(new ListarTrabalhadorView(userTrabalhador));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private class ListarTrabalhadorView extends Item<ViewHolder> {

        private final UserTrabalhador userTrabalhador;

        private ListarTrabalhadorView(UserTrabalhador userTrabalhador) {
            this.userTrabalhador = userTrabalhador;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtNome = viewHolder.itemView.findViewById(R.id.txtNomeList);
            TextView txtEmail = viewHolder.itemView.findViewById(R.id.txtEmailList);
            TextView txtMyPreco = viewHolder.itemView.findViewById(R.id.txtPrecoList);
            ImageView imgFoto = viewHolder.itemView.findViewById(R.id.imgFotoList);

            txtNome.setText(userTrabalhador.getNome());
            txtEmail.setText(userTrabalhador.getEmail());
            txtMyPreco.setText(String.valueOf(userTrabalhador.getMyPreco()));
            if (userTrabalhador.getUrlFotoPerfil()!=null && !userTrabalhador.getUrlFotoPerfil().equals("Nada")) Picasso.get().load(userTrabalhador.getUrlFotoPerfil()).into(imgFoto);
        }

        @Override
        public int getLayout() {
            return R.layout.item_barrauser_list;
        }
    }

    public void listarPostagens(final GroupAdapter adapter){
        FirebaseFirestore.getInstance().collection("postagens").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE", e.getMessage());
                }else{
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        Postagem postagem = doc.toObject(Postagem.class);
                        adapter.add(new ListarPostagemViewModel(postagem));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public interface OnResultUser{
        void onResultCliente(UserCliente userCliente);
        void onResultTrabalhador(UserTrabalhador userTrabalhador);
    }

    public void verificarUser(final OnResultUser resultUser){
        FirebaseFirestore.getInstance().collection("/userCliente").whereEqualTo("id", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : docs) {
                        UserCliente user = doc.toObject(UserCliente.class);
                        if (user == null) resultUser.onResultCliente(null);
                        else resultUser.onResultCliente(user);
                    }
                }
            }
        });
        FirebaseFirestore.getInstance().collection("/userTrabalhador").whereEqualTo("id", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TESTE","Error:"+e.getMessage());
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : docs) {
                        UserTrabalhador user = doc.toObject(UserTrabalhador.class);
                        if (user == null) resultUser.onResultTrabalhador(null);
                        else resultUser.onResultTrabalhador(user);
                    }
                }
            }
        });
    }

    private class ListarPostagemViewModel extends Item<ViewHolder> {
       private final Postagem postagem;

        private ListarPostagemViewModel(Postagem postagem) {
            this.postagem = postagem;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtTitle = viewHolder.itemView.findViewById(R.id.txtTituloPost);
            TextView txtAutor = viewHolder.itemView.findViewById(R.id.txtAutorPost);
            TextView txtEmail = viewHolder.itemView.findViewById(R.id.txtEmailPost);
            TextView txtPreco = viewHolder.itemView.findViewById(R.id.txtPrecoPost);
            TextView txtData = viewHolder.itemView.findViewById(R.id.txtDataPost);
            ImageView img = viewHolder.itemView.findViewById(R.id.imgPost);

            txtTitle.setText(postagem.getTitulo());
            txtAutor.setText(postagem.getNomeAutor());
            txtEmail.setText(postagem.getEmail());
            txtPreco.setText(String.valueOf(postagem.getPreco()));
            txtData.setText(postagem.getData());

        }

        @Override
        public int getLayout() {
            return R.layout.item_card_list;
        }
    }
}
