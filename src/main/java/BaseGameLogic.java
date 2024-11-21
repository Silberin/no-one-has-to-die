// src/main/java/BaseGameLogic.java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public abstract class BaseGameLogic {
    protected Room[] rooms; // Array der Räume im Spiel
    protected int turnCounter; // Zähler für die Anzahl der Züge
    protected boolean firstTurn; // Flag, um anzuzeigen, ob es der erste Zug ist
    protected Set<String> blockedFireTiles; // Set der Kacheln, die vom Feuer blockiert sind
    protected boolean waterActivated; // Flag, um anzuzeigen, ob das Wasser aktiviert ist
    protected int waterCounter; // Zähler für die Anzahl der Wasserzüge
    protected int deadTokens; // Zähler für die Anzahl der toten Tokens
    protected Set<Integer> deadTokensSet; // Set der IDs der toten Tokens

    // Konstruktor der Basisklasse
    public BaseGameLogic() {
        rooms = new Room[2]; // Initialisierung des Raumarrrays mit zwei Räumen
        turnCounter = 0; // Initialisierung des Zugzählers
        firstTurn = true; // Setzen des ersten Zuges auf wahr
        blockedFireTiles = new HashSet<>(); // Initialisierung des Sets der blockierten Feuerkacheln
        waterActivated = false; // Setzen des Wasser-Flags auf falsch
        waterCounter = 0; // Initialisierung des Wasserzählers
        deadTokens = 0; // Initialisierung des Zählers für tote Tokens
        deadTokensSet = new HashSet<>(); // Initialisierung des Sets der toten Tokens
    }

    // Methode zum Zurücksetzen des Raums
    public void resetRoom(int roomIndex, boolean playerInitiated) {
        rooms[roomIndex] = new Room("Room " + (roomIndex + 1), 8, 6); // Erstellen eines neuen Raums
        turnCounter = 0; // Zurücksetzen des Zugzählers
        firstTurn = true; // Setzen des ersten Zuges auf wahr
        blockedFireTiles.clear(); // Leeren des Sets der blockierten Feuerkacheln
        waterActivated = false; // Setzen des Wasser-Flags auf falsch
        waterCounter = 0; // Zurücksetzen des Wasserzählers
        deadTokens = 0; // Zurücksetzen des Zählers für tote Tokens
        if (playerInitiated) {
            deadTokensSet.clear(); // Leeren des Sets der toten Tokens, wenn vom Spieler initiiert
        }
        placeElementsInRoom(roomIndex); // Platzieren der Elemente im Raum
    }

    // Abstrakte Methode zum Platzieren der Elemente im Raum
    protected abstract void placeElementsInRoom(int roomIndex);

    // Methode zum Abrufen der Räume
    public Room[] getRooms() {
        return rooms;
    }

    // Methode zum Abrufen der Anzahl der toten Tokens
    public int getDeadTokens() {
        return deadTokens;
    }

    // Methode zum Abrufen des Zugzählers
    public int getTurnCounter() {
        return turnCounter;
    }

    // Methode zum Warten auf die Eingabetaste
    protected void waitForEnter(Scanner scanner) {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}