package com.klinik.antrian.dao;

import com.klinik.antrian.model.Dokter;
import com.klinik.antrian.model.Poli;
import com.klinik.antrian.model.Petugas;
import com.klinik.antrian.util.KoneksiDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DokterDAO {

    private final Connection conn;

    public DokterDAO() throws SQLException {
        this.conn = KoneksiDatabase.getInstance().getConnection();
    }

    public List<Dokter> getAllDokter() throws SQLException {
        List<Dokter> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM dokter ORDER BY nama")) {
            while (rs.next()) {
                Dokter d = new Dokter(rs.getString("nama"), rs.getString("spesialis"), rs.getInt("id_poli"));
                d.setIdDokter(rs.getInt("id_dokter"));
                list.add(d);
            }
        }
        return list;
    }

    public void insertDokter(Dokter d) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO dokter (nama, spesialis, id_poli) VALUES (?,?,?)")) {
            ps.setString(1, d.getNama()); ps.setString(2, d.getSpesialis()); ps.setInt(3, d.getIdPoli());
            ps.executeUpdate();
        }
    }

    public List<Poli> getAllPoli() throws SQLException {
        List<Poli> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM poli ORDER BY nama_poli")) {
            while (rs.next()) {
                Poli p = new Poli(rs.getString("nama_poli"), rs.getString("jadwal"));
                p.setIdPoli(rs.getInt("id_poli"));
                list.add(p);
            }
        }
        return list;
    }
}