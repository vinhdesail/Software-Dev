package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Date;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;

public class ProgramChairTest {
	private ProgramChair myProgramChairToTest;
    private Conference myMainTestConference;
    
	@Before
	public void setUp() throws Exception {
		Date conferenceDate = new Date(2016,10,17);
		Date manuscriptDueDate =  new Date(2016,9,1);
		Date reviewDueDate =  new Date(2016,9,19);
		Date recommendationDueDate =  new Date(2016,10,1);
		Date decisionDueDate =  new Date(2016,10,1);
		
		myMainTestConference = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		myProgramChairToTest = new ProgramChair(myMainTestConference, "UserName");
	}

	@Test
	public void showAllManuscriptAssignedToSpcTest() {
		SubprogramChair theSpc =  new SubprogramChair("SubUser",myMainTestConference);
		
		Manuscript theFirstManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleTwo", "The Body");
		myProgramChairToTest.assignManuscript(theSpc, theFirstManuscript);
		myProgramChairToTest.assignManuscript(theSpc, theSecondManuscript);		
		assertEquals(theFirstManuscript, myProgramChairToTest.showAllManuscriptAssignedToSpc(theSpc).get(0));	
		assertEquals(theSecondManuscript, myProgramChairToTest.showAllManuscriptAssignedToSpc(theSpc).get(1));
		
		
	}
	
	@Test
	public void showAllManuscriptsTest() {
		
		
		Manuscript theFirstManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", myMainTestConference.getConferenceID(), "TitleTwo", "The Body");		
		List<Manuscript> aListOfManuscripts = new ArrayList<Manuscript>();
		assertEquals(0, myProgramChairToTest.showAllManuscripts(aListOfManuscripts).size());	
		aListOfManuscripts.add(theFirstManuscript);
		aListOfManuscripts.add(theSecondManuscript);
		assertEquals(theFirstManuscript, myProgramChairToTest.showAllManuscripts(aListOfManuscripts).get(0));	
		assertEquals(theSecondManuscript, myProgramChairToTest.showAllManuscripts(aListOfManuscripts).get(1));
		
		
	}
	
	@Test
	public void equalsTest() {		
		ProgramChair otherPc = new ProgramChair(myMainTestConference, "UserName");
		assertEquals(true, myProgramChairToTest.equals(otherPc));					
	}
	
	@Test
	public void hashCodeTest() {		
		ProgramChair otherPc = myProgramChairToTest;
		assertEquals(myProgramChairToTest.hashCode(), otherPc.hashCode());				
	}
	
}
