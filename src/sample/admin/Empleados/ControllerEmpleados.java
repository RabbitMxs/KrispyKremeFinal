package sample.admin.Empleados;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import sample.models.clases.Employee;
import sample.models.clases.Permit;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEmpleados implements Initializable {
    @FXML
    private TextField TxtNombre;
    @FXML
    private TextField TxtId;
    @FXML
    private TextField TxtApellidos;
    @FXML
    private TextField TxtUsuario;
    @FXML
    private TextField TxtContraseña;
    @FXML
    private ComboBox<Permit> CbxPermisos;

    @FXML
    private TableView<Employee> TvEmployee;
    @FXML
    private TableColumn<Employee, Number> ClId;
    @FXML
    private TableColumn<Employee, String> ClNombre;
    @FXML
    private TableColumn<Employee, String> ClApellidos;
    @FXML
    private TableColumn<Employee, String> ClUsuario;
    @FXML
    private TableColumn<Employee, String> ClContraseña;
    @FXML
    private TableColumn<Employee, Permit> ClPermisos;

    @FXML
    private Button BtnAgregar;
    @FXML
    private Button BtnActualizar;
    @FXML
    private Button BtnEliminar;

    @FXML
    Circle image;

    private ObservableList<Employee> ListaEmpleados;
    private ObservableList<Permit> ListaPermisos;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));

        MySQL.Connect();
        LlenarTableview();
        llenarCombo();
        Seleccionado();
        MySQL.Disconnect();

    }


    public void llenarCombo()
    {
        try
        {
            ListaPermisos = FXCollections.observableArrayList();
            Permit.llenarComboPermisos(MySQL.getConnection(), ListaPermisos);
            CbxPermisos.setItems(ListaPermisos);
        }
        catch (Exception a)
        {
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setHeaderText("Resultado");
            mensaje.setTitle("Error");
            mensaje.setContentText("Algo salio mal Vuelva a intentarlo");
            mensaje.show();
        }
    }

    public void LlenarTableview()
    {
        try {
            ListaEmpleados = FXCollections.observableArrayList();
            Employee.llenarInformacion(MySQL.getConnection(), ListaEmpleados);
            TvEmployee.setItems(ListaEmpleados);

            ClId.setCellValueFactory(new PropertyValueFactory<Employee, Number>("id_employee"));
            ClNombre.setCellValueFactory(new PropertyValueFactory<Employee, String>("name_em"));
            ClApellidos.setCellValueFactory(new PropertyValueFactory<Employee, String>("last_em"));
            ClUsuario.setCellValueFactory(new PropertyValueFactory<Employee, String>("name_u"));
            ClContraseña.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
            ClPermisos.setCellValueFactory(new PropertyValueFactory<Employee, Permit>("Permiso"));
        }
        catch (Exception a)
        {
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setHeaderText("Resultado");
            mensaje.setTitle("Error");
            mensaje.setContentText("Algo salio mal Vuelva a intentarlo");
            mensaje.show();
        }
    }

    public void Seleccionado() {
        TvEmployee.getSelectionModel().selectedItemProperty().addListener
                (new ChangeListener<Employee>() {
                    @Override
                    public void changed(ObservableValue<? extends Employee> observable,
                                        Employee ValorAnterior, Employee ValorSeleccionado) {

                        if (ValorSeleccionado != null) {
                            TxtId.setText(String.valueOf(ValorSeleccionado.getId_employee()));
                            TxtNombre.setText(ValorSeleccionado.getName_em());
                            TxtApellidos.setText(ValorSeleccionado.getLast_em());
                            TxtUsuario.setText(ValorSeleccionado.getName_u());
                            TxtContraseña.setText(ValorSeleccionado.getPassword());
                            CbxPermisos.setValue(ValorSeleccionado.getPermiso());

                            BtnEliminar.setDisable(false);
                            BtnActualizar.setDisable(false);
                        }

                    }
                });
    }

    public void limpiar()
    {
        TxtId.clear();
        TxtNombre.clear();
        TxtApellidos.clear();
        TxtUsuario.clear();
        TxtContraseña.clear();
        CbxPermisos.setValue(null);
    }

    @FXML
    public void Guardar()
    {
        //Crear una nueva instantiancia del tipo Donas y luego guardar
        Employee empleado = new Employee(0,
                TxtNombre.getText(),
                TxtApellidos.getText(),
                TxtUsuario.getText(),
                TxtContraseña.getText(),
                CbxPermisos.getSelectionModel().getSelectedItem()
        );

        //LLamamos el metodo
        MySQL.Connect();
        int resultado = empleado.InsertarEmpleado(MySQL.getConnection());
        MySQL.Disconnect();
        if(resultado==1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Agregado");
            alert.setContentText("Se guardo exitosamente");
            alert.setHeaderText("Resultado");
            alert.show();
            LlenarTableview();
            limpiar();
        }
    }

    @FXML
    public void ActualizarRegistro()
    {
        try {
            Employee a = new Employee(
                    Integer.valueOf(TxtId.getText()),
                    TxtNombre.getText(),
                    TxtApellidos.getText(),
                    TxtUsuario.getText(),
                    TxtContraseña.getText(),
                    CbxPermisos.getSelectionModel().getSelectedItem()
            );
            MySQL.Connect();
            int resultado = a.ActualizarEmpleado(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                ListaEmpleados.set(TvEmployee.getSelectionModel().getSelectedIndex(), a);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setHeaderText("Resultado");
                mensaje.setTitle("Registro Actualizado");
                mensaje.setContentText("El Registro se ha Actualizado Exitosamente");
                mensaje.show();
                limpiar();
            }
        }
        catch (Exception a)
        {
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setHeaderText("Resultado");
            mensaje.setTitle("Error");
            mensaje.setContentText("Algo salio mal Vuelva a intentarlo");
            mensaje.show();
        }
    }

    @FXML
    public void Eliminar()
    {
        try {
            MySQL.Connect();
            int resultado = TvEmployee.getSelectionModel().getSelectedItem().EliminarRegistro(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                ListaEmpleados.remove(TvEmployee.getSelectionModel().getSelectedIndex());
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setHeaderText("Resultado");
                mensaje.setTitle("Registro Eliminado");
                mensaje.setContentText("El Registro se ha Eliminado  Exitosamente");
                mensaje.show();
                limpiar();
            }
        }
        catch (Exception a)
        {
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setHeaderText("Resultado");
            mensaje.setTitle("Error");
            mensaje.setContentText("Algo salio mal Vuelva a intentarlo");
            mensaje.show();
        }

    }
}
