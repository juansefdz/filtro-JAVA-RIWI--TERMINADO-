package controller;

import entity.Client;
import model.clientModel;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class clientController {

    clientModel objClientModel;
    public clientController() {
        this.objClientModel = new clientModel();
    }

    //LISTO CLIENTES
    public static void getAll() {
        List<Object> allClients = instanceModel().findAll();
        if(allClients.isEmpty()){
            JOptionPane.showMessageDialog(null,"there are not clients avaliables");
        }else {
            String list = getAll(allClients);
            JOptionPane.showMessageDialog(null, list);
        }
    }

    public static String getAll(List<Object> list) {
        StringBuilder listString = new StringBuilder("LIST: \n");

        for (Object temp : list) {
            Client objClient = (Client) temp;
            listString.append(objClient.toString()).append("\n");
        }
        return listString.toString();
    }

    //METODO PARA INSERTAR
    public void insert (){

        //se piden los datos del cliente
        String nameClient = JOptionPane.showInputDialog(null, "Enter client name: ");
        String lastNameClient = JOptionPane.showInputDialog(null, "Enter the customer's last name: ");
        String emailClient = JOptionPane.showInputDialog(null, "Enter the customer's email: ");

        //se instancia cliente y agrego datos
        Client newClient = new Client();
        newClient.setName(nameClient);
        newClient.setLastName(lastNameClient);
        newClient.setEmail(emailClient);

        instanceModel().insert(newClient);
        JOptionPane.showMessageDialog(null,"Client added successfully!");

    }

    //METODO PARA ELIMINAR
    public void delete (){
        //listo los clientes disponibles
        List<Object> allClients = instanceModel().findAll();
        if (allClients.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no clients available to delete.");
            return;
        }
        Object[] options = Utils.listToArray(allClients);
        Client objClient = (Client) JOptionPane.showInputDialog(
                null,
                "Enter the ID of the client to delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        instanceModel().delete(objClient);

    }

    public void update (){
        //obtengo los clientes disponibles
        Object[] options = Utils.listToArray(instanceModel().findAll());

        //selecciono el medico a actualizar
        Client objClient = (Client) JOptionPane.showInputDialog(
                null,
                "Select the client to update",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (objClient != null) {
            //se pide la nueva informacion del cliente
            String newName = JOptionPane.showInputDialog(null, "Enter the new name of the client", objClient.getName());
            String newLastName = JOptionPane.showInputDialog(null, "Enter the new last name", objClient.getLastName());
            String newEmail = JOptionPane.showInputDialog(null, "Enter the new Email", objClient.getEmail());

            //se actualiza la nueva info
            boolean updated = instanceModel().update(objClient);
        } else {
            JOptionPane.showMessageDialog(null, "No client selected.");
        }

    }


    //METODOS ESPECIFICOS
    //BUSCAR POR NOMBRE
    public void getByName() {

        String clientName = JOptionPane.showInputDialog(null, " insert the name of the client you want to search ");


        List<Client> client = (List<Client>) this.objClientModel.findbyClient(clientName);
      

        if (clientName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Client not found");
        } else {


            StringBuilder showClients = new StringBuilder("CLIENTS FOUND BY NAME => " + clientName.toUpperCase() + "\n");
            for (Client clientTemp : client) {
                showClients.append("ID: ").append(clientTemp.getId()).append("\n");
                showClients.append("Name: ").append(clientTemp.getName()).append("\n");
                showClients.append("Last Name: ").append(clientTemp.getLastName()).append("\n");
                showClients.append("Email: ").append(clientTemp.getEmail()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, showClients.toString());
        }
    }

    public static clientModel instanceModel() {
        return new clientModel();
    }
}
