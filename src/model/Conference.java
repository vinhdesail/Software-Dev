package model;

import java.io.Serializable;

/**
 * Conference class description.
 * @author Edie Megan Campbell
 * @version 2016.05.05
 */
public class Conference implements Serializable {
	
	/** Generated Serialization number. */
	private static final long serialVersionUID = -156166686815320306L;

	/** A unique name to identify this Conference. */
	private String myConferenceID;
	
	/** Program Chair is identified by their unique username (User ID) .*/
	private String myProgramChairID;
	
	private Date myConferenceDate;
	
	private Date myManuscriptDueDate;
	
	private Date myReviewDueDate;
	
	private Date myRecDueDate;
	
	private Date myDecisionDueDate;
	
	public Conference(String theConferenceID, String theProgramChairID, Date theConferenceDate, 
						Date theManuscriptDueDate, Date theReviewDueDate, Date theRecDueDate,
						Date theDecisionDueDate) {
		myConferenceID = theConferenceID;
		myProgramChairID = theProgramChairID;
		myConferenceDate = theConferenceDate;
		myManuscriptDueDate = theManuscriptDueDate;
		myReviewDueDate = theReviewDueDate;
		myRecDueDate = theRecDueDate;
		myDecisionDueDate = theDecisionDueDate;
	}
	
	public String getConferenceID() {
		return myConferenceID;
	}
	
	public String getProgramChairID() {
		return myProgramChairID;
	}
	
	public Date getConferenceDate() {
		return myConferenceDate;
	}
	
	public Date getManuscriptDueDate() {
		return myManuscriptDueDate;
	}
	
	public Date getReviewDueDate() {
		return myReviewDueDate;
	}
	
	public Date getRecDueDate() {
		return myRecDueDate;
	}
	
	public Date getDecisionDueDate() {
		return myDecisionDueDate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Conference: ");
		sb.append(myConferenceID);
		sb.append("\nDate: ");
		sb.append(myConferenceDate.toString());
		sb.append("\nProgram Chair: ");
		sb.append(myProgramChairID);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object theOther) {
		if (!this.getClass().equals(theOther.getClass())) {
			return false;
		}
		Conference otherCon = (Conference) theOther;
		if (!this.myConferenceID.equals(otherCon.myConferenceID)) {
			return false;
		} else if (!this.myConferenceDate.equals(otherCon.myConferenceDate)) {
			return false;
		} else if (!this.myProgramChairID.equals(otherCon.myProgramChairID)) {
			return false;
		} else if (!this.myManuscriptDueDate.equals(otherCon.myManuscriptDueDate)) {
			return false;
		} else if (!this.myReviewDueDate.equals(otherCon.myReviewDueDate)) {
			return false;
		} else if (!this.myRecDueDate.equals(otherCon.myRecDueDate)) {
			return false;
		} else if (!this.myDecisionDueDate.equals(otherCon.myDecisionDueDate)) {
			return false;
		} 
		return true;
	}

}