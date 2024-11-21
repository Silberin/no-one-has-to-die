// src/main/java/Tile.java

public class Tile {
    private String type; // Typ der Kachel (z.B. "Empty", "Fire", "Water", "Lock")
    private Token token; // Token auf der Kachel
    private boolean lockClosed; // Flag, ob das Schloss geschlossen ist

    // Konstruktor für die Kachel
    public Tile(String type) {
        this.type = type;
        this.token = null; // Initialisierung ohne Token
        this.lockClosed = false; // Initialisierung als nicht geschlossen
    }

    // Methode zum Abrufen des Typs der Kachel
    public String getType() {
        return type;
    }

    // Methode zum Setzen des Typs der Kachel
    public void setType(String type) {
        this.type = type;
    }

    // Methode zum Abrufen des Tokens auf der Kachel
    public Token getToken() {
        return token;
    }

    // Methode zum Setzen des Tokens auf der Kachel
    public void setToken(Token token) {
        this.token = token;
    }

    // Methode zum Überprüfen, ob das Schloss geschlossen ist
    public boolean isLockClosed() {
        return lockClosed;
    }

    // Methode zum Setzen des Schlossstatus der Kachel
    public void setLockClosed(boolean lockClosed) {
        this.lockClosed = lockClosed;
    }
}