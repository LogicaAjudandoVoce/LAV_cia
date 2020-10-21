package com.example.telas_v1.mensagens;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.telas_v1.R;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MetodosMensagens {
    public void carregarMensagensTrabalhador(final UserTrabalhador meT, final UserCliente userC, final GroupAdapter adapter) {
        String fromId = meT.getId();
        String toId = userC.getId();

        FirebaseFirestore.getInstance().collection("/conversas")
                .document(fromId)
                .collection(toId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                        } else {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            if (documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Mensagem message = doc.getDocument().toObject(Mensagem.class);
                                        adapter.add(new MessageItemTrabalhador(message, meT, userC));
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private class MessageItemTrabalhador extends Item<ViewHolder> {

        private final Mensagem message;
        private final UserTrabalhador meT;
        private final UserCliente userC;

        public MessageItemTrabalhador(Mensagem message, UserTrabalhador meT, UserCliente userC) {
            this.message = message;
            this.meT = meT;
            this.userC = userC;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Date date = new Date(message.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            TextView txtMsg = viewHolder.itemView.findViewById(R.id.txtMsn);
            TextView txtHora = viewHolder.itemView.findViewById(R.id.txtHora);
            ImageView imgMessage = viewHolder.itemView.findViewById(R.id.imgUser);

            txtMsg.setText(message.getText());
            txtHora.setText(sdf.format(date));

            Picasso.get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? meT.getUrlFotoPerfil()
                            : userC.getUrlFotoPerfil())
                    .into(imgMessage);
        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_msn_me
                    : R.layout.item_to_msn;
        }
    }

    public void carregarMensagensCliente(final UserCliente meC, final UserTrabalhador userT, final GroupAdapter adapter) {
        String fromId = meC.getId();
        String toId = userT.getId();

        FirebaseFirestore.getInstance().collection("/conversas")
                .document(fromId)
                .collection(toId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                        } else {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                            if (documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Mensagem message = doc.getDocument().toObject(Mensagem.class);
                                        adapter.add(new MessageItemCliente(message, meC, userT));
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private class MessageItemCliente extends Item<ViewHolder> {

        private final Mensagem message;
        private final UserCliente meC;
        private final UserTrabalhador userT;

        public MessageItemCliente(Mensagem message, UserCliente meC, UserTrabalhador userT) {
            this.message = message;
            this.meC = meC;
            this.userT = userT;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Date date = new Date(message.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            TextView txtMsg = viewHolder.itemView.findViewById(R.id.txtMsn);
            TextView txtHora = viewHolder.itemView.findViewById(R.id.txtHora);
            ImageView imgMessage = viewHolder.itemView.findViewById(R.id.imgUser);

            txtMsg.setText(message.getText());
            txtHora.setText(sdf.format(date));

            Picasso.get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? meC.getUrlFotoPerfil()
                            : userT.getUrlFotoPerfil())
                    .into(imgMessage);

        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_msn_me
                    : R.layout.item_to_msn;
        }
    }

    public void enviarMensagemToCliente(TextView txtMsn,final UserTrabalhador meT, final UserCliente userC){
        String text = txtMsn.getText().toString();

        if (!text.isEmpty()) {
            txtMsn.setText(null);

            final String fromId = FirebaseAuth.getInstance().getUid();
            final String toId = userC.getId();
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
                                Log.d("Teste", documentReference.getId());

                                Contato contact = new Contato();
                                contact.setUuid(toId);
                                contact.setUsername(userC.getNome());
                                contact.setEmail(userC.getEmail());
                                contact.setPhotoUrl(userC.getUrlFotoPerfil());
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
                                Log.d("Teste", documentReference.getId());

                                Contato contact = new Contato();
                                contact.setUuid(toId);
                                contact.setUsername(meT.getNome());
                                contact.setEmail(meT.getEmail());
                                contact.setPhotoUrl(meT.getUrlFotoPerfil());
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
    }

    public void enviarMensagemToTrabalhador(TextView txtMsn, final UserCliente meC, final UserTrabalhador userT){
        String text = txtMsn.getText().toString();
        if (!text.isEmpty()) {
            txtMsn.setText(null);

            final String fromId = FirebaseAuth.getInstance().getUid();
            final String toId = userT.getId();
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
                                Log.d("Teste", documentReference.getId());

                                Contato contact = new Contato();
                                contact.setUuid(toId);
                                contact.setEmail(userT.getEmail());
                                contact.setUsername(userT.getNome());
                                contact.setPhotoUrl(userT.getUrlFotoPerfil());
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
                                Log.d("Teste", documentReference.getId());

                                Contato contact = new Contato();
                                contact.setUuid(toId);
                                contact.setUsername(meC.getNome());
                                contact.setEmail(meC.getEmail());
                                contact.setPhotoUrl(meC.getUrlFotoPerfil());
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
    }
}
