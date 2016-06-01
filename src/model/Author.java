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
	
	/* Generated serialization number. */
	private static final long serialVersionUID = 8150606980901061867L;

	private static final int STATUS_DECISION_HAS_NOT_BEEN_MADE = 0;

	/* List to hold manuscripts submitted by this Author. */
	private List<Manuscript> myManuscripts;

	/**
	 * @param theAuthorID - the unique username of the User becomming an Author
	 * @param theConference - the Conference for which this User will act as an Author
	 */
	public Author(final String theAuthorID, Conference theConference){
		super("Author", theAuthorID, theConference);
		myManuscripts = new ArrayList<Manuscript>();		
	}
	
	/**
	 * Getter method for the List of all Manuscripts submitted by this Author to this 
	 * Conference.
	 * @return List<Manuscript>
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
	 * Adds theManuscript to my List of submitted Manuscripts and to the master List of all
	 * submitted Manuscripts.
	 * @throws IllegalArgumentException if the given Manuscript has already been submitted, if 
	 * the Submission Deadline for submitting Manuscripts has passed, or if any of the parameters 
	 * are null.
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
	 * Method to remove a manuscript from the my List of submitted Manuscripts and from the
	 * master List of Manuscripts.
	 * @throws IllegalArgumentException if the given Manuscript is not within this Author's 
	 * List of Submitted Manuscripts, or if any of the parameters are null
	 */
	public void deleteManuscript(final List<Manuscript> theManuscripts,
			 							final Manuscript theManuscript) {
		if (Objects.isNull(theManuscripts)) {
			throw new IllegalArgumentException("List of Manuscripts cannot be null.");
		} else if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (!myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException("Manuscript is not within this Author.");
		}
		theManuscripts.remove(theManuscript);
		myManuscripts.remove(theManuscript);
	}
	
	/**
	 * Method to allow an author to edit the title of a submitted manuscript.
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
	 * Method that allows a user to see the reviews of all Manuscripts they have submitted
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
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return getMyUsername().hashCode() + myManuscripts.hashCode();
	}
	
	
}