package sample.Employee_View.Shopping_Cart.Customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Employee_View.EmployeeView;
import sample.models.clases.Customer;
import sample.models.clases.Employee;
import sample.models.dao.Dao;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {
    Dao dao;
    private Employee employee;
    private double total,subtotal;
    private int payment;

    @FXML
    JFXTextField txtPhone,txtE_mail,txtName,txtLast,txtAdrress,txtCP,txtCity,txtState;

    @FXML
    JFXButton btnBuscar,btnAgregar,btnConfirmar,btnCerrar;

    public ControllerCustomer(Employee employee, double total, double subtotal, int payment) {
        this.employee = employee;
        this.total = total;
        this.subtotal = subtotal;
        this.payment = payment;
    }

    EventHandler<ActionEvent> event_buscar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String numero=txtPhone.getText();
            Customer c=null;
            c=dao.fetchCustomer(numero);
            if(c==null){
                Alert alerta=new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setContentText("El cliente con el numero "+c.getPhone()+" no existe");
                alerta.setHeaderText(null);
                alerta.show();
            }else {
                txtName.setText(c.getName_cu());
                txtLast.setText(c.getLast_cu());
                txtAdrress.setText(c.getAddress());
                txtCP.setText(c.getCp());
                txtCity.setText(c.getCity());
                txtState.setText(c.getState());
                txtE_mail.setText(c.getE_mail());
                txtPhone.setText(c.getPhone());
            }
        }
    };

    EventHandler<ActionEvent> event_close=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            EmployeeView.clearListProduct();
            EmployeeView.clearListProduct();
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
        }
    };

    EventHandler<ActionEvent> event_add=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Customer customer=new Customer(
                    txtName.getText(), txtLast.getText(),
                    txtE_mail.getText(), txtPhone.getText(),
                    txtAdrress.getText(), txtCity.getText(),
                    txtState.getText(), txtCP.getText()
            );
            ObservableList<Customer> customers= FXCollections.observableArrayList();
            customers=dao.findAllCustomer();
            if(buscar(customers, txtPhone.getText())){
                if (dao.insertCustomer(customer)){
                    Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmacion");
                    alerta.setContentText("Se agrego exitosamente el nuevo cliente");
                    alerta.setHeaderText(null);
                    alerta.show();
                }else{
                    Alert alerta=new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setContentText("No se pudo agregar el nuevo cliente");
                    alerta.setHeaderText(null);
                    alerta.show();
                }
            }

        }
    };

    public boolean buscar(ObservableList<Customer> customers, String phone){
        for (Customer c: customers) {
            if(c.getPhone().equals(phone)){
                return false;
            }
        }
        return true;
    }

    EventHandler<ActionEvent> event_Pagar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Customer customer=dao.fetchCustomer(txtPhone.getText());
            if(dao.insertInvoiceCliente(employee,subtotal, total,payment, customer.getId_customer())){
                EmployeeView.clearListProduct();
                EmployeeView.clearListProduct();
                ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
            }else{
                Alert alerta=new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Error");
                alerta.setContentText("No se pudo realizar la venta exitosamente");
                alerta.setHeaderText(null);
                alerta.show();
            }
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao=new Dao();
        btnCerrar.setOnAction(event_close);
        btnBuscar.setOnAction(event_buscar);
        btnAgregar.setOnAction(event_add);
        btnConfirmar.setOnAction(event_Pagar);
    }
}
