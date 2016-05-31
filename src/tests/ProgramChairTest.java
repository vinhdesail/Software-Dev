package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;

public class ProgramChairTest {
	private ProgramChair myStandardProgramChairForTesting;
	private ProgramChair myProgramChairToCompareToTheMainProgramChairThatIsTheSame;
	private ProgramChair myProgramChairToCompareToTheMainProgramChairThatIsDifferent;
    private Conference myMainTestConference;
    private Manuscript myFirstManuscript;
    private Manuscript mySecondManuscript;
    private List<Manuscript> myMasterListOfAllManuscriptsAssignedToThisConfernce;
    SubprogramChair mySpc;
	@Before
	public void setUp() throws Exception {
		Calendar conferenceDate = new GregorianCalendar(2016,10,17);
		Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
		Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
		Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
		Calendar decisionDueDate = new GregorianCalendar(2016,10,1);	
		
		myMainTestConference = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		myStandardProgramChairForTesting = new ProgramChair(myMainTestConference, "UserName");
		myProgramChairToCompareToTheMainProgramChairThatIsTheSame = new ProgramChair(myMainTestConference, "UserName");
		myProgramChairToCompareToTheMainProgramChairThatIsTheSame = new ProgramChair(myMainTestConference, "DifferentUserName");
	    myFirstManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleOne", "The Body");
		mySecondManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleTwo", "The Body");
		mySpc =  new SubprogramChair("SubUser",myMainTestConference);
		myStandardProgramChairForTesting.assignManuscript(mySpc, myFirstManuscript);
		myStandardProgramChairForTesting.approveManuscript(myFirstManuscript);
		myStandardProgramChairForTesting.rejectManuscript(mySecondManuscript);
	}

	@Test
	public void testShowAllManuscriptAssignedToSpc() {			
		assertEquals(myFirstManuscript, myStandardProgramChairForTesting.showAllManuscriptAssignedToSpc(mySpc).get(0));			
	}
	
	@Test
	public void testShowAllManuscriptAssignedToSpcExceptionWhereTheGivenSubprogramChairIsNull() {
		try {
			myStandardProgramChairForTesting.showAllManuscriptAssignedToSpc(null);	
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}			
	}
	
	@Test
	public void testAssignManuscript() {
		assertEquals(myFirstManuscript,mySpc.showAllAssignedManuscripts().get(0));
	}
	
	@Test
	public void testAssignManuscriptExceptionWhereTheGivenSubprogramChairIsNull() {
		try {
			myStandardProgramChairForTesting.assignManuscript(null, myFirstManuscript);	
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testAssignManuscriptExceptionWhereTheGivenManuscriptIsNull() {
		try {
			myStandardProgramChairForTesting.assignManuscript(mySpc, null);		
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testShowAllManuscriptsExceptionWhereTheGivenListIsNull() {	
		try {
			myStandardProgramChairForTesting.showAllManuscripts(null);
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testFindAllManuscriptsAssociatedWithEverySPCExceptionWhereTheGivenListIsNull() {	
		try {
			myStandardProgramChairForTesting.findAllManuscriptsAssociatedWithEverySPC(null);
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testGetAllSubprogramChairExceptionWhereTheGivenMapIsNull() {	
		try {
			myStandardProgramChairForTesting.getAllSubprogramChair(null);
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testApproveManuscript() {				
		assertEquals(1, myFirstManuscript.getStatus());					
	}
	
	@Test
	public void testApproveManuscriptExceptionWhereTheGivenManuscriptIsNull() {
		try {
			myStandardProgramChairForTesting.approveManuscript(null);	
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}		
	}
	
	@Test
	public void testRejectManuscript() {				
		assertEquals(-1, mySecondManuscript.getStatus());
	}
	
	@Test
	public void testRejectManuscriptExceptionWhereTheGivenManuscriptIsNull() {				
		try {
			myStandardProgramChairForTesting.rejectManuscript(null);	
			fail("The Exception was not passed.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testEqualsWhereBothValuesAreDifferent() {				
		assertEquals(false, myStandardProgramChairForTesting.equals(myProgramChairToCompareToTheMainProgramChairThatIsDifferent));					
	}
	
	
	
}
