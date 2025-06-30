package ec.edu.util;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FondoUtils {
    public static void ponerFondo(JFrame frame, String rutaImagen) {
        JLabel fondo = new JLabel(new ImageIcon(frame.getClass().getResource(rutaImagen)));
        fondo.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(fondo);
        frame.getContentPane().setComponentZOrder(fondo, frame.getContentPane().getComponentCount() - 1);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                fondo.setSize(frame.getWidth(), frame.getHeight());
            }
        });
    }
}
