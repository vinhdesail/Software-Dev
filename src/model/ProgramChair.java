package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProgramChair extends Role implements Serializable {
	
	/*Generated Serialization number.*/
	private static final long serialVersionUID = 79453357450439428L;
	/**/
	private static final int ACCEPTED = 1;
	/**/
	private static final int REJECTED = -1;
	
	
	/**
	 * The ProgramChair Constructor.
	 * @param theConference The conference that this Program Chair belongs to.
	 */
	public ProgramChair(Conference theConference , String theUserName) {
		super("Program Chair", theUserName, theConference);
	}

	/**
	 * Returns the List that of Manuscripts that belongs to a given Subprogram Chair.
	 * @param theSpc The Subprogram Chair.
	 * @return The list of Manuscripts that belong to the given Subprogram Chair.
	 * @throws IllegalArgumentException if the Given Subprogram Chair or is null.
	 */
	public List<Manuscript> showAllManuscriptAssignedToSpc(SubprogramChair theSpc) throws IllegalArgumentException {
		if(Objects.isNull(theSpc)) {
			throw new IllegalArgumentException("The Given Subprogram Chair can not be null");
		} 
		return theSpc.showAllAssignedManuscripts();	
	}
	
	/**
	 * Assigns a given Manuscript to a given Subprogram Chair.
	 * @param theSpc The Subprogram Chair.
	 * @param theManuscript The Manuscript.
	 * @throws IllegalArgumentException if the Given Subprogram Chair and/or Manuscript are null.
	 */
	public void assignManuscript(SubprogramChair theSpc, Manuscript theManuscript) throws IllegalArgumentException {
		if(Objects.isNull(theSpc)) {
			throw new IllegalArgumentException("The Given Subprogram Chair can not be null");
		} else if(Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("The Given Manuscript can not be null");
		}
		theSpc.assignManuscripts(theManuscript);
	}

	/**
	 * A Method that returns a List of all the Manuscripts that belong to the given Program Chairs Conference.  
	 * @param theManuscripts A List containing all of the Manuscripts on the system.
	 * @return A List of Manuscripts that belong to the Program Chairs Conference.
	 * @throws IllegalArgumentException if the list of Manuscripts given is null.
	 */
	public List<Manuscript> showAllManuscripts(List<Manuscript> theManuscripts) throws IllegalArgumentException{
		if(Objects.isNull(theManuscripts)) {
			throw new IllegalArgumentException("The List of Manuscripts cannot be null.");
		}
		List<Manuscript> returnManuscripts = new ArrayList<Manuscript>();
		for(int i = 0; i < theManuscripts.size(); i++){
			if(theManuscripts.get(i).getConference().equals(super.getConference().getConferenceID())) {
				returnManuscripts.add(theManuscripts.get(i));
			}
		}
		return returnManuscripts;
	}
	
	/**
	 * Get all manuscript related to a certain program chair. 
	 * @param users
	 * @return
	 */
	public Map<SubprogramChair, List<Manuscript>> findAllManuscriptsAssociatedWithEverySPC(List<User> users) {
		Map<SubprogramChair, List<Manuscript>> allSPCManus = new HashMap<>();
		SubprogramChair subprogramChairToCompareForConferenceCheck;
		for(int i = 0; i < allSPCManus.size();i++) {
			for(int j = 0; j < users.get(i).getMyConferenceRoles().size();j++) {
				if(users.get(i).getMyConferenceRoles().get(j) instanceof SubprogramChair) {
					subprogramChairToCompareForConferenceCheck = (SubprogramChair)users.get(i).getMyConferenceRoles().get(j);
					if(subprogramChairToCompareForConferenceCheck.getConference().equals(super.getConference())) {
						allSPCManus.put(subprogramChairToCompareForConferenceCheck, showAllManuscriptAssignedToSpc((SubprogramChair)users.get(i).getMyConferenceRoles().get(j)));	
					}
				}
			}							
		}
		return allSPCManus;
	}
	
	/**
	 * Get a List of all the subprogram chair.
	 * @param theUsers
	 * @return
	 */
	public List<SubprogramChair> getAllSubprogramChair(Map<String, User> theUsers){
		List<SubprogramChair> toReturn = new ArrayList<>();
		SubprogramChair subprogramChairToCompare;
		for(String temp : theUsers.keySet()){
			theUsers.get(temp).switchConference(getConference());
			List<Role> tempRole = theUsers.get(temp).getMyConferenceRoles();
			for(int i = 0; i < tempRole.size(); i++){
				if(tempRole.get(i) instanceof SubprogramChair){
					subprogramChairToCompare = (SubprogramChair)tempRole.get(i);
					if(subprogramChairToCompare.getConference().equals(super.getConference())) {
						toReturn.add(subprogramChairToCompare);
					}					
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * A Method that approves a given manuscript.
	 * @param theManuscript The Manuscript that is receiving judgment.
	 */
	public void approveManuscript(Manuscript theManuscript) {
		theManuscript.setStatus(ACCEPTED);
	}
	
	/**
	 * A Method that rejects a given manuscript.
	 * @param theManuscript The Manuscript that is receiving judgment.
	 */
	public void rejectManuscript(Manuscript theManuscript) {
		theManuscript.setStatus(REJECTED);
	}
	
	/**
	 * Return a toString representing the program chair.
	 * {@inheritDoc}
	 */
	public String toString() {
		return super.toString();
	}
	
	/**
	 * The equals method of Program Chair.
	 * {@inheritDoc}
	 */
	public boolean equals(Object theObj) {
		if(!(theObj instanceof ProgramChair)) {			
			return false;
		}
		ProgramChair pc = (ProgramChair) theObj;
		if(this.getMyUsername().equals(pc.getMyUsername())) {
			if(super.getConference().equals(pc.getConference())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(super.getConference(),this.getMyUsername());
	}
}