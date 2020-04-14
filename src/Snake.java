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
        Node node = new Node(row, col);
        node.setNext(body.get(body.size() - 1));
        body.add(node);
        eat = true;
        remainingNodesToCreate--;
    }
    
    public boolean move(int row, int col, int[][] board) {
        for (int i = body.size() - 1; i >= 0; i--) {
            Node n = body.get(i);
            if (n.getNext()== null) {
                n.moveRow(row, board.length - 1);
                n.moveCol(col, board[0].length - 1);
                
                if (board[n.getRow()][n.getCol()] == 2) {
                    remainingNodesToCreate++;
                    score.incrementScore(1);
                } else if (board[n.getRow()][n.getCol()] == 3) {
                    remainingNodesToCreate += 4;
                    score.incrementScore(4);
                }
                
                if (remainingNodesToCreate > 0) {
                    grew(row, col);
                }
                board[n.getRow()][n.getCol()] = 1;
            } else {
                n.moveToNext();
            }
        }
        return true;
    }    

    public boolean isEat() {
        return eat;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
