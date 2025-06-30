package com.cognixia.tv_show_tracker.connection;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;
    private ConnectionManager(){};
    private static void makeConnection()throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties probs=new Properties();
        //added hard coded url,username, and password because resources folder was causing errors depending
        // on how the project is opened.
        String url="jdbc:mysql://localhost:3306/TV_show_tracker";
        String username="root";
        String password="password";
       
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        connection=DriverManager.getConnection(url, username, password);
    }
    public static Connection getConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        if(ConnectionManager.connection==null)
            makeConnection();
            
        

        return connection;
           
    }
    

}