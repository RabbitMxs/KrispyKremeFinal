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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Employee_View.Package.ControllerPackage;
import sample.Employee_View.Shopping_Cart.ShoppingCart;
import sample.models.clases.Employee;
import sample.models.clases.Package;
import sample.models.clases.Product;
import sample.models.dao.Dao;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {
    static int count=0;
    ActionEvent controller;
    static double xOffset=0;
    static double yOffser=0;
    Dao dao;
    static Employee employee;
    private static ObservableList<Product> list_product= FXCollections.observableArrayList();
    private static ObservableList<Product> new_package=FXCollections.observableArrayList();

    public static int getCount() {
        return count;
    }

    public static void IngrementaCount() {
        EmployeeView.count ++;
    }

    @FXML
    JFXButton btnProduct,btnPackage,btnNewPackage,btnExit,btnCarrito,btnAgregaCarrito,btnAgregaCarrito1,btnNuevoPackage,btnAddPackage;

    @FXML
    TableView<Product> tableProduct=new TableView<>();

    @FXML
    TableView<Package> tablePackage=new TableView();

    @FXML
    VBox vbCarrito,vbPackage;

    @FXML
    private Circle image;

    @FXML
    Label lbWelcome;

    public void setController(ActionEvent controller) {
        this.controller = controller;
    }

    public static void setList(ObservableList<Product> lit) {
        list_product = lit;
    }

    public static void limpiaNewPackage(){
        new_package.clear();
    }

    public static void setListOneProduct(Product lit) {
        list_product.add(lit);
    }

    public void setEmployee(Employee employee) {
        EmployeeView.employee = employee;
    }

    EventHandler<ActionEvent> event_mostrar= new EventHandler<ActionEvent>() {
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
                    btnAgregaCarrito.setVisible(true);
                    btnAgregaCarrito1.setVisible(false);
                }
            }else{
                tableProduct.setVisible(false);
                tablePackage.setVisible(true);
                vbCarrito.setVisible(true);
                vbPackage.setVisible(false);
                btnAgregaCarrito.setVisible(false);
                btnAgregaCarrito1.setVisible(true);
            }
        }
    };

    EventHandler<ActionEvent> event_Cantidad=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource() == btnAgregaCarrito){
                Product product=tableProduct.getSelectionModel().getSelectedItem();
                product.setCantidad(Cantidad(product));
                addList(product);
            }else{
                Package pack=tablePackage.getSelectionModel().getSelectedItem();
                Product product=new Product(pack.getName_pa(),pack.getPrice_pa());
                product.setCantidad(Cantidad(product));
                addList(product);
            }

        }
    };

    public void addList(Product product){
        if (product.getCantidad()!=0){
            list_product.add(product);
        }
    }

    public int Cantidad( Product product){
        try{
            TextInputDialog inputDialog=new TextInputDialog();
            inputDialog.setTitle("Cantidad");
            inputDialog.setHeaderText(null);
            inputDialog.setContentText("Teclea la cantidad de "+product.getName_pro());
            inputDialog.initStyle(StageStyle.UTILITY);
            Optional<String> respuesta=inputDialog.showAndWait();
            return Integer.parseInt(respuesta.get());
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

     static EventHandler<ActionEvent> event_Carrito=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage confirmation=new Stage();
                confirmation.setTitle("Carrito");
                Parent root=null;
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Shopping_cart/Shopping_cart.fxml"));
                ShoppingCart confi=new ShoppingCart();
                confi.setList(list_product);
                confi.setEmployee(employee);
                loader.setController(confi);
                root=loader.load();
                Scene scene=new Scene(root);
                confirmation.setScene(scene);
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset=event.getSceneX();
                        yOffser=event.getSceneY();
                    }
                });

                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        confirmation.setX(event.getScreenX()-xOffset);
                        confirmation.setY(event.getScreenY()-yOffser);
                    }
                });
                confirmation.initStyle(StageStyle.UNDECORATED);
                confirmation.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    EventHandler<ActionEvent> event_Close=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
            list_product.clear();
            new_package.clear();
            ((Stage)((Button) controller.getSource()).getScene().getWindow()).show();
        }
    };

    EventHandler<ActionEvent> event_AddPackage=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(m_cuenta()<12){
                m_CantidadPackage();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Debes agregar el paquete actual al carrito");
                alert.show();
            }
        }
    };

    public int m_cuenta(){
        int suma=0;
        for (Product p:new_package) {
            suma+=p.getCantidad();
        }
        return suma;
    }

    public void m_CantidadPackage(){
        Product product=tableProduct.getSelectionModel().getSelectedItem();
        try{
            TextInputDialog inputDialog=new TextInputDialog();
            inputDialog.setTitle("Cantidad");
            inputDialog.setHeaderText(null);
            inputDialog.setContentText("Teclea la cantidad de "+product.getName_pro()+" que quieres agregar al paquete");
            inputDialog.initStyle(StageStyle.UTILITY);
            Optional<String> respuesta=inputDialog.showAndWait();
            int cantidad=Integer.parseInt(respuesta.get());
            product.setCantidad(cantidad);
            if(m_cuenta()+cantidad<=12){
                new_package.add(product);
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se puede agregar mas de 12 donas");
                alert.show();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    EventHandler<ActionEvent> eventShowPackage=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage windowPackage=new Stage();
                windowPackage.setTitle("Package");
                Parent root=null;
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Package/Package.fxml"));
                ControllerPackage confi=new ControllerPackage();
                confi.setList(new_package);
                loader.setController(confi);
                root=loader.load();
                Scene scene=new Scene(root);
                windowPackage.setScene(scene);

                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset=event.getSceneX();
                        yOffser=event.getSceneY();
                    }
                });

                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        windowPackage.setX(event.getScreenX()-xOffset);
                        windowPackage.setY(event.getScreenY()-yOffser);
                    }
                });
                windowPackage.initStyle(StageStyle.UNDECORATED);
                windowPackage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao=new Dao();
        lbWelcome.setText(lbWelcome.getText()+employee.getName_em());
        tableProduct.setVisible(true);
        tablePackage.setVisible(false);
        vbCarrito.setVisible(true);
        vbPackage.setVisible(false);
        btnAgregaCarrito1.setVisible(false);
        btnAgregaCarrito.setVisible(true);
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));
        btnNewPackage.setOnAction(event_mostrar);
        btnPackage.setOnAction(event_mostrar);
        btnProduct.setOnAction(event_mostrar);
        tableProduct.setItems(dao.findAllProduct());
        tablePackage.setItems(dao.findAllPackage());
        btnAgregaCarrito.setOnAction(event_Cantidad);
        btnAgregaCarrito1.setOnAction(event_Cantidad);
        btnCarrito.setOnAction(event_Carrito);
        btnExit.setOnAction(event_Close);
        btnAddPackage.setOnAction(event_AddPackage);
        btnNuevoPackage.setOnAction(eventShowPackage);
    }
}
