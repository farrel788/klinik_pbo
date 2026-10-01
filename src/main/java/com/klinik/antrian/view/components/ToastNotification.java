package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * ToastNotification — notifikasi non-modal di pojok kanan atas content area.
 * Auto-hilang setelah ±3 detik. Mengganti sebagian JOptionPane.
 */
public class ToastNotification extends JPanel {

    public enum Type { SUCCESS, ERROR, INFO }

    private Timer timer;
    private int alpha = 255;

    public ToastNotification(String message, Type type) {
        setLayout(new BorderLayout());
        setOpaque(false);

        Color bg = switch (type) {
            case SUCCESS -> Theme.SUCCESS_BG;
            case ERROR   -> Theme.DANGER_BG;
            case INFO    -> new Color(0xDBEAFE);
        };
        Color fg = switch (type) {
            case SUCCESS -> Theme.SUCCESS;
            case ERROR   -> Theme.DANGER;
            case INFO    -> Theme.PRIMARY;
        };
        String icon = switch (type) {
            case SUCCESS -> "✔";
            case ERROR   -> "✕";
            case INFO    -> "ℹ";
        };

        JLabel label = new JLabel(icon + " " + message);
        label.setFont(Theme.body());
        label.setForeground(fg);
        label.setBorder(BorderFactory.createEmptyBorder(Theme.M, Theme.L, Theme.M, Theme.L));
        add(label, BorderLayout.CENTER);

        setBackground(bg);
        setBorder(new CompoundBorder(
                new LineBorder(fg, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        timer = new Timer(50, e -> {
            alpha -= 8;
            if (alpha <= 0) {
                alpha = 0;
                timer.stop();
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(ToastNotification.this);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
        timer.setInitialDelay(3000);
    }

    public void show(JLayeredPane layeredPane) {
        Dimension size = getPreferredSize();
        setBounds(layeredPane.getWidth() - size.width - Theme.XL, Theme.XL, size.width, size.height);
        layeredPane.add(this, JLayeredPane.POPUP_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
        timer.start();
    }

    public void show(JPanel parent) {
        // fallback: show as floating panel on top-right
        setLayout(new BorderLayout());
        parent.setLayout(new BorderLayout());
        JPanel east = new JPanel(new BorderLayout());
        east.setOpaque(false);
        east.add(this, BorderLayout.NORTH);
        parent.add(east, BorderLayout.EAST);
        parent.revalidate();
        parent.repaint();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), alpha));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.RADIUS, Theme.RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}
