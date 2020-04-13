
import javax.swing.JFrame;


public class Game extends JFrame {

    static Board b;
    public Game() {
        b = new Board(25, 25);
        add(b);
        
        setSize(642, 665);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
        public static void main(String[] args) {
        Game game = new Game();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        b.run();
    }
}
