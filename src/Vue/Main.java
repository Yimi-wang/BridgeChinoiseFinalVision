package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.IOException;


public class Main extends JFrame implements ActionListener {
    JFrame mainframe;

    Container mainContentPanel;

    JButton btnStartButton;
    JButton btnLoadButton;
    JButton btnSettingButton;
    JButton btnOnlineButton;
    //�ı����밴ť
    JTextField jta;
    //gameMode
    JButton GameModeQuestionButton; // ?��ť
    JButton GameModeLeftButton;//���ͷ��ť
    JButton GameModeRightButton;//�Ҽ�ͷ��ť
    //JScrollPane GameModeInputJSP;//�ı������λ����������
    JLabel lblGameMode;//gamemode��ǩ
    JLabel lblGameModeTest;//�·�����ʾ������
    JComboBox GameModeComboBox;//��ѡ��


    //AIMode
    JButton AIModeQuestionButton; // ?��ť
    JButton AIModeLeftButton;//���ͷ��ť
    JButton AIModeRightButton;//�Ҽ�ͷ��ť
    JLabel lblAIMode;//gamemode��ǩ
    JComboBox AIModeComboBox;//��ѡ��

    //�û�����
    JFrame settingJframe;

    Container settingContentPanel;

    JLabel settingJLabel;

    JLabel settingDesktoplbl;

    JLabel settingCardBack;

    JLabel settingShowCard;



    JRadioButton backradioBtn1;

    JRadioButton backradioBtn2;

    JRadioButton backradioBtn3;

    JRadioButton cardRadioBtn1;

    JRadioButton cardRadioBtn2;

    JRadioButton cardRadioBtn3;

    JRadioButton cardShowBtn1;

    JRadioButton cardShowBtn2;

    JButton settingGetBackBtn;

    JButton settingSaveBackBtn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Main window = new Main();
        window.mainframe.setVisible(true);
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
        addEventListener();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //������
        String[] dfonts;
        dfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // ��ʼ������
        mainframe = new JFrame("Bcvue game");
        mainframe.setSize(720, 506);
        mainframe.setLocationRelativeTo(null);
        mainframe.getContentPane().setLayout(null);
        mainframe.setResizable(false);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel impanel = (JPanel) mainframe.getContentPane();
        impanel.setOpaque(false);


        ImageIcon logo = new ImageIcon("res/images/Logo.png");
        JLabel logolabel = new JLabel(logo);
        logolabel.setBounds(30, 20, 300, 100);
        logo.setImage(logo.getImage().getScaledInstance(logolabel.getWidth(), logolabel.getHeight(), Image.SCALE_DEFAULT));
        mainframe.getLayeredPane().add(logolabel, Integer.valueOf(Integer.MIN_VALUE));


        //��ʾ����
        ImageIcon background = new ImageIcon("res/images/bg_menu.png");
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, mainframe.getWidth(), mainframe.getHeight());
        background.setImage(background.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        mainframe.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));

