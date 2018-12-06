package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;//esta es static para poder entrar desde otra clase a ella


    @Override
    public void start(Stage _primaryStage) throws Exception{
        primaryStage=_primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("/Styles/StyleLogin.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
