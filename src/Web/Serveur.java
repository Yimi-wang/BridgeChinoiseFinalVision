package Web;

import Controleur.PlayCards;
import Controleur.TakeCard;
import Modele.*;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Serveur {
    Jeu j=new Jeu();
    ServerSocket serverSocket;
    Histoire h= new Histoire(j);
    public Serveur(){
    }

    public void serveur() throws IOException, ClassNotFoundException {
        //得到本地服务器地址
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            System.out.println("votre adresse IP est :" + ip4.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //建立socket连接
        ObjectOutput objectOutput = null;
        ObjectInput objectInput = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();
            System.out.println("Connexion avec succès");
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectInput = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        gameMode(objectOutput, objectInput);
    }

    public void gameMode(ObjectOutput objectOutput,ObjectInput objectInput) throws IOException, ClassNotFoundException {
        System.out.println("Gamemode 1 : BO1");
        System.out.println("Gamemode 2 : BO3");
        System.out.println("Gamemode 3 : Nombre de jeu fixe");
        System.out.println("Gamemode 4 : Score fixe");
        System.out.println("Gamemode 5 : AI random VS AI simple");
        System.out.println("Gamemode 6 : Web");
        System.out.println("Choisir le 'gamemode' souhaité");
        Scanner input = new Scanner(System.in);
        int gamemode = input.nextInt();
        this.j.GameMode = gamemode;
        switch (gamemode) {
            case 1:
                gameStart(j,h);
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    PlayGame(j,objectOutput,objectInput);
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    System.out.println("Joueur 1 a gagné!");
                } else {
                    System.out.println("Joueur 2 a gagné!");
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    j.Game_ind = i;
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        PlayGame(j,objectOutput,objectInput);
                    }
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    j.Player1WinGame++;
                } else if (j.Player1Score < j.Player2Score) {
                    j.Player2WinGame++;
                }
                if (j.Player1WinGame == 2) {
                    System.out.println("Joueur 1 a gagné!");
                    break;
                } else if (j.Player2WinGame == 2) {
                    System.out.println("Joueur 2 a gagné!");
                    break;
                }

                break;
            case 3:
                System.out.println("Saisissez la quantité de jeux vous voulez jouer");
                input = new Scanner(System.in);
                int nGame = input.nextInt();
                j.GameInformation = nGame;
                for (int i = 0; i < nGame; i++) {
                    j.Game_ind=i;
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        PlayGame(j,objectOutput,objectInput);
                    }
                }
                if (j.Player1totalScore > j.Player2totalScore) {

                    System.out.println("Joueur 1 a gagné!");
                } else {

                    System.out.println("Joueur 2 a gagné!");
                }
                h.cleanHistoire();
                break;
            case 4:
                System.out.println("Saisissez le score que vous voulez jouer");
                input = new Scanner(System.in);
                int ScoreWin = input.nextInt();
                j.GameInformation = ScoreWin;
                while (j.Player1totalScore < ScoreWin && j.Player2totalScore < ScoreWin) {
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        PlayGame(j,objectOutput,objectInput);
                        //turnstart(h);
                        if (j.Player1totalScore >= ScoreWin || j.Player2totalScore >= ScoreWin) {
                            if (j.Player1totalScore > j.Player2totalScore) {
                                System.out.println("Joueur 1 a gagné!");
                            } else {
                                System.out.println("Joueur 2 a gagné!");
                            }
                            break;
                        }
                    }

                }
                break;
        }
        objectOutput.close();
        serverSocket.close();

    }
    public void PlayGame(Jeu j, ObjectOutput objectOutput,ObjectInput objectInput) throws IOException, ClassNotFoundException {
        PlayCards playCards = new PlayCards(j, h);
        TakeCard takeCard = new TakeCard(j, h);
        switch (j.TurnProcess) {
            //先手方出牌
            case 1:
                j.playerNow = j.playerFirst;
                if (j.getPlayerNow() == 0) {
                    playCards.playerFirstPlayCard(j);
                    objectOutput.writeObject(j);
                    objectOutput.flush();
                    System.out.println("Envoyé avec succès");

                } else {
                    this.j=(Jeu) objectInput.readObject();
                    System.out.println("Reçu avec succès");
                }
                break;
            //后手方出牌
            case 2:
                j.playerNow = j.playerNow + 1;
                if (j.playerNow == 2) j.playerNow = 0;
                if (j.getPlayerNow() == 0) {
                    playCards.playerSecondePlayCard(j);
                    objectOutput.writeObject(j);
                    objectOutput.flush();
                    System.out.println("Envoyé avec succès");

                } else {
                    this.j=(Jeu) objectInput.readObject();
                    System.out.println("Reçu avec succès");
                }
                break;
            //根据赢家，进行拿牌操作。
            //if条件是当游戏在15轮内才进行拿牌操作。因为牌堆一共30张牌，第十六轮没有牌可以拿。
            case 3:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (j.playerNow == 0) {
                        takeCard.playerWinTakeCard(j);
                        objectOutput.writeObject(j);
                        objectOutput.flush();
                        System.out.println("Envoyé avec succès");

                    } else {
                        j.playerNow=123321123;
                        this.j=(Jeu) objectInput.readObject();
                        System.out.println("Reçu avec succès");
                    }

                } else {
                    j.TurnProcess++;
                }
                break;
            case 4:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin + 1;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (j.playerNow == 0) {
                        takeCard.playerLoseTakeCard(j);
                        objectOutput.writeObject(j);
                        objectOutput.flush();
                        System.out.println("Envoyé avec succès");

                    } else {
                        this.j=(Jeu) objectInput.readObject();
                        System.out.println("Reçu avec succès");
                    }
                } else {
                    j.TurnProcess++;
                }
                break;
            default:
                System.out.println("Erreur de tour");
        }
    }

    public void gameStart(Jeu j, Histoire h) {
        j.reset();
        if (j.numberOfGames == 0) j.numberOfGames = 1;//如果游戏刚开始的话
        if (j.playerFirst == 2) {//如果本轮该开始的话，判断哪个玩家先开始游戏。
            j.playerFirst = (j.numberOfGames - 1) % 2;
            j.numberOfRounds = 1;
            //进行发牌以及牌堆的实现
            StartHand startHand = new StartHand(j);
            startHand.stardHand();
            Atout a = new Atout(j);
            a.determinerAtout();
            Jeu j0 = (Jeu) j.clone();
            this.h.ajouteListDeHistoire(j0);
            Jeu j100 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j100);

        }
    }
}