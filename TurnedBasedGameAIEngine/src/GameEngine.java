import boards.TicTacToeBoard;
import gameState.Board;
import gameState.GameResult;
import gameState.Move;
import gameState.Player;

public class GameEngine {

    public Board start(String type) {
       if(type == "TicTacToe"){
           return new TicTacToeBoard();
       }else{
           throw new IllegalArgumentException();
       }
    }

    public void move(Board board, Player player, Move move) {
        if(board instanceof TicTacToeBoard){
             ((TicTacToeBoard) board).setCell(player.symbol(),Move.getCell());
        }
    }

    public GameResult isComplete(Board board) {
        if (board instanceof TicTacToeBoard) {
            String firstCharacter = "-";
            TicTacToeBoard board1 = (TicTacToeBoard) board;
            boolean rowComplete = false;
            for (int i = 0; i < 3; i++) {
                firstCharacter = board1.getCell(i,0);
                rowComplete = true;
                for (int j = 1; j < 3; j++) {
                    if (!board1.getCell(i,j).equals(firstCharacter)) {
                        rowComplete = false;
                        break;
                    }
                }
                if (rowComplete) {
                    break;
                }
            }
            if (rowComplete) {
                return new GameResult(true, firstCharacter);
            }
            boolean colComplete = false;
            for (int i = 0; i < 3; i++) {
                firstCharacter = board1.getCell(0,i);
                colComplete = true;
                for (int j = 1; j < 3; j++) {
                    if (!board1.getCell(j,i).equals(firstCharacter)) {
                        colComplete = false;
                        break;
                    }
                }
            }

            if (colComplete) {
                return new GameResult(true, firstCharacter);
            }


            boolean diagonalComplete = false;
            for (int i = 1; i < 3; i++) {
                firstCharacter = board1.getCell(0,0);
                diagonalComplete = true;
                if (!board1.getCell(i,i).equals(firstCharacter)) {
                    diagonalComplete = false;
                    break;
                }
            }
            if (diagonalComplete) {
                return new GameResult(true, firstCharacter);
            }

            boolean reverseDiagonalComplete = false;
            for (int i = 1; i < 3; i++) {
                firstCharacter = board1.getCell(0,2);
                reverseDiagonalComplete = true;
                if (!board1.getCell(i,2 - i).equals(firstCharacter)) {
                    reverseDiagonalComplete = false;
                    break;
                }
            }

            if (reverseDiagonalComplete) {
                return new GameResult(true, firstCharacter);
            }

            int countOfFilledCells = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board1.getCell(i,j) != null) {
                        countOfFilledCells++;
                    }
                }
            }

            if (countOfFilledCells == 9) {
                return new GameResult(true, "_");
            } else {
                return new GameResult(false, "_");
            }


        }else{
            return new GameResult(false, "_");
        }
    }

    public Move suggestMove(Player player, Board board) {
         if(board instanceof  TicTacToeBoard){
             TicTacToeBoard board1 = (TicTacToeBoard) board;
             for(int i=0;i<3;i++){
                 for(int j=0;j<3;j++){
                     if(board1.getCell(j,i) == null){
                          return new Move(new Cell(i,j));
                     }
                 }
             }
             throw IllegalStateException();
         }
    }


}












