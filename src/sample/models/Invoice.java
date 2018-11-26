package sample.models;

public class Invoice {

    int id_invoice;
    double subtotal, total;
    int id_customer, id_payment,id_employee;

    public Invoice(int id_invoice, double subtotal, double total, int id_customer, int id_payment, int id_employee) {
        this.id_invoice = id_invoice;
        this.subtotal = subtotal;
        this.total = total;
        this.id_customer = id_customer;
        this.id_payment = id_payment;
        this.id_employee = id_employee;
    }

    public int getId_invoice() { return id_invoice; }

    public void setId_invoice(int id_invoice) {this.id_invoice = id_invoice; }

    public double getSubtotal() {return subtotal; }

    public void setSubtotal(double subtotal) {this.subtotal = subtotal; }

    public double getTotal() {return total; }

    public void setTotal(double total) {this.total = total; }

    public int getId_customer() {return id_customer; }

    public void setId_customer(int id_customer) {this.id_customer = id_customer; }

    public int getId_payment() {return id_payment; }

    public void setId_payment(int id_payment) {this.id_payment = id_payment; }

    public int getId_employee() {return id_employee; }

    public void setId_employee(int id_employee) {this.id_employee = id_employee; }

    @Override
    public String toString() {
        return "Invoice{" +
                "id_invoice=" + id_invoice +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", id_customer=" + id_customer +
                ", id_payment=" + id_payment +
                ", id_employee=" + id_employee +
                '}';
    }
}
