import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private List<Node> body;
    private int remainingNodesToCreate;
    
    
    public Snake(int row, int col, int size) {
        body = new ArrayList<>();
        remainingNodesToCreate = size;
        createRemainingNodes(row, col);
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
    
    public void move(int row, int col, int[][] board) {
        for (int i = body.size() - 1; i >= 0; i--) {
            Node n = body.get(i);
            if (n.getNext()== null) {
                n.moveRow(row, board.length - 1);
                n.moveCol(col, board[0].length - 1);
                board[n.getRow()][n.getCol()] = 1;
            } else {
                n.moveToNext();
            }
        }
    }
}
