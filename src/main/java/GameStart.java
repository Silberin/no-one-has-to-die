// src/main/java/GameStart.java
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class GameStart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Einführungstext für das Spiel
        System.out.println();
        System.out.println("Your task is to navigate through a series of challenging decisions to save characters\n" +
                "trapped inside a burning building while uncovering the mysteries behind the company and the fire itself.\n");
        waitForEnter(scanner);
        System.out.println("Each choice you make will determine who lives and who dies, leading to\n" +
                "multiple possible outcomes and revealing different aspects of the story.\n");
        waitForEnter(scanner);
        System.out.println("When entering actions, make sure to spell the desired action exactly as it is written.\n" +
                "Example: activateWater\n");
        waitForEnter(scanner);

        // Initialisierung von Level 1
        GameLogicRoom1 gameLogicRoom1 = new GameLogicRoom1();
        boolean waterActivated = false;

        // Create and show the game window
        final GameWindow gameWindow = new GameWindow(gameLogicRoom1.getRooms()[0].getTiles());
        SwingUtilities.invokeLater(() -> gameWindow.setVisible(true));

        // Spielschleife für Level 1
        while (gameLogicRoom1.getTurnCounter() < 6) {
            gameWindow.updateGrid();
            System.out.println("\nEnter action (lockDoor1, lockDoor2, " + (waterActivated ? "deactivateWater" : "activateWater") + ", skip, reset or help): ");
            String action = scanner.nextLine();
            String[] parts = action.split(" ");
            if ("lockDoor1".equals(parts[0])) {
                gameLogicRoom1.playerAction("lockDoor1", -1, -1);
            } else if ("lockDoor2".equals(parts[0])) {
                gameLogicRoom1.playerAction("lockDoor2", -1, -1);
            } else if ("activateWater".equals(parts[0]) && !waterActivated) {
                gameLogicRoom1.playerAction("activateWater", -1, -1);
                waterActivated = true;
            } else if ("deactivateWater".equals(parts[0]) && waterActivated) {
                gameLogicRoom1.playerAction("deactivateWater", -1, -1);
                waterActivated = false;
            } else if ("skip".equals(action)) {
                gameLogicRoom1.playerAction("skip", -1, -1);
            } else if ("reset".equals(action)) {
                gameLogicRoom1.resetRoom(0, true);
                waterActivated = false;
                System.out.println("Level 1 has been reset.");
                waitForEnter(scanner);
            } else if ("help".equals(action)) {
                printHelp();
                waitForEnter(scanner);
                continue;
            } else {
                System.out.println("Invalid action. Try again.");
                continue;
            }

            if (!"reset".equals(action)) {
                gameLogicRoom1.nextTurn(scanner);
            }
        }

        int finalDeadTokens = gameLogicRoom1.getDeadTokens();

        if (finalDeadTokens == 1) {
            System.out.println("\nYou saved everyone you could. Proceeding to Level 2...");
            waitForEnter(scanner);
            gameLogicRoom1.resetRoom(0, false);
            GameLogicRoom2 gameLogicRoom2 = new GameLogicRoom2(gameLogicRoom1.getRooms(), gameLogicRoom1.getDeadTokensSet());
            waterActivated = false;

            // Update the game window for Level 2
            final GameWindow gameWindowLevel2 = new GameWindow(gameLogicRoom2.getRooms()[1].getTiles());
            SwingUtilities.invokeLater(() -> gameWindowLevel2.setVisible(true));

            // Spielschleife für Level 2
            while (gameLogicRoom2.getTurnCounter() < 8) {
                gameWindowLevel2.updateGrid();
                System.out.println("\nEnter action (lockDoor1, lockDoor2, " + (waterActivated ? "deactivateWater" : "activateWater") + ", skip, reset or help): ");
                String action = scanner.nextLine();
                String[] parts = action.split(" ");
                if ("lockDoor1".equals(parts[0])) {
                    gameLogicRoom2.playerAction("lockDoor1", -1, -1);
                } else if ("lockDoor2".equals(parts[0])) {
                    gameLogicRoom2.playerAction("lockDoor2", -1, -1);
                } else if ("activateWater".equals(parts[0]) && !waterActivated) {
                    gameLogicRoom2.playerAction("activateWater", -1, -1);
                    waterActivated = true;
                } else if ("deactivateWater".equals(parts[0]) && waterActivated) {
                    gameLogicRoom2.playerAction("deactivateWater", -1, -1);
                    waterActivated = false;
                } else if ("skip".equals(action)) {
                    gameLogicRoom2.playerAction("skip", -1, -1);
                } else if ("reset".equals(action)) {
                    gameLogicRoom2.resetRoom(1, true);
                    waterActivated = false;
                    System.out.println("Level 2 has been reset.");
                    waitForEnter(scanner);
                } else if ("help".equals(action)) {
                    printHelp();
                    waitForEnter(scanner);
                    continue;
                } else {
                    System.out.println("Invalid action. Try again.");
                    continue;
                }
                if (!"reset".equals(action)) {
                    gameLogicRoom2.nextTurn(scanner);
                }
            }

            int finalDeadTokensLevel2 = gameLogicRoom2.getDeadTokens();

            if (finalDeadTokensLevel2 == 1) {
                System.out.println("Level 2 complete. Game over... for now.");
            } else {
                System.out.println("\nYou failed to save a number of people. Restarting level...\n");
                waitForEnter(scanner);
                gameLogicRoom2.resetRoom(1, false);
                main(args);
            }
        } else {
            System.out.println("\nYou failed to save a number of people. Restarting level...\n");
            waitForEnter(scanner);
            gameLogicRoom1.resetRoom(0, false);
            main(args);
        }

        scanner.close();
    }

    // Methode zum Drucken der Karte von Level 1
    private static void printRoomMap(BaseGameLogic gameLogic) {
        System.out.println("\nMap of Level 1\n");

        Room room1 = gameLogic.getRooms()[0];
        Tile[][] tiles = room1.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                String content = "     ";
                if (i == 0 && j == 5) {
                    content = "Valve";
                } else if ("Fire".equals(tiles[i][j].getType())) {
                    content = "Fire";
                } else if ("Lock".equals(tiles[i][j].getType())) {
                    if (i == 2 && j == 2) {
                        content = tiles[i][j].isLockClosed() ? "Closed" : "Door 1";
                    } else if (i == 4 && j == 2) {
                        content = tiles[i][j].isLockClosed() ? "Closed" : "Door 2";
                    }
                } else if ("Water".equals(tiles[i][j].getType())) {
                    content = "Water";
                } else if (tiles[i][j].getToken() != null) {
                    content = tiles[i][j].getToken().getName();
                }
                System.out.printf("%-7s", content);
            }
            System.out.println();
        }
    }

    // Methode zum Drucken der Karte von Level 2
    private static void printRoomMap2(BaseGameLogic gameLogic) {
        System.out.println("\nMap of Level 2\n");

        Room room2 = gameLogic.getRooms()[1];
        Tile[][] tiles = room2.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                String content = "     ";
                if ("Fire".equals(tiles[i][j].getType())) {
                    content = "Fire";
                } else if ("Lock".equals(tiles[i][j].getType())) {
                    if (i == 3 && j == 5) {
                        content = tiles[i][j].isLockClosed() ? "Closed" : "Door 1";
                    } else if (i == 5 && j == 5) {
                        content = tiles[i][j].isLockClosed() ? "Closed" : "Door 2";
                    }
                } else if ("Water".equals(tiles[i][j].getType())) {
                    content = "Water";
                } else if (tiles[i][j].getToken() != null) {
                    content = tiles[i][j].getToken().getName();
                }
                System.out.printf("%-7s", content);
            }
            System.out.println();
        }
    }

    // Methode zum Warten auf die Eingabetaste
    public static void waitForEnter(Scanner scanner) {
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    // Methode zum Drucken der Hilfe
    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("lockDoor1 - Locks door 1.");
        System.out.println("lockDoor2 - Locks door 2.");
        System.out.println("activateWater - Activates the water system, spreading water to neighbouring tiles each turn.");
        System.out.println("deactivateWater - Deactivates the water system.");
        System.out.println("skip - Skips the current turn without taking any action.");
        System.out.println("reset - Resets Level 1 to its initial state.");
        System.out.println("help - Shows this help message");
    }
}