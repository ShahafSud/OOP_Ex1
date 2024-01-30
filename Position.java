import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Position {
    private ConcertePiece piece;
    private LinkedList<String> pieces_names;
    private final int x,y;
    public String toString() {
        return "(" + x + ", " + y + ')';}
    Position(int x, int y) {
        this.piece =null;
        this.pieces_names=new LinkedList<>();
        this.x = x;
        this.y = y;
    }
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public ConcertePiece getPiece(){return this.piece;}
    public void setPiece(ConcertePiece p){this.piece =p;this.pieces_names.add(p.getName());}
    public void empty(){this.piece =null;}

    public void undoName(){
        this.pieces_names.remove();
    }
    public boolean is_corner(){return ((this.x==0||this.x==10) & (this.y==0||this.y==10));}
    public int get_unique_pieces_count(){
        Set<String> uniquePieces = new HashSet<>(this.pieces_names);
        return uniquePieces.size();
    }
}