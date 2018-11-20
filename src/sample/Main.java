package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Employee_View/Employee_View.fxml"));//sample.fxml
        //primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Login");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("/Styles/StyleEmployee.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
