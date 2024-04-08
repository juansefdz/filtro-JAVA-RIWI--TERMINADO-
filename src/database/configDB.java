package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class configDB {
    static Connection objConnection = null;
    public static Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://b2m92oeartoxly5lbxbl-mysql.services.clever-cloud.com:3306/b2m92oeartoxly5lbxbl";
            String user = "u63fezacd9tvovsy";
            String password = "0FUZgls3SZoQIJ8VMpfU";
            //Establecemos la conexión
            objConnection = (Connection) DriverManager.getConnection(url, user, password);
            System.out.println("successful connection!");

        } catch (ClassNotFoundException e) {
            System.out.println("Error >> Driver not found - please install Driver");
        } catch (SQLException e) {
            System.out.println("Error >> A connection to the database could not be established");
        }

        return objConnection;
    }

    public static void closeConnection() {
        try {

            if (objConnection != null) objConnection.close();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }



}
