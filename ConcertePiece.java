import java.util.LinkedList;
public abstract class ConcertePiece implements Piece {
    //fields and constructor ################################################

    protected Player owner;protected String type, name;protected int kills, squares_count;
    protected LinkedList <Position> squares;

    //methods ################################################################

    public Player getOwner() {return this.owner;}
    public String getType() {return this.type;}
    public String getName() {return this.name;}
    public int getKills() {return this.kills;}
    public int getSC() {return this.squares_count;}
    public int get_move_number() {return this.squares.size();}
    public void kill(int plus){this.kills+=plus;}
    public void count_squares(int s) {this.squares_count+=s;}
    public void add_square(Position p){this.squares.add(p);}
    public void remove_square(){this.squares.remove();}
    public void print_k(){if(this.kills>0)System.out.println(this.name + ": " + this.kills + " kills");}
    public void print_s(){if(this.squares_count>0)System.out.println(this.name + ": " + this.squares_count + " squares");}
    public void print_history(){
        if(this.squares.size()>1)System.out.println(this.name + ": " + this.squares);}
}