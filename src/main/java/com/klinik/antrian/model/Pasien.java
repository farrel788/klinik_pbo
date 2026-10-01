package com.klinik.antrian.model;

import java.time.LocalDateTime;

public abstract class Pasien {

    protected int id;
    protected String nama;
    protected String nik;
    protected int usia;
    protected String keluhan;
    protected String noAntrian;
    protected LocalDateTime waktuDaftar;
    protected String status;

    public Pasien(String nama, String nik, int usia, String keluhan) {
        this.nama = nama;
        setNik(nik);
        this.usia = usia;
        this.keluhan = keluhan;
        this.waktuDaftar = LocalDateTime.now();
        this.status = "MENUNGGU";
    }

    public abstract int getBobotPrioritas();
    public abstract String getKategori();

    public int getId()                         { return id; }
    public void setId(int id)                  { this.id = id; }
    public String getNama()                    { return nama; }
    public void setNama(String nama)           { this.nama = nama; }
    public String getNik()                     { return nik; }
    public final void setNik(String nik) {
        if (nik == null || !nik.matches("\\d{16}"))
            throw new IllegalArgumentException("NIK harus 16 digit angka!");
        this.nik = nik;
    }
    public int getUsia()                       { return usia; }
    public void setUsia(int usia)              { this.usia = usia; }
    public String getKeluhan()                 { return keluhan; }
    public void setKeluhan(String keluhan)     { this.keluhan = keluhan; }
    public String getNoAntrian()               { return noAntrian; }
    public void setNoAntrian(String noAntrian) { this.noAntrian = noAntrian; }
    public LocalDateTime getWaktuDaftar()      { return waktuDaftar; }
    public void setWaktuDaftar(LocalDateTime w){ this.waktuDaftar = w; }
    public String getStatus()                  { return status; }
    public void setStatus(String status)       { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | bobot=%d | %s",
                noAntrian, nama, getKategori(), getBobotPrioritas(), keluhan);
    }
}