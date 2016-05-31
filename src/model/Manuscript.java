package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manuscript Objects maintain data about a submitted paper.
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Manuscript implements Serializable {
	
	/** integer code for a manuscript that has been rejected by the program chair. */
	public static final int REJECT = -1;
	
	/** integer code for a manuscript that has been accepted by the program chair. */
	public static final int ACCEPT = 1;
	
	/** integer code for a manuscript that the program chair has not made a decision on yet. */
	public static final int UNDECIDED = 0;
	
	private static final long serialVersionUID = 7127767408772156417L;

	/* Manuscript acceptance status: -1 = rejected, 0 = no decision, 1 = accepted. */
	private int myStatus;
	
	/* List of all received Reviews for this Manuscript. */
	private List<Review> myReviews;
	
	/* The Recommendation given by the SubProgram Chair to this Manuscript. */
	private Recommendation myRecommendation;
	
	/* Author is identified by their unique ID (username). */
	private final String myAuthorID;
	
	/* Conference is identified by conference ID. */
	private final String myConference;
	
	/* Each Manuscript must have a Title for identification. */
	private String myTitle;
	
	/* The file path that directs to the text file of this Manuscript. */
	private String myFilePath;
	
	private boolean myHasBeenAssignedToASubprogramChair;
	
	/**
	 * @param theAuthorID - the Author's unique username (User ID)
	 * @param theConference - the Conference's unique ID
	 * @param theTitle - the Manuscript's title
	 * @param theFilePath - the file location that contains the Manuscript text
	 */
	public Manuscript(String theAuthorID, String theConference, String theTitle, 
			String theFilePath) {
		if (Objects.isNull(theAuthorID)) {
			throw new IllegalArgumentException("Author ID cannot be null.");
		} else if (Objects.isNull(theConference)) {
			throw new IllegalArgumentException("Conference ID cannot be null.");
		} else if (Objects.isNull(theTitle)) {
			throw new IllegalArgumentException("Title cannot be null.");
		} else if (Objects.isNull(theFilePath)) {
			throw new IllegalArgumentException("File Path cannot be null.");
		}
		myStatus = 0;
		myReviews = new ArrayList<Review>();
		myRecommendation = null;
		
		myAuthorID = theAuthorID;
		myConference = theConference;
		myTitle = theTitle;
		
		myFilePath = theFilePath;
		myHasBeenAssignedToASubprogramChair = false;
	}
	
	/**
	 * TEST ONLY
	 * Overloaded constructor for Reviewer and Manuscript test classes, allows creating a 
	 * Manuscript with a specific list of Reviews already in place.
	 * @param theAuthorID - the Author's unique username (User ID)
	 * @param theConference - the Conference's unique ID
	 * @param theTitle - the Manuscript's title
	 * @param theFilePath - The path that directs to the text file of this Manuscript.
	 * @param theReviews - the List of Reviews to associate with this Manuscript
	 */
	public Manuscript(String theAuthorID, String theConference, String theTitle, 
			String theFilePath, List<Review> theReviews) {	
		if (Objects.isNull(theAuthorID) || Objects.isNull(theConference) || Objects.isNull(theTitle)
				|| Objects.isNull(theFilePath) || Objects.isNull(theReviews)) {
			throw new IllegalArgumentException("Null parameter passed to TEST ONLY constructor");
		}
		myStatus = 0;
		myRecommendation = null;
		
		myAuthorID = theAuthorID;
		myConference = theConference;
		myTitle = theTitle;
		myFilePath = theFilePath;
		myReviews = new ArrayList<Review>(theReviews);
		myHasBeenAssignedToASubprogramChair = false;
	}
	
	/**
	 * Getter for the acceptance status of this Manuscript
	 * @return integer status code: -1 if rejected, 0 if undecided, 1 if accepted
	 */
	public int getStatus() {
		return myStatus;
	}
	
	/**
	 * Returns whether the manuscript has been assigned to a Subprogram Chair or not.
	 * @return True if this given manuscript has been assigned to a Subprogram Chair, false if it has not. 
	 */
	public boolean hasBeenAssignedToASubprogramChair() {
		return myHasBeenAssignedToASubprogramChair;
	}
	
	/**
	 * Sets this given Manuscript's status to be true that it has been assigned to a Subprogram Chair.
	 */
	public void setAssignedASubprogramChair() {
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
	 * Getter for file path of the Manuscript itself.
	 * @return the path of the text file containing this manuscript as a String
	 */
	public String getFilePath() {
		return myFilePath;
	}
	
	/**
	 * Adds a Review to this Manuscript's list of received Reviews.
	 * @param theReview
	 * @throws IllegalArgumentException if theReview is null or is not for this Manuscript
	 */
	public void addReview(Review theReview) {
		if (Objects.isNull(theReview)) {
			throw new IllegalArgumentException("Review cannot be null.");
		} else if (!theReview.getManuscriptTitle().equals(myTitle)) {
			throw new IllegalArgumentException("Review is not for Manuscript: " + myTitle);
		}
		myReviews.add(theReview);
	}
	
	/**
	 * For use when a Reviewer is editing their Review of a Manuscript. The old Review is
	 * removed and the new one may be added via the addReview method (above).
	 * @param theReview is the old Review to remove from the list
	 * @throws IllegalArgumentException if theReview is null, is not for this Manuscript, or
	 * is not found in this Manuscript's list of Reviews
	 */
	public void removeReview(Review theReview) {
		if (Objects.isNull(theReview)) {
			throw new IllegalArgumentException("Review cannot be null.");
		} else if (!theReview.getManuscriptTitle().equals(myTitle)) {
			throw new IllegalArgumentException("Review is not for Manuscript: " + myTitle);
		} else if (!myReviews.contains(theReview)) {
			throw new IllegalArgumentException("Review was not found in Manuscript's list.");
		}
		myReviews.remove(theReview);
		
	}
	
	/**
	 * Associates this Manuscript with the Recommendation made by the Subprogram Chair.
	 * @param theRecommendation the Recommendation made for this Manuscript
	 */
	public void setRecommendation(Recommendation theRecommendation) {
		if (Objects.isNull(theRecommendation)) {
			throw new IllegalArgumentException("Recommendation cannot be null.");
		} else if (!theRecommendation.getManuscriptTitle().equals(myTitle)) {
			throw new IllegalArgumentException("Recommendation is not for Mansucript: " + myTitle);
		} else if (!myHasBeenAssignedToASubprogramChair) {
			throw new IllegalArgumentException("Manuscript has not been assigned to a Subprogram "
					+ "Chair for recommendation.");
		}
		myRecommendation = theRecommendation;
	}
	
	/**
	 * Setter for editing the Title of a Manuscript.
	 * @param newTitle the new Title of the Manuscript
	 */
	public void setTitle(String newTitle) {
		if (Objects.isNull(newTitle)) {
			throw new IllegalArgumentException("Title cannot be null");
		}
		myTitle = newTitle;
	}
	
	/**
	 * Setter for editing the Text of a Manuscript (by replacing old text with new)
	 * @param newText the new full Text of the edited Manuscript
	 */
	public void setFilePath(String newFilePath) {
		if (Objects.isNull(newFilePath)) {
			throw new IllegalArgumentException("File path cannot be null");
		}
		myFilePath = newFilePath;
	}
	
	/**
	 * Setter for Program Chair to decide whether to accept or reject a paper.
	 * @param theStatus: integer, -1 if Manuscript is rejected, 1 if Manuscript is accepted.
	 */
	public void setStatus(int theStatus) {
		if (theStatus != REJECT && theStatus != ACCEPT && theStatus != UNDECIDED) {
			throw new IllegalArgumentException("Invalid status code");
		}
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
		sb.append("\nFile Path: ");
		sb.append(myFilePath);
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
		} else if (!myFilePath.equals(other.myFilePath)) {
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

}
