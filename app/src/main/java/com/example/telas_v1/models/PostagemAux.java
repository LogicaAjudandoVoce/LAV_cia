package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PostagemAux implements Parcelable {

    private  String idCliente, idPost, nomeAutor, titulo, descricao, data, email, miniDescricao, filtroFixo, status;
    private  String idContratado, nomeContratato, urlImgContratado;
    private  List<String> keys, fotos, voluntarios;
    private  double preco, latitude, longitude;

    public PostagemAux(){

    }

    protected PostagemAux(Parcel in) {
        idCliente = in.readString();
        idPost = in.readString();
        nomeAutor = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        data = in.readString();
        email = in.readString();
        miniDescricao = in.readString();
        filtroFixo = in.readString();
        status = in.readString();
        idContratado = in.readString();
        nomeContratato = in.readString();
        urlImgContratado = in.readString();
        keys = in.createStringArrayList();
        fotos = in.createStringArrayList();
        voluntarios = in.createStringArrayList();
        preco = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCliente);
        dest.writeString(idPost);
        dest.writeString(nomeAutor);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(email);
        dest.writeString(miniDescricao);
        dest.writeString(filtroFixo);
        dest.writeString(status);
        dest.writeString(idContratado);
        dest.writeString(nomeContratato);
        dest.writeString(urlImgContratado);
        dest.writeStringList(keys);
        dest.writeStringList(fotos);
        dest.writeStringList(voluntarios);
        dest.writeDouble(preco);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostagemAux> CREATOR = new Creator<PostagemAux>() {
        @Override
        public PostagemAux createFromParcel(Parcel in) {
            return new PostagemAux(in);
        }

        @Override
        public PostagemAux[] newArray(int size) {
            return new PostagemAux[size];
        }
    };

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiniDescricao() {
        return miniDescricao;
    }

    public void setMiniDescricao(String miniDescricao) {
        this.miniDescricao = miniDescricao;
    }

    public String getFiltroFixo() {
        return filtroFixo;
    }

    public void setFiltroFixo(String filtroFixo) {
        this.filtroFixo = filtroFixo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdContratado() {
        return idContratado;
    }

    public void setIdContratado(String idContratado) {
        this.idContratado = idContratado;
    }

    public String getNomeContratato() {
        return nomeContratato;
    }

    public void setNomeContratato(String nomeContratato) {
        this.nomeContratato = nomeContratato;
    }

    public String getUrlImgContratado() {
        return urlImgContratado;
    }

    public void setUrlImgContratado(String urlImgContratado) {
        this.urlImgContratado = urlImgContratado;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public List<String> getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(List<String> voluntarios) {
        this.voluntarios = voluntarios;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
