package Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class SimpleLineChart extends JPanel {
    private Map<String, ? extends Number> data;
    private String title;
    private Color lineColor;
    private Color backgroundColor;
    private Color gridColor;
    
    public SimpleLineChart(Map<String, ? extends Number> data, String title) {
        this.data = data;
        this.title = title;
        this.lineColor = new Color(52, 152, 219); // Azul moderno
        this.backgroundColor = Color.WHITE;
        this.gridColor = new Color(240, 240, 240);
        
        setPreferredSize(new Dimension(320, 200));
        setMinimumSize(new Dimension(280, 170));
        setBackground(backgroundColor);
        setOpaque(true);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    public void setLineColor(Color color) {
        this.lineColor = color;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        try {
            // Configurar renderizado suave
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int width = getWidth();
            int height = getHeight();
            
            // Dibujar fondo con gradiente sutil
            GradientPaint gradient = new GradientPaint(0, 0, backgroundColor, 0, height, new Color(248, 249, 250));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
            
            if (data == null || data.isEmpty()) {
                drawNoDataMessage(g2d, width, height);
                return;
            }
            
            // Dibujar título con estilo moderno
            drawTitle(g2d, width);
            
            // Configurar área de dibujo
            int margin = 50;
            int chartWidth = width - 2 * margin;
            int chartHeight = height - 100;
            int chartX = margin;
            int chartY = 60;
            
            // Dibujar grid de fondo
            drawGrid(g2d, chartX, chartY, chartWidth, chartHeight);
            
            // Dibujar el gráfico de línea
            drawLineChart(g2d, chartX, chartY, chartWidth, chartHeight);
            
        } finally {
            g2d.dispose();
        }
    }
    
    private void drawNoDataMessage(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(150, 150, 150));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        String message = "📊 No hay datos disponibles";
        FontMetrics fm = g2d.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        g2d.drawString(message, (width - messageWidth) / 2, height / 2);
    }
    
    private void drawTitle(Graphics2D g2d, int width) {
        g2d.setColor(new Color(44, 62, 80));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 25);
    }
    
    private void drawGrid(Graphics2D g2d, int chartX, int chartY, int chartWidth, int chartHeight) {
        g2d.setColor(gridColor);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Líneas horizontales
        for (int i = 0; i <= 4; i++) {
            int y = chartY + (i * chartHeight / 4);
            g2d.drawLine(chartX, y, chartX + chartWidth, y);
        }
        
        // Líneas verticales
        if (data.size() > 1) {
            for (int i = 0; i <= data.size() - 1; i++) {
                int x = chartX + (i * chartWidth) / (data.size() - 1);
                g2d.drawLine(x, chartY, x, chartY + chartHeight);
            }
        }
    }
    
    private void drawLineChart(Graphics2D g2d, int chartX, int chartY, int chartWidth, int chartHeight) {
        // Encontrar valores máximo y mínimo
        double maxValue = data.values().stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .orElse(1.0);
        
        double minValue = data.values().stream()
                .mapToDouble(Number::doubleValue)
                .min()
                .orElse(0.0);
        
        if (maxValue == minValue) maxValue = minValue + 1;
        
        // Crear puntos
        List<Point> points = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            double value = entry.getValue().doubleValue();
            
            int x = data.size() == 1 ? chartX + chartWidth / 2 : 
                    chartX + (i * chartWidth) / (data.size() - 1);
            int y = chartY + chartHeight - (int) (((value - minValue) / (maxValue - minValue)) * chartHeight);
            
            points.add(new Point(x, y));
            i++;
        }
        
        // Dibujar línea con sombra
        if (points.size() > 1) {
            drawLineWithShadow(g2d, points);
        }
        
        // Dibujar puntos con estilo moderno
        drawModernPoints(g2d, points);
        
        // Dibujar etiquetas
        drawLabels(g2d, chartX, chartY, chartWidth, chartHeight, points);
    }
    
    private void drawLineWithShadow(Graphics2D g2d, List<Point> points) {
        // Sombra de la línea
        g2d.setColor(new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 50));
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int j = 0; j < points.size() - 1; j++) {
            Point p1 = points.get(j);
            Point p2 = points.get(j + 1);
            g2d.drawLine(p1.x + 1, p1.y + 1, p2.x + 1, p2.y + 1);
        }
        
        // Línea principal
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int j = 0; j < points.size() - 1; j++) {
            Point p1 = points.get(j);
            Point p2 = points.get(j + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
    
    private void drawModernPoints(Graphics2D g2d, List<Point> points) {
        for (Point point : points) {
            // Círculo exterior (sombra)
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillOval(point.x - 5, point.y - 5, 10, 10);
            
            // Círculo principal
            g2d.setColor(lineColor);
            g2d.fillOval(point.x - 4, point.y - 4, 8, 8);
            
            // Círculo interior (brillo)
            g2d.setColor(Color.WHITE);
            g2d.fillOval(point.x - 2, point.y - 2, 4, 4);
        }
    }
    
    private void drawLabels(Graphics2D g2d, int chartX, int chartY, int chartWidth, int chartHeight, List<Point> points) {
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        // Etiquetas del eje X
        int i = 0;
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            String label = entry.getKey();
            if (label.length() > 10) {
                label = label.substring(5); // Solo mostrar MM-DD
            }
            
            int x = data.size() == 1 ? chartX + chartWidth / 2 : 
                    chartX + (i * chartWidth) / (data.size() - 1);
            
            g2d.setColor(new Color(100, 100, 100));
            FontMetrics fm = g2d.getFontMetrics();
            int labelWidth = fm.stringWidth(label);
            g2d.drawString(label, x - labelWidth / 2, chartY + chartHeight + 20);
            i++;
        }
        
        // Valores en los puntos
        i = 0;
        for (Number value : data.values()) {
            if (i < points.size()) {
                Point point = points.get(i);
                String valueStr = String.format("%.0f", value.doubleValue());
                
                // Fondo del valor
                FontMetrics fm = g2d.getFontMetrics();
                int valueWidth = fm.stringWidth(valueStr);
                int valueHeight = fm.getHeight();
                
                g2d.setColor(new Color(255, 255, 255, 220));
                g2d.fillRoundRect(point.x - valueWidth/2 - 4, point.y - valueHeight - 8, 
                                valueWidth + 8, valueHeight + 4, 6, 6);
                
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.drawRoundRect(point.x - valueWidth/2 - 4, point.y - valueHeight - 8, 
                                valueWidth + 8, valueHeight + 4, 6, 6);
                
                // Texto del valor
                g2d.setColor(new Color(44, 62, 80));
                g2d.drawString(valueStr, point.x - valueWidth/2, point.y - 8);
            }
            i++;
        }
    }
}
