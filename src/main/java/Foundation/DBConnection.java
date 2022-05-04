package Foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con;

    /**
     * get the instance connection
     *
     * @return the connection
     */
    public static Connection getInstance() {
        if (con == null) {
            connect();
        }

        return con;
    }

    /**
     * establishes the connection to the database of the Animal Shelter
     */
    private static void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=AUTOCAMPER", "Patrick", "123456");
            System.out.println("Connected to " + con.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    public static boolean isConnected() {
        if (con != null) {
            return true;
        } else {
            return false;
        }
    }

}
