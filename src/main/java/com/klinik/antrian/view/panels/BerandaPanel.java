package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.MainFrame;
import com.klinik.antrian.view.Refreshable;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.SummaryCard;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

/**
 * BerandaPanel — halaman beranda setelah login, menampilkan ringkasan statistik.
 */
public class BerandaPanel extends JPanel implements Refreshable {

    private final SummaryCard cardTotal;
    private final SummaryCard cardDarurat;
    private final SummaryCard cardLansia;
    private final SummaryCard cardHamil;
    private final SummaryCard cardUmum;

    public BerandaPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Selamat Datang di Klinik Sehat Bersama");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, Theme.L, Theme.L));
        grid.setOpaque(false);
        cardTotal  = new SummaryCard(IconHelper.K_KLINIK, "Total Menunggu", "0", Theme.PRIMARY);
        cardDarurat= new SummaryCard(IconHelper.K_DARURAT, "Gawat Darurat", "0", Theme.DANGER);
        cardLansia = new SummaryCard(IconHelper.K_LANSIA, "Lansia", "0", Theme.WARNING);
        cardHamil  = new SummaryCard(IconHelper.K_HAMIL, "Ibu Hamil", "0", Theme.INFO);
        cardUmum   = new SummaryCard(IconHelper.K_UMUM, "Umum", "0", Theme.NEUTRAL);
        SummaryCard cardSelesai = new SummaryCard(IconHelper.K_SELESAI, "Selesai Hari Ini", "0", Theme.SUCCESS);

        grid.add(cardTotal); grid.add(cardDarurat); grid.add(cardLansia);
        grid.add(cardHamil); grid.add(cardUmum); grid.add(cardSelesai);
        add(grid, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        try {
            PasienDAO dao = new PasienDAO();
            List<Pasien> list = dao.getAntrianMenunggu();
            int d = 0, l = 0, h = 0, u = 0;
            for (Pasien p : list) {
                switch (p.getKategori()) {
                    case "GAWAT_DARURAT" -> d++;
                    case "LANSIA"        -> l++;
                    case "IBU_HAMIL"     -> h++;
                    default              -> u++;
                }
            }
            cardTotal.setValue(String.valueOf(list.size()));
            cardDarurat.setValue(String.valueOf(d));
            cardLansia.setValue(String.valueOf(l));
            cardHamil.setValue(String.valueOf(h));
            cardUmum.setValue(String.valueOf(u));
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal memuat beranda: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
    }
}
