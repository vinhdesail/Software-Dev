package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Conference objects store information about a Conference's program chair, dates, and deadlines.
 * @author Edie Megan Campbell
 * @version 2016.05.31
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
	 * Constructor that accepts all date-related fields as Calendar objects.
	 * @param theConferenceID - the unique name to identify this Conference as a String
	 * @param theProgramChairID - the unique user ID of the User who will act as Program Chair
	 * @param theConferenceDate - the Calendar date of this Conference
	 * @param theManuscriptDueDate - the Calendar date when Manuscripts are due for this Conference
	 * @param theReviewDueDate - the Calendar date when Reviews are due for this Conference
	 * @param theRecDueDate - the Calendar date when Recommendations are due for this Conference
	 * @param theDecisionDueDate - the Calendar date when program chair decisions are due
	 * @throws IllegalArgumentException if any of the parameters are null
	 */
	public Conference(String theConferenceID, String theProgramChairID, Calendar theConferenceDate, 
			Calendar theManuscriptDueDate, Calendar theReviewDueDate, Calendar theRecDueDate,
						Calendar theDecisionDueDate) {
		if (Objects.isNull(theConferenceID)) {
			throw new IllegalArgumentException("Conference ID cannot be null.");
		} else if (Objects.isNull(theProgramChairID)) {
			throw new IllegalArgumentException("Program Chair ID cannot be null.");
		} else if (Objects.isNull(theConferenceDate)) {
			throw new IllegalArgumentException("Conference date cannot be null.");
		} else if (Objects.isNull(theManuscriptDueDate)) {
			throw new IllegalArgumentException("Manuscript due date cannot be null.");
		} else if (Objects.isNull(theReviewDueDate)) {
			throw new IllegalArgumentException("Review due date cannot be null.");
		} else if (Objects.isNull(theRecDueDate)) {
			throw new IllegalArgumentException("Recommendation due date cannot be null.");
		} else if (Objects.isNull(theDecisionDueDate)) {
			throw new IllegalArgumentException("Decision due date cannot be null.");
		}
		myConferenceID = theConferenceID;
		myProgramChairID = theProgramChairID;
		myConferenceDate = theConferenceDate;
		myManuscriptDueDate = theManuscriptDueDate;
		myReviewDueDate = theReviewDueDate;
		myRecDueDate = theRecDueDate;
		myDecisionDueDate = theDecisionDueDate;
	}
	
//	/**
//	 * Constructor that accepts all date-related fields as Strings. All String date 
//	 * representations are in the format "DD-MM-YYYY"
//	 * @param theConferenceID - the unique name to identify this Conference as a String
//	 * @param theProgramChairID - the unique user ID of the User who will act as Program Chair
//	 * @param theConferenceDate - the String date of this Conference
//	 * @param theManuscriptDueDate - the String date when Manuscripts are due for this Conference
//	 * @param theReviewDueDate - the String date when Reviews are due for this Conference
//	 * @param theRecDueDate - the String date when Recommendations are due for this Conference
//	 * @param theDecisionDueDate - the String date when program chair decisions are due
//	 * @throws IllegalArgumentException if any of the parameters are null
//	 */
//	public Conference(String theConferenceID, String theProgramChairID, String theConferenceDate,
//						String theManuscriptDueDate, String theReviewDueDate, String theRecDueDate,
//						String theDecisionDueDate) {
//		if (Objects.isNull(theConferenceID)) {
//			throw new IllegalArgumentException("Conference ID cannot be null.");
//		} else if (Objects.isNull(theProgramChairID)) {
//			throw new IllegalArgumentException("Program Chair ID cannot be null.");
//		} else if (Objects.isNull(theConferenceDate)) {
//			throw new IllegalArgumentException("Conference date cannot be null.");
//		} else if (Objects.isNull(theManuscriptDueDate)) {
//			throw new IllegalArgumentException("Manuscript due date cannot be null.");
//		} else if (Objects.isNull(theReviewDueDate)) {
//			throw new IllegalArgumentException("Review due date cannot be null.");
//		} else if (Objects.isNull(theRecDueDate)) {
//			throw new IllegalArgumentException("Recommendation due date cannot be null.");
//		} else if (Objects.isNull(theDecisionDueDate)) {
//			throw new IllegalArgumentException("Decision due date cannot be null.");
//		}
//		myConferenceID = theConferenceID;
//		myProgramChairID = theProgramChairID;
//		myConferenceDate = stringToDate(theConferenceDate);
//		myManuscriptDueDate = stringToDate(theManuscriptDueDate);
//		myReviewDueDate = stringToDate(theReviewDueDate);
//		myRecDueDate = stringToDate(theRecDueDate);
//		myDecisionDueDate = stringToDate(theDecisionDueDate);	
//	}
	
	/**
	 * Getter method for the identifying Conference String for this Conference.
	 * @return String conference ID
	 */
	public String getConferenceID() {
		return myConferenceID;
	}
	
	/**
	 * Getter method for the Conference Program Chair's unique username
	 * @return String - the identifying username for this Conference's Program Chair
	 */
	public String getProgramChairID() {
		return myProgramChairID;
	}
	
	/**
	 * @return Calendar - the date of the Conference as a Calendar object
	 */
	public Calendar getConferenceDate() {
		return myConferenceDate;
	}
	
	/**
	 * @return - the date when all Manuscript submissions are due as a Calendar object
	 */
	public Calendar getManuscriptDueDate() {
		return myManuscriptDueDate;
	}
	
	/**
	 * @return - the date when all Reviewer Reviews are due as a Calendar object
	 */
	public Calendar getReviewDueDate() {
		return myReviewDueDate;		
	}
	
	/**
	 * @return - the date when all Subprogram Chair recommendations are due as a Calendar object
	 */
	public Calendar getRecDueDate() {
		return myRecDueDate;
	}
	
	/**
	 * @return - the date when all Program Chair decisions are due as a Calendar object
	 */
	public Calendar getDecisionDueDate() {
		return myDecisionDueDate;
	}
	
	/**
	 * This method converts Strings that express date in the format "dd-MM-yyyy" into Calendar
	 * objects.
	 * @param theDateString - String formatted as "dd-MM-yyyy" where dd is the two digit day, 
	 * MM is the two digit month, and yyyy is the 4 digit year.
	 * @return a Date object corresponding to the date specified in the String
	 * @throws IllegalArguementException if theDateString is null or not in the expected format
	 */
	public static Calendar stringToDate(String theDateString) {
		if (Objects.isNull(theDateString)) {
			throw new IllegalArgumentException("Date string cannot be null.");
		}
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Calendar theDate = new GregorianCalendar();
		try {
			theDate.setTime(df.parse(theDateString));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date string is not in expected format: dd-MM-yyyy");
		}
		return theDate;
	}
	
	/**
	 * {@inheritDoc}
	 * Formats the Conference name, the date of the Conference, and the name of the Program
	 * Chair as a String.
	 */
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
	
	/**
	 * {@inheritDoc}
	 * Overrides the Object hashCode method to be consistent with the overridden equals method.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 * Overrides the Object equals method to compare Conferences based on fields.
	 */
	@Override
	public boolean equals(Object theOther) {
		if (!(theOther instanceof Conference)) {
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
