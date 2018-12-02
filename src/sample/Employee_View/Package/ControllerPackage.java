package sample.Employee_View.Package;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Employee_View.EmployeeView;
import sample.models.clases.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerPackage implements Initializable {
    ObservableList<Product> list= FXCollections.observableArrayList();

    @FXML
    Label lbTotal;

    @FXML
    JFXButton btnBorrar,btnActualiza,btnAgregaCarrito,btnCerrar;

    @FXML
    JFXTextField txtCantidad;

    @FXML
    TableView<Product> table_package=new TableView<>();

    EventHandler<MouseEvent> evenTable=new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Product product=table_package.getSelectionModel().getSelectedItem();
            txtCantidad.setText(Integer.toString(product.getCantidad()));
        }
    };

    public void m_calcTotal(){
        double subtotal=0;
        for (Product p:list) {
            subtotal+=p.getCantidad()*p.getPrice_pro();
        }
        lbTotal.setText(String.valueOf(subtotal));
    }

    EventHandler<ActionEvent> eventActualiza=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Product product=table_package.getSelectionModel().getSelectedItem();
            if(!(txtCantidad.getText().equals(""))){
                for (Product p:list) {
                    if (product.getName_pro().equals(p.getName_pro())){
                        p.setCantidad(Integer.parseInt(txtCantidad.getText()));
                        m_calcTotal();
                        EmployeeView.setList(list);
                        txtCantidad.setText("");
                        table_package.refresh();
                    }
                }
            }
        }
    };

    EventHandler<ActionEvent> eventBorrar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            txtCantidad.setText("");
            Product product=table_package.getSelectionModel().getSelectedItem();
            list.remove(product);
            m_calcTotal();
            EmployeeView.setList(list);
            table_package.refresh();
            m_calcTotal();
        }
    };

    EventHandler<ActionEvent> eventCerrar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
        }
    };

    EventHandler<ActionEvent> eventAgregar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            EmployeeView.IngrementaCount();
            double total=Double.parseDouble(lbTotal.getText());
            String nombre="Paquete "+EmployeeView.getCount();
            Product newPackage=new Product(nombre,total);
            newPackage.setCantidad(Cantidad());
            if(newPackage.getCantidad()!=0){
                EmployeeView.setListOneProduct(newPackage);
            }
            lbTotal.setText("");
            list.clear();
            table_package.refresh();
            EmployeeView.clearNewPackage();
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
        }
    };

    public int Cantidad(){
        try{
            TextInputDialog inputDialog=new TextInputDialog();
            inputDialog.setTitle("Cantidad");
            inputDialog.setHeaderText(null);
            inputDialog.setContentText("Teclea la cantidad de paquetes que quieres comprar");
            inputDialog.initStyle(StageStyle.UTILITY);
            Optional<String> respuesta=inputDialog.showAndWait();
            return Integer.parseInt(respuesta.get());
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public void setList(ObservableList<Product> list) {
        this.list = list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_package.setItems(list);
        table_package.setOnMouseClicked(evenTable);
        m_calcTotal();
        btnActualiza.setOnAction(eventActualiza);
        btnBorrar.setOnAction(eventBorrar);
        btnCerrar.setOnAction(eventCerrar);
        btnAgregaCarrito.setOnAction(eventAgregar);
    }
}
