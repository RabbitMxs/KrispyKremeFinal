package sample.admin.Categorias;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import sample.models.clases.Category;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCategory implements Initializable
{
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

            MySQL.Connect();
            llenar_data();
            Seleccionado();
            MySQL.Disconnect();

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
