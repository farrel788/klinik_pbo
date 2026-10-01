package com.klinik.antrian.view.components;

import com.klinik.antrian.util.Theme;
import java.awt.Color;
import javax.swing.Icon;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.*;
import org.kordamp.ikonli.swing.FontIcon;

/**
 * Helper untuk memuat ikon vektor dari Ikonli.
 * Semua ikon di aplikasi melalui sini — konsisten & mudah diganti.
 */
public final class IconHelper {

    private IconHelper() {}

    /** Membuat FontIcon dengan ukuran & warna. */
    public static Icon of(Ikon ikon, int size, Color color) {
        return FontIcon.of(ikon, size, color);
    }

    /* ===================== MENU / NAVIGASI ===================== */
    public static final String K_HOME    = "home";
    public static final String K_DAFTAR  = "daftar";
    public static final String K_ANTRIAN = "antrian";
    public static final String K_PANGGIL = "panggil";
    public static final String K_RIWAYAT = "riwayat";
    public static final String K_LAPORAN = "laporan";
    public static final String K_LOGOUT  = "logout";
    public static final String K_REFRESH = "refresh";
    public static final String K_TV      = "tv";
    public static final String K_USER    = "user";
    public static final String K_KLINIK  = "klinik";
    public static final String K_SKIP    = "skip";
    public static final String K_SELESAI = "selesai";

    /* ===================== KATEGORI ===================== */
    public static final String K_DARURAT = "darurat";
    public static final String K_LANSIA  = "lansia";
    public static final String K_HAMIL   = "hamil";
    public static final String K_UMUM    = "umum";

    /** Resolve ikon dari String key. Fallback ke null bila tidak dikenal. */
    public static Icon getIcon(String key, int size, Color color) {
        if (key == null) return null;
        return switch (key) {
            case K_HOME    -> of(MaterialDesignH.HOME, size, color);
            case K_DAFTAR  -> of(MaterialDesignA.ACCOUNT_PLUS, size, color);
            case K_ANTRIAN -> of(MaterialDesignF.FORMAT_LIST_NUMBERED, size, color);
            case K_PANGGIL -> of(MaterialDesignB.BULLHORN, size, color);
            case K_RIWAYAT -> of(MaterialDesignH.HISTORY, size, color);
            case K_LAPORAN -> of(MaterialDesignC.CHART_BAR, size, color);
            case K_LOGOUT  -> of(MaterialDesignL.LOGOUT, size, color);
            case K_REFRESH -> of(MaterialDesignR.REFRESH, size, color);
            case K_TV      -> of(MaterialDesignT.TELEVISION, size, color);
            case K_USER    -> of(MaterialDesignA.ACCOUNT, size, color);
            case K_KLINIK  -> of(MaterialDesignH.HOSPITAL_BUILDING, size, color);
            case K_SKIP    -> of(MaterialDesignS.SKIP_NEXT, size, color);
            case K_SELESAI -> of(MaterialDesignC.CHECK_CIRCLE, size, color);
            case K_DARURAT -> of(MaterialDesignA.ALERT, size, color);
            case K_LANSIA  -> of(MaterialDesignW.WALK, size, color);
            case K_HAMIL   -> of(MaterialDesignH.HUMAN_PREGNANT, size, color);
            case K_UMUM    -> of(MaterialDesignA.ACCOUNT, size, color);
            default -> null;
        };
    }

    /** Ikon kategori berdasarkan string kategori pasien. */
    public static Icon categoryIcon(String kategori, int size, Color color) {
        return switch (kategori) {
            case "GAWAT_DARURAT" -> getIcon(K_DARURAT, size, color);
            case "LANSIA"        -> getIcon(K_LANSIA, size, color);
            case "IBU_HAMIL"     -> getIcon(K_HAMIL, size, color);
            default              -> getIcon(K_UMUM, size, color);
        };
    }
}
