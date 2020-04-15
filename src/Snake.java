import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private List<Node> body;
    private int remainingNodesToCreate;
    private boolean eat;
    private Direction direction;
    private ScoreBoard score;
    
    public Snake(int row, int col, int size, ScoreBoard score) {
        body = new ArrayList<>();
        remainingNodesToCreate = size;
        createRemainingNodes(row, col);
        eat = false;
        direction = Direction.RIGHT;
        this.score = score;
        score.incrementScore(0);
    }
    
    private void createRemainingNodes(int row, int col) {
        for (int i = 0; i < remainingNodesToCreate; i++) {
            Node node;
            if (body.isEmpty()) {
                node = new Node(row, col - i);
                node.setNext(null);
            } else {
                node = new Node(row, col - i);
                node.setNext(body.get(body.size() - 1));
            }
            body.add(node);
        }
        remainingNodesToCreate = 0;
    }
    
    public int[][] create(int numRows, int numCols) {
        int[][] board = new int[numRows][numCols];
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                board[x][y] = 0;
            }
        }
        
        for (Node n : body) {
            board[n.getRow()][n.getCol()] = 1;
        }
        return board;
    }
    
    public Node getFirst() {
        return body.get(0);
    }
    
    public int getLastRow() {
        return body.get(body.size() - 1).getRow();
    }
        
    public int getLastCol() {
        return body.get(body.size() - 1).getCol();
    }
   
    public void grew(int row, int col) {
        if (remainingNodesToCreate > 0) {
            Node node = new Node(row, col);
            
            node.setNext(body.get(body.size() - 1));
            body.add(node);
            
            remainingNodesToCreate--;
            eat = true;
        }
    }
    
    public boolean move(int row, int col, Food food, Food specialFood) {
        for (int i = body.size() - 1; i >= 0; i--) {
            Node n = body.get(i);
            if (n.getNext()== null) {
                moveNode(n, row, col, 25, 25);
                if (!collision(food, specialFood)) {return false;}
                grew(n.getRow(), n.getCol());
                
            } else {
                n.moveToNext();
            }
        }
        return true;
    }    
    
    private void moveNode(Node n, int row, int col, int length1, int length2) {
        n.moveRow(row, length1);
        n.moveCol(col, length2);
    }
    
    private boolean collision(Food food, Food specialFood) {
        if (food != null && collidesWithFood(food.getPosition())) {incrementFood(food);}
        if (specialFood != null && collidesWithFood(specialFood.getPosition())) {incrementFood(specialFood);}

        return !collidesWithItSelf();
    }
    
    private void incrementFood(Food food) {
        remainingNodesToCreate++;
        if (food.isSpecial()) {score.incrementScore(4);} 
        else {score.incrementScore(1);}
        food.setFood(false);
    }

    public boolean isEat() {
        return eat;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }

    public List<Node> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    private boolean collidesWithItSelf() {
        for (int i = 0; i < body.size(); i++) {
            for (int y = 0; y < body.size(); y++) {
                if (i == y) {continue;}
                if (body.get(i).getRow() == body.get(y).getRow() && body.get(i).getCol() == body.get(y).getCol()) {return true;}
            }
        }
        return false;
    }
    
    private boolean collidesWithFood(Node food) {
        if (body.get(0).getRow() == food.getRow() && body.get(0).getCol() == food.getCol()) {return true;}
        return false;
    }
    
    public void paint(Graphics2D g2d) {
        for (Node n : body) {
            Util.drawSquare(g2d, n.getRow(), n.getCol(), Board.SQUAREWIDTH, Board.SQUAREHEIGHT, Color.yellow);
        }
    }
}
