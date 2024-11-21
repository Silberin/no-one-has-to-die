// src/main/java/GameLogicRoom2.java
import java.util.Scanner;
import java.util.Set;

public class GameLogicRoom2 extends BaseGameLogic {
    private Set<Integer> deadTokensSet;

    // Konstruktor für GameLogicRoom2
    public GameLogicRoom2(Room[] rooms, Set<Integer> deadTokensSet) {
        super();
        this.rooms = rooms;
        this.deadTokensSet = deadTokensSet;
        rooms[1] = new Room("Room 2", 8, 6);
        placeElementsInRoom(1);
    }

    // Methode zum Platzieren der Elemente im Raum
    @Override
    protected void placeElementsInRoom(int roomIndex) {
        Room room = rooms[roomIndex];
        if (!deadTokensSet.contains(3)) {
            room.placeToken(1, 3, new Token(3, "Lionel", 1, 3));
        }
        if (!deadTokensSet.contains(1)) {
            room.placeToken(2, 5, new Token(1, "Troy", 2, 5));
        }
        if (!deadTokensSet.contains(2)) {
            room.placeToken(6, 5, new Token(2, "Sarah", 6, 5));
        }
        if (!deadTokensSet.contains(4)) {
            room.placeToken(7, 3, new Token(4, "Steve", 7, 3));
        }
        room.placeWater(2, 1);
        room.placeWater(6, 0);
        room.placeFire(4, 5);
        room.placeLock(5, 5);
        room.placeLock(3, 5);
        room.getTiles()[5][5].setLockClosed(false); // Ensure Door 2 is open
        room.getTiles()[3][5].setLockClosed(false); // Ensure Door 1 is open
    }

    // Methode zur Ausführung von Spieleraktionen
    public void playerAction(String action, int x, int y) {
        Room room = rooms[1];
        Tile[][] tiles = room.getTiles();

        switch (action) {
            case "lockDoor1":
                tiles[3][5].setLockClosed(true);
                break;
            case "lockDoor2":
                tiles[5][5].setLockClosed(true);
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
                resetRoom(1, true);
                break;
            default:
                System.out.println("Invalid action.");
                break;
        }
    }

    // Methode zur Ausführung des nächsten Zuges
    public void nextTurn(Scanner scanner) {
        Room room = rooms[1];
        Tile[][] tiles = room.getTiles();
        turnCounter++;

        if (firstTurn) {
            if (!blockedFireTiles.contains("3,5")) {
                spreadFire(tiles, 3, 5);
            }
            if (!blockedFireTiles.contains("5,5")) {
                spreadFire(tiles, 5, 5);
            }
            spreadFire(tiles, 4, 4);
            firstTurn = false;
        } else if (turnCounter == 2) {
            if (!blockedFireTiles.contains("3,5")) {
                spreadFire(tiles, 2, 5);
            }
            if (!blockedFireTiles.contains("5,5")) {
                spreadFire(tiles, 6, 5);
            }
            spreadFire(tiles, 4, 3);
        } else if (turnCounter == 3) {
            spreadFire(tiles, 3, 3);
            spreadFire(tiles, 5, 3);
            if (!blockedFireTiles.contains("3,5")) {
                spreadFire(tiles, 1, 5);
            }
            if (!blockedFireTiles.contains("5,5")) {
                spreadFire(tiles, 7, 5);
            }
        } else if (turnCounter == 4) {
            if (!blockedFireTiles.contains("2,3")) {
                spreadFire(tiles, 2, 3);
            }
            if (!blockedFireTiles.contains("6,3")) {
                spreadFire(tiles, 6, 3);
            }
        } else if (turnCounter == 5) {
            if (!blockedFireTiles.contains("2,3")) {
                spreadFire(tiles, 1, 3);
                spreadFire(tiles, 2, 2);
            }
            if (!blockedFireTiles.contains("6,3")) {
                spreadFire(tiles, 7, 3);
                spreadFire(tiles, 6, 2);
            }
        } else if (turnCounter == 6) {
            spreadFire(tiles, 6, 1);
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
            tiles[2][2].setType("Water");
            tiles[6][1].setType("Water");
        } else if (waterCounter == 1) {
            tiles[2][3].setType("Water");
            tiles[6][2].setType("Water");
        } else if (waterCounter == 2) {
            tiles[1][3].setType("Water");
            tiles[3][3].setType("Water");
            tiles[6][3].setType("Water");
        } else if (waterCounter == 3) {
            tiles[7][3].setType("Water");
            tiles[4][3].setType("Water");
            tiles[5][3].setType("Water");
        } else if (waterCounter == 4) {
            tiles[4][4].setType("Water");
        } else if (waterCounter == 5) {
            tiles[4][5].setType("Water");
        }
    }

    // Methode zum Zurücksetzen des Raums
    @Override
    public void resetRoom(int roomIndex, boolean playerInitiated) {
        super.resetRoom(roomIndex, playerInitiated);
    }
}