/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphic;

import javax.swing.*;
import java.awt.*;

public class LoadingCircle extends JPanel {
    private int angle = 0;

    public LoadingCircle() {

        Timer timer = new Timer(50, e -> {
            angle += 10; 
            if (angle >= 360) angle = 0; 
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = Math.min(getWidth(), getHeight()) - 20; 
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;


        for (int i = 0; i < 12; i++) {
            float opacity = (i + 1) / 12.0f;
            g2.setColor(new Color(0, 0, 255, (int) (opacity * 255))); 
            g2.fillArc(x, y, diameter, diameter, angle + i * 30, 20); 
        }
    }
}

