package Controleur;

import Modele.*;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class PlayCardsVue {

    Jeu j;
    Histoire h;
    SaveLoad SL = new SaveLoad();

    public PlayCardsVue(Jeu j, Histoire h) {
        this.j = j;
        this.h = h;
    }


    public Jeu playerFirstPlayCard(Jeu j, Brand card) {
        int index;
        for (index = 0; index < 11; index++) {
            if (card == j.playercard[j.playerFirst].get(index))
                break;
        }
        //进行出牌操作
        j = playCards(j, index);
        //打印先手方出的牌
        System.out.println((j.playerNow + 1) + "jouer" + j.FirstPlayerPlayCard.toString());
        j.playerNow=(j.playerNow+1)%2;
        Jeu j1 = (Jeu) j.clone();
        h.ajouteListDeHistoire(j1);
        return j;
    }

    public Jeu playerSecondePlayCard(Jeu j, Brand card) {
        //输入后手方想出的牌
        int index;
        for (index = 0; index < 11; index++) {
            if (card == j.playercard[(j.playerFirst+1)%2].get(index))
                break;
        }

        //进行出牌操作
        playCards(j, index);
        //打印后手方出的牌
        System.out.println((j.playerNow + 1) + "jouer" + j.SecondPlayerPlayerCard.toString());
        j = comparer(j);
        j.setlastgame();
        //先后手改变
        j.playerFirst = j.Playerwin;
        //得分增加
        if (j.Playerwin == 0) {
            j.Player1Score++;
            j.Player1totalScore++;
        } else {
            j.Player2Score++;
            j.Player2totalScore++;
        }
        System.out.println(("Player 1 score est " + j.Player1Score + ". Player 2 Score est " + j.Player2Score));
        if (j.numberOfRounds > 15) {
            j.numberOfRounds++;
            j.TurnProcess = 1;
        }
        j.playerNow=j.Playerwin;
        Jeu j1 = (Jeu) j.clone();
        h.ajouteListDeHistoire(j1);

        return j;
    }

    public Jeu IAplaycard(Jeu j, int IA) {

        System.out.println("numbre of Rounds est " + j.numberOfRounds);
        int index = -456;
        j.showCard();
        if (j.playerNow == 0) {
            System.out.println("IA random");
        } else {
            System.out.println("IA simple");
        }
        //选择IA
        switch (IA) {
            case 1:
                IArandom iar = new IArandom(j);
                index = iar.IArandomPlayerCard(j);
                break;
            case 2:
                IASimple ias = new IASimple(j);
                index = ias.IASimplePlayerCard();
                break;
            case 3:
                IArandom2 iar2 = new IArandom2(j);
                index = iar2.IArandomPlayerCard2(j);
                break;
        }
        System.out.println(index);
        //进行出牌操作
        if (j.playerNow == 0) {
            System.out.println("IA 1 jouer" + j.playercard[j.playerNow].get(index).toString());
        } else {
            System.out.println("IA 2 jouer" + j.playercard[j.playerNow].get(index).toString());
        }
        playCards(j, index);
        if (j.playerNow != j.playerFirst) {
            j = comparer(j);
            j.setlastgame();
            //先后手改变
            j.playerFirst = j.Playerwin;
            //得分增加
            if (j.Playerwin == 0) {
                j.Player1Score++;
                j.Player1totalScore++;

            } else {
                j.Player2Score++;
                j.Player2totalScore++;
            }
            System.out.println(("Player 1 score est " + j.Player1Score + ". Player 2 Score est " + j.Player2Score));
            if (j.numberOfRounds > 15) {j.numberOfRounds++; j.TurnProcess = 1;}
            j.playerNow=j.Playerwin;
        }else{
            j.playerNow=(j.playerNow+1)%2;
        }
        Jeu j2 = (Jeu) j.clone();
        h.ajouteListDeHistoire(j2);
        return j;
    }

    //实现将玩家选择的牌放到jeu数据里头，同时将该牌从手牌中删除
    Jeu playCards(Jeu j, int index) {
        if (j.playerNow == j.playerFirst) {
            j.FirstPlayerPlayCard = j.playercard[j.playerNow].get(index);
            j.playercard[j.playerNow].remove(index);
        } else {
            Brand card = j.playercard[j.playerNow].get(index);
            while (!limite(j, card)) {
                System.out.println("L'erreur, tu ne peux pas jouer ce card, reessayer");
                Scanner input = new Scanner(System.in);
                index = input.nextInt();
                card = j.playercard[j.playerNow].get(index);
            }
            j.SecondPlayerPlayerCard = card;
            j.playercard[j.playerNow].remove(index);
        }
        j.TurnProcess++;
        return j;
    }

    //返回两张卡花色是否一样。
    public boolean limite(Jeu j, Brand card) {
        if (j.playerNow == j.playerFirst) return true;
        boolean limite = false;
        for (int i = 0; i < j.playercard[j.playerNow].size(); i++) {
            if (Objects.equals(j.playercard[j.playerNow].get(i).getInttype(), j.FirstPlayerPlayCard.getInttype())) {
                limite = true;
                break;
            }
        }
        if (limite) {
            return Objects.equals(card.getInttype(), j.FirstPlayerPlayCard.getInttype());
        } else return true;
    }

    public Jeu comparer(Jeu j) {
        this.j = j;
        //如果双方出同花色
        if (Objects.equals(j.FirstPlayerPlayCard.getInttype(), j.SecondPlayerPlayerCard.getInttype())) {
            if (j.FirstPlayerPlayCard.getNum() > j.SecondPlayerPlayerCard.getNum())
                j.Playerwin = j.playerFirst;
            else {
                j.Playerwin = j.playerFirst + 1;
                if (j.Playerwin >= 2) {
                    j.Playerwin = 0;
                }
            }
        }
        //如果双方出不同花色
        else {
            //如果有王牌的话
            if (j.avoiratout) {
                //如果后手方出王牌的话
                if (Objects.equals(j.SecondPlayerPlayerCard.getInttype(), j.atout.getInttype())) {
                    j.Playerwin = j.playerFirst + 1;
                    if (j.Playerwin >= 2)
                        j.Playerwin = 0;
                } else {//如果后手方不出王牌的话
                    j.Playerwin = j.playerFirst;
                }
            }
            //如果没有王牌的话（默认先手方赢）
            else {
                j.Playerwin = j.playerFirst;
            }

        }
        System.out.println("winner is " + (j.Playerwin + 1));
        return j;
    }

}
