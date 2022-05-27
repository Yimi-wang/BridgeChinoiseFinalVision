package Modele;

import java.util.Objects;

public class Atout {
    Jeu j;

    public Atout(Jeu j) {
        this.j = j;
    }

    public void determinerAtout() {
        //在前15轮才有王牌，后面没有王牌
        if (j.numberOfRounds <= 15) {
            Brand maxtop = new Brand(Type.trefle, 1, 3,99);
            j.avoiratout = false;
            for (int i = 0; i <= 5; i++) {
                if (j.pilescard[i].size() != 0) {
                    Brand c = j.pilescard[i].get(0);
                    if (c.getNum() > maxtop.getNum()) {
                        maxtop = c;
                    } else if (Objects.equals(c.getNum(), maxtop.getNum())) {
                        if (c.getInttype() < maxtop.getInttype()) {
                            maxtop = c;
                        }
                    }
                }
            }
            if (maxtop.getNum() >= 9) {
                j.atout = maxtop;
                j.avoiratout = true;
            }
        } else {
            j.avoiratout = false;
        }
        toStringAtout(j);
    }

    public void toStringAtout(Jeu j) {
        if (j.avoiratout) {
            String a = j.atout.toString();
            System.out.println("Atout est " + a);
        } else {
            System.out.println("Pas de atout");
        }

    }
}
