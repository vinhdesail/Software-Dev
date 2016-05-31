
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Review class description
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class Review implements Serializable {
	
	/* Generated Serialization number. */
	private static final long serialVersionUID = -886831885940281472L;

	/* Reviewer is identified by their unique ID (username). */
	private String myReviewerID;
	
	/* The title of the Manuscript being reviewed (for reference to keep organized). */
	private String myManuscriptTitle;
	
	/* The file path that directs to the text file of this Review. */
	private String myFilePath;
	
	/**
	 * @param theReviewerID - the Reviewer's unique username (User ID)
	 * @param theManuscriptTitle - the title of the Manuscript being reviewed
	 * @param theReviewFilePath - the file path that directs to the text file of this Review
	 * @throws IllegalArgumentException if any parameter is null
	 */
	public Review(String theReviewerID, String theManuscriptTitle, String theReviewFilePath) {
		if (Objects.isNull(theReviewerID)) {
			throw new IllegalArgumentException("Reviewer ID cannot be null.");
		} else if (Objects.isNull(theManuscriptTitle)) {
			throw new IllegalArgumentException("Manuscript title cannot be null.");
		} else if (Objects.isNull(theReviewFilePath));
		myReviewerID = theReviewerID;
		myFilePath = theReviewFilePath;
		myManuscriptTitle = theManuscriptTitle;
	}
	
	/**
	 * Getter for the String file path that directs to the text file of the Review.
	 * @return String myReviewText
	 */
	public String getReviewFilePath() {
		return myFilePath;
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
		sb.append("\nFile Path: ");
		sb.append(myFilePath);
		sb.append("\n[End of Review]");
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 * Overrides the Object hashCode method for consistency with the overridden equals method.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 * Overrides the Object equals method to compare all fields.
	 */
	@Override
	public boolean equals(Object theOther) {
		
		if (! (theOther instanceof Review)) {
			return false;
		}
		// cast theOther as a Manuscript (called other)
		Review other = (Review) theOther;
		// compare all fields for equality
		return myFilePath.equals(other.myFilePath) && myManuscriptTitle.equals(other.myManuscriptTitle)
				&& myReviewerID.equals(other.myReviewerID);
	}
}
