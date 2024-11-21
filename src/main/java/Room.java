// src/main/java/Room.java

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name; // Name des Raums
    private List<Token> tokens; // Liste der Tokens im Raum
    private Tile[][] tiles; // 2D-Array der Kacheln im Raum

    // Konstruktor für den Raum
    public Room(String name, int width, int height) {
        this.name = name;
        tokens = new ArrayList<>();
        tiles = new Tile[width][height];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile("Empty"); // Initialisierung der Kacheln als leer
            }
        }
    }

    // Methode zum Abrufen des Namens des Raums
    public String getName() {
        return name;
    }

    // Methode zum Platzieren eines Tokens im Raum
    public void placeToken(int x, int y, Token token) {
        if (isValidCoordinate(x, y)) {
            token.setX(x);
            token.setY(y);
            tiles[x][y].setToken(token);
            tokens.add(token);
        }
    }

    // Methode zum Platzieren eines Schlosses im Raum
    public void placeLock(int x, int y) {
        if (isValidCoordinate(x, y)) {
            tiles[x][y].setType("Lock");
            tiles[x][y].setLockClosed(true);
        }
    }

    // Methode zum Platzieren von Feuer im Raum
    public void placeFire(int x, int y) {
        if (isValidCoordinate(x, y)) {
            tiles[x][y].setType("Fire");
        }
    }

    // Methode zum Platzieren von Wasser im Raum
    public void placeWater(int x, int y) {
        if (isValidCoordinate(x, y)) {
            tiles[x][y].setType("Water");
        }
    }

    // Methode zum Abrufen der Tokens im Raum
    public List<Token> getTokens() {
        return tokens;
    }

    // Methode zum Abrufen der Kacheln im Raum
    public Tile[][] getTiles() {
        return tiles;
    }

    // Methode zur Überprüfung, ob die Koordinaten gültig sind
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length;
    }
}