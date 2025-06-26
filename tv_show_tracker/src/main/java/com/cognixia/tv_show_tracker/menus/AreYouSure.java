package com.cognixia.tv_show_tracker.menus;

import java.util.Scanner;

public class AreYouSure {

       public static boolean areYouSure(Scanner sc, String actionDescription) {
        while (true) {
            System.out.printf("Are you sure you want to %s? (yes/no): ", actionDescription);
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }

      
    






}
