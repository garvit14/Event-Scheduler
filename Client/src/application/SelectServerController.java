package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectServerController {
    @FXML
    Label status;
    @FXML
    TextField ip;
    @FXML
    Button connect;
    public void pressButton(ActionEvent event) throws IOException {
        if(Main.connection(ip.getText())){
            Stage stage = (Stage) connect.getScene().getWindow();
            stage.close();
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }else{
            status.setVisible(true);
        }
    }
}