//        // ��Ϸ�ı�����
//        JLabel jLabel = new JLabel("Bridge Chinois");
//        jLabel.setBounds(30, 25, 300, 30);
//        Font f = new Font("����",Font.PLAIN,30);
//        jLabel.setFont(f);
//        Color fg = new Color(255,255,255);
//        jLabel.setForeground(fg);
//        mainContentPanel.add(jLabel);


        mainContentPanel = mainframe.getContentPane();
        mainContentPanel.setLayout(null);
        // start��ť
        //���ر���ͼƬ
        String startPath = "./res/images/START.png";
        ImageIcon startIcon1 = new ImageIcon(startPath);
        Image startImg = startIcon1.getImage();
        Image startNewimg = startImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon startIcon2 = new ImageIcon(startNewimg);
        btnStartButton = new RoundRectButton(startIcon2);
        btnStartButton.setBounds(90, 140, 120, 40);
        btnStartButton.setBorder(new RoundBorder(Color.WHITE));
        btnStartButton.setBackground(Color.RED);
        mainContentPanel.add(btnStartButton);


        // online
        //���ر���ͼƬ
        String onlinePath = "./res/images/ONLINE.png";
        ImageIcon onlineIcon1 = new ImageIcon(onlinePath);
        Image onlineImg = onlineIcon1.getImage();
        Image onlineNewImg = onlineImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon onlineIcon2 = new ImageIcon(onlineNewImg);

        btnOnlineButton = new RoundRectButton(onlineIcon2);
        btnOnlineButton.setBounds(90, 220, 120, 40);
        mainContentPanel.add(btnOnlineButton);


        // load
        //���ر���ͼƬ
        String loadPath = "./res/images/LOAD.png";
        ImageIcon loadIcon1 = new ImageIcon(loadPath);
        Image loadImg = loadIcon1.getImage();
        Image loadNewImg = loadImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon loadIcon2 = new ImageIcon(loadNewImg);

        btnLoadButton = new RoundRectButton(loadIcon2);
        btnLoadButton.setBounds(90, 300, 120, 40);
        mainContentPanel.add(btnLoadButton);


        // setting
        //���ر���ͼƬ
        String settingPath = "./res/images/SETTING.png";
        ImageIcon settingIcon1 = new ImageIcon(settingPath);
        Image settingImg = settingIcon1.getImage();
        Image settingNewImg = settingImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon settingIcon2 = new ImageIcon(settingNewImg);

        btnSettingButton = new RoundRectButton(settingIcon2);
        btnSettingButton.setBounds(90, 380, 120, 40);
        mainContentPanel.add(btnSettingButton);

