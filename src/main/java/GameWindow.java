// src/main/java/GameWindow.java
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private JPanel gridPanel;
    private Tile[][] tiles;

    public GameWindow(Tile[][] tiles) {
        this.tiles = tiles;
        setTitle("Game Grid");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gridPanel = new JPanel(new GridLayout(tiles.length, tiles[0].length));
        add(gridPanel, BorderLayout.CENTER);

        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                JLabel label = new JLabel(getTileContent(tiles[i][j]), SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(label);
            }
        }
    }

    private String getTileContent(Tile tile) {
        if (tile.getToken() != null) {
            return tile.getToken().getName();
        } else {
            return tile.getType();
        }
    }

    public void updateGrid() {
        gridPanel.removeAll();
        initializeGrid();
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}