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

public class TrackerDAOService implements TrackerDAO {
    // Connection object for managing DB operations
    private Connection connection = null;

    /**
     * Establishes a connection to the database using ConnectionManager.
     * Catches and prints errors if connection fails.
     */
    @Override
    public void establishConnection() throws SQLException {
        try {
            if (connection == null) {
                connection = ConnectionManager.getConnection();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to find the class");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the open connection to the database.
     */
    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Retrieves all TV shows from the database.
     * 
     * @return List of tv_shows objects
     */
    @Override
    public List<tv_shows> getAllShows() throws SQLException {
        List<tv_shows> tvList = new ArrayList<>();
        establishConnection();

        String sql = "SELECT * FROM tv_shows";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Extract show data and populate list
        while (rs.next()) {
            int show_id = rs.getInt("show_id");
            String name = rs.getString("name");
            int episodes = rs.getInt("episodes");
            String description = rs.getString("description");
            String genre = rs.getString("genre");

            tvList.add(new tv_shows(show_id, name, episodes, description, genre));
        }

        return tvList;
    }

    /**
     * Creates a new user in the database.
     * 
     * @param user - User object to be inserted
     * @return true if insertion was successful, false otherwise
     */
    @Override
    public boolean createUser(users user) throws SQLException {
        establishConnection();
        int result = 0;

        String sql = "INSERT INTO users(username,email,password) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());

        result = ps.executeUpdate();

        return result > 0;
    }

    /**
     * Authenticates a user by checking username and password.
     * 
     * @param user_name - entered username
     * @param password  - entered password
     * @return Optional user if found, else Optional.empty
     */
    @Override
    public Optional<users> loginUser(String user_name, String password) throws SQLException, UserNotFoundException {
        establishConnection();
        users user = null;

        String sql = "SELECT * FROM users WHERE username=? and password=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user_name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("user_id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String pass = rs.getString("password");
            user = new users(id, username, email, pass);
        }

        return Optional.ofNullable(user);
    }

    /**
     * Retrieves a TV show by its ID.
     * 
     * @param show_id - ID of the show
     * @return Optional of tv_show if found
     */
    @Override
    public Optional<tv_shows> getTVshowByID(int show_id) throws SQLException {
        establishConnection();
        String sql = "SELECT * FROM tv_shows WHERE show_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, show_id);

        ResultSet rs = ps.executeQuery();
        tv_shows tv_show = null;

        while (rs.next()) {
            int id = rs.getInt("show_id");
            String name = rs.getString("name");
            int episodes = rs.getInt("episodes");
            String description = rs.getString("description");
            String genre = rs.getString("genre");

            tv_show = new tv_shows(id, name, episodes, description, genre);
        }

        return Optional.ofNullable(tv_show);
    }

    /**
     * Retrieves a list of show tracking records for a specific user.
     * 
     * @param user_id - user's ID
     * @return Optional list of show_tracker objects
     */
    @Override
    public Optional<List<show_tracker>> getShowTrackerById(int user_id) throws SQLException {
        List<show_tracker> trackerList = new ArrayList<>();
        establishConnection();

        String sql = "SELECT * FROM show_tracker st " +
                "JOIN users u ON st.user_id = u.user_id " +
                "JOIN tv_shows ts ON st.show_id = ts.show_id " +
                "WHERE u.user_id = ? " +
                "ORDER BY st.status";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, user_id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int episodes_watched = rs.getInt("episodes_watched");
            int rating = rs.getInt("rating");
            int show_id = rs.getInt("show_id");
            status showStatus = status.valueOf(rs.getString("status"));

            trackerList.add(new show_tracker(show_id, user_id, episodes_watched, rating, showStatus));
        }

        
        return Optional.ofNullable(trackerList);
    }

    /**
     * Retrieves a tracking record by user ID and show ID.
     * 
     * @param user_id - user's ID
     * @param show_id - show's ID
     * @return Optional of show_tracker
     */
    @Override
    public Optional<show_tracker> getShowTrackerByUserAndTVshow(int user_id, int show_id) throws SQLException {
        show_tracker st = null;
        establishConnection();

        String sql = "SELECT * FROM show_tracker st " +
                "JOIN users u ON st.user_id = u.user_id " +
                "JOIN tv_shows ts ON st.show_id = ts.show_id " +
                "WHERE u.user_id = ? AND ts.show_id=? " +
                "ORDER BY st.status";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setInt(2, show_id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int episodes_watched = rs.getInt("episodes_watched");
            int rating = rs.getInt("rating");
            status showStatus = status.valueOf(rs.getString("status"));

            st = new show_tracker(show_id, user_id, episodes_watched, rating, showStatus);
        }

        return Optional.ofNullable(st);
    }

    /**
     * Updates a tracking record in the database.
     * 
     * @param st - show_tracker object with updated values
     * @return true if update was successful
     */
    @Override
    public boolean updateTracker(show_tracker st) throws SQLException {
        establishConnection();

        String sql = "UPDATE show_tracker SET episodes_watched = ?, rating = ?, status = ? " +
                "WHERE show_id = ? AND user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, st.getEpisodes_watched());
        ps.setInt(2, st.getRating());
        ps.setString(3, st.getShowStatus().toString());
        ps.setInt(4, st.getShow_id());
        ps.setInt(5, st.getUser_id());

        int result = ps.executeUpdate();
        return result > 0;
    }

    /**
     * Creates a new tracker entry for a user and show.
     * 
     * @param user_id - user's ID
     * @param show_id - show's ID
     * @return true if insertion successful
     */
    @Override
    public boolean createTracker(int user_id, int show_id) throws SQLException {
        establishConnection();

        String sql = "INSERT INTO show_tracker(user_id, show_id, episodes_watched) VALUES(?,?,0)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setInt(2, show_id);

        int result = ps.executeUpdate();
        return result > 0;
    }

    /**
     * Calculates the average rating for a given show.
     * 
     * @param show_id - show's ID
     * @return average rating as double
     */
    @Override
    public double averageRating(int show_id) throws SQLException {
        establishConnection();
        double average = 0;

        String sql = "SELECT avg(rating) as average FROM show_tracker st " +
                "JOIN tv_shows ts ON ts.show_id = st.show_id " +
                "WHERE ts.show_id = ? " +
                "GROUP BY ts.name";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, show_id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            average = rs.getDouble("average");
        }

        return average;
    }
}
