package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public Button loginbtn;
    @FXML
    public Button regbtn;
    @FXML
    public TextField txtusername;
    @FXML
    public TextField txtpassword;
    @FXML
    public Label invalid;

    //Method for login button press event
    public void loginPressed(ActionEvent event){
        if(txtusername.getText()=="" || txtpassword.getText()=="")
            invalid.setVisible(true);  //show error on empty field login
        else{
            Packet sent=new Packet();
            sent.operation="login";
            sent.s1=txtusername.getText();
            sent.s2=txtpassword.getText();
            Packet received=Main.request(sent); //Here we got response from server
            if(received.response){
                Main.username=sent.s1;  //Setting the working user
                //Sending new packet to get user information
                Packet p1=new Packet();
                p1.operation="userInfo";
                p1.s1=sent.s1;
                Packet userInfo=Main.request(p1); //Setting same in static variable in mainController class
                //Sending new packet to get list of venues
                Packet p2=new Packet();
                p2.operation="venueList";
                Packet venueList=Main.request(p2); //Main.request(sent);
                //Sending new packet to get list of user events
                Packet p3=new Packet();
                p3.operation="userEvents";
                Packet userEvents=null; //Main.request(p3); //Main.request(sent);
                Stage stage = (Stage) loginbtn.getScene().getWindow(); //selecting current window
                stage.close();  //then closing it
                try { //here opening new main window
                    Stage primaryStage=new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/main.fxml"));
                    Parent root=loader.load();
                    mainController controller = loader.<mainController>getController();
                    controller.initVariable(userInfo,venueList,userEvents);

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }else{
                invalid.setVisible(true);  //show error on invalid credentials
            }
        }
    }
    //Method for register button press event
    public void registerPressed(ActionEvent event){
        try {  //Just opening new registration window without closing the login window
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/register.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
