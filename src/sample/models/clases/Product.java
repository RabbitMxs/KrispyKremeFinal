package sample.models.clases;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Product {

    int id_product;
    String name_pro;
    double price_pro;
    String description_pro;
    //Blob image;
    int id_category;
    int cantidad;
    private Category Nombre_cat;

    public Product(int id_product, String name_pro, double price_pro, String description_pro, int id_category) {
        this.id_product = id_product;
        this.name_pro = name_pro;
        this.price_pro = price_pro;
        this.description_pro = description_pro;
        this.id_category = id_category;
    }

    public Product(int id_product, String name_pro, double price_pro, String description_pro, Category nombre_cat) {
        this.id_product = id_product;
        this.name_pro = name_pro;
        this.price_pro = price_pro;
        this.description_pro = description_pro;
        this.id_category = id_category;
        Nombre_cat = nombre_cat;
    }

    public Product(String name_pro, double price_pro) {
        this.name_pro = name_pro;
        this.price_pro = price_pro;
    }

    public int getId_product() { return id_product; }

    public void setId_product(int id_product) {this.id_product = id_product; }

    public String getName_pro() {return name_pro; }

    public void setName_pro(String name_pro) {this.name_pro = name_pro; }

    public double getPrice_pro() {return price_pro; }

    public void setPrice_pro(double price_pro) {this.price_pro = price_pro; }

    public String getDescription_pro() {return description_pro; }

    public void setDescription_pro(String description_pro) {this.description_pro = description_pro; }

    /*public Blob getImage() { return image; }

    public void setImage(Blob image) {this.image = image; }*/

    public Category getNombre_cat() {
        return Nombre_cat;
    }

    public void setNombre_cat(Category nombre_cat) {
        Nombre_cat = nombre_cat;
    }

    public int getId_category() {return id_category; }

    public void setId_category(int id_category) {this.id_category = id_category; }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", name_pro='" + name_pro + '\'' +
                ", price_pro=" + price_pro +
                ", cantidad=" + cantidad +
                '}';
    }

    public static void llenarInformacion(Connection conexion, ObservableList<Product> list)
    {
        try
        {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT " +
                    "A.id_product, " +
                    "A.name_pro, " +
                    "A.price_pro, " +
                    "A.description_pro, " +
                    "A.id_category, "+
                    "B.name " +
                    "FROM product A " +
                    "INNER JOIN category B " +
                    "ON (A.id_category = B.id_category) order by id_product"

            );
            while (resultado.next())
            {
                list.add(
                        new Product
                                (
                                        resultado.getInt("id_product"),
                                        resultado.getString("name_pro"),
                                        resultado.getDouble("price_pro"),
                                        resultado.getString("description_pro"),
                                        new Category(resultado.getInt("id_category"),resultado.getString("name"))
                                )
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("no c pude");
        }

    }

    public int InsertarProductos(Connection connection)
    {
        try
        {
            PreparedStatement instrucion =connection.prepareStatement("INSERT INTO product (name_pro,price_pro,description_pro,id_category)"
                    + "VALUES (?,?,?,?)");
            instrucion.setString(1,getName_pro());
            instrucion.setDouble(2,getPrice_pro());
            instrucion.setString(3,getDescription_pro());
            instrucion.setInt(4,Nombre_cat.getId_category());

            return instrucion.executeUpdate();

        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sugerencia");
            alert.setContentText("Asegurese que los datos ingresado sean correctos");
            alert.setHeaderText("Importante!!");
            alert.show();
            return 0;
        }


    }

    public int ActualizarRegistro(Connection connection)
    {
        try
        {
            PreparedStatement instruccion =
                    connection.prepareStatement(
                            "UPDATE product "+
                                    " SET  name_pro = ?, "+
                                    " price_pro = ?, "+
                                    " description_pro = ?, "+
                                    " id_category = ? "+
                                    " WHERE id_product = ?"
                    );
            instruccion.setString(1,getName_pro());
            instruccion.setDouble(2,getPrice_pro());
            instruccion.setString(3,getDescription_pro());
            instruccion.setInt(4,Nombre_cat.getId_category());
            instruccion.setInt(5,getId_product());

            return instruccion.executeUpdate();

        }
        catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sugerencia");
            alert.setContentText("Asegurese que los datos ingresado sean correctos");
            alert.setHeaderText("Importante!!");
            alert.show();
            return 0;

        }
    }

    public int EliminarRegistro(Connection connection)
    {
        try {
            PreparedStatement instruccion = connection.prepareStatement("DELETE FROM product " +
                    "where id_product = ? "
            );
            instruccion.setInt(1,getId_product());
            return instruccion.executeUpdate();
        }
        catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sugerencia");
            alert.setContentText("Asegurese que los datos ingresado sean correctos");
            alert.setHeaderText("Importante!!");
            alert.show();
            return 0;
        }

    }

}
