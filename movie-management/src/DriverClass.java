import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;

/**
 * -----------------------------------------------
 * Houda Filali 40276607
 * Manel Hellou 40284245
 * COMP 249
 * Assignment #2
 * Due date March 27th 2024
 * -----------------------------------------------
 *
	* This program manages user navigation through Mr. Filmbuff's movie catalog by:
	* 1. Partition movie records
	* 2. Serialization
	* 3. Deserialization 
	*
	* It consists of five classes: Movie, Part1, Part2, Part3, DriverClass
	*
	* ----- Movie -----
	* Represents a Movie object :
	* 10 attributes: 
	* year, title, duration, genre, rating, score, director, actor1, acto2, actor3
	* Constructors, accessors, mutators 
	* Overridden methods: toString() and equals()
	* Implements Serializable
	*
	* ----- Part1 -----
	* Part1 class contains 5 methods:
	* do_part1(), CSVformat(), validateRecord(), genreFileName(), manifest2Writer()
	* Partition all valid movie records into new genre-based1 text files
	* Writes invalid records to file called "bad_movie_records.txt".
	* part2_manifest.txt : Manifest file that stores the names of the CSV files produced
	*
	* ----- Part2 -----
	* Part2 class contains 4 methods:
	* do_part2(), numberOfLines(), movieGenreArray(), serializeMovieArrays()
	* Load an array of movie records from each of the partitioned text file 
	* Serialize the resulting movie array to a binary file
	* part3_manifest.txt : Manifest file that stores the names of the binary files
	* 
	* ----- Part3 -----
	* Part3 class contains 6 methods:
	* do_part3(), displayMainMenu(), selectMovieArray(), navigateMovies(), absoluteValue(), deserializeMovieArrays().
	* Deserialize the serialized arrays from the binary files into a 2D-array of movie record objects
	* Provide an interactive program that allows the user to navigate a movie array
	* Display user-specified number of movie-records
	*
	* ----- DriverClass -----
	* part1_manifest.txt : stores names of required input files
	* Displays menu 
	* Calls the appropriate methods 
	* Choices: s (Select a movie array to navigate), n (Navigate musical movies), x (Exit)
	* Gets user input
	* Executes Part1, Part2, and Part3 functions
	*
	*/	

public class DriverClass {
	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String[] args) {
    	String part1_manifest = "part1_manifest.txt";
    	//Creating and writing to manifest1 (manifest with names of yearly movie files)
    	try (PrintWriter manifest1Writer = new PrintWriter(new FileOutputStream(part1_manifest))) {
			String[] years = {"1990","1991","1991","1993","1994","1995","1996","1997","1998","1999"};
			for(int i=0; i<years.length; i++) {
				manifest1Writer.println("Movies" + years[i] + ".csv");
			}
		}
		catch (IOException e) {
			System.out.println("");
		}
		
    	//Part1
		Part1 part1Object = new Part1();
		String part2_manifest = part1Object.do_part1(part1_manifest);
		
		//Part2
		Part2 part2Object = new Part2();
		String part3_manifest = part2Object.do_part2(part2_manifest);
    	
		//Part3 - Deserializing
        Part3 part3 = new Part3();
        Movie[][] allMovies = part3.do_part3(part3_manifest);

        //Part - Navigating
        if (allMovies != null) {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
            	part3.displayMainMenu();

                String choice = scanner.nextLine();
                
                switch (choice.toLowerCase()) {
                	case "s":
                		part3.selectMovieArray();
                    case "n":
                        part3.navigateMovies();
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
        } else {
            System.out.println("Failed to load movie arrays. Exiting program.");
        }
    }
}
