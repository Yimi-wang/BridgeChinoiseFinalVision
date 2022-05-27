package Modele;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//实现分发手牌
public class StartHand {
    List<Integer> listCard;
    Jeu j;

    public StartHand(Jeu j) {
        listCard = new ArrayList<>();
        this.j = j;
    }

    void createPlayingCard() {
        for (int i = 0; i < 52; i++) {
            listCard.add(i);
        }
        Collections.shuffle(listCard); //随机打乱list数据

    }

    public void stardHand() {

        Brand card = null;
        createPlayingCard();//
        j.playercard = new LinkedList[2];//手牌
        j.playercard[0] = new LinkedList<>();
        j.playercard[1] = new LinkedList<>();
        j.pilescard = new LinkedList[6];//牌堆
        for (int i = 0; i <= 5; i++) {
            j.pilescard[i] = new LinkedList<>();
        }
        for (int i = 0; i < 52; i++) {
            int coleur = (listCard.get(i)) / 13;
            switch (coleur) {
                case 0:
                    card = new Brand(Type.pique, (listCard.get(i)) % 13 + 1, coleur,0);
                    break;
                case 1:
                    card = new Brand(Type.coeur, (listCard.get(i)) % 13 + 1, coleur,0);
                    break;
                case 2:
                    card = new Brand(Type.carreau, (listCard.get(i)) % 13 + 1, coleur,0);
                    break;
                case 3:
                    card = new Brand(Type.trefle, (listCard.get(i)) % 13 + 1, coleur,0);
                    break;
            }
            Brand c = card;
            if (i < 11) {
                c.setPlace(0);
                j.playercard[0].add(c);
            } else if (i > 10 && i < 22) {
                c.setPlace(1);
                j.playercard[1].add(card);
            } else {
                switch ((i - 21) % 6) {
                    case 0:
                        c.setPlace(2);
                        j.pilescard[0].add(c);
                        break;
                    case 1:
                        c.setPlace(3);
                        j.pilescard[1].add(c);
                        break;
                    case 2:
                        c.setPlace(4);
                        j.pilescard[2].add(c);
                        break;
                    case 3:
                        c.setPlace(5);
                        j.pilescard[3].add(c);
                        break;
                    case 4:
                        c.setPlace(6);
                        j.pilescard[4].add(c);
                        break;
                    case 5:
                        c.setPlace(7);
                        j.pilescard[5].add(c);
                        break;

                }

            }
        }
        showCard();
    }

    void showCard() {
        //将牌按照花色和大小排序
        j.handCardSorting();
        System.out.println("player 1 card");
        for (int i = 0; i < j.playercard[0].size(); i++) {
            Brand b = j.playercard[0].get(i);
            String s = b.toString();
            System.out.println("index " + i + " " + s);
        }
        System.out.println("player 2 card");
        for (int i = 0; i < j.playercard[1].size(); i++) {
            Brand b = j.playercard[1].get(i);
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
            if (j.pilescard[i].size() > 0) {
                Brand b = j.pilescard[i].get(0);
                String s = b.toString();
                System.out.println("pile " + i + " " + s);
            }
        }
    }


}
