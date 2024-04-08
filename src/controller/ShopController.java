package controller;
import entity.Shop;
import model.ShopModel;
import javax.swing.*;
import java.util.List;

public class ShopController {

    public static void getAll(){
        ShopModel shopModel = instanceModel();
        List<Object> allshops = shopModel.findAll();
        if (allshops.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no shops available.");
        } else {
            String list = getAll(allshops);
            JOptionPane.showMessageDialog(null, list);
        }
    }
    public static String getAll(List<Object> list) {
        StringBuilder listString = new StringBuilder(" LIST: \n");

        for (Object temp : list) {
            Shop objShop = (Shop) temp;
            listString.append(objShop.toString()).append("\n");
        }
        return listString.toString();
    }

    public static ShopModel instanceModel() {
        return new ShopModel();
    }

}
