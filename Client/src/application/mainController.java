package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mainController {

    private Packet getVenue,userEvents,sent;
    private ObservableList<String> l;
    private ObservableList<Column> l2;
    private ObservableList<Column2> l3;
    //List<String> list;
    @FXML
    Label uname;
    @FXML
    Label name2;
    @FXML
    Label gender;
    @FXML
    Label email;
    @FXML
    DatePicker date;
    @FXML
    Button refresh;
    @FXML
    Button logout;
    @FXML
    ComboBox<String> combobox;
    @FXML TableView<Column> table;
    @FXML TableColumn<Column,Integer> sno;
    @FXML TableColumn<Column,String> from;
    @FXML TableColumn<Column,String> to;
    @FXML TableColumn<Column,String> name;
    @FXML TableView<Column2> table1;
    @FXML TableColumn<Column2,Integer> sno1;
    @FXML TableColumn<Column2,String> from1;
    @FXML TableColumn<Column2,String> to1;
    @FXML TableColumn<Column2,String> name1;
    @FXML TableColumn<Column2,String> venue1;
    @FXML Button bookevent;
    @FXML Label bookstatus;
    @FXML TextField textfield;

    public void initVariable(Packet userInfo,Packet getVenue,Packet userEvents) throws SQLException {
        //List list=new ArrayList<String>();
        this.getVenue=getVenue;
        this.userEvents=userEvents;
        System.out.println(userInfo.s1);
        uname.setText("Welcome, "+userInfo.s1);
        name2.setText(userInfo.s2);
        gender.setText(userInfo.s3);
        email.setText(userInfo.s4);
        //System.
        //List<String> list;
        if(getVenue!=null) {
            l = FXCollections.observableList(getVenue.list);
            combobox.setItems(l);
        }
        //setTable3(userEvents.list2);
    }

    private void setTable(List<List<String> > list){
        List<Column> list2=new ArrayList<Column>();
        Column c;
        for(int i=0;i<12;i++){
            if(list.get(i).get(2)==null)
                c=new Column(i+1,(i+9)+":00",(i+10)+":00","No Event");
            else
                c=new Column(i+1,(i+9)+":00",(i+10)+":00",list.get(i).get(2));
            list2.add(c);
        }
        l2=FXCollections.observableList(list2);
        sno.setCellValueFactory(new PropertyValueFactory<Column, Integer>("sno"));
        from.setCellValueFactory(new PropertyValueFactory<Column, String>("from"));
        to.setCellValueFactory(new PropertyValueFactory<Column, String>("to"));
        name.setCellValueFactory(new PropertyValueFactory<Column, String>("name"));
        table.setItems(l2);
    }

    private void setTable3(List<List<String> > list){
        List<Column2> list2=new ArrayList<Column2>();
        Column2 c;
        for(int i=0;i<list.size();i++){
            //if(list.get(i).get(2)==null)
            //    c=new Column(i+1,(i+9)+":00",(i+10)+":00","No Event");
            //else
                c=new Column2(i+1,(Integer.parseInt(list.get(i).get(0))+9)+":00",(Integer.parseInt(list.get(i).get(0))+10)+":00",list.get(i).get(1),list.get(i).get(2));
            list2.add(c);
        }
        l3=FXCollections.observableList(list2);
        sno1.setCellValueFactory(new PropertyValueFactory<Column2, Integer>("sno"));
        from1.setCellValueFactory(new PropertyValueFactory<Column2, String>("from"));
        to1.setCellValueFactory(new PropertyValueFactory<Column2, String>("to"));
        venue1.setCellValueFactory(new PropertyValueFactory<Column2, String>("venue"));
        name1.setCellValueFactory(new PropertyValueFactory<Column2, String>("name"));
        table1.setItems(l3);

    }

    private void setTable2(){
        Column c;
        List<Column> list2=new ArrayList<Column>();
        for(int i=0;i<12;i++){
            c=new Column(i,i+"",i+"",i+"");
            list2.add(c);
        }
        l2=FXCollections.observableList(list2);
        sno.setCellValueFactory(new PropertyValueFactory<Column, Integer>("sno"));
        from.setCellValueFactory(new PropertyValueFactory<Column, String>("from"));
        to.setCellValueFactory(new PropertyValueFactory<Column, String>("to"));
        name.setCellValueFactory(new PropertyValueFactory<Column, String>("name"));
        table.setItems(l2);
    }

    public void refreshPressed(ActionEvent event){
        if(date.getValue()!=null && !combobox.getValue().equals("")){
            Packet sent=new Packet();
            sent.operation="getSlotInfo";
            sent.s1=combobox.getValue();
            sent.date=Date.from(date.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Packet received=Main.request(sent);
            //for(int i=0;i<12;i++){
            //    System.out.println(received.list2.get(i).get(2));
            //}
            setTable(received.list2);
            //setTable2();
        }
    }

    public void bookEvent(ActionEvent event){
        //bookstatus.setText(""+table.getSelectionModel().getFocusedIndex());
        /*if(table.getSelectionModel().getFocusedIndex().){
            bookstatus.setTextFill(Color.web("#e42831"));
            bookstatus.setText("Please select a time slot");
        }else{*/
        System.out.println(""+table.getSelectionModel().getFocusedIndex());
        Packet sent=new Packet();
        sent.operation="bookEvent";
        sent.s1=combobox.getValue();
        sent.s2=textfield.getText();
        sent.s3=Main.username;
        sent.date=Date.from(date.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(Main.username);
        sent.i=1+table.getSelectionModel().getFocusedIndex();
        System.out.println(sent.i);
        Packet received=Main.request(sent);
        System.out.println(received.response);
        if(received.response) {
            bookstatus.setTextFill(Color.web("#13c652"));
            bookstatus.setText("Event booked successfully");
            //Packet sent2=new Packet();
            //Packet received2=Main.request(sent2);
            //setTable3(received2.list2);
        }else{
            bookstatus.setTextFill(Color.web("#e42831"));
            bookstatus.setText("You already have an event booked");
        }
    }

    public void logoutPressed(ActionEvent event){
        Packet sent=new Packet();
        sent.operation="logout";
        Main.request(sent);
    }
}
