package com.klinik.antrian.model;

public class Poli {
    private int idPoli;
    private String namaPoli;
    private String jadwal;

    public Poli() {}
    public Poli(String namaPoli, String jadwal) { this.namaPoli = namaPoli; this.jadwal = jadwal; }

    public int getIdPoli()                  { return idPoli; }
    public void setIdPoli(int idPoli)       { this.idPoli = idPoli; }
    public String getNamaPoli()             { return namaPoli; }
    public void setNamaPoli(String n)       { this.namaPoli = n; }
    public String getJadwal()               { return jadwal; }
    public void setJadwal(String j)         { this.jadwal = j; }

    @Override public String toString() { return namaPoli + " (" + jadwal + ")"; }
}