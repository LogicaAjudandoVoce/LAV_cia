package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Postagem{

    private static String idCliente, idPost, nomeAutor, titulo, descricao, data, email, miniDescricao, filtroFixo, status;
    private static String idContratado, nomeContratato, urlImgContratado;
    private static List<String> keys, fotos, voluntarios;
    private static double preco, latitude, longitude;

    public static String getIdCliente() {
        return idCliente;
    }

    public static void setIdCliente(String idCliente) {
        Postagem.idCliente = idCliente;
    }

    public static String getIdPost() {
        return idPost;
    }

    public static void setIdPost(String idPost) {
        Postagem.idPost = idPost;
    }

    public static String getNomeAutor() {
        return nomeAutor;
    }

    public static void setNomeAutor(String nomeAutor) {
        Postagem.nomeAutor = nomeAutor;
    }

    public static String getTitulo() {
        return titulo;
    }

    public static void setTitulo(String titulo) {
        Postagem.titulo = titulo;
    }

    public static String getDescricao() {
        return descricao;
    }

    public static void setDescricao(String descricao) {
        Postagem.descricao = descricao;
    }

    public static String getData() {
        return data;
    }

    public static void setData(String data) {
        Postagem.data = data;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Postagem.email = email;
    }

    public static String getMiniDescricao() {
        return miniDescricao;
    }

    public static void setMiniDescricao(String miniDescricao) {
        Postagem.miniDescricao = miniDescricao;
    }

    public static String getFiltroFixo() {
        return filtroFixo;
    }

    public static void setFiltroFixo(String filtroFixo) {
        Postagem.filtroFixo = filtroFixo;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Postagem.status = status;
    }

    public static String getIdContratado() {
        return idContratado;
    }

    public static void setIdContratado(String idContratado) {
        Postagem.idContratado = idContratado;
    }

    public static String getNomeContratato() {
        return nomeContratato;
    }

    public static void setNomeContratato(String nomeContratato) {
        Postagem.nomeContratato = nomeContratato;
    }

    public static String getUrlImgContratado() {
        return urlImgContratado;
    }

    public static void setUrlImgContratado(String urlImgContratado) {
        Postagem.urlImgContratado = urlImgContratado;
    }

    public static List<String> getKeys() {
        return keys;
    }

    public static void setKeys(List<String> keys) {
        Postagem.keys = keys;
    }

    public static List<String> getFotos() {
        return fotos;
    }

    public static void setFotos(List<String> fotos) {
        Postagem.fotos = fotos;
    }

    public static List<String> getVoluntarios() {
        return voluntarios;
    }

    public static void setVoluntarios(List<String> voluntarios) {
        Postagem.voluntarios = voluntarios;
    }

    public static double getPreco() {
        return preco;
    }

    public static void setPreco(double preco) {
        Postagem.preco = preco;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Postagem.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Postagem.longitude = longitude;
    }
}