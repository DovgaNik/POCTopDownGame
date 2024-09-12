import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final String PATH_TO_TEXTURES = "assets/textures";
    static final int SCREEN_SIZE_X = 1366;
    static final int SCREEN_SIZE_Y = 768;

    static JFrame frame;
    static JPanel panel;
    static List<BufferedImage> textures;
    static List<PositionOnMap> map;

    static float cameraX,
        cameraY;

    public static void main(String[] args) {
        textures = loadTextures(PATH_TO_TEXTURES);
        map = new ArrayList<PositionOnMap>();

        for (int i = 0; i < 20; i++) {
            map.add(new PositionOnMap(1, 100, 100 + (24 * i)));
        }

        for (int i = 0; i < 20; i++) {
            map.add(new PositionOnMap(1, 556, 100 + (24 * i)));
        }

        for (int i = 0; i < 20; i++) {
            map.add(new PositionOnMap(1, 100 + (24 * i), 100));
        }

        for (int i = 0; i < 20; i++) {
            map.add(new PositionOnMap(1, 100 + (24 * i), 556));
        }

        initWindow();
        panel.repaint();
    }

    private static ArrayList<BufferedImage> loadTextures(String PATH_TO_TEXTURES) {
        File textures_directory = new File(PATH_TO_TEXTURES);
        File[] textures = textures_directory.listFiles();

        ArrayList<BufferedImage> textures_buffered = new ArrayList<BufferedImage>();

        try {
            for (File texture : textures) {
                System.out.println("Loading a texture at " + texture.getAbsolutePath());
                textures_buffered.add(ImageIO.read(texture));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textures_buffered;
    }

    private static void initWindow() {
        frame = new JFrame("Proof of concept");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(new Dimension(SCREEN_SIZE_X, SCREEN_SIZE_Y));

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                int viewW = this.getWidth();
                int viewH = this.getHeight();
                int textureW = textures.get(2).getWidth();
                int textureH = textures.get(2).getHeight();

                for (int i = 0; i < viewW; i += textureW)
                    for (int j = 0; j < viewH; j += textureH)
                        g.drawImage(textures.get(2), i, j, null);

                for (PositionOnMap element : map) {
                    g.drawImage(textures.get(element.textureIndex), Math.round(element.x - cameraX), Math.round(element.y - cameraY), null);
                }
            }
        };

        panel.addMouseMotionListener(new MapMovementListener());
        panel.addMouseListener(new MapMovementListener());
        panel.setSize(new Dimension(640, 480));
        frame.add(panel);
        frame.setVisible(true);

    }

    private static class PositionOnMap {
        public int textureIndex;
        public float x, y;

        public PositionOnMap(int textureI, float x, float y) {
            textureIndex = textureI;
            this.x = x;
            this.y = y;
        }
    }

    static int lastX, lastY;
    static boolean isDragged;
    private static class MapMovementListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            int xOffset = lastX - e.getX();
            int yOffset = lastY - e.getY();

            lastX = e.getX();
            lastY = e.getY();

            cameraX = cameraX + xOffset;
            cameraY = cameraY + yOffset;

            panel.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            isDragged = true;
            lastX = e.getX();
            lastY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isDragged = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}