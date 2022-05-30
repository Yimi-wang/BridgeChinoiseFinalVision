package Vue;


import Controleur.SaveLoadVue;
import Modele.*;
import global.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class InterfaceIALoad implements Runnable {

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

    //鏋勯?犲嚱鏁?
    public InterfaceIALoad(Histoire h1) {
        this.h=h1;
    }

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        SwingUtilities.invokeLater(new InterfaceIA());
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
        j = h.listDeHistoire.get(h.listDeHistoire.size() - 1);
        //gameprocessvue.gameMode(j);锛堝惎鍔ㄦ父鎴忥級

        //鐣岄潰
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //end
        frame.setPreferredSize(new Dimension(1200,800));

        GamePaneIALoad cardinterface = new GamePaneIALoad(j, this, frame,h);

        cardinterface.setLayout(null);

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
        JMenu quitterMenu = new JMenu("Quit");
        menuBar.add(saveMenu);
        menuBar.add(backMenu);
        menuBar.add(surrenderMenu);
        menuBar.add(showcardMenu);
        menuBar.add(helpMenu);
        menuBar.add(quitterMenu);
        /**
         * Save主菜单和监听以及对于方法
         */
        //Save菜单子菜单
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

                JLabel saveinfoLabel = new JLabel("Saissisez le nom du fichier à sauvgarder");

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

                JLabel saveinfoLabel = new JLabel("Saissisez le nom du fichier à sauvgarder");

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
                int pn = j.playerNow;
                j=h.returnHistoire();
                while(j.playerNow!=pn)
                    j=h.returnHistoire();
                frame.dispose();
                InterfaceIALoad ijl = new InterfaceIALoad(h);
                ijl.run();
            }

        });
        backMenuItem02.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                j=h.returnHistoire();
                while(j.numberOfRounds!=1||j.TurnProcess!=1)
                    j=h.returnHistoire();
                frame.dispose();
                InterfaceIALoad ijl = new InterfaceIALoad(h);
                ijl.run();

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
                JOptionPane.showMessageDialog(null, "Comment jouer: \n" +
                        "        Jouer la carte : Cliquez sur la carte puis cliquez sur jouer pour jouer la carte.\n" +
                        "        Piocher une carte : Cliquez sur le paquet pour prendre une carte\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Fonction barre de menus\n" +
                        "        Sauvegarder et continuer : Entrez le nom à sauvegarder, le jeu continue\n" +
                        "        Sauvegarder et quitter : Saisissez le nom à sauvegarder, le jeu est terminée\n" +
                        "        Retour à votre tour : revenez au tour précédent (si c'était votre tour de jeu, revenez à votre dernier tour de pioche)\n" +
                        "\n" +
                        "        Redémarrer : redémarrer le jeu\n" +
                        "\n" +
                        "        Abandonnez ce jeu : Abandonnez ce jeu et le joueur adverse obtiendra des points pour les tours restants.\n" +
                        "        Abandonnez tout le jeu : Abandonnez ce jeu. L'adversaire gagne\n" +
                        "\n" +
                        "        Showcard : Montrez la carte de l'adversaire", "help", JOptionPane.QUESTION_MESSAGE);
            }
        });
        helpMenuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Jeu de 52 cartes à deux joueurs.\n" +
                        "Le but du jeu est de faire le maximum de plis.\n" +
                        "L’atout est défini au début de la partie, si il y a une cartes de valeur > ou = à 10 sur les 6 tas de cartes, sinon la partie se joue sans atout.\n" +
                        "Il faut respecter la hiérarchie des couleur (Pique, C?ur, Carreau, Trèfle).\n" +
                        "Il doit fournir de la couleur quand c’est possible sinon on coupe avec l’atout ou on se défausse.\n", "regle", JOptionPane.QUESTION_MESSAGE);
            }
        });
        JMenuItem quitMenuItem01 = new JMenuItem("Quitter le Jeu");
        JMenuItem quitMenuItem02 = new JMenuItem("Return to Menu");
        quitterMenu.add(quitMenuItem01);
        quitterMenu.add(quitMenuItem02);
        //监听和方法
        quitMenuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }

        });
        quitMenuItem02.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.backmenu();

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