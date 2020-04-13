import java.util.List;

public class Food {
    private Node position;
    private int[][] board;
    private boolean haveFood;

    public Food(List<Node> position, int[][] b) { 
        board = b;
        this.position = position.get((int)(Math.random() * (position.size() - 1)));
        board[this.position.getRow()][this.position.getCol()] = 2;
        haveFood = true;
    }

    public boolean isHaveFood() {
        return haveFood;
    }

    public void setHaveFood(boolean haveFood) {
        this.haveFood = haveFood;
    }
}
