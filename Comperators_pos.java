import java.util.Comparator;

public class Comperators_pos implements Comparator<Position> {
    public Comperators_pos() {}

    public int compare(Position pos1, Position pos2) {
         int comp_out = Integer.compare(pos2.get_unique_pieces_count(),pos1.get_unique_pieces_count());
         if(comp_out!=0)return comp_out;
         comp_out = Integer.compare(pos1.getX(),pos2.getX());
         if(comp_out!=0)return comp_out;
        comp_out = Integer.compare(pos1.getY(),pos2.getY());
        return comp_out;
    }
}
