package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

	/** List to hold manuscripts. */
	private List<Manuscript> myManuscripts;

	/**
	 * Overloaded constructor to instantiate an author with author ID.
	 */
	public Author(final String theAuthorID, Conference theConference){
		super("Author", theAuthorID, theConference);
		myManuscripts = new ArrayList<Manuscript>();		
	}
	
	/**
	 * Method that returns a list of manuscripts.
	 * @return 
	 */
	public List<Manuscript> showAllMyManuscript(final List<Manuscript> theManuscripts, final String theAuthorID) {
		List<Manuscript> returnManuscripts = new ArrayList<Manuscript>();	
		for(int i = 0; i < theManuscripts.size(); i++){
			if(theManuscripts.get(i).getAuthor().equals(theAuthorID)) {
				returnManuscripts.add(theManuscripts.get(i));
			}
		}
		return returnManuscripts;
	}
	
	/**
	 * Method that checks if the current time is after the submission deadline.
	 * @return True if the submission deadline has been passed, false otherwise.
	 */
	public boolean isPastSubmissionDeadline() {
		Calendar currentTime = Calendar.getInstance();
		if(currentTime.after(getConference().getManuscriptDueDate())) {
			return true;
		} 
		return false;
	}
	
	/**
	 * Method to add a manuscript to the list.
	 */
	public void addManuscript(final List<Manuscript> theManuscripts,
									 final Manuscript theManuscript) throws IllegalArgumentException {		
		if(isPastSubmissionDeadline()) {
			throw new IllegalArgumentException("Manuscript Submission Deadline is over.");
		}
		theManuscripts.add(theManuscript);
		myManuscripts.add(theManuscript);
	}
	
	/**
	 * Method to remove a manuscript from the list.
	 */
	public void deleteManuscript(final List<Manuscript> theManuscripts,
			 							final Manuscript theManuscript) {
		theManuscripts.remove(theManuscript);
		myManuscripts.remove(theManuscript);
	}
	
	/**
	 * Method to allow an author to resubmit an edited manuscript.
	 */
	public void editManuscript(final List<Manuscript> theManuscripts, final Manuscript oldManuscript,
					final Manuscript theManuscript) {
		deleteManuscript(theManuscripts, oldManuscript);
		addManuscript(theManuscripts, theManuscript);

	}
	
	/**
	 * Method that allows a user to see the reviews of manuscripts
	 * after the program chair has made a decision.
	 */
	public List<Review> getReviews() {
		List<Review> returnReviews = new ArrayList<Review>();
		for(int i = 0; i < myManuscripts.size(); i++){
			if(myManuscripts.get(i).getStatus() != 0) {
				returnReviews.addAll(myManuscripts.get(i).getReviews());
			}
		}
		return returnReviews;
	}
}