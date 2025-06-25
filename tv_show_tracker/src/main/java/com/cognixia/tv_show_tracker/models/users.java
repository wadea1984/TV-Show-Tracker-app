package com.cognixia.tv_show_tracker.models;

public class users {
private int user_id;
private String username;
private String email;
private String password;
public users(){}

public users(int user_id, String username, String email, String password) {
    this.user_id = user_id;
    this.username = username;
    this.email = email;
    this.password = password;
}

    /**
     * @return int return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "users [user_id=" + user_id + ", username=" + username + ", email=" + email + ", password=" + password
                + "]";
    }
    

}
