package ua.genty.robot.ui;

import ua.genty.robot.enums.WorldElements;
import ua.genty.robot.EvolutionAlgorithm;
import ua.genty.robot.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Apr 23, 2008
 * Time: 9:17:43 AM
 */
public class MapPanel extends JPanel {

//    public static final int X_POS = 0;
//    public static final int Y_POS = 0;

//    public static final int MAP_WIDTH = 6;
//    public static final int MAP_HEIGHT = 6;

    public static final int CELL_SIZE = 20;

    public static MapPanel instance;

    public MapPanel() {

        instance = this;//todo: delete this
        PanelMouseListener panelMouseListener = new PanelMouseListener();

        addMouseListener(panelMouseListener);
        addMouseMotionListener(panelMouseListener);
    }

    public void paint(Graphics graphics) {

        WorldElements[][] map = EvolutionAlgorithm.getInstance().getWorldMap();
        Color oldColor = graphics.getColor();

        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, map.length * CELL_SIZE, map.length * CELL_SIZE);

        graphics.setColor(Color.BLACK);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                graphics.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[i].length; j++) {
                if (WorldElements.GRASS.equals(map[i][j]) || map[i][j] == null) {

                    graphics.setColor(Color.WHITE);

                } else if (WorldElements.WALL.equals(map[i][j])) {

                    graphics.setColor(Color.BLACK);

                } else if (WorldElements.FOOD.equals(map[i][j])) {

                    graphics.setColor(Color.GREEN);

                } else if (WorldElements.MINE.equals(map[i][j])) {
                    graphics.setColor(Color.RED);
                }
                
                graphics.fillRect(j * CELL_SIZE + 2, i * CELL_SIZE + 2, CELL_SIZE - 2, CELL_SIZE - 2);
            }
        }

        graphics.setColor(Color.BLUE);

        int i = EvolutionAlgorithm.getInstance().getRobotPosition().getI();
        int j = EvolutionAlgorithm.getInstance().getRobotPosition().getJ();

        graphics.fillRect(j * CELL_SIZE + 2, i * CELL_SIZE + 2, CELL_SIZE - 2, CELL_SIZE - 2);

        graphics.setColor(oldColor);
    }

    public class PanelMouseListener implements MouseListener, MouseMotionListener {

        public PanelMouseListener() {
        }

        public void mouseClicked(MouseEvent e) {

            int mapWidth = CELL_SIZE * World.MAP_WIDTH;
            int mapHeight = CELL_SIZE * World.MAP_HEIGHT;

            if (e.getX() < mapWidth && e.getY() < mapHeight) {
                int x = e.getX();
                int y = e.getY();                

                int j = x / CELL_SIZE;
                int i = y / CELL_SIZE;

                EvolutionAlgorithm.getInstance()
                        .getWorld().setWorldElement(GentyFrame.getInstance().getWorldElement(), i, j);

                MapPanel.this.repaint();
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }
}
