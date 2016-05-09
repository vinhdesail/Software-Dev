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
	
	/** String to hold the Author ID. */
	private String myAuthorID;
	
	/**
	 * Overloaded constructor to instantiate an author with author ID.
	 */
	public Author(final String theAuthorID){
		super("Author",theAuthorID);
		myAuthorID = theAuthorID;
	}
	
	/**
	 * Method that returns a list of manuscripts.
	 * @return 
	 */
	public List<Manuscript> showAllMyManuscript(final List<Manuscript> theManuscripts) {
		List<Manuscript> returnManuscripts = new ArrayList<Manuscript>();		
		for(int i = 0; i < theManuscripts.size(); i++){
			if(theManuscripts.get(i).getConference().equals(myAuthorID)) {
				returnManuscripts.add(theManuscripts.get(i));
			}
		}
		return returnManuscripts;
	}
	
	/**
	 * Method to add a manuscript to the list.
	 */
	public void addManuscript(final List<Manuscript> theManuscripts,
									 final Manuscript theManuscript) {
		theManuscripts.add(theManuscript);
	}
	
	/**
	 * Method to remove a manuscript from the list.
	 */
	public void deleteManuscript(List<Manuscript> theManuscripts,
			 							final Manuscript theManuscript) {
		int index = -1;
		for (int i = 0; i < theManuscripts.size(); i++) {
			if (theManuscripts.get(i).getTitle().equals(theManuscript.getTitle())) {
				index = i;
			}
		}
		if(index >= 0){
			theManuscripts.remove(index);
		}
	}
	
	/**
	 * Method to allow an author to resubmit an edited manuscript.
	 */
	public void editManuscript(final List<Manuscript> theManuscripts,
					final Manuscript theOldManuscript,
					Manuscript theNewManuscript) {
		deleteManuscript(theManuscripts, theOldManuscript);
		addManuscript(theManuscripts, theNewManuscript);
	}
	
	/**
	 * Method that allows a user to see the reviews of manuscripts
	 * after the program chair has made a decision.
	 */
	public List<Review> getReviews(Manuscript theManuscript) {
		List<Review> reviews = new ArrayList<>();
		for(int i = 0; i < theManuscript.getReviews().size(); i++){
			if(theManuscript.getStatus() == 1 ||
					theManuscript.getStatus() == -1) {
				reviews.add(theManuscript.getReviews().get(i));
			}
		}
		return reviews;
	}
}
