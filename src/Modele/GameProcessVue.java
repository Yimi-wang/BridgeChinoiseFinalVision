package Modele;

import Controleur.PlayCards;
import Controleur.SaveLoad;
import Controleur.TakeCard;
import Web.GameStartInternet;
import global.Configuration;

import static java.lang.System.exit;

public class GameProcessVue {
    int player2;
    int player1;
    int IA;
    Jeu j;
    Atout a;
    Histoire h;
    PlayCards playCards;
    TakeCard takeCard;
    GameStartInternet GSI = new GameStartInternet();

    public GameProcessVue() {
    }

    public Jeu creatJeu() {
        player1 = 0;
        player2 = 0;
        j = new Jeu();
        a = new Atout(j);
        h = new Histoire(j);
        String GameMode = Configuration.instance().lis("GameMode");
        int GameModei = Integer.parseInt(GameMode);
        if (GameModei > 2) {
            String GameInformation = Configuration.instance().lis("GameInformation");
            int GameInformationi = Integer.parseInt(GameInformation);
            j.GameInformation = GameInformationi;
        }

        String AI = Configuration.instance().lis("AI");
        int AIi = Integer.parseInt(AI);
        j.AI = AIi;
        j.GameMode = GameModei;

        j.playerFirst = 2;
        IA = j.AI;
        StartHand startHand = new StartHand(j);
        startHand.stardHand();
        //gameMode(j);
        return j;
    }

    public void gameStart(Jeu j, Histoire h) {
        j.reset();
        if (j.numberOfGames == 0) j.numberOfGames = 1;//如果游戏刚开始的话
        if (j.playerFirst == 2) {//如果本轮该开始的话，判断哪个玩家先开始游戏。
            j.playerFirst = (j.numberOfGames - 1) % 2;
            j.numberOfRounds = 1;
            //进行发牌以及牌堆的实现
            if (j.numberOfGames != 1) {
                StartHand startHand = new StartHand(j);
                startHand.stardHand();
            }
            a.determinerAtout();
            Jeu j0 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j0);
            Jeu j100 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j100);

        }
    }

    public void gameMode(Jeu j) {
        SaveLoad sl = new SaveLoad();
        int gamemode = j.GameMode;
        int nGame = j.GameInformation;
        if (j.AI == 4) gameAI(j);
        switch (gamemode) {
            case 1:
                gameStart(j, h);

                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    j = turnstart(h);
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
                    gameStart(j, h);

                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5) {
                            j.TurnProcess = 1;
                        }
                        j = turnstart(h);
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
                System.out.println(nGame);
                for (int i = 0; i < nGame; i++) {
                    j.Game_ind = i;
                    gameStart(j, h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        j = turnstart(h);
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
                int ScoreWin = j.GameInformation;
                System.out.println(ScoreWin);
                System.out.println(j.Player1totalScore);
                while (j.Player1totalScore < ScoreWin && j.Player2totalScore < ScoreWin) {
                    System.out.println(j.Player1totalScore);
                    gameStart(j, h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        j = turnstart(h);
                        System.out.println(j.Player1totalScore);
                        if (j.Player1totalScore >= ScoreWin || j.Player2totalScore >= ScoreWin) {
                            if (j.Player1totalScore > j.Player2totalScore) {
                                System.out.println("Joueur 1 a gagné!");
                            } else {
                                System.out.println("Joueur 2 a gagné!");
                            }
                            h.cleanHistoire();
                            exit(0);
                            break;
                        }
                    }

                }
                h.cleanHistoire();
                break;
        }

    }

    public void gameAI(Jeu j) {
        int gamemode = j.GameMode;
        int nGame = j.GameInformation;
        int ScoreWin = j.GameInformation;
        switch (gamemode) {
            case 1:
                System.out.println("c'est le mode 1");
                gameStart(j, h);
                //进行26轮游戏（因为一共52张牌）
                while (j.numberOfRounds != 26) {
                    if (j.TurnProcess == 5)
                        j.TurnProcess = 1;
                    j = AIvsAI();
                }
                //游戏结束，判断胜负手
                if (j.Player1Score > j.Player2Score) {
                    System.out.println("Joueur 1 a gagné!");
                } else {
                    System.out.println("Joueur 2 a gagné!");
                }
                break;
            case 2:
                System.out.println("c'est mode 2");
                for (int i = 0; i < 3; i++) {
                    gameStart(j, h);
                    while (j.numberOfRounds != 27) {
                        if (j.TurnProcess == 5) {
                            j.TurnProcess = 1;
                        }
                        j = AIvsAI();
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
                }
                break;
            case 3:
                System.out.println("c'est mode 3");
                for (int i = 0; i < nGame; i++) {
                    gameStart(j, h);
                    while (j.numberOfRounds != 27) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        j = AIvsAI();
                    }
                    System.out.println("Le score total du joueur 1 est " + j.Player1totalScore);
                    System.out.println("Le score total du joueur 2 est " + j.Player2totalScore);

                }
                if (j.Player1totalScore > j.Player2totalScore) {
                    System.out.println("Joueur 1 a gagné!");
                } else {
                    System.out.println("Joueur 2 a gagné!");
                }
                h.cleanHistoire();
                break;
            case 4:
                System.out.println("c'est mode 4");
                while (j.Player1totalScore < ScoreWin && j.Player2totalScore < ScoreWin) {
                    gameStart(j, h);
                    while (j.numberOfRounds != 26) {
                        if (j.TurnProcess == 5)
                            j.TurnProcess = 1;
                        j = AIvsAI();
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
                System.out.println("Le score total du joueur 1 est " + j.Player1totalScore);
                System.out.println("Le score total du joueur 2 est " + j.Player2totalScore);
                h.cleanHistoire();
                break;
        }
    }


    public Jeu turnstart(Histoire h) {
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
        return j;
    }

    public Jeu AIvsAI() {
        j = h.listDeHistoire.get(h.listDeHistoire.size() - 1);
        playCards = new PlayCards(j, h);
        takeCard = new TakeCard(j, h);
        switch (j.TurnProcess) {
            //先手方出牌
            case 1:
                j.playerNow = j.playerFirst;
                if (j.getPlayerNow() == 1) {
                    j = playCards.IAplaycard(j, 2);
                } else {
                    j = playCards.IAplaycard(j, 3);
                }

                //告诉vue，重新读取jeu的数据
                //vue.reload(Jeu j);


                break;
            //后手方出牌
            case 2:
                j.playerNow = j.playerNow + 1;
                if (j.playerNow == 2) j.playerNow = 0;
                if (IA > 0 && j.getPlayerNow() == 1) {
                    j = playCards.IAplaycard(j, 2);
                } else {
                    j = playCards.IAplaycard(j, 3);
                }
                break;
            //根据赢家，进行拿牌操作。
            //if条件是当游戏在15轮内才进行拿牌操作。因为牌堆一共30张牌，第十六轮没有牌可以拿。
            case 3:
                if (j.numberOfRounds <= 15) {
                    j.playerNow = j.Playerwin;
                    if (IA > 0 && j.playerNow == 1) {
                        j = takeCard.IAtakecard(j, 2);
                    } else {
                        j = takeCard.IAtakecard(j, 3);
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
                        j = takeCard.IAtakecard(j, 2);
                    } else {
                        j = takeCard.IAtakecard(j, 3);
                    }
                } else {
                    j.TurnProcess++;
                }
                break;
        }
        return j;
    }
}
