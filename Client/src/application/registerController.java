package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerController implements Initializable{
    @FXML
    TextField uname;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField password;
    @FXML
    TextField email;
    @FXML
    Button check;
    @FXML
    Button register;
    @FXML
    Label checklabel;
    @FXML
    Label reg;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    public void checkPressed(ActionEvent event){
        if(uname.getText()==""){
            checklabel.setTextFill(Color.web("#e42831"));
            checklabel.setText("Unavailable");
        }else{
            Packet sent=new Packet();
            sent.operation="check";
            sent.s1=uname.getText();
            Packet received=Main.request(sent);
            if(received.response){
                checklabel.setTextFill(Color.web("#13c652"));
                checklabel.setText("Available");
            }else{
                checklabel.setTextFill(Color.web("#e42831"));
                checklabel.setText("Unvailable");
            }
        }
        checklabel.setVisible(true);
    }

    public void registerPressed(ActionEvent event){
        if(uname.getText().equals("") || fname.getText().equals("") || lname.getText().equals("") || password.getText().equals("") || email.getText().equals("")){
            reg.setTextFill(Color.web("#e42831"));
            reg.setText("Please fill all the details");
        }else{
            Packet sent=new Packet();
            sent.operation="signUp";
            sent.s1=uname.getText();
            sent.s2=fname.getText()+" "+lname.getText();
            sent.s3=password.getText();
            sent.s4=email.getText();
            if(male.isSelected())
                sent.s5="M";
            else
                sent.s5="F";
            Packet received=Main.request(sent);
            if(received.response){
                reg.setTextFill(Color.web("#13c652"));
                reg.setText("Congratulations, Registration Completed");
            }else{
                reg.setTextFill(Color.web("#e42831"));
                reg.setText("Congratulations, Registration Completed");
            }
        }
        reg.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reg.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(reg, 0.0);
        AnchorPane.setRightAnchor(reg, 0.0);
        reg.setAlignment(Pos.CENTER);
    }
}
