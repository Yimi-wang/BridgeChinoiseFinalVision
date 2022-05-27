package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame implements ActionListener{
    JFrame mainframe;

    JFrame gameframe;

    Container mainContentPanel;

    JButton btnStartButton;
    JButton btnLoadButton;
    JButton btnSettingButton;
    JButton btnOnlineButton;

    //gameMode
    JButton GameModeQuestionButton; // ?按钮
    JButton GameModeLeftButton;//左箭头按钮
    JButton GameModeRightButton;//右箭头按钮
    JScrollPane GameModeInputJSP;//文本输入框，位于下来框下
    JLabel lblGameMode;//gamemode标签
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
        // 初始化窗体
        mainframe = new JFrame("Bcvue game");
        mainframe.setBounds(300, 300, 720, 506);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainContentPanel = mainframe.getContentPane();
//        // 游戏文本标题
//        JLabel jLabel = new JLabel("Bridge Chinois");
//        jLabel.setBounds(30, 25, 300, 30);
//        Font f = new Font("隶书",Font.PLAIN,30);
//        jLabel.setFont(f);
//        Color fg = new Color(255,255,255);
//        jLabel.setForeground(fg);
//        mainContentPanel.add(jLabel);



        // start按钮
        //加载背景图片
        String startPath="./bridgechinoise-New Version/bridgechinoise/res/images/START.png";
        ImageIcon startIcon1=new ImageIcon(startPath);
        Image startImg = startIcon1.getImage();
        Image startNewimg = startImg.getScaledInstance(120,40,java.awt.Image.SCALE_SMOOTH);
        ImageIcon startIcon2 = new ImageIcon(startNewimg);
        btnStartButton = new RoundRectButton(startIcon2);
        btnStartButton.setBounds(30, 90, 120, 40);
        btnStartButton.setBorder(new RoundBorder(Color.WHITE));
        btnStartButton.setBackground(Color.RED);
        mainContentPanel.add(btnStartButton);


        // online
        //加载背景图片
        String onlinePath="./bridgechinoise-New Version/bridgechinoise/res/images/ONLINE.png";
        ImageIcon onlineIcon1=new ImageIcon(onlinePath);
        Image onlineImg = onlineIcon1.getImage();
        Image onlineNewImg = onlineImg.getScaledInstance(120,40,java.awt.Image.SCALE_SMOOTH);
        ImageIcon onlineIcon2 = new ImageIcon(onlineNewImg);

        btnOnlineButton = new RoundRectButton(onlineIcon2);
        btnOnlineButton.setBounds(30, 150, 120, 40);
        mainContentPanel.add(btnOnlineButton);


        // load
        //加载背景图片
        String loadPath="./bridgechinoise-New Version/bridgechinoise/res/images/LOAD.png";
        ImageIcon loadIcon1=new ImageIcon(loadPath);
        Image loadImg = loadIcon1.getImage();
        Image loadNewImg = loadImg.getScaledInstance(120,40,java.awt.Image.SCALE_SMOOTH);
        ImageIcon loadIcon2 = new ImageIcon(loadNewImg);

        btnLoadButton = new RoundRectButton(loadIcon2);
        btnLoadButton.setBounds(30, 210, 120, 40);
        mainContentPanel.add(btnLoadButton);


        // setting
        //加载背景图片
        String settingPath="./bridgechinoise-New Version/bridgechinoise/res/images/SETTING.png";
        ImageIcon settingIcon1=new ImageIcon(settingPath);
        Image settingImg = settingIcon1.getImage();
        Image settingNewImg = settingImg.getScaledInstance(120,40,java.awt.Image.SCALE_SMOOTH);
        ImageIcon settingIcon2 = new ImageIcon(settingNewImg);

        btnSettingButton = new RoundRectButton(settingIcon2);
        btnSettingButton.setBounds(30, 270, 120, 40);
        mainContentPanel.add(btnSettingButton);

//        //添加gameMode
//        JLabel gameModeText = new JLabel("GameMode");
//        gameModePanel.add(gameModeText);
//        mainContentPanel.add(gameModePanel);

        lblGameMode = new JLabel("GameMode");
        lblGameMode.setBounds(200, 80, 300, 30);
        Font f1 = new Font("隶书",Font.PLAIN,30);
        lblGameMode.setFont(f1);
        Color fg1 = new Color(255,255,255);
        lblGameMode.setForeground(fg1);
        mainContentPanel.add(lblGameMode);

        GameModeComboBox = new JComboBox();
        // 绑定下拉框选项
        String[] strArray = { "BO1","BO3","Number of Fixe","Score Fixe"};
        for (String item : strArray)
        {
            GameModeComboBox.addItem(item);
        }
        GameModeComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        GameModeComboBox.setBounds(150, 140, 180, 30);
        mainContentPanel.add(GameModeComboBox);

        //添加问号按钮
        GameModeQuestionButton = new JButton("?");
        GameModeQuestionButton.setBounds(340, 140, 50, 30);
        mainContentPanel.add(GameModeQuestionButton);



        // 添加文本框
        JTextArea jta=new JTextArea("",7,30);
        jta.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
        jta.setBackground(Color.white);    //设置按钮背景色
        GameModeInputJSP =new JScrollPane(jta);    //将文本域放入滚动窗口
        Dimension size=jta.getPreferredSize();    //获得文本域的首选大小
        GameModeInputJSP.setBounds(150,190,240,size.height);
        mainContentPanel.add(GameModeInputJSP);


        //添加左右按钮
        GameModeLeftButton = new JButton("←");
        GameModeLeftButton.setBounds(150, 340, 50, 30);
        mainContentPanel.add(GameModeLeftButton);
        GameModeRightButton = new JButton("→");
        GameModeRightButton.setBounds(340, 340, 50, 30);
        mainContentPanel.add(GameModeRightButton);


        // 添加AIMode**********************
        lblAIMode = new JLabel("AIMode");
        lblAIMode.setBounds(200, 80, 300, 30);
        Font f2 = new Font("隶书",Font.PLAIN,30);
        lblAIMode.setFont(f2);
        Color fg2 = new Color(255,255,255);
        lblAIMode.setForeground(fg2);
        mainContentPanel.add(lblAIMode);

        AIModeComboBox = new JComboBox();
        // 绑定下拉框选项
        String[] AIStrArray = { "AIrandom","AIsimple","AIminmax","AIrandom"};
        for (String item : AIStrArray)
        {
            AIModeComboBox.addItem(item);
        }
        AIModeComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        AIModeComboBox.setBounds(150, 140, 180, 30);
        mainContentPanel.add(AIModeComboBox);

        //添加问号按钮
        //AIModeQuestionButton = new JButton("?");
        AIModeQuestionButton = new JButton();
        ImageIcon Help = new ImageIcon("bridgechinoise-New Version/bridgechinoise/res/images/HELP.png");
        AIModeQuestionButton.setIcon(Help);
        AIModeQuestionButton.setBounds(340, 140, 50, 30);
        mainContentPanel.add(AIModeQuestionButton);



        //添加左右按钮
        AIModeLeftButton = new JButton("←");
        AIModeLeftButton.setBounds(180, 240, 50, 30);
        mainContentPanel.add(AIModeLeftButton);
        AIModeRightButton = new JButton("→");
        AIModeRightButton.setBounds(300, 240, 50, 30);
        mainContentPanel.add(AIModeRightButton);


        // 设置背景, 背景要在最后面设置
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象

        ImageIcon icon = new ImageIcon("./bridgechinoise-New Version/bridgechinoise/res/images/bg_menu.png"); // 创建背景图片对象
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        lblBackground.setBounds(0, 0, mainframe.getWidth(), mainframe.getHeight()); // 设置组件的显示位置及大小
        mainContentPanel.add(lblBackground); // 将组件添加到面板中

        gameModeDisappear();
        AIModeDisappear();

    }

    //gameMode消失
    public void gameModeDisappear(){
        lblGameMode.setVisible(false);
        GameModeComboBox.setVisible(false);
        GameModeQuestionButton.setVisible(false);
        GameModeInputJSP.setVisible(false);
        GameModeLeftButton.setVisible(false);
        GameModeRightButton.setVisible(false);
    }

    //gameMode出现
    public void gameModeAppear(){
        lblGameMode.setVisible(true);
        GameModeComboBox.setVisible(true);
        GameModeQuestionButton.setVisible(true);
        GameModeInputJSP.setVisible(true);
        GameModeLeftButton.setVisible(true);
        GameModeRightButton.setVisible(true);
    }


    public void AIModeDisappear(){
        lblAIMode.setVisible(false);
        AIModeComboBox.setVisible(false);
        AIModeQuestionButton.setVisible(false);
        AIModeLeftButton.setVisible(false);
        AIModeRightButton.setVisible(false);
    }
    public void AIModeAppear(){
        lblAIMode.setVisible(true);
        AIModeComboBox.setVisible(true);
        AIModeQuestionButton.setVisible(true);
        AIModeLeftButton.setVisible(true);
        AIModeRightButton.setVisible(true);
    }



    /**
     * 主界面按钮消失
     */
    public void mainFrameButtonDisappear(){
        btnStartButton.setVisible(false);
        btnLoadButton.setVisible(false);
        btnSettingButton.setVisible(false);
        btnOnlineButton.setVisible(false);
    }


    /**
     * 主界面按钮出现
     */
    public void mainFrameButtonAppear(){
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
        if(e.getSource()==btnStartButton){
            mainFrameButtonDisappear();
            gameModeAppear();
        }
        if(e.getSource()== GameModeLeftButton){
            mainFrameButtonAppear();
            gameModeDisappear();
        }
        if(e.getSource()==GameModeRightButton){
            AIModeAppear();
            gameModeDisappear();
        }
        if(e.getSource()== GameModeQuestionButton){
            JOptionPane.showMessageDialog(null, "这里给出本游戏模式的疑问解答\r\n如果您有什么不懂的，可以联系我们121212@gmail.com\r\n", "提示", JOptionPane.QUESTION_MESSAGE);
        }
        if(e.getSource()==AIModeLeftButton){
            AIModeDisappear();
            gameModeAppear();
        }
        if(e.getSource()==AIModeRightButton){
            mainframe.setVisible(false);
            gameframe.setVisible(true);
        }
        if(e.getSource()==AIModeQuestionButton){
            JOptionPane.showMessageDialog(null, "这里给出本游戏AI模式的疑问解答\r\n如果您有什么不懂的，可以联系我们121212@gmail.com\r\n", "提示", JOptionPane.QUESTION_MESSAGE);
        }
    }
}


