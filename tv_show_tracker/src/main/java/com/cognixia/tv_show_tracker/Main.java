package com.cognixia.tv_show_tracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.tv_show_tracker.Exceptions.UserNotFoundException;
import com.cognixia.tv_show_tracker.connection.ConnectionManager;
import com.cognixia.tv_show_tracker.dao.TrackerDAOService;
import com.cognixia.tv_show_tracker.models.tv_shows;
import com.cognixia.tv_show_tracker.models.users;
import com.cognixia.tv_show_tracker.models.show_tracker.status;
import com.cognixia.tv_show_tracker.models.show_tracker;

/**
 * Hello world!
 *
 */

public class Main {
     static TrackerDAOService ts=new TrackerDAOService();
    public static void main(String[] args) {
     
      try{
      users user=new users(0,"johnSmith","alex@email.com","password");
      double average=ts.averageRating(1);
      

      System.out.println("average rating for Breaking bad: "+average);
    

    }
    
    catch(SQLException e){
        System.out.println("dumbass");

    }
   
    
      
    }
}
