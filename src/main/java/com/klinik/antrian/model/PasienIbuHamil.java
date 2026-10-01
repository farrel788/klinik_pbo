package com.klinik.antrian.model;

public class PasienIbuHamil extends Pasien {
    public PasienIbuHamil(String nama, String nik, int usia, String keluhan) {
        super(nama, nik, usia, keluhan);
    }
    @Override public int getBobotPrioritas() { return 60; }
    @Override public String getKategori()    { return "IBU_HAMIL"; }
}