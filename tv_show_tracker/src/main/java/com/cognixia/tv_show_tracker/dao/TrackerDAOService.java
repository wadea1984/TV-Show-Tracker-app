package com.cognixia.tv_show_tracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.tv_show_tracker.Exceptions.UserNotFoundException;
import com.cognixia.tv_show_tracker.connection.ConnectionManager;
import com.cognixia.tv_show_tracker.models.show_tracker;
import com.cognixia.tv_show_tracker.models.show_tracker.status;
import com.cognixia.tv_show_tracker.models.tv_shows;
import com.cognixia.tv_show_tracker.models.users;

public class TrackerDAOService implements TrackerDAO{
    private Connection connection = null;
    	@Override
	public void establishConnection() throws SQLException {
    try{
		if (connection == null) {
			connection = ConnectionManager.getConnection();
		}
    }
   
     catch(ClassNotFoundException e){
               System.out.println("Failed to find the class");
               e.printStackTrace();
        }
      catch(FileNotFoundException e){
               System.out.println("File not found");
               e.printStackTrace();
        }
    catch(IOException e){
        e.printStackTrace();
    }

	}

	@Override
	public void closeConnection() throws SQLException {
            
            
        
        connection.close();
           

	}

    @Override
    public List<tv_shows> getAllShows() throws SQLException{
        List<tv_shows> tvList=new ArrayList<>();
        establishConnection();
        String sql="SELECT * FROM tv_shows";
        PreparedStatement ps=connection.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        int show_id;
         String name;
        int episodes;
        String description;
         String genre;
        while(rs.next()){
            show_id=rs.getInt("show_id");
            name=rs.getString("name");
            episodes=rs.getInt("episodes");
            description=rs.getString("description");
            genre=rs.getString("genre");
            tvList.add(new tv_shows(show_id,name,episodes,description,genre));
            
        }


        
        return tvList;
        

         
    }

    @Override
    public boolean createUser(users user) throws SQLException{
        establishConnection();
        int result=0;
        String sql="INSERT INTO users(username,email,password) VALUES(?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3,user.getPassword());
        result=ps.executeUpdate();
        
        if(result>0)
            return true;
        else
            return false;
        
    }

    @Override
    public Optional<users> loginUser(String user_name, String password) throws SQLException,UserNotFoundException {
        establishConnection();
        users user = null;
        String sql="SELECT * FROM users WHERE username=? and password=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1,user_name);
        ps.setString(2, password);
        ResultSet rs=ps.executeQuery();
        int id=0;
        String username="";
        String pass="";
        String email="";
        while(rs.next()){
            id=rs.getInt("user_id");
            username=rs.getString("username");
            email=rs.getString("email");
            pass=rs.getString("password");
            user=new users(id,username,email,pass);
        }
        
        
        return Optional.ofNullable(user);
    }



    @Override
    public Optional<tv_shows> getTVshowByID(int show_id) throws SQLException {
        establishConnection();
        String sql="SELECT * FROM  tv_shows WHERE show_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,show_id);
        
        ResultSet rs=ps.executeQuery();
        int id;
         String name;
        int episodes;
        String description;
         String genre;
         tv_shows tv_show=null;
         while(rs.next()){
            id=rs.getInt("show_id");
            name=rs.getString("name");
            episodes=rs.getInt("episodes");
            description=rs.getString("description");
            genre=rs.getString("genre");
            tv_show=new tv_shows(id,name,episodes,description,genre);
            
        }
        
        
        return Optional.ofNullable(tv_show);
    }

    @Override
    public Optional<List<show_tracker>> getShowTrackerById(int user_id) throws SQLException {
        List<show_tracker> trackerList=new ArrayList<>();
        establishConnection();
        
         String sql = "SELECT * FROM show_tracker st "+
                 "JOIN users u ON st.user_id = u.user_id "+
                 "JOIN tv_shows ts ON st.show_id = ts.show_id "+
                 "WHERE u.user_id = ? "+
                 "ORDER BY st.status; ";
        
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,user_id);
        
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            int episodes_watched=rs.getInt("episodes_watched");
            int rating =rs.getInt("rating");
            int show_id=rs.getInt("show_id");
            status showStatus = status.valueOf(rs.getString("status"));
            trackerList.add(new show_tracker(show_id,user_id,episodes_watched,rating,showStatus));
        }
        
        closeConnection();
        return Optional.ofNullable(trackerList);
    }
      @Override
    public Optional<show_tracker> getShowTrackerByUserAndTVshow(int user_id,int show_id) throws SQLException {
        show_tracker st=null;
        establishConnection();
        
         String sql = "SELECT * FROM show_tracker st "+
                 "JOIN users u ON st.user_id = u.user_id "+
                 "JOIN tv_shows ts ON st.show_id = ts.show_id "+
                 "WHERE u.user_id = ? and ts.show_id=? "+
                 "ORDER BY st.status; ";
        
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,user_id);
         ps.setInt(2,show_id);
        
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            int episodes_watched=rs.getInt("episodes_watched");
            int rating =rs.getInt("rating");
            
            status showStatus = status.valueOf(rs.getString("status"));
            st=new show_tracker(show_id,user_id,episodes_watched,rating,showStatus);
        }
        
        
        return Optional.ofNullable(st);
    }

    @Override
    public boolean updateTracker(show_tracker st) throws SQLException {
        establishConnection();
        String sql = "UPDATE show_tracker SET episodes_watched = ?, rating = ?, status = ? " +
             "WHERE show_id = ? AND user_id = ?";

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,st.getEpisodes_watched());
        ps.setInt(2,st.getRating());
        ps.setString(3,st.getShowStatus().toString());
        ps.setInt(4,st.getShow_id());
        ps.setInt(5,st.getUser_id());
        int result=ps.executeUpdate();
        
        if(result>0)
           return true;


        return false;
        
    }

    @Override
    public boolean createTracker(int user_id, int show_id) throws SQLException {

        establishConnection();
        String sql="INSERT INTO show_tracker(user_id,show_id,episodes_watched) values(?,?,0)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,user_id);
        ps.setInt(2, show_id);
        int result=ps.executeUpdate();
        if(result>0)
           return true;
        return false;
    }

    @Override
    public double averageRating(int show_id) throws SQLException {
        establishConnection();
        double average=0;
        String sql="select avg(rating) as average from show_tracker st "+
        "JOIN tv_shows ts ON ts.show_id=st.show_id "+
        "Where ts.show_id=? "+
        "Group by ts.name ";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,show_id);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
             average=rs.getDouble("average");

        }

        return average;

    }

}
