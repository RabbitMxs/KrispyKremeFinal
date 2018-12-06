package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.clases.Customer;
import sample.models.clases.Employee;
import sample.models.clases.Package;
import sample.models.clases.Product;

import java.sql.*;

public class Dao {
    Connection conn;

    private static ObservableList<Product> data = FXCollections.observableArrayList();

    public Dao() { this.conn = MySQL.getConnection(); }

    public static void addTransaction(Product product)
    {
        data.add(product);
    }

    public ObservableList<sample.models.clases.Product> findAllProduct() {
        ObservableList<sample.models.clases.Product> customers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM product";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.models.clases.Product p = null;
            while(rs.next()) {
                p = new Product(
                        rs.getInt("id_product"), rs.getString("name_pro"),
                        rs.getDouble("price_pro"), rs.getString("description_pro"),
                        rs.getInt("id_category")
                );
                customers.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return customers;
    }

    public ObservableList<sample.models.clases.Package> findAllPackage() {
        ObservableList<sample.models.clases.Package> customers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM package";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.models.clases.Package p = null;
            while(rs.next()) {
                p = new Package(
                        rs.getInt("id_package"), rs.getString("name_pa"),
                        rs.getString("description_pa"), rs.getDouble("price_pa")
                );
                customers.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return customers;
    }

    public Boolean insertInvoice(Employee employee, double subtotal, double total,int payment) {
        try {
            String query = "insert into invoice "
                    + " (subtotal, total, id_customer, id_payment, id_employee,payment_day)"
                    + " values (?, ?, ?, ?, ?,now())";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setDouble(1, subtotal);
            st.setDouble(  2, total);
            st.setNull(3, 0);
            st.setInt(4, payment);
            st.setInt(5, employee.getId_employee());
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public sample.models.clases.Employee fetchEployee(String user,String password) {
        ResultSet rs = null;
        sample.models.clases.Employee e = null;
        try {
            String query = "SELECT * FROM employee where name_u= '" + user+"' and password='"+password+"'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                e = new sample.models.clases.Employee(
                        rs.getInt("id_employee"),rs.getInt("id_permit"),
                        rs.getString("name_em"),rs.getString("last_em"),
                        rs.getString("name_u"),rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

    public sample.models.clases.Customer fetchCustomer(String phone) {
        ResultSet rs = null;
        sample.models.clases.Customer e = null;
        try {
            String query = "SELECT * FROM customer where phone='" + phone + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                e = new sample.models.clases.Customer(
                        rs.getInt("id_customer"),rs.getString("name_cu"),
                        rs.getString("last_cu"),rs.getString("e_mail"),
                        rs.getString("phone"),rs.getString("address"),
                        rs.getString("city"),rs.getString("state"),
                        rs.getString("cp")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

    public Boolean insertCustomer(sample.models.clases.Customer customer) {
        try {
            String query = "insert into customer "
                    + " (name_cu, last_cu, e_mail, phone, address, city, state, cp)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, customer.getName_cu());
            st.setString(2, customer.getLast_cu());
            st.setString(3, customer.getE_mail());
            st.setString(4, customer.getPhone());
            st.setString(5, customer.getAddress());
            st.setString(6,customer.getCity());
            st.setString(7,customer.getState());
            st.setString(8, customer.getCp());
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean insertInvoiceCliente(Employee employee, double subtotal, double total,int payment, int customer) {
        try {
            String query = "insert into invoice "
                    + " (subtotal, total, id_customer, id_payment, id_employee,payment_day)"
                    + " values (?, ?, ?, ?, ?,now())";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setDouble(1, subtotal);
            st.setDouble(2, total);
            st.setInt(3, customer);
            st.setInt(4, payment);
            st.setInt(5, employee.getId_employee());
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ObservableList<sample.models.clases.Customer> findAllCustomer() {
        ObservableList<sample.models.clases.Customer> customers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM customer";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.models.clases.Customer p = null;
            while(rs.next()) {
                p = new Customer(
                        rs.getInt("id_customer"),rs.getString("name_cu"),
                        rs.getString("last_cu"),rs.getString("e_mail"),
                        rs.getString("phone"),rs.getString("address"),
                        rs.getString("city"),rs.getString("state"),
                        rs.getString("cp")
                );
                customers.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return customers;
    }
}
