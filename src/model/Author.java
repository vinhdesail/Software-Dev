package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents an author.
 * 
 * @author Justin A. Clark, Josh Meigs
 * @version 2.0
 */
public class Author extends Role implements Serializable {
	
	/*Generated serialization number. */
	private static final long serialVersionUID = 8150606980901061867L;
	/**/
	private static final int STATUS_DECISION_HAS_NOT_BEEN_MADE = 0;

	/*List to hold manuscripts. */
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
	 * @throws IllegalArgumentException If the Given Manuscript has already been submitted, if the Submission Deadline for submitting Manuscripts,
	 * or if any of the parameters are null.
	 * is over.
	 */
	public void addManuscript(final List<Manuscript> theManuscripts,
									 final Manuscript theManuscript) throws IllegalArgumentException {		
		if(Objects.isNull(theManuscripts)) {
			throw new IllegalArgumentException("The List of Manuscripts Cannot be null");
		} else if(Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("The Manuscript Cannot be null");
		}
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
	 * @throws IllegalArgumentException if any of the parameters are null.
	 */
	public void editManuscript(final List<Manuscript> theManuscripts, final Manuscript oldManuscript,
					final String theTitle) throws IllegalArgumentException{
		if(Objects.isNull(theManuscripts)) {
			throw new IllegalArgumentException("The List of Manuscripts Cannot be null");
		} else if(Objects.isNull(oldManuscript)) {
			throw new IllegalArgumentException("The Manuscript Cannot be null");
		} else if(Objects.isNull(theTitle)) {
			throw new IllegalArgumentException("The Title Cannot be null");
		}
		oldManuscript.setTitle(theTitle);
	}
	
	/**
	 * Method that allows a user to see the reviews of manuscripts
	 * after the program chair has made a decision.
	 */
	public List<Review> getReviews() {
		List<Review> returnReviews = new ArrayList<Review>();
		for(int i = 0; i < myManuscripts.size(); i++){
			if(myManuscripts.get(i).getStatus() != STATUS_DECISION_HAS_NOT_BEEN_MADE) {
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