package sample.Employee_View;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.Employee_View.Shopping_Cart.ShoppingCart;
import sample.models.clases.Product;
import sample.models.dao.Dao;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {
    ObservableList<Product> list= FXCollections.observableArrayList();

    @FXML
    JFXButton btnProduct,btnPackage,btnNewPackage,btnExit,btnCarrito,btnAgregaCarrito;

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

    EventHandler<ActionEvent> event_cantidad=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Product product=tableProduct.getSelectionModel().getSelectedItem();
            product.setCantidad(Integer.parseInt(JOptionPane.showInputDialog(null,"Teclea la cantidad de "+ product.getName_pro(),"Cantidad",1)));
            list.add(product);
            for (Product p:list){
                System.out.println(p);
            }
        }
    };

    EventHandler<ActionEvent> m_Carrito=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage confirmation=new Stage();
                confirmation.setTitle("Carrito");
                Parent root=null;
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Shopping_cart/Shopping_cart.fxml"));
                ShoppingCart confi=new ShoppingCart();
                confi.setList(list);
                loader.setController(confi);
                root=loader.load();
                Scene scene=new Scene(root);
                confirmation.setScene(scene);
                confirmation.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dao dao=new Dao();
        tableProduct.setVisible(true);
        tablePackage.setVisible(false);
        vbCarrito.setVisible(true);
        vbPackage.setVisible(false);
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));
        btnNewPackage.setOnAction(m_mostrar);
        btnPackage.setOnAction(m_mostrar);
        btnProduct.setOnAction(m_mostrar);
        tableProduct.setItems(dao.findAllProduct());
        tablePackage.setItems(dao.findAllPackage());
        btnAgregaCarrito.setOnAction(event_cantidad);

    }
}
