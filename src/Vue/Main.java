package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame implements ActionListener {
    JFrame mainframe;

    JFrame gameframe;

    Container mainContentPanel;

    JButton btnStartButton;
    JButton btnLoadButton;
    JButton btnSettingButton;
    JButton btnOnlineButton;
    //文本输入按钮
    JTextField  jta ;
    //gameMode
    JButton GameModeQuestionButton; // ?按钮
    JButton GameModeLeftButton;//左箭头按钮
    JButton GameModeRightButton;//右箭头按钮
    //JScrollPane GameModeInputJSP;//文本输入框，位于下来框下
    JLabel lblGameMode;//gamemode标签
    JLabel lblGameModeTest;//下方的提示框文字
    JComboBox GameModeComboBox;//单选框


    //AIMode
    JButton AIModeQuestionButton; // ?按钮
    JButton AIModeLeftButton;//左箭头按钮
    JButton AIModeRightButton;//右箭头按钮
    JLabel lblAIMode;//gamemode标签
    JComboBox AIModeComboBox;//单选框


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
        //字体表格
        String[] dfonts;
        dfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // 初始化窗体
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


        //显示背景
        ImageIcon background = new ImageIcon("res/images/bg_menu.png");
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, mainframe.getWidth(), mainframe.getHeight());
        background.setImage(background.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        mainframe.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));

//        // 游戏文本标题
//        JLabel jLabel = new JLabel("Bridge Chinois");
//        jLabel.setBounds(30, 25, 300, 30);
//        Font f = new Font("隶书",Font.PLAIN,30);
//        jLabel.setFont(f);
//        Color fg = new Color(255,255,255);
//        jLabel.setForeground(fg);
//        mainContentPanel.add(jLabel);


        mainContentPanel = mainframe.getContentPane();
        mainContentPanel.setLayout(null);
        // start按钮
        //加载背景图片
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
        //加载背景图片
        String onlinePath = "./res/images/ONLINE.png";
        ImageIcon onlineIcon1 = new ImageIcon(onlinePath);
        Image onlineImg = onlineIcon1.getImage();
        Image onlineNewImg = onlineImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon onlineIcon2 = new ImageIcon(onlineNewImg);

        btnOnlineButton = new RoundRectButton(onlineIcon2);
        btnOnlineButton.setBounds(90, 220, 120, 40);
        mainContentPanel.add(btnOnlineButton);


        // load
        //加载背景图片
        String loadPath = "./res/images/LOAD.png";
        ImageIcon loadIcon1 = new ImageIcon(loadPath);
        Image loadImg = loadIcon1.getImage();
        Image loadNewImg = loadImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon loadIcon2 = new ImageIcon(loadNewImg);

        btnLoadButton = new RoundRectButton(loadIcon2);
        btnLoadButton.setBounds(90, 300, 120, 40);
        mainContentPanel.add(btnLoadButton);


        // setting
        //加载背景图片
        String settingPath = "./res/images/SETTING.png";
        ImageIcon settingIcon1 = new ImageIcon(settingPath);
        Image settingImg = settingIcon1.getImage();
        Image settingNewImg = settingImg.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon settingIcon2 = new ImageIcon(settingNewImg);

        btnSettingButton = new RoundRectButton(settingIcon2);
        btnSettingButton.setBounds(90, 380, 120, 40);
        mainContentPanel.add(btnSettingButton);

