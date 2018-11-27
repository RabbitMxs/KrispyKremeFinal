package sample.Employee_View;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import sample.models.clases.Product;
import sample.models.dao.DonasDao;
import sample.models.dao.MySQL;
import sample.models.dao.PackageDao;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {
    @FXML
    JFXButton btnProduct,btnPackage,btnNewPackage,btnExit;

    @FXML
    TableView<Product> tableProduct=new TableView<>();

    @FXML
    TableView tablePackage=new TableView();

    @FXML
    VBox vbCarrito,vbPackage;

    @FXML
    private Circle image;

    @FXML
    Label lbWelcome;

    EventHandler<ActionEvent> m_mostrar= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            JFXButton button= (JFXButton) event.getSource();
            if(button==btnProduct || button==btnNewPackage){
                tableProduct.setVisible(true);
                tablePackage.setVisible(false);
                if(button==btnNewPackage){
                    vbCarrito.setVisible(false);
                    vbPackage.setVisible(true);
                }else {
                    vbCarrito.setVisible(true);
                    vbPackage.setVisible(false);
                }
            }else{
                tableProduct.setVisible(false);
                tablePackage.setVisible(true);
                vbCarrito.setVisible(true);
                vbPackage.setVisible(false);
            }
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DonasDao donasDao=new DonasDao(MySQL.getConnection());
        PackageDao packageDao=new PackageDao(MySQL.getConnection());
        tableProduct.setVisible(true);
        tablePackage.setVisible(false);
        vbCarrito.setVisible(true);
        vbPackage.setVisible(false);
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));
        btnNewPackage.setOnAction(m_mostrar);
        btnPackage.setOnAction(m_mostrar);
        btnProduct.setOnAction(m_mostrar);
        tableProduct.setItems(donasDao.findAll());
        tablePackage.setItems(packageDao.findAll());
    }
}
