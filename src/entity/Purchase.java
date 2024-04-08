package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Purchase {

    private int id;
    private int idClient;
    private int idProduct;
    private Timestamp purchaseDate;
    private int amount;

    /*CONSTRUCTORS*/

    public Purchase() {
    }

    public Purchase(int id, int idClient, int idProduct, Timestamp purchaseDate, int amount) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
    }

    /*GET&SET*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



    /*ToString*/

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idProduct=" + idProduct +
                ", purchaseDate=" + purchaseDate +
                ", amount=" + amount +
                '}';
    }
}
