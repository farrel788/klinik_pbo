package com.klinik.antrian.model;

public class PasienGawatDarurat extends Pasien {
    public PasienGawatDarurat(String nama, String nik, int usia, String keluhan) {
        super(nama, nik, usia, keluhan);
    }
    @Override public int getBobotPrioritas() { return 100; }
    @Override public String getKategori()    { return "GAWAT_DARURAT"; }
}