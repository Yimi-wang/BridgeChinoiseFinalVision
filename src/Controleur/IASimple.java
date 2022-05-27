package Controleur;

import Modele.Brand;
import Modele.Jeu;
import Modele.Type;

import java.util.Objects;

public class IASimple {
    public Jeu j;
    Type t;

    public IASimple(Jeu j) {
        this.j = j;
    }

    public int IASimplePlayerCard() {
        System.out.println("IASimplePlayCard");
        boolean peutAtout = true;
        if (j.getPlayerfirst() == 1) {//先出的话，出数值最大的牌
            Brand max = j.playercard[1].get(0);
            for (int i = 1; i < j.playercard[1].size(); i++) {
                if (j.playercard[1].get(i).getNum() >= max.getNum()) {
                    max = j.playercard[1].get(i);
                }
            }
            return j.playercard[1].indexOf(max);
        } else {

            //如果有。出最小的大于它的牌
            for (int index = 0; index < j.playercard[1].size(); index++) {
                if (Objects.equals(j.playercard[1].get(index).getInttype(), j.getFirstPlayerPlayCard().getInttype())) {
                    peutAtout = false;
                    if (j.playercard[1].get(index).getNum() > j.getFirstPlayerPlayCard().getNum())
                        return index;
                }
            }
            //如果有，出最小的王牌。
            if (j.avoiratout && peutAtout) {
                for (int index = 0; index < j.playercard[1].size(); index++) {
                    if (Objects.equals(j.playercard[1].get(index).getInttype(), j.atout.getInttype())) return index;
                }
            }
            //如果有同花色但赢不了，出最小的牌
            Brand min = new Brand(Type.trefle, 10000, 1000000000,99);
            //如果都没有。出最小的牌
            if (!peutAtout) {
                for (int i = j.playercard[1].size() - 1; i >= 0; i--) {
                    if ((j.playercard[1].get(i).getNum() <= min.getNum()) && Objects.equals(j.playercard[1].get(i).getInttype(), j.getFirstPlayerPlayCard().getInttype())) {
                        min = j.playercard[1].get(i);
                    }
                }
                return j.playercard[1].indexOf(min);
            }
            for (int i = j.playercard[1].size() - 1; i >= 0; i--) {
                if (j.playercard[1].get(i).getNum() <= min.getNum()) {
                    min = j.playercard[1].get(i);
                }
            }
            return j.playercard[1].indexOf(min);

        }

    }


    //    public int IASimpleTakeCard() {
//        //看王牌数量
//        int numberatout = 0;
//        int numberpile = 0;
//        int i1 = 0, i2 = 0;
//        for (int i = 0; i <= 5; i++) {
//            if (j.pilescard[i].size() != 0) {
//                numberpile++;
//            }
//        }
//        Brand maxtop = new Brand(Type.trefle, 1, 3);
//        Brand maxtop2 = new Brand(Type.trefle, 1, 3);
//        for (int i = 0; i < j.playercard[1].size(); i++) {
//            if (Objects.equals(j.playercard[1].get(i).getInttype(), j.atout.getInttype())) {
//                numberatout++;
//            }
//        }//如果王牌数量小于4，就换王牌
//        if (numberatout < 4 && j.avoiratout) {
//            for (int i = 0; i <= 5; i++) {
//                if (j.pilescard[i].size() != 0) {
//                    if (j.pilescard[i].get(0) == j.atout) return i;
//                }
//
//            }
//        }
//        //如果王牌数量大于四，就拿第二大的王牌
//        else if (j.avoiratout) {
//            for (int i = 0; i <= 5; i++) {
//                if (j.pilescard[i].size() != 0) {
//                    Brand c = j.pilescard[i].get(0);
//                    if (c.getNum() > maxtop.getNum()) {
//                        i2 = i1;
//                        i1 = i;
//                        maxtop = c;
//                    } else if (Objects.equals(c.getNum(), maxtop.getNum())) {
//                        if (c.getInttype() < maxtop.getInttype()) {
//                            i2 = i1;
//                            i1 = i;
//                            maxtop = c;
//                        }
//                    }
//                }
//            }
//            return i2;
//        }
//        //如果只剩下一张牌，就拿最后一张牌
//        else if (numberpile == 1) {
//            for (int i = 0; i <= 5; i++) {
//                if (j.pilescard[i].size() != 0) {
//                    return i;
//                }
//            }
//        }
//        //如果没有王牌，就拿最大的牌。
//        else {
//            int index = 0;
//            for (int i = 0; i <= 5; i++) {
//                if (j.pilescard[i].size() != 0) {
//                    Brand c = j.pilescard[i].get(0);
//                    if (c.getNum() > maxtop.getNum()) {
//                        maxtop = c;
//                        index = i;
//                    } else if (Objects.equals(c.getNum(), maxtop.getNum())) {
//                        if (c.getInttype() < maxtop.getInttype()) {
//                            maxtop = c;
//                            index = i;
//                        }
//                    }
//                }
//            }
//            return index;
//        }
//        return 0;
//    }
    public int IASimpleTakeCard() {
        System.out.println("IASimpleTakeCard");
        //如果游戏有王牌且牌堆有王牌的话
        Brand b = new Brand(Type.pique, 0, 0,99);
        int index = -10;
        if (j.avoiratout) {
            for (int i = 0; i <= 5; i++) {
                if (j.pilescard[i].size() > 0) {
                    if ((j.pilescard[i].get(0).getType() == j.atout.getType()) && j.pilescard[i].get(0).getNum() >= b.getNum()) {
                        index = i;
                        b = j.pilescard[i].get(0);
                    }
                }
            }
            if (index > -1) {
                return index;
            }

        }

        for (int i = 0; i <= 5; i++) {
            if (j.pilescard[i].size() != 0) {
                if (j.pilescard[i].get(0).getNum() > b.getNum()) {
                    b = j.pilescard[i].get(0);
                    index = i;
                } else if (Objects.equals(j.pilescard[i].get(0).getNum(), b.getNum())) {
                    if (j.pilescard[i].get(0).getInttype() > b.getInttype()) {
                        b = j.pilescard[i].get(0);
                        index = i;
                    }
                }
            }
        }
        return index;

    }
}
