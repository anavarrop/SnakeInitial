import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Food {
    private Node position;
    private int[][] board;
    private boolean special;
    public int timerSpecialFood = 1;
    private boolean food;

    public Food(List<Node> position, int[][] b, boolean special) { 
        board = b;
        this.special = special;
        this.position = position.get((int)(Math.random() * (position.size() - 1)));
        
        if (special) {
            board[this.position.getRow()][this.position.getCol()] = 3;
            food = false;
        } else {
            board[this.position.getRow()][this.position.getCol()] = 2;
            food = true;
        }
    }
    
    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }
    
    public void desapear() {    
        board[position.getRow()][position.getCol()] = 0;
        food = false;
    }

    public void setTimer(int timerSpecialFood) {
        this.timerSpecialFood = timerSpecialFood;
    }
    
    public void paint(Graphics2D g2d) {
        if (special) {Util.drawSquare(g2d, position.getRow(), position.getCol(), 25, 25, Color.green);}
        else {Util.drawSquare(g2d, position.getRow(), position.getCol(), 25, 25, Color.red);}
    }
}
