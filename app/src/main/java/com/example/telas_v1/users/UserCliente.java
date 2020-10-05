package com.example.telas_v1.users;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCliente implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil;

    public UserCliente() {
    }

    protected UserCliente(Parcel in) {
        id = in.readString();
        email = in.readString();
        nome = in.readString();
        senha = in.readString();
        tipoUser = in.readString();
        dataNasc = in.readString();
        urlFotoPerfil = in.readString();
    }

    public static final Creator<UserCliente> CREATOR = new Creator<UserCliente>() {
        @Override
        public UserCliente createFromParcel(Parcel in) {
            return new UserCliente(in);
        }

        @Override
        public UserCliente[] newArray(int size) {
            return new UserCliente[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(nome);
        dest.writeString(senha);
        dest.writeString(tipoUser);
        dest.writeString(dataNasc);
        dest.writeString(urlFotoPerfil);
    }
}
