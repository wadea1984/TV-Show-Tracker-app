package com.cognixia.tv_show_tracker;

import java.sql.Connection;

import com.cognixia.tv_show_tracker.connection.ConnectionManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            Connection c = ConnectionManager.getConnection();
            System.out.println("connection made!!!");
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
