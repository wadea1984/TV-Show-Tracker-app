package com.cognixia.tv_show_tracker.Exceptions;

import com.cognixia.tv_show_tracker.models.users;

public class UserNotFoundException extends Exception{
    private users user;
    public UserNotFoundException(users user){
       this.user=user;
    }
    @Override
    public String getMessage() {
        
        return "User "+this.user.getUsername()+" is not found";
    }
    
    
    
}