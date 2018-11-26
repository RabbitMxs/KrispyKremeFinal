package sample.models;

public class Details_invoice_product {
    int id_invoice,id_product;

    public Details_invoice_product(int id_invoice, int id_product) {
        this.id_invoice = id_invoice;
        this.id_product = id_product;
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    @Override
    public String toString() {
        return "Details_invoice_product{" +
                "id_invoice=" + id_invoice +
                ", id_product=" + id_product +
                '}';
    }
}
