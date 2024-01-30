public class ConcretePlayer implements Player{

    //fields and constructor ################################################

    private int wins;private boolean player_one;
    ConcretePlayer(boolean is_player_one){this.wins = 0;this.player_one = is_player_one;}

    //methods ################################################################

    public boolean isPlayerOne() {if(this.player_one){return true;}return false;}
    public void victory(){this.wins++;}
    public int getWins(){return this.wins;}
}
