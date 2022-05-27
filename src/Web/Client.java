package Web;

import Modele.Histoire;
import Modele.Jeu;
import Controleur.PlayCards;
import Controleur.TakeCard;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Jeu j;
    Socket socket;
    Histoire h;
    public Client(){}
    public void client() throws IOException, ClassNotFoundException {
        //建立套接字通讯
        System.out.println("Donner le ip adresse pour connecte");
        Scanner input = new Scanner(System.in);
        String ipadresse = input.nextLine();
        ObjectOutput objectOutput = null;
        ObjectInput objectInput = null;
        try {
            socket = new Socket(ipadresse, 8080);
            System.out.println("connect success");
            objectInput = new ObjectInputStream(socket.getInputStream());
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameModeC(objectOutput, objectInput);
    }

    public void gameModeC(ObjectOutput objectOutput,ObjectInput objectInput) throws IOException, ClassNotFoundException {
         j = (Jeu) objectInput.readObject();
        switch (j.GameMode) {
            case 1:
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    PlayGame(j, objectOutput,objectInput);
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    System.out.println("Player 1 win!");
                } else {
                    System.out.println("Player 2 win!");
                }
                break;
            case 2:
                for (int i = j.Game_ind; i < 3; i++) {
                    j.Game_ind = i;
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5) {
                            j.TurnProcess = 1;
                        }
                        PlayGame(j, objectOutput,objectInput);
                    }
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    j.Player1WinGame++;
                } else if (j.Player1Score < j.Player2Score) {
                    j.Player2WinGame++;
                }
                if (j.Player1WinGame == 2) {
                    System.out.println("Player 1 win!");
                    break;
                } else if (j.Player2WinGame == 2) {
                    System.out.println("Player 2 win!");
                    break;
                }

                break;
            case 3:
                for (int i = j.Game_ind; i < j.GameInformation; i++) {
                    j.Game_ind = i;
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        PlayGame(j, objectOutput,objectInput);
                    }
                }
                if (j.Player1totalScore > j.Player2totalScore) {
                    System.out.println("Player 1 win!");
                } else {
                    System.out.println("Player 2 win!");
                }
                h.cleanHistoire();
                break;
            case 4:
                while (j.Player1totalScore < j.GameInformation && j.Player2totalScore < j.GameInformation) {
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        PlayGame(j, objectOutput,objectInput);
                        if (j.Player1totalScore >= j.GameInformation || j.Player2totalScore >= j.GameInformation) {
                            if (j.Player1totalScore > j.Player2totalScore) {
                                System.out.println("Player 1 win!");
                            } else {
                                System.out.println("Player 2 win!");
                            }
                            break;
                        }
                    }

                }
                h.cleanHistoire();
                break;


        }
        objectInput.close();
        socket.close();
    }
    public void PlayGame(Jeu j, ObjectOutput objectOutput,ObjectInput objectInput) throws IOException, ClassNotFoundException {
        PlayCards playCards = new PlayCards(j, h);
        TakeCard takeCard = new TakeCard(j, h);
        switch (j.TurnProcess) {
            //先手方出牌
            case 1:
                j.playerNow = j.playerFirst;
                if (j.getPlayerNow() == 1) {
                    playCards.playerFirstPlayCard(j);
                    objectOutput.writeObject(j);
                    objectOutput.flush();
                    System.out.println("fa song cheng gong ");

                } else {
                    this.j=(Jeu) objectInput.readObject();
                    System.out.println("jie shou cheng gong ");
                }
                break;
            //后手方出牌
            case 2:
                j.playerNow = j.playerNow + 1;
                if (j.playerNow == 2) j.playerNow = 0;
                if (j.getPlayerNow() == 1) {
                    playCards.playerSecondePlayCard(j);
                    objectOutput.writeObject(j);
                    objectOutput.flush();
                    System.out.println("fa song cheng gong ");

                } else {
                    this.j=(Jeu) objectInput.readObject();
                    System.out.println("jie shou cheng gong ");
                }
                break;
            //根据赢家，进行拿牌操作。
            //if条件是当游戏在15轮内才进行拿牌操作。因为牌堆一共30张牌，第十六轮没有牌可以拿。
            case 3:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (j.playerNow == 1) {
                        takeCard.playerWinTakeCard(j);
                        objectOutput.writeObject(j);
                        objectOutput.flush();
                        System.out.println("fa song cheng gong ");
                    } else {
                        this.j=(Jeu) objectInput.readObject();
                        System.out.println("jie shou cheng gong ");
                    }

                } else {
                    j.TurnProcess++;
                }
                break;
            case 4:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin + 1;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (j.playerNow == 1) {
                        takeCard.playerLoseTakeCard(j);
                        objectOutput.writeObject(j);
                        objectOutput.flush();
                        System.out.println("fa song cheng gong ");

                    } else {
                        this.j=(Jeu) objectInput.readObject();
                        System.out.println("jie shou cheng gong ");
                    }
                } else {
                    j.TurnProcess++;
                }
                break;
            default:
                System.out.println("turn erreur");
        }
    }

}
