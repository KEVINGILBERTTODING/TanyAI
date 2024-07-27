package com.example.tanyai.core.models;

public class ResultModel {
    private String name;
    private String kalori;
    private String gula;
    private String catatan;
    private String saran;

    public ResultModel(String name, String kalori, String gula, String catatan, String saran) {
        this.name = name;
        this.kalori = kalori;
        this.gula = gula;
        this.catatan = catatan;
        this.saran = saran;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getGula() {
        return gula;
    }

    public void setGula(String gula) {
        this.gula = gula;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getSaran() {
        return saran;
    }

    public void setSaran(String saran) {
        this.saran = saran;
    }
}
