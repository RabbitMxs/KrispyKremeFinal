package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
                    + " (subtotal, total, id_customer, id_payment, id_employee)"
                    + " values (?, ?, ?, ?, ?)";
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
}
