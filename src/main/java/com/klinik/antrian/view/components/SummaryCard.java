package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.*;
import javax.swing.*;

/**
 * SummaryCard — kartu ringkasan statistik (icon, label, angka).
 * Dipakai di Dashboard Loket dan Laporan.
 */
public class SummaryCard extends JPanel {

    private final JLabel lblValue;

    public SummaryCard(String iconKey, String label, String value, Color accent) {
        super(new BorderLayout(Theme.M, Theme.S));
        setOpaque(false);
        setBackground(Theme.SURFACE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(Theme.L, Theme.L, Theme.L, Theme.L)));

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(IconHelper.getIcon(iconKey, 28, accent));

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(Theme.caption());
        lblLabel.setForeground(Theme.TEXT_SECONDARY);

        lblValue = new JLabel(value);
        lblValue.setFont(Theme.display().deriveFont(28f));
        lblValue.setForeground(Theme.TEXT_PRIMARY);

        add(iconLabel, BorderLayout.WEST);
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(lblValue);
        textPanel.add(lblLabel);
        add(textPanel, BorderLayout.CENTER);
    }

    public void setValue(String value) {
        lblValue.setText(value);
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
