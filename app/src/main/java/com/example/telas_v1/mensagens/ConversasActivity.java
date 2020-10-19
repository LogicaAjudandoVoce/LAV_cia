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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class ConversasActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    private UserCliente cliente, userC;
    private UserTrabalhador trabalhador, userT;
    private MetodosUsers metodosUsers = new MetodosUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);
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


        RecyclerView rv = findViewById(R.id.rcView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull final Item item, @NonNull View view) {
//                ContactItem contactItem = (ContactItem) item;
//                if (cliente!=null){
//                    FirebaseFirestore.getInstance().collection("userTrabalhador").document(contactItem.contact.getUuid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            userT = documentSnapshot.toObject(UserTrabalhador.class);
//                            Intent intent = new Intent(ConversasActivity.this, ChatActivity.class);
//                            intent.putExtra("trabalhador", userT);
//                            intent.putExtra("forma", "conversas");
//                            startActivity(intent);
//                        }
//                    });
//                }else if(trabalhador!=null){
//                    FirebaseFirestore.getInstance().collection("userTrabalhador").document(contactItem.contact.getUuid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            userC = documentSnapshot.toObject(UserCliente.class);
//                            Intent intent = new Intent(ConversasActivity.this, ChatActivity.class);
//                            intent.putExtra("cliente", userC);
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        });
        fetchLastMessage();
    }

    private void fetchLastMessage() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;


        FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                .document(uid)
                .collection("contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.d("TESTE", e.getMessage());
                        }else {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            if (documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Contato contact = doc.getDocument().toObject(Contato.class);

                                        adapter.add(new ContactItem(contact));
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private class ContactItem extends Item<ViewHolder> {

        private final Contato contact;

        private ContactItem(Contato contact) {
            this.contact = contact;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView username = viewHolder.itemView.findViewById(R.id.txtNome);
            TextView message = viewHolder.itemView.findViewById(R.id.txtMsn);
            ImageView imgPhoto = viewHolder.itemView.findViewById(R.id.imgPerfil);

            username.setText(contact.getUsername());
            message.setText(contact.getLastMessage());
            Picasso.get()
                    .load(contact.getPhotoUrl())
                    .into(imgPhoto);
        }

        @Override
        public int getLayout() {
            return R.layout.item_user_message;
        }
    }
}