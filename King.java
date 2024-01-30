import java.util.LinkedList;

public class King extends ConcertePiece{
    King(Player owner) {
        this.owner = owner;this.type = "♔";this.name="K7";
        this.squares = new LinkedList<Position>();
        this.squares_count=0;this.kills=0;
    }
}