package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Recommendation class description.
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class Recommendation implements Serializable {
	
	/* Generated Serialization number. */
	private static final long serialVersionUID = 3412683669998140643L;

	/* Subprogram Chair is identified by their unique ID (username). */
	private String mySubprogramChairID;
	
	/* The title of the Manuscript being reviewed (for reference to keep organized). */
	private String myManuscriptTitle;
	
	/* The file path that directs to the text file of this Recommendation. */
	private String myFilePath;
	
	/**
	 * Constructor for a Recommendation Object
	 * @param theSubprogramChairID - the Subprogram Chair's unique username (User ID)
	 * @param theManuscriptTitle - the title of the Manuscript the Recommendation is about
	 * @param theRecommendationText - the full text of the Recommendation itself
	 * @throws IllegalArgumentException if any of the parameters are null
	 */
	public Recommendation(String theSubprogramChairID, String theManuscriptTitle, 
							String theRecommendationText) {
		if (Objects.isNull(theSubprogramChairID)) {
			throw new IllegalArgumentException("Subprogram Chair ID cannot be null.");
		} else if (Objects.isNull(theManuscriptTitle)) {
			throw new IllegalArgumentException("Manuscript title cannot be null.");
		} else if (Objects.isNull(theRecommendationText)) {
			throw new IllegalArgumentException("File path cannot be null.");
		}
		mySubprogramChairID = theSubprogramChairID;
		myManuscriptTitle = theManuscriptTitle;
		myFilePath = theRecommendationText;
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
		return myFilePath;
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
		sb.append(myFilePath);
		sb.append("\n[End of Recommendation]");
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
		
		if (!(theOther instanceof Recommendation)) {
			return false;
		}
		// cast theOther as a Recommendation (called other)
		Recommendation other = (Recommendation) theOther;
		// compare all fields for equality
		return myFilePath.equals(other.myFilePath) && myManuscriptTitle.equals(other.myManuscriptTitle)
				&& mySubprogramChairID.equals(other.mySubprogramChairID);
	}	
}
