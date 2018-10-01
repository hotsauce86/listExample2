public class playerGames {

    private int playerID;
    private float gameTotal;

    playerGames (int playerID, float gameTotal){
        this.playerID = playerID;
        this.gameTotal = gameTotal;
    }

    public int getPlayerID() {
        return playerID;
    }

    public float getGameTotal() {
        return gameTotal;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setGameTotal(float gameTotal) {
        this.gameTotal = gameTotal;
    }
}
