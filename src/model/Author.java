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
	
	/** Boolean to hold whether a user is an author or not. */
	private static boolean myIsAuthor;

	
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
	
	/**
	 * Method to set the status of an author role.
	 */
	public static void setAuthor(final boolean theAuthorStatus) {
		myIsAuthor = theAuthorStatus;
	}
	
	/**
	 * Method that returns a boolean value for whether or
	 * not a user is an author.
	 */
	public boolean isAuthor() {
		return myIsAuthor;
	}

	
}
