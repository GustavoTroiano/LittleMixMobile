package com.example.littlemixmobile.model;

public class ImagemUpload {

    private int index;
    private String caminhoImagem;

    public ImagemUpload(int index, String caminhoImagem) {
        this.index = index;
        this.caminhoImagem = caminhoImagem;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }
}
