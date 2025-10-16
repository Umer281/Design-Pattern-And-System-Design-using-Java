package gameState;

public class Cell {

    int row,col;


    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public  Integer  getRow(){
        return row;
    }

    public Integer getCol(){
        return col;
    }
}
