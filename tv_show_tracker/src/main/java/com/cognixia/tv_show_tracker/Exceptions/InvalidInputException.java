package com.cognixia.tv_show_tracker.Exceptions;

public class InvalidInputException extends Exception{
      
    public InvalidInputException(){
       
    }
     @Override
    public String getMessage() {
        
        return "Invalid Input.";
    }
    

}
