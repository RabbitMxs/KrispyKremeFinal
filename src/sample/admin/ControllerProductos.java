package sample.admin;

import com.jfoenix.controls.JFXTextField;
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
import sample.models.clases.Category;
import sample.models.clases.Product;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProductos implements Initializable {
    @FXML private Circle image;
    @FXML private TableColumn<Product,String> ClNombre;
    @FXML private TableColumn<Product,Double> ClPrecio;
    @FXML private TableColumn<Product,String> ClDescripcion;
    @FXML private TableColumn<Product,Category> ClCategoria;
    //@FXML private TableColumn<Product,String> ClImagen;
    @FXML private TableView<Product> TvProductos;

    @FXML private ComboBox<Category> CbxCategoria;
    @FXML private JFXTextField TxtId;
    @FXML private TextField TxtNombre;
    @FXML private TextField TxtPrecio;
    @FXML private TextField TxtDescripcion;

    @FXML private Button BtnAgregar;
    @FXML private Button BtnEliminar;
    @FXML private Button BtnActualizar;


    ObservableList<Product> ListaProductos;
    ObservableList<Category> ListaCategoria;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));

        MySQL.Connect();

        llenarCombo();
        lennar_data();
        Seleccionado();

        MySQL.Disconnect();

    }

    public void limpiar()
    {
        TxtId.clear();
        TxtNombre.clear();
        TxtDescripcion.clear();
        TxtPrecio.clear();
        CbxCategoria.setValue(null);

    }

    public void lennar_data()
    {
        try {
            ListaProductos = FXCollections.observableArrayList();
            Product.llenarInformacion(MySQL.getConnection(), ListaProductos);
            TvProductos.setItems(ListaProductos);

            ClNombre.setCellValueFactory(new PropertyValueFactory<Product, String>("name_pro"));
            ClPrecio.setCellValueFactory(new PropertyValueFactory<Product, Double>("price_pro"));
            ClDescripcion.setCellValueFactory(new PropertyValueFactory<Product, String>("description_pro"));
            ClCategoria.setCellValueFactory(new PropertyValueFactory<Product, Category>("Nombre_cat"));
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

    public void llenarCombo()
    {
        try {

            ListaCategoria = FXCollections.observableArrayList();
            Category.llenarComboCat(MySQL.getConnection(), ListaCategoria);
            CbxCategoria.setItems(ListaCategoria);
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

    public void Seleccionado()
    {
        TvProductos.getSelectionModel().selectedItemProperty().addListener
                (new ChangeListener<Product>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable,
                                        Product ValorAnterior, Product ValorSeleccionado)
                    {

                        if (ValorSeleccionado !=null) {
                            TxtId.setText(String.valueOf(ValorSeleccionado.getId_product()));
                            TxtNombre.setText(ValorSeleccionado.getName_pro());
                            TxtPrecio.setText(String.valueOf(ValorSeleccionado.getPrice_pro()));
                            TxtDescripcion.setText(ValorSeleccionado.getDescription_pro());
                            CbxCategoria.setValue(ValorSeleccionado.getNombre_cat());

                            BtnEliminar.setDisable(false);
                            BtnActualizar.setDisable(false);
                        }

                    }
                });
    }

    @FXML
    public void Guardar()
    {
        try {
            //Crear una nueva instantiancia del tipo Donas y luego guardar
            Product producto = new Product(0,
                    TxtNombre.getText(),
                    Double.valueOf(TxtPrecio.getText()),
                    TxtDescripcion.getText(),
                    CbxCategoria.getSelectionModel().getSelectedItem()
            );

            //LLamamos el metodo
            MySQL.Connect();
            int resultado = producto.InsertarProductos(MySQL.getConnection());
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
            Product a = new Product(
                    Integer.valueOf(TxtId.getText()),
                    TxtNombre.getText(),
                    Double.valueOf(TxtPrecio.getText()),
                    TxtDescripcion.getText(),
                    CbxCategoria.getSelectionModel().getSelectedItem()
            );
            MySQL.Connect();
            int resultado = a.ActualizarRegistro(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                ListaProductos.set(TvProductos.getSelectionModel().getSelectedIndex(), a);
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
            int resultado = TvProductos.getSelectionModel().getSelectedItem().EliminarRegistro(MySQL.getConnection());
            MySQL.Disconnect();

            if (resultado == 1) {
                ListaProductos.remove(TvProductos.getSelectionModel().getSelectedIndex());
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

   /* @FXML
    private void AbrirImg()
    {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        j.setFileFilter(fil);

        int s = j.showOpenDialog(this);
        if(s == JFileChooser.APPROVE_OPTION){
            String ruta = j.getSelectedFile().getAbsolutePath();
            LbUrl.setText(ruta);
        }
    }//GEN-LAST:event_jButton1ActionPerformed*/
}
