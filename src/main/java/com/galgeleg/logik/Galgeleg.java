/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galgeleg.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author zanakis
 */
@Path("/galgeleg")
@ApplicationPath("/resources")
public class Galgeleg extends Application {

    private static LogikConnector c = new LogikConnector();
    private static DBConnector dbc = new DBConnector();
    
    @Context
    private UriInfo context;

    /**
     * Retrieves representation of an instance of brugerautorisation.Galgeleg
     * @param ch
     * @param ordet
     * @param synligtOrd
     * @param antalBrugteBogstaver
     * @param sidsteBogstavVarForkert
     * @param score
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/guess/{ordet}/{synligtOrd}/{antalBrugteBogstaver}/{sidsteBogstavVarForkert}/{score}/{ch}")
    public String getUpdate(@PathParam("ch") String ch, @PathParam("synligtOrd") String synligtOrd,
            @PathParam("antalBrugteBogstaver") String antalBrugteBogstaver, @PathParam("sidsteBogstavVarForkert") String sidsteBogstavVarForkert,
            @PathParam("ordet") String ordet, @PathParam("score") String score) {
        ArrayList<Object> list;
        list = c.gætBogstav(ch, ordet, synligtOrd, Integer.parseInt(antalBrugteBogstaver),
                Boolean.parseBoolean(sidsteBogstavVarForkert), Integer.parseInt(score));
        String response = "";
        for(Object i : list) {
            response += i;
            response += ";";
        }
        return response.substring(0, response.length()-1);
    }
    
    @GET
    @Path("/reset")
    public String reset() {
        ArrayList<Object> list;
        list = c.init();
        String response = "";
        for(Object i : list) {
            response += i;
            response += ";";
        }
        return response.substring(0, response.length()-1);
    }
    
    @GET
    @Path("/login/{user}/{psw}")
    public Boolean login(@PathParam("user") String user, @PathParam("psw") String psw) {
        return c.login(user, psw);
    }
    
    @GET
    @Path("/getHighscore")
    public String getHighscore() {
        ArrayList<String> list = dbc.loadBoardToArray();
        String response = "";
        for(Object i : list) {
            response += i;
            response += ";";
        }
        return response;
    }

    /**
     * PUT method for updating or creating an instance of Galgeleg
     * @param content representation for the resource
     */
    @PUT
    @Path("/setHighscore/{score}")
    public void putHighscore(String username, @PathParam("score") String score) {
        dbc.addToLeaderBoard(Integer.parseInt(score), username);
    }
}
