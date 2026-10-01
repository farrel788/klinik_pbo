package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.dao.RiwayatDAO;
import com.klinik.antrian.model.Pasien;
import com.klinik.antrian.util.AntrianPrioritasKlinik;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.FormTampilanTV;
import com.klinik.antrian.view.Refreshable;
import com.klinik.antrian.view.components.CategoryBadge;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

/**
 * PanggilanPanel — dashboard poli untuk memanggil pasien berikutnya.
 */
public class PanggilanPanel extends JPanel implements Refreshable {

    private final JPanel currentPanel;
    private final JLabel lblNomor, lblNama, lblKategori, lblKeluhan;
    private final JPanel waitingList;
    private final AntrianPrioritasKlinik antrian;
    private Pasien current;
    private FormTampilanTV tv;

    public PanggilanPanel() {
        antrian = new AntrianPrioritasKlinik();
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Pemanggilan Pasien");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(520);
        split.setOpaque(false);
        split.setBorder(null);

        // ---- PANEL SEDANG DIPANGGIL ----
        currentPanel = new JPanel(new BorderLayout(Theme.XL, Theme.XL));
        currentPanel.setBackground(Theme.SURFACE);
        currentPanel.setBorder(Theme.cardBorder());

        JLabel sub = new JLabel("SEDANG DIPANGGIL", SwingConstants.CENTER);
        sub.setFont(Theme.bold());
        sub.setForeground(Theme.TEXT_SECONDARY);
        currentPanel.add(sub, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, Theme.M, Theme.M));
        center.setOpaque(false);
        lblNomor = new JLabel("-", SwingConstants.CENTER);
        lblNomor.setFont(Theme.display());
        lblNomor.setForeground(Theme.TEXT_PRIMARY);
        center.add(lblNomor);

        lblNama = new JLabel("Tidak ada pasien", SwingConstants.CENTER);
        lblNama.setFont(Theme.h1());
        lblNama.setForeground(Theme.TEXT_PRIMARY);
        center.add(lblNama);

        lblKategori = new JLabel("-", SwingConstants.CENTER);
        lblKategori.setFont(Theme.h2());
        lblKategori.setForeground(Theme.TEXT_SECONDARY);
        center.add(lblKategori);

