package Web;

import Modele.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameStartInternet {
    Serveur s = new Serveur();
    Client c = new Client();
    public GameStartInternet(){
    }

    public void choseSorC() throws IOException, ClassNotFoundException {
        Serveur s = new Serveur();
        Jeu j = new Jeu();
        Histoire h = new Histoire(j);
        Atout a = new Atout(j);
        System.out.println("1:Serveur, 2:Client");
        Scanner input = new Scanner(System.in);
        int SorC = input.nextInt();
        if(SorC==1){
            s.serveur();
        }else{
            c.client();
        }
    }

}
