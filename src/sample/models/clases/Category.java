package sample.models.clases;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Category {
    int id_category;
    String name;

    public Category(int id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_category() { return id_category; }

    public void setId_category(int id_category) { this.id_category = id_category; }

    public static void llenarComboCat(Connection connection, ObservableList<Category> list)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultado  = statement.executeQuery("SELECT name FROM category");
            while (resultado.next())
            {
                list.add(new Category
                        (
                                //Debe coincidir con el nombre de la consulta
                                resultado.getString("name")
                        ));
            }
        }
        catch (SQLException e)
        {

        }

    }

    public static void llenarInformacion(Connection conexion, ObservableList<Category> list)
    {
        try
        {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT * FROM category"
            );
            while (resultado.next())
            {
                list.add(
                        new Category
                                (
                                        resultado.getInt("id_category"),
                                        resultado.getString("name")
                                )
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println("no c pude");
        }

    }

    public int InsertarCategoria(Connection connection)
    {
        try
        {
            PreparedStatement instrucion =connection.prepareStatement("INSERT INTO category (id_category,name)"
                    + "VALUES (?,?)");
            instrucion.setInt(1,getId_category());
            instrucion.setString(2,getName());

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
                            "UPDATE category "+
                                    " SET name = ? "+
                                    " WHERE id_category = ?");
            instruccion.setString(1,getName());
            instruccion.setDouble(2,getId_category());
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
            PreparedStatement instruccion = connection.prepareStatement("DELETE FROM category " +
                    "where id_category = ? "
            );
            instruccion.setInt(1,getId_category());
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



    public String toString()
    {

        return getName();
    }
}
