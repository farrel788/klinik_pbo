package com.klinik.antrian.view.panels;

import com.klinik.antrian.dao.PasienDAO;
import com.klinik.antrian.util.Theme;
import com.klinik.antrian.view.Refreshable;
import com.klinik.antrian.view.components.IconHelper;
import com.klinik.antrian.view.components.SummaryCard;
import com.klinik.antrian.view.components.ToastNotification;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * LaporanPanel — statistik pasien per kategori dengan JFreeChart.
 */
public class LaporanPanel extends JPanel implements Refreshable {

    private final SummaryCard cardTotal, cardTop;
    private final DefaultCategoryDataset dataset;

    public LaporanPanel() {
        setLayout(new BorderLayout(Theme.L, Theme.L));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.empty(Theme.XL));

        JLabel title = new JLabel("Laporan Statistik Pasien");
        title.setFont(Theme.h1());
        title.setForeground(Theme.TEXT_PRIMARY);
        add(title, BorderLayout.NORTH);

        // cards
        JPanel cards = new JPanel(new GridLayout(1, 2, Theme.L, Theme.L));
        cards.setOpaque(false);
        cardTotal = new SummaryCard(IconHelper.K_LAPORAN, "Total Pasien", "0", Theme.PRIMARY);
        cardTop = new SummaryCard(IconHelper.K_SELESAI, "Kategori Terbanyak", "-", Theme.SUCCESS);
        cards.add(cardTotal); cards.add(cardTop);
        add(cards, BorderLayout.NORTH);

        dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Jumlah Pasien per Kategori",
                "Kategori", "Jumlah",
                dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Theme.SURFACE);
        plot.setRangeGridlinePaint(Theme.BORDER);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Theme.PRIMARY);
        renderer.setSeriesPaint(1, Theme.WARNING);
        renderer.setSeriesPaint(2, Theme.INFO);
        renderer.setSeriesPaint(3, Theme.DANGER);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Theme.SURFACE);
        add(chartPanel, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setIcon(IconHelper.getIcon(IconHelper.K_REFRESH, 18, Theme.TEXT_PRIMARY));
        btnRefresh.setFont(Theme.body());
        btnRefresh.addActionListener(e -> refresh());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        dataset.clear();
        try {
            PasienDAO dao = new PasienDAO();
            String[] cats = {"UMUM", "LANSIA", "IBU_HAMIL", "GAWAT_DARURAT"};
            int total = 0, max = -1;
            String top = "-";
            for (String c : cats) {
                int n = dao.hitungPerKategori(c);
                dataset.addValue(n, "Pasien", Theme.categoryLabel(c));
                total += n;
                if (n > max) { max = n; top = Theme.categoryLabel(c); }
            }
            cardTotal.setValue(String.valueOf(total));
            cardTop.setValue(top);
        } catch (SQLException ex) {
            ToastNotification t = new ToastNotification("Gagal memuat laporan: " + ex.getMessage(), ToastNotification.Type.ERROR);
            t.show(this);
        }
    }
}
