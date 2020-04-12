import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    
    private int numRows;
    private int numCols;
    private int[][] board;
    private Snake snake;
    private Food food;
    private Food specialFood;
    private Timer snakeTimer;
    private Timer specialFoodTimer;
    private int DeltaTime;
    
    private void myInit() {
        snake = new Snake(4,4,3);
        DeltaTime = 100;
        snakeTimer = new Timer(400, this);
        snakeTimer.start();
   }
    
    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
      
        board = new int[numRows][numCols];
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                board[x][y] = 0;
            }
        }
        
        initComponents();
        myInit();
    }
    
    public boolean colideFood() {
        // Finish this method
        return false;
    }
    
    public void gameOver() {
        // Finish this method
    }
    
    @Override 
    protected void paintComponent(Graphics g)  {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintSnake(g2d);
        paintFood(g2d);
    }
    
    private void paintSnake(Graphics2D g2d) {
        List<Node> body = snake.getBody();
        for (Node n : body) {
            board[n.getRow()][n.getCol()] = 1;
        }
        
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                if (board[x][y] == 1) {
                    Util.drawSquare(g2d, x, y, numRows, numCols, Color.yellow);
                }
            }
        }
    }
    
    private void paintFood(Graphics2D g2d) {
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
