// src/main/java/Token.java

public class Token {
    private int id; // ID des Tokens
    private String name; // Name des Tokens
    private int x; // X-Koordinate des Tokens
    private int y; // Y-Koordinate des Tokens
    private boolean isDead; // Flag, ob der Token tot ist

    // Konstruktor für den Token
    public Token(int id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.isDead = false; // Initialisierung als nicht tot
    }

    // Methode zum Abrufen der ID des Tokens
    public int getId() {
        return id;
    }

    // Methode zum Abrufen des Namens des Tokens
    public String getName() {
        return name;
    }

    // Methode zum Abrufen der X-Koordinate des Tokens
    public int getX() {
        return x;
    }

    // Methode zum Setzen der X-Koordinate des Tokens
    public void setX(int x) {
        this.x = x;
    }

    // Methode zum Abrufen der Y-Koordinate des Tokens
    public int getY() {
        return y;
    }

    // Methode zum Setzen der Y-Koordinate des Tokens
    public void setY(int y) {
        this.y = y;
    }

    // Methode zum Überprüfen, ob der Token tot ist
    public boolean isDead() {
        return isDead;
    }

    // Methode zum Setzen des Todesstatus des Tokens
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
}