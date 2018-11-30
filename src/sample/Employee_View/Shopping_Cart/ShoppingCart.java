package sample.Employee_View.Shopping_Cart;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Employee_View.EmployeeView;
import sample.models.clases.Employee;
import sample.models.clases.Product;
import sample.models.dao.Dao;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShoppingCart implements Initializable {
    ObservableList<Product> list= FXCollections.observableArrayList();
    Employee employee;

    @FXML
    Label lbSubtotal,lbTotal;

    @FXML
    JFXButton btnBorrar,btnActualiza,btnPagar,btnCerrar;

    @FXML
    JFXTextField txtCantidad;

    @FXML
    TableView<Product> table_venta=new TableView<>();

    public void setList(ObservableList<Product> list) {
        this.list = list;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    EventHandler<MouseEvent> evenTable=new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Product product=table_venta.getSelectionModel().getSelectedItem();
            txtCantidad.setText(Integer.toString(product.getCantidad()));
        }
    };

    public void m_calcTotal(){
        double subtotal=0;
        for (Product p:list) {
            subtotal+=p.getCantidad()*p.getPrice_pro();
        }
        lbSubtotal.setText(String.valueOf(subtotal));
        lbTotal.setText(String.valueOf((subtotal*.16)+subtotal));
    }

    EventHandler<ActionEvent> eventActualiza=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Product product=table_venta.getSelectionModel().getSelectedItem();
            if(!(txtCantidad.getText().equals(""))){
                for (Product p:list) {
                    if (product.getName_pro().equals(p.getName_pro())){
                        p.setCantidad(Integer.parseInt(txtCantidad.getText()));
                        m_calcTotal();
                        EmployeeView.setList(list);
                        txtCantidad.setText("");
                        table_venta.refresh();
                    }
                }
            }
        }
    };

    EventHandler<ActionEvent> eventBorrar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Product product=table_venta.getSelectionModel().getSelectedItem();
            list.remove(product);
            m_calcTotal();
            EmployeeView.setList(list);
            table_venta.refresh();
            m_calcTotal();
        }
    };

    public int payment(){
        Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Tipo de pago");
        alerta.setContentText("Selecciona el tipo de pago");
        alerta.setHeaderText(null);
        ButtonType btnSi=new ButtonType("Efectivo", ButtonBar.ButtonData.YES);
        ButtonType btnNo=new ButtonType("Tarjeta",ButtonBar.ButtonData.NO);
        alerta.getButtonTypes().setAll(btnSi,btnNo);
        Optional<ButtonType> result=alerta.showAndWait();
        if(result.get()==btnSi){
            return 1;
        }else{
            return 2;
        }
    }

    EventHandler<ActionEvent> eventPago=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Domicilio");
            alerta.setContentText("Es una venta a domicilio?");
            alerta.setHeaderText(null);
            ButtonType btnSi=new ButtonType("Si", ButtonBar.ButtonData.YES);
            ButtonType btnNo=new ButtonType("No",ButtonBar.ButtonData.NO);
            alerta.getButtonTypes().setAll(btnSi,btnNo);
            Optional<ButtonType> result=alerta.showAndWait();
            if(result.get()==btnNo){
                Dao dao=new Dao();
                dao.insertInvoice(employee,Double.parseDouble(lbSubtotal.getText()),Double.parseDouble(lbTotal.getText()),payment());
                list.clear();
                EmployeeView.setList(list);
                ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
            }else{

            }
        }
    };

    EventHandler<ActionEvent> eventCerrar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_venta.setItems(list);
        table_venta.setOnMouseClicked(evenTable);
        m_calcTotal();
        btnActualiza.setOnAction(eventActualiza);
        btnBorrar.setOnAction(eventBorrar);
        btnPagar.setOnAction(eventPago);
        btnCerrar.setOnAction(eventCerrar);
    }
}
