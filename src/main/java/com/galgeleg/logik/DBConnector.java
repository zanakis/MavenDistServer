package com.galgeleg.logik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
    
    private final String HOST = "matadordist.crxqr17duncu.us-west-2.rds.amazonaws.com";
    private final int PORT = 3306;
    private final String USERNAME = "arbabjava";
    private final String PASSWORD = "fresh123";
    private final String DB_NAME = "Leaderboard";
    private final String createDB = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
    public Connection connection;
    
    /**
     * **************************************************************
     * This method is used to create a connection to the specific	* database
     * that we are using.	*
     **************************************************************
     */
    public DBConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * **************************************************************************
     * Method to use making SQL queries for our later use of SQL scripts.
     *
     *
     * @param query The SQL command we want to complete is defined by the query
     *
     * @return Returns the results we want.	*
     **************************************************************************
     */
    public ResultSet doQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
    
    /**
     * **************************************************************************
     * Method to use when we want to send the query we just created to the *
     * database.
     *
     *
     * @param query The SQL command we want to complete is defined by the query
     * *
     **************************************************************************
     */
    public void doUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }
    
    /**
     * **************************************************************************
     * Method that will create a string from the query we created so it can be	*
     * read in the database.
     *
     *
     * @param query The SQL command we want to complete is defined by the query
     * *
     **************************************************************************
     */
    public String[] doQueryToString(String query) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            list.add(rs.getString(1));
            
        }
        
        String[] result = new String[list.size()];
        result = list.toArray(result);
        
        return result;
    }
    
    /**
     * **************************************************************************
     * Method that will create an arraylist of the games in the database	* and
     * will create the columns listed under in the Game schema.
     *
     *
     * @param query The SQL command we want to complete is defined by the query
     *
     * @return The list of games that all have the same columns in the schema	*
     **************************************************************************
     */
    public ArrayList<String> loadBoardToArray() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT position, player_id, points FROM leaderboard;");
            while (rs.next()) {
                int position = rs.getInt("position");
                String playerName = rs.getString("player_id");
                int points = rs.getInt("points");
                list.add(String.valueOf(position));
                list.add(playerName);
                list.add(String.valueOf(points));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * **********************************************************************
     * SQL method to create the leaderboard. It saves the score of the player
     * so that it is stored for when you come back.
     **********************************************************************
     */
    public void createLeaderboard() {
        String query = ("CREATE TABLE IF NOT EXISTS leaderboard"
                + "(position int NOT NULL AUTO_INCREMENT,"
                + "player_id varchar (255) NOT NULL, "
                + "points int NOT NULL,"
                + "PRIMARY KEY (position))");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * *
     * Adds player to table when gameBoard is initialized.
     *
     * @param gameName Name of the game that is being played.
     *
     * @param playerIndex
     * @param playerName Name of the player being added.
     * @param balance The balance of the player being added.
     * @param housesOwned How many houses the player owns.
     * @param hotelsOwned How many hotels the player owns.
     * @param prisonCards If the player has any prisoncards.
     * @param playerPosition Where the player is on the gameBoard.	*
     **************************************************************
     */
    public void addToPlayerTable(String gameName, int playerIndex, String playerName, int balance, int housesOwned, int hotelsOwned, int prisonCards, int playerPosition) {
        String query = ("INSERT INTO " + gameName + ".player_list(playerName, playerIndex,playerBalance, housesOwned, hotelsOwned, prisonCards, playerPosition)"
                + "VALUES('" + playerName + "','" + playerIndex + "','" + balance + "','" + housesOwned + "','" + hotelsOwned + "','" + prisonCards + "','" + playerPosition + "');");
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * ******************************************************************
     * Updates the player table whenever there are changes to the table.
     *
     *
     * @param gameName Name of the game that is being played.
     *
     * @param playerName Name of the player being added.
     *
     * @param balance The balance of the player being added.
     *
     * @param housesOwned How many houses the player owns.
     *
     * @param hotelsOwned How many hotels the player owns.
     *
     * @param prisonCards If the player has any prisoncards.
     *
     * @param playerPosition Where the player is on the gameBoard.	*
     ******************************************************************
     */
    public void updatePlayerTable(String gameName, String playerName, int balance, int housesOwned, int hotelsOwned, int prisonCards, int playerPosition) {
        String query = ("UPDATE " + gameName + ".player_list "
                + "SET playerBalance='" + balance + "', housesOwned='" + housesOwned + "', hotelsOwned='" + hotelsOwned + "', prisonCards='" + prisonCards + "', playerPosition='" + playerPosition + "' "
                + "WHERE playerName='" + playerName + "';");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * **********************************************************
     * Adds a new game to the game table.
     *
     *
     * @param gameName Name of the game that is being created.
     *
     * @param gameState Which state of the game we're at.
     *
     * @param turnNumber Which turn it is.
     *
     * @param playerTurn Whose turn it is to roll the dice.	*
     **********************************************************
     */
    public void addToLeaderBoard(int points, String playerId) {
        String query = ("INSERT INTO leaderboard(points, player_id)"
                + "VALUES('" + points + "', '" + playerId + "');");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * **************************************************************
     * Will update game table when there are changes to the table.
     *
     *
     * @param gameName Name of the game that is being updated.
     *
     * @param gameState Which state the game is at.
     *
     * @param turnNumber Which turn it is.
     *
     * @param playerTurn Whose turn it is to roll the dice.	*
     **************************************************************
     */
    public void updateLeaderBoard(int points, String playerId) {
        String query = ("UPDATE leaderboard "
                + "SET points='" + points + "', player_id='" + playerId + "'"
                + "WHERE player_id='" + playerId + "';");
        
        try {
            doUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void createTables() {
        createLeaderboard();
    }
    
    public void createGameDB() {
        try {
            doUpdate(createDB);
            doQuery("USE " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearLeaderboard() {
        try {
            doUpdate("TRUNCATE leaderboard;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
