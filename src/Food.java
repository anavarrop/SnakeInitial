import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Food {
    private final Node position;
    private final boolean special;
    public int timerSpecialFood = 1;
    private boolean food;

    public Food(List<Node> position, boolean special) { 
        this.special = special;
        this.position = position.get((int)(Math.random() * (position.size() - 1)));
        food = true;
    }
    
    public boolean isFood() {
        return food;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setFood(boolean food) {
        this.food = food;
    }
    
    public void desapear() {    
        food = false;
    }

    public void setTimer(int timerSpecialFood) {
        this.timerSpecialFood = timerSpecialFood;
    }

    public Node getPosition() {
        return position;
    }
    
    public void paint(Graphics2D g2d) {
        if (!food) {return;}
        if (special) {Util.drawSquare(g2d, position.getRow(), position.getCol(), 25, 25, Color.green);}
        else {Util.drawSquare(g2d, position.getRow(), position.getCol(), 25, 25, Color.red);}
    }
}
