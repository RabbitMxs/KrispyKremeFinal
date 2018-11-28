package sample.Employee_View.Shopping_Cart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import sample.models.clases.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCart implements Initializable {
    ObservableList<Product> list= FXCollections.observableArrayList();

    @FXML
    TableView<Product> table_venta=new TableView<>();

    public void setList(ObservableList<Product> list) {
        this.list = list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_venta.setItems(list);
    }
}
