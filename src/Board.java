import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends javax.swing.JPanel {
    
    private int numRows;
    private int numCols;
    private Snake snake;
    private Food food;
    private Food specialFood;
    private Timer snakeTimer;
    private Timer specialFoodTimer;
    private int DeltaTime;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        myInit();
    }
    
    private void myInit() {
        DeltaTime = 100;
        snakeTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };
        snakeTimer.schedule(task, 0, DeltaTime);
    }
    
    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
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
        // Finish this method
        // Paint the Snake and the food here
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
}
