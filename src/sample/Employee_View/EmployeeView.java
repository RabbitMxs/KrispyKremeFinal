package sample.Employee_View;

import de.jensd.fx.glyphs.emojione.EmojiOne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {
    @FXML
    private Circle image;

    @FXML
    Label lbWelcome;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image m=new Image("/Images/Employee.jpg");
        image.setFill(new ImagePattern(m));
        //lbWelcome.setText(lbWelcome.getText()+"MAX");
    }
}
