import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    
    private Direction direction;
    private List<Node> body;
    private int remainingNodesToCreate = 0;
    
    
    public Snake(int row, int col, int size) { // Initial position of the head of the snake and number of inital nodes
        body = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node node = new Node(row, col - i);
            body.add(node);
        }
    }
    
    public boolean canMove(int row, int col) {
        // Finish this method
        return true;
    }
    
    public List<Node> getBody() {
        return body;
    }
    
    public void paint(Graphics g, int squareWidth, int squareHeight) {

    }
    
    public void move() {
        
    }
}
