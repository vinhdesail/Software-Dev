package model;

import java.io.Serializable;

/**
 * Review class description
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Review implements Serializable {
	
	/** Generated Serialization number. */
	private static final long serialVersionUID = -886831885940281472L;

	/** Reviewer is identified by their unique ID (username). */
	private String myReviewerID;
	
	/** The title of the Manuscript being reviewed (for reference to keep organized). */
	private String myManuscriptTitle;
	
	/** The full text of the Review itself, as a String. */
	private String myReviewText;
	
	/**
	 * Constructor for a Review Object.
	 * @param theReviewerID - the Reviewer's unique username (User ID)
	 * @param theManuscriptTitle - the title of the Manuscript being reviewed
	 * @param theReviewText - the full text of the Review
	 */
	public Review(String theReviewerID, String theManuscriptTitle, String theReviewText) {
		myReviewerID = theReviewerID;
		myReviewText = theReviewText;
		myManuscriptTitle = theManuscriptTitle;
	}
	
	/**
	 * Getter for the String containing all of this Review's text
	 * @return String myReviewText
	 */
	public String getReviewText() {
		return myReviewText;
	}
	
	/**
	 * Getter for the Reviewer writing this Review
	 * @return the unique username of the Reviewer
	 */
	public String getReviewerID() {
		return myReviewerID;
	}
	
	/**
	 * Getter for the title of the Manuscript being reviewed
	 * @return the identifying title of the Manuscript
	 */
	public String getManuscriptTitle() {
		return myManuscriptTitle;
	}
	
	/**
	 * Overrides the Object toString method to display all Review fields formatted as a 
	 * String.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Review]\nReviewer: ");
		sb.append(myReviewerID);
		sb.append("\nManuscript Title: ");
		sb.append(myManuscriptTitle);
		sb.append("\n\n");
		sb.append(myReviewText);
		sb.append("\n[End of Review]");
		return sb.toString();
	}
	
	/**
	 * Overrides the Object hashCode method for consistency with the overridden equals method.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * Overrides the Object equals method to compare all fields.
	 */
	@Override
	public boolean equals(Object theOther) {
		// first check that theOther Object is a Review
		if (!theOther.getClass().equals(this.getClass())) {
			return false;
		}
		// cast theOther as a Manuscript (called other)
		Review other = (Review) theOther;
		// compare all fields for equality
		if (!myReviewerID.equals(other.myReviewerID)) {
			return false;
		} else if (!myManuscriptTitle.equals(other.myManuscriptTitle)) {
			return false;
		} else if (!myReviewText.equals(other.myReviewText)) {
			return false;
		} else {
			return true;
		}
	}

}
