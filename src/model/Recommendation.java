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
	
}
