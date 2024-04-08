package entity;

public class Product {

    private int id;
    private String productName;
    private Float productPrice;
    private int stock;
    private int idShop;

    /*CONSTRUCTORS*/

    public Product() {
    }

    public Product(int id, String productName, Float productPrice, int stock, int idShop) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
        this.idShop = idShop;
    }

    /*GET&SET*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    /*ToSTRING*/

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", stock=" + stock +
                ", idShop=" + idShop +
                '}';
    }
}
