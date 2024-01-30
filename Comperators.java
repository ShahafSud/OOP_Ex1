import java.util.Comparator;
import java.util.Objects;

public class Comperators implements Comparator<ConcertePiece> {
    private ConcretePlayer winner;private String mode;
    public Comperators(ConcretePlayer winner, String mode) {this.winner=winner;this.mode=mode;}

    @Override
    public int compare(ConcertePiece p1, ConcertePiece p2) {
        if(Objects.equals(this.mode,"history")){
            if(p1.getOwner()==this.winner & p2.getOwner()!=this.winner)return Integer.compare(0,1);
            if(p1.getOwner()!=this.winner & p2.getOwner()==this.winner)return Integer.compare(1,0);
            int comp_out = Integer.compare(p1.get_move_number(), p2.get_move_number());
            if (comp_out != 0)return comp_out;
            int num1= Integer.parseInt(p1.getName().replace("D","").replace("A","").replace("K",""));
            int num2= Integer.parseInt(p2.getName().replace("D","").replace("A","").replace("K",""));
            return Integer.compare(num1, num2);
        }
        if(Objects.equals(this.mode,"kills")){
            int comp_out = Integer.compare(p2.getKills(), p1.getKills());
            if(comp_out!=0)return comp_out;
            int num1= Integer.parseInt(p1.getName().replace("D","").replace("A","").replace("K",""));
            int num2= Integer.parseInt(p2.getName().replace("D","").replace("A","").replace("K",""));
            comp_out = Integer.compare(num1, num2);
            if(comp_out!=0)return comp_out;
            if(p1.getOwner()==this.winner & p2.getOwner()!=this.winner)return Integer.compare(0,1);
            if(p1.getOwner()!=this.winner & p2.getOwner()==this.winner)return Integer.compare(1,0);
            return 0;
        }
        if(Objects.equals(this.mode,"squares")){
            int comp_out = Integer.compare(p2.getSC(), p1.getSC());
            if(comp_out!=0)return comp_out;
            int num1= Integer.parseInt(p1.getName().replace("D","").replace("A","").replace("K",""));
            int num2= Integer.parseInt(p2.getName().replace("D","").replace("A","").replace("K",""));
            comp_out = Integer.compare(num1, num2);
            if(comp_out!=0)return comp_out;
            if(p1.getOwner()==this.winner & p2.getOwner()!=this.winner)return Integer.compare(0,1);
            if(p1.getOwner()!=this.winner & p2.getOwner()==this.winner)return Integer.compare(1,0);
            return 0;
        }
        return 0;
    }
}
