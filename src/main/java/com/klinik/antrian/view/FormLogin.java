package com.klinik.antrian.view;

import com.klinik.antrian.dao.RiwayatDAO;
import com.klinik.antrian.model.Petugas;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.components.IconHelper;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;

/**
 * FormLogin — jendela login modern dengan kartu di tengah,
 * inline error, dan tema FlatLaf.
 */
public class FormLogin extends JFrame {

    private final JTextField tfUser;
    private final JPasswordField tfPass;
    private final JLabel lblError;

    public FormLogin() {
        setTitle("Sistem Antrian Prioritas Klinik");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 520);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Theme.BACKGROUND);

        JPanel card = Theme.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(360, 380));

        JLabel icon = new JLabel(IconHelper.of(MaterialDesignH.HOSPITAL_BUILDING, 48, Theme.PRIMARY));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Sistem Antrian Klinik", SwingConstants.CENTER);
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Masuk untuk melanjutkan", SwingConstants.CENTER);
        subtitle.setFont(Theme.body());
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createVerticalStrut(Theme.L));
        card.add(title);
        card.add(Box.createVerticalStrut(Theme.S));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(Theme.XL));

        card.add(label("Username"));
        tfUser = new JTextField();
        styleField(tfUser);
        card.add(tfUser);
        card.add(Box.createVerticalStrut(Theme.M));

        card.add(label("Password"));
        tfPass = new JPasswordField();
        styleField(tfPass);
        card.add(tfPass);
        card.add(Box.createVerticalStrut(Theme.S));

        lblError = new JLabel(" ");
        lblError.setFont(Theme.caption());
        lblError.setForeground(Theme.DANGER);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblError);
        card.add(Box.createVerticalStrut(Theme.M));

        JButton btnLogin = new JButton("Masuk ke Sistem");
        btnLogin.setFont(Theme.bold());
        btnLogin.setBackground(Theme.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.addActionListener(e -> login());
        tfPass.addActionListener(e -> login());
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(Theme.L));

        JLabel demo = new JLabel("Demo: admin/admin123 | loket/loket123 | poli/poli123");
        demo.setFont(Theme.caption());
        demo.setForeground(Theme.TEXT_SECONDARY);
        demo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(demo);

        add(card, new GridBagConstraints());
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.caption());
        l.setForeground(Theme.TEXT_SECONDARY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(Theme.body());
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(Theme.M, Theme.M, Theme.M, Theme.M)));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void login() {
        lblError.setText(" ");
        String user = tfUser.getText().trim();
        String pass = new String(tfPass.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Username dan password wajib diisi.");
            return;
        }
        try {
            RiwayatDAO dao = new RiwayatDAO();
            Petugas petugas = dao.login(user, pass);
            if (petugas == null) {
                lblError.setText("Username atau password salah.");
                return;
            }
            dispose();
            new MainFrame(petugas.getRole(), petugas.getUsername()).setVisible(true);
        } catch (SQLException ex) {
            lblError.setText("Koneksi database gagal. Pastikan MySQL aktif.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}
