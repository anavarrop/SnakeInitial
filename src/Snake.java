import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private List<Node> body;
    private int remainingNodesToCreate;
    private boolean eat;
    private ScoreBoard scoreBoard;
    
    public Snake(int row, int col, int size, ScoreBoard scoreBoard) {
        body = new ArrayList<>();
        remainingNodesToCreate = size;
        createRemainingNodes(row, col);
        eat = false;
        this.scoreBoard = scoreBoard;
        this.scoreBoard.incrementScore(0);
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
    }
    
    public List<Node> getBody() {
        return body;
    }
    
    public void grew(int row, int col) {
        remainingNodesToCreate++;
        Node node = new Node(row, col);
        node.setNext(body.get(body.size() - 1));
        body.add(node);
        eat = true;
        scoreBoard.incrementScore(1);
    }
    
    public boolean move(int row, int col, int[][] board) {
        for (int i = body.size() - 1; i >= 0; i--) {
            Node n = body.get(i);
            if (n.getNext()== null) {
                n.moveRow(row, board.length - 1);
                n.moveCol(col, board[0].length - 1);
                if (board[n.getRow()][n.getCol()] == 2) {
                    grew(n.getRow(), n.getCol());
                } else if (board[n.getRow()][n.getCol()] != 0) {
                    return false;
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
    
        
}
