package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProgramChair extends Role implements Serializable {
	
	/** Generated Serialization number. */
	private static final long serialVersionUID = 79453357450439428L;
	/**The Conference that this Program Chair belongs to.*/
	private Conference myConference;
	
	/**
	 * The ProgramChair Constructor.
	 * @param theConference The conference that this Program Chair belongs to.
	 */
	public ProgramChair(Conference theConference) {
		super("Program Chair");//Formating can be changed to whatever is easiest to work with.
		myConference = theConference; //might be changed if a copy constructor is made. 
		
	}
	/*Might not be needed*/
	/**
	 * Returns the List that of Manuscripts that belongs to a given Subprogram Chair.
	 * @param theSpc The Subprogram Chair.
	 * @return The list of Manuscripts that belong to the given Subprogram Chair.
	 */
	public List<Manuscript> showAllManuscriptAssignedToSpc(SubprogramChair theSpc) {
		return theSpc.showAllAssignedManuscripts();	
	}
	
	/**
	 * Assigns a given Manuscript to a given Subprogram Chair.
	 * @param theSpc The Subprogram Chair.
	 * @param theManuscript The Manuscript.
	 */
	public void assignManuscript(SubprogramChair theSpc, Manuscript theManuscript) {
		theSpc.assignManuscripts(theManuscript);
	}

	/**
	 * A Method that returns a List of all the Manuscripts that belong to the given Program Chairs Conference.  
	 * @param theManuscripts A List containing all of the Manuscripts on the system.
	 * @return A List of Manuscripts that belong to the Program Chairs Conference.
	 */
	public List<Manuscript> showAllManuscripts(List<Manuscript> theManuscripts) {
		List<Manuscript> returnManuscripts = new ArrayList<Manuscript>();
		
		for(int i = 0; i < theManuscripts.size(); i++){
			if(theManuscripts.get(i).getConference().equals(myConference)) {
				returnManuscripts.add(theManuscripts.get(i));
			}
		}
		return returnManuscripts;
	}
	
	
	/**
	 * A Method that approves a given manuscript.
	 * @param theManuscript The Manuscript that is receiving judgment.
	 */
	public void approveManuscript(Manuscript theManuscript) {
		//theManuscript.setStatues(1);
	}
	
	/**
	 * A Method that rejects a given manuscript.
	 * @param theManuscript The Manuscript that is receiving judgment.
	 */
	public void rejectManuscript(Manuscript theManuscript) {
		//theManuscript.setStatues(-1);
		
	}
	/*
	public List<Recommendation> showAllRecommendation(Manuscript theManuscript) {
		List<Recommendation> manuscriptRecommendation = new ArrayList<>();
		theManuscript.
		return manuscriptRecommendation;
	}
	*/
	public String toString() {
		return super.toString();
	}
}
