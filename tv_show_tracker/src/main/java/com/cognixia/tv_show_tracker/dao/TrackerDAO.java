package com.cognixia.tv_show_tracker.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.tv_show_tracker.Exceptions.UserNotFoundException;
import com.cognixia.tv_show_tracker.models.show_tracker;
import com.cognixia.tv_show_tracker.models.tv_shows;
import com.cognixia.tv_show_tracker.models.users;


public interface TrackerDAO {
    
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	
	public void closeConnection() throws SQLException ;

	public List<tv_shows> getAllShows() throws SQLException;
	public boolean createUser(users user) throws SQLException;
	public Optional<users> loginUser(String user_name,String password) throws SQLException, UserNotFoundException;
	public Optional<tv_shows> getTVshowByID(int show_id) throws SQLException;
	public Optional<show_tracker> getShowTrackerByUserAndTVshow(int user_id,int show_id) throws SQLException;
	public Optional<List<show_tracker>> getShowTrackerById(int user_id) throws SQLException;
	public boolean updateTracker(show_tracker st) throws SQLException;
	public boolean createTracker(int user_id,int show_id) throws SQLException;
	public double averageRating(int show_id) throws SQLException;



	
	

}
