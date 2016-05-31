package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Conference objects store information about a Conference's program chair, dates, and deadlines.
 * @author Edie Megan Campbell
 * @version 2016.05.30
 */
public class Conference implements Serializable {
	
	private static final long serialVersionUID = -156166686815320306L;

	/* A unique name to identify this Conference. */
	private String myConferenceID;
	
	/* The Program Chair, identified by their unique username (User ID) .*/
	private String myProgramChairID;
	
	/* The date of the Conference itself as a Calendar object. */
	private Calendar myConferenceDate;
	
	/* The date when all Manuscripts are due for this Conference as a Calendar object. */
	private Calendar myManuscriptDueDate;
	
	/* The date when all Reviews are due for this Conference as a Calendar object. */
	private Calendar myReviewDueDate;
	
	/* The date when all Recommendations are due for this Conference as a Calendar object. */
	private Calendar myRecDueDate;
	
	/* The date when the final decisions from the program chair are due as a Calendar object. */
	private Calendar myDecisionDueDate;
	
	/**
	 * @param theConferenceID - the unique name to identify this Conference as a String
	 * @param theProgramChairID - the unique user ID of the User who will act as Program Chair
	 * @param theConferenceDate - the Calendar date of this Conference
	 * @param theManuscriptDueDate - the Calendar date when Manuscripts are due for this Conference
	 * @param theReviewDueDate - the Calendar date when Reviews are due for this Conference
	 * @param theRecDueDate - the Calendar date when Recommendations are due for this Conference
	 * @param theDecisionDueDate - the Calendar date when program chair decisions are due
	 */
	public Conference(String theConferenceID, String theProgramChairID, Calendar theConferenceDate, 
			Calendar theManuscriptDueDate, Calendar theReviewDueDate, Calendar theRecDueDate,
						Calendar theDecisionDueDate) {
		myConferenceID = theConferenceID;
		myProgramChairID = theProgramChairID;
		myConferenceDate = theConferenceDate;
		myManuscriptDueDate = theManuscriptDueDate;
		myReviewDueDate = theReviewDueDate;
		myRecDueDate = theRecDueDate;
		myDecisionDueDate = theDecisionDueDate;
	}
	
	/**
	 * All String date representations are in the format "DD-MM-YYYY"
	 * @param theConferenceID - the unique name to identify this Conference as a String
	 * @param theProgramChairID - the unique user ID of the User who will act as Program Chair
	 * @param theConferenceDate - the String date of this Conference
	 * @param theManuscriptDueDate - the String date when Manuscripts are due for this Conference
	 * @param theReviewDueDate - the String date when Reviews are due for this Conference
	 * @param theRecDueDate - the String date when Recommendations are due for this Conference
	 * @param theDecisionDueDate - the String date when program chair decisions are due
	 */
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
	
	/**
	 * Getter method for the identifying Conference String for this Conference.
	 * @return String conference ID
	 */
	public String getConferenceID() {
		return myConferenceID;
	}
	
	public String getProgramChairID() {
		return myProgramChairID;
	}
	
	public Calendar getConferenceDate() {
		return myConferenceDate;
	}
	
	public Calendar getManuscriptDueDate() {
		return myManuscriptDueDate;
	}
	
	public Calendar getReviewDueDate() {
		return myReviewDueDate;		
	}
	
	public Calendar getRecDueDate() {
		return myRecDueDate;
	}
	
	public Calendar getDecisionDueDate() {
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
	public static Calendar stringToDate(String theDateString) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Calendar theDate = new GregorianCalendar();
			theDate.setTime(df.parse(theDateString));
			//Date theDate = df.parse(theDateString);
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
