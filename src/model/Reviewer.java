package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Reviewer class description.
 * @author Edie Megan Campbell
 * @version 2016.05.08
 */
public class Reviewer extends Role implements Serializable {
	
	private List<Manuscript> myPapers;

	/** Generated serialization number. */
	private static final long serialVersionUID = -3658253011793370271L;
	
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
	
	public void editReview(Review theReview) {
		
	}
	
	public void deleteReview(Review theReview) {
		
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
