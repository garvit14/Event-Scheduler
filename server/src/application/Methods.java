/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author Garvit Gupta
 */
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Methods {
    PreparedStatement pstmt;
    Connection con=null;
    String sql;
    ResultSet rs;
    Packet p1=new Packet();
    int lim=12;
    //Class.forName("java.sql.DriverManager");
    //whenever the instance of this class will be created, the connection with the database will be made
    Methods()
    {
        
        try {
            Class.forName("java.sql.DriverManager");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/Event_Scheduler","root","anubhav");
            pstmt = null;
            sql=null;
            rs=null;
            //make a string of query
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //zeroth method
    public boolean check(String userID) throws SQLException
    {
        sql="SELECT * FROM user WHERE userID=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,userID);
        rs=pstmt.executeQuery();
        if(!rs.next())
            return true;
        else
            return false;
    }
    //First method
    
    public boolean signUp(String userID,String username,String password,String gender,String email) throws SQLException
    {
       sql="INSERT INTO user VALUES"+"(?,?,?,?,?)";
       pstmt=con.prepareStatement(sql);
       pstmt.setString(1,userID);
       pstmt.setString(3,username);
       pstmt.setString(2,password);
       pstmt.setString(4,gender);
       pstmt.setString(5,email);
       int temp=pstmt.executeUpdate();
       if(!(temp==0))
           return true;
       else
           return false;
    } 
    
    //Second method
    
    public boolean login(String userID,String password) throws SQLException
    {
        //sql="SELECT * FROM user WHERE userID='"+userID+"' AND password='"+password+"';";
        sql="SELECT * FROM user WHERE userID=? AND password=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,userID);
        pstmt.setString(2,password);
        rs=(ResultSet)pstmt.executeQuery();
        /*Statement stmt=(Statement)con.createStatement();
        rs=stmt.executeQuery(sql);*/
        //rs=pstmt.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }
    
    //Third Method
    
    public List venueList() throws SQLException
    {
        List<String> l=new ArrayList<String>();
        sql="SELECT venuename from venues";
        pstmt=con.prepareStatement(sql);
        rs=pstmt.executeQuery();
        while(rs.next())
            l.add(rs.getString("venuename"));
        return l;
    }
    
    //Fourth method-Schedule for the selected venue 
    List getSlotInfo(String venuename,Date date) throws SQLException
   {
     List<List<String>> l=new ArrayList<List<String>>();
     Date curdate=new Date();
     int venueID=0;
     int i=0;
     int diffInDays=(date.getDate()-curdate.getDate());
     System.out.println(diffInDays);
     sql="SELECT venueID FROM venues WHERE venuename=?";
     pstmt=con.prepareStatement(sql);
     pstmt.setString(1,venuename);
     rs=pstmt.executeQuery();
     while(rs.next())
       venueID=rs.getInt("venueID");
     sql="SELECT * FROM V? LIMIT ?,?";
        pstmt=con.prepareStatement(sql);
        int rownum=diffInDays*12;
        pstmt.setInt(1,venueID);
        pstmt.setInt(2,rownum);
        pstmt.setInt(3,lim);
        rs=pstmt.executeQuery();
       while(rs.next())
       {
         l.add(new ArrayList<String>());
         l.get(i).add(rs.getString("time_slot"));
         l.get(i).add(rs.getString("status"));
         l.get(i).add(rs.getString("eventname"));
         l.get(i).add(rs.getString("hostID"));
         ++i;
       }
     return l;  
   }
    
    //Fifth method - Booking an event
    
    public boolean bookEvent(int time_slot,String venuename,String eventname,String hostID,Date date) throws SQLException//venue name
    {
       //System.out.println("info received is:"+hostID+" ");
       int venueID=0;
       sql="SELECT venueID FROM venues WHERE venuename=?";
       pstmt=con.prepareStatement(sql);
       pstmt.setString(1,venuename);
       rs=pstmt.executeQuery();
       while(rs.next())
         venueID=rs.getInt("venueID");
       Date curdate=new Date();
       int diffInDays=date.getDate()-curdate.getDate();
       int rownum=diffInDays*12+(time_slot);
       sql="UPDATE V? SET status=?,eventname=?,hostID=? WHERE sno=?";
       pstmt=con.prepareStatement(sql);
       pstmt.setInt(1,venueID);
       pstmt.setString(2,"Booked");
       pstmt.setString(3,eventname);
       pstmt.setString(4,hostID);
       pstmt.setString(5,""+rownum);
       pstmt.executeUpdate();
       return true; 
    }
    
    //Sixth Method
    List userEvents(String userID) throws SQLException
    {
        int i,venueID,j=0;
        String venuename=null;
        List<List<String>> l=new ArrayList<List<String>>();
        ResultSet rs2;
        sql="SELECT * FROM venues";
        pstmt=con.prepareStatement(sql);
        for(i=0;i<(12*14);++i)
        {
        rs=pstmt.executeQuery();
         while(rs.next())
         {
            venueID=rs.getInt("venueID");
            String time_slot;
            sql="SELECT * FROM V? LIMIT ?,1";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,venueID);
            pstmt.setInt(2,i);
            rs2=pstmt.executeQuery();
            if(rs2.next())
            {
              if(rs2.getString("hostID").equals(userID))
               {
                l.add(new ArrayList<String>());
               if(rs2.getString("time_slot").equals("9-10"))
                l.get(j).add("1");
               else if(rs2.getString("time_slot").equals("10-11"))
                l.get(j).add("2");
               else if(rs2.getString("time_slot").equals("11-12"))
                l.get(j).add("3");
               else if(rs2.getString("time_slot").equals("12-13"))
                l.get(j).add("4");
               else if(rs2.getString("time_slot").equals("13-14"))
                l.get(j).add("5");
               else if(rs2.getString("time_slot").equals("14-15"))
                l.get(j).add("6");
               else if(rs2.getString("time_slot").equals("15-16"))
                l.get(j).add("7");
               else if(rs2.getString("time_slot").equals("16-17"))
                l.get(j).add("8");
               else if(rs2.getString("time_slot").equals("17-18"))
                l.get(j).add("9");
               else if(rs2.getString("time_slot").equals("18-19"))
                l.get(j).add("10");
               else if(rs2.getString("time_slot").equals("19-20"))
                l.get(j).add("11");
               else 
                l.get(j).add("12");
                l.get(j).add(rs2.getString("venuename"));
                l.get(j).add(rs2.getString("eventname"));
                ++j;
               }
            }
         }
        }
       return l;
    }
    
    //Seventh Method
    Packet userInfo(String userID) throws SQLException
    {
        sql="SELECT * FROM user WHERE userID=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,userID);
        rs=pstmt.executeQuery();
        while(rs.next())
        {
        p1.s1=userID+"";
        p1.s2=rs.getString("username");
        p1.s3=rs.getString("gender");
        p1.s4=rs.getString("email");
        }
        return p1;
    }
   }

