import java.util.List;

public class Food {
    private Node position;
    private int[][] board;
    private boolean haveFood;
    private boolean special;
    public int timerSpecialFood = 1;

    public Food(List<Node> position, int[][] b, boolean special) { 
        board = b;
        this.special = special;
        this.position = position.get((int)(Math.random() * (position.size() - 1)));
        if (special) {
            board[this.position.getRow()][this.position.getCol()] = 3;
        } else {
            board[this.position.getRow()][this.position.getCol()] = 2;
            haveFood = true;
        }
    }

    public boolean isHaveFood() {
        return haveFood;
    }

    public void setHaveFood(boolean haveFood) {
        this.haveFood = haveFood;
    }
    
    public void desapear() {    
        board[position.getRow()][position.getCol()] = 0;
    }

    public void setTimer(int timerSpecialFood) {
        this.timerSpecialFood = timerSpecialFood;
    }
    
    
}
