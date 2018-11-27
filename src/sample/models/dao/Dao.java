package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.clases.Package;
import sample.models.clases.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
