package controller;

import entity.Client;
import entity.Product;
import entity.Purchase;
import entity.Shop;
import model.ProductModel;
import model.purchaseModel;
import utils.Utils;

import javax.swing.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;

public class PurchaseController {

    purchaseModel objPurchaseModel;

    public PurchaseController() {
        this.objPurchaseModel = new purchaseModel();
    }

    public static purchaseModel instanceModel() {
        return new purchaseModel();
    }

    public static void getAll() {
        List<Object> allPurchases = instanceModel().findAll();
        if (allPurchases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "there are not purchases avaliables");
        } else {
            String list = getAll(allPurchases);
            JOptionPane.showMessageDialog(null, list);
        }
    }

    public static String getAll(List<Object> list) {
        StringBuilder listString = new StringBuilder("LIST: \n");

        for (Object temp : list) {
            Purchase objPurchases = (Purchase) temp;
            listString.append(objPurchases.toString()).append("\n");
        }
        return listString.toString();
    }


    public void insert() {
        int amountProduct = 0;
        List<Object> shops = ShopController.instanceModel().findAll();
        List<Object> products = ProductController.instanceModel().findAll();
        List<Object> clients = clientController.instanceModel().findAll();

        Object[] shopOptions = Utils.listToArray(shops);
        Object[] productOptions = Utils.listToArray(products);
        Object[] clientOptions = Utils.listToArray(clients);

        Shop selectedShop = (Shop) JOptionPane.showInputDialog(
                null, "Select the Shop:", "Shop Selection",
                JOptionPane.QUESTION_MESSAGE, null, shopOptions, shopOptions[0]);

        Product selectedProduct = (Product) JOptionPane.showInputDialog(
                null, "Select the product to buy:", "Product Selection",
                JOptionPane.QUESTION_MESSAGE, null, productOptions, productOptions[0]);

        Client selectedClient = (Client) JOptionPane.showInputDialog(
                null, "Select the customer who is going to make the purchase:", "Client Selection",
                JOptionPane.QUESTION_MESSAGE, null, clientOptions, clientOptions[0]);

        if (selectedShop == null || selectedProduct == null || selectedClient == null) {
            JOptionPane.showMessageDialog(null, "Please select valid options for shop, product, and client.");
            return;
        }

        do {
            amountProduct = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the quantity of product you want to buy"));
        } while (amountProduct > selectedProduct.getStock());

        Purchase newPurchase = new Purchase();
        newPurchase.setId(newPurchase.getId()); // Assuming ID will be auto-generated
        newPurchase.setIdClient(selectedClient.getId());
        newPurchase.setIdProduct(selectedProduct.getId());
        newPurchase.setAmount(amountProduct);
        Timestamp purcharseDate = new Timestamp(System.currentTimeMillis());
        newPurchase.setPurchaseDate(purcharseDate);


        instanceModel().insert(newPurchase);
        JOptionPane.showMessageDialog(null, "Purchase successful!");
    }

    public List<String> getAvaliableStock(Product product) {
        int productstock = getProductStock(product.getStock());

        if (productstock == -1) {
            System.out.println("The capacity of the stock associated with the product was not found.");
            return new ArrayList<>();
        }

        List<String> allProducts = new ArrayList<>();
        for (int i = 1; i <= productstock; i++) {
            allProducts.add(String.valueOf(i));
        }


        return allProducts;
    }

    public int getProductStock(int stock) {
        return ProductModel.instanceModel().getProductStock(stock);
    }

    //METODO PARA ELIMINAR
    public void delete() {
        List<Object> allPurchases = instanceModel().findAll();
        if (allPurchases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no purchases available to delete.");
            return;
        }

        Object[] options = Utils.listToArray(allPurchases);
        Purchase selectedPurchase = (Purchase) JOptionPane.showInputDialog(
                null,
                "Select the purchase to delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        boolean deleted = instanceModel().delete(selectedPurchase);
        if (deleted) {
            JOptionPane.showMessageDialog(null, "Purchase deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete purchase.");
        }
    }

    public void update() {
        List<Object> allPurchases = instanceModel().findAll();
        if (allPurchases.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no purchases available to update.");
            return;
        }

        Object[] options = Utils.listToArray(allPurchases);
        Purchase selectedPurchase = (Purchase) JOptionPane.showInputDialog(
                null,
                "Select the purchase to update",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selectedPurchase != null) {
            int newAmount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the new amount:", selectedPurchase.getAmount()));

            selectedPurchase.setAmount(newAmount);
            boolean updated = instanceModel().update(selectedPurchase);
            if (updated) {
                JOptionPane.showMessageDialog(null, "Purchase updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update purchase.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No purchase selected.");
        }
    }

    public void printBill() {

        StringBuilder bill = new StringBuilder("BILL:\n");
        bill.append("------------------------------------------\n");

        // Obtener todas las compras
        List<Object> allPurchases = instanceModel().findAll();

        // Verificar si hay al menos una compra realizada
        if (!allPurchases.isEmpty()) {
            // Convertir la lista de compras en un arreglo para mostrar en el cuadro de diálogo
            Object[] purchaseOptions = Utils.listToArray(allPurchases);

            // Mostrar cuadro de diálogo para seleccionar una compra
            Purchase selectedPurchase = (Purchase) JOptionPane.showInputDialog(
                    null, "Select the purchase to print the bill for:", "Purchase Selection",
                    JOptionPane.QUESTION_MESSAGE, null, purchaseOptions, purchaseOptions[0]);

            // Verificar si se seleccionó una compra
            if (selectedPurchase != null) {
                // Obtener el cliente asociado a la compra seleccionada
                Client client = getClientById(selectedPurchase.getIdClient());

                // Verificar si se encontró el cliente asociado a la compra
                if (client != null) {
                    bill.append("CLIENT: ").append(client.getName()).append(" ").append(client.getLastName()).append("\n");
                    bill.append("------------------------------------------\n");
                    bill.append("PRODUCTS:\n");
                    bill.append("------------------------------------------\n");

                    // Obtener el producto asociado a la compra
                    Product product = getProductById(selectedPurchase.getIdProduct());

                    // Calcular el valor total sin IVA
                    double subtotal = product.getProductPrice() * selectedPurchase.getAmount();
                    // Calcular el valor del IVA (19%)
                    double iva = subtotal * 0.19;
                    // Calcular el valor total con IVA
                    double total = subtotal + iva;

                    // Mostrar detalles de la compra seleccionada
                    bill.append("Product: ").append(selectedPurchase.getId()).append("\n");
                    bill.append("Amount: ").append(selectedPurchase.getAmount()).append("\n");
                    bill.append("Unitary Price: ").append(product.getProductPrice()).append("\n");
                    bill.append("Subtotal: ").append(subtotal).append("\n");
                    bill.append("IVA (19%): ").append(iva).append("\n");
                    bill.append("------------------------------------------\n");
                    bill.append("\n\nTotal with IVA:      ").append(total).append("\n");
                    bill.append("------------------------------------------\n");

                    // Mostrar confirmación para imprimir la factura
                    int option = JOptionPane.showConfirmDialog(null, "Do you want to print the bill?", "Print Bill", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        // Imprimir la factura
                        JOptionPane.showMessageDialog(null, bill.toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el cliente asociado a la compra seleccionada.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No purchase selected.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay compras realizadas.");
        }
    }

    // Método para obtener el cliente por ID
    private Client getClientById(int clientId) {
        List<Object> clients = clientController.instanceModel().findAll();
        for (Object obj : clients) {
            Client client = (Client) obj;
            if (client.getId() == clientId) {
                return client;
            }
        }
        return null;
    }

    // Método para obtener el producto por ID
    private Product getProductById(int productId) {
        List<Object> products = ProductController.instanceModel().findAll();
        for (Object obj : products) {
            Product product = (Product) obj;
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }


}
