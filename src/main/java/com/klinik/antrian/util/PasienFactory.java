package com.klinik.antrian.util;

import com.klinik.antrian.model.*;

public final class PasienFactory {
    private PasienFactory() {}

    public static Pasien buat(String kategori, String nama, String nik,
                              int usia, String keluhan) {
        if (kategori == null)
            throw new IllegalArgumentException("Kategori tidak boleh null");
        return switch (kategori.toUpperCase()) {
            case "GAWAT_DARURAT" -> new PasienGawatDarurat(nama, nik, usia, keluhan);
            case "LANSIA"        -> new PasienLansia(nama, nik, usia, keluhan);
            case "IBU_HAMIL"     -> new PasienIbuHamil(nama, nik, usia, keluhan);
            default              -> new PasienUmum(nama, nik, usia, keluhan);
        };
    }
}