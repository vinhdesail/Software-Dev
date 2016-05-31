package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Reviewer class description.
 * @author Edie Megan Campbell
 * @author 
 * @version 2016.05.08
 */
public class Reviewer extends Role implements Serializable {
	
	/** The maximum number of Manuscripts a Reviewer can be assigned to Review. */
	public static final int MAX_MANUSCRIPTS = 4;
	
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
	 * yet or if this Reviewer has already submitted a review for theManuscript
	 */
	public void submitReview(Manuscript theManuscript, String theReviewURL) {
		if (!myManuscripts.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		} else if (getMyReview(theManuscript) != null) {
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
	 * @param theReviewText - the full text of the (edited) Review
	 * @throws IllegalArguementException if this Reviewer is not assigned to theManuscript, or
	 * if this Reviewer has not yet submitted a review for this Manuscript to edit.
	 */
	public void editReview(Manuscript theManuscript, String theReviewText) {
		if (!myManuscripts.contains(theManuscript)) {
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
	public List<Manuscript> getMyManuscripts() {
		return myManuscripts;
	}
	
	//TODO
	/**
	 * Accessor method
	 * @return The list of paper already been reviewed.
	 */
	public List<Manuscript> getAlreadyReviewManuscript(){
		List<Manuscript> toReturn = new ArrayList<>();
		for(Manuscript manu : myManuscripts){
			for(Review rev : manu.getReviews()){
				if(rev.getReviewerID().equals(this.getMyUsername())){
					toReturn.add(manu);
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Assigns the given Manuscript to this Reviewer so that they will have the ability to 
	 * access the Manuscript and submit a Review for it.
	 * @param theManuscript
	 * @throws IllegalArgumentException if this Reviewer already has the max allowed number of papers.
	 */
	public void assignReview(Manuscript theManuscript) {
		if (myManuscripts.size() < MAX_MANUSCRIPTS) {
			myManuscripts.add(theManuscript);
		} else {
			throw new IllegalArgumentException("Can't assign more than " + MAX_MANUSCRIPTS + 
					" Manuscripts to this reviewer!");
		}	
	}
	
	/**
	 * Searches through a given Manuscript's Reviews so that this Reviewer can access their own.
	 * @param theManuscript - the Manuscript Object the review was written about
	 * @return the Review object associated with this reviewer's unique username, or null if 
	 * there is none
	 * @throws IllegalArgumentException if this Reviewer has not been assigned to review theManuscript
	 */
	public Review getMyReview(Manuscript theManuscript) throws IllegalArgumentException {
		if (!myManuscripts.contains(theManuscript)) {
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