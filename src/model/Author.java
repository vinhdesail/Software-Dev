package model;

/**
 * Class that represents an author.
 * 
 * @author Justin A. Clark, Josh Meigs
 * @version 1.0
 */
public class Author extends Role{
	
	/** List to hold the manuscript names. */
	private String[] myManuscripts;
	
	
	public Author() {
		super("Author");//Formating can be changed to whatever is easiest to work with.
	}
	
	/**
	 * Method that returns a list of manuscripts.
	 */
	public String[] showAllMyManuscript() {
		return myManuscripts;
	}
	
	/**
	 * Method to add a manuscript to the list.
	 */
	public static void addManuscript() {
		
	}
	
	/**
	 * Method to remove a manuscript from the list.
	 */
	public static void deleteManuscript() {
		
	}
	
	/**
	 * Method to edit a manuscript.
	 */
	public static void editManuscript() {
		
	}
	

	
	
}
