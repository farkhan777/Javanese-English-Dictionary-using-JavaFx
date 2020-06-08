package sample.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Farkhan Hamzah Firdaus
 * @project ProjectPBOKamus
 * @created 14/05/2020 - 5:20 AM
 */
public class DbConnect {
    public static Connection getConnection(){

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Kamus.sqlite");

            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
