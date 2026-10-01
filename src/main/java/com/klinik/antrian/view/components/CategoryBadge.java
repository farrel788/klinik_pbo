package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.*;
import javax.swing.*;

/**
 * CategoryBadge — label berbentuk pill/rounded dengan warna sesuai kategori pasien.
 * Dipakai di tabel, kartu antrian, ringkasan, dan tampilan TV.
 */
public class CategoryBadge extends JLabel {

    public CategoryBadge(String kategori) {
        super(Theme.categoryLabel(kategori));
        setIcon(IconHelper.categoryIcon(kategori, 14, Theme.categoryFg(kategori)));
        setOpaque(true);
        setFont(Theme.bold());
        setForeground(Theme.categoryFg(kategori));
        setBackground(Theme.categoryBg(kategori));
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
