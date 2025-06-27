package com.cognixia.tv_show_tracker.models;

public class tv_shows {

private int show_id;
private String name;
private int episodes;
private String description;
private String genre;


   
    

    public tv_shows() {}

    public tv_shows(int show_id, String name, int episodes, String description,String genre) {
    this.show_id = show_id;
    this.name = name;
    this.episodes = episodes;
    this.description = description;
    this.genre=genre;
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
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return int return the episodes
     */
    public int getEpisodes() {
        return episodes;
    }

    /**
     * @param episodes the episodes to set
     */
    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
      public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "tv_shows [show_id=" + show_id + ", name=" + name + ", episodes=" + episodes + ", description="
                + description + ", genre=" + genre + "]";
    }

    

  

    

}
