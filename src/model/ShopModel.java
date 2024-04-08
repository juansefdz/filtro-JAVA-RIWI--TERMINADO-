package model;

import database.CRUD;
import database.configDB;
import entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopModel implements CRUD {


    @Override
    public List<Object> findAll() {
        Connection objConnection = configDB.openConnection();

        List<Object> ListShops = new ArrayList<>();

        try {
            String sql = "SELECT * FROM shops;";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);

            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()) {

                Shop objShop = new Shop();

                objShop.setId((objResult.getInt("shops.id_shop")));
                objShop.setNameShop(objResult.getString("shops.name"));
                objShop.setLocationShop(objResult.getString("shops.ubication"));


                ListShops.add(objShop);
            }
        } catch (
                SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        configDB.closeConnection();
        return ListShops;
    }

    @Override
    public Object insert(Object object) {
        return null;
    }

    @Override
    public boolean delete(Object object) {
        return false;
    }

    @Override
    public boolean update(Object object) {
        return false;
    }
}
