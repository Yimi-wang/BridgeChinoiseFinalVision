package Controleur;

import Modele.GameProcess;
import Modele.Histoire;
import Modele.Jeu;

import java.io.*;
import java.util.Scanner;

public class SaveLoad implements java.io.Serializable {
    Modele.GameProcess GameProcess = new GameProcess();

    public SaveLoad() {
    }

    public void saveorload(Histoire h) {
        System.out.println("Save or Load");
        Scanner input = new Scanner(System.in);
        String a = input.nextLine();
        if (a.equals("save")) {
            Save(h);
        } else if (a.equals("load")) {
            Load();
        }
    }

    public void Save(Histoire h) {
        System.out.println("Donner le nom pour save");
        Scanner input = new Scanner(System.in);
        String Save = input.nextLine();
        Save = "./res/saveload/"+Save;
        Save = Save.concat(".ser");

        try {
            File file = new File(Save);
            FileOutputStream fileOut = new FileOutputStream(Save);

            //创建一个ObjectOutputStream
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            //将对象写入输出流
            objOut.writeObject(h);

            objOut.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public void Load() {
        Histoire h;
        try {
            System.out.println("Donner le nom pour load");
            Scanner input = new Scanner(System.in);
            String Load = input.nextLine();
            Load="./res/saveload/"+Load;
            Load = Load.concat(".ser");
            //读取对象
            FileInputStream fileIn = new FileInputStream(Load);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            h = (Histoire) objIn.readObject();
            objIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
            return;
        }
        //load开始游戏
        System.out.println("Load Game");
        h.listDeHistoire.remove(h.listDeHistoire.size() - 1);
        Jeu j = h.listDeHistoire.get(h.listDeHistoire.size() - 1);
        switch (j.GameMode) {
            case 1:
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    GameProcess.turnstart(h);
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
                        GameProcess.turnstart(h);
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
                        GameProcess.turnstart(h);
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
                        GameProcess.turnstart(h);
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
    }


}

