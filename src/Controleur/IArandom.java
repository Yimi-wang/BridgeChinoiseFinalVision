package Controleur;

import Modele.Brand;
import Modele.Jeu;


import java.util.Objects;
import java.util.Random;


public class IArandom {
    public Jeu j;

    public IArandom(Jeu j) {
        this.j = j;
    }

    public int IArandomPlayerCard(Jeu j) {
        Random r = new Random();
        if (j.getPlayerNow() == 1) {
            if (j.getPlayerfirst() == j.getPlayerNow()) {
                return r.nextInt(j.playercard[1].size());
            } else {
                int index = r.nextInt(j.playercard[1].size());
                System.out.println("le firstplayercard est:" + j.getFirstPlayerPlayCard().toString());
                while (!limite(j, j.playercard[1].get(index))) {
                    index = r.nextInt(j.playercard[1].size());
                }
                return index;
            }
        }
        return -100;
    }

    public int IarandomGetCard() {
        Random r = new Random();
        int index = r.nextInt(6);
        if (j.getPlayerNow() == 1) {
            while (j.pilescard[index].size() == 0) {
                index = r.nextInt(6);
            }
        }
        return index;
    }

    public boolean limite(Jeu j, Brand card) {
        boolean limite = false;
        for (int i = 0; i < j.playercard[1].size(); i++) {
            if (Objects.equals(j.playercard[1].get(i).getInttype(), j.getFirstPlayerPlayCard().getInttype())) {
                limite = true;
                break;
            }
        }
        if (limite) {
            return Objects.equals(card.getInttype(), j.getFirstPlayerPlayCard().getInttype());
        } else return true;
    }
}
