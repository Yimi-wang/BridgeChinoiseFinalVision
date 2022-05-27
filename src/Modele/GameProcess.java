package Modele;

import Controleur.PlayCards;
import Controleur.SaveLoad;
import Controleur.TakeCard;
import Web.GameStartInternet;

import java.io.IOException;
import java.util.Scanner;


public class GameProcess {
    int player2;
    int player1;
    int IA;
    Jeu j;
    Atout a;
    Histoire h;
    PlayCards playCards;
    TakeCard takeCard;
    GameStartInternet GSI = new GameStartInternet();
    public GameProcess() {
    }

    public void creatJeu() throws IOException, ClassNotFoundException {
        player1 = 0;
        player2 = 0;
        IA = 0;
        j = new Jeu();
        a = new Atout(j);
        h = new Histoire(j);

        j.playerFirst = 2;
        System.out.println("IA 0 : sans IA");
        System.out.println("IA 1 : random");
        System.out.println("IA 2 : IA simple");
        System.out.println("IA 3 : QAQ");
        System.out.println("4 : web");
        System.out.println("Donner le 'IA' que vous voulez jouer");
        Scanner input = new Scanner(System.in);
        IA = input.nextInt();
        if(IA==4){
            GSI.choseSorC();
            return;
        }
        gameMode();
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
            a.determinerAtout();
            Jeu j0 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j0);
            Jeu j100 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j100);

        }
    }

    public void gameMode() {
        SaveLoad sl = new SaveLoad();
        System.out.println("Gamemode 1 : BO1");
        System.out.println("Gamemode 2 : BO3");
        System.out.println("Gamemode 3 : Number de Game Fixe");
        System.out.println("Gamemode 4 : Score Fixe");
        System.out.println("Gamemode 5 : AI random VS AI simple");
        System.out.println("Gamemode 6 : Web");
        System.out.println("Donner le 'gamemode' que vous voulez jouer");
        Scanner input = new Scanner(System.in);
        int gamemode = input.nextInt();
        j.GameMode = gamemode;
        switch (gamemode) {
            case 1:
                gameStart(j,h);
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    turnstart(h);
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    System.out.println("Player 1 win!");
                } else {
                    System.out.println("Player 2 win!");
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    j.Game_ind = i;
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5) {
                            j.TurnProcess = 1;
                        }
                        turnstart(h);
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
                System.out.println("Donner le numbre de game vous voulez jouer");
                input = new Scanner(System.in);
                int nGame = input.nextInt();
                j.GameInformation = nGame;
                for (int i = 0; i < nGame; i++) {
                    j.Game_ind=i;
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        turnstart(h);
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
                System.out.println("Donner le score vous voulez jouer");
                input = new Scanner(System.in);
                int ScoreWin = input.nextInt();
                j.GameInformation = ScoreWin;
                while (j.Player1totalScore < ScoreWin && j.Player2totalScore < ScoreWin) {
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        turnstart(h);
                        if (j.Player1totalScore >= ScoreWin || j.Player2totalScore >= ScoreWin) {
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
            case 5:
                gameAI();
                break;
            case -2:
                sl.Load();
                break;

        }

    }

    public void gameAI() {
        System.out.println("Gamemode 1 : BO1");
        System.out.println("Gamemode 2 : BO3");
        System.out.println("Gamemode 3 : Number de Game Fixe");
        System.out.println("Gamemode 4 : Score Fixe");
        System.out.println("Donner le 'gamemode' que vous voulez jouer");
        Scanner input = new Scanner(System.in);
        int gamemode = input.nextInt();
        switch (gamemode) {
            case 1:
                gameStart(j,h);
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    AIvsAI();
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    System.out.println("Player 1 win!");
                } else {
                    System.out.println("Player 2 win!");
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    gameStart(j,h);
                    while (j.numberOfRounds != 27) {
                        if (j.TurnProcess == 5) {
                            j.TurnProcess = 1;
                        }
                        AIvsAI();
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
                }
                break;
            case 3:
                System.out.println("Donner le numbre de game vous voulez jouer");
                input = new Scanner(System.in);
                int nGame = input.nextInt();
                for (int i = 0; i < nGame; i++) {
                    gameStart(j,h);
                    while (j.numberOfRounds != 27) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        AIvsAI();
                    }
                    System.out.println("player 1 total score est " + j.Player1totalScore);
                    System.out.println("player 2 total score est " + j.Player2totalScore);

                }
                if (j.Player1totalScore > j.Player2totalScore) {
                    System.out.println("Player 1 win!");
                } else {
                    System.out.println("Player 2 win!");
                }
                h.cleanHistoire();
                break;
            case 4:
                System.out.println("Donner le score vous voulez jouer");
                input = new Scanner(System.in);
                int ScoreWin = input.nextInt();
                while (j.Player1totalScore < ScoreWin && j.Player2totalScore < ScoreWin) {
                    gameStart(j,h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        AIvsAI();
                        if (j.Player1totalScore >= ScoreWin || j.Player2totalScore >= ScoreWin) {
                            if (j.Player1totalScore > j.Player2totalScore) {
                                System.out.println("Player 1 win!");
                            } else {
                                System.out.println("Player 2 win!");
                            }
                            break;
                        }
                    }
                }
                System.out.println("player 1 total score est " + j.Player1totalScore);
                System.out.println("player 2 total score est " + j.Player2totalScore);
                h.cleanHistoire();
                break;
        }
    }


    public void turnstart(Histoire h) {
        j = h.listDeHistoire.get(h.listDeHistoire.size() - 1);
        playCards = new PlayCards(j, h);
        takeCard = new TakeCard(j, h);
        switch (j.TurnProcess) {
            //先手方出牌
            case 1:
                j.playerNow = j.playerFirst;
                if (IA > 0 && j.getPlayerNow() == 1) {
                    playCards.IAplaycard(j, IA);
                } else {
                    playCards.playerFirstPlayCard(j);
                }
                Jeu j1 = (Jeu) j.clone();
                h.ajouteListDeHistoire(j1);
                break;
            //后手方出牌
            case 2:
                j.playerNow = j.playerNow + 1;
                if (j.playerNow == 2) j.playerNow = 0;
                if (IA > 0 && j.getPlayerNow() == 1) {
                    playCards.IAplaycard(j, IA);
                } else {
                    playCards.playerSecondePlayCard(j);
                }
                Jeu j2 = (Jeu) j.clone();
                h.ajouteListDeHistoire(j2);
                break;
            //根据赢家，进行拿牌操作。
            //if条件是当游戏在15轮内才进行拿牌操作。因为牌堆一共30张牌，第十六轮没有牌可以拿。
            case 3:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (IA > 0 && j.playerNow == 1) {
                        takeCard.IAtakecard(j, IA);
                    } else {
                        takeCard.playerWinTakeCard(j);
                    }

                    Jeu j3 = (Jeu) j.clone();
                    h.ajouteListDeHistoire(j3);
                } else {
                    j.TurnProcess++;
                }
                break;
            case 4:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin + 1;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (IA > 0 && j.playerNow == 1) {
                        takeCard.IAtakecard(j, IA);
                    } else {
                        takeCard.playerLoseTakeCard(j);
                    }
                    Jeu j4 = (Jeu) j.clone();
                    h.ajouteListDeHistoire(j4);
                } else {
                    j.TurnProcess++;
                }

                break;
        }
    }

    public void AIvsAI() {
        j = h.listDeHistoire.get(h.listDeHistoire.size() - 1);
        playCards = new PlayCards(j, h);
        takeCard = new TakeCard(j, h);
        switch (j.TurnProcess) {
            //先手方出牌
            case 1:
                j.playerNow = j.playerFirst;
                if (j.getPlayerNow() == 1) {
                    playCards. IAplaycard(j, IA);
                } else {
                    playCards.IAplaycard(j, 3);
                }
                break;
            //后手方出牌
            case 2:
                j.playerNow = j.playerNow + 1;
                if (j.playerNow == 2) j.playerNow = 0;
                if (IA > 0 && j.getPlayerNow() == 1) {
                    playCards.IAplaycard(j, IA);
                } else {
                    playCards.IAplaycard(j, 3);
                }
                break;
            //根据赢家，进行拿牌操作。
            //if条件是当游戏在15轮内才进行拿牌操作。因为牌堆一共30张牌，第十六轮没有牌可以拿。
            case 3:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin;
                    if (IA > 0 && j.playerNow == 1) {
                        takeCard.IAtakecard(j, IA);
                    } else {
                        takeCard.IAtakecard(j, 3);
                    }

                } else {
                    j.TurnProcess++;
                }
                break;
            case 4:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin + 1;
                    if (j.playerNow == 2) j.playerNow = 0;
                    if (IA > 0 && j.playerNow == 1) {
                        takeCard.IAtakecard(j, IA);
                    } else {
                        takeCard.IAtakecard(j, 3);
                    }
                } else {
                    j.TurnProcess++;
                }
                break;
        }
    }
}


