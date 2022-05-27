package Controleur;

import Modele.GameProcessVue;
import Vue.InterfaceInitialise;
import Vue.InterfaceJeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class AdapteurStarter implements ActionListener {
    boolean isAIGame;
    InterfaceInitialise interIni;
    GameProcessVue gameProcessVue = new GameProcessVue();

    public AdapteurStarter(boolean isAIGame, InterfaceInitialise interIni) {
        this.isAIGame = isAIGame;
        this.interIni = interIni;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        try {
            initiailiseParameters();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //loadAndStartMainInterface();
        this.interIni.frame.setVisible(false);
    }

    private void initiailiseParameters() throws IOException, ClassNotFoundException {
        int GameMode;
        try {
            FileWriter fw;
            fw = new FileWriter("./bridgechinoise-New Version/bridgechinoise/res/default.cfg", false);
            // Human vs Human
            for (GameMode = 0; GameMode < this.interIni.aiSelections.size(); GameMode++) {
                if (this.interIni.obSelection.get(GameMode).isSelected()) {
                    GameMode++;
                    fw.write("GameMode=" + GameMode + "\n");
                    break;
                }

            }
            String NB = this.interIni.NBText.getText();
            if (GameMode > 2) {
                fw.write("GameInformation=" + NB + "\n");
                System.out.println("gameInformation est " + NB);
            }

            if (this.isAIGame) {
                int aiType = 0;
                for (int i = 0; i < this.interIni.aiSelections.size(); i++) {
                    if (this.interIni.aiSelections.get(i).isSelected()) {
                        aiType = i + 1;
                        break;
                    }
                }
                System.out.println("ai type est " + aiType);
                fw.write("AI=" + aiType + "\n");
            } else {
                fw.write("AI=0\n");
            }
            fw.close();
            interIni.frame.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        gameProcessVue.creatJeu();
        InterfaceJeu.start();

    }

}
