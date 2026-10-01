package com.klinik.antrian.view;

import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.components.CategoryBadge;
import java.awt.*;
import javax.swing.*;

/**
 * FormTampilanTV — jendela full-screen untuk ruang tunggu.
 * Menampilkan nomor antrian yang sedang dipanggil dengan font raksasa.
 */
public class FormTampilanTV extends JFrame {

    private final JLabel lblNomor, lblNama, lblKategori, lblKeluhan;
    private final JPanel mainPanel;

    public FormTampilanTV() {
        setTitle("Tampilan Antrian - TV");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        mainPanel = new JPanel(new BorderLayout(Theme.XL, Theme.XL));
        mainPanel.setBackground(Theme.SURFACE);
        mainPanel.setBorder(Theme.empty(Theme.XXL));
        add(mainPanel);

        JLabel title = new JLabel("NOMOR ANTRIAN YANG DIPANGGIL", SwingConstants.CENTER);
        title.setFont(Theme.h1().deriveFont(36f));
        title.setForeground(Theme.TEXT_SECONDARY);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, Theme.M, Theme.M));
        center.setOpaque(false);

        lblNomor = new JLabel("-", SwingConstants.CENTER);
        lblNomor.setFont(Theme.display().deriveFont(120f));
        lblNomor.setForeground(Theme.TEXT_PRIMARY);
        center.add(lblNomor);

        lblNama = new JLabel("Silakan menunggu", SwingConstants.CENTER);
        lblNama.setFont(Theme.h1().deriveFont(48f));
        lblNama.setForeground(Theme.TEXT_PRIMARY);
        center.add(lblNama);

        lblKategori = new JLabel("-", SwingConstants.CENTER);
        lblKategori.setFont(Theme.h2().deriveFont(32f));
        lblKategori.setForeground(Theme.TEXT_SECONDARY);
        center.add(lblKategori);

        mainPanel.add(center, BorderLayout.CENTER);

        lblKeluhan = new JLabel("", SwingConstants.CENTER);
        lblKeluhan.setFont(Theme.body().deriveFont(24f));
        lblKeluhan.setForeground(Theme.TEXT_SECONDARY);
        mainPanel.add(lblKeluhan, BorderLayout.SOUTH);

        JButton btnClose = new JButton("Tutup [Esc]");
        btnClose.setFont(Theme.body());
        btnClose.addActionListener(e -> dispose());
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setOpaque(false);
        p.add(btnClose);
        mainPanel.add(p, BorderLayout.PAGE_END);
    }

    public void update(Pasien p) {
        SwingUtilities.invokeLater(() -> {
            lblNomor.setText(p.getNoAntrian());
            lblNama.setText(p.getNama());
            lblKategori.setText(Theme.categoryLabel(p.getKategori()));
            lblKeluhan.setText(p.getKeluhan());
            mainPanel.setBackground(Theme.categoryBg(p.getKategori()));
        });
    }
}
