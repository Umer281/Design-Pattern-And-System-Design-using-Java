package gameState;

public class Move {

     private Cell cell;
     public Move(int row,int col){
          new Cell(row,col);
     }
       public  static  Cell  getCell(){
            return cell;
       }

}
