package sample.models.clases;

public class Payment {

    int id_payment;
    String type_pay;

    public Payment(int id_payment, String type_pay) {
        this.id_payment = id_payment;
        this.type_pay = type_pay;
    }

    public int getId_payment() {return id_payment; }

    public void setId_payment(int id_payment) {this.id_payment = id_payment; }

    public String getType_pay() {return type_pay; }

    public void setType_pay(String type_pay) {this.type_pay = type_pay; }

    @Override
    public String toString() {
        return "Payment{" +
                "id_payment=" + id_payment +
                ", type_pay='" + type_pay + '\'' +
                '}';
    }
}
