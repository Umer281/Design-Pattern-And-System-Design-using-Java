import gameState.Board;
import gameState.Cell;
import gameState.Move;
import gameState.Player;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        GameEngine gameEngine = new GameEngine();
        Board board =gameEngine.start("TicTacToe");

         // make moves
        //gameEngine.isComplete returns GameResult
        while(!gameEngine.isComplete(board).isOver()){
            System.out.println("Make your move");
            Player opponent = new Player("X");
            Random rand = new Random();
            int row = rand.nextInt();
           int  col = rand.nextInt();
           Move oppMove = new Move(new Cell(row,col));
            Player computer = new Player("0");
           Move computerMove = gameEngine.suggestMove(computer, board);

            gameEngine.move(board, opponent , oppMove);

            if(!gameEngine.isComplete(board).isOver()){
                gameEngine.move(board,computer, computerMove);
            }


        }

        System.out.println("GameResult: " + gameEngine.isComplete(board));



    }
}
