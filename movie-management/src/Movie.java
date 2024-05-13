import java.io.Serializable;

/**
 * The Movie class represents movies with the following attributes: year, title, duration, genre, rating, score, director, actor1, actor2, actor3
 * It implements serializable to support serialization (that will be done later).
 * It has a constructor, accessor and mutator methods for each attribute, an equals mehtod and a toString method.
 */

public class Movie implements Serializable {
	private int year;
	private String title;
	private int duration;
	private String genre;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;
	

	/**
	 * Movie constructor 
	 * @param year
	 * @param title 
	 * @param duration
	 * @param genre
	 * @param rating
	 * @param score
	 * @param director
	 * @param actor1
	 * @param actor2
	 * @param actor3
	 */
	public Movie(int year, String title, int duration, String genre, String rating, double score, String director, String actor1, String actor2, String actor3) {
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating; 
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
	}
	
	/**
	 * Gets (accesses) the year of the movie 
	 * @return year
	 */
	public int getYear() {
		return this.year;
	}
	
	/**
	 * Gets (accesses) the title of the movie 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Gets (accesses) the duration of the movie 
	 * @return duration
	 */
	public int getDuration() {
		return this.duration;
	}
	
	/**
	 * Gets (accesses) the genre of the movie 
	 * @return genre
	 */
	public String getGenre() {
		return this.genre;
	}
	
	/**
	 * Gets (accesses) the rating of the movie 
	 * @return rating
	 */
	public String getRating() {
		return this.rating;
	}
	
	/**
	 * Gets (accesses) the score of the movie 
	 * @return score
	 */
	public double getScore() {
		return this.score;
	}
	
	/**
	 * Gets (accesses) the director of the movie 
	 * @return director
	 */
	public String getDirector() {
		return this.director;
	}
	
	/**
	 * Gets (accesses) the first actor of the movie 
	 * @return actor1
	 */
	public String getActor1() {
		return this.actor1;
	}
	
	/**
	 * Gets (accesses) the second actor of the movie 
	 * @return actor2
	 */
	public String getActor2() {
		return this.actor2;
	}
	
	/**
	 * Gets (accesses) the third actor of the movie 
	 * @return actor3
	 */
	public String getActor3() {
		return this.actor3;
	}
	
	/**
	 * Sets the year of the movie
	 * @param year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Sets the title of the movie
	 * @param year to title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Sets the duration of the movie
	 * @param year to duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Sets the genre of the movie
	 * @param genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Sets the rating of the movie
	 * @param rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	/**
	 * Sets the score of the movie
	 * @param score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/**
	 * Sets the director of the movie
	 * @param director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	
	/**
	 * Sets the first actor of the movie
	 * @param actor1 to set
	 */
	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}
	
	/**
	 * Sets the second actor of the movie
	 * @param actor2 to set
	 */
	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}
	
	/**
	 * Sets the third actor of the movie
	 * @param actor3 to set
	 */
	public void setActor3(String actor3) {
		this.actor3 = actor3;
	}
	
	/**
	 * Tests if two movies are equal (same object, or same attributes)
	 * @param otherMovie to compare current movie to
	 * @return true (equal) or false (not equal)
	 */
	public boolean equals(Movie otherMovie) {
		if (this == otherMovie) {
			return true;
		}
		else if(otherMovie == null || this.getClass() != otherMovie.getClass()) {
			return false;
		}
		else {
			return (this.year == otherMovie.year && this.title.equals(otherMovie.title) && this.duration == otherMovie.duration
					&& this.genre.equals(otherMovie.genre) && this.rating.equals(otherMovie.rating) &&this.score == otherMovie.score
					&& this.director.equals(otherMovie.director) && this.actor1.equals(otherMovie.actor1) 
					&& this.actor2.equals(otherMovie.actor2) && this.actor3.equals(otherMovie.actor3));
		}
	}
	
	/**
	 * Turns the movie into a string 
	 * @return a string representation of the movie
	 */
	public String toString() {
		String movieInformation = ("Year: " + this.year + "\nTitle: " + this.title + "\nDuration: " + this.duration + "\nGenre: " + this.genre
		+ "\nRating: " + this.rating + "\nScore: " + this.score + "\nDirector: " + this.director + "\nActor 1: " + this.actor1
		+ "\nActor 2: " + this.actor2 + "\nActor 3: " + this.actor3);
		System.out.println(movieInformation);
		return movieInformation;
		
	}
	
}
