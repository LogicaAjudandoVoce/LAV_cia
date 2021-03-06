package com.example.telas_v1.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.telas_v1.activitys.startactivitys.*;
import com.example.telas_v1.fragmentos.fragmentosmenu.*;
import com.example.telas_v1.R;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class MetodosUsers{
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
                Log.e("TESTE", "Login: "+e.getMessage(), e);
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
                        userCliente.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F716c1386-5b82-431c-a2ba-0e16cdeff750?alt=media&token=a48b95e4-7538-4c47-92e8-7f4576eba9c8");
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
                                Log.e("TESTE", "Cadastrar Firestore Cliente: "+e.getMessage(), e);
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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("TESTE", "Cadastrar Auth Cliente: "+e.getMessage(), e);
                }
            });

        }else{
            userTrabalhador.setUrlFotoPerfil("https://firebasestorage.googleapis.com/v0/b/projetolavcia-2020.appspot.com/o/imgsUsuarios%2F2e3534fb-21e2-429c-8f1c-ab8f0f5165ee?alt=media&token=0e3b8518-22ab-4ff2-bc13-367059352e92");
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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TESTE", "Cadastrar Firestore Trablhador: "+e.getMessage(), e);
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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("TESTE", "Cadastrar Auth Trabalhador: "+e.getMessage(), e);
                }
            });
        }
    }

    public void listarTrabalhador(final GroupAdapter adapter, final String tipoTrab, final List<String> keys) {
        FirebaseFirestore.getInstance().collection("/userTrabalhador").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.e("TESTE", "Listar Trabalhadores: " +e.getMessage(), e);
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        UserTrabalhador userTrabalhador = doc.toObject(UserTrabalhador.class);
                        if (!tipoTrab.equals("Nenhum Selecionado")) {
                            if (userTrabalhador.getFiltoFixo().equals(tipoTrab)){
                                adapter.add(new ListarTrabalhadorView(userTrabalhador));
                            }
                        } else if (!keys.isEmpty()){
                            for (String keyT : userTrabalhador.getKeys()) {
                                for (String filtro : keys) {
                                    if (keyT.equals(filtro)) {
                                        adapter.add(new ListarTrabalhadorView(userTrabalhador));
                                    }
                                }
                            }
                        }
                        else {
                            adapter.add(new ListarTrabalhadorView(userTrabalhador));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }public void listarTrabalhador(final GroupAdapter adapter, final PostagemAux postagem) {
        FirebaseFirestore.getInstance().collection("/userTrabalhador").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.e("TESTE", "Listar Trabalhadores: " +e.getMessage(), e);
                }
                else {
                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                        UserTrabalhador userTrabalhador = doc.toObject(UserTrabalhador.class);
                        for(String id: postagem.getVoluntarios()){
                            if (id.equals(userTrabalhador.getId())){
                                adapter.add(new ListarTrabalhadorView(userTrabalhador));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public class ListarTrabalhadorView extends Item<ViewHolder> {

        public final UserTrabalhador userTrabalhador;

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

    public void listarPostagens(final GroupAdapter adapter, final String filtro, final List<String> keys){
        FirebaseFirestore.getInstance().collection("postagens")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE", "Listar Postagens: "+e.getMessage(), e);
                        }else{
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                PostagemAux postagem = doc.toObject(PostagemAux.class);

                                if (postagem.getStatus().equals("Pendente")){
                                    if (!filtro.equals("Nenhum Selecionado")){
                                        if (postagem.getFiltroFixo().equals(filtro)){
                                            adapter.add(new ListarPostagemViewModel(postagem));
                                        }
                                    }else if (!keys.isEmpty()){
                                        for(String key : keys){
                                            for (String key1: postagem.getKeys()){
                                                if (key.equals(key1)){
                                                    adapter.add(new ListarPostagemViewModel(postagem));
                                                }
                                            }
                                        }
                                    }else{
                                        adapter.add(new ListarPostagemViewModel(postagem));
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }

    public void listarPostagens(final GroupAdapter adapter, final UserCliente userCliente, final TextView txtInfo){
        FirebaseFirestore.getInstance().collection("postagens")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE", "Listar Postagens: "+e.getMessage(), e);
                        }else{
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                PostagemAux postagem = doc.toObject(PostagemAux.class);
                                if (postagem.getStatus().equals("Pendente") && postagem.getIdCliente().equals(userCliente.getId())){
                                    txtInfo.setText("");
                                    adapter.add(new ListarPostagemViewModel(postagem));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }

    public void listarPostagensTrabalhador(final GroupAdapter adapter, final String id, final String status, final TextView txtInfo){
        FirebaseFirestore.getInstance().collection("postagens")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE", "Listar Postagens: "+e.getMessage(), e);
                        }else{
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                PostagemAux postagem = doc.toObject(PostagemAux.class);
                                if (postagem.getStatus().equals(status)) {
                                    if (postagem.getVoluntarios() != null) {
                                        for (String user : postagem.getVoluntarios()) {
                                            if (user.equals(id)) {
                                                txtInfo.setText("");
                                                adapter.add(new ListarPostagemViewModel(postagem));
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void listarPostagensCliente(final GroupAdapter adapter, final  String status, final UserCliente cliente, final TextView txtInfo){
        FirebaseFirestore.getInstance().collection("postagens")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.e("TESTE", "Listar Postagens: "+e.getMessage(), e);
                        }else{
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            adapter.clear();
                            for (DocumentSnapshot doc : docs) {
                                PostagemAux postagem = doc.toObject(PostagemAux.class);
                                if (status.equals("Pendente")) {
                                    if (postagem.getStatus().equals(status) && postagem.getVoluntarios() != null && !postagem.getVoluntarios().isEmpty()&& postagem.getIdCliente().equals(cliente.getId())) {
                                        txtInfo.setText("");
                                        adapter.add(new ListarPostagemViewModel(postagem));
                                    }
                                }else if(status.equals("Ativo")){
                                    if (postagem.getStatus().equals(status) && postagem.getIdCliente().equals(cliente.getId())) {
                                        txtInfo.setText("");
                                        adapter.add(new ListarPostagensAtivas(postagem));
                                    }
                                }else{
                                    if (postagem.getStatus().equals(status)) {
                                        txtInfo.setText("");
                                        adapter.add(new ListarPostagensAtivas(postagem));
                                    }
                                }
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
                    Log.e("TESTE","Verificar Usuario: " +e.getMessage(), e);
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
                    Log.e("TESTE","Verificar Usuario: " +e.getMessage(), e);
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

    public class ListarPostagemViewModel extends Item<ViewHolder> {
        public final PostagemAux postagem;

        private ListarPostagemViewModel(PostagemAux postagem) {
            this.postagem = postagem;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtTitle = viewHolder.itemView.findViewById(R.id.txtTituloPost);
            TextView txtAutor = viewHolder.itemView.findViewById(R.id.txtAutorPost);
            TextView txtMini = viewHolder.itemView.findViewById(R.id.txtMiniDescricao);
            TextView txtPreco = viewHolder.itemView.findViewById(R.id.txtPrecoPost);
            TextView txtData = viewHolder.itemView.findViewById(R.id.textDataPost);
            ImageView img = viewHolder.itemView.findViewById(R.id.imgPost);

            if (postagem.getFotos()!=null)Picasso.get().load(postagem.getFotos().get(0)).fit().into(img);
            txtMini.setText(postagem.getMiniDescricao());
            txtTitle.setText(postagem.getTitulo());
            txtAutor.setText(postagem.getNomeAutor());
            txtPreco.setText(String.valueOf(postagem.getPreco()));
            txtData.setText(postagem.getData());
        }

        @Override
        public int getLayout() {
            return R.layout.item_card_list;
        }
    }

    public class ListarPostagensAtivas extends Item<ViewHolder>{

        final public PostagemAux postagem;

        private ListarPostagensAtivas(PostagemAux postagem) {
            this.postagem = postagem;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView title = viewHolder.itemView.findViewById(R.id.txtTitlePost);
            TextView desc = viewHolder.itemView.findViewById(R.id.txtDescPost);
            TextView data = viewHolder.itemView.findViewById(R.id.txtDatePost);
            TextView nameUser = viewHolder.itemView.findViewById(R.id.txtNameTrab);
            ImageView img = viewHolder.itemView.findViewById(R.id.imgTrab);

            title.setText(postagem.getTitulo());
            desc.setText(postagem.getMiniDescricao());
            data.setText(postagem.getData());
            nameUser.setText(postagem.getNomeContratato());
            Picasso.get().load(postagem.getUrlImgContratado()).into(img);
        }

        @Override
        public int getLayout() {
            return R.layout.item_post_ativo;
        }
    }

    public void avaliarUser(final Context context, final UserCliente userC, final UserTrabalhador userT){
        final Dialog dialog= new Dialog(context);
        dialog.setContentView(R.layout.item_avaliar_users);

        final RatingBar ratingBar = dialog.findViewById(R.id.avaliarBarra);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnAvaliar = dialog.findViewById(R.id.btnAvaliarStars);

        dialog.show();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userC!=null) {
                    Log.d("TESTE", String.valueOf(ratingBar.getNumStars()));
                    userC.setCountStar(userC.getCountStar() + 1);
                    userC.setStars(userC.getStars()+ratingBar.getRating());
                    FirebaseFirestore.getInstance().collection("userCliente").document(userC.getId()).set(userC).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Obrigado pela avaliação!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Ocorreu algum erro... tente novamente", Toast.LENGTH_LONG).show();
                            Log.e("TESTE", "Erro avaliar cliente: "+e.getMessage(), e);
                        }
                    });
                }else if (userT!=null) {
                    Log.d("TESTE", String.valueOf(ratingBar.getNumStars()));
                    userT.setCountStars(userT.getCountStars() + 1);
                    userT.setStars(userT.getStars()+ratingBar.getRating());
                    FirebaseFirestore.getInstance().collection("userTrabalhador").document(userT.getId()).set(userT).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Obrigado pela avaliação!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Ocorreu algum erro... tente novamente", Toast.LENGTH_LONG).show();
                            Log.e("TESTE", "Erro avaliar trabalhador: "+e.getMessage(), e);
                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }
}
