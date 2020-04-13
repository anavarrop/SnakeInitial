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
            Node n;
            List<Node> body = b.getSnake().getBody(); 
            try {Thread.sleep(Board.DeltaTime);} catch (InterruptedException ex) {}
            switch(b.getDirection()) {
                case LEFT:
                    if (!snake.isEat()) {
                        board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    } else {
                        snake.setEat(false);
                    }
                    if (!snake.move(0, -1, board)) {Board.setGameOver();};
                    break;
                case RIGHT:
                    if (!snake.isEat()) {
                        board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    } else {
                        snake.setEat(false);
                    }
                    if (!snake.move(0, +1, board)) {Board.setGameOver();};
                    break;
                case UP:
                    if (!snake.isEat()) {
                        board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    } else {
                        snake.setEat(false);
                    }
                    if (!snake.move(-1, 0, board)) {Board.setGameOver();};
                    break;
                case DOWN:
                    if (!snake.isEat()) {
                        board[body.get(body.size() - 1).getRow()][body.get(body.size() - 1).getCol()] = 0;
                    } else {
                        snake.setEat(false);
                    }
                    if (!snake.move(+1, 0,board)) {Board.setGameOver();};
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
                case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                    if (lastDirection == Direction.RIGHT) {break;}
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                    if (lastDirection == Direction.LEFT) {break;}
                    direction = Direction.RIGHT;
                    break;
                case KeyEvent.VK_UP: case KeyEvent.VK_W:
                    if (lastDirection == Direction.DOWN) {break;}
                    direction = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                    if (lastDirection == Direction.UP) {break;}
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
    private Direction lastDirection;
    private Direction direction;
    private MyRunnable runnable;
    private static boolean gameOver;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        public void run() {
            if (!food.isHaveFood()) {            
                food = new Food(fill(), board);
            }
        }
    };
    
    private void myInit() {
        gameOver = false;
        lastDirection = direction;
        food = new Food(fill(), board);
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        snake = new Snake(4,4,3, scoreBoard1);
        DeltaTime = 200;
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
    
    public static void setGameOver() {
        gameOver = true;
    }
    
    public void gameOver() {
        System.exit(0);
        return;
    }
    
    @Override 
    protected void paintComponent(Graphics g)  {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintSnake(g2d);
    }
    
    private void paintSnake(Graphics2D g2d) {
        if (gameOver) {
            pause();
            gameOver();
        }
        lastDirection = direction;
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scoreBoard1 = new ScoreBoard();

        setBackground(new java.awt.Color(204, 255, 255));
        setLayout(new java.awt.BorderLayout());
        add(scoreBoard1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ScoreBoard scoreBoard1;
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
