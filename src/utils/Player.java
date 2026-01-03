package utils;

public class Player {
    private int id;
    private String playerName;
    private boolean isAlive = true;
    private int wonCount;
    private int lostCount;

    public Player(int id, String playerName) {
        this.id = id;
        this.playerName = playerName;
    }

    public static Player createGuest(int size) {
        return new Player(size + 1 * -1, "Guest");
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWonCount() {
        return wonCount;
    }

    public void setWonCount(int wonCount) {
        this.wonCount = wonCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", wonCount=" + wonCount +
                ", lostCount=" + lostCount +
                '}';
    }
}
