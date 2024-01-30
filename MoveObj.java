public class MoveObj {
    private final String[] captures;private final Position from;private final Position to;

    public MoveObj(String[] captures, Position from, Position to) {
        // captures = {north, east, south, west} with names of the pieces(pawns)
        this.captures = captures;this.from = from;this.to = to;
    }
    public String[] getCaptures() {return captures;}
    public Position getFrom() {return from;}
    public Position getTo() {return to;}
}
