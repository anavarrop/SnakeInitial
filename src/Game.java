
import javax.swing.JFrame;


public class Game extends JFrame {

    public Game() {
        Board b = new Board(25, 25);
        add(b);
        //b.start();
        
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
        public static void main(String[] args) {

        Game game = new Game();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    } 
}
