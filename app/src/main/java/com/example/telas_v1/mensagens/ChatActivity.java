package com.example.telas_v1.mensagens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
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

public class ChatActivity extends AppCompatActivity {

    private EditText txtMensagem;
    private ImageView imgPerfil;
    private TextView txtNome;
    private Button btnVoltar;
    private ImageButton btnEnviar;
    private String forma, toId;
    private RecyclerView rcView;
    private GroupAdapter adapter;
    private MetodosUsers metodosUsers = new MetodosUsers();
    private UserCliente fromCliente, meC;
    private UserTrabalhador fromTrabalhador, meT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        verificarUser();
        forma = getIntent().getExtras().getString("forma");
        fromCliente = getIntent().getExtras().getParcelable("cliente");
        fromTrabalhador = getIntent().getExtras().getParcelable("trabalhador");

        txtMensagem = findViewById(R.id.txtMensagem);
        imgPerfil = findViewById(R.id.imgPerfil);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEnviar = findViewById(R.id.btnEnviar);
        txtNome = findViewById(R.id.txtNome);
        rcView = findViewById(R.id.rcView);
        if (fromCliente!=null) {
            txtNome.setText(fromCliente.getNome());
           if (!fromCliente.getUrlFotoPerfil().equals("Nada") && fromCliente.getUrlFotoPerfil()!=null) Picasso.get().load(fromCliente.getUrlFotoPerfil()).into(imgPerfil);
        }

        adapter = new GroupAdapter();
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forma.equals("post")){
                    finish();
                }else if (forma.equals("conversas")){
                    startActivity(new Intent(ChatActivity.this, ConversasActivity.class));
                    finish();
                }
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    meC = userCliente;
                    carregarMensagens();
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    meT = userTrabalhador;
                    carregarMensagens();
                }
            }
        });
    }

    private void sendMessage() {
        String text = txtMensagem.getText().toString();

        txtMensagem.setText(null);

        final String fromId = FirebaseAuth.getInstance().getUid();
        if (meC!=null && fromTrabalhador!=null){
            toId = fromTrabalhador.getId();
        }else if(meT!=null && fromCliente!=null){
            toId = fromCliente.getId();
        }
        long timestamp = System.currentTimeMillis();

        final Mensagem message = new Mensagem();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(text);

        if (!message.getText().isEmpty()) {
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(fromId)
                    .collection(toId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Contato contact = new Contato();
                            contact.setUuid(toId);
                            if (meC!=null){
                                contact.setUsername(fromTrabalhador.getNome());
                                contact.setPhotoUrl(fromTrabalhador.getUrlFotoPerfil());
                                contact.setEmail(fromTrabalhador.getEmail());
                            }else if(meT!=null){
                                contact.setUsername(fromCliente.getNome());
                                contact.setPhotoUrl(fromCliente.getUrlFotoPerfil());
                                contact.setEmail(fromCliente.getEmail());
                            }
                            contact.setTimestamp(message.getTimestamp());
                            contact.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                                    .document(fromId)
                                    .collection("contacts")
                                    .document(toId)
                                    .set(contact);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });

            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Contato contact = new Contato();
                            contact.setUuid(toId);
                            if (fromCliente!=null){
                                contact.setUsername(meT.getNome());
                                contact.setPhotoUrl(meT.getUrlFotoPerfil());
                                contact.setEmail(meT.getEmail());
                            }else if(fromTrabalhador!=null){
                                contact.setUsername(meC.getNome());
                                contact.setPhotoUrl(meC.getUrlFotoPerfil());
                                contact.setEmail(meC.getEmail());
                            }
                            contact.setTimestamp(message.getTimestamp());
                            contact.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                                    .document(toId)
                                    .collection("contacts")
                                    .document(fromId)
                                    .set(contact);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });
        }
    }

    private void carregarMensagens(){
        Log.d("TESTE", "ENTROU");
        String fromId = FirebaseAuth.getInstance().getUid();
        String toId=null;

        if (meC!=null && fromTrabalhador!=null){
            toId = fromTrabalhador.getId();
        }else if(meT!=null && fromCliente!=null){
            toId = fromCliente.getId();
        }

        FirebaseFirestore.getInstance().collection("/conversas")
                .document(fromId)
                .collection(toId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) { List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                        if (documentChanges != null) {
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Mensagem message = doc.getDocument().toObject(Mensagem.class);
                                    adapter.add(new MessageItemTrabalhador(message));
                                }
                            }
                        }
                        }
                });
    }

    private class MessageItemTrabalhador extends Item<ViewHolder> {
        private final Mensagem message;

        private MessageItemTrabalhador(Mensagem message) {
            this.message = message;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtMsg = viewHolder.itemView.findViewById(R.id.txtMsn);
            ImageView imgMessage = viewHolder.itemView.findViewById(R.id.imgUser);

            txtMsg.setText(message.getText());

            Picasso.get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? meT.getUrlFotoPerfil()
                            : fromCliente.getUrlFotoPerfil())
                    .into(imgMessage);
        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_msn_me
                    : R.layout.item_from_msn;
        }
    }
}
