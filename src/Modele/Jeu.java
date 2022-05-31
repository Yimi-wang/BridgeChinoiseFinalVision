package Modele;

import java.io.Serializable;
import java.util.LinkedList;

public class Jeu implements Cloneable, Serializable {
    public LinkedList<Brand>[] playercard;//=new List[2];//玩家手牌//建立了一个tableau 的list playercard[0]是玩家1的手卡，etc
    public LinkedList<Brand>[] pilescard;//=new List[6];//牌堆//建立了一个tableau 的list pilescard[0]是第一个牌堆，etc
    public int numberOfGames;//回合数
    public int numberOfRounds;//轮数
    public int playerFirst;//哪个玩家先手，0代表玩家A。1代表玩家B
    public int playerNow;
    public Brand atout;
    public Boolean avoiratout;

    public int AI;
    public Brand FirstPlayerPlayCard;
    public Brand SecondPlayerPlayerCard;
    public int Playerwin;
    public int Player1Score;
    public int Player2Score;
    public int Player1totalScore;
    public int Player2totalScore;
    public int Player1WinGame;
    public int Player2WinGame;
    public int TurnProcess;
    //存储游戏模式等信息。
    public int GameMode;
    public int GameInformation;
    public int Game_ind;
    public Brand player1takecard;
    public Brand player2takecard;

    public Brand lastgamep0playcard;

    public Brand lastgamep1playcard;
    public Jeu() {
        numberOfGames = 0;
        playerFirst = 2;
        Player1Score = 0;
        Player2Score = 0;
        Player1totalScore = 0;
        Player2totalScore = 0;
        Player1WinGame = 0;
        Player2WinGame = 0;
        TurnProcess = 1;

    }

    public Object clone() {
        Jeu a = new Jeu();
        try {
            a = (Jeu) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        a.playercard = new LinkedList[2];//手牌
        a.pilescard = new LinkedList[6];//
        for (int i = 0; i < 2; i++) {
            a.playercard[i] = new LinkedList<>();
            for (int z = 0; z < this.playercard[i].size(); z++) {
                Brand temp = new Brand(this.playercard[i].get(z).getType(), this.playercard[i].get(z).getNum(), this.playercard[i].get(z).getInttype(),this.playercard[i].get(z).getPlace());
                a.playercard[i].add(temp);
            }
        }
        for (int i = 0; i < 6; i++) {
            a.pilescard[i] = new LinkedList<>();
            for (int z = 0; z < this.pilescard[i].size(); z++) {
                Brand temp = new Brand(this.pilescard[i].get(z).getType(), this.pilescard[i].get(z).getNum(), this.pilescard[i].get(z).getInttype(),this.pilescard[i].get(z).getPlace());
                a.pilescard[i].add(temp);
            }
        }
        return a;
    }


    public void handCardSorting() {
        for (int i = 0; i < 2; i++) {
            LinkedList<Brand> sort = new LinkedList<>();
            Brand card;
            for (int coleur = 0; coleur < 4; coleur++) {
                for (int num = 1; num < 14; num++) {
                    for (int index = 0; index < playercard[i].size(); index++) {
                        if ((playercard[i].get(index).getInttype() == coleur) && (playercard[i].get(index).getNum() == num)) {
                            card = playercard[i].get(index);
                            sort.add(card);
                        }
                    }
                }
            }
            playercard[i] = sort;
        }
    }

    public void showCard() {
        //将牌按照花色和大小排序
        handCardSorting();
        System.out.println("Carte du joueur 1");
        for (int i = 0; i < playercard[0].size(); i++) {
            Brand b = playercard[0].get(i);
            String s = b.toString();
            System.out.println("index " + i + " " + s);
        }
        System.out.println("Carte du joueur 2");
        for (int i = 0; i < playercard[1].size(); i++) {
            Brand b = playercard[1].get(i);
            String s = b.toString();
            System.out.println("index " + i + " " + s);
        }
        /*
        System.out.println("pile 1 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[0].get(i);
            String s = b.toString();
            System.out.println(s);
        }
        System.out.println("pile 2 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[1].get(i);
            String s = b.toString();
            System.out.println(s);
        }
        System.out.println("pile 3 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[2].get(i);
            String s = b.toString();
            System.out.println(s);
        }
        System.out.println("pile 4 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[3].get(i);
            String s = b.toString();
            System.out.println(s);
        }
        System.out.println("pile 5 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[4].get(i);
            String s = b.toString();
            System.out.println(s);
        }
        System.out.println("pile 6 card");
        for(int i=0;i<5;i++){
            Brand b =  j.pilescard[5].get(i);
            String s = b.toString();
            System.out.println(s);
        }

         */
        for (int i = 0; i <= 5; i++) {
            if (pilescard[i].size() > 0) {
                Brand b = pilescard[i].get(0);
                String s = b.toString();
                System.out.println("pile " + i + " " + s);
            }
        }
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getPlayer1Score() {
        return Player1Score;
    }

    public int getPlayer2Score() {
        return Player2Score;
    }

    public int getPlayerNow() {
        return playerNow;
    }

    public int getPlayerfirst() {
        return playerFirst;
    }

    public Brand getFirstPlayerPlayCard() {
        return FirstPlayerPlayCard;
    }

    public void setAtout(Brand atout) {
        this.atout = atout;
    }

    public void reset() {
        playerFirst = 2;
        numberOfRounds = 0;
        Playerwin = 1100000;
        Player1Score = 0;
        Player2Score = 0;
        TurnProcess = 1;
        numberOfGames++;
        lastgamep0playcard=null;
        lastgamep1playcard=null;
    }
    public void restart(){
        playerFirst=(numberOfGames+1)%2;
        numberOfRounds = 0;
        Playerwin = 1100000;
        Player1Score = 0;
        Player2Score = 0;
        TurnProcess = 1;
    }
    public void setlastgame(){
        if(playerNow==0){
            lastgamep0playcard=SecondPlayerPlayerCard;
            lastgamep1playcard=FirstPlayerPlayCard;
        }else{
            lastgamep1playcard=SecondPlayerPlayerCard;
            lastgamep0playcard=FirstPlayerPlayCard;
        }
    }
}
