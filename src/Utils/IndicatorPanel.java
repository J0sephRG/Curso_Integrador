package Utils;

import java.awt.*;
import javax.swing.*;

public class IndicatorPanel extends JPanel {
    private String title;
    private String value;
    private String subtitle;
    private Color backgroundColor;
    private Color textColor;
    private Icon icon;
    
    public IndicatorPanel(String title, String value, String subtitle, Color backgroundColor) {
        this.title = title;
        this.value = value;
        this.subtitle = subtitle;
        this.backgroundColor = backgroundColor;
        this.textColor = Color.WHITE;
        
        setPreferredSize(new Dimension(180, 120));
        setBackground(backgroundColor);
        setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    public void setValue(String value) {
        this.value = value;
        repaint();
    }
    
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo con gradiente
        GradientPaint gradient = new GradientPaint(
            0, 0, backgroundColor,
            0, getHeight(), backgroundColor.darker()
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Título
        g2d.setColor(textColor);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics titleFm = g2d.getFontMetrics();
        int titleWidth = titleFm.stringWidth(title);
        g2d.drawString(title, (getWidth() - titleWidth) / 2, 20);
        
        // Valor principal
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics valueFm = g2d.getFontMetrics();
        int valueWidth = valueFm.stringWidth(value);
        g2d.drawString(value, (getWidth() - valueWidth) / 2, 55);
        
        // Subtítulo
        if (subtitle != null && !subtitle.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            FontMetrics subtitleFm = g2d.getFontMetrics();
            int subtitleWidth = subtitleFm.stringWidth(subtitle);
            g2d.drawString(subtitle, (getWidth() - subtitleWidth) / 2, 75);
        }
        
        // Borde decorativo
        g2d.setColor(textColor.darker());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
    }
}
