import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    static JFrame frame;
    static JPanel panel;

    float cameraX,
        cameraY;
    public static void main(String[] args) {
        initWindow();
        panel.repaint();
    }

    private static void initWindow() {
        frame = new JFrame("Proof of concept");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(640, 480));

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("texture1.jpg"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                g.drawImage(img, -1000, -100, null);
            }
        };

        panel.setSize(new Dimension(640, 480));
        frame.add(panel);
        frame.setVisible(true);

    }
}