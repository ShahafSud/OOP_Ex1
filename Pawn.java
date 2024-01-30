import java.util.LinkedList;

public class Pawn extends ConcertePiece {
    Pawn(Player owner, int n) {
        this.squares = new LinkedList<Position>();
        this.owner = owner;this.type = owner.isPlayerOne() ? "♙" : "♟";this.name=(owner.isPlayerOne() ? "D" : "A")+n;
        this.squares_count=0;this.kills=0;
    }
}
