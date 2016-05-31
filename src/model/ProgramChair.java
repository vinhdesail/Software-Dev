package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/** 
 * @author Joshua Meigs
 * @version 2016.5.31
 */
public class ProgramChair extends Role implements Serializable {
	
	/*Generated Serialization number.*/
	private static final long serialVersionUID = 79453357450439428L;
	/*A Value that represents the Acceptance Status of "Accept"*/
	private static final int ACCEPTED = 1;
	/*A Value that represents the Acceptance Status of "Rejected"*/
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
	 * @return A List of Manuscripts that belong to the Program Chair's Conference.
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
	 * Get all Manuscripts related to a this Program Chair. 
	 * @param theUsers The List Of Users that belong to the system.
	 * @return A Map of Subprogram Chairs as a Key 
	 * and Manuscripts as its value.
	 * @throws If the Given List of Users is Null.
	 */
	public Map<SubprogramChair, List<Manuscript>> findAllManuscriptsAssociatedWithEverySPC(List<User> theUsers) throws IllegalArgumentException {
		if(Objects.isNull(theUsers)) {
			throw new IllegalArgumentException("The List of Users cannot be null.");
		}
		Map<SubprogramChair, List<Manuscript>> allSPCManus = new HashMap<>();
		SubprogramChair subprogramChairToCompareForConferenceCheck;
		for(int i = 0; i < allSPCManus.size();i++) {
			for(int j = 0; j < theUsers.get(i).getMyConferenceRoles().size();j++) {
				if(theUsers.get(i).getMyConferenceRoles().get(j) instanceof SubprogramChair) {
					subprogramChairToCompareForConferenceCheck = (SubprogramChair)theUsers.get(i).getMyConferenceRoles().get(j);
					if(subprogramChairToCompareForConferenceCheck.getConference().equals(super.getConference())) {
						allSPCManus.put(subprogramChairToCompareForConferenceCheck, showAllManuscriptAssignedToSpc((SubprogramChair)theUsers.get(i).getMyConferenceRoles().get(j)));	
					}
				}
			}							
		}
		return allSPCManus;
	}
	
	/**
	 * Get a List of all the subprogram chair.
	 * @param theUsers A Map of all The Users and their 
	 * assigned Manuscripts.
	 * @return A List of All Of the Subprogram Chairs for the
	 * conference that match's this Program Chair's conference.
	 * @throws If the Given Map of Users is Null.
	 */
	public List<SubprogramChair> getAllSubprogramChair(Map<String, User> theUsers){
		if(Objects.isNull(theUsers)) {
			throw new IllegalArgumentException("The Map of Users cannot be null.");
		}
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
	 * @throws IllegalArgumentException If the Given Manuscript is Null.
	 */
	public void approveManuscript(Manuscript theManuscript) {
		if(Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("The Given Manuscript cannot be null.");
		}
		theManuscript.setStatus(ACCEPTED);
	}
	
	/**
	 * A Method that rejects a given manuscript.
	 * @param theManuscript The Manuscript that is receiving judgment.
	 * @throws IllegalArgumentException If the Given Manuscript is Null.
	 */
	public void rejectManuscript(Manuscript theManuscript) {
		if(Objects.isNull(theManuscript)) {
			throw new IllegalArgumentException("The Given Manuscript cannot be null.");
		}
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