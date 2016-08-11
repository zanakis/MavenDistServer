/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.galgeleg.gui;

import com.galgeleg.logik.DBConnector;
import com.galgeleg.logik.Galgelogik;
import com.galgeleg.logik.LogikConnector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author zanakis
 */
public class Test {
    
    static Scanner in;
    static LogikConnector connector;
    static String ordet;
    static ArrayList<String> brugteBogstaver = new ArrayList<String>();
    static String synligtOrd;
    static int antalForkerteBogstaver;
    static boolean sidsteBogstavVarKorrekt;
    static boolean spilletErVundet;
    static boolean spilletErTabt;
    static int score;
    
    public static void main(String[] args) throws Exception {
        
        in = new Scanner(System.in);
        connector = new LogikConnector();
        
//        login();
        spil();
    }
    
//    public static void spil() {
////        connector.nulstil();
//        String gæt = "";
//        System.out.println("Ordet er: " + connector.getSynligtOrd());
//        while(!connector.erSpilletSlut()) {
//            ordet = connector.getOrdet();
//            gæt = in.nextLine();
//            for(int i = 0; i < gæt.length(); i++) {
//                connector.gætBogstav(gæt.charAt(i) + "");
//            }
//            System.out.println("Ordet er: " + connector.getSynligtOrd());
//            System.out.println(connector.getBrugteBogstaver());
//        }
//        slutSpil();
//    }

    public static void spil() {
        brugteBogstaver = new ArrayList<>();
        ArrayList<Object> map = connector.init();
        breakdownInitStr(map);
//        check();
        String gæt = "";
        System.out.println("Ordet er: " + synligtOrd);
        while(!spilletErTabt && !spilletErVundet) {
            gæt = in.nextLine();
            for(int i = 0; i < gæt.length(); i++) {
                if(!brugteBogstaver.contains(gæt.charAt(i)+"")) {
                    map = connector.gætBogstav(gæt.charAt(i) + "", ordet,
                            synligtOrd, antalForkerteBogstaver, sidsteBogstavVarKorrekt, score);
                    breakdownInitStr(map);
                    brugteBogstaver.add(gæt.charAt(i)+"");
                }
            }
//            map = connector.update();
//            breakdownInitStr(map);
            System.out.println("Ordet er: " + synligtOrd);
            System.out.println("Brugte bogstaver: " + brugteBogstaver);
        }
        slutSpil();
    }
    
    public static void check() {
        ordet= "hejmed";
        brugteBogstaver = new ArrayList<>();
        brugteBogstaver.add("e");
        brugteBogstaver.add("a");
        brugteBogstaver.add("h");
        antalForkerteBogstaver=1;
        sidsteBogstavVarKorrekt = true;
        score = 110;
    }
    
    public static void breakdownInitStr(ArrayList<Object> map) {
        ordet = (String)map.get(0);
        synligtOrd = (String)map.get(1);
        antalForkerteBogstaver = Integer.parseInt((String)map.get(2));
        sidsteBogstavVarKorrekt = "true".equals((String)map.get(3));
        spilletErVundet = "true".equals((String)map.get(4));
        spilletErTabt = "true".equals((String)map.get(5));
        score = Integer.parseInt((String)map.get(6));
    }
    
    public static void slutSpil() {
        if(spilletErVundet)
            System.out.println("Du har vundet");
        else System.out.println("Du har tabt");
        System.out.println("spil igen? y/n");
        if(in.nextLine().startsWith("y"))
            spil();
    }
    
    public static void login() {
        while(true) {
            System.out.println("indtast brugernavn");
            String usr = in.nextLine();
            System.out.println("indtast kodeord");
            String psw = in.nextLine();
            if(connector.login(usr, psw)) break;
        }
    }
}
