package com.klinik.antrian.model;

public class Dokter {
    private int idDokter;
    private String nama;
    private String spesialis;
    private int idPoli;

    public Dokter() {}
    public Dokter(String nama, String spesialis, int idPoli) {
        this.nama = nama; this.spesialis = spesialis; this.idPoli = idPoli;
    }

    public int getIdDokter()                { return idDokter; }
    public void setIdDokter(int id)         { this.idDokter = id; }
    public String getNama()                 { return nama; }
    public void setNama(String n)           { this.nama = n; }
    public String getSpesialis()            { return spesialis; }
    public void setSpesialis(String s)      { this.spesialis = s; }
    public int getIdPoli()                  { return idPoli; }
    public void setIdPoli(int id)           { this.idPoli = id; }

    @Override public String toString() { return nama + " \u2014 " + spesialis; }
}