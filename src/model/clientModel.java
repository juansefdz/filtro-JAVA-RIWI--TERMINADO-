package model;

import database.CRUD;
import database.configDB;
import entity.Client;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class clientModel implements CRUD {
    @Override
    public Object insert(Object object) {
        Connection objConnection = configDB.openConnection();

        Client objClient = (Client) object;

        try {
            String sql = "INSERT INTO clients (name_client,last_name_client,email_client) VALUES (?,?,?);";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, RETURN_GENERATED_KEYS);
            objPrepare.setString(1, objClient.getName());
            objPrepare.setString(2, objClient.getLastName());
            objPrepare.setString(3, objClient.getEmail());

            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()) {
                objClient.setId(objResult.getInt(1));
            }
            objPrepare.close();
            JOptionPane.showMessageDialog(null, "client has been created successfully in DB");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding the client: " + e.getMessage());
        }

        configDB.closeConnection();
        return objClient;
    }
    @Override
    public List<Object> findAll() {
        List<Object> listClients = new ArrayList<>();
        Connection objConnection = configDB.openConnection();

        try {
            String sql = "SELECT * FROM clients;";
            PreparedStatement objPrepareStatement = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepareStatement.executeQuery();

            while (objResult.next()) {
                Client objCLient = new Client();

                objCLient.setId(objResult.getInt("id_client"));
                objCLient.setName(objResult.getString("name_client"));
                objCLient.setLastName(objResult.getString("last_name_client"));
                objCLient.setEmail(objResult.getString("email_client"));

                listClients.add(objCLient);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data acquisition error: " + e.getMessage());
        }

        configDB.closeConnection();
        return listClients;
    }

    @Override
    public boolean delete(Object object) {
        Client objClient = (Client) object;
        boolean isDeleted = false;

        Connection objConnection = configDB.openConnection();

        try {
            String sql = "DELETE FROM clients WHERE id_client=?";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setInt(1, objClient.getId());

            int totalAffectedRows = objPrepare.executeUpdate();
            if (totalAffectedRows > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "Client deleted successfully");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting the client: " + e.getMessage());
        }

        configDB.closeConnection();
        return isDeleted;
    }

    @Override
    public boolean update(Object object) {
        Connection objConnection = configDB.openConnection();
        Client objClient = (Client) object;
        boolean isUpdated = false;

        try {
            String sql = "UPDATE clients SET name_client=?, last_name_client=?, email_client =? WHERE id_client=?";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setString(1, objClient.getName());
            objPrepare.setString(2, objClient.getLastName());
            objPrepare.setString(3, objClient.getEmail());
            objPrepare.setInt(4, objClient.getId());


            int rowAffected = objPrepare.executeUpdate();
            if (rowAffected > 0) {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Client updated successfully");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating the client: " + e.getMessage());
        }

        configDB.closeConnection();
        return isUpdated;
    }

    public Object findbyClient(String clientName) {

        Connection objConnection = configDB.openConnection(); //open connection
        List<Client> clientByName = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE clients.name_client LIKE ?;"; //SQL sentence

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql); //prepareStatment
            objPrepare.setString(1, "%" + clientName + "%"); //Preparestatment value

            ResultSet objResult = objPrepare.executeQuery(); //execute query
            while (objResult.next()) {
                Client objNameClient = new Client();
                objNameClient.setId(objResult.getInt("id_client"));
                objNameClient.setName(objResult.getString("name_client")); //values to  query parameters
                objNameClient.setLastName(objResult.getString("last_name_client")); //values to  query parameters
                objNameClient.setEmail(objResult.getString("email_client"));
                clientByName.add(objNameClient);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        configDB.closeConnection(); //close connection
        return clientByName;
    }



}
