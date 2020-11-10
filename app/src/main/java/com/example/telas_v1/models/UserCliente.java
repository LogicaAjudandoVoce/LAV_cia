package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCliente implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil, urlFotoFundo, sobremim, contatos;
    private int u=0, d=0, t=0, q=0, c=0;

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
        urlFotoFundo = in.readString();
        contatos = in.readString();
        sobremim = in.readString();
        u = in.readInt();
        d = in.readInt();
        t = in.readInt();
        q = in.readInt();
        c = in.readInt();
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

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
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

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getUrlFotoFundo() {
        return urlFotoFundo;
    }

    public void setUrlFotoFundo(String urlFotoFundo) {
        this.urlFotoFundo = urlFotoFundo;
    }

    public String getSobremim() {
        return sobremim;
    }

    public void setSobremim(String sobremim) {
        this.sobremim = sobremim;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(nome);
        parcel.writeString(senha);
        parcel.writeString(tipoUser);
        parcel.writeString(dataNasc);
        parcel.writeString(urlFotoPerfil);
        parcel.writeString(urlFotoFundo);
        parcel.writeString(sobremim);
        parcel.writeString(contatos);
        parcel.writeInt(u);
        parcel.writeInt(d);
        parcel.writeInt(t);
        parcel.writeInt(q);
        parcel.writeInt(c);
    }
}