//        //添加gameMode
//        JLabel gameModeText = new JLabel("GameMode");
//        gameModePanel.add(gameModeText);
//        mainContentPanel.add(gameModePanel);
        /**
         * start gamemode 窗口
         */
        //标题
        lblGameMode = new JLabel("GameMode");
        lblGameMode.setBounds(200, 150, 300, 30);
        Font f1 = new Font("隶书", Font.PLAIN, 30);
        lblGameMode.setFont(f1);
        Color fg1 = new Color(255, 255, 255);
        lblGameMode.setForeground(fg1);
        mainContentPanel.add(lblGameMode);

        GameModeComboBox = new JComboBox();
        // 绑定下拉框选项
        String[] strArray = {"BO1", "BO3", "Number of Fixe", "Score Fixe"};
        for (String item : strArray) {
            GameModeComboBox.addItem(item);
        }
        GameModeComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        GameModeComboBox.setBounds(150, 200, 180, 30);
        mainContentPanel.add(GameModeComboBox);

        //添加问号按钮
        GameModeQuestionButton = new JButton("?");
        GameModeQuestionButton.setBounds(340, 200, 50, 30);
        mainContentPanel.add(GameModeQuestionButton);

        //下方的文字提示
        lblGameModeTest = new JLabel("<html><body>Donner le nombre de Jue<br>(Si vous choisi Numbre  Fixe)<br>ou le nombre de Score<br>(Si vous choisi Score Fixe</body></html>");
        lblGameModeTest.setBounds(150, 150, 300, 300);
        Font f22 = new Font("隶书", Font.PLAIN, 15);
        lblGameModeTest.setFont(f22);
        Color fg22 = new Color(27, 179, 205);
        lblGameModeTest.setForeground(fg22);
        mainContentPanel.add(lblGameModeTest);
        //添加文本框
        jta = new JTextField();
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font(dfonts[15],Font.ROMAN_BASELINE,10 ));    //修改字体样式
        jta.setBounds(150,350,200,30);
        mainContentPanel.add(jta);

        //添加左右按钮
        GameModeLeftButton = new JButton("←");
        GameModeLeftButton.setBounds(150, 400, 50, 30);
        mainContentPanel.add(GameModeLeftButton);
        GameModeRightButton = new JButton("→");
        GameModeRightButton.setBounds(340, 400, 50, 30);
        mainContentPanel.add(GameModeRightButton);

        /**
         * AImode
         */
        // 添加AIMode**********************
        lblAIMode = new JLabel("AIMode");
        lblAIMode.setBounds(200, 150, 300, 30);
        Font f2 = new Font("隶书", Font.PLAIN, 30);
        lblAIMode.setFont(f2);
        Color fg2 = new Color(255, 255, 255);
        lblAIMode.setForeground(fg2);
        mainContentPanel.add(lblAIMode);

        AIModeComboBox = new JComboBox();
        // 绑定下拉框选项
        String[] AIStrArray = {"AIrandom", "AIsimple", "AIminmax", "AIrandom"};
        for (String item : AIStrArray) {
            AIModeComboBox.addItem(item);
        }
        AIModeComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        AIModeComboBox.setBounds(150, 200, 180, 30);
        mainContentPanel.add(AIModeComboBox);

        //添加问号按钮
        //AIModeQuestionButton = new JButton("?");
        AIModeQuestionButton = new JButton();
        ImageIcon Help = new ImageIcon("bridgechinoise-New Version/bridgechinoise/res/images/HELP.png");
        AIModeQuestionButton.setIcon(Help);
        AIModeQuestionButton.setBounds(340, 200, 50, 30);
        mainContentPanel.add(AIModeQuestionButton);


        //添加左右按钮
        AIModeLeftButton = new JButton("←");
        AIModeLeftButton.setBounds(150, 400, 50, 30);
        mainContentPanel.add(AIModeLeftButton);
        AIModeRightButton = new JButton("→");
        AIModeRightButton.setBounds(340, 400, 50, 30);
        mainContentPanel.add(AIModeRightButton);

        gameModeDisappear();
        AIModeDisappear();

    }

    //gameMode消失
    public void gameModeDisappear() {
        lblGameMode.setVisible(false);
        GameModeComboBox.setVisible(false);
        GameModeQuestionButton.setVisible(false);
        jta.setVisible(false);
        lblGameModeTest.setVisible(false);
        GameModeLeftButton.setVisible(false);
        GameModeRightButton.setVisible(false);
    }

    //gameMode出现
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
     * 主界面按钮消失
     */
    public void mainFrameButtonDisappear() {
        btnStartButton.setVisible(false);
        btnLoadButton.setVisible(false);
        btnSettingButton.setVisible(false);
        btnOnlineButton.setVisible(false);
    }


    /**
     * 主界面按钮出现
     */
    public void mainFrameButtonAppear() {
        btnStartButton.setVisible(true);
        btnLoadButton.setVisible(true);
        btnSettingButton.setVisible(true);
        btnOnlineButton.setVisible(true);

    }

    public void addEventListener() {

        //主界面按钮
        btnStartButton.addActionListener(this);
        btnLoadButton.addActionListener(this);
        btnOnlineButton.addActionListener(this);
        btnSettingButton.addActionListener(this);

        //gameMode按钮
        GameModeQuestionButton.addActionListener(this);
        GameModeLeftButton.addActionListener(this);
        GameModeRightButton.addActionListener(this);

        //AIMode按钮
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
        //将GameMode保存
        if (e.getSource() == GameModeRightButton) {
            AIModeAppear();
            gameModeDisappear();
            int GameMode;
            try {
                FileWriter fw;
                fw = new FileWriter("./res/default.cfg", false);
                // Human vs Human



//                String NB = this.interIni.NBText.getText();
//                if (GameMode > 2) {
//                    fw.write("GameInformation=" + NB + "\n");
//                    System.out.println("gameInformation est " + NB);
//                }
//
//                if (this.isAIGame) {
//                    int aiType = 0;
//                    for (int i = 0; i < this.interIni.aiSelections.size(); i++) {
//                        if (this.interIni.aiSelections.get(i).isSelected()) {
//                            aiType = i + 1;
//                            break;
//                        }
//                    }
//                    System.out.println("ai type est " + aiType);
//                    fw.write("AI=" + aiType + "\n");
//                } else {
//                    fw.write("AI=0\n");
//                }
//                fw.close();
//                interIni.frame.setVisible(false);

            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        if (e.getSource() == GameModeQuestionButton) {
            JOptionPane.showMessageDialog(null, "这里给出本游戏模式的疑问解答\r\n如果您有什么不懂的，可以联系我们121212@gmail.com\r\n", "提示", JOptionPane.QUESTION_MESSAGE);
        }
        if (e.getSource() == AIModeLeftButton) {
            AIModeDisappear();
            gameModeAppear();
        }
        if (e.getSource() == AIModeRightButton) {
            mainframe.setVisible(false);
            gameframe.setVisible(true);
        }
        if (e.getSource() == AIModeQuestionButton) {
            JOptionPane.showMessageDialog(null, "这里给出本游戏AI模式的疑问解答\r\n如果您有什么不懂的，可以联系我们121212@gmail.com\r\n", "提示", JOptionPane.QUESTION_MESSAGE);
        }
    }
}


