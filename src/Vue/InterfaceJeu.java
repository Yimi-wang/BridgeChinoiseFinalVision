package Vue;

import Controleur.*;
import Modele.*;
import global.Configuration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import static java.lang.System.exit;


public class InterfaceJeu implements Runnable {
    Jeu j;
    int player2;
    int player1;
    int IA;
    Atout a;
    public Brand selected;
    Histoire h;
    PlayCards playCards;
    TakeCard takeCard;
    JButton player0play;
    JButton player1play;
    JLabel Atout;
    int a1 = 0;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        SwingUtilities.invokeLater(new InterfaceJeu());
    }

    public void run() {
        //MusicTest m = new MusicTest();
       // m.play();
        //不知道用处
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //保证Jeu j playercard 里头有手卡
        j = creatJeu();
        gameStart(j, h);
        //gameprocessvue.gameMode(j);（启动游戏）

        //界面
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //end

        GamePane cardinterface = new GamePane(j);
        cardinterface.setLayout(null);

        frame.setSize(1200, 800);
        frame.add(cardinterface);
        //初始化正常界面大小
        frame.pack();
        //end

        //窗口位于屏幕中间
        frame.setLocationRelativeTo(null);
        //end

        //显示窗口
        frame.setVisible(true);
    }

    //构造函数
    public InterfaceJeu() {
    }

    public class GamePane extends JPanel {
        PlayCardsVue playcard = new PlayCardsVue(j, h);
        TakeCardVue takecard = new TakeCardVue(j, h);
        private Map<Brand, Rectangle> mapCards;
        Brand player1playercard;
        Brand player2playercard;

        public GamePane(Jeu j) {
            //点击鼠标，如果点击是卡的位置，则会将卡向上移，不是的话已经移动过的卡回退原来的位置）

            mapCards = new HashMap<>(j.playercard[0].size());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selected != null) {
                        Rectangle bounds = mapCards.get(selected);
                        player0play.setText("HINT");
                        player1play.setText("HINT");
                        if (selected.getPlace() == 0)
                            bounds.y += 30;
                        else if (selected.getPlace() == 1) bounds.y -= 30;
                        repaint();
                    }
                    selected = null;
                    if (j.TurnProcess == 1) {
                        if (j.playerFirst == 0) {
                            for (Brand card : j.playercard[0]) {
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains(e.getPoint())) {
                                    selected = card;
                                    bounds.y -= 30;
                                    //打印玩家手牌
                                    player0play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }
                            }
                        } else
                        {
                            for (Brand card : j.playercard[1]) {
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains(e.getPoint())) {
                                    selected = card;
                                    bounds.y += 30;
                                    //打印玩家手牌
                                    player1play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }

                            }
                        }
                    } else if (j.TurnProcess == 2) {
                        for (Brand card : j.playercard[0]) {
                            if (playcard.limite(j, card)) {
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains(e.getPoint())) {
                                    selected = card;
                                    bounds.y -= 30;
                                    //打印玩家手牌
                                    player0play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }
                            }
                        }
                        for (Brand card : j.playercard[1]) {
                            if (playcard.limite(j, card)) {
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains(e.getPoint())) {
                                    selected = card;
                                    bounds.y += 30;
                                    //打印玩家手牌
                                    player1play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }
                            }
                        }
                    } else if (j.TurnProcess >= 3 && j.numberOfRounds <= 15) {
                        for (int i = 0; i <= 5; i++) {
                            Brand card = j.pilescard[i].get(0);
                            Rectangle bounds = mapCards.get(card);
                            if (bounds.contains((e.getPoint()))) {
                                if (j.TurnProcess == 3)
                                    takecard.playerWinTakeCard(j, card);
                                else takecard.playerLoseTakeCard(j, card);
                                drawCHANGFANGXING();
                                repaint();
                            }
                        }
                    }

                }
            });


        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1200, 800);
        }

        @Override
        public void invalidate() {
            super.invalidate();
            mapCards.clear();
            this.removeAll();
            this.repaint();
            //player0出牌按钮
            player0play = new JButton();
            player0play.setBounds(getWidth() / 3, getHeight() / 20 * 15, getWidth() / 15, getHeight() / 20);
            player0play.setText("HINT");

            player0play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player0play.getText() == "PLAY") {
                        if (j.playerNow == 0) {
                            System.out.println("playcard");
                            player1playercard=selected;
                            if(j.playerNow==j.playerFirst)
                            playcard.playerFirstPlayCard(j, selected);
                            else playcard.playerSecondePlayCard(j,selected);
                            drawCHANGFANGXING();
                            repaint();
                            j.playerNow = (j.playerNow + 1) % 2;
                        }
                    } else System.out.println("1");
                }
            });

            //player1出牌按钮
            player1play = new JButton();
            player1play.setBounds(getWidth() / 3, getHeight() / 20 * 4, getWidth() / 15, getHeight() / 20);
            player1play.setText("HINT");

            player1play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player1play.getText() == "PLAY") {
                        if (j.playerNow == 1) {
                            System.out.println("playcard");
                            player2playercard=selected;
                            if(j.playerNow==j.playerFirst)
                                playcard.playerFirstPlayCard(j, selected);
                            else playcard.playerSecondePlayCard(j,selected);
                            drawCHANGFANGXING();
                            repaint();
                            j.playerNow = (j.playerNow + 1) % 2;
                        }
                    } else System.out.println("1");
                }
            });

            this.add(player0play);
            this.add(player1play);

            //右边游戏信息
            drawCHANGFANGXING();
        }

        public void drawCHANGFANGXING() {
            //画长方形，并且将每个长方形和card绑定
            //确定每个卡片的高与宽
            int cardHeight = (getHeight() - 20) / 8;
            int cardWidth = (int) (cardHeight * 0.6);
            //每个卡片的偏移量
            int xDelta = cardWidth + 5;
            //第一个卡片的横坐标和纵坐标
            int xPos = getWidth() / 10;
            int yPos = (getHeight() - 20) - cardHeight;
            //建立一个hashmap，使每个长方形和每个卡片进行对应。显示下方玩家的手牌。
            for (Brand card : j.playercard[0]) {
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                mapCards.put(card, bounds);
                xPos += xDelta;
            }
            //显示上方的手牌
            xPos = getWidth() / 10;
            yPos = getHeight() / 20;
            for (Brand card : j.playercard[1]) {
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                mapCards.put(card, bounds);
                xPos += xDelta;
            }
            //显示pile的手卡。
            xPos = getWidth() / 5 * 3;
            xDelta = cardWidth / 5;
            yPos = getHeight() / 2 - cardHeight / 2;
            for (int i = 0; i <= 5; i++) {
                for (int a = j.pilescard[i].size() - 1; a >= 0; a--) {
                    Brand card = j.pilescard[i].get(a);
                    Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                    mapCards.put(card, bounds);
                    xPos -= xDelta;
                }
                xPos = xPos - getWidth() / 20;
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            //背景图片（游戏主界面）
            BufferedImage imageBackGround;
            File ImageBackGroundFile;
            ImageBackGroundFile = new File("./res/images/background.png");
            try {
                imageBackGround = ImageIO.read(ImageBackGroundFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g2d.drawImage(imageBackGround,0, 0, getWidth()/5*4,getWidth() , null);


            //背景界面（游戏右菜单）
            BufferedImage imageBackGroundRight;
            File ImageBackGroundRightFile;
            ImageBackGroundRightFile = new File("./res/images/backright.jpg");
            try {
                imageBackGroundRight = ImageIO.read(ImageBackGroundRightFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g2d.drawImage(imageBackGroundRight,getWidth()/5*4, 0, getWidth(),getWidth() , null);




            if (j.TurnProcess == 2 || j.TurnProcess == 3) {
                //画先手方出的牌
                if (j.TurnProcess == 2) {
                    BufferedImage imageCard;
                    File imgFilecard = null;
                    if(j.playerFirst==0){
                    imgFilecard = new File("./res/images/card (" + player1playercard.id + ").png");}
                    else {imgFilecard = new File("./res/images/card (" + player2playercard.id + ").png");
                    }
                    try {
                        imageCard = ImageIO.read(imgFilecard);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (j.playerNow == 1) {
                        a1 = 1;
                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        g2d.drawImage(imageCard, getWidth() / 3, getHeight() / 20 * 12, cardWidth, cardHeight, null);
                    } else {
                        a1 = 0;
                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        g2d.drawImage(imageCard, getWidth() / 3, getHeight() / 20 * 5, cardWidth, cardHeight, null);
                    }
                }
                //打印双方出的牌
                if (j.TurnProcess == 3) {
                    //打印先手方出的牌
                    BufferedImage imageCard;
                    File imgFilecard = new File("./res/images/card (" + player1playercard.id + ").png");
                    try {
                        imageCard = ImageIO.read(imgFilecard);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        g2d.drawImage(imageCard, getWidth() / 3, getHeight() / 20 * 12, cardWidth, cardHeight, null);

                    //后手方出的牌
                    imgFilecard = new File("./res/images/card (" + player2playercard.id + ").png");
                    try {
                        imageCard = ImageIO.read(imgFilecard);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                        g2d.drawImage(imageCard, getWidth() / 3, getHeight() / 20 * 5, cardWidth, cardHeight, null);

                }
            }
            for (Brand card : j.playercard[1]) {
                Rectangle bounds = mapCards.get(card);
                //根据长方形的位置，填充图片
                if (bounds != null) {
                    BufferedImage imageCard;
                    File imgFile = new File("./res/images/card (" + card.id + ").png");
                    try {
                        imageCard = ImageIO.read(imgFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    //画图像
                    g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                    //画长方形边框
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                    if ((j.playerFirst != j.playerNow) && !playcard.limite(j, card) && j.playerNow == 1 && j.TurnProcess == 2) {
                        g2d.setColor(Color.RED);
                        g2d.drawLine(bounds.x, bounds.y, bounds.x + cardWidth, bounds.y + cardHeight);
                    }
                }
            }
            for (Brand card : j.playercard[0]) {
                Rectangle bounds = mapCards.get(card);
                //System.out.println(bounds);
                //根据长方形的位置，填充图片
                if (bounds != null) {
                    BufferedImage imageCard;
                    File imgFile = new File("./res/images/card (" + card.id + ").png");
                    try {
                        imageCard = ImageIO.read(imgFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    //画图像
                    g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                    //画长方形边框
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                    if ((j.playerFirst != j.playerNow) && !playcard.limite(j, card) && j.playerNow == 0 && j.TurnProcess == 2) {
                        g2d.setColor(Color.RED);
                        g2d.drawLine(bounds.x, bounds.y, bounds.x + cardWidth, bounds.y + cardHeight);
                    }
                }
            }
            for (int i = 0; i <= 5; i++) {

                for (int a = j.pilescard[i].size() - 1; a >= 0; a--) {
                    Brand card = j.pilescard[i].get(a);
                    Rectangle bounds = mapCards.get(card);
                    //System.out.println(bounds);
                    //根据长方形的位置，填充图片
                    if (bounds != null) {
                        BufferedImage imageCard;
                        File imgFile;
                        if (a == 0) {
                            imgFile = new File("./res/images/card (" + card.id + ").png");
                        } else {
                            imgFile = new File("./res/images/back.png");
                        }

                        try {
                            imageCard = ImageIO.read(imgFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        //画图像
                        g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                        //画长方形边框
                        g2d.setColor(Color.BLACK);
                        g2d.draw(bounds);
                    }
                }
            }

            String[] dfonts;
            dfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

            //输出右边游戏信息。
            //Atout 文字
            g2d.setColor(Color.red);
            g2d.setFont(new Font(dfonts[1],Font.BOLD,30));
            g2d.drawString("Atout",getWidth()/100*85,getHeight()/15);
            //BRAND 图片
            BufferedImage imageCardAtout;
            File ImageAtoutFile;
            if(j.avoiratout){
                ImageAtoutFile = new File("./res/images/Atout" + j.atout.getInttype() + ".png");
            }else{
                ImageAtoutFile = new File("./res/images/back.png");
            }
            try {
                imageCardAtout = ImageIO.read(ImageAtoutFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g2d.drawImage(imageCardAtout,getWidth()/100*82, getHeight()/100*8, getWidth()/100*15,(getWidth()/100*15)/6*10 , null);

            //GameMode 游戏模式输出
            String GameModeStr = "GameMode est :";
            String GameMode2 = null;
            switch (j.GameMode){
                case 1 :
                    GameModeStr=GameModeStr.concat("BO1");
                    break;
                case 2 :
                    GameModeStr=GameModeStr.concat("BO3");
                    break;
                case 3 :
                    GameModeStr=GameModeStr.concat("Nombre fixe de tours");
                    GameMode2="Le tours pour gagner est "+j.GameInformation;
                    break;
                case 4 :
                    GameModeStr=GameModeStr.concat("Nombre fix de Score");
                    GameMode2="Le score pour gagner est "+j.GameInformation;
                    break;
            }
            g2d.setColor(Color.green);
            g2d.setFont(new Font(dfonts[2],Font.BOLD,15));
            g2d.drawString(GameModeStr,getWidth()/100*82,getHeight()/100*55);
            if(GameMode2!=null){
                g2d.drawString(GameModeStr,getWidth()/100*82,getHeight()/100*60);
            }

            //numbre de tour  回合数
            String nbtour="Le numbre de tour est "+j.numberOfRounds;
            g2d.setColor(Color.blue);
            g2d.setFont(new Font(dfonts[3],Font.BOLD,15));
            g2d.drawString(nbtour,getWidth()/100*82,getHeight()/100*65);
            //playernow  哪个玩家进行操作
            g2d.setColor(Color.yellow);
            g2d.setFont(new Font(dfonts[4],Font.BOLD,15));
            g2d.drawString("C'est le tour de joueur "+j.getPlayerNow()+1,getWidth()/100*82,getHeight()/100*70);
            //playercard ou takecard该出牌还是拿牌
            g2d.setColor(Color.green);
            g2d.setFont(new Font(dfonts[5],Font.BOLD,15));
            if(j.TurnProcess<3){
                g2d.drawString("Veuillez montrer vos cartes",getWidth()/100*82,getHeight()/100*75);
            }else g2d.drawString("Veuillez prendre une carte",getWidth()/100*82,getHeight()/100*75);
            //显示玩家1得分
            g2d.setColor(Color.PINK);
            g2d.setFont(new Font(dfonts[8],Font.BOLD,15));
            g2d.drawString("Player 01 total score est "+j.Player1totalScore,getWidth()/100*82,getHeight()/100*80);
            //显示玩家2得分
            g2d.setColor(Color.cyan);
            g2d.setFont(new Font(dfonts[7],Font.BOLD,15));
            g2d.drawString("Player 02 total score est "+j.Player2totalScore,getWidth()/100*82,getHeight()/100*85);


            g2d.dispose();
        }

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


}