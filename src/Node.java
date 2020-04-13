public class Node {
    private Node next;
    private int row;
    private int col;
    
    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public Node(Node node) {
        next = node.next;
        row = node.row;
        col = node.col;
    }
    
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    
    public void moveRow(int i, int limit) {
        if (i == 0) {return;}
        if (limit < row + i || row + i < 0) {
            if (limit < row + i) {
                row = 0;
            } else {
                row = limit;
            }
        } else {
            row = row + i;
        }
    }
    
    public void moveCol(int i, int limit) {
        if (i == 0) {return;}
        if (limit < col + i || col + i < 0) {
            if (limit < col + i) {
                col = 0;
            } else {
                col = limit;
            }
        } else {
            col = col + i;
        }
    }
    
    public void moveToNext() {
        row = next.getRow();
        col = next.getCol();
    }

    public void setNext(Node previous) {
        this.next = previous;
    }

    public Node getNext() {
        return next;
    }
}
