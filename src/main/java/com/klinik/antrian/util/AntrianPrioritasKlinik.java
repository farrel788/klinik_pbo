package com.klinik.antrian.util;

import com.klinik.antrian.model.Pasien;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AntrianPrioritasKlinik implements Antrian<Pasien> {

    private final PriorityQueue<Pasien> antrian;
    private final List<Pasien> riwayatPasien;

    public AntrianPrioritasKlinik() {
        this.antrian = new PriorityQueue<>(
            Comparator.comparingInt(Pasien::getBobotPrioritas).reversed()
                      .thenComparing(Pasien::getWaktuDaftar)
        );
        this.riwayatPasien = new ArrayList<>();
    }

    @Override public void tambahAntrian(Pasien p) {
        if (p == null) return;
        antrian.offer(p);
    }

    @Override public Pasien panggilBerikutnya() {
        Pasien p = antrian.poll();
        if (p != null) {
            p.setStatus("DIPANGGIL");
            simpanKeRiwayat(p);
        }
        return p;
    }

    @Override public boolean isEmpty() { return antrian.isEmpty(); }
    @Override public int ukuran()      { return antrian.size(); }

    public List<Pasien> getDaftarAntrian() {
        List<Pasien> snap = new ArrayList<>(antrian);
        snap.sort(Comparator.comparingInt(Pasien::getBobotPrioritas).reversed()
                  .thenComparing(Pasien::getWaktuDaftar));
        return snap;
    }

    public void simpanKeRiwayat(Pasien p) {
        p.setStatus("SELESAI");
        riwayatPasien.add(p);
    }

    public List<Pasien> getRiwayat() { return riwayatPasien; }
    public void clear()             { antrian.clear(); }
    public Pasien lihatBerikutnya() { return antrian.peek(); }
}