        lblKeluhan = new JLabel("-", SwingConstants.CENTER);
        lblKeluhan.setFont(Theme.body());
        lblKeluhan.setForeground(Theme.TEXT_SECONDARY);
        center.add(lblKeluhan);
        currentPanel.add(center, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, Theme.M, Theme.M));
        btnPanel.setOpaque(false);
        JButton btnPanggil = new JButton("Panggil Berikutnya");
        btnPanggil.setFont(Theme.bold());
        btnPanggil.setIcon(IconHelper.getIcon(IconHelper.K_PANGGIL, 20, Color.WHITE));
        btnPanggil.setBackground(Theme.PRIMARY);
        btnPanggil.setForeground(Color.WHITE);
        btnPanggil.setFocusPainted(false);
        btnPanggil.addActionListener(e -> panggil());

        JButton btnSkip = new JButton("Skip");
        styleSecondary(btnSkip);
        btnSkip.setIcon(IconHelper.getIcon(IconHelper.K_SKIP, 18, Theme.TEXT_PRIMARY));
        btnSkip.addActionListener(e -> skip());

        JButton btnSelesai = new JButton("Selesai");
        styleSecondary(btnSelesai);
        btnSelesai.setIcon(IconHelper.getIcon(IconHelper.K_SELESAI, 18, Theme.TEXT_PRIMARY));
        btnSelesai.addActionListener(e -> selesai());

        btnPanel.add(btnPanggil); btnPanel.add(btnSkip); btnPanel.add(btnSelesai);

        JButton btnTV = new JButton("Buka Tampilan TV");
        btnTV.setIcon(IconHelper.getIcon(IconHelper.K_TV, 18, Theme.TEXT_PRIMARY));
        styleSecondary(btnTV);
        btnTV.addActionListener(e -> bukaTV());
        JPanel tvPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tvPanel.setOpaque(false);
        tvPanel.add(btnTV);

        JPanel southPanel = new JPanel(new BorderLayout(Theme.M, Theme.M));
        southPanel.setOpaque(false);
        southPanel.add(btnPanel, BorderLayout.CENTER);
        southPanel.add(tvPanel, BorderLayout.SOUTH);
        currentPanel.add(southPanel, BorderLayout.SOUTH);

        split.setLeftComponent(currentPanel);

        // ---- PANEL MENUNGGU ----
        JPanel right = new JPanel(new BorderLayout(Theme.L, Theme.L));
        right.setOpaque(false);
        JLabel waitingTitle = new JLabel("Menunggu");
        waitingTitle.setFont(Theme.h2());
        waitingTitle.setForeground(Theme.TEXT_PRIMARY);
        right.add(waitingTitle, BorderLayout.NORTH);

        waitingList = new JPanel();
        waitingList.setLayout(new BoxLayout(waitingList, BoxLayout.Y_AXIS));
        waitingList.setOpaque(false);
        right.add(new JScrollPane(waitingList), BorderLayout.CENTER);
        split.setRightComponent(right);

        add(split, BorderLayout.CENTER);
        refresh();
    }

    private void styleSecondary(JButton b) {
        b.setFont(Theme.body());
        b.setBackground(Theme.SURFACE);
        b.setForeground(Theme.TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1, true));
    }

    public void refresh() {
        antrian.clear();
        try {
            for (Pasien p : new PasienDAO().getAntrianMenunggu()) antrian.tambahAntrian(p);
            renderWaitingList();
            if (current == null && antrian.isEmpty()) {
                lblNomor.setText("-");
                lblNama.setText("Tidak ada pasien menunggu");
                lblKategori.setText("-");
                lblKeluhan.setText("-");
                currentPanel.setBackground(Theme.SURFACE);
            }
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal memuat antrian: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
    }

    private void renderWaitingList() {
        waitingList.removeAll();
        List<Pasien> list = antrian.getDaftarAntrian();
        if (list.isEmpty()) {
            JLabel empty = new JLabel("Tidak ada pasien menunggu");
            empty.setFont(Theme.body());
            empty.setForeground(Theme.TEXT_SECONDARY);
            waitingList.add(empty);
        }
        for (Pasien p : list) {
            JPanel card = new JPanel(new BorderLayout(Theme.M, Theme.M));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            card.setBackground(Theme.SURFACE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                    BorderFactory.createEmptyBorder(Theme.M, Theme.M, Theme.M, Theme.M)));

            JLabel no = new JLabel("<html><b>" + p.getNoAntrian() + "</b> " + p.getNama() + "</html>");
            no.setFont(Theme.body());
            card.add(no, BorderLayout.CENTER);
            card.add(new CategoryBadge(p.getKategori()), BorderLayout.EAST);
            waitingList.add(card);
            waitingList.add(Box.createRigidArea(new Dimension(0, Theme.M)));
        }
        waitingList.revalidate();
        waitingList.repaint();
    }

    private void panggil() {
        if (antrian.isEmpty()) {
            ToastNotification t = new ToastNotification("Antrian kosong!", ToastNotification.Type.INFO);
            t.show(this);
            return;
        }
        current = antrian.panggilBerikutnya();
        lblNomor.setText(current.getNoAntrian());
        lblNama.setText(current.getNama());
        lblKategori.setText(Theme.categoryLabel(current.getKategori()));
        lblKeluhan.setText(current.getKeluhan());
        currentPanel.setBackground(Theme.categoryBg(current.getKategori()));

        try { new PasienDAO().updateStatus(current.getId(), "DIPANGGIL"); }
        catch (SQLException ignored) {}

        Toolkit.getDefaultToolkit().beep();
        if (tv != null) tv.update(current);
        refresh();
    }

    private void skip() {
        if (current == null) return;
        try { new PasienDAO().updateStatus(current.getId(), "SKIP"); }
        catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal skip: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
        current = null;
        currentPanel.setBackground(Theme.SURFACE);
        lblNomor.setText("-"); lblNama.setText("Pasien dilewatkan");
        lblKategori.setText("-"); lblKeluhan.setText("-");
        refresh();
    }

    private void selesai() {
        if (current == null) return;
        try {
            new PasienDAO().updateStatus(current.getId(), "SELESAI");
            new RiwayatDAO().catatRiwayat(current.getId(), 1, "Selesai diperiksa");
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal menyelesaikan: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
        current = null;
        currentPanel.setBackground(Theme.SURFACE);
        lblNomor.setText("-"); lblNama.setText("Selesai dilayani");
        lblKategori.setText("-"); lblKeluhan.setText("-");
        refresh();
    }

    private void bukaTV() {
        if (tv == null || !tv.isVisible()) {
            tv = new FormTampilanTV();
            tv.setVisible(true);
        }
        if (current != null) tv.update(current);
    }
}
