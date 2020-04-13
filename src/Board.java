import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

class MyRunnable implements Runnable {

    private int[][] board;
    private Snake snake;
    private Board b;
    private boolean pause = false;
    
    public MyRunnable(Board b) {
        this.b = b;
        this.board = b.getBoard();
        this.snake = b.getSnake();
    }
    
    @Override
    public void run() {
        while (!pause) {
            List<Node> body = b.getSnake().getBody(); 
            try {Thread.sleep(Board.DeltaTime);} catch (InterruptedException ex) {}
            switch(b.getDirection()) {
                case LEFT:
                    board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    snake.move(0, -1, board);
                    break;
                case RIGHT:
                    board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    snake.move(0, +1, board);
                    break;
                case UP:
                    board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    snake.move(-1, 0, board);
                    break;
                default:
                    board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    snake.move(+1, 0,board);
                    break;
            }
            b.repaint();
        }
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    public boolean getPause() {
        return pause;
    }
}

public class Board extends JPanel implements ActionListener {
    
    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction == Direction.RIGHT) {break;}
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction == Direction.LEFT) {break;}
                    direction = Direction.RIGHT;
                    break;
                case KeyEvent.VK_UP:
                    if (direction == Direction.DOWN) {break;}
                    direction = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction == Direction.UP) {break;}
                    direction = Direction.DOWN;
                    break;
                case KeyEvent.VK_ESCAPE:
                    pause();
                default:
                    break;
            }
        }
    }
    
    private int numRows;
    private int numCols;
    private int[][] board;
    private Snake snake;
    private Food food;
    private Food specialFood;
    public static int DeltaTime;
    private Direction direction;
    private MyRunnable runnable;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        public void run() {
            if (!food.isHaveFood()) {            
                food = new Food(fill(), board);
            }
        }
    };
    
    private void myInit() {
        food = new Food(fill(), board);
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        snake = new Snake(4,4,3);
        DeltaTime = 500;
        direction = Direction.RIGHT;
        runnable = new MyRunnable(this);
        
        List<Node> body = snake.getBody(); 
        for (Node n : body) {
            board[n.getRow()][n.getCol()] = 1;
        }
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
    
    public void run() {
        runnable = new MyRunnable(this);
        runnable.run();
    }
    
    public void pause() {
        boolean pause = runnable.getPause();
        if (!pause) {
            runnable.setPause(true);
        } else {
            run();
        }
    }
    
    private List<Node> fill() {
        List<Node> position = new ArrayList<>();
        
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 0) {
                    position.add(new Node(x, y));
                }
            }
        }
        
        return position;
    }
    
    public boolean colideFood() {
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
        boolean haveFood = false;
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                if (board[x][y] == 1) {
                    Util.drawSquare(g2d, x, y, numRows, numCols, Color.yellow);
                } else if (board[x][y] == 2) {
                    haveFood = true;
                    Util.drawSquare(g2d, x, y, numRows, numCols, Color.red);
                }
            }
        }
        food.setHaveFood(haveFood);
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

    public Direction getDirection() {
        return direction;
    }

    public int[][] getBoard() {
        return board;
    }

    public Node getNode() {
        return snake.getBody().get(0);
    }

    public Snake getSnake() {
        return snake;
    }
}
