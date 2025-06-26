package com.cognixia.tv_show_tracker.menus;

import java.io.EOFException;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.tv_show_tracker.Exceptions.InvalidInputException;
import com.cognixia.tv_show_tracker.dao.TrackerDAOService;
import com.cognixia.tv_show_tracker.models.users;

 //sends user to login or register page or exits the program
public class loginAndRegister {
    public static void HomePage(){
        Scanner sc=new Scanner(System.in);
        boolean check=false;
        int x=0;
       
        while (!check) {
             System.out.println("###### Welcome to the TV Show Progress Application ###########");
                System.out.println("Enter 1 to login");
                System.out.println("Enter 2 to register");
                System.out.println("Enter 3 to Exit");
                System.out.print("-->");
                try{
                    x=sc.nextInt();
                    if(x==1){
                        loginPage(sc);
                    }
                    else if(x==2){
                        registerPage(sc);
                    }
                    else if(x==3){
                        sc.close();
                        check=true;
                    }
                    else{
                        throw new InvalidInputException();
                    }                
                }
                catch(Exception e){
                    System.out.println("Invalid Input");
                    sc.nextLine();           

                }
                   
        }
       
         
         

    }
    //check logs user into the app
    public static void loginPage(Scanner sc){
        sc.nextLine();
        boolean check=false;
        String username="";
        String password="";
        TrackerDAOService ts=new TrackerDAOService();
        
        while(!check){
            
            try{
                System.out.println("Login. Enter q as a username to return to the homepage!!");
                System.out.print("Enter username: ");
                username=sc.nextLine();

                
                //returns to the home page if user enters q
                if(username.toLowerCase().equals("q")){
                    return;
                }
                System.out.print("Enter password: ");
                password=sc.nextLine();
                System.out.println(username+" "+password);
                Optional<users> user=ts.loginUser(username,password);
                if(user.isPresent()){
                         //sends user to to the movie menu
                         System.out.println(username+"is logged in");
                         check=true;
                        }
                else{
                    throw new InvalidInputException();
                }

            }
            catch(Exception e){
                System.out.println("Invalid Username or Password");
                
                
                
            }

        }
    }
    //method creates users 
    public static void registerPage(Scanner sc){
           sc.nextLine();
        boolean check=false;
        String username="";
        String password="";
        String email="";
        TrackerDAOService ts=new TrackerDAOService();
        boolean created=false;
        while(!check){
            
            try{
                System.out.println("Register. Enter q as a username to return to the homepage!!");
                System.out.print("Enter your username: ");
                username=sc.nextLine();

                
                //returns to the home page if user enters q
                if(username.toLowerCase().equals("q")){
                    return;
                }
                System.out.print("Enter your password: ");
                password=sc.nextLine();
                System.out.print("Enter email address: ");
                email=sc.nextLine();
                //checks for correct input
                if(username.length()<5||username.length()>20){
                    System.out.println("username most be greater than 4 characters and less than 21");
                    throw new InvalidInputException();
                }
                //checks for correct input
                if(password.length()<8){
                    System.out.println("Your password must be at least 8 characters. ");
                    throw new InvalidInputException();
                    
                }

                 //creates user in database
                created=ts.createUser(new users(0,username,email,password));
                    
                
                if(created){
                         
                         System.out.println("Created user with the username: "+username);
                         check=true;
                        }
                else{
                    throw new InvalidInputException();
                }

            }
            catch(Exception e){
                System.out.println("Invalid Username or Password");
                
                
            }

        }
           

    }

}
