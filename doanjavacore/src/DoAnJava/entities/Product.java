package DoAnJava.entities;

public class Product {
    private static int autoId;
    private int id;
    private String name;
    private double price;
    private String description;
    private int quantity;
    private Enum status;

    public Product(String name, double price, String description, int quantity, Enum status) {
        this.id = ++autoId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
    }

    public Product(String name) {
        this.name = name;
    }

    public Product() {

    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        Product.autoId = autoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}