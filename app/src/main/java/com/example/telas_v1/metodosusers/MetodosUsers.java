package com.example.telas_v1.metodosusers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.telas_v1.LoginActiviy;
import com.example.telas_v1.MenuActivity;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class MetodosUsers {
    private BarraProgresso progresso;

    public void autenticarUsuario(final Context context, String email, String password){
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
    }}
