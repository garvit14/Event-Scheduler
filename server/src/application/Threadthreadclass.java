/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anubh
 */
public class Threadthreadclass implements Runnable{
    
    ObjectOutputStream serverOutputStream=null;
    ObjectInputStream serverInputStream=null;
    Packet p=null;
    Methods method=new Methods();
    Threadthreadclass(ObjectInputStream serverInputStream,ObjectOutputStream serverOutputStream,Packet p)
    {
        this.serverOutputStream=serverOutputStream;
        this.serverInputStream=serverInputStream;
        this.p=p;
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       // System.out.println("object received.");
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
                p.list=method.venueList();
            } catch (SQLException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else if(operation.equals("getSlotInfo"))
        {
            try {
                p.list2=method.getSlotInfo(p.s1,p.date);
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
            try {
                p=method.userInfo(p.s1);
            } catch (SQLException ex) {
                Logger.getLogger(Threadthreadclass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(operation.equals("userEvents"))
        {
            try {
                p.list2=method.userEvents(p.s1);
            } catch (SQLException ex) {
                Logger.getLogger(Threadthreadclass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
                serverOutputStream.writeObject(p);
            } catch (IOException ex) {
                Logger.getLogger(Threadclass.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
}
