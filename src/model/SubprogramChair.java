package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubprogramChair extends Role implements Serializable{

	/** Generated Serialization number. */
	private static final long serialVersionUID = -888370705327456440L;
	/** A List of all of the Manuscripts that are assigned to this given Subprogram Chair.*/
	private ArrayList<Manuscript> myAssignedManuscripts;
	
	/**
	 * The constructor for the Subprogram Chair Class.
	 */
	public SubprogramChair() {
		super("SubProgram Chair");//Formating can be changed to whatever is easiest to work with.
		myAssignedManuscripts.removeAll(myAssignedManuscripts);//will removeLater
	}
	
	/**
	 * A Method that assigns a given Review a given Manuscript.
	 * @param theReviewer The Reviewer that is being assigned.
	 * @param theManuscript The Manuscript being assigned.
	 */
	public void AssignReviewer(Reviewer theReviewer, Manuscript theManuscript) {//will need to pass a reviewer obj
		//theReviewer.assignReview(theManuscript);
	}

	
	/**
	 * A Method that returns all of the Manuscripts that have been assigned to this given Subprogram Chair.
	 * @return The List of assigned Manuscripts.
	 */
	public List<Manuscript> showAllAssignedManuscripts() {
		return myAssignedManuscripts;
	}
	
	/**
	 * A method that assigns this Subprogram Chair a given Manuscript.
	 * @param theManuscripts The Manuscript being assigned to the Subprogram Chair.
	 */
	public void assignManuscripts(Manuscript theManuscript) {
			if(myAssignedManuscripts.size()<= 4) {
				if(containsManuscript(theManuscript) == 0) {
					myAssignedManuscripts.add(theManuscript);
				} else {
					//throw exception.
				}
				
			} else {
				//throw exception.
			}
			
	}
	
	
	/**
	 * A Method that submits a Recommendation to a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript being given a Recommendation.
	 * @param theRecommendationText The Text that carries the Recommendation body.
	 */
	public void submitRecomendation(Manuscript theManuscript, String theRecommendationText) {
		
		if(myAssignedManuscripts.size() > 0 && myAssignedManuscripts.size() < 4) { //last part might not be needed.
			if(containsManuscript(theManuscript) > 0) {
				Recommendation recommendation = new Recommendation(super.getMyUsername(), theManuscript.getTitle(), theRecommendationText);
				theManuscript.setRecommendation(recommendation);									
			} else {
				//throw exception
			}
			
		} else {
			//throw exception
		}
	}
	
	
	/**
	 * A Method that returns the Text of a Recommendation of a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript that the Recommendation belongs to.
	 * @return The Recommendation Text.
	 */
	public String getRecommendationText(Manuscript theManuscript) {
		String recommendationText = "";
		int manuscriptLocation = containsManuscript(theManuscript);
		if(myAssignedManuscripts.size() > 0 && myAssignedManuscripts.size() < 4) { //last part might not be needed.
			if(manuscriptLocation > 0) {
				recommendationText = myAssignedManuscripts.get(manuscriptLocation).getText();				
			} else {
				//throw exception
			}
			
		} else {
			//throw exception
		}		
		return recommendationText;
	}
	
	
	//might not be needed. 
	/**
	 * A Method that returns the Text of a Recommendation of a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript that the Recommendation belongs to.
	 * @return The Recommendation Text.
	 */
	public void editRecomendation(Manuscript theManuscript,String theRecommendationText) {
		
		if(myAssignedManuscripts.size() > 0 && myAssignedManuscripts.size() < 4) { //last part might not be needed.
			if(containsManuscript(theManuscript) > 0) {
				Recommendation recommendation = new Recommendation(super.getMyUsername(), theManuscript.getTitle(), theRecommendationText);
				theManuscript.setRecommendation(recommendation);									
			} else {
				//throw exception
			}
			
		} else {
			//throw exception
		}
	}
	
	
	/*Might Not be needed*/
	/**
	 * A Method that sets a given Manuscripts Recommendation to be null, if the Manuscript is within the Subprogram Chairs List. 
	 * @param theManuscript 
	 */ 
	public void deleteRecomendation(Manuscript theManuscript) {
		theManuscript.setRecommendation(null);
	}
	
	/**
	 * A method that checks if this given instance of a Subprogram Chair contains the given Manuscript.
	 * @param theManuscript The Manuscript that is being searched for.
	 * @return The index of where the Manuscript was found, else 0.
	 */
	private int containsManuscript(Manuscript theManuscript) {
		int foundAt = 0;
		for(int i  = 0; i < myAssignedManuscripts.size(); i++) {
			if(myAssignedManuscripts.get(i).equals(theManuscript)) {
				foundAt = i;		
			}
		}
		return foundAt;
	}
}
