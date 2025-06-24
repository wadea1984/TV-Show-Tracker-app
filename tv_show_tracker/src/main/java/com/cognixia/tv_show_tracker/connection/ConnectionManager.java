package com.cognixia.tv_show_tracker.connection;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;
    private ConnectionManager(){};
    private static void makeConnection()throws Exception{
        Properties probs=new Properties();
        probs.load(new FileInputStream("src\\resources\\config.properties"));
        String url=probs.getProperty("url");
        String username=probs.getProperty("username");
        String password=probs.getProperty("password");
       
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        connection=DriverManager.getConnection(url, username, password);
    }
    public static Connection getConnection() throws Exception{
        if(ConnectionManager.connection==null)
            makeConnection();
            System.out.println("Connection made!!!!");
        

        return connection;
           
    }
    

}