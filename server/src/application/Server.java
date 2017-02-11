/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author anubh
 */
/*public class Server {

    public static void main(String[] args) throws Exception {
        //Packet p= null;
        //String user,pass;
        ServerSocket socketConnection = new ServerSocket(12345);
        
        //Socket pipe=null;
        /*Thread t=new Thread(new Threadclass(pipe,socketConnection));
        t.start();*/
        
       /* while(true)
        {
        System.out.println("Server is waiting");
        Socket pipe = socketConnection.accept();
        Threadclass r = new Threadclass(pipe);
        Thread t=new Thread(r);
        t.start();
        System.out.println("Server has received connection");*/
        /*ObjectInputStream serverInputStream = new ObjectInputStream(pipe.getInputStream());
        ObjectOutputStream serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
        //System.out.println("object received.");
        p = (packet)serverInputStream.readObject();
        System.out.println("object received.");
        user=p.s1;
        pass=p.s2;
        System.out.println(user+"  "+pass);
         try
        {
        Class.forName("java.sql.DriverManager");
        Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/temporary","root","timepass123");
        Statement stmt=(Statement) con.createStatement();
        String q="Select * from User_info where name='"+user+"' and password='"+pass+"';";
        ResultSet rs=(ResultSet) stmt.executeQuery(q);
        if(!rs.next())
          p.response=false;
        else
         p.response=true;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
         
         serverOutputStream.writeObject(p);

         serverInputStream.close();
         serverOutputStream.close();*/
     /*   }
    }
    */
//}
