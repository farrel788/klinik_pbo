package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.Refreshable;
import com.klinik.antrian.view.components.CategoryBadge;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.StatusBadge;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * RiwayatPanel — log pasien yang sudah dipanggil/selesai/skip, dengan filter.
 */
public class RiwayatPanel extends JPanel implements Refreshable {

    private final DefaultTableModel model;
    private final JComboBox<String> filterStatus;
    private final JComboBox<String> filterKategori;
    private List<Pasien> data;

    public RiwayatPanel() {
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Riwayat Pemeriksaan");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        // filters
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.M, Theme.M));
        filters.setOpaque(false);
        filters.add(new JLabel("Status:")); filters.add(filterStatus = new JComboBox<>(new String[]{"Semua", "SELESAI", "SKIP", "DIPANGGIL"}));
        filters.add(new JLabel("Kategori:")); filters.add(filterKategori = new JComboBox<>(new String[]{"Semua", "UMUM", "LANSIA", "IBU_HAMIL", "GAWAT_DARURAT"}));
        filterStatus.addActionListener(e -> applyFilter());
        filterKategori.addActionListener(e -> applyFilter());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(Theme.body());
        btnRefresh.setIcon(IconHelper.getIcon(IconHelper.K_REFRESH, 18, Theme.TEXT_PRIMARY));
        btnRefresh.addActionListener(e -> refresh());
        filters.add(Box.createHorizontalStrut(Theme.L));
        filters.add(btnRefresh);
        add(filters, BorderLayout.SOUTH);

        model = new DefaultTableModel(new String[]{"No. Antrian", "Nama", "Kategori", "Status", "Waktu Daftar", "Keluhan"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(Theme.body());
        table.getTableHeader().setFont(Theme.bold());
        table.getColumnModel().getColumn(2).setCellRenderer(new BadgeRenderer(true));
        table.getColumnModel().getColumn(3).setCellRenderer(new BadgeRenderer(false));
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        try {
            data = new PasienDAO().getRiwayat();
            applyFilter();
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal memuat riwayat: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
    }

    private void applyFilter() {
        model.setRowCount(0);
        if (data == null) return;
        String s = (String) filterStatus.getSelectedItem();
        String k = (String) filterKategori.getSelectedItem();
        List<Pasien> filtered = data.stream()
                .filter(p -> "Semua".equals(s) || p.getStatus().equals(s))
                .filter(p -> "Semua".equals(k) || p.getKategori().equals(k))
                .collect(Collectors.toList());
        for (Pasien p : filtered) {
            model.addRow(new Object[]{p.getNoAntrian(), p.getNama(), p.getKategori(),
                    p.getStatus(),
                    p.getWaktuDaftar() != null ? p.getWaktuDaftar().toString() : "-",
                    p.getKeluhan()});
        }
    }

    private static class BadgeRenderer extends DefaultTableCellRenderer {
        private final boolean category;
        BadgeRenderer(boolean category) { this.category = category; }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            String text = value != null ? value.toString() : "";
            Component c = category ? new CategoryBadge(text) : new StatusBadge(text);
            if (isSelected) c.setBackground(table.getSelectionBackground());
            return c;
        }
    }
}
