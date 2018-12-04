package sample.models.clases;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Employee {

    int id_employee,id_permit;
    private Permit Permiso;
    String name_em,last_em,name_u,password;

    public Employee(int id_employee, String name_em, String last_em,  String name_u, String password, Permit permiso) {
        this.id_employee = id_employee;
        this.name_em = name_em;
        this.last_em = last_em;
        this.name_u = name_u;
        this.password = password;
        Permiso = permiso;

    }

    public Employee(int id_employee, int id_permit, String name_em, String last_em, String name_u, String password) {
        this.id_employee = id_employee;
        this.id_permit = id_permit;
        this.name_em = name_em;
        this.last_em = last_em;
        this.name_u = name_u;
        this.password = password;
    }

    public Employee(Permit permiso) {
        Permiso = permiso;
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

    public Permit getPermiso() {
        return Permiso;
    }

    public void setPermiso(Permit permiso) {
        Permiso = permiso;
    }

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

    public static void llenarInformacion(Connection conexion, ObservableList<Employee> list)
    {
        try
        {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT " +
                    "A.id_employee, " +
                    "A.name_em, " +
                    "A.last_em, " +
                    "A.name_u, " +
                    "A.password, "+
                    "A.id_permit, "+
                    "B.type " +
                    "FROM employee A " +
                    "INNER JOIN permit B " +
                    "ON (A.id_permit = B.id_permit) order by id_employee"

            );
            while (resultado.next())
            {
                list.add(
                        new Employee
                                (
                                        resultado.getInt("id_employee"),
                                        resultado.getString("name_em"),
                                        resultado.getString("last_em"),
                                        resultado.getString("name_u"),
                                        resultado.getString("password"),
                                        new Permit(resultado.getInt("id_permit"),resultado.getString("type"))
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

    public int InsertarEmpleado(Connection connection)
    {
        try
        {
            PreparedStatement instrucion =connection.prepareStatement("INSERT INTO employee (name_em,last_em,name_u,password,id_permit)"
                    + "VALUES (?,?,?,?,?)");
            instrucion.setString(1,getName_em());
            instrucion.setString(2,getLast_em());
            instrucion.setString(3,getName_u());
            instrucion.setString(4,getPassword());
            instrucion.setInt(5,Permiso.getId_permit());

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

    public int ActualizarEmpleado(Connection connection)
    {
        try
        {
            PreparedStatement instruccion =
                    connection.prepareStatement(
                            "UPDATE employee "+
                                    " SET  name_em = ?, "+
                                    " last_em = ?, "+
                                    " name_u = ?, "+
                                    " password = ?, "+
                                    " id_permit = ? "+
                                    " WHERE id_employee = ?"
                    );
            instruccion.setString(1,getName_em());
            instruccion.setString(2,getLast_em());
            instruccion.setString(3,getName_u());
            instruccion.setString(4,getPassword());
            instruccion.setInt(5,Permiso.getId_permit());
            instruccion.setInt(6,getId_employee());

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
            PreparedStatement instruccion = connection.prepareStatement("DELETE FROM employee " +
                    "where id_employee = ? "
            );
            instruccion.setInt(1,getId_employee());
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
