package com.klinik.antrian.view;

import com.klinik.antrian.model.Petugas;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.SidebarButton;
import com.klinik.antrian.view.panels.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;

/**
 * MainFrame — jendela utama aplikasi dengan sidebar + CardLayout.
 *
 * Semua halaman (Beranda, Pendaftaran, Antrian, Panggilan, Riwayat, Laporan)
 * menjadi "card" di dalam content area; tidak membuka JFrame baru.
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private final JPanel contentArea;
    private final Map<String, SidebarButton> navButtons = new HashMap<>();
    private final Map<String, JPanel> panels = new HashMap<>();
    private final Petugas.Role role;
    private final String username;

    private SidebarButton activeButton;

    public MainFrame(Petugas.Role role, String username) {
        this.role = role;
        this.username = username;

        setTitle("Sistem Antrian Prioritas Klinik");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildTopbar(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(contentArea = buildContentArea(), BorderLayout.CENTER);

        switchTo("BERANDA");
    }

    private JPanel buildTopbar() {
        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(Theme.SURFACE);
        topbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));

        JLabel logo = new JLabel("Klinik Sehat Bersama");
        logo.setFont(Theme.h2());
        logo.setIcon(IconHelper.of(MaterialDesignH.HOSPITAL_BUILDING, 24, Theme.PRIMARY));
        logo.setForeground(Theme.TEXT_PRIMARY);
        logo.setBorder(Theme.empty(Theme.L));
        topbar.add(logo, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.L, Theme.M));
        right.setOpaque(false);
        JLabel user = new JLabel(username + " (" + role + ")");
        user.setFont(Theme.body());
        user.setIcon(IconHelper.of(MaterialDesignA.ACCOUNT, 18, Theme.TEXT_SECONDARY));
        user.setForeground(Theme.TEXT_SECONDARY);
        right.add(user);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(Theme.body());
        btnLogout.setIcon(IconHelper.of(MaterialDesignL.LOGOUT, 18, Theme.TEXT_SECONDARY));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> logout());
        right.add(btnLogout);
        topbar.add(right, BorderLayout.EAST);
        return topbar;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.SURFACE);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER));
        sidebar.setPreferredSize(new Dimension(220, 0));

        sidebar.add(Box.createVerticalStrut(Theme.XL));

        addNav(sidebar, "BERANDA", IconHelper.K_HOME, "Beranda", true);
        addNav(sidebar, "PENDAFTARAN", IconHelper.K_DAFTAR, "Daftar", role == Petugas.Role.LOKET || role == Petugas.Role.ADMIN);
        addNav(sidebar, "ANTRIAN", IconHelper.K_ANTRIAN, "Antrian", role == Petugas.Role.LOKET || role == Petugas.Role.ADMIN);
        addNav(sidebar, "PANGGILAN", IconHelper.K_PANGGIL, "Panggil", role == Petugas.Role.POLI || role == Petugas.Role.ADMIN);
        addNav(sidebar, "RIWAYAT", IconHelper.K_RIWAYAT, "Riwayat", role == Petugas.Role.POLI || role == Petugas.Role.ADMIN);
        addNav(sidebar, "LAPORAN", IconHelper.K_LAPORAN, "Laporan", role == Petugas.Role.ADMIN);

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private void addNav(JPanel sidebar, String key, String icon, String label, boolean visible) {
        if (!visible) return;
        SidebarButton btn = new SidebarButton(icon, label);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.addActionListener(e -> switchTo(key));
        navButtons.put(key, btn);
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(Theme.S));
    }

    private JPanel buildContentArea() {
        cardLayout = new CardLayout();
        JPanel area = new JPanel(cardLayout);
        area.setBackground(Theme.BACKGROUND);

        panels.put("BERANDA", new BerandaPanel(this));
        panels.put("PENDAFTARAN", new PendaftaranPanel());
        panels.put("ANTRIAN", new AntrianPanel());
        panels.put("PANGGILAN", new PanggilanPanel());
        panels.put("RIWAYAT", new RiwayatPanel());
        panels.put("LAPORAN", new LaporanPanel());

        for (Map.Entry<String, JPanel> e : panels.entrySet())
            area.add(e.getValue(), e.getKey());
        return area;
    }

    public void switchTo(String key) {
        cardLayout.show(contentArea, key);
        JPanel panel = panels.get(key);
        if (panel instanceof Refreshable r) r.refresh();

        for (Map.Entry<String, SidebarButton> e : navButtons.entrySet())
            e.getValue().setActive(e.getKey().equals(key));
        activeButton = navButtons.get(key);
        if (activeButton != null) activeButton.setActive(true);
    }

    private void logout() {
        dispose();
        new FormLogin().setVisible(true);
    }
}
