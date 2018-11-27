package sample.models.dao;

public class PackageDao {
    /*
    Connection conn;

    private static ObservableList<Product> data = FXCollections.observableArrayList();

    public PackageDao(Connection conn) { this.conn = conn; }

    public static void addTransaction(Product product)
    {
        data.add(product);
    }

    public ObservableList<sample.models.clases.Package> findAll() {
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


    public ObservableList<Customer> fetchAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM customer";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.models.Customer p = null;
            while(rs.next()) {
                p = new sample.models.Customer(
                        rs.getInt("id_customer"), rs.getInt("id_city"),
                        rs.getString("firts_name"), rs.getString("last_name"),
                        rs.getString("address"), rs.getString("codigo_p")
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

    public sample.models.Customer fetch(int trans_id) {
        ResultSet rs = null;
        sample.models.Customer e = null;
        try {
            String query = "SELECT * FROM customer where id_customer= " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()){
                City city=new City();
                city.setId_city(rs.getInt("id_city"));
                e = new sample.models.Customer(
                        rs.getInt("id_customer"),rs.getString("firts_name"),
                        rs.getString("last_name"),rs.getString("address"),
                        rs.getString("codigo_p"),city,rs.getDouble("saldo")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

    public Boolean delete(int trans_id) {
        try {
            String query = "delete from transaction where id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, trans_id);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insert(sample.models.Customer customer) {
        try {
            String query = "insert into customer "
                    + " (category, description, date_created, amount, type)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, customer.getCategory());
            st.setString(2, customer.getDescription());
            st.setDate(  3, customer.getDate_created());
            st.setDouble(4, customer.getAmount());
            st.setString(5, String.valueOf(customer.getType()));
            st.execute();
            //data.add(customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean update(sample.models.Customer customer) {
        try {
            String query = "update customer "
                    + " set category = ?, description = ?, date_created = ?, amount = ?, type = ?"
                    + " where id=?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setString(1, customer.getCategory());
            st.setString(2, customer.getDescription());
            st.setDate(  3, customer.getDate_created());
            st.setDouble(4, customer.getAmount());
            st.setString(5, String.valueOf(customer.getType()));
            st.setInt(6, customer.getId());
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }
    */
}
