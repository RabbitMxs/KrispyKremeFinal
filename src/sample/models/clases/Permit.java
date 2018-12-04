package sample.models.clases;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Permit {

    int id_permit;
    String type;

    public Permit(int id_permit, String type) {
        this.id_permit = id_permit;
        this.type = type;
    }

    public int getId_permit() { return id_permit; }

    public void setId_permit(int id_permit) {this.id_permit = id_permit; }

    public String getType() { return type; }

    public void setType(String type) {this.type = type; }

    public static void llenarComboPermisos(Connection connection, ObservableList<Permit> list)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultado  = statement.executeQuery("SELECT id_permit,type FROM permit");
            while (resultado.next())
            {
                list.add(new Permit
                        (
                                //Debe coincidir con el nombre de la consulta
                                resultado.getInt("id_permit"),
                                resultado.getString("type")
                        ));
            }
        }
        catch (SQLException e)
        {

        }

    }

    public static void llenarInformacion(Connection conexion, ObservableList<Permit> list)
    {
        try
        {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT * FROM permit"
            );
            while (resultado.next())
            {
                list.add(
                        new Permit
                                (
                                        resultado.getInt("id_permit"),
                                        resultado.getString("type")
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
            PreparedStatement instrucion =connection.prepareStatement("INSERT INTO permit (id_permit,type)"
                    + "VALUES (?,?)");
            instrucion.setInt(1,getId_permit());
            instrucion.setString(2,getType());

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
                            "UPDATE permit "+
                                    " SET type = ? "+
                                    " WHERE id_permit = ?");
            instruccion.setString(1,getType());
            instruccion.setDouble(2,getId_permit());
            return instruccion.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println(e);
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sugerencia");
            alert.setContentText("Asegurese que los datos ingresado sean correctos");
            alert.setHeaderText("Importante!!");
            alert.show();*/
            return 0;

        }
    }

    public int EliminarRegistro(Connection connection)
    {
        try {
            PreparedStatement instruccion = connection.prepareStatement("DELETE FROM permit " +
                    "where id_permit = ? "
            );
            instruccion.setInt(1,getId_permit());
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

    @Override
    public String toString() {
        return getType() +" ("+getId_permit()+")";
    }
}
