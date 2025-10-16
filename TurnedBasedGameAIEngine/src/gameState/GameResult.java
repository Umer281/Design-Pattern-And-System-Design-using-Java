package gameState;

public class  GameResult {
    boolean isOver;
    String winner;

    public  GameResult(boolean isOver, String winner){
        this.isOver = isOver;
        this.winner = winner;
    }

    public boolean isOver() {
        return isOver;
    }

    public String getWinner(){
        return  winner;
    }
}
