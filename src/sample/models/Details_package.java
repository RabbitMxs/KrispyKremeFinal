package sample.models;

public class Details_package {
    int id_package,id_product;

    public Details_package(int id_package, int id_product) {
        this.id_package = id_package;
        this.id_product = id_product;
    }

    public int getId_package() {
        return id_package;
    }

    public void setId_package(int id_package) {
        this.id_package = id_package;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    @Override
    public String toString() {
        return "Details_package{" +
                "id_package=" + id_package +
                ", id_product=" + id_product +
                '}';
    }
}
