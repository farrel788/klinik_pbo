package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.PasienFactory;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.components.CategoryBadge;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * PendaftaranPanel — halaman input pasien baru untuk petugas Loket.
 * Kategori dipilih lewat kartu pilihan (bukan JComboBox).
 */
public class PendaftaranPanel extends JPanel {

    private final JTextField tfNama, tfNik, tfUsia, tfKeluhan;
    private final Map<String, JToggleButton> categoryButtons = new HashMap<>();
    private String selectedCategory = "UMUM";
    private final JPanel previewPanel;
    private final JLabel lblNoAntrian, lblBobot, lblPosisi;
    private final JLabel lblError;

    public PendaftaranPanel() {
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Pendaftaran Pasien Baru");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(Theme.L, Theme.L));
        content.setOpaque(false);

        // ---- FORM KIRI ----
        JPanel form = new JPanel(new GridLayout(10, 1, Theme.S, Theme.S));
        form.setOpaque(false);

        form.add(label("Nama Lengkap"));
        tfNama = new JTextField(); styleField(tfNama); form.add(tfNama);

        form.add(label("NIK (16 digit)"));
        tfNik = new JTextField(); styleField(tfNik); form.add(tfNik);

        JPanel row = new JPanel(new GridLayout(1, 2, Theme.L, Theme.L));
        row.setOpaque(false);
        JPanel usiaPanel = new JPanel(new GridLayout(2, 1, Theme.S, Theme.S)); usiaPanel.setOpaque(false);
        usiaPanel.add(label("Usia")); tfUsia = new JTextField(); styleField(tfUsia); usiaPanel.add(tfUsia);
        JPanel keluhanPanel = new JPanel(new GridLayout(2, 1, Theme.S, Theme.S)); keluhanPanel.setOpaque(false);
        keluhanPanel.add(label("Keluhan")); tfKeluhan = new JTextField(); styleField(tfKeluhan); keluhanPanel.add(tfKeluhan);
        row.add(usiaPanel); row.add(keluhanPanel);
        form.add(row);

        form.add(label("Pilih Kategori"));
        form.add(categorySelector());

        lblError = new JLabel(" ");
        lblError.setFont(Theme.caption());
        lblError.setForeground(Theme.DANGER);
        form.add(lblError);

        content.add(form, BorderLayout.CENTER);

        // ---- PREVIEW KANAN ----
        previewPanel = new JPanel(new BorderLayout(Theme.L, Theme.L));
        previewPanel.setBackground(Theme.SURFACE);
        previewPanel.setBorder(Theme.cardBorder());
        previewPanel.setPreferredSize(new Dimension(260, 0));

        JLabel previewTitle = new JLabel("Ringkasan");
        previewTitle.setFont(Theme.h2());
        previewTitle.setForeground(Theme.TEXT_PRIMARY);
        previewPanel.add(previewTitle, BorderLayout.NORTH);

        JPanel previewBody = new JPanel(new GridLayout(6, 1, Theme.M, Theme.M));
        previewBody.setOpaque(false);
        previewBody.add(label("Kategori"));
        previewBody.add(new CategoryBadge(selectedCategory));
        previewBody.add(label("Bobot Prioritas"));
        lblBobot = new JLabel("10");
        lblBobot.setFont(Theme.h2());
        lblBobot.setForeground(Theme.TEXT_PRIMARY);
        previewBody.add(lblBobot);
        previewBody.add(label("Estimasi Posisi"));
        lblPosisi = new JLabel("-");
        lblPosisi.setFont(Theme.h2());
        lblPosisi.setForeground(Theme.TEXT_PRIMARY);
        previewBody.add(lblPosisi);
        previewBody.add(label("Nomor Antrian"));
        lblNoAntrian = new JLabel("-");
        lblNoAntrian.setFont(Theme.display().deriveFont(32f));
        lblNoAntrian.setForeground(Theme.PRIMARY);
        previewBody.add(lblNoAntrian);
        previewPanel.add(previewBody, BorderLayout.CENTER);

        content.add(previewPanel, BorderLayout.EAST);
        add(content, BorderLayout.CENTER);

        // ---- BUTTON ----
        JButton btnDaftar = new JButton("Daftar & Ambil Nomor");
        btnDaftar.setFont(Theme.bold());
        btnDaftar.setForeground(Color.WHITE);
        btnDaftar.setBackground(Theme.PRIMARY);
        btnDaftar.setFocusPainted(false);
        btnDaftar.setBorder(BorderFactory.createEmptyBorder(Theme.M, Theme.XL, Theme.M, Theme.XL));
        btnDaftar.addActionListener(e -> daftar());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        btnPanel.add(btnDaftar);
        add(btnPanel, BorderLayout.SOUTH);

        updatePreview();
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.caption());
        l.setForeground(Theme.TEXT_SECONDARY);
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(Theme.body());
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(Theme.M, Theme.M, Theme.M, Theme.M)));
    }

    private JPanel categorySelector() {
        JPanel p = new JPanel(new GridLayout(1, 4, Theme.M, Theme.M));
        p.setOpaque(false);
        String[] cats = {"UMUM", "LANSIA", "IBU_HAMIL", "GAWAT_DARURAT"};
        String[] iconKeys = {IconHelper.K_UMUM, IconHelper.K_LANSIA, IconHelper.K_HAMIL, IconHelper.K_DARURAT};
        for (int i = 0; i < cats.length; i++) {
            String c = cats[i];
            JToggleButton b = new JToggleButton(" " + c);
            b.setFont(Theme.body());
            b.setFocusPainted(false);
            b.setBackground(Theme.categoryBg(c));
            b.setForeground(Theme.categoryFg(c));
            b.setIcon(IconHelper.getIcon(iconKeys[i], 16, Theme.categoryFg(c)));
            b.setBorder(BorderFactory.createLineBorder(Theme.categoryFg(c), 1, true));
            if (i == 0) { b.setSelected(true); selectedCategory = c; }
            b.addActionListener(e -> selectCategory(c));
            categoryButtons.put(c, b);
            p.add(b);
        }
        return p;
    }

    private void selectCategory(String c) {
        selectedCategory = c;
        for (Map.Entry<String, JToggleButton> e : categoryButtons.entrySet()) {
            e.getValue().setSelected(e.getKey().equals(c));
        }
        updatePreview();
    }

    private void updatePreview() {
        previewPanel.removeAll();
        JLabel previewTitle = new JLabel("Ringkasan");
        previewTitle.setFont(Theme.h2());
        previewTitle.setForeground(Theme.TEXT_PRIMARY);
        previewPanel.add(previewTitle, BorderLayout.NORTH);

        Pasien dummy = PasienFactory.buat(selectedCategory, "", "1234567890123456", 0, "");
        JPanel previewBody = new JPanel(new GridLayout(6, 1, Theme.M, Theme.M));
        previewBody.setOpaque(false);
        previewBody.add(label("Kategori"));
        previewBody.add(new CategoryBadge(selectedCategory));
        previewBody.add(label("Bobot Prioritas"));
        JLabel b = new JLabel(String.valueOf(dummy.getBobotPrioritas()));
        b.setFont(Theme.h2()); b.setForeground(Theme.TEXT_PRIMARY);
        previewBody.add(b);
        previewBody.add(label("Estimasi Posisi"));
        JLabel pos = new JLabel("-");
        pos.setFont(Theme.h2()); pos.setForeground(Theme.TEXT_PRIMARY);
        previewBody.add(pos);
        previewBody.add(label("Nomor Antrian"));
        JLabel no = new JLabel("-");
        no.setFont(Theme.display().deriveFont(32f)); no.setForeground(Theme.PRIMARY);
        previewBody.add(no);
        previewPanel.add(previewBody, BorderLayout.CENTER);
        previewPanel.revalidate();
        previewPanel.repaint();
    }

    private void daftar() {
        lblError.setText(" ");
        String nama = tfNama.getText().trim();
        String nik = tfNik.getText().trim();
        String usiaStr = tfUsia.getText().trim();
        String keluhan = tfKeluhan.getText().trim();

        if (nama.isEmpty() || nik.isEmpty() || usiaStr.isEmpty()) {
            lblError.setText("Nama, NIK, dan Usia wajib diisi.");
            return;
        }
        int usia;
        try { usia = Integer.parseInt(usiaStr); }
        catch (NumberFormatException ex) {
            lblError.setText("Usia harus berupa angka.");
            return;
        }
        try {
            PasienDAO dao = new PasienDAO();
            String no = dao.generateNoAntrian(selectedCategory);
            Pasien p = PasienFactory.buat(selectedCategory, nama, nik, usia, keluhan);
            p.setNoAntrian(no);
            dao.insert(p);
            ToastNotification t = new ToastNotification("Pasien terdaftar, nomor antrian " + no, ToastNotification.Type.SUCCESS);
            t.show(this);
            reset();
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        } catch (SQLException ex) {
            lblError.setText("Database error: " + ex.getMessage());
        }
    }

    private void reset() {
        tfNama.setText(""); tfNik.setText(""); tfUsia.setText(""); tfKeluhan.setText("");
        selectCategory("UMUM");
        lblError.setText(" ");
    }
}
