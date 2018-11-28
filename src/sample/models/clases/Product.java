package sample.models.clases;

import java.sql.Blob;

public class Product {

    int id_product;
    String name_pro;
    double price_pro;
    String description_pro;
    Blob image;
    int id_category;
    int cantidad;

    public Product(int id_product, String name_pro, double price_pro, String description_pro, int id_category) {
        this.id_product = id_product;
        this.name_pro = name_pro;
        this.price_pro = price_pro;
        this.description_pro = description_pro;
        this.id_category = id_category;
    }

    public int getId_product() { return id_product; }

    public void setId_product(int id_product) {this.id_product = id_product; }

    public String getName_pro() {return name_pro; }

    public void setName_pro(String name_pro) {this.name_pro = name_pro; }

    public double getPrice_pro() {return price_pro; }

    public void setPrice_pro(double price_pro) {this.price_pro = price_pro; }

    public String getDescription_pro() {return description_pro; }

    public void setDescription_pro(String description_pro) {this.description_pro = description_pro; }

    public Blob getImage() { return image; }

    public void setImage(Blob image) {this.image = image; }

    public int getId_category() {return id_category; }

    public void setId_category(int id_category) {this.id_category = id_category; }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", name_pro='" + name_pro + '\'' +
                ", price_pro=" + price_pro +
                ", cantidad=" + cantidad +
                '}';
    }
}
