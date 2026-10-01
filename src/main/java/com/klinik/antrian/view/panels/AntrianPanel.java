package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.AntrianPrioritasKlinik;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.Refreshable;
import com.klinik.antrian.view.components.CategoryBadge;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.StatusBadge;
import com.klinik.antrian.view.components.SummaryCard;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * AntrianPanel — daftar antrian menunggu dengan kartu ringkasan & badge warna.
 */
public class AntrianPanel extends JPanel implements Refreshable {

    private final DefaultTableModel model;
    private final SummaryCard cardTotal, cardDarurat, cardLansia, cardHamil, cardUmum;

    public AntrianPanel() {
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Daftar Antrian Prioritas");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        // summary cards
        JPanel cards = new JPanel(new GridLayout(1, 5, Theme.L, Theme.L));
        cards.setOpaque(false);
        cardTotal = new SummaryCard(IconHelper.K_KLINIK, "Total", "0", Theme.PRIMARY);
        cardDarurat = new SummaryCard(IconHelper.K_DARURAT, "Darurat", "0", Theme.DANGER);
        cardLansia = new SummaryCard(IconHelper.K_LANSIA, "Lansia", "0", Theme.WARNING);
        cardHamil = new SummaryCard(IconHelper.K_HAMIL, "Ibu Hamil", "0", Theme.INFO);
        cardUmum = new SummaryCard(IconHelper.K_UMUM, "Umum", "0", Theme.NEUTRAL);
        cards.add(cardTotal); cards.add(cardDarurat); cards.add(cardLansia);
        cards.add(cardHamil); cards.add(cardUmum);
        add(cards, BorderLayout.NORTH);

        // table
        model = new DefaultTableModel(new String[]{"No. Antrian", "Nama", "Kategori", "Bobot", "Waktu Daftar", "Keluhan"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(Theme.body());
        table.getTableHeader().setFont(Theme.bold());
        table.getTableHeader().setBackground(Theme.BACKGROUND);
        table.getColumnModel().getColumn(2).setCellRenderer(new BadgeRenderer(true));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setFont(Theme.body());
        btnRefresh.addActionListener(e -> refresh());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        try {
            PasienDAO dao = new PasienDAO();
            List<Pasien> list = dao.getAntrianMenunggu();
            AntrianPrioritasKlinik antrian = new AntrianPrioritasKlinik();
            for (Pasien p : list) antrian.tambahAntrian(p);

            int d = 0, l = 0, h = 0, u = 0;
            for (Pasien p : antrian.getDaftarAntrian()) {
                model.addRow(new Object[]{p.getNoAntrian(), p.getNama(), p.getKategori(),
                        p.getBobotPrioritas(),
                        p.getWaktuDaftar() != null ? p.getWaktuDaftar().toLocalTime().toString() : "-",
                        p.getKeluhan()});
                switch (p.getKategori()) {
                    case "GAWAT_DARURAT" -> d++;
                    case "LANSIA"        -> l++;
                    case "IBU_HAMIL"     -> h++;
                    default              -> u++;
                }
            }
            cardTotal.setValue(String.valueOf(antrian.ukuran()));
            cardDarurat.setValue(String.valueOf(d));
            cardLansia.setValue(String.valueOf(l));
            cardHamil.setValue(String.valueOf(h));
            cardUmum.setValue(String.valueOf(u));
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal memuat antrian: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
    }

    /** Renderer untuk kolom kategori agar tampil badge berwarna. */
    private static class BadgeRenderer extends DefaultTableCellRenderer {
        private final boolean category;
        BadgeRenderer(boolean category) { this.category = category; }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            String text = value != null ? value.toString() : "";
            Component c;
            if (category) c = new CategoryBadge(text);
            else c = new StatusBadge(text);
            if (isSelected) c.setBackground(table.getSelectionBackground());
            return c;
        }
    }
}
