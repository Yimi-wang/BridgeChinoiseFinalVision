package Modele;

import java.io.Serializable;
import java.util.LinkedList;


public class Histoire implements Serializable {
    public LinkedList<Jeu> listDeHistoire;

    public Jeu j;

    public Histoire(Jeu j) {
        this.j = j;
        listDeHistoire = new LinkedList<>();

    }

    public void ajouteListDeHistoire(Jeu j) {
        listDeHistoire.add(j);
        System.out.println("put in histoire");
    }

    public void returnHistoire() {
        if (j.numberOfRounds == 0) {
            //无法回退
            System.out.println("首回合");
        }

        listDeHistoire.remove(listDeHistoire.size() - 1);
        listDeHistoire.remove(listDeHistoire.size() - 1);
        Jeu j1 = listDeHistoire.get(listDeHistoire.size() - 1);
        Jeu j0 = (Jeu) j1.clone();
        ajouteListDeHistoire(j0);
        System.out.println("delete");

    }

    public void cleanHistoire() {
        listDeHistoire.clear();
    }

}
