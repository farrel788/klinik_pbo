package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.*;
import javax.swing.*;

/**
 * SidebarButton — tombol menu di sidebar kiri MainFrame.
 * Ada state aktif (dengan indicator garis kiri + bg biru muda) dan non-aktif.
 */
public class SidebarButton extends JButton {

    private boolean active = false;

    public SidebarButton(String iconKey, String label) {
        setText("  " + label);
        setFont(Theme.body());
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setHorizontalAlignment(LEFT);
        setBorder(BorderFactory.createEmptyBorder(Theme.M, Theme.L, Theme.M, Theme.L));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setForeground(Theme.TEXT_PRIMARY);
        setBackground(Theme.SIDEBAR_BG);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        updateIcon(iconKey, false);
    }

    public void setActive(boolean active) {
        this.active = active;
        setBackground(active ? Theme.SIDEBAR_ACTIVE : Theme.SIDEBAR_BG);
        setForeground(active ? Theme.PRIMARY : Theme.TEXT_PRIMARY);
        setFont(active ? Theme.bold() : Theme.body());
        repaint();
    }

    /** Set icon key externally (used when creating from MainFrame). */
    public void setIconKey(String iconKey) {
        updateIcon(iconKey, active);
    }

    public boolean isActive() { return active; }

    public void updateIcon(String iconKey, boolean activeState) {
        Icon ikon = IconHelper.getIcon(iconKey, 20,
                activeState ? Theme.PRIMARY : Theme.TEXT_SECONDARY);
        setIcon(ikon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(Theme.S, Theme.S / 2, getWidth() - Theme.M, getHeight() - Theme.S, Theme.RADIUS, Theme.RADIUS);
        if (active) {
            g2.setColor(Theme.PRIMARY);
            g2.fillRoundRect(Theme.S, Theme.S / 2 + 6, 4, getHeight() - Theme.S - 12, 2, 2);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
