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
	public List<Manuscript> showAllMyManuscripts() {		
		return myManuscripts;
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
	 * @throws IllegalArgumentException If the Given Manuscript has already been submitted, or if the Submission Deadline for submitting Manuscripts
	 * is over.
	 */
	public void addManuscript(final List<Manuscript> theManuscripts,
									 final Manuscript theManuscript) throws IllegalArgumentException {		
		if(myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException("Manuscript has already been submitted");
		}
		if(isPastSubmissionDeadline()) {
			throw new IllegalArgumentException("Manuscript Submission Deadline is over.");
		}
		theManuscripts.add(theManuscript);
		myManuscripts.add(theManuscript);
	}
	
	/**
	 * Method to remove a manuscript from the list.
	 * @throws IllegalArgumentException If the Given Manuscript is not within this Authors List of Submitted Manuscripts.
	 */
	public void deleteManuscript(final List<Manuscript> theManuscripts,
			 							final Manuscript theManuscript) throws IllegalArgumentException {
		if(!myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException("Manuscript is not within this Author.");
		}
		theManuscripts.remove(theManuscript);
		myManuscripts.remove(theManuscript);
	}
	
	/**
	 * Method to allow an author to resubmit an edited manuscript.
	 */
	public void editManuscript(final List<Manuscript> theManuscripts, final Manuscript oldManuscript,
					final String theTitle) {
		oldManuscript.setTitle(theTitle);
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
	
	@Override
	public boolean equals(Object theObject){
		if(!(theObject instanceof Author)) {
			return false;
		}
		Author otherAuthor = (Author) theObject;
		if(!super.equals(otherAuthor)) {
			return false;
		} else if(!myManuscripts.equals(otherAuthor.showAllMyManuscripts())) {
			return false;
		}
		return true;
	}
	
	
}