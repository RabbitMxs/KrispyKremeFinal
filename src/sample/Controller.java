package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Employee_View.EmployeeView;
import sample.models.clases.Employee;
import sample.models.dao.Dao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Dao dao;
    @FXML
    JFXTextField txtUser;

    @FXML
    JFXPasswordField txtPassword;

    @FXML
    Label lbError;

    @FXML
    JFXButton btnLogin;

    EventHandler<ActionEvent> eventLogin=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Dao dao=new Dao();
            Employee employee=dao.fetchEployee(txtUser.getText(),txtPassword.getText());
            if(employee!=null){
                if(employee.getId_permit()==2){
                    showEmpleado(employee,event);
                }else{
                    showAdmin(employee,event);
                }
            }else{
                txtPassword.setText("");
                txtUser.setText("");
                lbError.setVisible(true);
            }
        }
    };

    public void showAdmin(Employee employee, ActionEvent event){
        try {
            txtPassword.setText("");
            txtUser.setText("");
            Stage Empleado_admin=new Stage();
            Empleado_admin.setTitle("Admin");
            Parent root=null;
            FXMLLoader loader= new FXMLLoader(getClass().getResource("admin/productos.fxml"));
            root=loader.load();
            Scene scene=new Scene(root);
            Empleado_admin.setScene(scene);
            Empleado_admin.setMaximized(true);
            Empleado_admin.setResizable(true);
            Empleado_admin.show();
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();//para ocultar la vista del main
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showEmpleado(Employee employee, ActionEvent event){
        try {
            txtPassword.setText("");
            txtUser.setText("");
            Stage Empleado_stage=new Stage();
            Empleado_stage.setTitle("Empleado");
            Parent root=null;
            FXMLLoader loader= new FXMLLoader(getClass().getResource("Employee_View/Employee_View.fxml"));
            EmployeeView emp=new EmployeeView();
            emp.setEmployee(employee);
            loader.setController(emp);//asi se pasa informacion de una ventana a otra atravez del controllador
            root=loader.load();
            Scene scene=new Scene(root);
            scene.getStylesheets().add("/Styles/StyleEmployee.css");
            Empleado_stage.setScene(scene);
            Empleado_stage.setMaximized(true);
            Empleado_stage.setResizable(true);
            Empleado_stage.show();
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();//para ocultar la vista del main
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao=new Dao();
        btnLogin.setOnAction(eventLogin);
    }
}
