package com.klinik.antrian;

import com.formdev.flatlaf.FlatLightLaf;
import com.klinik.antrian.view.FormLogin;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                UIManager.put("Button.arc", 12);
                UIManager.put("Component.arc", 12);
                UIManager.put("TextComponent.arc", 8);
                UIManager.put("Panel.arc", 12);
            } catch (Exception ex) {
                System.err.println("Gagal set FlatLaf: " + ex.getMessage());
            }
            new FormLogin().setVisible(true);
        });
    }
}
