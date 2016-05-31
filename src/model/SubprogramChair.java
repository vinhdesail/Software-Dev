package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubprogramChair extends Role implements Serializable {

	/*Generated Serialization number.*/
	private static final long serialVersionUID = -888370705327456440L;
	/**/
	private static final int MAX_AMOUNT_OF_MANUSCRIPTS = 4;
	/**/
	private static final int NOT_FOUND = -1;
	/**/
	private static final int EMPTY = 0;
	/*A List of all of the Manuscripts that are assigned to this given Subprogram Chair.*/
	private ArrayList<Manuscript> myAssignedManuscripts;
	

	/**
	 * The constructor for the Subprogram Chair Class.
	 */
	public SubprogramChair(String theUserName, Conference theConference) {
		super("SubProgram Chair", theUserName, theConference);//Formating can be changed to whatever is easiest to work with.
		myAssignedManuscripts =  new ArrayList<Manuscript>();			
	}
	
	/**
	 * A Method that assigns a given Review a given Manuscript.
	 * @param theReviewer The Reviewer that is being assigned.
	 * @param theManuscript The Manuscript being assigned.
	 */
	public void AssignReviewer(Reviewer theReviewer, Manuscript theManuscript) {
		theReviewer.assignReview(theManuscript);
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
	 * @throws IllegalArgumentException If the given Manuscript's Conference does not match with this 
	 * Subprogram Chairs Conference, if the Manuscript has already been assigned to a Subprogram Chair,
	 * if 
	 */
	public void assignManuscripts(Manuscript theManuscript) throws IllegalArgumentException{
		if(Objects.isNull(theManuscript)){
			throw new IllegalArgumentException("The Given Manuscript cannot be null.");
		}
		if(!containsMaxAmmountOfManuscripts()) {
			int manuscriptFoundAt = (containsManuscriptAt(theManuscript));
			if(manuscriptFoundAt == NOT_FOUND) {
				if(theManuscript.getConference().equals(this.getConference().getConferenceID())) {
					myAssignedManuscripts.add(theManuscript);
					theManuscript.setAssignedASubprogramChair();
				} else{
					throw new IllegalArgumentException("The Manuscript being assigned to this SubprogramChair does not match its given conference.");
				}					
			} else {
				throw new IllegalArgumentException("Manuscript has already been assigned.");
			}			
		} else {
			throw new IllegalArgumentException("Four Manuscripts have already been assigned to this SPC.");				
		}		
	}
	
	/**
	 * A Method that submits a Recommendation to a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript being given a Recommendation.
	 * @param theRecommendationText The Text that carries the Recommendation body.
	 */
	public void submitRecomendation(Manuscript theManuscript, String theRecommendationText) throws IllegalArgumentException {		
		if(myAssignedManuscripts.size() > EMPTY && !containsMaxAmmountOfManuscripts()) { //last part might not be needed.
			int manuscriptLocation = containsManuscriptAt(theManuscript);
			if(manuscriptLocation >= EMPTY) {
				if(!Objects.nonNull(myAssignedManuscripts.get(manuscriptLocation).getRecommendation() )) {
					Recommendation recommendation = new Recommendation(super.getMyUsername(), theManuscript.getTitle(), theRecommendationText);
					theManuscript.setRecommendation(recommendation);
				} else {
					throw new IllegalArgumentException("Given Manuscript already contains a Recommendation");
				}												
			} else {
				throw new IllegalArgumentException("Manuscript not found");				
			}			
		} else {
			throw new IllegalArgumentException("No Assigned Manuscripts to submit Reccomendation for.");
		}
	}
	
	/**
	 * A Method that returns the Text of a Recommendation of a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript that the Recommendation belongs to.
	 * @return The Recommendation Text.
	 */
	public String getRecommendationText(Manuscript theManuscript) throws IllegalArgumentException {
		String recommendationText = "";
		int manuscriptLocation = containsManuscriptAt(theManuscript);
		if(myAssignedManuscripts.size() > EMPTY && !containsMaxAmmountOfManuscripts()) { //last part might not be needed.
			if(manuscriptLocation >= EMPTY) {
				recommendationText = myAssignedManuscripts.get(manuscriptLocation).getRecommendation().getRecommmendationText();				
			} else {
				throw new IllegalArgumentException("Manuscript not found");
			}			
		} else {
			throw new IllegalArgumentException("No Assigned Manuscripts to get a reccemdation for.");
		}		
		return recommendationText;
	}
	
	//might not be needed. 
	/**
	 * A Method that returns the Text of a Recommendation of a given Manuscript, if the Manuscript is within the Subprogram Chairs List.
	 * @param theManuscript The Manuscript that the Recommendation belongs to.
	 * @return The Recommendation Text.
	 */
	public void editRecomendation(Manuscript theManuscript,String theRecommendationText) throws IllegalArgumentException{    
		if(myAssignedManuscripts.size() > EMPTY && !containsMaxAmmountOfManuscripts()) { //last part might not be needed.
			if(containsManuscriptAt(theManuscript) >= EMPTY) {
				deleteRecomendation(theManuscript);
				Recommendation recommendation = new Recommendation(super.getMyUsername(), theManuscript.getTitle(), theRecommendationText);			
				theManuscript.setRecommendation(recommendation);									
			} else {
				throw new IllegalArgumentException("Manuscript not found");
			}		
		} else {
			throw new IllegalArgumentException("No Assigned Manuscripts to get a reccemdation for.");
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
	 * @return The index of where the Manuscript was found, else -1.
	 */
	public int containsManuscriptAt(Manuscript theManuscript) {
		int foundAt = NOT_FOUND;
		for(int i  = 0; i < myAssignedManuscripts.size(); i++) {
			if(myAssignedManuscripts.get(i).equals(theManuscript)) {
				foundAt = i;		
			}
		}
		return foundAt;
	}
	/**
	 * A Method that returns a boolean value that represents the state of if this Subprogram Chair contains the maximum amount of Manuscripts.
	 * @return False if this given SubprogramChair still contains room for more Manuscripts, True if it can not be assigned more. 	 
	 */
	public boolean containsMaxAmmountOfManuscripts() {
		if(myAssignedManuscripts.size() < MAX_AMOUNT_OF_MANUSCRIPTS) {
			return false;
		}
		return true;
	}

	/**
	 * Return all reviewer related to conference.
	 * @param theUsers Map of all users.
	 * @return A List of all the Reviewers for this conference.
	 */
	public List<Role> getAllReviewer(Map<String, User> theUsers){
		List<Role> toReturn = new ArrayList<>();
		for(User addUser : theUsers.values()){
			if(addUser.getConference().equals(getConference())){
				for(Role role : addUser.getMyConferenceRoles()){
					if(role instanceof Reviewer){
						toReturn.add(role);
					}
				}
			}
		}
		return toReturn;
	}
	
	public boolean equals(Object theObj) {	
		if(! (theObj instanceof SubprogramChair)) {
			return false;
		}		
		SubprogramChair spc = (SubprogramChair) theObj;
		if(this.getMyUsername().equals(spc.getMyUsername())) {
			if(this.myAssignedManuscripts.size() == spc.myAssignedManuscripts.size()) {
				for(int i = 0; i < this.myAssignedManuscripts.size(); i++) {
					if(this.containsManuscriptAt(spc.myAssignedManuscripts.get(i)) == NOT_FOUND) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
}
	

	public int hashCode() {
		return Objects.hash(this.myAssignedManuscripts,this.getMyUsername());
	}
}
