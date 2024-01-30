import java.util.HashMap;

public class Board {
    private Position[][] board;
    private HashMap<String,ConcertePiece> all_pieces;
    private void set_new_game(ConcretePlayer P1,ConcretePlayer P2){
        int[][] locations_2 ={{3,0,1},{4,0,2},{5,0,3},{6,0,4},{7,0,5},{5,1,6},{0,3,7},
                {10,3,8},{0,4,9},{10,4,10},{0,5,11},{1,5,12},{9,5,13},{10,5,14},{0,6,15},
                {10,6,16},{0,7,17},{10,7,18},{5,9,19},{3,10,20},{4,10,21},{5,10,22},
                {6,10,23},{7,10,24}};
        int[][] locations_1 ={{5,3,1},{4,4,2},{5,4,3},{6,4,4},{3,5,5},{4,5,6},{6,5,8},
                {7,5,9},{4,6,10},{5,6,11},{6,6,12},{5,7,13}};
        for(int[] piece_data : locations_1){
            int x = piece_data[0],y = piece_data[1],n = piece_data[2];
            Pawn pawn = new Pawn(P1,n);
            this.all_pieces.put(pawn.getName(),pawn);
            this.board[x][y].setPiece(pawn);
            pawn.add_square(new Position(x,y));
        }
        for(int[] piece_data : locations_2){
            int x = piece_data[0],y = piece_data[1],n = piece_data[2];
            Pawn pawn = new Pawn(P2,n);
            this.all_pieces.put(pawn.getName(),pawn);
            this.board[x][y].setPiece(pawn);
            pawn.add_square(new Position(x,y));
        }
        King k =new King(P1);
        this.all_pieces.put(k.getName(),k);
        this.board[5][5].setPiece(k);
        k.add_square(new Position(5,5));
    }
    public ConcertePiece getPiece_name(String name){return this.all_pieces.get(name);}
    public ConcertePiece getPiece_pos(Position p){
        return this.board[p.getX()][p.getY()].getPiece();
    }
    public ConcertePiece getPiece_XY(int x,int y){return this.board[x][y].getPiece();}

    Board(ConcretePlayer P1, ConcretePlayer P2) {
        this.board = new Position[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                this.board[i][j] = new Position(i, j);
            }
        }
        this.all_pieces=new HashMap<String,ConcertePiece>();
        set_new_game(P1, P2);
    }
    public void putPiece(ConcertePiece p, Position pos){this.board[pos.getX()][pos.getY()].setPiece(p);}
    public void remove(Position pos){this.board[pos.getX()][pos.getY()].empty();}
    public ConcertePiece[] get_all_pieces(){
        ConcertePiece[] ans = new ConcertePiece[37];int i=0;
        for (ConcertePiece p : this.all_pieces.values())ans[i++]=p;
        return ans;
    }

    public Position[] get_all_pos(){
        Position[] ans = new Position[121];
        for(int i=0;i<11;i++){for(int j=0;j<11;j++){ans[(i*11)+j]=this.board[i][j];}}
        return ans;
    }
    public void edit_pos_for_undo(int x, int y){this.board[x][y].undoName();}
}
