package com.cognixia.tv_show_tracker.Exceptions;

public class InvalidInputException extends Exception{
       public static String m;
    public InvalidInputException(String m){
       this.m=m;
    }
     @Override
    public String getMessage() {
        
        return m;
    }


    

}
