package Vue;

import Controleur.SaveLoadVue;
import Modele.Histoire;
import Modele.Jeu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class LoadInterface implements Runnable{

    public JFrame frame;

    public LoadInterface() {
    }

    public String[] getAllFileNames() {
        File folder = new File("./res/saveload");
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> al = new ArrayList<String>();
        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) al.add(listOfFiles[i].getName());
        }
        return al.toArray(new String[al.size()]);
    }

    @Override
    public void run() {
        this.frame = new JFrame("Charger !");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        JButton confirm = new JButton("Charger");
        JButton cancel = new JButton("Quitter");

        JLabel infoLabel = new JLabel("Choisissez un fichier ид charger :");
        infoLabel.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(confirm);
        buttonBox.add(cancel);

        String items[] = this.getAllFileNames();

        JComboBox<String> fileComboBox = new JComboBox<String>(items);
        fileComboBox.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        Box contentBox = Box.createVerticalBox();
        contentBox.add(infoLabel);
        contentBox.add(fileComboBox);

        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               System.out.println( fileComboBox.getSelectedItem().toString());
                SaveLoadVue sl = new SaveLoadVue();
                Histoire h=sl.Load(fileComboBox.getSelectedItem().toString());
                Jeu j=h.listDeHistoire.get(h.listDeHistoire.size()-1);
                if(j.AI==0){
                    InterfaceJeuLoad ij = new InterfaceJeuLoad(h);
                    ij.run();
                }else{
                    InterfaceIALoad ia = new InterfaceIALoad(h);
                    ia.run();
                }
                frame.dispose();

            }
        });


        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.backmenu();
            }
        });

        frame.setLayout(new BorderLayout(20, 20));

        frame.add(contentBox, BorderLayout.CENTER);
        frame.add(buttonBox, BorderLayout.SOUTH);

        frame.setSize(600, 180);
        frame.setVisible(true);
    }

}
