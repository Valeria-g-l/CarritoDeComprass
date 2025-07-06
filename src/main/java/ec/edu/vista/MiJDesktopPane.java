package ec.edu.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        GradientPaint skyGradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(25, 25, 112));
        g2.setPaint(skyGradient);
        g2.fillRect(0, 0, getWidth(), getHeight());


        g2.setColor(new Color(255, 255, 100));
        g2.fillOval(50, 50, 80, 80);


        g2.setColor(new Color(34, 139, 34));
        Polygon mountains = new Polygon();
        mountains.addPoint(0, getHeight());
        mountains.addPoint(0, 300);
        mountains.addPoint(150, 200);
        mountains.addPoint(300, 350);
        mountains.addPoint(450, 250);
        mountains.addPoint(600, 300);
        mountains.addPoint(800, 150);
        mountains.addPoint(getWidth(), 250);
        mountains.addPoint(getWidth(), getHeight());
        g2.fillPolygon(mountains);


        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, getHeight() - 100, getWidth(), 100);

        g2.setColor(new Color(139, 69, 19));
        g2.fillRect(400, 350, 30, 100);
        g2.setColor(new Color(0, 100, 0));
        g2.fillOval(370, 300, 90, 80);


        g2.setColor(Color.WHITE);
        g2.fillOval(200, 80, 60, 40);
        g2.fillOval(220, 70, 70, 50);
        g2.fillOval(250, 80, 60, 40);

        g2.fillOval(500, 100, 60, 40);
        g2.fillOval(520, 90, 70, 50);
        g2.fillOval(550, 100, 60, 40);


        g2.setColor(new Color(255, 204, 153));
        g2.fillOval(100, 300, 200, 200);


        Polygon orejaIzq = new Polygon();
        orejaIzq.addPoint(120, 300);
        orejaIzq.addPoint(100, 250);
        orejaIzq.addPoint(160, 300);
        g2.fillPolygon(orejaIzq);

        Polygon orejaDer = new Polygon();
        orejaDer.addPoint(280, 300);
        orejaDer.addPoint(300, 250);
        orejaDer.addPoint(240, 300);
        g2.fillPolygon(orejaDer);

        g2.setColor(Color.WHITE);
        g2.fillOval(140, 350, 30, 30);
        g2.fillOval(230, 350, 30, 30);

        g2.setColor(Color.BLACK);
        g2.fillOval(150, 360, 10, 10);
        g2.fillOval(240, 360, 10, 10);

        g2.setColor(Color.PINK);
        g2.fillOval(190, 390, 20, 15);

        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(180, 400, 130, 390);
        g2.drawLine(180, 405, 130, 405);
        g2.drawLine(180, 410, 130, 420);

        g2.drawLine(220, 400, 270, 390);
        g2.drawLine(220, 405, 270, 405);
        g2.drawLine(220, 410, 270, 420);


        g2.setColor(Color.RED);
        g2.drawArc(190, 400, 10, 10, 0, -180);
        g2.drawArc(200, 400, 10, 10, 0, -180);


        g2.setColor(new Color(255, 204, 153));
        g2.fillOval(280, 400, 60, 30);
    }
}