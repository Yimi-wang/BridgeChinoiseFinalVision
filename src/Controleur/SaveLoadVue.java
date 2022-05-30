package Controleur;

import Modele.GameProcess;
import Modele.Histoire;
import Modele.Jeu;

import java.io.*;
import java.util.Scanner;

public class SaveLoadVue implements java.io.Serializable {
    Modele.GameProcess GameProcess = new GameProcess();

    public SaveLoadVue() {
    }

    public void Save(Histoire h,String Save) {
        Save = "./res/saveload/"+Save;
        Save = Save.concat(".ser");

        try {
            File file = new File(Save);
            FileOutputStream fileOut = new FileOutputStream(Save);

            //创建一个ObjectOutputStream
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            //将对象写入输出流
            objOut.writeObject(h);

            objOut.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public Histoire Load(String Load) {
        Histoire h = null;
        try {
            String Loada = "./res/saveload/" + Load;
            //读取对象
            FileInputStream fileIn = new FileInputStream(Loada);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            h = (Histoire) objIn.readObject();
            objIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return h;
        } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
            return h;
        }
        return h;
    }



}

