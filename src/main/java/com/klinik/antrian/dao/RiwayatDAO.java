package com.klinik.antrian.dao;

import com.klinik.antrian.model.Petugas;
import com.klinik.antrian.util.KoneksiDatabase;
import java.sql.*;

public class RiwayatDAO {

    private final Connection conn;

    public RiwayatDAO() throws SQLException {
        this.conn = KoneksiDatabase.getInstance().getConnection();
    }

    public Petugas login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM petugas WHERE username=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Petugas p = new Petugas(rs.getString("username"), rs.getString("password"),
                            Petugas.Role.valueOf(rs.getString("role")));
                    p.setIdPetugas(rs.getInt("id_petugas"));
                    return p;
                }
            }
        }
        return null;
    }

    public void catatRiwayat(int idPasien, int idDokter, String catatan) throws SQLException {
        String sql = "INSERT INTO riwayat_pemeriksaan (id_pasien, id_dokter, waktu_dipanggil, waktu_selesai, catatan) "
                   + "VALUES (?,?,NOW(),NOW(),?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPasien); ps.setInt(2, idDokter); ps.setString(3, catatan);
            ps.executeUpdate();
        }
    }
}