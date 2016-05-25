package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Reviewer class description.
 * @author
 * @author 
 * @version 2016.05.08
 */
public class Reviewer extends Role implements Serializable {
	
	/** A Reviewer may be assigned no more than 4 papers to Review. */
	public static final int MAX_PAPERS = 4;
	
	private List<Manuscript> myPapers;

	/** Generated serialization number. */
	private static final long serialVersionUID = -3658253011793370271L;
	
	/**
	 * Constructor for Reviewer
	 * @param myUser - the unique username of the User who is becoming a Reviewer
	 * @param myConference - the Conference for which this User will be acting as a Reviewer
	 */
	public Reviewer(String myUser, Conference myConference) {
		super("Reviewer", myUser, myConference);
		myPapers = new ArrayList<>();
		
	}
	
	/**
	 * TEST ONLY
	 * Overloaded constructor for making particular instances of Reviewers in unit tests.
	 * @param myUser - the unique username of the User who is becoming a Reviewer
	 * @param myConference - the Conference for which this User will be acting as a Reviewer
	 * @param thePapers - the list of Papers assigned to this Reviewer
	 */
	public Reviewer(String myUser, Conference myConference, List<Manuscript> thePapers) {
		super("Reviewer", myUser, myConference);
		myPapers = new ArrayList<>(thePapers);
	}


	/**
	 * Submits a Review by this Reviewer for the given Manuscript
	 * @param theManuscript
	 * @param theReviewText
	 * @throws IllegalArgumentException if this Reviewer has not been assigned to theManuscript 
	 * yet or if this Reviewer has already submitted a review for theManuscript
	 */
	public void submitReview(Manuscript theManuscript, String theReviewText) {
		
		if (!myPapers.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		} else if (getMyReview(theManuscript) != null) {
			throw new IllegalArgumentException(this.getMyUsername() + "has already submitted a review for the"
					+ " Manuscript: " + theManuscript.getTitle());
			
		}
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
		theManuscript.addReview(review);

	}
	
	/**
	 * Edits a Review by this Reviewer for the given Manuscript
	 * Reviews are "edited" by removing the old review and submitting a new one.
	 * @param theManuscript - the Manuscript being reviewed
	 * @param theReviewText - the full text of the (edited) Review
	 * @throws IllegalArguementException if this Reviewer is not assigned to theManuscript, or
	 * if this Reviewer has not yet submitted a review for this Manuscript to edit.
	 */
	public void editReview(Manuscript theManuscript, String theReviewText) {
		
		if (!myPapers.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		}
		
		Review myOldReview = getMyReview(theManuscript);
		if (myOldReview == null) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not yet submitted a review for the "
								+ "Manuscript: " + theManuscript.getTitle());
		}
		theManuscript.removeReview(getMyReview(theManuscript));
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
		theManuscript.addReview(review);
		
	}
	
	
	/**
	 * Accessor method 
	 * @return returns the ArrayList of all papers assigned to this Reviewer
	 */
	public List<Manuscript> viewMyPapers() {
		return myPapers;
	}
	
	/**
	 * Assigns the given Manuscript to this Reviewer so that they will have the ability to 
	 * access the Manuscript and submit a Review for it.
	 * @param theManuscript
	 * @throws IllegalArgumentException if this Reviewer already has the max allowed number of papers.
	 */
	public void assignReview(Manuscript theManuscript) {
		if (myPapers.size() < MAX_PAPERS) {
			myPapers.add(theManuscript);
		} else {
			throw new IllegalArgumentException("Can't assign more than " + MAX_PAPERS + 
					" Manuscripts to this reviewer!");
		}	
	}
	
	
	/**
	 * Searches through a given Manuscript's Reviews so that this Reviewer can access their own.
	 * @param theManuscript
	 * @return the Review object associated with this reviewer's unique username, or null if there is none
	 * @throws IllegalArgumentException if this Reviewer has not been assigned to review theManuscript
	 */
	public Review getMyReview(Manuscript theManuscript) throws IllegalArgumentException {
		
		if (!myPapers.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		}
		
		List<Review> reviewList = theManuscript.getReviews();
		for (int i = 0; i < reviewList.size(); i++) {
			if (reviewList.get(i).getReviewerID().equals(this.getMyUsername())) {
				return theManuscript.getReviews().get(i);
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object theOther) {
		if (!(theOther instanceof Reviewer && super.equals(theOther))) {
			return false;
		}

		Reviewer theReviewer = (Reviewer) theOther;
		
		return myPapers.equals(theReviewer.myPapers);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + myPapers.hashCode();
	}
}