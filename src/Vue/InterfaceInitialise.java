package Vue;

import Controleur.AdapteurStarter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InterfaceInitialise implements Runnable{

    public JTextField obText;
    public ArrayList<JRadioButton> obSelection;
    public ButtonGroup obDiffGroup;
    public JFrame frame;
    public JButton startHuman, startAI;

    public JRadioButton[] aiPlayerSelections;
    public ArrayList<JRadioButton> aiSelections;
    public ButtonGroup aiDiffGroup;


    public JTextField NBText;
    public static void main(String[] args) {

    }
    public InterfaceInitialise() {

        aiPlayerSelections = new JRadioButton[2];
        aiDiffGroup = new ButtonGroup();
        this.aiSelections = new ArrayList<JRadioButton>();
        obText=new JTextField();

        obDiffGroup = new ButtonGroup();

        this.obSelection=new ArrayList<JRadioButton>();

        NBText=new JTextField();
        NBText.setPreferredSize(new Dimension(20,20));
    }

    @Override
    public void run() {
        //MusicTest.play();
        this.frame = new JFrame("Bridge Chinoise");
        JRadioButton aiOption1 = new JRadioButton("AI Random");
        JRadioButton aiOption2 = new JRadioButton("AI Simple");
        JRadioButton aiOption3 = new JRadioButton("AI Arbre MinMax");
        JRadioButton aiOption4 = new JRadioButton("AI Random VS AI Simple");
        aiSelections.add(aiOption1);
        aiSelections.add(aiOption2);
        aiSelections.add(aiOption3);
        aiSelections.add(aiOption4);

        JRadioButton obOption1=new JRadioButton("BO1");
        JRadioButton obOption2=new JRadioButton("BO3");
        JRadioButton obOption3=new JRadioButton("Number de Game Fixe");
        JRadioButton obOption4=new JRadioButton("Score Fixe");
        obSelection.add(obOption1);
        obSelection.add(obOption2);
        obSelection.add(obOption3);
        obSelection.add(obOption4);

        this.startHuman = new JButton("Start with human");
        this.startHuman.addActionListener(new AdapteurStarter(false, this));
        this.startAI = new JButton("Start with an AI");
        this.startAI.addActionListener(new AdapteurStarter(true, this));

        aiDiffGroup.add(aiOption1);
        aiDiffGroup.add(aiOption2);
        aiDiffGroup.add(aiOption3);
        aiDiffGroup.add(aiOption4);
        aiOption1.setSelected(true);


        obDiffGroup.add(obOption1);
        obDiffGroup.add(obOption2);
        obDiffGroup.add(obOption3);
        obDiffGroup.add(obOption4);
        obOption1.setSelected(true);


        frame.setLayout(new FlowLayout());

        Box frameBox = Box.createVerticalBox();
        Box settingBox = Box.createHorizontalBox();
        Box humanBox = Box.createVerticalBox();
        Box aiBox = Box.createVerticalBox();
        Box aiSettingBox = Box.createHorizontalBox();
        Box aiDiffBox = Box.createVerticalBox();
        Box obBox=Box.createVerticalBox();
        Box obDiffBox = Box.createVerticalBox();

        Box NumberBox = Box.createHorizontalBox();
        JLabel NumLabel = new JLabel("Donner NB de game");

        NumberBox.add(NumLabel);
        NumberBox.add(NBText);


        obDiffBox.add(obOption1);
        obDiffBox.add(obOption2);
        obDiffBox.add(obOption3);
        obDiffBox.add(obOption4);

        obBox.add(obDiffBox);



        aiDiffBox.add(aiOption1);
        aiDiffBox.add(aiOption2);
        aiDiffBox.add(aiOption3);
        aiDiffBox.add(aiOption4);

        aiSettingBox.add(aiDiffBox);

        aiBox.add(startAI);
        aiBox.add(Box.createVerticalStrut(20));
        startAI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        aiBox.add(aiSettingBox);


        humanBox.add(startHuman);
        humanBox.add(Box.createVerticalStrut(20));
        startHuman.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        humanBox.add(obBox);
        humanBox.add(NumberBox);



        settingBox.add(humanBox);
        //humanBox.setPreferredSize(new Dimension(2, 150));
        //settingBox.add(Box.createHorizontalStrut(10));
        settingBox.add(aiBox);


        frameBox.add(settingBox);

        frame.add(frameBox);

        frame.setSize(400, 250);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void start() {
        // Swing s'exécute dans un thread séparé. En aucun cas il ne faut accéder directement
        // aux composants graphiques depuis le thread principal. Swing fournit la méthode
        // invokeLater pour demander au thread de Swing d'exécuter la méthode run d'un Runnable.
        SwingUtilities.invokeLater(new InterfaceInitialise());
    }
}
