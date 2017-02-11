/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Garvit Gupta
 */
public class Threadclass implements Runnable{
    Socket pipe;
    Packet p=null;
    //String user,pass;
    Methods method=new Methods();
    Threadclass(Socket pipe)
    {
        this.pipe=pipe;
    }
    @Override
    public void run() {
        ObjectInputStream serverInputStream=null;
        try {
            serverInputStream = new ObjectInputStream(pipe.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream serverOutputStream=null;
        try {
            serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        //input and output  stream created 
        int i=1;
        while(true)
        {
        try {
            //System.out.println("object received.");
            p = (Packet)serverInputStream.readObject();
            System.out.println("Operation of the object received is:"+p.operation+" "+i);
            i++;
            System.out.println("Waiting to receive packet.");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
        }
                Threadthreadclass r=new Threadthreadclass(serverInputStream,serverOutputStream,p);
                Thread t=new Thread(r);
                t.start();
                System.out.println("A new thread for receiving packets has been started..");
                if(p.operation.equals("logout"))
                    break;
                /*System.out.println("object received.");
                String operation=p.operation;
                System.out.println(operation);
                if(operation.equals("signUp"))
                {
                try {
                p.response=method.signUp(p.s1,p.s2,p.s3,p.s5,p.s4);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                }
                else if(operation.equals("login"))
                {
                System.out.println(p.s1+" "+p.s2);
                try {
                p.response=method.login(p.s1,p.s2);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                }
                else if(operation.equals("venueList"))
                {
                try {
                p.rs=method.venueList();
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                }
                else if(operation.equals("schedule"))
                {
                try {
                p.rs=method.schedule(p.s1,p.date);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                }
                else if(operation.equals("bookEvent"))
                {
                try {
                p.response=method.bookEvent(p.i,p.s1,p.s2,p.s3,p.date);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else if(operation.equals("check"))
                {
                try {
                p.response=method.check(p.s1);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else if(operation.equals("userInfo"))
                {
                ResultSet temp=null;
                try {
                temp=method.userInfo(p.s1);
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                temp.next();
                } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                try{
                p.s1=temp.getString("userID");
                p.s2=temp.getString("username");
                p.s3=temp.getString("gender");
                p.s4=temp.getString("email");
                }catch(SQLException e){}
                }
                else if(operation.equals("userEvents"))
                {
                
                }
                else
                break;
                try {
                serverOutputStream.writeObject(p);
                } catch (IOException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
                
                /*user=p.s1;
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
                catch(ClassNotFoundException | SQLException e)
                {
                JOptionPane.showMessageDialog(null,e.getMessage());
                }
                
                try {
                serverOutputStream.writeObject(p);
                } catch (IOException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                serverInputStream.close();
                } catch (IOException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                serverOutputStream.close();
                } catch (IOException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
            }*/ 
    }
    
}
}
