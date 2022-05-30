package Vue;

import Controleur.IASimpleVue;
import Controleur.PlayCardsVue;
import Controleur.TakeCardVue;
import Modele.*;
import global.ConfigurationSetting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class GamePane extends JPanel {
    InterfaceJeu ifjgp;
    JFrame fgp;
    JFrame frame;
    Jeu j;
    JButton player0play, player1play, player0Suggest, player1Suggest;
    PlayCardsVue playcard;
    TakeCardVue takecard;
    Brand player1playercard;
    Brand player2playercard;
    private Map<Brand, Rectangle> mapCards;

    public GamePane(Jeu j, InterfaceJeu ifj, JFrame f) {
        //鐐瑰嚮榧犳爣锛屽鏋滅偣鍑绘槸鍗＄殑浣嶇疆锛屽垯浼氬皢鍗″悜涓婄Щ锛屼笉鏄殑璇濆凡缁忕Щ鍔ㄨ繃鐨勫崱鍥為??鍘熸潵鐨勪綅缃級

        this.j = j;
        this.ifjgp = ifj;
        this.fgp = f;

        playcard = new PlayCardsVue(j, ifjgp.h);
        takecard = new TakeCardVue(j, ifjgp.h);

        mapCards = new HashMap<>(j.playercard[0].size());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ifjgp.selected != null) {
                    Rectangle bounds = mapCards.get(ifjgp.selected);
                    if (ifjgp.selected.getPlace() == 0)
                        mapCards.get(ifjgp.selected).y += 30;
                    else if (ifjgp.selected.getPlace() == 1)
                        mapCards.get(ifjgp.selected).y -= 30;
                    repaint();
                }
                ifjgp.selected = null;
                if (j.TurnProcess == 1) {
                    if (j.playerFirst == 0) {
                        for (Brand card : j.playercard[0]) {
                            //Rectangle bounds = mapCards.get(card);
                            if (mapCards.get(card).contains(e.getPoint())) {
                                ifjgp.selected = card;
                                mapCards.get(card).y -= 30;
                                //鎵撳嵃鐜╁鎵嬬墝
                                //player0play.setText("PLAY");
                                System.out.println(card.toString());
                                repaint();
                                break;
                            }
                        }
                    } else {
                        for (Brand card : j.playercard[1]) {
                            Rectangle bounds = mapCards.get(card);
                            if (bounds.contains(e.getPoint())) {
                                ifjgp.selected = card;
                                bounds.y += 30;
                                //鎵撳嵃鐜╁鎵嬬墝
                                //player1play.setText("PLAY");
                                System.out.println(card.toString());
                                //repaint();
                                break;
                            }

                        }
                    }
                } else if (j.TurnProcess == 2) {
                    for (Brand card : j.playercard[0]) {
                        if (playcard.limite(j, card)) {
                            Rectangle bounds = mapCards.get(card);
                            if (bounds.contains(e.getPoint())) {
                                ifjgp.selected = card;
                                bounds.y -= 30;
                                //鎵撳嵃鐜╁鎵嬬墝
                                //player0play.setText("PLAY");
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
                                ifjgp.selected = card;
                                bounds.y += 30;
                                //鎵撳嵃鐜╁鎵嬬墝
                                //player1play.setText("PLAY");
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
                                if (j.TurnProcess == 3) takecard.playerWinTakeCard(j, card);
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

        player0play = new JButton("Play");
        player0play.setBounds(getWidth() / 3, getHeight() / 20 * 15, getWidth() / 15, getHeight() / 20);
        this.add(player0play);
        player0Suggest = new JButton("Hint");
        player0Suggest.setBounds(getWidth() / 100 * 45, getHeight() / 20 * 15, getWidth() / 15, getHeight() / 20);
        this.add(player0Suggest);

        player1play = new JButton("Play");
        player1play.setBounds(getWidth() / 3, getHeight() / 20 * 4, getWidth() / 15, getHeight() / 20);
        this.add(player1play);
        player1Suggest = new JButton("Hint");
        player1Suggest.setBounds(getWidth() / 100 * 45, getHeight() / 20 * 4, getWidth() / 15, getHeight() / 20);
        this.add(player1Suggest);

        player0play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ifjgp.selected != null) {
                    if (j.playerNow == 0) {
                        System.out.println("playcard");
                        player1playercard = ifjgp.selected;
                        if (j.playerNow == j.playerFirst) playcard.playerFirstPlayCard(j, ifjgp.selected);
                        else {
                            playcard.playerSecondePlayCard(j, ifjgp.selected);
                            estFINI(j, ifjgp.h);
                        }
                        drawCHANGFANGXING(j);
                        //TODO Can't make the button disappear
                        repaint();
                        ifjgp.selected = null;
                    }
                } else {
                    System.out.println("Please choose your card.");
                }
            }
        });

        player0Suggest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (j.playerNow == 0) {
                    Rectangle bounds;
                    if (ifjgp.selected != null) {
                        bounds = mapCards.get(ifjgp.selected);
                        bounds.y += 30;
                    }
                    System.out.println("playcard");
                    IASimpleVue ia = new IASimpleVue(j);
                    int index = ia.IASimplePlayerCard(0);
                    ifjgp.selected = j.playercard[0].get(index);
                    bounds = mapCards.get(ifjgp.selected);
                    bounds.y -= 30;
                    repaint();
                    //player0play.setText("PLAY");
                }
            }
        });

        player1play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ifjgp.selected != null) {
                    if (j.playerNow == 1) {
                        System.out.println("playcard");
                        player2playercard = ifjgp.selected;
                        if (j.playerNow == j.playerFirst) playcard.playerFirstPlayCard(j, ifjgp.selected);
                        else {
                            playcard.playerSecondePlayCard(j, ifjgp.selected);
                            estFINI(j, ifjgp.h);
                        }
                        drawCHANGFANGXING(j);
                        repaint();
                        ifjgp.selected = null;
                    }
                }
            }
        });

        player1Suggest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (j.playerNow == 1) {
                    Rectangle bounds;
                    if (ifjgp.selected != null) {
                        bounds = mapCards.get(ifjgp.selected);
                        bounds.y -= 30;
                    }
                    System.out.println("playcard");
                    IASimpleVue ia = new IASimpleVue(j);
                    int index = ia.IASimplePlayerCard(1);
                    ifjgp.selected = j.playercard[1].get(index);
                    bounds = mapCards.get(ifjgp.selected);
                    bounds.y += 30;
                    repaint();
                    //player1play.setText("PLAY");
                }
            }
        });

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
        int yPos = getHeight() / 100 * 85;
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
        //背景
        String background = ConfigurationSetting.instance().lis("background");
        int backgroundi = Integer.parseInt(background);
        backgroundi++;
        BufferedImage imageBackGround;
        File ImageBackGroundFile;
        ImageBackGroundFile = new File("./res/images/background ("+backgroundi+").png");
        try {
            imageBackGround = ImageIO.read(ImageBackGroundFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.drawImage(imageBackGround, 0, 0, getWidth() / 5 * 4, getWidth(), null);


        //右边背景

        BufferedImage imageBackGroundRight;
        File ImageBackGroundRightFile;
        String rightback= ConfigurationSetting.instance().lis("backright");
        int rightbacki =Integer.parseInt(rightback);
        rightbacki++;
        ImageBackGroundRightFile = new File("./res/images/backright ("+rightbacki+").png");
        try {
            imageBackGroundRight = ImageIO.read(ImageBackGroundRightFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.drawImage(imageBackGroundRight, getWidth() / 5 * 4, 0, getWidth(), getWidth(), null);


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
                    ifjgp.a1 = 1;
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    g2d.drawImage(imageCard, getWidth() / 3, getHeight() / 20 * 12, cardWidth, cardHeight, null);
                } else {
                    ifjgp.a1 = 0;
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
                if (ifjgp.showcard || j.playerNow == 1) {
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
                    File imgFile = new File("./res/images/back (1).png");
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
        File imgFileT = null;
        BufferedImage imageTurn;
        switch (j.TurnProcess) {
            case 1:
                if (j.playerNow == 0) imgFileT = new File("./res/images/j1j-modified.png");
                else imgFileT = new File("./res/images/j2j-modified.png");
                break;
            case 2:
                if (j.playerNow == 0) imgFileT = new File("./res/images/j1j-modified.png");
                else imgFileT = new File("./res/images/j2j-modified.png");
                break;
            case 3:
                if (j.playerNow == 0) imgFileT = new File("./res/images/j1p-modified.png");
                else imgFileT = new File("./res/images/j2p-modified.png");
                break;
            case 4:
                if (j.playerNow == 0) imgFileT = new File("./res/images/j1p-modified.png");
                else imgFileT = new File("./res/images/j2p-modified.png");
                break;
        }
        try {
            imageTurn = ImageIO.read(imgFileT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.drawImage(imageTurn, getWidth() / 100 * 70, getHeight() / 100 * 45, getWidth() / 100 * 10, getWidth() / 100 * 10, null);


        for (Brand card : j.playercard[0]) {
            Rectangle bounds = mapCards.get(card);
            //System.out.println(bounds);
            //鏍规嵁闀挎柟褰㈢殑浣嶇疆锛屽～鍏呭浘鐗?
            if (bounds != null) {
                BufferedImage imageCard;
                if (ifjgp.showcard || j.playerNow == 0) {
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
                    File imgFile = new File("./res/images/back (1).png");
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
                        imgFile = new File("./res/images/back (1).png");
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
        g2d.setFont(new Font(dfonts[1], Font.BOLD, 30));
        g2d.drawString("Atout", getWidth() / 100 * 85, getHeight() / 15);
        //BRAND 鍥剧墖
        BufferedImage imageCardAtout;
        File ImageAtoutFile;
        if (j.avoiratout) {
            ImageAtoutFile = new File("./res/images/Atout" + j.atout.getInttype() + ".png");
        } else {
            ImageAtoutFile = new File("./res/images/back (1).png");
        }
        try {
            imageCardAtout = ImageIO.read(ImageAtoutFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.drawImage(imageCardAtout, getWidth() / 100 * 82, getHeight() / 100 * 8, getWidth() / 100 * 15, (getWidth() / 100 * 15) / 6 * 10, null);

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
        g2d.setFont(new Font(dfonts[2], Font.BOLD, 15));
        g2d.drawString(GameModeStr, getWidth() / 100 * 82, getHeight() / 100 * 55);
        if (GameMode2 != null) {
            g2d.drawString(GameMode2, getWidth() / 100 * 82, getHeight() / 100 * 60);
        }

        //numbre de tour  鍥炲悎鏁?
        String nbtour = "Le numbre de tour est " + j.numberOfRounds;
        g2d.setColor(Color.blue);
        g2d.setFont(new Font(dfonts[3], Font.BOLD, 15));
        g2d.drawString(nbtour, getWidth() / 100 * 82, getHeight() / 100 * 65);
        //playernow  鍝釜鐜╁杩涜鎿嶄綔
        g2d.setColor(Color.yellow);
        g2d.setFont(new Font(dfonts[4], Font.BOLD, 15));
        g2d.drawString("C'est le tour de joueur " + j.getPlayerNow() + 1, getWidth() / 100 * 82, getHeight() / 100 * 70);
        //playercard ou takecard璇ュ嚭鐗岃繕鏄嬁鐗?
        g2d.setColor(Color.green);
        g2d.setFont(new Font(dfonts[5], Font.BOLD, 15));
        if (j.TurnProcess < 3) {
            g2d.drawString("Veuillez montrer vos cartes", getWidth() / 100 * 82, getHeight() / 100 * 75);
        } else g2d.drawString("Veuillez prendre une carte", getWidth() / 100 * 82, getHeight() / 100 * 75);
        //鏄剧ず鐜╁1寰楀垎
        g2d.setColor(Color.PINK);
        g2d.setFont(new Font(dfonts[8], Font.BOLD, 15));
        g2d.drawString("Player 01 total score est " + j.Player1totalScore, getWidth() / 100 * 82, getHeight() / 100 * 80);
        //鏄剧ず鐜╁2寰楀垎
        g2d.setColor(Color.cyan);
        g2d.setFont(new Font(dfonts[7], Font.BOLD, 15));
        g2d.drawString("Player 02 total score est " + j.Player2totalScore, getWidth() / 100 * 82, getHeight() / 100 * 85);

        //player1 score
        g2d.setColor(Color.blue);
        g2d.setFont(new Font(dfonts[10], Font.ITALIC, 15));
        g2d.drawString("Player 1 score est ", getWidth() / 100 * 70, getHeight() / 100 * 85);
        g2d.setFont(new Font(dfonts[10], Font.BOLD, 50));
        g2d.drawString(String.valueOf(j.Player1Score), getWidth() / 100 * 72, getHeight() / 100 * 92);
        //player2 score
        g2d.setColor(Color.red);
        g2d.setFont(new Font(dfonts[10], Font.ITALIC, 15));
        g2d.drawString("Player 2 score est ", getWidth() / 100 * 70, getHeight() / 100 * 5);
        g2d.setFont(new Font(dfonts[10], Font.BOLD, 50));
        g2d.drawString(String.valueOf(j.Player2Score), getWidth() / 100 * 72, getHeight() / 100 * 12);

        //双发拿的手卡
        if (j.numberOfRounds <= 16) {
            if (!(j.numberOfRounds == 1 && j.TurnProcess <= 3)) {

                if (j.TurnProcess == 4) {
                    BufferedImage imageCard;
                    File imgFilecard = null;
                    if (j.playerNow == 0) {
                        imgFilecard = new File("./res/images/card (" + j.player2takecard.id + ").png");
                    } else {
                        imgFilecard = new File("./res/images/card (" + j.player1takecard.id + ").png");
                    }
                    try {
                        imageCard = ImageIO.read(imgFilecard);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    g2d.setFont(new Font(dfonts[10], Font.BOLD, 10));
                    if (j.playerNow == 1) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawString("Joueur 1 pioche", getWidth() / 100 * 5, getHeight() / 20 * 13);
                        g2d.drawImage(imageCard, getWidth() / 100 * 5, getHeight() / 20 * 14, cardWidth, cardHeight, null);
                    } else {

                        g2d.setColor(Color.RED);
                        g2d.drawString("Joueur 2 pioche", getWidth() / 100 * 5, getHeight() / 100 * 38);
                        g2d.drawImage(imageCard, getWidth() / 100 * 5, getHeight() / 100 * 20, cardWidth, cardHeight, null);
                    }


                } else if (j.TurnProcess == 3) {

                } else {
                    BufferedImage imageCard;
                    BufferedImage imageCard2;
                    File imgFilecard = new File("./res/images/card (" + j.player2takecard.id + ").png");
                    File imgFilecard2 = new File("./res/images/card (" + j.player1takecard.id + ").png");
                    try {
                        imageCard = ImageIO.read(imgFilecard);
                        imageCard2 = ImageIO.read(imgFilecard2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int cardHeight = (getHeight() - 20) / 8;
                    int cardWidth = (int) (cardHeight * 0.6);
                    g2d.setFont(new Font(dfonts[10], Font.BOLD, 10));

                    g2d.setColor(Color.BLUE);
                    g2d.drawString("Joueur 1 pioche", getWidth() / 100 * 5, getHeight() / 20 * 13);
                    g2d.drawImage(imageCard2, getWidth() / 100 * 5, getHeight() / 20 * 14, cardWidth, cardHeight, null);

                    g2d.setColor(Color.RED);
                    g2d.drawString("Joueur 2 pioche", getWidth() / 100 * 5, getHeight() / 100 * 38);
                    g2d.drawImage(imageCard, getWidth() / 100 * 5, getHeight() / 100 * 20, cardWidth, cardHeight, null);
                }
            }
        }


        g2d.dispose();
    }

    public void changejue(Jeu j) {
        this.j = j;
        repaint();
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
            j.playerNow = j.playerFirst;
            //进行发牌以及牌堆的实现
            if (j.numberOfGames != 1) {
                StartHand startHand = new StartHand(j);
                startHand.stardHand();
            }
            drawCHANGFANGXING(j);
            Atout a = new Atout(j);
            a.determinerAtout();
            Jeu j0 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j0);
            Jeu j100 = (Jeu) j.clone();
            h.ajouteListDeHistoire(j100);

        }
    }

    public void surrenderthisgame() {
        if (j.playerNow == 0) {
            j.Player2totalScore += (26 - j.numberOfRounds + 1);
        } else {
            j.Player1totalScore += (26 - j.numberOfRounds + 1);
        }
        j.numberOfRounds = 27;
        estFINI(j, ifjgp.h);

    }

    public void wingamewindow(Jeu j, int i, int winner) {
        if (i == 1) {
            String winmassage = "Joueur " + winner + "ganne cette rond, le jeu va continuer";
            JOptionPane.showMessageDialog(null, winmassage, "winer", JOptionPane.PLAIN_MESSAGE);
        } else {
            String winmassage = "Joueur " + winner + "ganne cette jeux, Vous volez jouer encore？";
            int res = JOptionPane.showConfirmDialog(null, winmassage, "win", JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                //TODO how to close the window of game 如何回到主菜单
                fgp.dispose();
                Main window = new Main();
                window.mainframe.setVisible(true);
            } else {
                exit(0);
            }
        }

    }

}