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
    private UserCliente meC, toCliente;
    private UserTrabalhador meT, toTrabalhador;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        txtInfo = findViewById(R.id.txtInfo);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        meC= getIntent().getExtras().getParcelable("meC");
        meT= getIntent().getExtras().getParcelable("meT");


        adapter = new GroupAdapter();
        RecyclerView rv = findViewById(R.id.rcView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                final ContactItem contactItem = (ContactItem) item;
                if(meT!=null){
                    FirebaseFirestore.getInstance().collection("userCliente").whereEqualTo("email", contactItem.contact.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (e!=null){

                            }else{
                                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot doc: docs){
                                    toCliente = doc.toObject(UserCliente.class);
                                    if (toCliente.getEmail().equals(contactItem.contact.getEmail())){

                                        Intent intent = new Intent(ConversasActivity.this, ChatActivity.class);
                                        intent.putExtra("meTrabalhador", meT);
                                        intent.putExtra("toCliente", toCliente);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    });

                    FirebaseFirestore.getInstance().collection("userCliente").document(contactItem.contact.getUuid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            toCliente = documentSnapshot.toObject(UserCliente.class);
                            Intent intent = new Intent(ConversasActivity.this, ChatActivity.class);
                            intent.putExtra("meTrabalhador", meT);
                            intent.putExtra("toCliente", toCliente);
                            startActivity(intent);
                        }
                    });
                }else if(meC!=null){
                    FirebaseFirestore.getInstance().collection("userTrabalhador").whereEqualTo("email", contactItem.contact.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc:docs){
                                toTrabalhador = doc.toObject(UserTrabalhador.class);
                                if (toTrabalhador.getEmail().equals(contactItem.contact.getEmail())){
                                    Intent intent = new Intent(ConversasActivity.this, ChatActivity.class);
                                    intent.putExtra("meCliente", meC);
                                    intent.putExtra("toTrabalhador", toTrabalhador);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    });
                }
            }
        });

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
                                        if (contact!=null) {
                                            txtInfo.setText("");
                                            adapter.add(new ContactItem(contact));
                                        }
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