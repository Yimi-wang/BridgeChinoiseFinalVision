package Controleur;

import Modele.*;

import java.util.Scanner;

import static java.lang.System.exit;

public class TakeCardVue {
    Jeu j;
    Histoire h;


    public TakeCardVue(Jeu j, Histoire h) {
        this.j = j;
        this.h = h;
    }


    public Jeu playerWinTakeCard(Jeu j, Brand card) {
        int index=0;
        for(int i=0;i<=5;i++){
            if(j.pilescard[i].size()!=0){
                if(card==j.pilescard[i].get(0))
                    index=i;
            }
        }
        //把赢家的牌放到手牌里头
        j.pilescard[index].remove(0);
        j.playercard[j.Playerwin].add(card);
        if(j.Playerwin==0)j.player1takecard=card;
        else j.player2takecard=card;
        j.handCardSorting();
        j.TurnProcess++;
        j.playerNow=(j.playerNow+1)%2;
        Jeu j3 = (Jeu) j.clone();
        h.ajouteListDeHistoire(j3);

        return j;
    }

    public Jeu playerLoseTakeCard(Jeu j,Brand card) {
        int a = j.Playerwin + 1;
        if (a == 2) a = 0;
        int index=0;
        for(int i=0;i<=5;i++){
            if(j.pilescard[i].size()!=0){
                if(card==j.pilescard[i].get(0))
                    index=i;
            }
        }
        //把输家的牌放到手牌里头
        j.pilescard[index].remove(0);
        j.playercard[a].add(card);
        if(j.Playerwin==0)j.player2takecard=card;
        else j.player1takecard=card;
        j.handCardSorting();
        j.TurnProcess++;
        j.numberOfRounds++;
        j.playerNow=j.playerFirst;
        if (j.TurnProcess == 5) {
            j.TurnProcess = 1;
        }
        Jeu j4 = (Jeu) j.clone();
       h.ajouteListDeHistoire(j4);

        return j;
    }

    public Jeu IAtakecard(Jeu j, int IA) {
        //System.out.println("numbre of Rounds est "+j.numberOfRounds);
        if (j.playerNow == 0) {
            System.out.println("IA random");
        } else {
            System.out.println("IA simple");
        }
        int index=-123;
        switch (IA) {
            case 1:
                IArandom iar = new IArandom(j);
                index = iar.IarandomGetCard();
                break;
            case 2:
                IASimple ias = new IASimple(j);
                index = ias.IASimpleTakeCard();
                break;
            case 3:
                IArandom2 iar2 = new IArandom2(j);
                index = iar2.IarandomGetCard2();
                break;
        }
        System.out.println(index);
        Brand card = j.pilescard[index].get(0);
        System.out.println("IA prendre" + card.toString() + index);
        j.pilescard[index].remove(0);
        j.playercard[j.playerNow].add(card);
         j.player2takecard=card;
        j.TurnProcess++;
        if(j.TurnProcess==5)j.TurnProcess=1;

        if (j.playerNow != j.Playerwin) {
            j.numberOfRounds++;
        }
        j.playerNow=(j.playerNow+1)%2;
        return j;
    }

}
