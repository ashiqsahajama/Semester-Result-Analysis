package Model;

import java.io.*;
import java.util.*;
import java.sql.*;

public class demo {
    public ArrayList<String> loginUser() throws Exception{
        String url="jdbc:mysql://localhost:3306/miniproj";
        String user="root";
        String sqlpassword="ssnce";

        Class.forName("com.mysql.jdbc.Driver").newInstance();

        System.out.println("Hey I am after forname method");
        
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproj?autoReconnect=true&useSSL=false",user,sqlpassword);

        Statement st=con.createStatement();
        
        System.out.println("Hey I am after create statement");
        ArrayList<String> list = new ArrayList<>();
        try {
            String s=String.format("select * from CAT");
            ResultSet rs=st.executeQuery(s);
            
            while(rs.next()) {
                String t = "";
                for(int i=1; i<=9; i++) {
                    t += rs.getString(i) + " - ";   
                }
                System.out.println(t);
                list.add(t);
            }
            
            rs.close();
            
        }
       catch(Exception e){
           System.out.println(e);
       }

       st.close();
       con.close();

       return list;

    }
    
}
