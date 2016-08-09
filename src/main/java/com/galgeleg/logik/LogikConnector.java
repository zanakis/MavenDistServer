/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galgeleg.logik;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author zanakis
 */
public class LogikConnector {
    static Galgelogik l = new Galgelogik();
    
    public HashMap<Integer, Object> update() {
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(0, l.getOrdet());
        map.put(1, l.getBrugteBogstaver());
        map.put(2, l.getSynligtOrd());
        map.put(3, ""+l.getAntalForkerteBogstaver());
        map.put(4, ""+l.erSidsteBogstavKorrekt());
        map.put(5, ""+l.erSpilletVundet());
        map.put(6, ""+l.erSpilletTabt());
        map.put(7, ""+l.getScore());
//        l.nulstil();
        return map;
    }
    
    public void setParams(String ordet, ArrayList<String> brugteBogstaver, String synligtOrd,
            int antalBrugteBogstaver, boolean sidsteBogstavVarForkert, int score) {
        l.setOrdet(ordet);
        l.setAntalForkerteBogstaver(antalBrugteBogstaver);
        l.setBrugteBogstaver(brugteBogstaver);
        l.setSidsteBogstavVarKorrekt(sidsteBogstavVarForkert);
        l.setSynligtOrd(synligtOrd);
        l.setScore(score);
    }

    public String getSynligtOrd() {
        return l.getSynligtOrd();
    }

    public boolean erSpilletSlut() {
        return l.erSpilletSlut();
    }

    public String getOrdet() {
        return l.getOrdet();
    }

    public void gætBogstav(String string, String ordet, ArrayList<String> brugteBogstaver,
            String synligtOrd, int antalBrugteBogstaver, boolean sidsteBogstavVarForkert, int score) {
        setParams(ordet, brugteBogstaver, synligtOrd, antalBrugteBogstaver,
                sidsteBogstavVarForkert, score);
        l.gætBogstav(string);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<String> getBrugteBogstaver() {
        return l.getBrugteBogstaver();
    }

    public boolean erSpilletVundet() {
        return l.erSpilletVundet();
    }

    public boolean login(String usr, String psw) {
        return l.login(usr, psw);
    }

    public HashMap<Integer, Object> init() {
        l.nulstil();
        return update();
    }
    
}
