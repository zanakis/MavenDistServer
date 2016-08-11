/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galgeleg.logik;

import java.util.ArrayList;

public class LogikConnector {
    static Galgelogik l = new Galgelogik();
    
    public ArrayList<Object> update() {
        ArrayList<Object> map = new ArrayList<>();
        map.add(l.getOrdet());
        map.add(l.getSynligtOrd());
        map.add(""+l.getAntalForkerteBogstaver());
        map.add(""+l.erSidsteBogstavKorrekt());
        map.add(""+l.erSpilletVundet());
        map.add(""+l.erSpilletTabt());
        map.add(""+l.getScore());
//        l.nulstil();
        return map;
    }
    
    public void setParams(String ordet, String synligtOrd,
            int antalBrugteBogstaver, boolean sidsteBogstavVarForkert, int score) {
        l.setOrdet(ordet);
        l.setAntalForkerteBogstaver(antalBrugteBogstaver);
        l.setSidsteBogstavVarKorrekt(sidsteBogstavVarForkert);
        l.setSynligtOrd(synligtOrd);
        l.setScore(score);
    }

    public ArrayList<Object> gætBogstav(String c, String ordet,
            String synligtOrd, int antalBrugteBogstaver, boolean sidsteBogstavVarForkert, int score) {
        setParams(ordet, synligtOrd, antalBrugteBogstaver,
                sidsteBogstavVarForkert, score);
        l.gætBogstav(c);
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<Object> map = update();
        return update();
    }

    public boolean login(String usr, String psw) {
        return l.login(usr, psw);
    }

    public ArrayList<Object> init() {
        l.nulstil();
        return update();
    }

    void setHighscore(String username, String score) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
