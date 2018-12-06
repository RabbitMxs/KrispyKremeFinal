package sample.admin.Categorias;

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
import sample.models.clases.Category;
import sample.models.clases.Invoice;
import sample.models.dao.MySQL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCategory implements Initializable
{
    @FXML
    private JFXButton btnProducto,btnPermiso,btnEmpleado,btnCategoria,btnExit,BtnReporteMes,BtnReporteAño,BtnReporteEmpleados;

    @FXML private Circle image;

    @FXML private TextField TxtNombre;
    @FXML private TextField TxtId;


    @FXML private JFXButton BtnAgregar;
    @FXML private JFXButton BtnActualizar;
    @FXML private JFXButton BtnEliminar;

    @FXML private TableView<Category> TvCategoria;
    @FXML private TableColumn<Category,Number> ClId_Categoria;
    @FXML private TableColumn<Category,String> ClNombre;

    ObservableList<Category> Lista_Categoria;


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
            llenar_data();
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

    public void llenar_data()
    {
        try {
            Lista_Categoria = FXCollections.observableArrayList();
            Category.llenarInformacion(MySQL.getConnection(), Lista_Categoria);
            TvCategoria.setItems(Lista_Categoria);

            ClId_Categoria.setCellValueFactory(new PropertyValueFactory<Category, Number>("id_category"));
            ClNombre.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
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

    public void limpiar()
    {
        TxtId.clear();
        TxtNombre.clear();
    }


    public void Seleccionado()
    {
        try {
            TvCategoria.getSelectionModel().selectedItemProperty().addListener
                    (new ChangeListener<Category>() {
                        @Override
                        public void changed(ObservableValue<? extends Category> observable,
                                            Category ValorAnterior, Category ValorSeleccionado) {

                            if (ValorSeleccionado != null) {
                                TxtId.setText(String.valueOf(ValorSeleccionado.getId_category()));
                                TxtNombre.setText(ValorSeleccionado.getName());

                            }

                        }
                    });
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
    public void Guardar()
    {
        try {
            //Crear una nueva instantiancia del tipo Donas y luego guardar
            Category categoria = new Category(Integer.valueOf(TxtId.getText()), TxtNombre.getText());
            //LLamamos el metodo
            MySQL.Connect();
            int resultado = categoria.InsertarCategoria(MySQL.getConnection());
            llenar_data();
            MySQL.Disconnect();
            if (resultado == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Agregado");
                alert.setContentText("Se guardo exitosamente");
                alert.setHeaderText("Resultado");
                alert.show();

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
    public void ActualizarRegistro()
    {
        try {
            Category a = new Category(
                    Integer.valueOf(TxtId.getText()),
                    TxtNombre.getText()
            );

            MySQL.Connect();
            int resultado = a.ActualizarRegistro(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                Lista_Categoria.set(TvCategoria.getSelectionModel().getSelectedIndex(), a);
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
            int resultado = TvCategoria.getSelectionModel().getSelectedItem().EliminarRegistro(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                Lista_Categoria.remove(TvCategoria.getSelectionModel().getSelectedIndex());
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
