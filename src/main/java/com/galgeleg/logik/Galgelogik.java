package com.galgeleg.logik;

import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Galgelogik {
  private ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;

  private int score;

  public void setOrdet(String ordet) {
      this.ordet = ordet;
  }
  
  public void setBrugteBogstaver(ArrayList<String> brugteBogstaver) {
      this.brugteBogstaver = brugteBogstaver;
  }
  
  public void setSynligtOrd(String synligtOrd) {
      this.synligtOrd = synligtOrd;
  }
  
  public void setAntalForkerteBogstaver(int antalForkerteBogstaver) {
      this.antalForkerteBogstaver = antalForkerteBogstaver;
  }
  
  public void setSidsteBogstavVarKorrekt(Boolean sidsteBogstavVarKorrekt) {
      this.sidsteBogstavVarKorrekt = sidsteBogstavVarKorrekt;
  }
  
  public void setScore(int score) {
      this.score = score;
  }

  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }

  //Scoresystem
  public int getScore(){
    return score;
  }

  public void enrigtigiscore(int nyScore){
    score +=10;
  }
  public void enfejiscore(int nyScore){
    score -=10;
  }



  public Galgelogik() {
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    try {
        hentOrdFraDr();
    } catch(Exception e) {
        e.printStackTrace();
        System.out.println("Kan ikke hente ord fra DR");
    }
    nulstil();
  }

  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
    setScore(100);
  }


  public void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav+ " DIN STARTSCORE ER: "+ getScore());
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      enrigtigiscore(getScore());
      System.out.println("Bogstavet var korrekt: " + bogstav+ " DIN SCORE ER NU: "+ getScore());

    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      enfejiscore(getScore());
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav+ " DIN SCORE ER NU: " + getScore());
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
    System.out.println("DIN SCORE ER NU: " + getScore());
  }


  public static String hentUrl(String url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }

  public void hentOrdFraDr() throws Exception {
    String data = hentUrl("http://dr.dk");
    //System.out.println("data = " + data);

    data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }

  public void letteOrd(){

    Iterator<String> it = muligeOrd.iterator();
    while (it.hasNext()) {
      String str = it.next();
      if(str.length()<3 || str.length()>5){
        it.remove();
        nulstil();
      }
    }
  }

  public void normalOrd(){
    Iterator<String> it = muligeOrd.iterator();
    while (it.hasNext()) {
      String str = it.next();
      if(str.length()<5 || str.length()>7){
        it.remove();
        nulstil();
      }
    }
  }

  public void svaerOrd(){
    Iterator<String> it = muligeOrd.iterator();
    while (it.hasNext()) {
      String str = it.next();
      if(str.length()<=8 || str.length()>15){
        it.remove();
        nulstil();
      }
    }
  }
  
  public boolean login(String usr, String psw) {
      try {
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            Brugeradmin ba = service.getPort(Brugeradmin.class);
            ba.hentBruger(usr, psw);
            return true;
      }catch(Exception e) {
          e.printStackTrace();
          return false;
      }
      
  }
}
