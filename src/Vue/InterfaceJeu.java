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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static java.lang.System.exit;


public class InterfaceJeu implements Runnable {

    Jeu j;
    int player2;
    int player1;
    int IA;
    Atout a;
    public Brand selected;
    Histoire h;
    JButton player0play;
    JButton player1play;
    JLabel Atout;
    int a1 = 0;

    boolean showcard;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        SwingUtilities.invokeLater(new InterfaceJeu());
    }

    public void run() {
        //MusicTest m = new MusicTest();
        // m.play();
        //涓嶇煡閬撶敤澶?
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //确保jue里头有手卡
        j = creatJeu();
        gameStart(j, h);
        //gameprocessvue.gameMode(j);锛堝惎鍔ㄦ父鎴忥級

        //鐣岄潰
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //end

        GamePane cardinterface = new GamePane(j);
        cardinterface.setLayout(null);

        frame.setSize(1200, 800);
        frame.setMinimumSize(new Dimension(1200, 800));
        /**
         * 游戏菜单栏实现
         */

        //主菜单
        JMenuBar menuBar = new JMenuBar();
        JMenu saveMenu = new JMenu("Save");
        JMenu backMenu = new JMenu("Back");
        JMenu surrenderMenu = new JMenu("Surrender");
        JMenu showcardMenu = new JMenu("Showcard");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(saveMenu);
        menuBar.add(backMenu);
        menuBar.add(surrenderMenu);
        menuBar.add(showcardMenu);
        menuBar.add(helpMenu);
        /**
         * Save主菜单和监听以及对于方法
         */
        //Save菜单子菜单
        JMenuItem saveMenuItem01 = new JMenuItem("Save and continue");
        JMenuItem saveMenuItem02 = new JMenuItem("Save and quit");
        saveMenu.add(saveMenuItem01);
        saveMenu.add(saveMenuItem02);
        //TODO save 实现
        saveMenuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
                //SaveLoadVue.Save(h,"name");
            }
        });
        saveMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });
        /**
         * Back主菜单和监听和对应方法
         */
        //Back菜单子菜单
        JMenuItem backMenuItem01 = new JMenuItem("Back to your trun");
        JMenuItem backMenuItem02 = new JMenuItem("Restart");
        backMenu.add(backMenuItem01);
        backMenu.add(backMenuItem02);
        //监听和方法
        backMenuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                j = h.returnHistoire();
                j = h.returnHistoire();
                cardinterface.changejue(j);
                cardinterface.drawCHANGFANGXING(j);
                cardinterface.repaint();
            }

        });
        backMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });
        /**
         * Surrender 主菜单和监听和对应方法
         */
        //surrender菜单子菜单
        JMenuItem surrenderMenuItem01 = new JMenuItem("Surrender this game");
        JMenuItem surrenderMenuItem02 = new JMenuItem("Surrender all game");
        surrenderMenu.add(surrenderMenuItem01);
        surrenderMenu.add(surrenderMenuItem02);
        //监听
        surrenderMenuItem01.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });
        surrenderMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });
        /**
         * showcard主菜单和监听和对应方法
         */
        //shoucard菜单子菜单
        final JRadioButtonMenuItem showcardTrue = new JRadioButtonMenuItem("yes");
        final JRadioButtonMenuItem showcardFalse = new JRadioButtonMenuItem("no");
        showcardMenu.add(showcardTrue);
        showcardMenu.add(showcardFalse);
        // 其中两个 单选按钮子菜单，要实现单选按钮的效果，需要将它们放到一个按钮组中
        ButtonGroup showcardgroup = new ButtonGroup();
        showcardgroup.add(showcardTrue);
        showcardgroup.add(showcardFalse);

        // 默认第一个单选按钮子菜单选中
        showcardFalse.setSelected(true);

        showcardTrue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showcard = true;
                cardinterface.repaint();
            }
        });
        showcardFalse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showcard = false;
                cardinterface.repaint();
            }
        });
        /**
         * help主菜单和监听和对应方法。
         */
        //help 菜单子菜单
        JMenuItem helpMenuItem01 = new JMenuItem("help");
        JMenuItem helpMenuItem02 = new JMenuItem("regle");
        helpMenu.add(helpMenuItem01);
        helpMenu.add(helpMenuItem02);
        //监听
        helpMenuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });
        helpMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建  被点击");
            }
        });

        //添加到frame
        frame.setJMenuBar(menuBar);
        frame.add(cardinterface);
        //大小合适
        frame.pack();
        //位置居中
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    //鏋勯?犲嚱鏁?
    public InterfaceJeu() {
    }

    public class GamePane extends JPanel {
        Jeu j;
        PlayCardsVue playcard = new PlayCardsVue(j, h);
        TakeCardVue takecard = new TakeCardVue(j, h);
        private Map<Brand, Rectangle> mapCards;
        Brand player1playercard;
        Brand player2playercard;

        public GamePane(Jeu j) {
            //鐐瑰嚮榧犳爣锛屽鏋滅偣鍑绘槸鍗＄殑浣嶇疆锛屽垯浼氬皢鍗″悜涓婄Щ锛屼笉鏄殑璇濆凡缁忕Щ鍔ㄨ繃鐨勫崱鍥為??鍘熸潵鐨勪綅缃級

            this.j = j;
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
                                    //鎵撳嵃鐜╁鎵嬬墝
                                    player0play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }
                            }
                        } else {
                            for (Brand card : j.playercard[1]) {
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains(e.getPoint())) {
                                    selected = card;
                                    bounds.y += 30;
                                    //鎵撳嵃鐜╁鎵嬬墝
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
                                    //鎵撳嵃鐜╁鎵嬬墝
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
                                    //鎵撳嵃鐜╁鎵嬬墝
                                    player1play.setText("PLAY");
                                    System.out.println(card.toString());
                                    repaint();
                                    break;
                                }
                            }
                        }
                    } else if (j.TurnProcess >= 3 && j.numberOfRounds <= 15) {
                        for (int i = 0; i <= 5; i++) {
                            if (j.pilescard[i].size() > 0) {
                                Brand card = j.pilescard[i].get(0);
                                Rectangle bounds = mapCards.get(card);
                                if (bounds.contains((e.getPoint()))) {
                                    if (j.TurnProcess == 3)
                                        takecard.playerWinTakeCard(j, card);
                                    else takecard.playerLoseTakeCard(j, card);
                                    drawCHANGFANGXING(j);
                                    repaint();
                                }
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
            //player0鍑虹墝鎸夐挳
            player0play = new JButton();
            player0play.setBounds(getWidth() / 3, getHeight() / 20 * 15, getWidth() / 15, getHeight() / 20);
            player0play.setText("HINT");

            player0play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player0play.getText() == "PLAY") {
                        if (j.playerNow == 0) {
                            System.out.println("playcard");
                            player1playercard = selected;
                            if (j.playerNow == j.playerFirst)
                                playcard.playerFirstPlayCard(j, selected);
                            else {
                                playcard.playerSecondePlayCard(j, selected);
                                estFINI(j, h);
                            }
                            drawCHANGFANGXING(j);

                            repaint();
                        }
                    } else System.out.println("1");
                }
            });

            //player1鍑虹墝鎸夐挳
            player1play = new JButton();
            player1play.setBounds(getWidth() / 3, getHeight() / 20 * 4, getWidth() / 15, getHeight() / 20);
            player1play.setText("HINT");

            player1play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player1play.getText() == "PLAY") {
                        if (j.playerNow == 1) {
                            System.out.println("playcard");
                            player2playercard = selected;
                            if (j.playerNow == j.playerFirst)
                                playcard.playerFirstPlayCard(j, selected);
                            else {
                                playcard.playerSecondePlayCard(j, selected);
                                estFINI(j, h);
                            }
                            drawCHANGFANGXING(j);
                            repaint();
                        }
                    } else System.out.println("1");
                }
            });

            this.add(player0play);
            this.add(player1play);

            //鍙宠竟娓告垙淇℃伅
            drawCHANGFANGXING(j);
        }

        public void drawCHANGFANGXING(Jeu j) {
            //鐢婚暱鏂瑰舰锛屽苟涓斿皢姣忎釜闀挎柟褰㈠拰card缁戝畾
            //纭畾姣忎釜鍗＄墖鐨勯珮涓庡
            int cardHeight = (getHeight() - 20) / 8;
            int cardWidth = (int) (cardHeight * 0.6);
            //姣忎釜鍗＄墖鐨勫亸绉婚噺
            int xDelta = cardWidth + 5;
            //绗竴涓崱鐗囩殑妯潗鏍囧拰绾靛潗鏍?
            int xPos = getWidth() / 10;
            int yPos = (getHeight() - 20) - cardHeight;
            //寤虹珛涓?涓猦ashmap锛屼娇姣忎釜闀挎柟褰㈠拰姣忎釜鍗＄墖杩涜瀵瑰簲銆傛樉绀轰笅鏂圭帺瀹剁殑鎵嬬墝銆?
            for (Brand card : j.playercard[0]) {
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                mapCards.put(card, bounds);
                xPos += xDelta;
            }
            //鏄剧ず涓婃柟鐨勬墜鐗?
            xPos = getWidth() / 10;
            yPos = getHeight() / 20;
            for (Brand card : j.playercard[1]) {
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                mapCards.put(card, bounds);
                xPos += xDelta;
            }
            //鏄剧ずpile鐨勬墜鍗°??
            int xOri = 60;
            xPos = getWidth() / 100 * xOri;
            xDelta = cardWidth / 5;
            yPos = getHeight() / 2 - cardHeight / 2;
            for (int i = 0; i <= 5; i++) {
                for (int a = j.pilescard[i].size() - 1; a >= 0; a--) {
                    Brand card = j.pilescard[i].get(a);
                    Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                    mapCards.put(card, bounds);
                    xPos -= xDelta;
                }
                xOri -= 10;
                xPos = getWidth() / 100 * xOri;
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            //TODO setting background.
//            //背景
//            BufferedImage imageBackGround;
//            File ImageBackGroundFile;
//            ImageBackGroundFile = new File("./res/images/background.png");
//            try {
//                imageBackGround = ImageIO.read(ImageBackGroundFile);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            g2d.drawImage(imageBackGround, 0, 0, getWidth() / 5 * 4, getWidth(), null);
//
//
//            //右边背景
//            BufferedImage imageBackGroundRight;
//            File ImageBackGroundRightFile;
//            ImageBackGroundRightFile = new File("./res/images/backright.jpg");
//            try {
//                imageBackGroundRight = ImageIO.read(ImageBackGroundRightFile);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            g2d.drawImage(imageBackGroundRight, getWidth() / 5 * 4, 0, getWidth(), getWidth(), null);


            if (j.TurnProcess == 2 || j.TurnProcess == 3) {
                //鐢诲厛鎵嬫柟鍑虹殑鐗?
                if (j.TurnProcess == 2) {
                    BufferedImage imageCard;
                    File imgFilecard = null;
                    if (j.playerFirst == 0) {
                        imgFilecard = new File("./res/images/card (" + player1playercard.id + ").png");
                    } else {
                        imgFilecard = new File("./res/images/card (" + player2playercard.id + ").png");
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
                //鎵撳嵃鍙屾柟鍑虹殑鐗?
                if (j.TurnProcess == 3) {
                    //鎵撳嵃鍏堟墜鏂瑰嚭鐨勭墝
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

                    //鍚庢墜鏂瑰嚭鐨勭墝
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
                //鏍规嵁闀挎柟褰㈢殑浣嶇疆锛屽～鍏呭浘鐗?
                if (bounds != null) {
                    BufferedImage imageCard;
                    if (showcard || j.playerNow == 1) {
                        if ((j.playerFirst != j.playerNow) && !playcard.limite(j, card) && j.playerNow == 1 && j.TurnProcess == 2) {
                            File imgFile = new File("./res/images/Bcard (" + card.id + ").png");
                            try {
                                imageCard = ImageIO.read(imgFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            File imgFile = new File("./res/images/card (" + card.id + ").png");
                            try {
                                imageCard = ImageIO.read(imgFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        //鐢诲浘鍍?
                        g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                        //鐢婚暱鏂瑰舰杈规
                        g2d.setColor(Color.BLACK);
                        g2d.draw(bounds);
                    } else {
                        File imgFile = new File("./res/images/back.png");
                        try {
                            imageCard = ImageIO.read(imgFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    //鐢诲浘鍍?
                    g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                    //鐢婚暱鏂瑰舰杈规
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                }
            }


            for (Brand card : j.playercard[0]) {
                Rectangle bounds = mapCards.get(card);
                //System.out.println(bounds);
                //鏍规嵁闀挎柟褰㈢殑浣嶇疆锛屽～鍏呭浘鐗?
                if (bounds != null) {
                    BufferedImage imageCard;
                    if (showcard || j.playerNow == 0) {
                        if ((j.playerFirst != j.playerNow) && !playcard.limite(j, card) && j.playerNow == 0 && j.TurnProcess == 2) {
                            File imgFile = new File("./res/images/Bcard (" + card.id + ").png");
                            try {
                                imageCard = ImageIO.read(imgFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            File imgFile = new File("./res/images/card (" + card.id + ").png");
                            try {
                                imageCard = ImageIO.read(imgFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        int cardHeight = (getHeight() - 20) / 8;
                        int cardWidth = (int) (cardHeight * 0.6);
                        //鐢诲浘鍍?
                        g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                        //鐢婚暱鏂瑰舰杈规
                        g2d.setColor(Color.BLACK);
                        g2d.draw(bounds);
                    } else {
                        File imgFile = new File("./res/images/back.png");
                        try {
                            imageCard = ImageIO.read(imgFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    //鐢诲浘鍍?
                    g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                    //鐢婚暱鏂瑰舰杈规
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);
                }
            }

            for (int i = 0; i <= 5; i++) {

                for (int a = j.pilescard[i].size() - 1; a >= 0; a--) {
                    Brand card = j.pilescard[i].get(a);
                    Rectangle bounds = mapCards.get(card);
                    //System.out.println(bounds);
                    //鏍规嵁闀挎柟褰㈢殑浣嶇疆锛屽～鍏呭浘鐗?
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
                        //鐢诲浘鍍?
                        g2d.drawImage(imageCard, bounds.x, bounds.y, cardWidth, cardHeight, null);
                        //鐢婚暱鏂瑰舰杈规
                        g2d.setColor(Color.BLACK);
                        g2d.draw(bounds);
                    }
                }
            }

            String[] dfonts;
            dfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().

                    getAvailableFontFamilyNames();

            //杈撳嚭鍙宠竟娓告垙淇℃伅銆?
            //Atout 鏂囧瓧
            g2d.setColor(Color.red);
            g2d.setFont(new

                    Font(dfonts[1], Font.BOLD, 30));
            g2d.drawString("Atout",

                    getWidth() / 100 * 85,

                    getHeight() / 15);
            //BRAND 鍥剧墖
            BufferedImage imageCardAtout;
            File ImageAtoutFile;
            if (j.avoiratout) {
                ImageAtoutFile = new File("./res/images/Atout" + j.atout.getInttype() + ".png");
            } else {
                ImageAtoutFile = new File("./res/images/back.png");
            }
            try {
                imageCardAtout = ImageIO.read(ImageAtoutFile);
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            g2d.drawImage(imageCardAtout,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 8,

                    getWidth() / 100 * 15, (

                            getWidth() / 100 * 15) / 6 * 10, null);

            //GameMode 娓告垙妯″紡杈撳嚭
            String GameModeStr = "GameMode est :";
            String GameMode2 = null;
            switch (j.GameMode) {
                case 1:
                    GameModeStr = GameModeStr.concat("BO1");
                    break;
                case 2:
                    GameModeStr = GameModeStr.concat("BO3");
                    break;
                case 3:
                    GameModeStr = GameModeStr.concat("Nombre fixe de tours");
                    GameMode2 = "Le tours pour gagner est " + j.GameInformation;
                    break;
                case 4:
                    GameModeStr = GameModeStr.concat("Nombre fix de Score");
                    GameMode2 = "Le score pour gagner est " + j.GameInformation;
                    break;
            }
            g2d.setColor(Color.green);
            g2d.setFont(new

                    Font(dfonts[2], Font.BOLD, 15));
            g2d.drawString(GameModeStr,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 55);
            if (GameMode2 != null) {
                g2d.drawString(GameModeStr, getWidth() / 100 * 82, getHeight() / 100 * 60);
            }

            //numbre de tour  鍥炲悎鏁?
            String nbtour = "Le numbre de tour est " + j.numberOfRounds;
            g2d.setColor(Color.blue);
            g2d.setFont(new

                    Font(dfonts[3], Font.BOLD, 15));
            g2d.drawString(nbtour,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 65);
            //playernow  鍝釜鐜╁杩涜鎿嶄綔
            g2d.setColor(Color.yellow);
            g2d.setFont(new

                    Font(dfonts[4], Font.BOLD, 15));
            g2d.drawString("C'est le tour de joueur " + j.getPlayerNow() + 1,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 70);
            //playercard ou takecard璇ュ嚭鐗岃繕鏄嬁鐗?
            g2d.setColor(Color.green);
            g2d.setFont(new

                    Font(dfonts[5], Font.BOLD, 15));
            if (j.TurnProcess < 3) {
                g2d.drawString("Veuillez montrer vos cartes", getWidth() / 100 * 82, getHeight() / 100 * 75);
            } else g2d.drawString("Veuillez prendre une carte",

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 75);
            //鏄剧ず鐜╁1寰楀垎
            g2d.setColor(Color.PINK);
            g2d.setFont(new

                    Font(dfonts[8], Font.BOLD, 15));
            g2d.drawString("Player 01 total score est " + j.Player1totalScore,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 80);
            //鏄剧ず鐜╁2寰楀垎
            g2d.setColor(Color.cyan);
            g2d.setFont(new

                    Font(dfonts[7], Font.BOLD, 15));
            g2d.drawString("Player 02 total score est " + j.Player2totalScore,

                    getWidth() / 100 * 82,

                    getHeight() / 100 * 85);


            g2d.dispose();
        }

        public void estFINI(Jeu j, Histoire h) {
            switch (j.GameMode) {
                case 1:
                    if (j.numberOfRounds == 27) {
                        if (j.Player1totalScore > j.Player2totalScore) {
                            wingamewindow(j, 2, 1);
                        } else {
                            wingamewindow(j, 2, 2);
                        }
                        h.cleanHistoire();
                    }
                    break;
                case 2:
                    if (j.numberOfRounds == 27) {
                        if (j.Player1Score > j.Player2Score) {
                            wingamewindow(j, 1, 1);
                            j.Player1WinGame++;
                        } else {
                            wingamewindow(j, 1, 2);
                            j.Player2WinGame++;
                        }
                        if (j.Player1WinGame == 2) {
                            wingamewindow(j, 2, 1);
                        } else if (j.Player2WinGame == 2) {
                            wingamewindow(j, 2, 2);
                        }

                        gameStartencore(j, h);
                    }
                    break;
                case 3:
                    if (j.numberOfRounds == 27) {
                        j.Game_ind++;
                        if (j.Game_ind > j.GameInformation) {
                            if (j.Player1totalScore > j.Player2totalScore) {
                                wingamewindow(j, 2, 1);
                                System.out.println("Player 1 win!");
                            } else {
                                wingamewindow(j, 2, 2);
                                System.out.println("Player 2 win!");
                            }
                            h.cleanHistoire();
                        } else {
                            if (j.Player1Score > j.Player2Score) {
                                wingamewindow(j, 1, 1);
                            } else {
                                wingamewindow(j, 1, 2);
                            }
                        }
                        gameStartencore(j, h);
                    }
                    break;
                case 4:
                    if (j.numberOfRounds == 27) {
                        gameStartencore(j, h);
                    }
                    if (j.Player1totalScore >= j.GameInformation || j.Player2totalScore >= j.GameInformation) {
                        if (j.Player1totalScore > j.Player2totalScore) {
                            wingamewindow(j, 2, 1);
                        } else {
                            wingamewindow(j, 2, 2);
                        }
                        h.cleanHistoire();
                        exit(0);
                    }
                    break;

            }
        }

        public void gameStartencore(Jeu j, Histoire h) {
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
                Atout a = new Atout(j);
                a.determinerAtout();
                Jeu j0 = (Jeu) j.clone();
                h.ajouteListDeHistoire(j0);
                Jeu j100 = (Jeu) j.clone();
                h.ajouteListDeHistoire(j100);

            }
        }

        public void changejue(Jeu j) {
            this.j = j;
            repaint();
        }

        public void wingamewindow(Jeu j, int i, int winner) {
            if (i == 1) {
                String winmassage = "Joueur " + winner + "ganne cette rond, le jeu va continuer";
                JOptionPane.showMessageDialog(null, winmassage, "winer", JOptionPane.PLAIN_MESSAGE);
            } else {
                String winmassage = "Joueur " + winner + "ganne cette jeux, Vous volez jouer encore？";
                int res = JOptionPane.showConfirmDialog(null, winmassage, "win", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    //TODO how to close the window of game
                    Main window = new Main();
                    window.mainframe.setVisible(true);
                } else {
                    exit(0);
                }
            }

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
        if (j.numberOfGames == 0) j.numberOfGames = 1;//濡傛灉娓告垙鍒氬紑濮嬬殑璇?
        if (j.playerFirst == 2) {//濡傛灉鏈疆璇ュ紑濮嬬殑璇濓紝鍒ゆ柇鍝釜鐜╁鍏堝紑濮嬫父鎴忋??
            j.playerFirst = (j.numberOfGames - 1) % 2;
            j.numberOfRounds = 1;
            //杩涜鍙戠墝浠ュ強鐗屽爢鐨勫疄鐜?
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