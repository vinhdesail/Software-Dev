package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private String myURL;
	
	private boolean myHasBeenAssignedToASubprogramChair;
	
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
	 * @param theURL - the URL to the file location that contains the Manuscript text
	 */
	public Manuscript(String theAuthorID, String theConference, String theTitle, 
			String theURL) {
		
		myStatus = 0;
		myReviews = new ArrayList<Review>();
		myRecommendation = null;
		
		myAuthorID = theAuthorID;
		myConference = theConference;
		myTitle = theTitle;
		
		myURL = theURL;
		myHasBeenAssignedToASubprogramChair = false;
	}
	
	/**
	 * TEST ONLY
	 * Overloaded constructor for Reviewer test class, allows creating a Manuscript with a specific
	 * list of Reviews already in place.
	 * @param theAuthorID - the Author's unique username (User ID)
	 * @param theConference - the Conference's unique ID
	 * @param theTitle - the Manuscript's title
	 * @param theURL - the URL to the file location that contains the Manuscript text
	 * @param theReviews - the List of Reviews to associate with this Manuscript
	 */
	public Manuscript(String theAuthorID, String theConference, String theTitle, 
			String theURL, List<Review> theReviews) {	
		myStatus = 0;
		myReviews = new ArrayList<Review>();
		myRecommendation = null;
		myAuthorID = theAuthorID;
		myConference = theConference;
		myTitle = theTitle;
		myURL = theURL;
		myReviews = theReviews;
		myHasBeenAssignedToASubprogramChair = false;
	}
	
	/**
	 * Getter for the acceptance status of this Manuscript
	 * @return -1 if rejected, 0 if undecided, 1 if accepted
	 */
	public int getStatus() {
		return myStatus;
	}
	/**
	 * Returns the status of the manuscript, namely if the manuscript has been assigned to a Subprogram Chair.
	 * @return True if this given manuscript has been assigned to a Subprogram Chair, false if it has not. 
	 */
	public boolean hasBeenAssignedToASubprogramChair() {
		return myHasBeenAssignedToASubprogramChair;
	}
	
	/**
	 * Sets this given Manuscript's statues to be true that it has been assigned to a Subprogram Chair.
	 */
	public void becomeAssignedASubprogramChair() {
		myHasBeenAssignedToASubprogramChair = true;
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
		return myURL;
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
		myURL = newText;
	}
	
	/**
	 * Setter for Program Chair to decide whether to accept or reject a paper.
	 * @param theStatus - int, -1 if Manuscript is rejected, 1 if Manuscript is accepted.
	 */
	public void setStatus(int theStatus) {
		myStatus = theStatus;
	}
	
	/**
	 * Overrides the Object toString() method.
	 * @return String - all of the fields of the Manuscript formatted into a single String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Manuscript]\nTitle: ");
		sb.append(myTitle);
		sb.append("\nAuthor: ");
		sb.append(myAuthorID);
		sb.append("\nConference: ");
		sb.append(myConference);
		sb.append("\n\n");
		sb.append(myURL);
		sb.append("\n[End of Manuscript]");
		return sb.toString();
	}
	
	/**
	 * Overrides the Object equals method to compare all fields.
	 */
	@Override
	public boolean equals(Object theOther) {
		boolean skipper = true;//Will better work this in later.
		// first check that theOther Object is a Manuscript
		if (!theOther.getClass().equals(this.getClass())) {
			return false;
		}
		// cast theOther as a Manuscript (called other)
		Manuscript other = (Manuscript) theOther;
		// compare all fields for equality
		
		 if (Objects.isNull(myRecommendation) || Objects.isNull(other.myRecommendation)) {
			    if(Objects.nonNull(myRecommendation) && Objects.isNull(((Manuscript)theOther).myRecommendation)) {
			    	return false;
			    } else if(Objects.isNull(myRecommendation) && Objects.nonNull(other.myRecommendation)) {
			    	return false;
			    } else {
			    	skipper = false;		    	
			    }
		}
		if (myStatus != other.myStatus) {
			return false;
		} else if (!myReviews.equals(other.myReviews)) {
			return false;
		} else if(skipper && !myRecommendation.equals(other.getRecommendation())) {  
			return false;
		} else if (!myAuthorID.equals(other.myAuthorID)) {
			return false;
		} else if (!myConference.equals(other.myConference)) {
			return false;
		} else if (!myTitle.equals(other.myTitle)) {
			return false;
		} else if (!myURL.equals(other.myURL)) {
			return false;
		} 
			return true;
		
	}
	
	/**
	 * Overrides the Object hashCode method for consistency with the overridden equals method.
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}
	
//	/**
//	 * Overrides the Object hashCode method for consistency with the overridden equals method.
//	 */
//	@Override
//	public int hashCode() {
//		int hash = myStatus;
//		hash += myReviews.hashCode();
//		hash += myRecommendation.hashCode();
//		hash += myConference.hashCode();
//		hash += myAuthorID.hashCode();
//		hash += myTitle.hashCode();
//		hash += myText.hashCode();
//		return hash;
//	}
	
	

}
