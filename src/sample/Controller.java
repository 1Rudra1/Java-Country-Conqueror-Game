
package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Controller {
    private int chosenCountry;
    private int Round;
    private int TypeTroopCounter = 1;
    private int playerSoldierTroops = 0;
    private int playerTanksTroops = 0;
    private int SoldierCasualties = 0;
    private int TankCasualties = 0;
    private int EnemySoldierCasualties = 0;
    private int EnemyTankCasualties = 0;
    private int totalPrisoners = 0;
    private int totalSoldierCasualties = 0;
    private int totalTankCasualties = 0;
    private int totalEnemySoldierCasualties = 0;
    private int totalEnemyTankCasualties = 0;
    private int totalGoldSpent = 0;
    private int totalPurchases = 0;
    private boolean SoldiersAlive = true;
    private boolean TanksAlive = true;
    private boolean done = false;
    private ArrayList<Integer> arrSoldiers = new ArrayList<>();
    private ArrayList<Integer> arrEnemySoldiers = new ArrayList<>();
    private ArrayList<Integer> arrTanks = new ArrayList<>();
    private ArrayList<Integer> arrEnemyTanks = new ArrayList<>();
    private ArrayList<Country> Countries = new ArrayList<>();
    private ArrayList<Integer> arrSoldierCasualties = new ArrayList<>();
    private ArrayList<Integer> arrEnemySoldierCasualties = new ArrayList<>();
    private ArrayList<Integer> arrTankCasualties = new ArrayList<>();
    private ArrayList<Integer> arrEnemyTankCasualties = new ArrayList<>();

    Player player = new Player(30, 20);
    Image image = new Image("sample/MapImage.png");
    @FXML
    ListView lstEvents;
    @FXML
    Label lblTitle,lblPrisoners, lblShopTitle, lblCountryTitle, lblAttackSoldiers, lblAttackTanks, lblTypeOfTroop, lblLand, lblEnemyLand, lblDescription, lblNumSoldiers, lblNumTanks, lblGold, lblNumEnemySoldiers, lblNumEnemyTanks, lblEnemyGold, lblNumCas, lblNumEnemyCas, lblResult;
    @FXML
    Button btnAttack;
    @FXML
    TextField txtNumTroops, txtNumGold;
    @FXML
    ListView lstCountries, lstConquered, lstBattleStats, lstShop;
    @FXML
    ImageView imageView;

    @FXML
    Button btnRound, btnRetreat, btnAttackAll, btnRestart;

    //Acts like a start button for setting up the game, but its runs on its own
    @FXML
    public void initialize() {
        imageView.setImage(image);
        lblNumSoldiers.setText("0");
        lblNumTanks.setText("0");
        lblLand.setText("100");
        lblPrisoners.setText("0");
        btnRound.setVisible(false);
        btnRetreat.setVisible(false);
        btnAttack.setDisable(true);
        btnRetreat.setVisible(false);
        btnRestart.setVisible(false);
        lblGold.setText("" + player.getGold());
        lblNumSoldiers.setText("" + player.getSoldiers());
        lblNumTanks.setText("" + player.getTanks());
        lstShop.getItems().add("Buy Soldiers");
        lstShop.getItems().add("Buy Tanks");
        lstShop.getItems().add("Sell Land For Gold");
        lstShop.getItems().add("Sell Soldiers For Gold");
        lstShop.getItems().add("Sell Tanks For Gold");
        lstShop.getItems().add("Upgrade Soldiers To Tanks");
        lstShop.getItems().add("Train Prisoners To Soldiers");



        for (int i = 0; i < 10; i++) {
            Countries.add(new Country(i + 1));
        }

        for (Country country : Countries) {
            lstCountries.getItems().add(country.getName());
        }

    }

    //Runs the stats method
    @FXML
    private void Stats() {
        stats();
    }


    //Resets some parts of the battle for retreating
    @FXML
    private void Retreat() {
        Round = 0;
        lblResult.setText("You have Retreated");
        lblNumCas.setText("?");
        lblNumEnemyCas.setText("?");
        chosenCountry = 0;
        btnAttack.setDisable(true);
        btnRound.setVisible(false);
        btnRetreat.setVisible(false);
        playerSoldierTroops = 0;
        playerTanksTroops = 0;
        lblAttackSoldiers.setText("?");
        lblAttackTanks.setText("?");
        arrEnemySoldierCasualties.clear();
        arrEnemyTankCasualties.clear();
        arrSoldierCasualties.clear();
        arrTankCasualties.clear();
        lstBattleStats.getItems().clear();
        System.out.println();
    }

    //Acts like the attack method; it run the required attack methods
    @FXML
    private void Rounds() {

        btnAttack.setDisable(true);
        btnRound.setDisable(true);
        if (player.getSoldiers() == 0 && Countries.get(chosenCountry).getTanks() == 0) {
            AttackTankSoldiers();
            return;
        }
        else if(player.getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0){
            AttackSoldierTanks();
            return;
        }

        if(SoldiersAlive){
            AttackSoldiers();

        }
        if(TanksAlive){
            AttackTanks();
        }
    }


    //Runs the description method
    @FXML
    private void Description() {
        int itemChosen = lstShop.getSelectionModel().getSelectedIndex();
        descriptions(itemChosen);
    }

    //Runs the shop method
    @FXML
    private void Shop() {
        if (player.getGold() <= 0) {
            lblDescription.setText("Sorry, you don't have enough money to purchase this");
        } else {
            int itemChosen = lstShop.getSelectionModel().getSelectedIndex();
            shop(itemChosen);
        }

    }


    //Sets attacking troops to all the possible troops
    @FXML
    private void AttackAll(){
        playerSoldierTroops = player.getSoldiers();
        playerTanksTroops = player.getTanks();
        if(Round == 0){
            btnAttack.setDisable(false);
        }
        if(Round >= 1){
            btnRound.setDisable(false);
        }
    }

    //Runs attack methods depending on the situation, and sets up for the first attack
    @FXML
    private void Attack() {
        lstBattleStats.getItems().clear();
        arrEnemySoldiers.clear();
        btnAttack.setDisable(true);
        btnRound.setVisible(true);
        btnRound.setDisable(true);
        btnRetreat.setVisible(true);

        for (int counter = 0; counter < Countries.get(chosenCountry).getSoldiers(); counter++) {
            arrEnemySoldiers.add((int) (Math.random() * 2) + 1);
        }

        for (int counter = 0; counter < Countries.get(chosenCountry).getTanks(); counter++) {
            arrEnemyTanks.add((int) (Math.random() * 2) + 2);
        }



        if (player.getSoldiers() == 0 && Countries.get(chosenCountry).getTanks() == 0) {
            AttackTankSoldiers();
            return;
        }
        else if(player.getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0){
            AttackSoldierTanks();
            return;
        }

        if(SoldiersAlive){
            AttackSoldiers();
        }
        if(TanksAlive){
            AttackTanks();
        }

       for(int i = 0; i<1; i++){
          lstShop.getItems().add("Earn Gold For Kills");
       }

    }

    //Shows the player they won
    @FXML
    private void Win(){
        FinalStats();
        lblTitle.setText("Winner!");
        lblResult.setText("You Win!");
        lblNumEnemyCas.setText("");
        lblNumCas.setText("");
    }

    //Restarts the game
    @FXML
    private void Restart(){
        chosenCountry = 0;
        Round = 0;
        TypeTroopCounter = 1;
        playerSoldierTroops = 0;
        playerTanksTroops = 0;
        SoldierCasualties = 0;
        TankCasualties = 0;
        EnemySoldierCasualties = 0;
        EnemyTankCasualties = 0;
        totalPrisoners = 0;
        totalSoldierCasualties = 0;
        totalTankCasualties = 0;
        totalEnemySoldierCasualties = 0;
        totalEnemyTankCasualties = 0;
        totalGoldSpent = 0;
        totalPurchases = 0;
        SoldiersAlive = true;
        TanksAlive = true;
        done = false;
        arrSoldiers.clear();
        arrEnemySoldiers.clear();
        arrTanks.clear();
        arrEnemyTanks.clear();
        Countries.clear();
        arrSoldierCasualties.clear();
        arrEnemySoldierCasualties.clear();
        arrTankCasualties.clear();
        arrEnemyTankCasualties.clear();
        player.setSoldiers(30);
        player.setTanks(20);
        player.setGold(5000);
        player.setLand(1000);
        lblNumTanks.setText("");
        lblNumSoldiers.setText("");
        lblGold.setText("");
        lblLand.setText("");
        lblTitle.setText("Country Conqueror");
        lblPrisoners.setText("?");
        lblShopTitle.setText("Shop");
        lblCountryTitle.setText("Countries To Conquer");
        lblAttackSoldiers.setText("?");
        lblAttackTanks.setText("?");
        lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
        lblEnemyLand.setText("");
        lblDescription.setText("Description");
        lblNumEnemySoldiers.setText("");
        lblNumEnemyTanks.setText("");
        lblEnemyGold.setText("");
        lblNumCas.setText("");
        lblNumEnemyCas.setText("");
        lblResult.setText("Result");
        txtNumTroops.clear();
        txtNumGold.clear();
        lstCountries.getItems().clear();
        lstConquered.getItems().clear();
        lstBattleStats.getItems().clear();
        lstShop.getItems().clear();
        initialize();
    }




    //Shows enemy stats on labels
    public void stats() {
        chosenCountry = lstCountries.getSelectionModel().getSelectedIndex();
        lblNumEnemySoldiers.setText("" + Countries.get(chosenCountry).getSoldiers());
        lblNumEnemyTanks.setText("" + Countries.get(chosenCountry).getTanks());
        lblEnemyGold.setText("" + Countries.get(chosenCountry).getGold());
        lblEnemyLand.setText("" + Countries.get(chosenCountry).getLand());
    }

    //Shows the battle stats on labels
    public void BattleStats() {
        lstBattleStats.getItems().clear();
        if(Round >= 1){
            for (int counter = 0; counter < Round; counter++) {
                lstBattleStats.getItems().add("     ROUND " + (counter + 1) );
                lstBattleStats.getItems().add("Player Soldier Casualties : "   + arrSoldierCasualties.get(counter));
                lstBattleStats.getItems().add("Enemy Soldier Casualties : "  + arrEnemySoldierCasualties.get(counter));
                lstBattleStats.getItems().add("Player Tank Casualties : "  + arrTankCasualties.get(counter));
                lstBattleStats.getItems().add("Enemy Tank Casualties : "  + arrEnemyTankCasualties.get(counter));
                lstBattleStats.getItems().add("");
            }
        }

    }

    //Shows all the stats when the game ends in some way
    public void FinalStats() {
        btnRestart.setVisible(true);
        lstBattleStats.getItems().clear();
        lstCountries.getItems().clear();
        lstShop.getItems().clear();
        lblCountryTitle.setText("Battle Final Stats");
        lblShopTitle.setText("Shop Final Stats");
        lstCountries.getItems().add("Total Player Soldier Deaths: " + totalSoldierCasualties);
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Player Tank Deaths: " + totalTankCasualties);
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Enemy Soldier Deaths: " + totalEnemySoldierCasualties);
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Enemy Tank Deaths: " + totalEnemyTankCasualties);
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Soldier Deaths: " + (totalEnemySoldierCasualties + totalSoldierCasualties));
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Tank Deaths: " + (totalEnemyTankCasualties + totalTankCasualties));
        lstCountries.getItems().add("");
        lstCountries.getItems().add("Total Troop Deaths: " + (totalEnemySoldierCasualties + totalSoldierCasualties + totalTankCasualties + totalEnemyTankCasualties));

        lstShop.getItems().add("Total Gold Spent: " + totalGoldSpent);
        lstShop.getItems().add("");
        lstShop.getItems().add("Total Purchases Made: " + totalPurchases);
        lstShop.getItems().add("");
        lstShop.getItems().add("Total Gold Made: " + (totalGoldSpent + player.getGold()));
    }

    //Ends the game
    public void GameOver() {
        FinalStats();
        lblResult.setText("All Your Troops Died =(");
        lblTitle.setText("GAME OVER");
    }

    //Makes the player control a defeated country
    public void Conquered() {
        lblResult.setText("You Conquered And Looted The Country!");
        lblNumCas.setText("?");
        lblNumEnemyCas.setText("?");
        arrEnemySoldierCasualties.clear();
        arrEnemyTankCasualties.clear();

        for(int counter = 0; counter<Round; counter++){
            totalPrisoners = totalPrisoners + ((arrSoldierCasualties.get(counter) + arrTankCasualties.get(counter))/2);
        }

        lblPrisoners.setText(totalPrisoners + "");
        arrSoldierCasualties.clear();
        arrTankCasualties.clear();
        Round = 0;


        player.setGold(player.getGold() + Countries.get(chosenCountry).getGold());
        Countries.get(chosenCountry).setGold(0);
        lblGold.setText("" + player.getGold());
        lblEnemyGold.setText("" + Countries.get(chosenCountry).getGold());

        player.setLand(player.getLand() + Countries.get(chosenCountry).getLand());
        lblLand.setText(player.getLand() + "");
        Countries.get(chosenCountry).setLand(0);
        lblEnemyLand.setText(Countries.get(chosenCountry).getLand() + "");


        lstCountries.getItems().remove(Countries.get(chosenCountry).getName());
        lstConquered.getItems().add((Countries.get(chosenCountry).getName()));
        Countries.remove(chosenCountry);


        btnAttack.setDisable(false);
        btnRound.setVisible(false);
        chosenCountry = 0;
    }

    //Allows the player to buy things from the shop listview
    public void shop(int itemChosen) {
        int payment = Integer.parseInt(txtNumGold.getText());


        if (itemChosen == 0) {
            int extraSoldiers = payment / 1000;
            if (extraSoldiers >= 1 && player.getGold() >= 1000) {
                player.setGold((player.getGold() + (payment%1000)) - payment);
                lblGold.setText(player.getGold() + "");
                totalGoldSpent = (payment-(payment%1000)) + totalGoldSpent;
                totalPurchases += 1;

                player.setSoldiers(extraSoldiers + player.getSoldiers());
                lblNumSoldiers.setText(player.getSoldiers() + "");

                lblDescription.setText("Thank You For Your Purchase!");
                txtNumGold.clear();
            } else {
                lblDescription.setText("Not Enough Gold To Purchase This Item");
            }
        }

        if (itemChosen == 1) {
            int extraTanks = payment / 3000;
            if (extraTanks >= 1 && player.getGold() >= 3000) {
                player.setGold((player.getGold() + payment%3000) - payment);
                lblGold.setText(player.getGold() + "");
                totalGoldSpent = (payment-(payment%3000)) + totalGoldSpent;
                totalPurchases+= 1;



                player.setTanks(extraTanks + player.getTanks());
                lblNumTanks.setText(player.getTanks() + "");

                lblDescription.setText("Thank You For Your Purchase!");
                txtNumGold.clear();
            }
            else {
                lblDescription.setText("Not Enough Gold To Purchase This Item");
            }
        }

        if(itemChosen == 2){
            int extraGold = (payment/1000)*1000;
            if (payment >= 1000) {
                player.setGold(player.getGold() + extraGold);
                lblGold.setText(player.getGold() + "");
                totalPurchases+=1;

                player.setLand(player.getLand() + payment%1000);
                player.setLand(player.getLand() - payment);
                lblLand.setText(player.getLand() + "");
                lblDescription.setText("Thank You For Your Purchase!");
                txtNumGold.clear();
            }

        }

        if(itemChosen == 3){
            int extraGold = payment*1000;
            if (payment >= 1) {
                totalPurchases+=1;
                player.setGold(player.getGold() + extraGold);
                lblGold.setText(player.getGold() + "");
                player.setSoldiers(player.getSoldiers() - payment);
                lblNumSoldiers.setText(player.getSoldiers() + "");
                lblDescription.setText("Thank You For Your Purchase!");
                txtNumGold.clear();
            }
        }

        if(itemChosen == 4){
            int extraGold = payment*3000;
            if (payment >= 1) {
                totalPurchases+=1;
                player.setGold(player.getGold() + extraGold);
                lblGold.setText(player.getGold() + "");
                player.setTanks(player.getTanks() - payment);
                lblNumTanks.setText(player.getTanks() + "");
                lblDescription.setText("Thank You For Your Purchase!");
                txtNumGold.clear();
            }
        }

        if (itemChosen == 5) {

            if(player.getGold() >= 4000 && payment >= 4000){
                if (Countries.get(chosenCountry).getSoldiers() == 0 && player.getTanks() == 0){
                    player.setGold(player.getGold() - payment);
                    player.setGold(player.getGold() + payment % 4000);
                    lblGold.setText(player.getGold() + "");
                    totalGoldSpent = (payment-(payment%4000)) + totalGoldSpent;
                    totalPurchases+=1;


                    player.setTanks(player.getSoldiers());
                    lblNumTanks.setText(player.getTanks() + "");

                    lblDescription.setText("Thank You For Your Purchase!");
                    txtNumGold.clear();
                }
                else{
                    lblDescription.setText("The current battle doesn't meet the requirements for this item to work \n Try Again After Reading The Item Description");
                    txtNumTroops.clear();
                }

            }
            else {
                lblDescription.setText("Not Enough Gold To Purchase This Item");
            }

        }

        if(itemChosen == 6){
            if(payment>= 100 && totalPrisoners>= 1){
                int extraPrisoners = payment/100;
                player.setGold((player.getGold() + (payment%100)) - payment);
                totalGoldSpent = (payment-(payment%100)) + totalGoldSpent;
                totalPurchases+=1;

                player.setSoldiers(player.getSoldiers() + extraPrisoners);
                lblGold.setText(player.getGold() + "");
                lblNumSoldiers.setText(player.getSoldiers() + "");
                txtNumGold.clear();
            }
            else{
                lblDescription.setText("Not Enough Gold or Prisoners To Purchase This Item");
            }

        }

        if(itemChosen == 7){
            if(payment >= 2 && player.getTanks() >= 2){
                totalPurchases+=1;
                player.setTanks(player.getTanks()-payment);
                lblNumTanks.setText("" + player.getTanks());
                player.setGold(player.getGold() +(((EnemySoldierCasualties + EnemyTankCasualties)/2)* 1000));
                lblGold.setText("" + player.getGold());
                txtNumGold.clear();

            }
            else{
                lblDescription.setText("Not Enough Tanks To Purchase This Item");
            }
        }


    }


    //Has soldiers attack other soldiers
    public void AttackSoldiers(){


        arrSoldiers.clear();
        for (int counter = 0; counter < playerSoldierTroops; counter++) {
            arrSoldiers.add((int) (Math.random() * 2) + 1);
        }


        if(!TanksAlive){
            Round++;
        }

        for (int index = Math.min(arrSoldiers.size(), arrEnemySoldiers.size()) - 1; index >= 0; index--) {
            if (arrEnemySoldiers.get(index) < arrSoldiers.get(index)) {
                arrEnemySoldiers.remove(index);
            } else if (arrEnemySoldiers.get(index) > arrSoldiers.get(index)) {
                arrSoldiers.remove(index);
            } else if ((arrEnemySoldiers.get(index)).equals(arrSoldiers.get(index))) {
                arrEnemySoldiers.remove(index);
                arrSoldiers.remove(index);
            }
        }


        SoldierCasualties = playerSoldierTroops - arrSoldiers.size();
        EnemySoldierCasualties = Countries.get(chosenCountry).getSoldiers() - arrEnemySoldiers.size();
        totalSoldierCasualties = totalSoldierCasualties + SoldierCasualties;
        totalEnemySoldierCasualties = totalEnemySoldierCasualties + EnemySoldierCasualties;
        lblNumCas.setText((SoldierCasualties + TankCasualties) + "");
        lblNumEnemyCas.setText((EnemySoldierCasualties + EnemyTankCasualties) + "");
        arrSoldierCasualties.add(playerSoldierTroops - arrSoldiers.size());
        arrEnemySoldierCasualties.add(Countries.get(chosenCountry).getSoldiers() - arrEnemySoldiers.size());
        BattleStats();

        if(player.getSoldiers() == 0){
            SoldiersAlive = false;
            SoldierCasualties = 0;
        }


        player.setSoldiers((player.getSoldiers() - playerSoldierTroops) + (arrSoldiers.size()));
        Countries.get(chosenCountry).setSoldiers(arrEnemySoldiers.size());

        lblNumSoldiers.setText("" + player.getSoldiers());
        lblNumEnemySoldiers.setText("" + Countries.get(chosenCountry).getSoldiers());

        lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
        lblAttackTanks.setText("?");
        lblAttackSoldiers.setText("?");

    if(!TanksAlive){
        if ((player.getTanks() + player.getSoldiers()) > (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("We Survived With More Troops!");
        } else if ((player.getTanks() + player.getSoldiers()) < (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("The Enemy Survived With More Troops");
        } else {
            lblResult.setText("Both Sides Have An Equal Amount \n Of Troops!");
        }
    }

        if (player.getTanks() == 0 && player.getSoldiers() == 0) {
            GameOver();
        } else if (Countries.get(chosenCountry).getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0) {
            Conquered();
        }
        else if(Countries.get(8).getSoldiers() == 0 && Countries.get(8).getTanks() == 0){
            Win();
        }
    }


    //Has tanks attack other tanks
    public void AttackTanks() {

        arrTanks.clear();
        for (int counter = 0; counter < playerTanksTroops; counter++) {
            arrTanks.add((int) (Math.random() * 2) + 2);
        }

            Round++;
        for (int index = Math.min(arrTanks.size(), arrEnemyTanks.size()) - 1; index >= 0; index--) {
            if (arrEnemyTanks.get(index) < arrTanks.get(index)) {
                arrEnemyTanks.remove(index);
            } else if (arrEnemyTanks.get(index) > arrTanks.get(index)) {
                arrTanks.remove(index);
            } else if ((arrEnemyTanks.get(index)).equals(arrTanks.get(index))) {
                arrEnemyTanks.remove(index);
                arrTanks.remove(index);
            }
        }


        TankCasualties = playerTanksTroops - arrTanks.size();
        EnemyTankCasualties = Countries.get(chosenCountry).getTanks() - arrEnemyTanks.size();
        totalTankCasualties = totalTankCasualties + TankCasualties;
        totalEnemyTankCasualties = totalEnemyTankCasualties + EnemyTankCasualties;
        lblNumCas.setText((SoldierCasualties + TankCasualties) + "");
        lblNumEnemyCas.setText((EnemySoldierCasualties + EnemyTankCasualties) + "");
        arrTankCasualties.add(playerTanksTroops - arrTanks.size());
        arrEnemyTankCasualties.add(Countries.get(chosenCountry).getTanks() - arrEnemyTanks.size());
        BattleStats();

        if(player.getTanks() == 0){
            TanksAlive = false;
            TankCasualties = 0;
        }

        player.setTanks((player.getTanks() - playerTanksTroops) + (arrTanks.size()));
        Countries.get(chosenCountry).setTanks(arrEnemyTanks.size());


        lblNumTanks.setText("" + player.getTanks());
        lblNumEnemyTanks.setText("" + Countries.get(chosenCountry).getTanks());
        lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
        lblAttackTanks.setText("?");
        lblAttackSoldiers.setText("?");



        //Result Label
        if ((player.getTanks() + player.getSoldiers()) > (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("We Survived With More Troops!");
        } else if ((player.getTanks() + player.getSoldiers()) < (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("The Enemy Survived With More Troops");
        } else {
            lblResult.setText("Both Sides Have An Equal Amount \n Of Troops!");
        }



        if (player.getTanks() == 0 && player.getSoldiers() == 0) {
            GameOver();
        } else if (Countries.get(chosenCountry).getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0) {
            Conquered();
        }
        else if(Countries.get(8).getSoldiers() == 0 && Countries.get(8).getTanks() == 0){
            Win();
        }


    }

    //Has tanks attack other soldiers
    public void AttackTankSoldiers() {

        if (playerTanksTroops > player.getTanks()) {
            lblResult.setText("You Don't Have That Many Tanks");
            lblNumCas.setText("TRY AGAIN");
            lblNumEnemyCas.setText("TRY AGAIN");
            return;
        }



        arrTanks.clear();
        for (int counter = 0; counter < playerTanksTroops; counter++) {
            arrTanks.add((int) (Math.random() * 2) + 2);
        }

        Round++;
        for (int index = Math.min(arrTanks.size(), arrEnemySoldiers.size()) - 1; index >= 0; index--) {
            if (arrEnemySoldiers.get(index) < arrTanks.get(index)) {
                arrEnemySoldiers.remove(index);
            } else if (arrEnemySoldiers.get(index) > arrTanks.get(index)) {
                arrTanks.remove(index);
            } else if ((arrEnemySoldiers.get(index)).equals(arrTanks.get(index))) {
                arrEnemySoldiers.remove(index);
                arrTanks.remove(index);
            }
        }

        TankCasualties = playerTanksTroops - arrTanks.size();
        EnemySoldierCasualties = Countries.get(chosenCountry).getSoldiers() - arrEnemySoldiers.size();
        totalTankCasualties = totalTankCasualties + TankCasualties;
        totalEnemySoldierCasualties = totalEnemySoldierCasualties + EnemySoldierCasualties;
        lblNumCas.setText((SoldierCasualties + TankCasualties) + "");
        lblNumEnemyCas.setText((EnemySoldierCasualties + EnemyTankCasualties) + "");
        arrTankCasualties.add(playerTanksTroops - arrTanks.size());
        arrSoldierCasualties.add(0);
        arrEnemySoldierCasualties.add(Countries.get(chosenCountry).getSoldiers() - arrEnemySoldiers.size());
        arrEnemyTankCasualties.add(0);
        BattleStats();

        if(player.getTanks() == 0){
            TanksAlive = false;
            TankCasualties = 0;
        }


        player.setTanks((player.getTanks() - playerTanksTroops) + (arrTanks.size()));
        Countries.get(chosenCountry).setSoldiers(arrEnemySoldiers.size());


        lblNumTanks.setText("" + player.getTanks());
        lblNumEnemySoldiers.setText("" + Countries.get(chosenCountry).getSoldiers());
        lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
        lblAttackTanks.setText("?");
        lblAttackSoldiers.setText("?");

        if ((player.getTanks() + player.getSoldiers()) > (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("We Survived With More Troops!");
        } else if ((player.getTanks() + player.getSoldiers()) < (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("The Enemy Survived With More Troops");
        } else {
            lblResult.setText("Both Sides Have An Equal Amount \n Of Troops!");
        }



        if (player.getTanks() == 0 && player.getSoldiers() == 0) {
            GameOver();
        } else if (Countries.get(chosenCountry).getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0) {
            Conquered();
        }
        else if(Countries.get(8).getSoldiers() == 0 && Countries.get(8).getTanks() == 0){
            Win();
        }
    }

    //Has soldiers attack other tanks
    public void AttackSoldierTanks(){

        if (playerSoldierTroops > player.getSoldiers()) {
            lblResult.setText("You Don't Have That Many Soldiers");
            lblNumCas.setText("TRY AGAIN");
            lblNumEnemyCas.setText("TRY AGAIN");
            return;
        }



        arrSoldiers.clear();
        for (int counter = 0; counter < playerSoldierTroops; counter++) {
            arrSoldiers.add((int) (Math.random() * 2) + 1);
        }


        Round++;
        for (int index = Math.min(arrSoldiers.size(), arrEnemyTanks.size()) - 1; index >= 0; index--) {
            if (arrEnemyTanks.get(index) < arrSoldiers.get(index)) {
                arrEnemyTanks.remove(index);
            } else if (arrEnemyTanks.get(index) > arrSoldiers.get(index)) {
                arrSoldiers.remove(index);
            } else if ((arrEnemyTanks.get(index)).equals(arrSoldiers.get(index))) {
                arrEnemyTanks.remove(index);
                arrSoldiers.remove(index);
            }
        }

        SoldierCasualties = playerSoldierTroops - arrSoldiers.size();
        EnemyTankCasualties = Countries.get(chosenCountry).getTanks() - arrEnemyTanks.size();
        totalSoldierCasualties = totalSoldierCasualties + SoldierCasualties;
        totalEnemyTankCasualties = totalEnemyTankCasualties + TankCasualties;
        lblNumCas.setText((SoldierCasualties + TankCasualties) + "");
        lblNumEnemyCas.setText((EnemySoldierCasualties + EnemyTankCasualties) + "");
        arrSoldierCasualties.add(playerSoldierTroops - arrSoldiers.size());
        arrTankCasualties.add((0));
        arrEnemyTankCasualties.add(Countries.get(chosenCountry).getSoldiers() - arrEnemySoldiers.size());
        arrEnemySoldierCasualties.add(0);
        BattleStats();

        if(player.getSoldiers() == 0){
            SoldiersAlive = false;
            SoldierCasualties = 0;
        }


        player.setSoldiers((player.getSoldiers() - playerSoldierTroops) + (arrSoldiers.size()));
        Countries.get(chosenCountry).setTanks(arrEnemyTanks.size());

        lblNumSoldiers.setText("" + player.getSoldiers());
        lblNumEnemyTanks.setText("" + Countries.get(chosenCountry).getTanks());
        lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
        lblAttackTanks.setText("?");
        lblAttackSoldiers.setText("?");

        if ((player.getTanks() + player.getSoldiers()) > (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("We Survived With More Troops!");
        } else if ((player.getTanks() + player.getSoldiers()) < (Countries.get(chosenCountry).getTanks() + Countries.get(chosenCountry).getSoldiers())) {
            lblResult.setText("The Enemy Survived With More Troops");
        } else {
            lblResult.setText("Both Sides Have An Equal Amount \n Of Troops!");
        }



        if (player.getTanks() == 0 && player.getSoldiers() == 0) {
            GameOver();
        } else if (Countries.get(chosenCountry).getTanks() == 0 && Countries.get(chosenCountry).getSoldiers() == 0) {
            Conquered();
        }
        else if(Countries.get(8).getSoldiers() == 0 && Countries.get(8).getTanks() == 0){
            Win();
        }
    }

    //Sets the attacking troops
    public void FightingTroops() {
        if (TypeTroopCounter == 1 && SoldiersAlive) {
            playerSoldierTroops = Integer.parseInt(txtNumTroops.getText());
            lblAttackSoldiers.setText("" + playerSoldierTroops);
            TypeTroopCounter++;

            if(TanksAlive && playerSoldierTroops < player.getSoldiers()){
                lblTypeOfTroop.setText("Number Of Tanks To Attack With:");
            }
            else{
                lblTypeOfTroop.setText("Done Inputting");
            }


            txtNumTroops.clear();


            if (playerSoldierTroops > player.getSoldiers()) {
                lblResult.setText("You Don't Have That Many Soldiers");
                lblNumCas.setText("TRY AGAIN");
                lblNumEnemyCas.setText("TRY AGAIN");
                lblAttackSoldiers.setText("Not Possible");
                btnAttack.setDisable(true);
                btnRound.setDisable(true);
            }

            if(Round >= 1 && !TanksAlive){
                btnRound.setDisable(false);
            }

        } else if (TypeTroopCounter == 2 && TanksAlive) {

            if(!SoldiersAlive){
                lblTypeOfTroop.setText("Number Of Tanks To Attack With:");
            }

            if(Round >= 1){
                btnRound.setDisable(false);
            }

            if(Round == 0){
                btnAttack.setDisable(false);
            }
            else{
                btnAttack.setDisable(true);
            }

            if(playerSoldierTroops>player.getSoldiers()){
                btnAttack.setDisable(true);
            }



            playerTanksTroops = Integer.parseInt(txtNumTroops.getText());


            if(playerSoldierTroops > player.getSoldiers()){
                lblAttackTanks.setText("Not Possible");
            }
            else{
                lblAttackTanks.setText("" + playerTanksTroops);
            }


            TypeTroopCounter = 1;
            lblTypeOfTroop.setText("Done Inputting");
            txtNumTroops.clear();


            if (playerTanksTroops > player.getTanks()) {
                lblResult.setText("You Don't Have That Many Tanks");
                lblTypeOfTroop.setText("Number Of Soldiers To Attack With");
                lblNumCas.setText("TRY AGAIN");
                lblNumEnemyCas.setText("TRY AGAIN");
                lblAttackSoldiers.setText("");
                lblAttackTanks.setText("");
                playerTanksTroops = 0;
                btnAttack.setDisable(true);
                btnRound.setDisable(true);
            }
        }

    }


    //Gives the description of things in the shop
    public void descriptions(int itemChosen) {
        if (itemChosen == 0) {
            lblDescription.setText("You get to buy more soldiers \n To purchase, input how much gold your willing to spend in the text field  \n Price: 1000 gold per soldier");
        }
        if(itemChosen == 1) {
            lblDescription.setText("You get to buy more tanks \n To purchase, input how much gold your willing to spend in the text field  \n Price: 3000 gold per tank");
        }
        if(itemChosen == 2){
            lblDescription.setText("You get to sell some of your land for gold \n To sell, input how much land your willing to sell in the text field  \n Gold: 1000 gold per 1000 square feet");
        }
        if(itemChosen == 3){
            lblDescription.setText("You get to sell some of your soldiers for gold \n To sell, input how many soldiers your willing to sell in the text field  \n Gold: 1000 gold per 1 soldier");
        }
        if(itemChosen == 4){
            lblDescription.setText("You get to sell some of your tanks for gold \n To sell, input how many tanks your willing to sell in the text field  \n Gold: 1000 gold per 1 tank");
        }
        if(itemChosen == 5){
            lblDescription.setText("You get to switch your soldiers with tanks, \n But only if you have only soldiers living and the enemy only has tanks  \n To purchase, input the gold required  \n Price: 4000 gold" );
        }
        if(itemChosen == 6){
            lblDescription.setText("You get to train prisoners to become your soldiers for gold,   \n To purchase, input how much gold your willing to spend  \n Price: 100 gold per soldier" );
        }
        if(itemChosen == 7){
            lblDescription.setText("You get to earn gold for the most recent total enemy casualties,   \n To purchase, input how many tanks your willing to sell  \n Price: 2 tanks" );
        }


    }



}