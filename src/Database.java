import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Database {
    public  static ArrayList<String> GetLvl1Positions(){
        // metoda pentru a extrage din baza de date pozitiile pasarilor pentru primul nivel
        ArrayList<String> position = new ArrayList<String>();
        Connection c = null;
        Statement statement = null;
        Map<String,String> databasemap = new HashMap<String, String>();
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:startnewgamelvl1.db");
            c.setAutoCommit(true);
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM POSITION;");
            while(rs.next()){
                position.add(rs.getString("pozitie"));
            }
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Startnewgamelvl1.db successfully opened to GetPositions()");
        return position;
    }

    public static void SaveContext(HashMap<String,String> map){
        // metoda pentru salvarea contextului unui nivel, in baza de date, pentru un eventual resume
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:savecontext.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
        }
        catch(Exception ex){
            System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        try {
            String sql;
            sql = "DELETE FROM SAVECONTEXT";
            stmt.executeUpdate(sql);
            int i;
            for (i = 0; i < 50; i++) {
                 sql = "INSERT INTO SAVECONTEXT VALUES(" + i + ", '" + map.get(i + "") + "');";
                 stmt.executeUpdate(sql);
            }
            sql = "INSERT INTO SAVECONTEXT VALUES('rocket', '" + map.get(i + "") + "');";
            stmt.executeUpdate(sql);
            i++;
            sql = "INSERT INTO SAVECONTEXT VALUES('health', '" + map.get(i + "") + "');";
            stmt.executeUpdate(sql);
            i++;sql = "INSERT INTO SAVECONTEXT VALUES('scor', '" + map.get(i + "") + "');";
            stmt.executeUpdate(sql);
            i++;sql = "INSERT INTO SAVECONTEXT VALUES('munitie', '" + map.get(i + "") + "');";
            stmt.executeUpdate(sql);
            i++;sql = "INSERT INTO SAVECONTEXT VALUES('rocketfilepath', '" + map.get(i + "") + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        }catch(SQLException e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("savecontext.db successfully opened to SaveContext()");
    }

    public static HashMap<String,String> resumeGame(){
        // metoda pentru extragerea din baza de date, a informatiilor necesare pentru resume
        HashMap<String,String> map = new HashMap<String,String>();
        Connection c = null;
        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:savecontext.db");
            c.setAutoCommit(true);
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SAVECONTEXT;");
            while(rs.next()){
                String key = rs.getString("KEY");
                String element = rs.getString("ELEMENT");
                map.put(key,element);
            }
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("savecontext.db successfully opened to resumeGame()");
        return map;
    }

    public static void AddRocketToDb(String filepath){
        // metoda pentru schimbarea rachetei ; se salveaza adresa acesteia in baza de date
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:startnewgamelvl1.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
        }
        catch(Exception ex){
            System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        try {
            String sql;
            sql = "UPDATE Position\n"+ "SET `Pozitie` = '" + filepath + "'\n" + "WHERE `Index` = 50;";
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        }catch(SQLException e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("startnewgamelvl1.db successfully opened to AddRocketToDb()");
    }


    public static Map<String,String> GetFilepaths(){
        //metoda pentru incarcarea tututor path-urilor pentru elementele grafice
        Map<String,String> databasemap = new HashMap<String, String>();
        Connection c = null;
        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:filepaths.db");
            c.setAutoCommit(true);
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM FILEPATH;");
            while(rs.next()){
                databasemap.put(rs.getString("key"),rs.getString("value"));
            }
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("filepaths.db successfully opened to GetFilePaths()");
        return databasemap;
    }
}
