package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.ProgramChair;

public class ConferenceTest {
	
	Conference myMainTestConference;
	Conference myDifferentFromMainConference_conference;
	Conference mySameAsMainConference_conference;
	ProgramChair myTestProgramChair;
	
	
	/*public Conference(String theConferenceID, String theProgramChairID, Date theConferenceDate, 
						Date theManuscriptDueDate, Date theReviewDueDate, Date theRecDueDate,
						Date theDecisionDueDate)*/
	@Before
	public void setUp() throws Exception {
		
		Date conferenceDate = new Date(2016,10,17);
		Date manuscriptDueDate =  new Date(2016,9,1);
		Date reviewDueDate =  new Date(2016,9,19);
		Date recommendationDueDate =  new Date(2016,10,1);
		Date decisionDueDate =  new Date(2016,10,1);
		
		myMainTestConference = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		myDifferentFromMainConference_conference = new Conference("BITZOLCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		mySameAsMainConference_conference = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		myTestProgramChair =  new ProgramChair(myMainTestConference, "doeJ");
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
