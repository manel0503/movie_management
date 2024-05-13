import java.io.*;
import java.util.*;

public class Part2 { 
	
	/**
	 * Reads a manifest file (for this project it will be the manifest2 file with the names of the CSV genre files), serializes movie objects based on genre and creates a manifest file with the names of the SER genre files (part3_manifest)
	 * @param manifestFileName (will be part2_manifest)
	 * @return "part3_manifest.txt" (name of the file with the names of the SER genre files)
	 */
    public String do_part2(String manifestFileName) {
    	//Creating manifest3
        try(PrintWriter manifest3Writer = new PrintWriter(new FileWriter("part3_manifest.txt"));) {
            //Reading from manifest2
        	try(Scanner manifest2Reader = new Scanner(new File(manifestFileName));) {
                while(manifest2Reader.hasNextLine()) {
                	//Gets genre from manifest2 and creates an array of movies for each genre
                    String genreFileName = manifest2Reader.nextLine();
                    String genre = genreFileName.substring(0,genreFileName.indexOf('.'));
                    Movie[] movies = movieGenreArray(genreFileName);
                    
                    //Serializes the arrays and writes the names of the binary files to manifest3
                    String binaryGenreFileName = genre + ".ser";
                    serializeMovieArrays(movies, binaryGenreFileName);
                    manifest3Writer.println(binaryGenreFileName);    
                }
            }
            catch(IOException e){
                System.err.println("An error occured when reading file: " + manifestFileName);
                return null;
            }
            return "part3_manifest.txt";
        }
        catch(IOException e){
            System.err.println("An error occured when writing to file part3_manifest");
            return null;
        }
    }
    
    /**
     * Counts the number of lines in a file
     * @param fileName (name of file for which lines should be counted)
     * @return lineNumber (number of lines in that file)
     */
    private int numberOfLines(String fileName) {
        try{
            Scanner reader = new Scanner(new FileReader(fileName));
            int lineNumber = 0;
            
            while(reader.hasNextLine()) {
                lineNumber++;
                reader.nextLine();
            }
            reader.close();
            return lineNumber;
        }
        catch(IOException e) {
            System.err.println("An IOException occured when opening file: " + fileName + "to count the number of lines.");
            return -1;
        }   
    }
    
    /**
     * Reads movie records from a CSV genre file and creates an array of movie objects (all the movies of that genre)
     * @param genreFileName (name of genre file for which an array should be created)
     * @return movies (the array of movies of that genre)
     */
    private Movie[] movieGenreArray(String genreFileName) {
        int numLines = numberOfLines(genreFileName);
        //Checking if program read number of lines without problem
        if(numLines == -1) {
        	return null;
        }
        //Creating an array with length "numLines" because each line is a movie
        Movie[] movies = new Movie[numLines];
        
        try (Scanner genreFileReader = new Scanner(new FileReader(genreFileName))) {
            int index = 0;
            while(genreFileReader.hasNextLine()) {
                String line = genreFileReader.nextLine();
                
                try {
                	//Turning the movie into an array of fields
                    String[] movieFields = Part1.arrayFormat(line);
                    //Creating a movie object with each field as attribute
                    Movie movie = new Movie(Integer.parseInt(movieFields[0]), movieFields[1], Integer.parseInt(movieFields[2]), movieFields[3], movieFields[4], Double.parseDouble(movieFields[5]), movieFields[6], movieFields[7], movieFields[8], movieFields[9]);
                    //Putting that movie in the movie array
                    movies[index] = movie;
                    index++;
                }
                catch (NumberFormatException | MissingQuotesException | ExcessFieldsException | MissingFieldsException e) {
                    System.err.println("An error occured when turning a record into an array.");
                }
            }
        }
        catch(IOException e){
            System.err.println("An error occured when opening file: " + genreFileName);    
        } 
        return movies;
    }
    
    /**
     * Serializes a movie array to a binary SER file
     * @param movies (the array of movies that should be serialized to a binary file)
     * @param binaryFileName (the name of the binary file where the arrays will be serialized)
     */
    private void serializeMovieArrays(Movie[] movies, String binaryFileName) {
        try {
            ObjectOutputStream binaryFileObject = new ObjectOutputStream(new FileOutputStream(binaryFileName));
            binaryFileObject.writeObject(movies);
            binaryFileObject.close();
        } catch (IOException e) {
            System.err.println("An error occurred when creating the file: " + binaryFileName);
 
        }
    }

}
