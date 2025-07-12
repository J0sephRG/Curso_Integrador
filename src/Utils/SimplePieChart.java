package Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class SimplePieChart extends JPanel {
    private Map<String, ? extends Number> data;
    private String title;
    private List<Color> colors;
    
    public SimplePieChart(Map<String, ? extends Number> data, String title) {
        this.data = data;
        this.title = title;
        this.colors = generateColors(data.size());
        setPreferredSize(new Dimension(300, 250));
        setBackground(Color.WHITE);
    }
    
    private List<Color> generateColors(int count) {
        List<Color> colorList = new ArrayList<>();
        Color[] defaultColors = {
            new Color(255, 99, 132),
            new Color(54, 162, 235),
            new Color(255, 205, 86),
            new Color(75, 192, 192),
            new Color(153, 102, 255),
            new Color(255, 159, 64),
            new Color(199, 199, 199),
            new Color(83, 102, 255)
        };
        
        for (int i = 0; i < count; i++) {
            colorList.add(defaultColors[i % defaultColors.length]);
        }
        return colorList;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (data == null || data.isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.drawString("No hay datos disponibles", getWidth()/2 - 50, getHeight()/2);
            return;
        }
        
        // Dibujar título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (getWidth() - titleWidth) / 2, 20);
        
        // Calcular total
        double total = data.values().stream().mapToDouble(Number::doubleValue).sum();
        if (total == 0) return;
        
        // Configurar área del gráfico
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 10;
        int radius = Math.min(getWidth(), getHeight() - 80) / 3;
        
        // Dibujar sectores
        double startAngle = 0;
        int colorIndex = 0;
        
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            double value = entry.getValue().doubleValue();
            double angle = (value / total) * 360;
            
            g2d.setColor(colors.get(colorIndex % colors.size()));
            g2d.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 
                       (int) startAngle, (int) angle);
            
            // Dibujar borde
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 
                       (int) startAngle, (int) angle);
            
            startAngle += angle;
            colorIndex++;
        }
        
        // Dibujar leyenda
        drawLegend(g2d);
    }
    
    private void drawLegend(Graphics2D g2d) {
        int legendX = 10;
        int legendY = getHeight() - (data.size() * 20) - 10;
        int colorIndex = 0;
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));
        
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            // Dibujar cuadrado de color
            g2d.setColor(colors.get(colorIndex % colors.size()));
            g2d.fillRect(legendX, legendY + (colorIndex * 18), 12, 12);
            
            // Dibujar texto
            g2d.setColor(Color.BLACK);
            String text = entry.getKey() + ": " + entry.getValue();
            if (text.length() > 25) {
                text = text.substring(0, 22) + "...";
            }
            g2d.drawString(text, legendX + 18, legendY + (colorIndex * 18) + 10);
            
            colorIndex++;
        }
    }
}
