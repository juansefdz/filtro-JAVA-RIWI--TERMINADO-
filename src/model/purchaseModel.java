package model;

import database.CRUD;
import database.configDB;
import entity.Client;
import entity.Purchase;
import utils.Utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class purchaseModel implements CRUD {

    public static purchaseModel instanceModel() {
        return new purchaseModel();
    }

    @Override
    public Object insert(Object object) {
        Connection objConnection = configDB.openConnection();

        Purchase objPurchase = (Purchase) object;

        try {
            String sql = "INSERT INTO purchases (id_purchase,id_client,id_product,purchase_date,amount) VALUES (?,?,?,?,?);";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, RETURN_GENERATED_KEYS);
            objPrepare.setInt(1, objPurchase.getId());
            objPrepare.setInt(2, objPurchase.getIdClient());
            objPrepare.setInt(3, objPurchase.getIdProduct());
            objPrepare.setTimestamp(4, objPurchase.getPurchaseDate());
            objPrepare.setInt(5, objPurchase.getAmount());

            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()) {
                objPurchase.setId(objResult.getInt(1));
            }
            objPrepare.close();
            JOptionPane.showMessageDialog(null, "purchase has been created successfully in DB");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding the purchase: " + e.getMessage());
            System.out.println(e);
        }

        configDB.closeConnection();
        return objPurchase;
    }

    @Override
    public List<Object> findAll() {
        List<Object> listPurchase = new ArrayList<>();
        Connection objConnection = configDB.openConnection();

        try {
            String sql = "SELECT * FROM purchases;";
            PreparedStatement objPrepareStatement = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepareStatement.executeQuery();

            while (objResult.next()) {
                Purchase objPurchase = new Purchase();

                objPurchase.setId(objResult.getInt("id_purchase"));
                objPurchase.setIdClient(objResult.getInt("id_client"));
                objPurchase.setIdProduct(objResult.getInt("id_product"));
                objPurchase.setPurchaseDate(objResult.getTimestamp("purchase_date"));
                objPurchase.setAmount(objResult.getInt("amount"));

                listPurchase.add(objPurchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data acquisition error: " + e.getMessage());
        }

        configDB.closeConnection();
        return listPurchase;
    }

    @Override
    public boolean delete(Object object) {
        Purchase objPurchase = (Purchase) object;
        boolean isDeleted = false;

        Connection objConnection = configDB.openConnection();

        try {
            String sql = "DELETE FROM purchases WHERE id_purchase=?";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setInt(1, objPurchase.getId());

            int totalAffectedRows = objPrepare.executeUpdate();
            if (totalAffectedRows > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "purchase deleted successfully");
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
        Purchase objPurchase = (Purchase) object;
        boolean isUpdated = false;

        try {
            String sql = "UPDATE purchases SET id_client=?, id_product=?,purchase_date=?,amount=? WHERE id_purchase=?";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            objPrepare.setInt(1, objPurchase.getIdClient());
            objPrepare.setInt(2, objPurchase.getIdProduct());
            objPrepare.setTimestamp(3, objPurchase.getPurchaseDate());
            objPrepare.setInt(4, objPurchase.getAmount());
            objPrepare.setInt(5, objPurchase.getId());


            int rowAffected = objPrepare.executeUpdate();
            if (rowAffected > 0) {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Purchase updated successfully");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating the purchase: " + e.getMessage());
        }

        configDB.closeConnection();
        return isUpdated;


    }



    public List<Object> getPurchaseByClient(Client client) {
        List<Object> clientPurchases = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = configDB.openConnection();
            String sql = "SELECT * FROM purchases WHERE id_client = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, client.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(resultSet.getInt("id_purchase"));
                purchase.setIdClient(resultSet.getInt("id_client"));
                purchase.setIdProduct(resultSet.getInt("id_product"));
                purchase.setPurchaseDate(resultSet.getTimestamp("purchase_date"));
                purchase.setAmount(resultSet.getInt("amount"));
                clientPurchases.add(purchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las compras del cliente: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error cerrando ResultSet: " + e.getMessage());
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error cerrando PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error cerrando Connection: " + e.getMessage());
                }
            }
        }

        return clientPurchases;
    }
}
