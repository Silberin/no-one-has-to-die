// src/main/java/GameLogicRoom1.java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GameLogicRoom1 extends BaseGameLogic {
    private Set<Integer> deadTokensSet;

    // Konstruktor für GameLogicRoom1
    public GameLogicRoom1() {
        super();
        rooms[0] = new Room("Room 1", 8, 6);
        deadTokensSet = new HashSet<>();
        placeElementsInRoom(0);
    }

    // Methode zum Platzieren der Elemente im Raum
    @Override
    protected void placeElementsInRoom(int roomIndex) {
        Room room = rooms[roomIndex];
        room.placeToken(1, 2, new Token(1, "Troy", 1, 2));
        room.placeToken(5, 2, new Token(4, "Steve", 5, 2));
        room.placeToken(2, 0, new Token(2, "Sarah", 2, 0));
        room.placeToken(1, 5, new Token(3, "Lionel", 1, 5));
        room.placeLock(2, 2);
        room.placeLock(4, 2);
        room.getTiles()[2][2].setLockClosed(false); // Ensure Door 1 is open
        room.getTiles()[4][2].setLockClosed(false); // Ensure Door 2 is open
        room.placeFire(3, 2);
        room.placeWater(4, 0);
        room.placeWater(4, 5);
    }

    // Methode zur Ausführung von Spieleraktionen
    public void playerAction(String action, int x, int y) {
        Room room = rooms[0];
        Tile[][] tiles = room.getTiles();

        switch (action) {
            case "lockDoor1":
                tiles[2][2].setLockClosed(true);
                break;
            case "lockDoor2":
                tiles[4][2].setLockClosed(true);
                break;
            case "activateWater":
                waterActivated = true;
                waterCounter = 0;
                spreadWater(tiles);
                break;
            case "deactivateWater":
                waterActivated = false;
                break;
            case "skip":
                break;
            case "reset":
                resetRoom(0, true);
                break;
            default:
                System.out.println("Invalid action.");
                break;
        }
    }

    // Methode zur Ausführung des nächsten Zuges
    public void nextTurn(Scanner scanner) {
        Room room = rooms[0];
        Tile[][] tiles = room.getTiles();
        turnCounter++;

        if (firstTurn) {
            spreadFire(tiles, 3, 1);
            spreadFire(tiles, 4, 2);
            spreadFire(tiles, 2, 2);
            spreadFire(tiles, 3, 3);
            firstTurn = false;
        } else if (turnCounter == 2) {
            if (!blockedFireTiles.contains("2,2")) {
                spreadFire(tiles, 1, 2);
            }
            if (!blockedFireTiles.contains("4,2")) {
                spreadFire(tiles, 5, 2);
            }
            spreadFire(tiles, 3, 0);
            spreadFire(tiles, 3, 4);
        } else if (turnCounter == 3) {
            if (!blockedFireTiles.contains("3,0")) {
                spreadFire(tiles, 2, 0);
            }
            if (!blockedFireTiles.contains("3,0")) {
                spreadFire(tiles, 3, 5);
            }
        } else if (turnCounter == 4) {
            if (!blockedFireTiles.contains("3,0")) {
                spreadFire(tiles, 2, 5);
            }
        } else if (turnCounter == 5) {
            if (!blockedFireTiles.contains("3,5")) {
                spreadFire(tiles, 1, 5);
            }
        } else if (turnCounter == 6) {
            if (!blockedFireTiles.contains("3,5")) {
                spreadFire(tiles, 0, 5);
            }
        }

        if (waterActivated) {
            spreadWater(tiles);
            waterCounter++;
        }

        for (Token token : room.getTokens()) {
            int x = token.getX();
            int y = token.getY();
            if (!token.isDead() && ("Fire".equals(tiles[x][y].getType()) || "Water".equals(tiles[x][y].getType()))) {
                String tokenName = token.getName();
                String causeOfDeath = "Fire".equals(tiles[x][y].getType()) ? "burned" : "drowned";
                System.out.println("\n" + tokenName + " " + causeOfDeath + " to death.");
                tiles[x][y].setToken(null);
                token.setDead(true);
                deadTokensSet.add(token.getId());
                if ("Steve".equals(tokenName) || "Sarah".equals(tokenName) || "Lionel".equals(tokenName) || "Troy".equals(tokenName)) {
                    deadTokens++;
                }
                waitForEnter(scanner);
            }
        }
    }

    // Methode zum Ausbreiten des Feuers
    private void spreadFire(Tile[][] tiles, int x, int y) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if ("Water".equals(tiles[i][j].getType())) {
                    blockedFireTiles.add(i + "," + j);
                }
            }
        }

        if (blockedFireTiles.contains(x + "," + y)) {
            return;
        }

        if (!"Lock".equals(tiles[x][y].getType()) || !tiles[x][y].isLockClosed()) {
            if (!"Water".equals(tiles[x][y].getType())) {
                tiles[x][y].setType("Fire");
            }
        } else {
            blockedFireTiles.add(x + "," + y);
        }
    }

    // Methode zum Ausbreiten des Wassers
    private void spreadWater(Tile[][] tiles) {
        if (waterCounter == 0) {
            tiles[3][5].setType("Water");
            tiles[3][0].setType("Water");
        } else if (waterCounter == 1) {
            tiles[3][1].setType("Water");
            tiles[2][0].setType("Water");
            tiles[3][4].setType("Water");
            tiles[2][5].setType("Water");
        } else if (waterCounter == 2) {
            tiles[1][5].setType("Water");
            tiles[3][2].setType("Water");
            tiles[3][3].setType("Water");
        } else if (waterCounter == 3) {
            tiles[0][5].setType("Water");
            tiles[2][2].setType("Water");
            tiles[4][2].setType("Water");
        } else if (waterCounter == 4) {
            tiles[1][2].setType("Water");
            tiles[5][2].setType("Water");
        }
    }

    public Set<Integer> getDeadTokensSet() {
        return deadTokensSet;
    }

    // Methode zum Zurücksetzen des Raums
    @Override
    public void resetRoom(int roomIndex, boolean playerInitiated) {
        super.resetRoom(roomIndex, playerInitiated);
    }
}