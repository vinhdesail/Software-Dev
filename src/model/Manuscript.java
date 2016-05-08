package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manuscript class description
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Manuscript implements Serializable {
	
	/** Generated Serialization number. */
	private static final long serialVersionUID = 7127767408772156417L;

	/** Manuscript acceptance status: -1 = rejected, 0 = no decision, 1 = accepted. */
	private int myStatus;
	
	/** List of all received Reviews for this Manuscript. */
	private List<Review> myReviews;
	
	/** The Recommendation given by the SubProgram Chair to this Manuscript. */
	private Recommendation myRecommendation;
	
	/** Author is identified by their unique ID (username). */
	private final String myAuthorID;
	
	/** Conference is identified by conference ID. */
	private final String myConference;
	
	/** Each Manuscript must have a Title for identification. */
	private String myTitle;
	
	/** The full text of the manuscript itself, as a String. */
	private String myText;
	
//	/** 
//	 * The no-argument constructor creates a Manuscript object but all fields must be manually
//	 * set user setter methods.
//	 */
//	public Manuscript() {
//		myStatus = 0;
//		myReviews = new ArrayList<Review>();
//	}
	
	/**
	 * Creates a Manuscript object with all fields specified by parameters.
	 * @param theAuthorID - the Author's unique username (User ID)
	 * @param theConference - the Conference's unique ID
	 * @param theTitle - the Manuscript's title
	 * @param theText - the full text of the Manuscript
	 */
	public Manuscript(String theAuthorID, String theConference, String theTitle, 
			String theText) {
		
		myStatus = 0;
		myReviews = new ArrayList<Review>();
		myRecommendation = null;
		
		myAuthorID = theAuthorID;
		myConference = theConference;
		myTitle = theTitle;
		myText = theText;
	}
	
	/**
	 * Getter for the acceptance status of this Manuscript
	 * @return -1 if rejected, 0 if undecided, 1 if accepted
	 */
	public int getStatus() {
		return myStatus;
	}
		
	/**
	 * Setter for the acceptance status of this Manuscript	 
	 * @param The New statues for the Manuscript.
	 */
	public void setStatus(int theStatues) {
		if(theStatues >= -1 && theStatues <=1) {
			this.myStatus = theStatues;
		} else {
			//throw exception
		}		
	}
	
	/**
	 * Getter for the list of received Reviews for this Manuscript.
	 * @return the ArrayList of Reviews (may be an empty list if no Reviews received)
	 */
	public List<Review> getReviews() {
		return myReviews;
	}
	
	/**
	 * Getter for the Recommendation of the Subprogram Chair on this Manuscript.
	 * @return the Recommendation made by the Subprogram Chair, or null if no Recommendation
	 * has been made
	 */
	public Recommendation getRecommendation() {
		return myRecommendation;
	}
	
	/**
	 * Getter for the Author of the manuscript
	 * @return the unique User ID of the registered user who authored this Manuscript
	 */
	public String getAuthor() {
		return myAuthorID;
	}
	
	/**
	 * Getter for the Conference this Manuscript was submitted to.
	 * @return the unique ID of the Conference
	 */
	public String getConference() {
		return myConference;
	}
	
	/**
	 * Getter for the Title of this Manuscript.
	 * @return the title of the paper as a String
	 */
	public String getTitle() {
		return myTitle;
	}
	
	/**
	 * Getter for the full text of the Manuscript itself.
	 * @return the text of the Manuscript as a String
	 */
	public String getText() {
		return myText;
	}
	
	/**
	 * Adds a Review to this Manuscript's list of received Reviews
	 * @param theReview
	 */
	public void addReview(Review theReview) {
		myReviews.add(theReview);
	}
	
	/**
	 * For use when a Reviewer is editing their Review of a Manuscript. The old Review is
	 * removed and the new one may be added via the addReview method (above).
	 * @param theReview the old Review to remove from the list
	 * @return true if the Review was found and removed, false if theReview was not in list.
	 */
	public boolean removeReview(Review theReview) {
		for (int i = 0; i < myReviews.size(); i++) {
			if (myReviews.get(i).equals(theReview)) {
				myReviews.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Associates this Manuscript with the Recommendation made by the Subprogram Chair.
	 * @param theRecommendation the Recommendation made for this Manuscript
	 */
	public void setRecommendation(Recommendation theRecommendation) {
		myRecommendation = theRecommendation;
	}
	
	/**
	 * Setter for editing the Title of a Manuscript.
	 * @param newTitle the new Title of the Manuscript
	 */
	public void setTitle(String newTitle) {
		myTitle = newTitle;
	}
	
	/**
	 * Setter for editing the Text of a Manuscript (by replacing old text with new)
	 * @param newText the new full Text of the edited Manuscript
	 */
	public void setText(String newText) {
		myText = newText;
	}
	
	

}