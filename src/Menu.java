import controller.ProductController;
import controller.PurchaseController;
import controller.clientController;

import javax.swing.*;

public class Menu {

    public void menu() {

        String[] mainOptions = {"CLIENTS", "PRODUCTS", "PURCHASES", "EXIT"};
        int mainOption = 0;

        clientController objClientController = new clientController();
        ProductController objProductController = new ProductController();
        PurchaseController objPurchaseController = new PurchaseController();

        do {
            mainOption = JOptionPane.showOptionDialog(null, "Choose one option: ", "OUTLET MODA SYSTEM MENU", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, mainOptions, mainOptions[0]);

            switch (mainOption) {
                case 0: //clients
                    String optionsClients;
                    do {
                        String[] optionsMenu = {"Show all Clients", "Register new client", "Delete specific client", "update specific client", "search client by name", "Back"};
                        optionsClients = (String) JOptionPane.showInputDialog(null, "Select an option: ", "Clients Section", JOptionPane.QUESTION_MESSAGE, null, optionsMenu, optionsMenu[0]);
                        if (optionsClients != null) {
                            switch (optionsClients) {
                                case "Show all Clients": //mostrar clientes
                                    objClientController.getAll();
                                    break;
                                case "Register new client"://insertar clientes
                                    objClientController.insert();
                                    break;
                                case "Delete specific client"://borrar cliente especifico
                                    objClientController.delete();
                                    break;
                                case "update specific client"://actualizar cliente
                                    objClientController.update();
                                    break;
                                case "search client by name":
                                    objClientController.getByName();//buscar por nombre
                                    break;
                                case "Back":
                                    break;
                            }
                        }
                    } while (!"Back".equals(optionsClients));
                    break;
                case 1://products
                    String optionsProducts;
                    do {
                        String[] optionsMenu = {"Show all products", "create new product", "Delete specific product", "update specific product", "search product by name", "search product by store", "Back"};
                        optionsProducts = (String) JOptionPane.showInputDialog(null, "Select an option: ", "Products Section", JOptionPane.QUESTION_MESSAGE, null, optionsMenu, optionsMenu[0]);
                        if (optionsProducts != null) {
                            switch (optionsProducts) {
                                case "Show all products": //mostrar productos
                                    ProductController.getAll();
                                    break;
                                case "create new product"://insertar productos
                                    objProductController.insert();
                                    break;
                                case "Delete specific product"://borrar producto especifico
                                    objProductController.delete();
                                    break;
                                case "update specific product"://actualizar producto
                                    objProductController.update();
                                    break;
                                case "search product by name"://buscar por nombre de producto
                                    objProductController.getByProductName();
                                    break;
                                case "search product by store"://buscar por tienda
                                    objProductController.getByProductByStore();
                                    break;
                                case "Back":
                                    break;
                            }
                        }
                    } while (!"Back".equals(optionsProducts));
                    break;
                case 2:
                    String optionsPurchases;
                    do {
                        String[] optionsMenu = {"Show all purchase", "realizate new purchase", "Delete purchase","Update purchase", "Print bill", "Back"};
                        optionsPurchases = (String) JOptionPane.showInputDialog(null, "Select an option: ", "Purchases Section", JOptionPane.QUESTION_MESSAGE, null, optionsMenu, optionsMenu[0]);
                        if (optionsPurchases != null) {
                            switch (optionsPurchases) {
                                case "Show all purchase": //mostrar compras
                                    PurchaseController.getAll();
                                    break;
                                case "realizate new purchase"://realizar nueva compra
                                    objPurchaseController.insert();
                                    break;
                                case "Delete purchase"://borrar compra
                                    objPurchaseController.delete();
                                    break;
                                case "Update purchase"://borrar compra
                                    objPurchaseController.update();
                                case "Print bill"://imprimir factura
                                    objPurchaseController.printBill();
                                    break;
                                case "Back":
                                    break;
                            }
                        }
                    } while (!"Back".equals(optionsPurchases));//purchases
                    break;
            }

        } while (mainOption != 3);
    }
}
