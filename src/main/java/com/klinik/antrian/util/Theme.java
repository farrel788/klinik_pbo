package com.klinik.antrian.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Theme — kumpulan token warna, font, spacing, dan helper UI
 * untuk redesign "Clinical Calm" pada project Sistem Antrian Klinik.
 *
 * Semua komponen GUI mengacu ke konstanta ini agar konsisten.
 */
public final class Theme {

    private Theme() {}

    /* ===================== PALET WARNA ===================== */
    public static final Color PRIMARY       = new Color(0x2563EB);
    public static final Color PRIMARY_DARK  = new Color(0x1D4ED8);
    public static final Color SURFACE       = Color.WHITE;
    public static final Color BACKGROUND    = new Color(0xF3F5F9);
    public static final Color TEXT_PRIMARY  = new Color(0x1E2430);
    public static final Color TEXT_SECONDARY= new Color(0x64748B);
    public static final Color BORDER        = new Color(0xE2E8F0);

    public static final Color DANGER        = new Color(0xDC2626);
    public static final Color DANGER_BG     = new Color(0xFEE2E2);
    public static final Color WARNING       = new Color(0xD97706);
    public static final Color WARNING_BG    = new Color(0xFEF3C7);
    public static final Color INFO          = new Color(0x7C3AED);
    public static final Color INFO_BG       = new Color(0xEDE9FE);
    public static final Color NEUTRAL       = new Color(0x475569);
    public static final Color NEUTRAL_BG    = new Color(0xF1F5F9);
    public static final Color SUCCESS       = new Color(0x16A34A);
    public static final Color SUCCESS_BG    = new Color(0xDCFCE7);

    public static final Color SIDEBAR_BG    = new Color(0xFFFFFF);
    public static final Color SIDEBAR_ACTIVE= new Color(0xEFF6FF);

    /* ===================== TIPOGRAFI ===================== */
    public static Font display() { return derive("Inter", Font.BOLD, 48); }
    public static Font h1()      { return derive("Inter", Font.BOLD, 22); }
    public static Font h2()      { return derive("Inter", Font.BOLD, 16); }
    public static Font body()    { return derive("Inter", Font.PLAIN, 14); }
    public static Font caption() { return derive("Inter", Font.PLAIN, 12); }
    public static Font bold()    { return derive("Inter", Font.BOLD, 14); }

    private static Font derive(String preferred, int style, int size) {
        if (isFontAvailable(preferred)) return new Font(preferred, style, size);
        Font segoe = new Font("Segoe UI", style, size);
        if (segoe.getFamily().equals("Segoe UI")) return segoe;
        return new Font(Font.SANS_SERIF, style, size);
    }

    private static boolean isFontAvailable(String name) {
        for (String f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
            if (f.equalsIgnoreCase(name)) return true;
        return false;
    }

    /* ===================== SPACING & BORDER ===================== */
    public static final int S = 4;
    public static final int M = 8;
    public static final int L = 16;
    public static final int XL = 24;
    public static final int XXL = 32;

    public static final int RADIUS = 12;

    public static Border cardBorder() {
        return new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(L, L, L, L));
    }

    public static Border empty(int all) {
        return BorderFactory.createEmptyBorder(all, all, all, all);
    }

    public static Border empty(int v, int h) {
        return BorderFactory.createEmptyBorder(v, h, v, h);
    }

    /* ===================== WARNA KATEGORI ===================== */
    public static Color categoryFg(String kategori) {
        return switch (kategori) {
            case "GAWAT_DARURAT" -> DANGER;
            case "LANSIA"        -> WARNING;
            case "IBU_HAMIL"     -> INFO;
            default              -> NEUTRAL;
        };
    }

    public static Color categoryBg(String kategori) {
        return switch (kategori) {
            case "GAWAT_DARURAT" -> DANGER_BG;
            case "LANSIA"        -> WARNING_BG;
            case "IBU_HAMIL"     -> INFO_BG;
            default              -> NEUTRAL_BG;
        };
    }

    public static String categoryIcon(String kategori) {
        return switch (kategori) {
            case "GAWAT_DARURAT" -> "⚠";
            case "LANSIA"        -> "🚶";
            case "IBU_HAMIL"     -> "🤰";
            default              -> "👤";
        };
    }

    public static String categoryLabel(String kategori) {
        return switch (kategori) {
            case "GAWAT_DARURAT" -> "GAWAT DARURAT";
            case "IBU_HAMIL"     -> "IBU HAMIL";
            default              -> kategori;
        };
    }

    /* ===================== WARNA STATUS ===================== */
    public static Color statusFg(String status) {
        return switch (status) {
            case "SELESAI"   -> SUCCESS;
            case "DIPANGGIL" -> PRIMARY;
            case "SKIP"      -> TEXT_SECONDARY;
            default          -> WARNING;
        };
    }

    public static Color statusBg(String status) {
        return switch (status) {
            case "SELESAI"   -> SUCCESS_BG;
            case "DIPANGGIL" -> new Color(0xDBEAFE);
            case "SKIP"      -> NEUTRAL_BG;
            default          -> WARNING_BG;
        };
    }

    public static String statusLabel(String status) {
        if (status == null) return "MENUNGGU";
        return switch (status) {
            case "MENUNGGU"  -> "MENUNGGU";
            case "DIPANGGIL" -> "DIPANGGIL";
            case "SELESAI"   -> "SELESAI";
            case "SKIP"      -> "SKIP";
            default          -> status;
        };
    }

    /* ===================== HELPER PANEL CARD ===================== */
    public static javax.swing.JPanel card() {
        javax.swing.JPanel p = new javax.swing.JPanel();
        p.setBackground(SURFACE);
        p.setBorder(cardBorder());
        return p;
    }
}
