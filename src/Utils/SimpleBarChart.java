package Utils;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class SimpleBarChart extends JPanel {
    private Map<String, ? extends Number> data;
    private String title;
    private Color barColor;
    private Color backgroundColor;
    private Color gridColor;
    
    public SimpleBarChart(Map<String, ? extends Number> data, String title) {
        this.data = data;
        this.title = title;
        this.barColor = new Color(46, 204, 113); // Verde moderno
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
    
    public void setBarColor(Color color) {
        this.barColor = color;
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
            
            // Dibujar las barras
            drawBars(g2d, chartX, chartY, chartWidth, chartHeight);
            
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
    }
    
    private void drawBars(Graphics2D g2d, int chartX, int chartY, int chartWidth, int chartHeight) {
        // Encontrar valor máximo
        double maxValue = data.values().stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .orElse(1.0);
        
        if (maxValue == 0) maxValue = 1;
        
        // Configurar barras
        int barWidth = chartWidth / data.size();
        int barSpacing = Math.max(5, barWidth / 8);
        int actualBarWidth = barWidth - barSpacing;
        
        // Colores para gradiente de barras
        Color[] barColors = {
            new Color(52, 152, 219),  // Azul
            new Color(46, 204, 113),  // Verde
            new Color(155, 89, 182),  // Púrpura
            new Color(241, 196, 15),  // Amarillo
            new Color(231, 76, 60)    // Rojo
        };
        
        int i = 0;
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            double value = entry.getValue().doubleValue();
            int barHeight = (int) ((value / maxValue) * chartHeight);
            
            int barX = chartX + i * barWidth + barSpacing / 2;
            int barY = chartY + chartHeight - barHeight;
            
            // Color de la barra (usar colores diferentes para cada barra)
            Color currentBarColor = barColors[i % barColors.length];
            
            // Dibujar sombra de la barra
            g2d.setColor(new Color(0, 0, 0, 20));
            g2d.fillRoundRect(barX + 2, barY + 2, actualBarWidth, barHeight, 8, 8);
            
            // Dibujar barra con gradiente
            GradientPaint barGradient = new GradientPaint(
                barX, barY, currentBarColor.brighter(),
                barX, barY + barHeight, currentBarColor.darker()
            );
            g2d.setPaint(barGradient);
            g2d.fillRoundRect(barX, barY, actualBarWidth, barHeight, 8, 8);
            
            // Borde de la barra
            g2d.setColor(currentBarColor.darker());
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(barX, barY, actualBarWidth, barHeight, 8, 8);
            
            // Brillo en la parte superior
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.fillRoundRect(barX + 2, barY + 2, actualBarWidth - 4, Math.min(20, barHeight / 3), 6, 6);
            
            // Dibujar etiqueta del producto
            drawBarLabel(g2d, entry.getKey(), barX, chartY + chartHeight, actualBarWidth);
            
            // Dibujar valor si la barra es suficientemente alta
            if (barHeight > 25) {
                drawBarValue(g2d, value, barX, barY, actualBarWidth, barHeight);
            }
            
            i++;
        }
    }
    
    private void drawBarLabel(Graphics2D g2d, String label, int barX, int baseY, int barWidth) {
        g2d.setColor(new Color(100, 100, 100));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        
        // Truncar etiqueta si es muy larga
        if (label.length() > 10) {
            label = label.substring(0, 8) + "...";
        }
        
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        
        // Dibujar etiqueta rotada si es necesaria
        if (labelWidth > barWidth) {
            Graphics2D g2dRotated = (Graphics2D) g2d.create();
            g2dRotated.translate(barX + barWidth / 2, baseY + 15);
            g2dRotated.rotate(-Math.PI / 4);
            g2dRotated.drawString(label, -labelWidth / 2, 0);
            g2dRotated.dispose();
        } else {
            g2d.drawString(label, barX + (barWidth - labelWidth) / 2, baseY + 15);
        }
    }
    
    private void drawBarValue(Graphics2D g2d, double value, int barX, int barY, int barWidth, int barHeight) {
        String valueStr = String.format("%.0f", value);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int valueWidth = fm.stringWidth(valueStr);
        
        // Fondo para el valor
        int valueX = barX + (barWidth - valueWidth) / 2;
        int valueY = barY + barHeight / 2;
        
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(valueX - 3, valueY - fm.getHeight() / 2 - 2, 
                         valueWidth + 6, fm.getHeight() + 4, 4, 4);
        
        // Texto del valor
        g2d.setColor(new Color(44, 62, 80));
        g2d.drawString(valueStr, valueX, valueY + fm.getHeight() / 4);
    }
}
