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
	
	private List<Manuscript> myPapers;

	/** Generated serialization number. */
	private static final long serialVersionUID = -3658253011793370271L;
	
	/**
	 * Constructor for Reviewer
	 * @param myUser - the unique username of the User who is becoming a Reviewer
	 */
	public Reviewer(String myUser) {
		super("Reviewer", myUser);
		myPapers = new ArrayList<>();
		
	}

//	public void submitReview(Manuscript theManuscript, String theReviewText) {
//		
//		if (!myPapers.contains(theManuscript)) {
//			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
//					+ "to review the Manuscript: " + theManuscript.getTitle());
//		} else if (getMyReview(theManuscript) != null) {
//			
//		}
//		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
//		theManuscript.addReview(review);
//	}
	
	/**
	 * Submits a Review by this Reviewer containing the given reviewText
	 * Reviews are "edited" by removing the old review and submitting a new one.
	 * @param theReview - the old review to be deleted
	 * @param theManuscript - the Manuscript being reviewed
	 * @param theReviewText - the full text of the Review itself
	 */
	public void submitReview(Manuscript theManuscript, String theReviewText) 
			throws IllegalArgumentException {
		
		if (!myPapers.contains(theManuscript)) {
			throw new IllegalArgumentException(this.getMyUsername() + " has not been assigned "
					+ "to review the Manuscript: " + theManuscript.getTitle());
		}
		
		Review myOldReview = getMyReview(theManuscript);
		if (myOldReview != null) {
			theManuscript.removeReview(getMyReview(theManuscript));
		}
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
		theManuscript.addReview(review);
		
	}
	
	public void deleteReview(Review theReview) {
		// search through myPapers for the paper associated with theReview
		for (int i = 0; i < myPapers.size(); i++) {
			// for each of myPapers, access that Manuscript's list of reviews
			List<Review> reviewList = myPapers.get(i).getReviews();
			// search through the list of reviews for theReview
			for (int j = 0; j < reviewList.size(); j++) {
				// if found, this is the correct paper, so removeReview from this paper
				if (reviewList.get(j).equals(theReview)) {
					myPapers.get(i).removeReview(theReview);
				}
			}
		}
		
	}
	
	public List<Manuscript> viewMyPapers() {
		return myPapers;
	}
	
	public void assignReview(Manuscript theManuscript) {
		if (myPapers.size() < 4) {
			myPapers.add(theManuscript);
		} else {
			throw new IllegalArgumentException("Can't assign more the 4 Manuscripts to this reviewer!");
		}	
	}
	
	/**
	 * Searches through a given Manuscript's Reviews so that this Reviewer can access their own.
	 * @param theManuscript
	 * @return the Review object associated with this reviewer's unique username
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
}
