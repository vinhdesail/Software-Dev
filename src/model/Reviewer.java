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

	public void submitReview(Manuscript theManuscript, String theReviewText) {
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
		for(int i = 0; i < myPapers.size(); i++) {
			if(theManuscript.equals(myPapers.get(i))) {
				myPapers.get(i).addReview(review);
			}
		}
	}
	
	public void editReview(Review theReview, Manuscript theManuscript, String theReviewText) {
		Review review = new Review(this.getMyUsername(), theManuscript.getTitle(), theReviewText);
		for(int i = 0; i < myPapers.size(); i++) {
			if(theManuscript.equals(myPapers.get(i))) {
				myPapers.get(i).removeReview(theReview);
				myPapers.get(i).addReview(review);
			}
		}
		
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
		if(myPapers.size()<4) {
			myPapers.add(theManuscript);
		} else {
			throw new IllegalArgumentException("Can't assign more the 4 Manuscripts to this reviewer!");
		}
		
	}
}
