import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

public class GameLogic implements PlayableLogic{
    private Board board;
    private Stack<MoveObj> history;
    private final ConcretePlayer player1, player2;
    private boolean king_captured, king_corner, first_player_turn;
    GameLogic(){
        this.player1= new ConcretePlayer(true);
        this.player2= new ConcretePlayer(false);
        this.king_captured=false;this.king_corner=false;
        this.reset();
    }
    public boolean move(Position a, Position b) {
        //is the move legal?
        if(!empty_straight_path(a,b))return false;
        ConcertePiece PieceIn_a=this.board.getPiece_pos(a);
        if((!Objects.equals(PieceIn_a.getType(), "♔")) & b.is_corner())return false;
        if (PieceIn_a.getOwner()==this.player1 & first_player_turn)return false;
        if (PieceIn_a.getOwner()==this.player2 & !first_player_turn)return false;
        if(PieceIn_a==null)return false;
        //move PieceIn_a from a to b & change turn
        this.board.putPiece(PieceIn_a,b);
        this.board.remove(a);
        PieceIn_a.count_squares(Math.abs(b.getX()-a.getX())+Math.abs(b.getY()-a.getY()));
        PieceIn_a.add_square(b);
        this.first_player_turn=!this.first_player_turn;
        //check all captures and build String[] captured
        //if king
        if(PieceIn_a instanceof King){
            if(b.is_corner()){this.king_corner=true;}
            this.history.add(new MoveObj(new String[4],a,b));return true;
        }
        //else
        String[] names_captured = new String[4];
        int x_b=b.getX(),y_b=b.getY();
        if(y_b!=0)if(this.board.getPiece_XY(x_b,y_b-1)!=null){names_captured[0]=is_captured(this.board.getPiece_XY(x_b,y_b-1),x_b,y_b-1,b);if(names_captured[0]!=null)PieceIn_a.kill(1);}
        if(x_b!=10)if(this.board.getPiece_XY(x_b+1,y_b)!=null){names_captured[1]=is_captured(this.board.getPiece_XY(x_b+1,y_b),x_b+1,y_b,b);if(names_captured[1]!=null)PieceIn_a.kill(1);}
        if(y_b!=10)if(this.board.getPiece_XY(x_b,y_b+1)!=null){names_captured[2]=is_captured(this.board.getPiece_XY(x_b,y_b+1),x_b,y_b+1,b);if(names_captured[2]!=null)PieceIn_a.kill(1);}
        if(x_b!=0)if(this.board.getPiece_XY(x_b-1,y_b)!=null){names_captured[3]=is_captured(this.board.getPiece_XY(x_b-1,y_b),x_b-1,y_b,b);if(names_captured[3]!=null)PieceIn_a.kill(1);}
        //from GameLogicTest it is apparent that king captures should not be counted as captures (the only kill in the output is defensive)
        for(int i=0;i<4;i++){
            if(names_captured[i]!=null)if(names_captured[i].contains("K"))PieceIn_a.kill(-1);
        }
        //remove captured pieces
        if(names_captured[0]!=null & y_b!=0)this.board.remove(new Position(x_b,y_b-1));
        if(names_captured[1]!=null & x_b!=10)this.board.remove(new Position(x_b+1,y_b));
        if(names_captured[2]!=null & y_b!=10)this.board.remove(new Position(x_b,y_b+1));
        if(names_captured[3]!=null & x_b!=0)this.board.remove(new Position(x_b-1,y_b));
        //Create MoveObj and change turn
        this.history.add(new MoveObj(names_captured,a,b));
        if(this.king_captured){player2.victory();this.print_analytics(player2);}
        if(this.king_corner){player1.victory();this.print_analytics(player1);}
        return true;
    }
    private boolean empty_straight_path(Position p1, Position p2){
        int x1=p1.getX(), x2= p2.getX(), y1=p1.getY(), y2=p2.getY();
        if(x1==x2 & y1==y2)return false;
        if(x1!=x2 & y1!=y2)return false;
        if(y1<y2)for(int y=y1+1;y<=y2;y++){if(this.board.getPiece_XY(x1,y)!=null)return false;}
        if(y1>y2)for(int y=y1-1;y>=y2;y--){if(this.board.getPiece_XY(x1,y)!=null)return false;}
        if(x1<x2)for(int x=x1+1;x<=x2;x++){if(this.board.getPiece_XY(x,y1)!=null)return false;}
        if(x1>x2){for(int x=x1-1;x>=x2;x--){if(this.board.getPiece_XY(x,y1)!=null)return false;}
        }
        return true;
    }
    public String is_captured(ConcertePiece piece,int pos_x, int pos_y, Position move_played){
        if(piece instanceof King) {
            if(pos_x != 0){if(this.board.getPiece_XY(pos_x - 1,pos_y) == null || this.board.getPiece_XY(pos_x - 1,pos_y).getOwner()==this.player1) return null;}
            if(pos_x != 10){if(this.board.getPiece_XY(pos_x + 1,pos_y) == null || this.board.getPiece_XY(pos_x + 1,pos_y).getOwner()==this.player1) return null;}
            if(pos_y != 0){if(this.board.getPiece_XY(pos_x,pos_y - 1) == null|| this.board.getPiece_XY(pos_x,pos_y - 1).getOwner()==this.player1) return null;}
            if(pos_y != 10){if(this.board.getPiece_XY(pos_x,pos_y + 1) == null || this.board.getPiece_XY(pos_x,pos_y + 1).getOwner()==this.player1) return null;}
            this.king_captured=true;return piece.getName();
        }
        else{
            if (piece.getOwner()==this.board.getPiece_pos(move_played).getOwner())return null;
            int vecX=pos_x-move_played.getX(),vecY=pos_y-move_played.getY();
            if(pos_x+vecX<0 || pos_x+vecX>10)return piece.getName();
            if(pos_y+vecY<0 || pos_y+vecY>10)return piece.getName();
            if(this.board.getPiece_XY(pos_x+vecX,pos_y+vecY)!=null){if(this.board.getPiece_XY(pos_x+vecX,pos_y+vecY).getOwner()!=piece.getOwner() & (!(this.board.getPiece_XY(pos_x+vecX,pos_y+vecY) instanceof King)))return piece.getName();}
            Position corner_suspect = new Position(pos_x+vecX,pos_y+vecY);
            if(corner_suspect.is_corner()){
                Position suspect_position_for_pawn=null;
                if (vecY==0 & pos_y==0)suspect_position_for_pawn=new Position(pos_x+vecX,pos_y+vecY+1);
                if (vecY==0 & pos_y==10)suspect_position_for_pawn=new Position(pos_x+vecX,pos_y+vecY-1);
                if (vecX==0 & pos_x==0)suspect_position_for_pawn=new Position(pos_x+vecX+1,pos_y+vecY);
                if (vecX==0 & pos_x==10)suspect_position_for_pawn=new Position(pos_x+vecX-1,pos_y+vecY);
                if (this.getPieceAtPosition(suspect_position_for_pawn)!=null){
                    if (this.getPieceAtPosition(suspect_position_for_pawn).getOwner()!=piece.getOwner()){
                        this.getPieceAtPosition(suspect_position_for_pawn);
                        return piece.getName();
                    }
                }
            }
        }
        return null;
    }
    public Piece getPieceAtPosition(Position position){return board.getPiece_pos(position);}
    public Player getFirstPlayer() {return this.player1;}
    public Player getSecondPlayer() {return this.player2;}
    public boolean isGameFinished() {return(this.king_captured||this.king_corner);}
    public boolean isSecondPlayerTurn() {
        return !this.first_player_turn;
    }
    public void reset() {
        this.king_captured=false;
        this.king_corner=false;
        this.first_player_turn=true;
        this.board=new Board(this.player1, this.player2);
        this.history = new Stack<>();
    }
    @Override
    public void undoLastMove() {
        //initial definitions and checks
        if(this.history.isEmpty())return;
        this.first_player_turn=!this.first_player_turn;
        MoveObj last_move = this.history.pop();
        String[] captured=last_move.getCaptures();
        Position to=last_move.getTo(),from=last_move.getFrom();
        this.board.edit_pos_for_undo(to.getX(),to.getY());
        ConcertePiece piece_moved=this.board.getPiece_pos(to);
        piece_moved.remove_square();
        piece_moved.count_squares(-1*(Math.abs(from.getX()-to.getX())+Math.abs(from.getY()-to.getY())));
        this.board.putPiece(piece_moved,from);
        this.board.remove(to);
        if(captured[0]!=null){this.board.putPiece(this.board.getPiece_name(captured[0]),new Position(to.getX(),to.getY()-1));piece_moved.kill(-1);}
        if(captured[1]!=null){this.board.putPiece(this.board.getPiece_name(captured[1]),new Position(to.getX()+1,to.getY()));piece_moved.kill(-1);}
        if(captured[2]!=null){this.board.putPiece(this.board.getPiece_name(captured[2]),new Position(to.getX(),to.getY()+1));piece_moved.kill(-1);}
        if(captured[3]!=null){this.board.putPiece(this.board.getPiece_name(captured[3]),new Position(to.getX()-1,to.getY()));piece_moved.kill(-1);}
    }
    public int getBoardSize() {return 11;}
    private void print_analytics(ConcretePlayer winner){
        ConcertePiece[] all_pieces = this.board.get_all_pieces();
        //print pieces history
        Arrays.sort(all_pieces, new Comperators(winner,"history"));
        for (ConcertePiece p : all_pieces)p.print_history();
        System.out.println("*".repeat(75));
        //print pieces kills
        Arrays.sort(all_pieces, new Comperators(winner,"kills"));
        for (ConcertePiece p : all_pieces)p.print_k();
        System.out.println("*".repeat(75));
        //print pieces squares count
        Arrays.sort(all_pieces, new Comperators(winner,"squares"));
        for (ConcertePiece p : all_pieces)p.print_s();
        System.out.println("*".repeat(75));
        //print positions
        Position[] all_positions=this.board.get_all_pos();
        Arrays.sort(all_positions, new Comperators_pos());
        for (Position pos : all_positions){
            if(pos.get_unique_pieces_count()>1)System.out.println(pos.toString()+pos.get_unique_pieces_count()+" pieces");
        }
        System.out.println("*".repeat(75));
    }
}
