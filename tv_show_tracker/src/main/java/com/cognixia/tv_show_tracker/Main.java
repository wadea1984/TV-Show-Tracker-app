package com.cognixia.tv_show_tracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.tv_show_tracker.Exceptions.UserNotFoundException;
import com.cognixia.tv_show_tracker.connection.ConnectionManager;
import com.cognixia.tv_show_tracker.dao.TrackerDAOService;
import com.cognixia.tv_show_tracker.menus.loginAndRegister;
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
     loginAndRegister.HomePage();
    }
}
