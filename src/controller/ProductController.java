package controller;


import entity.Product;
import entity.Shop;
import model.ProductModel;
import model.ShopModel;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private static final ProductModel productModel = new ProductModel();
    private static final ShopModel shopModel = new ShopModel();

    private ProductModel objProductModel;

    public ProductController() {
        this.objProductModel = new ProductModel();
    }


    /*               CRUD                */
    public static void getAll() {
        List<Object> products = productModel.findAll();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No products found.", "Products", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String listString = getAllAsString(products);
            JOptionPane.showMessageDialog(null, listString, "Products", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static String getAllAsString(List<Object> list) {
        StringBuilder listString = new StringBuilder("LIST OF RECORDS: \n");
        for (Object temp : list) {
            Product obj = (Product) temp;
            listString.append(obj.toString()).append("\n");
        }
        return listString.toString();
    }


    //INSERTAR

    public void insert() {

        List<Object> shops = ShopController.instanceModel().findAll();
        Object[] optionShop = Utils.listToArray(shops);


        Shop selectedShop = (Shop) JOptionPane.showInputDialog(
                null, "Select the shop:", "Shop Selection",
                JOptionPane.QUESTION_MESSAGE, null, optionShop, optionShop[0]);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        if (selectedShop != null) {

            String productName = JOptionPane.showInputDialog(null, "enter the product name: ");
            String productPrice = JOptionPane.showInputDialog(null, "enter price for the product " + productName + " : ");
            String productStock = String.valueOf(Integer.parseInt(JOptionPane.showInputDialog(null, "enter the stock of the product: " + productName + " :")));

            Product NewProduct = new Product();
            NewProduct.setProductName(productName);
            NewProduct.setProductPrice(Float.valueOf(productPrice));
            NewProduct.setStock(Integer.parseInt(productStock));
            NewProduct.setIdShop(selectedShop.getId());

            productModel.insert(NewProduct);
            JOptionPane.showMessageDialog(null, "product created successfully");


        } else {
            JOptionPane.showMessageDialog(null, "product creation cancelled.");

        }


    }

    public static ProductModel instanceModel() {
        return new ProductModel();
    }


    public void delete() {
        //listo los productos disponibles
        List<Object> allProducts = instanceModel().findAll();
        if (allProducts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no products available to delete.");
            return;
        }
        Object[] options = Utils.listToArray(allProducts);
        Product objProduct = (Product) JOptionPane.showInputDialog(
                null,
                "Enter the ID of the product to delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        instanceModel().delete(objProduct);
    }

    public void update() {
        //obtengo los clientes disponibles
        Object[] options = Utils.listToArray(instanceModel().findAll());

        //selecciono el producto a actualizar
        Product objProduct = (Product) JOptionPane.showInputDialog(
                null,
                "Select the product to update",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (objProduct != null) {

            String newName = JOptionPane.showInputDialog(null, "Enter the new name of the product", objProduct.getProductName());
            float newPrice = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the new last name", objProduct.getProductPrice()));


            //se actualiza la nueva info
            boolean updated = instanceModel().update(objProduct);
        } else {
            JOptionPane.showMessageDialog(null, "No product selected.");
        }
    }


    /*              SPECIFIC        */

    public List<String> getAvailableShops(Product product) {
        int productStock = getProductStock(product.getStock());

        if (productStock == -1) {
            System.out.println("The stock of the product associated with the product was not found.");
            return new ArrayList<>();
        }

        List<String> allSeats = new ArrayList<>();
        for (int i = 1; i <= productStock; i++) {
            allSeats.add(String.valueOf(i));
        }

        List<Product> productInfo = productModel.instanceModel().findByProductId(productStock);
        for (Product productTemp : productInfo) {
            allSeats.remove(productTemp.getStock());
        }

        return allSeats;
    }

    public int getProductStock(int stock) {
        return ProductModel.instanceModel().getProductStock(stock);
    }

    public void getByProductName() {

        String productName = JOptionPane.showInputDialog(null, " insert the name of the product you want to search ");


        List<Product> product = (List<Product>) this.objProductModel.findbyProductName(productName);
        if (product.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Client not found");
        } else {


            StringBuilder showProducts = new StringBuilder("CLIENTS FOUND BY NAME => " + productName.toUpperCase() + "\n");
            for (Product productTemp : product) {
                showProducts.append("ID: ").append(productTemp.getId()).append("\n");
                showProducts.append("Name: ").append(productTemp.getProductName()).append("\n");
                showProducts.append("Price: ").append(productTemp.getProductPrice()).append("\n");
                showProducts.append("Stock: ").append(productTemp.getStock()).append("\n");
                showProducts.append("ID shop: ").append(productTemp.getIdShop()).append("\n\n");
                ;
            }
            JOptionPane.showMessageDialog(null, showProducts.toString());
        }
    }

    public void getByProductByStore() {

        String StoreName = JOptionPane.showInputDialog(null, " insert the name of the store to search the product: ");

        List<Shop> product = (List<Shop>) this.objProductModel.findbyProductName(StoreName);
        if (product.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product in store not found");
        } else {


            StringBuilder ShowStore = new StringBuilder("PRODUCTS FOUND BY STORE => " + StoreName.toUpperCase() + "\n");
            for (Shop storeTemp : product) {
                ShowStore.append("ID: ").append(storeTemp.getId()).append("\n");
                ShowStore.append("Name: ").append(storeTemp.getNameShop()).append("\n");
                ShowStore.append("Price: ").append(storeTemp.getLocationShop()).append("\n\n");

            }
            JOptionPane.showMessageDialog(null, ShowStore.toString());
        }
    }


}


