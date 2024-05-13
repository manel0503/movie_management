import java.io.*;
import java.util.*;

public class Part3 {
    private static final String[] possibleGenres = {"musical", "comedy", "animation", "adventure", "drama", "crime",
            "biography", "horror", "action", "documentary", "fantasy", "mystery", "sci-fi", "family", "romance", "thriller",
            "western"};
    private Movie[][] allMovies;
    private int[] currentPositions = new int[17];
    private static int genreIndex = 0;
    private static String selectedGenre = possibleGenres[genreIndex];
    
    /**
     * Reads the serialized movie arrays from a manifest file (for this project it is the manifest3 file with the names of the SER genre files),
     * and creates a 2D array (an array where each item is a genre array whose items are movies of that genre).
     * @param manifestFileName (will be part3_manifest)
     * @return allMovies (an array where each item is a genre array whose items are movies of that genre)
     */
    public Movie[][] do_part3(String manifestFileName){
    	//Creating an array of 17 genre arrays of movies, each array containing all the movies from that genre
        Movie[][] allMovies = new Movie[17][];
        //Reading the binary file names from manifest3 and deserializing each binary file and putting the array of movies of a specific genre into the appropriate genre array
        try(Scanner manifest3Reader = new Scanner(new File(manifestFileName))) {
            int indexGenre = 0;
            while(manifest3Reader.hasNextLine()) {
                String binaryFileName = manifest3Reader.nextLine();
                Movie[] movies = deserializeMovieArrays(binaryFileName);
                allMovies[indexGenre] = movies;
                indexGenre++;
            }
        }
        catch(IOException e){
            System.err.println("An error occurred when reading the file " + manifestFileName);
            return null;
        }

        this.allMovies = allMovies;
        this.currentPositions = new int[allMovies.length]; //will use later

    
        return allMovies;
    }

    /**
     * Displays the main menu
     * 3 options: selecting a movie array, navigating a movie array and exiting
     */
    public void displayMainMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("          Main Menu");
        System.out.println("-----------------------------");
        System.out.println(" s  Select a movie array to navigate");
        System.out.println(" n  Navigate " + possibleGenres[genreIndex] + " movies" + " (" + allMovies[genreIndex].length + " movies)");
        System.out.println(" x  Exit");
        System.out.println("-------------------------------");
        System.out.print(" Enter Your Choice: ");
    }

    /**
     * Displays the sub-menu (menu of all movie genres) and stores the index and name of the genre that the user chooses
     * User can also exit and go back to main menu
     */
    public void selectMovieArray() {
        System.out.println("\n-----------------------------");
        System.out.println("       Genre Sub-Menu");
        System.out.println("-----------------------------");
        for (int i = 0; i < allMovies.length; i++) {
            System.out.println(" " + (i + 1) + "  " + possibleGenres[i] + " (" + allMovies[i].length + " movies)");
        }
        System.out.println(" " + (allMovies.length + 1) + "  Exit");
        System.out.println("-------------------------------");
        System.out.print(" Enter Your Choice: ");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while(!exit) {
        	if(scanner.hasNextInt()) {
        		int choice = scanner.nextInt();
            	if(choice >=1 && choice <=17) {
                    genreIndex = choice - 1;
                    selectedGenre = possibleGenres[genreIndex];
                    navigateMovies();
                    exit = true;
            	}
            	else if(choice == 18) {
            		System.out.println("\nExiting sub-menu. \nYou will be brought back to main menu.\n");
            		exit = true;
            	}
            	else {
            		System.out.println("\nInvalid choice. Please try again.\n");
            		choice = scanner.nextInt();
            	}
        	}
        	else {
        		scanner.nextLine();
        		System.out.println("\nInvalid choice. Please try again.\n");
        	}
        }
    }
    
    /**
     * Navigates through the movies of a specific genre
     * If user enters 0 -> user is brought back to menu
     * If user enters a negative number, program display |n|-1 movie records above current position 
     * (or all records through top of the array if there are less than |n|-1 records above the current position)
     * If user enters a positive number, program display |n|-1 movie records below current position 
     * (or all records through bottom of the array if there are less than |n|-1 records below the current position)
     */
    public void navigateMovies() {
    	displayMainMenu();
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        
        while (!exit) {
            String choice = scanner.nextLine();
            
            switch (choice.toLowerCase()) {
            	case "s":
            		selectMovieArray();
                case "n":
                	System.out.println("\nNavigating " + possibleGenres[genreIndex] + " movies" + " (" + allMovies[genreIndex].length + " movies)");
                	System.out.println("Enter your choice: ");
                	Scanner in = new Scanner(System.in);
                	int n = in.nextInt();
                	if (n==0) {
                		System.out.println("\nYou will be brought back to menu.\n");
                		exit = true;
                	}
                	else {
                		int newPosition;
                		if(n<0) {
                			if((currentPositions[genreIndex] - (absoluteValue(n)-1)) < 0) {
                				newPosition = 0;
                				System.out.println("BOF has been reached.");
                			}
                			else {
                				newPosition = currentPositions[genreIndex] - (absoluteValue(n)-1);
                			}
                			for (int j = newPosition; j<=currentPositions[genreIndex];j++) {
                				System.out.println("\n------ Movie " + j +" ------");
                				allMovies[genreIndex][j].toString();
                			}
                			currentPositions[genreIndex] = newPosition;
                		}
                		else {
                			if((currentPositions[genreIndex] + (absoluteValue(n)-1)) > (allMovies[genreIndex].length-1)) {
                				newPosition = allMovies[genreIndex].length - 1;
                				System.out.println("EOF has been reached.");
                			}
                			else {
                				newPosition = currentPositions[genreIndex] + (absoluteValue(n)-1);
                			}
                			for (int j = currentPositions[genreIndex]; j<=newPosition;j++) {
                				System.out.println("\n------ Movie " + j +" ------");
                				allMovies[genreIndex][j].toString();
                			}
                			currentPositions[genreIndex] = newPosition;
                		}
                	}
                	
                    boolean exit2 = false;

                    while (!exit2) {

                    	displayMainMenu();
                        
                        String choice2 = scanner.nextLine();
                        
                        switch (choice2.toLowerCase()) {
                        	case "s":
                        		selectMovieArray();
                            case "n":
                            	navigateMovies();
                                break;
                            case "x":
                                exit = true;
                                System.out.println("\nSystem will terminate.");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("\nInvalid choice. Please try again.\n");
                                break;
                        }
                    }
                	
                    break;
                case "x":
                    exit = true;
                    System.out.println("\nSystem will terminate.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
                    break;
            }
        }
        scanner.close();

        scanner.close();
    }
    
    /**
     * Computes absolute value of an integer
     * @param x (integer for which absolute value is calculated)
     * @return -x (if x is negative) and x (if x is positive)
     */
    public int absoluteValue(int x) {
    	if(x<0) {
    		return -x;
    	}
    	else{
    		return x;
    	}
    }

    /**
     * Deserializes a binary file that contains a serialized array of movies
     * @param binaryFileName (name of binary file to deserialize)
     * @return movies (array of movies) or null (if there was error when deserializing the file)
     */
    private Movie[] deserializeMovieArrays(String binaryFileName) {
        try(ObjectInputStream binaryFileObject = new ObjectInputStream(new FileInputStream(binaryFileName))) {
            Movie[] movies = (Movie[])binaryFileObject.readObject();
            return movies;
        }
        catch (IOException | ClassNotFoundException e){
            System.err.println("\nAn error occurred when deserializing file: " + binaryFileName);
            return null;
        }
    }
}

