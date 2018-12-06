package sample.admin.Empleados;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.Main;
import sample.models.clases.Employee;
import sample.models.clases.Invoice;
import sample.models.clases.Permit;
import sample.models.dao.MySQL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEmpleados implements Initializable {
    @FXML
    private JFXButton btnProducto,btnPermiso,btnEmpleado,btnCategoria,btnExit,BtnReporteMes,BtnReporteAño,BtnReporteEmpleados;

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
        btnCategoria.setOnAction(event_accion);
        btnEmpleado.setOnAction(event_accion);
        btnExit.setOnAction(event_accion);
        btnPermiso.setOnAction(event_accion);
        btnProducto.setOnAction(event_accion);

        BtnReporteMes.setOnAction(miReportInvoiceMES);
        BtnReporteAño.setOnAction(miReportInvoiceAño);
        BtnReporteEmpleados.setOnAction(miReportInvoiceEmpleados);

        MySQL.Connect();
        LlenarTableview();
        llenarCombo();
        Seleccionado();

    }

    public String destiny_invoiceEmpleados= "src/sample/admin/Reportes/Report_InvoicesEmpleados.pdf";
    EventHandler<ActionEvent> miReportInvoiceEmpleados = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            try {
                File file_invoice = new File(destiny_invoiceEmpleados);
                file_invoice.getParentFile().mkdirs();
                Invoice.consulta_facturaAño(destiny_invoiceEmpleados);

            }catch (IOException IOE)
            {
                System.out.println(IOE.toString());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    };

    public String destiny_invoice= "src/sample/admin/Reportes/Report_InvoicesMes.pdf";
    EventHandler<ActionEvent> miReportInvoiceMES = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            try {
                File file_invoice = new File(destiny_invoice);
                file_invoice.getParentFile().mkdirs();
                Invoice.consulta_facturaMes(destiny_invoice);

            }catch (IOException IOE)
            {
                System.out.println(IOE.toString());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    };
    public String destiny_invoiceAño= "src/sample/admin/Reportes/Report_InvoicesAño.pdf";
    EventHandler<ActionEvent> miReportInvoiceAño = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event) {
            try {
                File file_invoice = new File(destiny_invoiceAño);
                file_invoice.getParentFile().mkdirs();
                Invoice.consulta_facturaAño(destiny_invoiceAño);

            }catch (IOException IOE)
            {
                System.out.println(IOE.toString());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    };

    EventHandler<ActionEvent> event_accion=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                JFXButton boton= (JFXButton) event.getSource();
                if(btnCategoria==boton){
                    m_muestra("./../Categorias/Category.fxml", event);
                }else if (boton== btnEmpleado){
                    m_muestra("./../Empleados/Empleados.fxml", event);
                }if (boton==btnPermiso){
                    m_muestra("./../Permisos/Permisos.fxml", event);
                }else if (boton== btnProducto){
                    m_muestra("./../productos.fxml", event);
                }else if (btnExit==boton){
                    ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
                    Main.primaryStage.show();
                }

            }catch (IOException e){
                System.out.println(e);
            }
        }
    };

    public void m_muestra(String ruta, ActionEvent event) throws IOException {
        Stage Empleado_admin=new Stage();
        Empleado_admin.setTitle("Admin");
        Parent root=null;
        FXMLLoader loader= new FXMLLoader(getClass().getResource(ruta));
        root=loader.load();
        Scene scene=new Scene(root);
        Empleado_admin.setScene(scene);
        Empleado_admin.setMaximized(true);
        Empleado_admin.setResizable(true);
        Empleado_admin.show();
        ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
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
        LlenarTableview();
        MySQL.Disconnect();
        if(resultado==1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Agregado");
            alert.setContentText("Se guardo exitosamente");
            alert.setHeaderText("Resultado");
            alert.show();

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
