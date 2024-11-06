package sample;

public class Country {
    private String name = "";
    private int land = 0;
    private int gold = 0;
    private int soldiers = 0;
    private int tanks = 0;


    public Country(int Num){

        int len = (int) (Math.floor(Math.random() * 4) + 1);
        String str = "";

        if(Num >= 1 && Num <= 3) {
            land = (int) (Math.floor(Math.random() * 500) + 1000);
            gold = (int) (Math.floor(Math.random() * 10000) + 1000);
            soldiers = (int) (Math.floor(Math.random() * 20) + 10);
            tanks = (int) (Math.floor(Math.random() * 10) + 5);

            str = "Land Of ";
        }
        else if(Num >= 4 && Num <=6){
            land = (int) (Math.floor(Math.random() * 500) + 2000);
            gold = (int) (Math.floor(Math.random() * 20000) + 10000);
            soldiers = (int) (Math.floor(Math.random() * 20) + 30);
            tanks = (int) (Math.floor(Math.random() * 10) + 10);

            str = "Republic Of ";
        }
        else if(Num >= 7 && Num <= 9){
            land = (int) (Math.floor(Math.random() * 500) + 3000);
            gold = (int) (Math.floor(Math.random() * 30000) + 2000);
            soldiers = (int) (Math.floor(Math.random() * 10) + 15);
            tanks = (int) (Math.floor(Math.random() * 10) + 15);

            str = "United ";
        }
        else if(Num == 10){
            land = (int) (Math.floor(Math.random() * 1000000) + 50000);
            gold = (int) (Math.floor(Math.random() * 10000000) + 100000);
            soldiers = (int) (Math.floor(Math.random() * 10) + 40);
            tanks = (int) (Math.floor(Math.random() * 15) + 20);

            str = "Gods Land ";
        }

        String end = "DO";

        for(int i = 0;i<len;i++){
            int temp = (int) (Math.floor(Math.random() * 26) + 65);

            str = str + (char) temp;
        }
        name = str + end;
    }

    public String getName(){
        return name;
    }

    public int getLand(){
        return land;
    }

    public int getGold(){
        return gold;
    }

    public int getTanks(){
        return tanks;
    }

    public int getSoldiers(){
        return soldiers;
    }

    public void setSoldiers(int s){
        soldiers = s;
    }

    public void setTanks(int t){
        tanks = t;
    }

    public void setGold(int newGold){
        gold = newGold;
    }

    public void setLand(int newLand){
        land = newLand;
    }
}
