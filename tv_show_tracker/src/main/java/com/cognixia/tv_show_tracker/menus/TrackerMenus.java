package com.cognixia.tv_show_tracker.menus;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.cognixia.tv_show_tracker.Exceptions.InvalidInputException;
import com.cognixia.tv_show_tracker.Exceptions.UserNotFoundException;
import com.cognixia.tv_show_tracker.dao.TrackerDAOService;
import com.cognixia.tv_show_tracker.models.show_tracker;
import com.cognixia.tv_show_tracker.models.tv_shows;
import com.cognixia.tv_show_tracker.models.users;
import com.cognixia.tv_show_tracker.models.show_tracker.status;

public class TrackerMenus {
    // menu for user to choose their next action
    public static void mainPage(Scanner sc, users user) {
        boolean check = false;
        int x = 0;

        while (!check) {
            // displays menu
            System.out.println("###### Hello " + user.getUsername() + " choose from the following options###########");
            System.out.println("Enter 1 to Track TV shows");
            System.out.println("Enter 2 to view and update");
            System.out.println("Enter 3 to Log out");
            System.out.print("-->");
            try {
                x = sc.nextInt();
                if (x == 1) {
                    // goes to trackShows method
                    trackShows(sc, user);
                } else if (x == 2) {
                    updateShowTracker(user, sc);
                } else if (x == 3) {
                    sc.nextLine();
                    loginAndRegister.HomePage();
                } else {
                    throw new InvalidInputException("values must be between 1-3");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid Input\n");
                sc.nextLine();

            }

        }
    }

    public static void trackShows(Scanner sc, users user) {
        sc.nextLine();
        // create instance of TrackerDAO to access mysql
        TrackerDAOService ts = new TrackerDAOService();
        Boolean check = false;
        int x = 0;
        int y = 0;

        while (!check) {
            try {
                // displays all the tv shows
                List<tv_shows> showList = ts.getAllShows();
                System.out.println("TV Shows");
                System.out.printf("%-5s %-25s %-15s %-25s %10s\n", "Id:", "Name:", "Episodes:", "Genre", "Rating:");

                for (tv_shows show : showList) {
                    System.out.printf("%-5d %-25s %-15d %-25s %10.2f\n",
                            show.getShow_id(), show.getName(),
                            show.getEpisodes(), show.getGenre(),
                            ts.averageRating(show.getShow_id()));
                }
                // takes user back to main page when they enter -1
                System.out.print("Enter the Id of the show you would like to track(Enter -1 go back): ");
                x = sc.nextInt();
                if (x == -1) {
                    sc.nextLine();
                    return;
                }
                boolean isCreated = ts.createTracker(user.getUser_id(), x);

                Optional<tv_shows> show = ts.getTVshowByID(x);

                // creates new tracker for the show that the user chooses
                if (show.isPresent()) {
                    if (isCreated) {

                        System.out.println("You are now tracking " + show.get().getName());
                        sc.nextLine();
                        return;

                    } else {
                        System.out.println("Failed to track show");
                    }
                }

            } catch (SQLException e) {
                System.out.println("You are currently tracking this show.");
            } catch (Exception e) {

                System.out.println("Invalid input");

            }
        }
    }

    public static void updateShowTracker(users user, Scanner sc) {
        sc.nextLine();
        // create instance of TrackerDAO to access mysql
        TrackerDAOService ts = new TrackerDAOService();
        Boolean check = false;
        int x = 0;

        while (!check) {
            try {
                // displays all the tracked tv shows
                Optional<List<show_tracker>> trackerList = ts.getShowTrackerById(user.getUser_id());
                System.out.println("TV Shows");
                System.out.printf("%-5s %-25s %-10s %-15s %-15s%n",
                        "ID", "Show Name", "Watched", "Rating", "Status");
                if (!trackerList.isPresent())
                    throw new UserNotFoundException(user);

                for (show_tracker tracker : trackerList.get()) {
                    Optional<tv_shows> show = ts.getTVshowByID(tracker.getShow_id());

                    if (show.isPresent()) {
                        String episodesProgress = String.format("%d/%d",
                                tracker.getEpisodes_watched(),
                                show.get().getEpisodes());

                        System.out.printf("%-5d %-25s %-10s %-15.2f %-15s\n",
                                tracker.getShow_id(),
                                show.get().getName(),
                                episodesProgress,
                                ts.averageRating(tracker.getShow_id()),
                                tracker.getShowStatus());
                    }
                }
                // takes user back to main page when they enter -1
                System.out.print("Enter the Id of the show you would like to update(Enter -1 go back): ");
                x = sc.nextInt();
                if (x == -1) {
                    sc.nextLine();
                    return;
                }
                // creates objects for the felids that will be updated
                Optional<show_tracker> updatedTracker = ts.getShowTrackerByUserAndTVshow(user.getUser_id(), x);
                Optional<tv_shows> updatedShow = ts.getTVshowByID(x);
                if (updatedShow.isPresent() && updatedShow.isPresent()) {
                    boolean updated = false;
                    System.out.println("Your are updating " + updatedShow.get().getName());
                    System.out.print("Enter the number of episodes have you watched: ");
                    int e = sc.nextInt();
                    int total_watched = updatedTracker.get().getEpisodes_watched() + e;
                    
                     // start the episodes watched over after user finished a show
                   
                    if (updatedTracker.get().getShowStatus() == status.Finished) {
                        System.out.println("You are now watching " + updatedShow.get().getName() + " again");
                        updated = ts.updateTracker(new show_tracker(updatedShow.get().getShow_id(), user.getUser_id(),
                                e, updatedTracker.get().getRating(), status.Currently_Watching));
                    }
                    // if user completes the show the status is changed to finished
                    else if (total_watched >= updatedShow.get().getEpisodes()) {
                        System.out.println("Congratulations you have completed "
                                + updatedShow.get().getName());
                        int choice = 0;
                        while (true) {
                            System.out.print("Please rate this show from 1-5: ");
                            choice = sc.nextInt();
                            if (choice > 0 && choice < 6) {
                                break;
                            } else {
                                System.out.println("The values must be from 1-5");
                            }
                        }
                        updated = ts.updateTracker(new show_tracker(updatedShow.get().getShow_id(), user.getUser_id(),
                                updatedShow.get().getEpisodes(), choice, status.Finished));

                    }
                    // If the user is watching a new show the status is changed to plan to watch
                    else if (updatedTracker.get().getShowStatus() == status.Plan_to_Watch) {
                        System.out.println("You are now currently watching " + updatedShow.get().getName());
                        updated = ts.updateTracker(new show_tracker(updatedShow.get().getShow_id(), user.getUser_id(),
                                total_watched, 3, status.Currently_Watching));
                    }
                   
                    // if user has already currently watching a show it updates only the episodes
                    // watched
                    else {
                        System.out.println("You are currently " + updatedShow.get().getName());
                        updated = ts.updateTracker(new show_tracker(updatedShow.get().getShow_id(), user.getUser_id(),
                                total_watched,3, status.Currently_Watching));
                    }

                    if (updated) {
                        System.out.println("Your has been tracker updated");
                        sc.nextLine();
                        return;
                    } else {
                        sc.nextLine();
                        throw new InvalidInputException("Failed to update tracker");
                    }
                } else {
                    throw new UserNotFoundException(user);
                }

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println("This Id is not in your tracking list");
                e.printStackTrace();
            } catch (Exception e) {

                System.out.println("Invalid input");
                e.printStackTrace();
                sc.nextLine();

            }
        }

    }

}