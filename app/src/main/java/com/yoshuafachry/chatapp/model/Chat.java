package com.yoshuafachry.chatapp.model;

public class Chat {
    private String ID, nama, ket, tgl;

    public Chat() {
    }

    public Chat(String ID, String nama, String ket, String tgl) {
        this.ID = ID;
        this.nama = nama;
        this.ket = ket;
        this.tgl = tgl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
}
