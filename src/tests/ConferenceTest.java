package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.ProgramChair;

/**
 * Tests for the Conference class (model)
 * @author Joshua Meigs, Edie Megan Campbell
 * @version 2016.05.31
 */
public class ConferenceTest {
	
	Conference myMainTestConference;
	Conference myDifferentFromMainConference_conference;
	Conference mySameAsMainConference_conference;
	ProgramChair myTestProgramChair;
	String programChairID = "Name of Program Chair";
	String conference1ID = "Conference 1 ID";
	String conference2ID = "Conference 2 ID";
	Calendar conferenceDate = new GregorianCalendar(2016,10,17);
	Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
	Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
	Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
	Calendar decisionDueDate = new GregorianCalendar(2016,10,1);	
	
	
	/* Method signature for Conference constructor (for reference):
	 * public Conference(String theConferenceID, String theProgramChairID, Date theConferenceDate, 
						Date theManuscriptDueDate, Date theReviewDueDate, Date theRecDueDate,
						Date theDecisionDueDate) */
	@Before
	public void setUp() {	
		
		myMainTestConference = new Conference(conference1ID, programChairID, conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		myDifferentFromMainConference_conference = new Conference(conference2ID, programChairID, 
				conferenceDate, manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		mySameAsMainConference_conference = new Conference(conference1ID, programChairID, conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		myTestProgramChair =  new ProgramChair(myMainTestConference, programChairID);
	}
	
	// must test all null parameter exceptions for constructor:

	@Test
	public void constructorNullConferenceIDExceptionTest() {
		try {
			new Conference(null, programChairID, conferenceDate, manuscriptDueDate, 
					reviewDueDate, recommendationDueDate, decisionDueDate);
			fail("null conference ID in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullProgramIDExceptionTest() {
		try {
			new Conference(conference1ID, null, conferenceDate, manuscriptDueDate, 
					reviewDueDate, recommendationDueDate, decisionDueDate);
			fail("null program chair ID in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullConferenceDateExceptionTest() {
		try {
			new Conference(conference1ID, programChairID, null, manuscriptDueDate, 
					reviewDueDate, recommendationDueDate, decisionDueDate);
			fail("null conference date in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullManuscriptDateExceptionTest() {
		try {
			new Conference(conference1ID, programChairID, conferenceDate, null, 
					reviewDueDate, recommendationDueDate, decisionDueDate);
			fail("null manuscript date in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullReviewDateExceptionTest() {
		try {
			new Conference(conference1ID, programChairID, conferenceDate, manuscriptDueDate, 
					null, recommendationDueDate, decisionDueDate);
			fail("null review date in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullRecommendationDateExceptionTest() {
		try {
			new Conference(conference1ID, programChairID, conferenceDate, manuscriptDueDate, 
					reviewDueDate, null, decisionDueDate);
			fail("null recommendation date in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullDecisionDateExceptionTest() {
		try {
			new Conference(conference1ID, programChairID, conferenceDate, manuscriptDueDate, 
					reviewDueDate, recommendationDueDate, null);
			fail("null decision date in constructor didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void equalsBetweenTwoDifferentConferencesWithDifferentValuesTest() {
		assertFalse(myMainTestConference.equals(myDifferentFromMainConference_conference));
	}
	
	@Test
	public void equalsBetweenTwoDifferentConferencesWithSameValuesTest() {
		assertTrue(myMainTestConference.equals(mySameAsMainConference_conference));
	}

}
