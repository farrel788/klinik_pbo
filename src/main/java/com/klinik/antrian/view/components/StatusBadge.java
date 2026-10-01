package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.*;
import javax.swing.*;

/**
 * StatusBadge — label pill untuk status pasien (MENUNGGU/DIPANGGIL/SELESAI/SKIP).
 */
public class StatusBadge extends JLabel {

    public StatusBadge(String status) {
        super(Theme.statusLabel(status));
        setOpaque(true);
        setFont(Theme.bold());
        setForeground(Theme.statusFg(status));
        setBackground(Theme.statusBg(status));
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(Theme.S, Theme.M, Theme.S, Theme.M));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.RADIUS, Theme.RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}
