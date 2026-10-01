package com.klinik.antrian.model;

public class PasienLansia extends Pasien {
    public PasienLansia(String nama, String nik, int usia, String keluhan) {
        super(nama, nik, usia, keluhan);
    }
    @Override public int getBobotPrioritas() { return 70; }
    @Override public String getKategori()    { return "LANSIA"; }
}