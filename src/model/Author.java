package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an author.
 * 
 * @author Justin A. Clark, Josh Meigs
 * @version 2.0
 */
public class Author extends Role implements Serializable {
	
	/** Generated serialization number. */
	private static final long serialVersionUID = 8150606980901061867L;
	
	/** List to hold the manuscripts. */
	private List<Manuscript> myManuscripts;
	
	/** String to hold the Author ID. */
	private String myAuthorID;
	
	/**
	 * Constructor instantiates an author object.
	 */
	public Author() {
		super("Author");//Formating can be changed to whatever is easiest to work with.
		myManuscripts = new ArrayList<Manuscript>();
	}
	
	/**
	 * Overloaded constructor to instantiate an author with author ID.
	 */
	public Author(final String theAuthorID){
		super("Author");
		myAuthorID = theAuthorID;
		myManuscripts = new ArrayList<Manuscript>();
	}
	
	/**
	 * Method that returns a list of manuscripts.
	 */
	public void showAllMyManuscript(final ArrayList<Manuscript> theManuscripts) {
		for (int i = 0; i < theManuscripts.size(); i++) {
			System.out.println(Integer.toString(i++)+ ") " + theManuscripts.get(i));
		}	
	}
	
	/**
	 * Method to add a manuscript to the list.
	 */
	public void addManuscript(final ArrayList<Manuscript> theManuscripts,
									 final Manuscript theManuscript) {
		theManuscripts.add(theManuscript);
	}
	
	/**
	 * Method to remove a manuscript from the list.
	 */
	public void deleteManuscript(final ArrayList<Manuscript> theManuscripts,
			 							final Manuscript theManuscript) {
		for (int i = 0; i < theManuscripts.size(); i++) {
			if (theManuscripts.get(i).getTitle() == theManuscript.getTitle()) {
				theManuscripts.remove(i);
			}
		}
	}
	
	/**
	 * Method to allow an author to resubmit an edited manuscript.
	 */
	public void editManuscript(final ArrayList<Manuscript> theManuscripts,
					final Manuscript theManuscript) {
		deleteManuscript(theManuscripts, theManuscript);
		addManuscript(theManuscripts, theManuscript);
	}
}
