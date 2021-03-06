package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Reviewer class description.
 * @author Edie Megan Campbell
 * @author 
 * @version 2016.05.08
 */
public class Reviewer extends Role implements Serializable {
	
	/** The maximum number of Manuscripts a Reviewer can be assigned to Review. */
	public static final int MAX_MANUSCRIPTS = 4;
	/*A List that contains all of this Reviewers Assigned Manuscripts. */
	private List<Manuscript> myManuscripts;
	private static final long serialVersionUID = -3658253011793370271L;
	
	/**
	 * @param theUser - the unique username of the User who is becoming a Reviewer
	 * @param theConference - the Conference for which this User will be acting as a Reviewer
	 */
	public Reviewer(String theUser, Conference theConference) {
		super("Reviewer", theUser, theConference);
		myManuscripts = new ArrayList<>();
	}
	
	/**
	 * TEST ONLY
	 * Overloaded constructor for making particular instances of Reviewers in unit tests.
	 * @param theUser - the unique username of the User who is becoming a Reviewer
	 * @param theConference - the Conference for which this User will be acting as a Reviewer
	 * @param theManuscripts - the list of Papers assigned to this Reviewer
	 */
	public Reviewer(String theUser, Conference theConference, List<Manuscript> theManuscripts) {
		super("Reviewer", theUser, theConference);
		myManuscripts = new ArrayList<>(theManuscripts);
	}

	/**
	 * Submits a Review by this Reviewer and specified by the URL of the Review contents for 
	 * the given Manuscript.
	 * @param theManuscript
	 * @param theReviewURL
	 * @throws IllegalArgumentException if this Reviewer has not been assigned to theManuscript 
	 * yet or if this Reviewer has already submitted a review for theManuscript, or if either
	 * parameter is null.
	 */
	public void submitReview(Manuscript theManuscript, String theReviewURL) {
		if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (Objects.isNull(theReviewURL)) {
			throw new IllegalArgumentException("Review file path cannot be null.");
		} else if (!myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		} else if (Objects.nonNull(getMyReview(theManuscript))) {
			throw new IllegalArgumentException(this.getMyUsername() + "has already submitted a review for the"
					+ " Manuscript: " + theManuscript.getTitle());	
		} 
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewURL);
		theManuscript.addReview(review);
	}
	
	/**
	 * Edits a Review by this Reviewer for the given Manuscript
	 * Reviews are "edited" by removing the old review and submitting a new one.
	 * @param theManuscript - the Manuscript being reviewed
	 * @param theReviewFilePath - the full text of the (edited) Review
	 * @throws IllegalArguementException if this Reviewer is not assigned to theManuscript, or
	 * if this Reviewer has not yet submitted a review for this Manuscript to edit.
	 */
	public void editReview(Manuscript theManuscript, String theReviewFilePath) {
		if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (Objects.isNull(theReviewFilePath)) {
			throw new IllegalArgumentException("Review file path cannot be null.");
		} else if (!myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		}
		Review myOldReview = getMyReview(theManuscript);
		if (Objects.isNull(myOldReview)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not yet submitted a review for the "
								+ "Manuscript: " + theManuscript.getTitle());
		}
		theManuscript.removeReview(getMyReview(theManuscript));
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewFilePath);
		theManuscript.addReview(review);
	}
	
	/**
	 * @return ArrayList of all papers assigned to this Reviewer
	 */
	public List<Manuscript> getMyManuscripts() {
		return myManuscripts;
	}
	

	/**
	 * @return ArrayList of papers from those assigned to this Reviewer that this Reviewer
	 * has already reviewed
	 */
	public List<Manuscript> getMyReviewedManuscripts(){
		List<Manuscript> myReviewedManuscripts = new ArrayList<>();
		for (Manuscript manu : myManuscripts) {
			if (Objects.nonNull(getMyReview(manu))) {
				myReviewedManuscripts.add(manu);
			}
		}
		return myReviewedManuscripts;
	}
	
	/**
	 * Assigns the given Manuscript to this Reviewer so that they will have the ability to 
	 * access the Manuscript and submit a Review for it.
	 * @param theManuscript The Given Manuscript to be assigned to this Reviewer
	 * @throws IllegalArgumentException if this Reviewer already has the max allowed number of papers.
	 */
	public void assignReview(Manuscript theManuscript) throws IllegalArgumentException {
		if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (myManuscripts.size() >= MAX_MANUSCRIPTS) {
			throw new IllegalArgumentException("Can't assign more than " + MAX_MANUSCRIPTS + 
					" Manuscripts to this reviewer!");
		} else if (myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException(getMyUsername() + " has already been assigned "
					+ "to review manuscript: " + theManuscript.getTitle());
		} else if(isAuthorOf(theManuscript)) {
			throw new IllegalArgumentException("The Manuscript was Authored by this Reviewer.");
		} else {
			myManuscripts.add(theManuscript);
		}	
	}
	/**
	 * Verifies if this instance of a Reviewer is the Author of the given Manuscript
	 * @param theManuscript The Given Manuscript to be assigned to this Reviewer
	 * @return A boolean value that represents if the Given Manuscript is Authored by this Reviewer
	 * @throws IllegalArgumentException If the Given Manuscript is null.
	 */
	public boolean isAuthorOf(Manuscript theManuscript) throws IllegalArgumentException {
		if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		}
		return theManuscript.getAuthor().equals(getMyUsername());
	}
	
	/**
	 * Searches through a given Manuscript's Reviews so that this Reviewer can access their own.
	 * @param theManuscript - the Manuscript Object the review was written about
	 * @return the Review object associated with this reviewer's unique username, or null if 
	 * there is none
	 * @throws IllegalArgumentException if this Reviewer has not been assigned to review theManuscript
	 * or if theManuscript is null
	 */
	public Review getMyReview(Manuscript theManuscript) {
		if (Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (!myManuscripts.contains(theManuscript)) {
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
	
	/**
	 * Finds the number of Manuscripts that have been assigned to this reviewer and returns that value.
	 * @return The Amount of Manuscripts that have been assigned to this Reviewer.
	 */
	public int getAssignmentSize() {
		return myManuscripts.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object theOther) {
		if (!(theOther instanceof Reviewer && super.equals(theOther))) {
			return false;
		}
		Reviewer theReviewer = (Reviewer) theOther;
		return myManuscripts.equals(theReviewer.myManuscripts);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode() + myManuscripts.hashCode();
	}
}