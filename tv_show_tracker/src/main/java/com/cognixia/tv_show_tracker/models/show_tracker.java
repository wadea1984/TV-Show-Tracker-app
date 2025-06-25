package com.cognixia.tv_show_tracker.models;

//enum for status
public class show_tracker {
public enum status{
    Plan_to_Watch,
    Currently_Watching,
    Finished

}

private int show_id;
private int user_id;
private int episodes_watched;
private int rating; 
private status showStatus;
public show_tracker() {
}
public show_tracker(int show_id, int user_id, int episodes_watched, int rating, status showStatus) {
    this.show_id = show_id;
    this.user_id = user_id;
    this.episodes_watched = episodes_watched;
    this.rating = rating;
    this.showStatus = showStatus;
    
}


    /**
     * @return int return the show_id
     */
    public int getShow_id() {
        return show_id;
    }

    /**
     * @param show_id the show_id to set
     */
    public void setShow_id(int show_id) {
        this.show_id = show_id;
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
     * @return int return the episodes_watched
     */
    public int getEpisodes_watched() {
        return episodes_watched;
    }

    /**
     * @param episodes_watched the episodes_watched to set
     */
    public void setEpisodes_watched(int episodes_watched) {
        this.episodes_watched = episodes_watched;
    }

    /**
     * @return int return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return status return the showStatus
     */
    public status getShowStatus() {
        return showStatus;
    }

    /**
     * @param showStatus the showStatus to set
     */
    public void setShowStatus(status showStatus) {
        this.showStatus = showStatus;
    }
    @Override
    public String toString() {
        return "show_tracker [show_id=" + show_id + ", user_id=" + user_id + ", episodes_watched=" + episodes_watched
                + ", rating=" + rating + ", showStatus=" + showStatus + "]";
    }

    

}
