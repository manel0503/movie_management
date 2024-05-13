import java.io.*;
import java.util.*;

class BadYearException extends Exception {}
class BadTitleException extends Exception {}
class BadGenreException extends Exception {}
class BadScoreException extends Exception {}
class BadDurationException extends Exception {}
class BadRatingException extends Exception {}
class BadNameException extends Exception {}
class MissingQuotesException extends Exception {}
class ExcessFieldsException extends Exception {}
class MissingFieldsException extends Exception {}

public class Part1 {
    PrintWriter badRecordsWriter = null;
    private static final String[] possibleGenres = {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror", "action", "documentary", "fantasy", "mystery", "sci-fi","family", "romance", "thriller", "western"};

    /**
     * Processes the manifest file (for this project it will be the manifest1 file with the names of the yearly movie files),
     * creates a CSV file for each genre (and writes each movie record to the appropriate genre csv file) and creates a manifest file with the names of each genre file
     * 
     * @param manifestFileName (will be manifest1 file)
     * @return "part2_manifest.txt" (the name of the manifest2 file with the names of each genre file)
     */
    public String do_part1(String manifestFileName) {
    	//Writing CSV files for each genre
    	for (int i=0; i<possibleGenres.length; i++) {
    		try (PrintWriter genreWriter = new PrintWriter(new FileOutputStream(possibleGenres[i]+".csv"))) {
    		}
    		catch (IOException e) {
    			System.err.println("An error occured when trying create CSV genre files.");
    		}
    	}

        try {
        	//Reading the names of yearly movie files
            Scanner manifestReader = new Scanner(new FileInputStream(manifestFileName));

            while (manifestReader.hasNextLine()) {
            	//Accessing and reading from yearly movie files
                String fileName = manifestReader.nextLine().trim();
                try (Scanner fileReader = new Scanner(new FileInputStream(fileName))) {
                    
                	int lineNumber = 0; //Counting number of lines for bad records (starting at 0)
 
                    while (fileReader.hasNextLine()) {
                        lineNumber++; // Incrementing the number of lines
                        
                        //Getting the movie record from the yearly movie file
                        String movieRecord = fileReader.nextLine();
                        try {
                        	//Turning the record into an array and making sure it is valid
                            String[] movieFields = arrayFormat(movieRecord);
                            validateRecord(movieFields);
                            
                            //Getting the genre of each movie and writing the record of that movie to the appropriate CSV genre file
                            String genre = movieFields[3].toLowerCase().trim();
                            PrintWriter genreFileWriter = new PrintWriter(new FileWriter(genreFileName(genre), true));
                            genreFileWriter.println(movieRecord);
                            genreFileWriter.close();
                        } catch (Exception e) { //If record is non-valid, writing it to bad records
                            badRecordWriter(e, movieRecord, fileName, lineNumber);
                        }
                    }

                } catch (IOException e) { 
                    System.err.println("An error occurred when reading from file: " + fileName);
                }
            }
            
            manifest2Writer();  //Creating and writing to manifest 2
            return "part2_manifest.txt";
            
        } catch (IOException e) {
            System.err.println("An error occurred when reading from the manifest file.");
            return null;
            
        } finally { 
            if (badRecordsWriter != null) {
                badRecordsWriter.close();
            }
        }
    }
    
    /**
     * Turns a movie record into an array with each array item being a movie field (movie attribute)
     * @param movieRecord (the movie record in CSV format)
     * @return movieFields (an array with all the movie attributes of the movie record passed as parameter)
     * @throws MissingQuotesException (if quotes are missing in the movie record in CSV format)
     * @throws ExcessFieldsException (if there are excess fields in the movie record in CSV format)
     * @throws MissingFieldsException (if there are missing fields in the movie record in CSV format)
     */
    public static String[] arrayFormat(String movieRecord) throws MissingQuotesException, ExcessFieldsException, MissingFieldsException {
	    String[] movieFields = new String[10];
	    int fieldCounter = 0;
	    int start = 0;
	    boolean isWithinQuotes = false;

	    for (int i = 0; i < movieRecord.length()-1; i++) {
	        char character = movieRecord.charAt(i);
	        
	        //Checking if we are within quotes
	        if (character == '"') {
	            isWithinQuotes = !isWithinQuotes;  
	        } 
	        
	        //If there is a comma that is not within quotes -> take everything before that comma and put it in a move field
	        else if (character == ',' && !isWithinQuotes) {
	            String field = movieRecord.substring(start, i).trim();
	            
	            //If the field was within quotes
	            boolean startsWithQuote = field.startsWith("\"");
	            boolean endsWithQuote = field.endsWith("\"");
	            //Take the field without the quotes
	            if (startsWithQuote && endsWithQuote) {
	                field = field.substring(1, field.length() - 1);
	            } 
	            //If there is only starting quotes or ending quotes and the other one is missing
	            else if (startsWithQuote || endsWithQuote) {
	                throw new MissingQuotesException();
	            }
	            
	            //Putting field in the appropriate array item and incrementing the field index
	            movieFields[fieldCounter] = field;
	            fieldCounter++;
	            
	            start = i + 1; 
	        }
	    }
	    
	    //Managing last field on its own because will not have a comma after it
	    String lastField = movieRecord.substring(start).trim();
	    if (lastField.startsWith("\"") && !lastField.endsWith("\"")) {
	        throw new MissingQuotesException();
	    }

	    movieFields[fieldCounter] = lastField;
	    fieldCounter++;

	    //Checking for missing fields
	    if (fieldCounter < 10) {
	        throw new MissingFieldsException();
	    }

	    //Checking for excess fields
	    if (fieldCounter > 10) {
	        throw new ExcessFieldsException();
	    }

	    return movieFields;
	}


    /**
     * Validates each field in the movie array
     * @param movieFields (the array of the movie to be validated)
     * @throws BadYearException (if year is missing or not between 1990 and 1999)
     * @throws BadTitleException (if title is missing)
     * @throws BadDurationException (if duration is missing or not between 30 and 300)
     * @throws BadGenreException (if genre is missing or not one of the 17 possible genres)
     * @throws BadRatingException (if rating is missing or not one of the 6 possible ratings)
     * @throws BadScoreException (if score is missing or not between 0 and 10)
     * @throws BadNameException (if name of director or actor is missing)
     */
    private static void validateRecord(String[] movieFields) throws Exception{
		
		//YEAR
		try {
			if (Integer.parseInt(movieFields[0])<1990 || Integer.parseInt(movieFields[0])>1999) {
				throw new BadYearException();
			}
		}
		catch(NumberFormatException e){
			throw new BadYearException();
		}

		
		//TITLE 
		if(movieFields[1].trim().isEmpty()) {
			throw new BadTitleException();
		}
		
		//DURATION
		try {
			if (Integer.parseInt(movieFields[2])<30 || Integer.parseInt(movieFields[2])>300) {
				throw new BadDurationException();
			}
		}
		catch(NumberFormatException e){
			throw new BadDurationException();
		}
		
		//GENRE
		boolean validGenre = false;
		if(movieFields[3].trim().isEmpty()) {
			throw new BadGenreException();
		}
		else {
			for (String genre : possibleGenres) {
				if (movieFields[3].toLowerCase().trim().equals(genre)) {
					validGenre = true;
				}
			}
			if(!validGenre) {
				throw new BadGenreException();
			}
		}
		
		//RATING
		String[] possibleRatings = {"PG", "Unrated", "G", "R", "PG-13", "NC-17"};
		boolean validRating = false;
		if(movieFields[4].trim().isEmpty()) {
			throw new BadRatingException();
		}
		else {
			for (String rating : possibleRatings) {
				if (movieFields[4].toLowerCase().trim().equals(rating)) {
					validRating = true;
				}
			}
			if(!validGenre) {
				throw new BadRatingException();
			}
		}

		//SCORE
		try {
			if (Double.parseDouble(movieFields[5])<0 || Double.parseDouble(movieFields[5])>10) {
				throw new BadScoreException();
			}
		}
		catch(NumberFormatException e){
			throw new BadScoreException();
		}
		
		//NAME
		for (int i = 6; i<=9; i++) {
			if(movieFields[i].trim().isEmpty()) {
				throw new BadNameException();
			}
		}

	}

    /**
     * Generates a name for the CSV genre file
     * @param genre (for which a file has to be created)
     * @return (name of the genre file for the genre passed as parameter)
     */
    private static String genreFileName(String genre) {
        return genre + ".csv";
    }
    
    /**
     * Writes non-valid movie records to a file along with information about those records
     * @param e (error that makes that movie record non-valid)
     * @param movieRecord (movie record that is non-valid)
     * @param fileName (name of the year file that contains that movie record)
     * @param lineNumber (line number of the movie record in the year file)
     */
    private void badRecordWriter(Exception e, String movieRecord, String fileName, int lineNumber) {
        try {
        	//Creating new bad records if there was not any before
            if (badRecordsWriter == null) {
                badRecordsWriter = new PrintWriter(new FileWriter("bad_movie_records.txt"));
            }
            
            //Getting error type (syntax or semantic)
            String errorType = (e instanceof MissingQuotesException || e instanceof MissingFieldsException || e instanceof ExcessFieldsException) ? "Syntax" : "Semantic";
            //Writing movie record information
            badRecordsWriter.println("Error: " + e.getClass().getSimpleName() + "(" + errorType + ")");
            badRecordsWriter.println("Record: " + movieRecord);
            badRecordsWriter.println("File: " + fileName);
            badRecordsWriter.println("Line: " + lineNumber);
            badRecordsWriter.println(); 
        } catch (IOException ex) {
            System.err.println("An error occurred when writing to the bad movie records file.");
        }
    }
    
    /**
     * Writes the names of the CSV genre files to a manifest (part2_manifest.txt)
     */
    private void manifest2Writer() {
    	try(PrintWriter manifest2Writer2 = new PrintWriter(new FileOutputStream("part2_manifest.txt"))) {
        	for (int i=0; i<possibleGenres.length; i++) {
        		manifest2Writer2.println(possibleGenres[i]+".csv");
        	}
		}
		catch (IOException e) {
			System.err.println("An error occured while writing to the file part2_manifest");
		}
    }

}