//        //���gameMode
//        JLabel gameModeText = new JLabel("GameMode");
//        gameModePanel.add(gameModeText);
//        mainContentPanel.add(gameModePanel);
        /**
         * start gamemode ����
         */
        //����
        lblGameMode = new JLabel("GameMode");
        lblGameMode.setBounds(200, 150, 300, 30);
        Font f1 = new Font("����", Font.PLAIN, 30);
        lblGameMode.setFont(f1);
        Color fg1 = new Color(255, 255, 255);
        lblGameMode.setForeground(fg1);
        mainContentPanel.add(lblGameMode);

        GameModeComboBox = new JComboBox();
        // ��������ѡ��
        String[] strArray = {"BO1", "BO3", "Number of Fixe", "Score Fixe"};
        for (String item : strArray) {
            GameModeComboBox.addItem(item);
        }
        GameModeComboBox.setFont(new Font("����", Font.PLAIN, 14));
        GameModeComboBox.setBounds(150, 200, 180, 30);
        mainContentPanel.add(GameModeComboBox);

        //����ʺŰ�ť
        GameModeQuestionButton = new JButton("?");
        GameModeQuestionButton.setBounds(340, 200, 50, 30);
        mainContentPanel.add(GameModeQuestionButton);

        //�·���������ʾ
        lblGameModeTest = new JLabel("<html><body>Donner le nombre de Jue<br>(Si vous choisi Numbre  Fixe)<br>ou le nombre de Score<br>(Si vous choisi Score Fixe</body></html>");
        lblGameModeTest.setBounds(150, 150, 300, 300);
        Font f22 = new Font("����", Font.PLAIN, 15);
        lblGameModeTest.setFont(f22);
        Color fg22 = new Color(27, 179, 205);
        lblGameModeTest.setForeground(fg22);
        mainContentPanel.add(lblGameModeTest);
        //����ı���
        jta = new JTextField();
        jta.setForeground(Color.BLACK);    //��������ı���ɫ
        jta.setFont(new Font(dfonts[15], Font.ROMAN_BASELINE, 10));    //�޸�������ʽ
        jta.setBounds(150, 350, 200, 30);
        mainContentPanel.add(jta);

        //������Ұ�ť
        GameModeLeftButton = new JButton("��");
        GameModeLeftButton.setBounds(150, 400, 50, 30);
        mainContentPanel.add(GameModeLeftButton);
        GameModeRightButton = new JButton("��");
        GameModeRightButton.setBounds(340, 400, 50, 30);
        mainContentPanel.add(GameModeRightButton);

        /**
         * AImode
         */
        // ���AIMode**********************
        lblAIMode = new JLabel("AIMode");
        lblAIMode.setBounds(200, 150, 300, 30);
        Font f2 = new Font("����", Font.PLAIN, 30);
        lblAIMode.setFont(f2);
        Color fg2 = new Color(255, 255, 255);
        lblAIMode.setForeground(fg2);
        mainContentPanel.add(lblAIMode);

        AIModeComboBox = new JComboBox();
        // ��������ѡ��
        String[] AIStrArray = {"AIrandom", "AIsimple", "AIminmax", "AIrandom"};
        for (String item : AIStrArray) {
            AIModeComboBox.addItem(item);
        }
        AIModeComboBox.setFont(new Font("����", Font.PLAIN, 14));
        AIModeComboBox.setBounds(150, 200, 180, 30);
        mainContentPanel.add(AIModeComboBox);

        //����ʺŰ�ť
        //AIModeQuestionButton = new JButton("?");
        AIModeQuestionButton = new JButton();
        ImageIcon Help = new ImageIcon("bridgechinoise-New Version/bridgechinoise/res/images/HELP.png");
        AIModeQuestionButton.setIcon(Help);
        AIModeQuestionButton.setBounds(340, 200, 50, 30);
        mainContentPanel.add(AIModeQuestionButton);


        //������Ұ�ť
        AIModeLeftButton = new JButton("��");
        AIModeLeftButton.setBounds(150, 400, 50, 30);
        mainContentPanel.add(AIModeLeftButton);
        AIModeRightButton = new JButton("��");
        AIModeRightButton.setBounds(340, 400, 50, 30);
        mainContentPanel.add(AIModeRightButton);

        gameModeDisappear();
        AIModeDisappear();

    }

    //gameMode��ʧ
    public void gameModeDisappear() {
        lblGameMode.setVisible(false);
        GameModeComboBox.setVisible(false);
        GameModeQuestionButton.setVisible(false);
        jta.setVisible(false);
        lblGameModeTest.setVisible(false);
        GameModeLeftButton.setVisible(false);
        GameModeRightButton.setVisible(false);
    }

    //gameMode����
    public void gameModeAppear() {
        lblGameMode.setVisible(true);
        GameModeComboBox.setVisible(true);
        GameModeQuestionButton.setVisible(true);
        jta.setVisible(true);
        lblGameModeTest.setVisible(true);
        GameModeLeftButton.setVisible(true);
        GameModeRightButton.setVisible(true);
    }


    public void AIModeDisappear() {
        lblAIMode.setVisible(false);
        AIModeComboBox.setVisible(false);
        AIModeQuestionButton.setVisible(false);
        AIModeLeftButton.setVisible(false);
        AIModeRightButton.setVisible(false);
    }

    public void AIModeAppear() {
        lblAIMode.setVisible(true);
        AIModeComboBox.setVisible(true);
        AIModeQuestionButton.setVisible(true);
        AIModeLeftButton.setVisible(true);
        AIModeRightButton.setVisible(true);
    }


    /**
     * �����水ť��ʧ
     */
    public void mainFrameButtonDisappear() {
        btnStartButton.setVisible(false);
        btnLoadButton.setVisible(false);
        btnSettingButton.setVisible(false);
        btnOnlineButton.setVisible(false);
    }


    /**
     * �����水ť����
     */
    public void mainFrameButtonAppear() {
        btnStartButton.setVisible(true);
        btnLoadButton.setVisible(true);
        btnSettingButton.setVisible(true);
        btnOnlineButton.setVisible(true);

    }

    public void addEventListener() {

        //�����水ť
        btnStartButton.addActionListener(this);
        btnLoadButton.addActionListener(this);
        btnOnlineButton.addActionListener(this);
        btnSettingButton.addActionListener(this);

        //gameMode��ť
        GameModeQuestionButton.addActionListener(this);
        GameModeLeftButton.addActionListener(this);
        GameModeRightButton.addActionListener(this);

        //AIMode��ť
        AIModeLeftButton.addActionListener(this);
        AIModeRightButton.addActionListener(this);
        AIModeQuestionButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStartButton) {
            mainFrameButtonDisappear();
            gameModeAppear();
        }
        if (e.getSource() == GameModeLeftButton) {
            mainFrameButtonAppear();
            gameModeDisappear();
        }
        //��GameMode����
        if (e.getSource() == GameModeRightButton) {
            AIModeAppear();
            gameModeDisappear();
            int GameMode;
            try {
                FileWriter fw;
                fw = new FileWriter("./res/default.cfg", false);
                // Human vs Human
                GameMode = GameModeComboBox.getSelectedIndex() + 1;
                fw.write("GameMode=" + GameMode + "\n");
                String NB = jta.getText();
                if (GameMode > 2) {
                    fw.write("GameInformation=" + NB + "\n");
                    System.out.println("gameInformation est " + NB);
                }
                fw.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        if (e.getSource() == GameModeQuestionButton) {
            JOptionPane.showMessageDialog(null, "�����������Ϸģʽ�����ʽ��\r\n�������ʲô�����ģ�������ϵ����121212@gmail.com\r\n", "��ʾ", JOptionPane.QUESTION_MESSAGE);
        }
        if (e.getSource() == AIModeLeftButton) {
            AIModeDisappear();
            gameModeAppear();
        }
        if (e.getSource() == AIModeRightButton) {
            try {
                FileWriter fw;
                fw = new FileWriter("./res/default.cfg", true);
                // Human vs Human
                int IA = AIModeComboBox.getSelectedIndex() + 1;
                fw.write("AI=" + IA + "\n");
                fw.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
            mainframe.dispose();
            InterfaceJeu.start();
        }

        if (e.getSource() == AIModeQuestionButton) {
            JOptionPane.showMessageDialog(null, "�����������ϷAIģʽ�����ʽ��\r\n�������ʲô�����ģ�������ϵ����121212@gmail.com\r\n", "��ʾ", JOptionPane.QUESTION_MESSAGE);
        }
        if(e.getSource()==btnLoadButton){
            mainframe.dispose();
            //TODO Loadgame
            //use Jeu j =Controleur.SaveLoadVue(Dossier nom)
            //
        }
    }

    //setting Gui
    public void initSettingJFrame(){

        settingJframe = new JFrame("Bcvue game");
        settingJframe.setSize(600,600);
        settingJframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gameframe.setResizable(false);
        settingJframe.setLocationRelativeTo(getOwner()); // ��Ļ����
        settingContentPanel = settingJframe.getContentPane();


        settingGetBackBtn = new JButton("����");
        settingGetBackBtn.setBounds(130,500,60,30);
        settingContentPanel.add(settingGetBackBtn);


        settingSaveBackBtn = new JButton("����");
        settingSaveBackBtn.setBounds(370,500,60,30);
        settingContentPanel.add(settingSaveBackBtn);

        //��ʼ�����
        settingJLabel = new JLabel("Configuration");
        settingJLabel.setBounds(220,20,200,30);
        Font f1 = new Font("����",Font.PLAIN,18);
        settingJLabel.setFont(f1);
        settingContentPanel.add(settingJLabel);

        JLabel seprate0 = new JLabel("");
        seprate0.setBounds(0, 55, 10000, 1);
        seprate0.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        settingContentPanel.add(seprate0);

        settingDesktoplbl = new JLabel("Set desktop background:");
        settingDesktoplbl.setBounds(30,60,230,30);
        settingDesktoplbl.setFont(f1);
        settingContentPanel.add(settingDesktoplbl);

        backradioBtn1 = new JRadioButton("", true);
        backradioBtn1.setBounds(60,100,30,30);
        settingContentPanel.add(backradioBtn1);
        JLabel back1 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\back1.png", true));
        back1.setBounds(90,100,100,60);
        settingContentPanel.add(back1);

        backradioBtn2 = new JRadioButton("", false);
        backradioBtn2.setBounds(230,100,30,30);
        settingContentPanel.add(backradioBtn2);
        JLabel back2 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\back2.png", true));
        back2.setBounds(260,100,100,60);
        settingContentPanel.add(back2);

        backradioBtn3 = new JRadioButton("", false);
        backradioBtn3.setBounds(390,100,30,30);
        settingContentPanel.add(backradioBtn3);
        JLabel back3 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\back3.png", true));
        back3.setBounds(420,100,100,60);
        settingContentPanel.add(back3);
        ButtonGroup btnBackGroup = new ButtonGroup();
        btnBackGroup.add(backradioBtn1);
        btnBackGroup.add(backradioBtn2);
        btnBackGroup.add(backradioBtn3);

        //�ָ���
        JLabel seprate1 = new JLabel("");
        seprate1.setBounds(0, 180, 10000, 1);
        seprate1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        settingContentPanel.add(seprate1);


        settingCardBack = new JLabel("Set card background:");
        settingCardBack.setBounds(30,200,230,30);
        settingCardBack.setFont(f1);
        settingContentPanel.add(settingCardBack);
//
        cardRadioBtn1 = new JRadioButton("", true);
        cardRadioBtn1.setBounds(60,250,30,30);
        settingContentPanel.add(cardRadioBtn1);
        JLabel cardBack1 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\cardBack1.png", true));
        cardBack1.setBounds(90,250,60,90);
        settingContentPanel.add(cardBack1);

        cardRadioBtn2 = new JRadioButton("", false);
        cardRadioBtn2.setBounds(230,250,30,30);
        settingContentPanel.add(cardRadioBtn2);
        JLabel cardBack2 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\cardBack2.png", true));
        cardBack2.setBounds(260,250,60,90);
        settingContentPanel.add(cardBack2);

        cardRadioBtn3 = new JRadioButton("", false);
        cardRadioBtn3.setBounds(390,250,30,30);
        settingContentPanel.add(cardRadioBtn3);
        JLabel cardBack3 = new JLabel(SwingUtil.createAutoAdjustIcon("D:\\OutSourcing\\CardGame\\BCvue\\bridgechinoise-New Version\\bridgechinoise\\src\\images\\cardBack3.png", true));
        cardBack3.setBounds(420,250,60,90);
        settingContentPanel.add(cardBack3);
        ButtonGroup btnCardGroup = new ButtonGroup();
        btnCardGroup.add(cardRadioBtn1);
        btnCardGroup.add(cardRadioBtn2);
        btnCardGroup.add(cardRadioBtn3);


        //�ָ���2
        JLabel seprate2 = new JLabel("");
        seprate2.setBounds(0, 390, 10000, 1);
        seprate2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        settingContentPanel.add(seprate2);



        settingShowCard = new JLabel("Show card:");
        settingShowCard.setBounds(30,400,230,30);
        settingShowCard.setFont(f1);
        settingContentPanel.add(settingShowCard);


        cardShowBtn1 = new JRadioButton("Yes", true);
        Font f2 = new Font("����",Font.PLAIN,20);
        cardShowBtn1.setFont(f2);
        cardShowBtn1.setBounds(120,440,120,30);
        settingContentPanel.add(cardShowBtn1);


        cardShowBtn2 = new JRadioButton("No", false);
        cardShowBtn2.setFont(f2);
        cardShowBtn2.setBounds(300,440,120,30);
        settingContentPanel.add(cardShowBtn2);
        ButtonGroup btnShowGroup = new ButtonGroup();
        btnShowGroup.add(cardShowBtn1);
        btnShowGroup.add(cardShowBtn2);


        settingContentPanel.setLayout(null);

    }

    //���setting�������
    public void addSettingActionListener(){
        cardRadioBtn1.addActionListener(this);
        cardRadioBtn2.addActionListener(this);
        cardRadioBtn3.addActionListener(this);
        backradioBtn1.addActionListener(this);
        backradioBtn2.addActionListener(this);
        backradioBtn3.addActionListener(this);
        cardShowBtn1.addActionListener(this);
        cardShowBtn2.addActionListener(this);
        settingGetBackBtn.addActionListener(this);
        settingSaveBackBtn.addActionListener(this);

    }
}


