package Vue;


import Controleur.SaveLoadVue;
import Modele.*;
import global.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceJeu implements Runnable {

    public Brand selected;
    Jeu j;
    int player2;
    int player1;
    int IA;
    Atout a;
    Histoire h;
    JLabel Atout;
    int a1 = 0;

    boolean showcard;

    //构�?�函�?
    public InterfaceJeu() {
    }

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        SwingUtilities.invokeLater(new InterfaceJeu());
    }

    public void run() {
        //MusicTest m = new MusicTest();
        // m.play();
        //不知道用�?
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //ȷ��jue��ͷ���ֿ�
        j = creatJeu();
        gameStart(j, h);
        //gameprocessvue.gameMode(j);（启动游戏）

        //界面
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //end
        frame.setPreferredSize(new Dimension(1200,800));
        GamePane cardinterface = new GamePane(j, this, frame,h);
        cardinterface.setLayout(null);

        frame.setMinimumSize(new Dimension(1200, 800));
        /**
         * ��Ϸ�˵���ʵ��
         */

        //���˵�
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
         * Save���˵��ͼ����Լ����ڷ���
         */
        //Save�˵��Ӳ˵�
        JMenuItem saveMenuItem01 = new JMenuItem("Save and continue");
        JMenuItem saveMenuItem02 = new JMenuItem("Save and quit");
        saveMenu.add(saveMenuItem01);
        saveMenu.add(saveMenuItem02);
        saveMenuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                SaveLoadVue slv = new SaveLoadVue();

                JFrame saveframe = new JFrame("Sauvgarder !");
                saveframe.setSize(200, 120);
                /*
                 * ImageIcon savelogo = new ImageIcon("res/images/Logo.png"); JLabel
                 * savelogolabel = new JLabel(savelogo); savelogolabel.setBounds(30, 20, 300,
                 * 100); savelogo.setImage(savelogo.getImage().getScaledInstance(savelogolabel.
                 * getWidth(), savelogolabel.getHeight(), Image.SCALE_DEFAULT));
                 * saveframe.add(savelogolabel, BorderLayout.NORTH);
                 */


                JButton saveconfirm = new JButton("Sauvgarder");

                JButton savecancel = new JButton("Quitter");

                JLabel saveinfoLabel = new JLabel("Saissisez le nom du fichier �� sauvgarder");

                JTextField savenameField = new JTextField("defautSaveFile");

                Box savebuttonBox = Box.createHorizontalBox();
                savebuttonBox.add(saveconfirm);
                savebuttonBox.add(savecancel);

                Box savecontentBox = Box.createVerticalBox();
                savecontentBox.add(saveinfoLabel);
                savecontentBox.add(savenameField);

                saveconfirm.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        slv.Save(h, savenameField.getText());
                        saveframe.dispose();
                    }
                });
                savecancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveframe.dispose();
                    }
                });


                saveframe.setLayout(new BorderLayout(20, 20));
                saveframe.setLocationRelativeTo(null);

                saveframe.add(savecontentBox, BorderLayout.CENTER);
                saveframe.add(savebuttonBox, BorderLayout.SOUTH);

                saveframe.setVisible(true);

            }
        });
        saveMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                SaveLoadVue slv = new SaveLoadVue();

                JFrame saveframe = new JFrame("Sauvgarder !");
                saveframe.setSize(200, 120);
                /*
                 * ImageIcon savelogo = new ImageIcon("res/images/Logo.png"); JLabel
                 * savelogolabel = new JLabel(savelogo); savelogolabel.setBounds(30, 20, 300,
                 * 100); savelogo.setImage(savelogo.getImage().getScaledInstance(savelogolabel.
                 * getWidth(), savelogolabel.getHeight(), Image.SCALE_DEFAULT));
                 * saveframe.add(savelogolabel, BorderLayout.NORTH);
                 */


                JButton saveconfirm = new JButton("Sauvgarder");

                JButton savecancel = new JButton("Quitter");

                JLabel saveinfoLabel = new JLabel("Saissisez le nom du fichier �� sauvgarder");

                JTextField savenameField = new JTextField("defautSaveFile");

                Box savebuttonBox = Box.createHorizontalBox();
                savebuttonBox.add(saveconfirm);
                savebuttonBox.add(savecancel);

                Box savecontentBox = Box.createVerticalBox();
                savecontentBox.add(saveinfoLabel);
                savecontentBox.add(savenameField);

                saveconfirm.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        slv.Save(h, savenameField.getText());
                        saveframe.dispose();
                        frame.dispose();
                        Main.backmenu();
                    }
                });
                savecancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveframe.dispose();
                    }
                });


                saveframe.setLayout(new BorderLayout(20, 20));
                saveframe.setLocationRelativeTo(null);

                saveframe.add(savecontentBox, BorderLayout.CENTER);
                saveframe.add(savebuttonBox, BorderLayout.SOUTH);

                saveframe.setVisible(true);

            }
        });

        /**
         * Back���˵��ͼ����Ͷ�Ӧ����
         */
        //Back�˵��Ӳ˵�
        JMenuItem backMenuItem01 = new JMenuItem("Back to your trun");
        JMenuItem backMenuItem02 = new JMenuItem("Restart");
        backMenu.add(backMenuItem01);
        backMenu.add(backMenuItem02);
        //�����ͷ���
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
            public void actionPerformed(ActionEvent e) {
                //TODO �ֿ�û��ʵ��
                j.restart();
                cardinterface.repaint();
            }
        });
        /**
         * Surrender ���˵��ͼ����Ͷ�Ӧ����
         */
        //surrender�˵��Ӳ˵�
        JMenuItem surrenderMenuItem01 = new JMenuItem("Surrender this game");
        JMenuItem surrenderMenuItem02 = new JMenuItem("Surrender all game");
        surrenderMenu.add(surrenderMenuItem01);
        surrenderMenu.add(surrenderMenuItem02);
        //����
        surrenderMenuItem01.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                cardinterface.surrenderthisgame();
            }
        });
        surrenderMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardinterface.wingamewindow(j, 2, j.playerNow);
            }
        });
        /**
         * showcard���˵��ͼ����Ͷ�Ӧ����
         */
        //shoucard�˵��Ӳ˵�
        final JRadioButtonMenuItem showcardTrue = new JRadioButtonMenuItem("yes");
        final JRadioButtonMenuItem showcardFalse = new JRadioButtonMenuItem("no");
        showcardMenu.add(showcardTrue);
        showcardMenu.add(showcardFalse);
        // �������� ��ѡ��ť�Ӳ˵���Ҫʵ�ֵ�ѡ��ť��Ч������Ҫ�����Ƿŵ�һ����ť����
        ButtonGroup showcardgroup = new ButtonGroup();
        showcardgroup.add(showcardTrue);
        showcardgroup.add(showcardFalse);

        // Ĭ�ϵ�һ����ѡ��ť�Ӳ˵�ѡ��
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
         * help���˵��ͼ����Ͷ�Ӧ������
         */
        //help �˵��Ӳ˵�
        JMenuItem helpMenuItem01 = new JMenuItem("help");
        JMenuItem helpMenuItem02 = new JMenuItem("regle");
        helpMenu.add(helpMenuItem01);
        helpMenu.add(helpMenuItem02);
        //����
        helpMenuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("�½�  �����");
            }
        });
        helpMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("�½�  �����");
            }
        });

        //��ӵ�frame
        frame.setJMenuBar(menuBar);
        frame.add(cardinterface);

        //��С����
        frame.pack();
        //λ�þ���
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
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
        if (j.numberOfGames == 0) j.numberOfGames = 1;//如果游戏刚开始的�?
        if (j.playerFirst == 2) {//如果本轮该开始的话，判断哪个玩家先开始游戏�??
            j.playerFirst = (j.numberOfGames - 1) % 2;
            j.numberOfRounds = 1;
            //进行发牌以及牌堆的实�?
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