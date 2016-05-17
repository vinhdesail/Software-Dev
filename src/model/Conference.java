package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public Conference(String theConferenceID, String theProgramChairID, String theConferenceDate,
						String theManuscriptDueDate, String theReviewDueDate, String theRecDueDate,
						String theDecisionDueDate) {
		
		myConferenceID = theConferenceID;
		myProgramChairID = theProgramChairID;
		myConferenceDate = stringToDate(theConferenceDate);
		myManuscriptDueDate = stringToDate(theManuscriptDueDate);
		myReviewDueDate = stringToDate(theReviewDueDate);
		myRecDueDate = stringToDate(theRecDueDate);
		myDecisionDueDate = stringToDate(theDecisionDueDate);	
		
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
	
	/**
	 * This method converts Strings that express date in the format "dd-MM-yyyy" into Date
	 * objects.
	 * @param theDateString formatted as "dd-MM-yyyy" where dd is the two digit day, MM is the
	 * two digit month, and yyyy is the 4 digit year.
	 * @return a Date object corresponding to the date specified in the String, or null if 
	 * the String was not of the expected format
	 */
	public static Date stringToDate(String theDateString) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date theDate = df.parse(theDateString);
			return theDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
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
