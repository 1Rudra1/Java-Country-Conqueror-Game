package sample;


public class Player {


    private int soldiers = 0;
    private int tanks = 0;
    private int gold = 5000;
    private int land = 1000;


    public Player(int numSoldiers, int numTanks){
        soldiers = numSoldiers;
        tanks = numTanks;
    }

    public int getSoldiers(){
        return soldiers;
    }

    public int getTanks(){
        return tanks;
    }

    public int getGold(){
        return gold;
    }

    public int getLand(){
        return land;
    }

    public void setSoldiers(int newSoldiers){
        soldiers = newSoldiers;
    }

    public void setTanks(int newTanks){
        tanks = newTanks;
    }

    public void setGold(int newGold){
        gold = newGold;
    }

    public void setLand(int newLand){
        land = newLand;
    }



}
