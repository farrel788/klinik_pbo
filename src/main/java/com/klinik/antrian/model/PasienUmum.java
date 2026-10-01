package com.klinik.antrian.model;

public class PasienUmum extends Pasien {
    public PasienUmum(String nama, String nik, int usia, String keluhan) {
        super(nama, nik, usia, keluhan);
    }
    @Override public int getBobotPrioritas() { return 10; }
    @Override public String getKategori()    { return "UMUM"; }
}