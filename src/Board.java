import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jdk.nashorn.internal.objects.NativeArray;

class MyRunnable implements Runnable {

    private final Snake snake;
    private final Board b;
    
    public MyRunnable(Board b) {
        this.b = b;
        this.snake = b.getSnake();
    }
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        System.err.println("Start");
        while (!b.getPause()) {
            try {Thread.sleep(Board.DELTATIME);} catch (InterruptedException ex) {}
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
        System.err.println("Stop");
    }
    
    private void checkCollision(int incrementeRow, int incrementCol) {
        Food food = b.getFood();
        Food specialFood = b.getSpecialFood();
        if (!snake.move(incrementeRow, incrementCol, food, specialFood)) {
            gameOver();
        }
    }
    
    @SuppressWarnings("FinalizeCalledExplicitly")
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
                case KeyEvent.VK_ESCAPE:
                    pause();
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
    
    public static final int SQUAREWIDTH = 25;
    public static final int SQUAREHEIGHT = 25; 
    
    private Snake snake;
    private Direction lastDirection;
    
    private Food food;
    private Food specialFood;
    
    public static int DELTATIME = 200;
    private MyRunnable runnable;   
    
    private Timer timer;
    private TimerTask timerTask;
    
    private Timer timerSpecial;
    private TimerTask timerTaskSpecial;
    
    private boolean firstTime;
    private boolean pause;
    
    
    
    public Board() {       
        initComponents();
        myInit();
    }
    
    private void myInit() {   
        firstTime = true;
        pause = false;
        
        snake = new Snake(4,4,3, scoreBoard);
        lastDirection = snake.getDirection();
        
        food = new Food(fill(), false);
        
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
                
        startTimers();
    }
    
     private void startTimers() {                
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!food.isFood()) {            
                    food = new Food(fill(), false);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        
        timerSpecial = new Timer();
        timerTaskSpecial = new TimerTask() {
            @Override
            public void run() {
                if (firstTime || specialFood.timerSpecialFood == 0) {
                    int r = (int)(Math.random() * (100 - 1) + 1);
                    if (r > 50 && r < 60){     
                        firstTime = false;
                        specialFood = new Food(fill(), true);
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
     
    private void stopTimers() {
        timer.purge();
        timerTask.cancel();
        timerSpecial.purge();
        timerTaskSpecial.cancel();
    }
     
    private List<Node> fill() {
        List<Node> list = new ArrayList<>();
        List<Node> filled = new ArrayList<>();
         
        for (Node n : snake.getBody()) {
            filled.add(n);
        }
        
        for (int i = 0; i <= SQUAREWIDTH; i++) {
            for (int y = 0; y <= SQUAREHEIGHT; y++) {
                Node n = new Node(i, y);
                boolean si = true;
                for (int z = 0; z < filled.size(); z++) {
                    if (n.compareTo(filled.get(z)) == 0) {
                        si = false;
                        break;
                    }
                }
                if (si) {
                    list.add(n);
                }
            }
        }
         
        if (food != null) {filled.add(food.getPosition());}
        if (specialFood != null) {filled.add(specialFood.getPosition());}
                   
        return list;
    }
    
    public void start() {
        runnable = new MyRunnable(this);
        runnable.run();
    }
    
    public void pause() {
        pause = true;
        stopTimers();
                        
        switch (JOptionPane.showConfirmDialog(this, "¿Quiere reanudar la partida?", "PAUSA", JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.YES_OPTION:
                myInit();
                initComponents();
                start();
                break;
            default:
                System.exit(0);
                break;
        }
    }
    
    public void gameOver() {
        stopTimers();
                        
        switch (JOptionPane.showConfirmDialog(this, "¿Quiere hacer otra partida?", "PERDISTE", JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.YES_OPTION:
                myInit();
                scoreBoard.setScore(0);
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

        lastDirection = snake.getDirection();

        snake.paint(g2d);
        if (food != null && food.isFood()) {food.paint(g2d);}//
        if (specialFood != null && specialFood.isFood()) {specialFood.paint(g2d);}
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scoreBoard = new ScoreBoard();

        setBackground(new java.awt.Color(204, 255, 255));
        setLayout(new java.awt.BorderLayout());
        add(scoreBoard, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ScoreBoard scoreBoard;
    // End of variables declaration//GEN-END:variables

    public Snake getSnake() {
        return snake;
    }
    
    public boolean getPause() {
        return pause;
    }

    public Food getFood() {
        return food;
    }

    public Food getSpecialFood() {
        return specialFood;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {}
}
