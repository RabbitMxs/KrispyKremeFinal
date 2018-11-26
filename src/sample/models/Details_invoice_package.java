package sample.models;

public class Details_invoice_package {

    int id_invoice,id_package;

    public Details_invoice_package(int id_invoice, int id_package) {
        this.id_invoice = id_invoice;
        this.id_package = id_package;
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public int getId_package() {
        return id_package;
    }

    public void setId_package(int id_package) {
        this.id_package = id_package;
    }

    @Override
    public String toString() {
        return "Details_invoice_package{" +
                "id_invoice=" + id_invoice +
                ", id_package=" + id_package +
                '}';
    }
}
