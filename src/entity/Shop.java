package entity;

public class Shop {

    private int id;
    private String nameShop;
    private String locationShop;

    /*CONSTRUCTORS*/
    public Shop() {
    }

    public Shop(int id, String nameShop, String locationShop) {
        this.id = id;
        this.nameShop = nameShop;
        this.locationShop = locationShop;
    }

    /*GET&SET*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getLocationShop() {
        return locationShop;
    }

    public void setLocationShop(String locationShop) {
        this.locationShop = locationShop;
    }

    /*ToSTRING*/

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", nameShop='" + nameShop + '\'' +
                ", locationShop='" + locationShop + '\'' +
                '}';
    }
}
