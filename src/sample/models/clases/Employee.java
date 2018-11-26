package sample.models.clases;

public class Employee {

    int id_employee,id_permit;
    String name_em,last_em,name_u,password;

    public Employee(int id_employee, int id_permit, String name_em, String last_em, String name_u, String password) {
        this.id_employee = id_employee;
        this.id_permit = id_permit;
        this.name_em = name_em;
        this.last_em = last_em;
        this.name_u = name_u;
        this.password = password;
    }

    public int getId_employee() { return id_employee; }

    public void setId_employee(int id_employee) { this.id_employee = id_employee; }

    public int getId_permit() {return id_permit; }

    public void setId_permit(int id_permit) { this.id_permit = id_permit; }

    public String getName_em() { return name_em; }

    public void setName_em(String name_em) { this.name_em = name_em; }

    public String getLast_em() { return last_em; }

    public void setLast_em(String last_em) { this.last_em = last_em; }

    public String getName_u() { return name_u; }

    public void setName_u(String name_u) { this.name_u = name_u; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Employee{" +
                "id_employee=" + id_employee +
                ", id_permit=" + id_permit +
                ", name_em='" + name_em + '\'' +
                ", last_em='" + last_em + '\'' +
                ", name_u='" + name_u + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
