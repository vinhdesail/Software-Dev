package model;

/**
 * Review class description
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Review {
	
	/** Reviewer is identified by their unique ID (username). */
	private String myReviewerID;
	
	/** The full text of the Review itself as a String. */
	private String myReviewText;
	
	/** The title of the Manuscript being reviewed (for reference to keep organized). */
	private String myManuscriptTitle;
	
	public Review(String theReviewerID, String theReviewText, String theManuscriptTitle) {
		myReviewerID = theReviewerID;
		myReviewText = theReviewText;
		myManuscriptTitle = theManuscriptTitle;
	}
	
	public String getReviewText() {
		return myReviewText;
	}
	
	public String getReviewerID() {
		return myReviewerID;
	}
	
	public String getManuscriptTitle() {
		return myManuscriptTitle;
	}

}
