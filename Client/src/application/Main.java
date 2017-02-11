package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {

	private static Socket s;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	public static String username;
	public static Packet p;

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			//setUserAgentStylesheet(STYLESHEET_CASPIAN);
			//AquaFx.style();
			Parent root = FXMLLoader.load(getClass().getResource("/application/selectserver.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean connection(String ip){
		try {
			s=new Socket(ip,12345);
		} catch (Exception e){
			return false;
		}
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch (Exception e){
			return false;
		}
		try {
			ois = new ObjectInputStream(s.getInputStream());
		}catch (Exception e){
			return false;
		}
		return true;
	}

	public static Packet request(Packet p){
		System.out.println(p.operation);
		try {
			oos.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem in sending Packet");
		}
		System.out.println("Packet sent");
		Packet received= null;
		try {
			received = (Packet)ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem in receiving Packet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem in receiving Packet");
		} catch (Exception e){
			System.out.println("Problem in receiving Packet");
		}
		System.out.println("Packet received");
		return received;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
