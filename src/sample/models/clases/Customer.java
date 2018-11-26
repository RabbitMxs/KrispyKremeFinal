package sample.models.clases;

public class Customer {

    int id_customer;
    String name_cu,last_cu, e_mail,phone,address,city,state,cp;

    public Customer(int id_customer, String name_cu, String last_cu, String e_mail, String phone, String address, String city, String state, String cp) {
        this.id_customer = id_customer;
        this.name_cu = name_cu;
        this.last_cu = last_cu;
        this.e_mail = e_mail;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.cp = cp;
    }

    public int getId_customer() {return id_customer; }

    public void setId_customer(int id_customer) { this.id_customer = id_customer; }

    public String getName_cu() { return name_cu; }

    public void setName_cu(String name_cu) { this.name_cu = name_cu; }

    public String getLast_cu() { return last_cu; }

    public void setLast_cu(String last_cu) { this.last_cu = last_cu;}

    public String getE_mail() { return e_mail; }

    public void setE_mail(String e_mail) { this.e_mail = e_mail; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) {this.state = state; }

    public String getCp() { return cp; }

    public void setCp(String cp) { this.cp = cp; }

    @Override
    public String toString() {
        return "Customer{" +
                "id_customer=" + id_customer +
                ", name_cu='" + name_cu + '\'' +
                ", last_cu='" + last_cu + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", cp='" + cp + '\'' +
                '}';
    }
}
