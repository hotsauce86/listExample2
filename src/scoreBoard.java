public class scoreBoard {

    private int playerID;
    private int score;


    scoreBoard(int playerID, int score){
        this.playerID = playerID;
        this.score = score;

    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getPlayerID() {
        return playerID;
    }

    public int getScore() {
        return score;
    }

}
