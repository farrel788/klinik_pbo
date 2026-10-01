package com.klinik.antrian.dao;

import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.KoneksiDatabase;
import com.klinik.antrian.util.PasienFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasienDAO {

    private final Connection conn;

    public PasienDAO() throws SQLException {
        this.conn = KoneksiDatabase.getInstance().getConnection();
    }

    public int insert(Pasien p) throws SQLException {
        String sql = "INSERT INTO pasien (nama, nik, usia, kategori, keluhan, no_antrian, waktu_daftar, status) "
                   + "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNik());
            ps.setInt(3, p.getUsia());
            ps.setString(4, p.getKategori());
            ps.setString(5, p.getKeluhan());
            ps.setString(6, p.getNoAntrian());
            ps.setTimestamp(7, Timestamp.valueOf(p.getWaktuDaftar()));
            ps.setString(8, p.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { p.setId(rs.getInt(1)); return rs.getInt(1); }
            }
        }
        return -1;
    }

    public void updateStatus(int idPasien, String status) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE pasien SET status=? WHERE id_pasien=?")) {
            ps.setString(1, status); ps.setInt(2, idPasien); ps.executeUpdate();
        }
    }

    public List<Pasien> getAntrianMenunggu() throws SQLException {
        List<Pasien> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pasien WHERE status='MENUNGGU' ORDER BY waktu_daftar")) {
            while (rs.next()) list.add(map(rs));
        }
        list.sort((a, b) -> {
            int cmp = Integer.compare(b.getBobotPrioritas(), a.getBobotPrioritas());
            return cmp == 0 ? a.getWaktuDaftar().compareTo(b.getWaktuDaftar()) : cmp;
        });
        return list;
    }

    public List<Pasien> getRiwayat() throws SQLException {
        List<Pasien> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pasien WHERE status IN ('SELESAI','SKIP','DIPANGGIL') ORDER BY waktu_daftar DESC")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int hitungPerKategori(String kategori) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM pasien WHERE kategori=?")) {
            ps.setString(1, kategori);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        return 0;
    }

    private Pasien map(ResultSet rs) throws SQLException {
        Pasien p = PasienFactory.buat(rs.getString("kategori"), rs.getString("nama"),
                rs.getString("nik"), rs.getInt("usia"), rs.getString("keluhan"));
        p.setId(rs.getInt("id_pasien"));
        p.setNoAntrian(rs.getString("no_antrian"));
        Timestamp ts = rs.getTimestamp("waktu_daftar");
        if (ts != null) p.setWaktuDaftar(ts.toLocalDateTime());
        p.setStatus(rs.getString("status"));
        return p;
    }

    public String generateNoAntrian(String kategori) throws SQLException {
        String prefix = switch (kategori) {
            case "GAWAT_DARURAT" -> "D"; case "LANSIA" -> "P";
            case "IBU_HAMIL"     -> "H"; default       -> "A";
        };
        int urut = 1;
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM pasien WHERE kategori=?")) {
            ps.setString(1, kategori);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) urut = rs.getInt(1) + 1; }
        }
        return String.format("%s-%03d", prefix, urut);
    }
}