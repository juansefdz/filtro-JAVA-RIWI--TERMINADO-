package model;

import database.CRUD;
import database.configDB;
import entity.Client;
import entity.Product;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ProductModel implements CRUD {
    @Override
    public  Object insert(Object object) {
        Connection objConnection = configDB.openConnection();

        Product objProduct = (Product) object;

        try {
            String sql = "INSERT INTO products (id_product, product_name, product_price, id_shop,stock) VALUES (?,?,?,?,?);";
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql, RETURN_GENERATED_KEYS);

            objPrepare.setInt(1, objProduct.getId());
            objPrepare.setString(2, objProduct.getProductName());
            objPrepare.setFloat(3, objProduct.getProductPrice());
            objPrepare.setInt(4, objProduct.getIdShop());
            objPrepare.setInt(5, objProduct.getStock());

            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()) {
                objProduct.setId(objResult.getInt(1));
            }
            objPrepare.close();
            JOptionPane.showMessageDialog(null, "product has been create successful in DB");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting product: " + e.getMessage());
        } finally {
            configDB.closeConnection();
        }

        return objProduct;
    }

    @Override
    public List<Object> findAll() {
        Connection objConnection = configDB.openConnection();

        List<Object> ListProducts = new ArrayList<>();

        try {
            String sql = "SELECT * FROM products\n" +
                    "INNER JOIN shops \n" +
                    "ON  shops.id_shop=products.id_shop;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);

            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()) {

                Product objProduct = new Product();

                objProduct.setId((objResult.getInt("products.id_product")));
                objProduct.setProductName(objResult.getString("products.product_name"));
                objProduct.setProductPrice(objResult.getFloat("products.product_price"));
                objProduct.setIdShop(objResult.getInt("products.id_shop"));
                objProduct.setStock(objResult.getInt("products.stock"));

                ListProducts.add(objProduct);
            }

        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        configDB.closeConnection();
        return ListProducts;
    }

    @Override
    public boolean delete(Object object) {
        Connection objConnection = configDB.openConnection();
        Product objAppointment = (Product) object;
        boolean isDeleted = false;

        try {
            String sql = "DELETE FROM products WHERE products.id_product=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);

            objPrepare.setInt(1, objAppointment.getId());
            int totalRowAffected = objPrepare.executeUpdate();

            if (totalRowAffected > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "register deleted successful");
            }
        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        }
        configDB.closeConnection();
        return isDeleted;
    }

    @Override
    public boolean update(Object object) {
        Connection objConnection = configDB.openConnection();
        Product objReservation = (Product) object;
        boolean isUpdated = false;
        try {
            String sql = "UPDATE products SET product_name=?, product_price=?,id_shop=?,stock=? WHERE id_product=?;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);


            objPrepare.setString(1, objReservation.getProductName());
            objPrepare.setFloat(2, objReservation.getProductPrice());
            objPrepare.setInt(3, objReservation.getIdShop());
            objPrepare.setInt(4, objReservation.getStock());

            objPrepare.setInt(5, objReservation.getId());


            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected > 0) {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "product updated successfully");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            configDB.closeConnection();
        }
        return isUpdated;
    }

    //ESPECIFICOS

    public static ProductModel instanceModel() {
        return new ProductModel();
    }

    public List<Product> findByProductId(int productId) {

        List<Product> reservations = new ArrayList<>();

        try {
            Connection connection = configDB.openConnection();
            String sql = "SELECT * FROM products WHERE id_product= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id_product"));
                product.setProductName(resultSet.getString("product_name"));
                product.setProductPrice(resultSet.getFloat("product_price"));
                product.setIdShop(resultSet.getInt("id_shop"));
                product.setStock(resultSet.getInt("stock"));
                reservations.add(product);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching products: " + e.getMessage());
        }
        return reservations;
    }

    public Object findbyProductName(String productName) {

        Connection objConnection = configDB.openConnection(); //open connection
        List<Product> productByName = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE products.product_name LIKE ?;"; //SQL sentence

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql); //prepareStatment
            objPrepare.setString(1, "%" + productName + "%"); //Preparestatment value

            ResultSet objResult = objPrepare.executeQuery(); //execute query
            while (objResult.next()) {
                Product productbyName = new Product();
                productbyName.setId(objResult.getInt("id"));
                productbyName.setProductName(objResult.getString("product_name")); //values to  query parameters
                productbyName.setProductPrice(objResult.getFloat("product_price")); //values to  query parameters
                productbyName.setStock(objResult.getInt("stock")); //values to  query parameters
                productbyName.setIdShop(objResult.getInt("id_shop")); //values to  query parameters

                productByName.add(productbyName);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        configDB.closeConnection(); //close connection
        return productByName;
    }

    public Object findProductByStore(String store) {

        Connection objConnection = configDB.openConnection(); //open connection
        List<Product> productByStore = new ArrayList<>();
        String sql = "SELECT * FROM stores WHERE products.product_name LIKE ?;"; //SQL sentence

        try {
            PreparedStatement objPrepare = objConnection.prepareStatement(sql); //prepareStatment
            objPrepare.setString(1, "%" + store + "%"); //Preparestatment value

            ResultSet objResult = objPrepare.executeQuery(); //execute query
            while (objResult.next()) {
                Product productbyName = new Product();
                productbyName.setId(objResult.getInt("id"));
                productbyName.setProductName(objResult.getString("product_name")); //values to  query parameters
                productbyName.setProductPrice(objResult.getFloat("product_price")); //values to  query parameters
                productbyName.setStock(objResult.getInt("stock")); //values to  query parameters
                productbyName.setIdShop(objResult.getInt("id_shop")); //values to  query parameters
                productByStore.add(productbyName);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        configDB.closeConnection(); //close connection
        return productByStore;
    }



    public int getProductStock(int idStock) {
        int stockProduct = -1; // Valor predeterminado en caso de que no se encuentre la capacidad del avión

        try {
            Connection connection = configDB.openConnection();
            String sql = "SELECT stock FROM products WHERE id_product = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idStock);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                stockProduct = resultSet.getInt("stock");
            } else {
                System.out.println("no stock found for the product " + idStock);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error when obtaining the stock of the product: " + e.getMessage());
        } finally {
            configDB.closeConnection();
        }

        return stockProduct;
    }


}
