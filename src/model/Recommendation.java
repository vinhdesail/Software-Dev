package model;

import java.io.Serializable;

/**
 * Recommendation class description.
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Recommendation implements Serializable {
	
	/** Generated Serialization number. */
	private static final long serialVersionUID = 3412683669998140643L;

	/** Subprogram Chair is identified by their unique ID (username). */
	private String mySubprogramChairID;
	
	/** The title of the Manuscript being reviewed (for reference to keep organized). */
	private String myManuscriptTitle;
	
	/** The full text of the Recommendation itself, as a String. */
	private String myRecommendationText;
	
	/**
	 * Constructor for a Recommendation Object
	 * @param theSubprogramChairID - the Subprogram Chair's unique username (User ID)
	 * @param theManuscriptTitle - the title of the Manuscript the Recommendation is about
	 * @param theRecommendationText - the full text of the Recommendation itself
	 */
	public Recommendation(String theSubprogramChairID, String theManuscriptTitle, 
							String theRecommendationText) {
		mySubprogramChairID = theSubprogramChairID;
		myManuscriptTitle = theManuscriptTitle;
		myRecommendationText = theRecommendationText;
	}
	
	/**
	 * Getter for the Subprogram Chair who is making the recommendation
	 * @return String - the unique User ID (username) of the Subprogram Chair
	 */
	public String getSubprogramChairID() {
		return mySubprogramChairID;
	}
	
	/**
	 * Getter for the title of the Manuscript being recommended.
	 * @return String - the identifying title of the Manuscript
	 */
	public String getManuscriptTitle() {
		return myManuscriptTitle;
	}
	
	/**
	 * Getter for the full text of the Recommendation.
	 * @return String - the text of the Recommendation itself
	 */
	public String getRecommmendationText() {
		return myRecommendationText;
	}
	
	/**
	 * Overrides the Object toString method to display all Recommendation fields formatted as a 
	 * String.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Recommendation]\nSubprogram Chair: ");
		sb.append(mySubprogramChairID);
		sb.append("\nManuscript Title: ");
		sb.append(myManuscriptTitle);
		sb.append("\n\n");
		sb.append(myRecommendationText);
		sb.append("\n[End of Recommendation]");
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
		Recommendation other = (Recommendation) theOther;
		// compare all fields for equality
		if (!mySubprogramChairID.equals(other.mySubprogramChairID)) {
			return false;
		} else if (!myManuscriptTitle.equals(other.myManuscriptTitle)) {
			return false;
		} else if (!myRecommendationText.equals(other.myRecommendationText)) {
			return false;
		} else {
			return true;
		}
	}
	
}
