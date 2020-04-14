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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MyRunnable implements Runnable {

    private int[][] board;
    private Snake snake;
    private Board b;
    
    public MyRunnable(Board b) {
        this.b = b;
        this.board = b.getBoard();
        this.snake = b.getSnake();
    }
    
    @Override
    public void run() {
        while (true) {
            try {Thread.sleep(Board.DeltaTime);} catch (InterruptedException ex) {}
            removeLastNodeIfIsNecessary();
            
            switch(snake.getDirection()) {
                case LEFT:
                    checkCollision(0, -1);
                    break;
                case RIGHT:
                    checkCollision(0, +1);
                    break;
                case UP:
                    checkCollision(-1, 0);
                    break;
                case DOWN:
                    checkCollision(+1, 0);
                    break;
            }
            b.repaint();
        }
    }
    
    private void checkCollision(int incrementeRow, int incrementCol) {
        if (!snake.move(incrementeRow, incrementCol, board)) {
            gameOver();
        }
    }
    
    private void removeLastNodeIfIsNecessary() {
        if (!snake.isEat()) {
            removeLastNode();
        } else {
            snake.setEat(false);
        }
    }
    
    private void removeLastNode() {
        board[snake.getLastRow()][snake.getLastCol()] = 0;
    }
    
    private void gameOver() {
        try {finalize();} catch (Throwable ex) {}
        b.gameOver();
    }
}

public class Board extends JPanel implements ActionListener {
  
    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                    checkDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                    checkDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP: case KeyEvent.VK_W:
                    checkDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                    checkDirection(Direction.DOWN);
                    break;
                default:
                    break;
            }
        }
        
        private void checkDirection(Direction direction) {
            if (lastDirection != oppositeDirection(direction)) {snake.setDirection(direction);}
        }
        
        private Direction oppositeDirection(Direction direction) {
            switch (direction) {
                case LEFT:
                    return Direction.RIGHT;
                case RIGHT:
                    return Direction.LEFT;
                case UP:
                    return Direction.DOWN;
                case DOWN:
                    return Direction.UP;
                default:
                    return Direction.LEFT;
            }
        }
    }
    
    
    
    private int numRows;
    private int numCols;
    private int[][] board;
    
    private Snake snake;
    private Direction lastDirection;
    
    private Food food;
    private Food specialFood;
    
    public static int DeltaTime;
    private MyRunnable runnable;   
    
    private Timer timer;
    private TimerTask timerTask;
    
    private Timer timerSpecial;
    private TimerTask timerTaskSpecial;
    
    private boolean firstTime;
    
    
    private void myInit() {   
        firstTime = true;
        
        snake = new Snake(4,4,3, scoreBoard);
        lastDirection = snake.getDirection();
        board = snake.create(numRows, numCols);
        
        food = new Food(fill(), board, false);
        
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        DeltaTime = 200;
        runnable = new MyRunnable(this); 
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                if (!food.isHaveFood()) {            
                    food = new Food(fill(), board, false);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        timerSpecial = new Timer();
        timerTaskSpecial = new TimerTask() {
            public void run() {
                if (firstTime || specialFood.timerSpecialFood == 0) {
                    int r = (int)(Math.random() * (100 - 1) + 1);
                    if (r > 50 && r < 60){     
                        firstTime = false;
                        specialFood = new Food(fill(), board, true);
                    }
                } else {
                    if (specialFood.timerSpecialFood == 11) {
                        specialFood.timerSpecialFood = 0;
                        specialFood.desapear();
                    }
                    else {specialFood.timerSpecialFood++;}
                }
            }
        };
        timerSpecial.scheduleAtFixedRate(timerTaskSpecial, 0, 1000);
    }
    
    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        
        initComponents();
        myInit();
    }
    
    public void start() {
        runnable = new MyRunnable(this);
        runnable.run();
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
    
    public void gameOver() {
        timer.purge();
        timerTask.cancel();
        timerSpecial.purge();
        timerTaskSpecial.cancel();
        
        JOptionPane jpane = new JOptionPane();
        int a = jpane.showConfirmDialog(this, "¿Quiere hacer otra partida?", "PERDISTE", JOptionPane.INFORMATION_MESSAGE);
        
        switch (a) {
            case JOptionPane.YES_OPTION:
                myInit();
                start();
                break;
            default:
                System.exit(0);
                break;
        }
    }
    
    @Override 
    protected void paintComponent(Graphics g)  {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintSnake(g2d);
    }
    
    private void paintSnake(Graphics2D g2d) {
        lastDirection = snake.getDirection();
        
        boolean haveFood = false;
        boolean haveSpecialFood = false;
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                switch (board[x][y]) {
                    case 1:
                        Util.drawSquare(g2d, x, y, numRows, numCols, Color.yellow);
                        break;
                    case 2:
                        haveFood = true;
                        Util.drawSquare(g2d, x, y, numRows, numCols, Color.red);
                        break;
                    case 3:
                        haveSpecialFood = true;
                        Util.drawSquare(g2d, x, y, numRows, numCols, Color.green);
                        break;
                    default:
                        break;
                }
            }
        }
        if (!firstTime && !haveSpecialFood) {specialFood.setTimer(0);}
        food.setHaveFood(haveFood);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scoreBoard1 = new ScoreBoard();
        scoreBoard = new ScoreBoard();

        setBackground(new java.awt.Color(204, 255, 255));
        setLayout(new java.awt.BorderLayout());
        add(scoreBoard, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ScoreBoard scoreBoard;
    private ScoreBoard scoreBoard1;
    // End of variables declaration//GEN-END:variables
    public int[][] getBoard() {
        return board;
    }

    public Node getNode() {
        return snake.getFirst();
    }

    public Snake getSnake() {
        return snake;
    }
    
    public Runnable getRunnable() {
        return runnable;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
