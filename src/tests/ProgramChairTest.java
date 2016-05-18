package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;

public class ProgramChairTest {
	private ProgramChair myPC;
    private Conference myConference;
    
	@Before
	public void setUp() throws Exception {
		myConference = new Conference("First Conference", "UserName", "01-01-2001", "01-01-2001", 
									"01-01-2001", "01-01-2001", "01-01-2001");
		myPC = new ProgramChair(myConference, "UserName");
	}

	@Test
	public void showAllManuscriptAssignedToSpcTest() {
		SubprogramChair theSpc =  new SubprogramChair("SubUser", myConference);
		
		Manuscript theFirstManuscript = new Manuscript("Author", myConference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", myConference.getConferenceID(), "TitleTwo", "The Body");
		myPC.assignManuscript(theSpc, theFirstManuscript);
		myPC.assignManuscript(theSpc, theSecondManuscript);		
		assertEquals(theFirstManuscript, myPC.showAllManuscriptAssignedToSpc(theSpc).get(0));	
		assertEquals(theSecondManuscript, myPC.showAllManuscriptAssignedToSpc(theSpc).get(1));
		
		
	}
	
	@Test
	public void showAllManuscriptsTest() {
		
		
		Manuscript theFirstManuscript = new Manuscript("Author", myConference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", myConference.getConferenceID(), "TitleTwo", "The Body");		
		List<Manuscript> aListOfManuscripts = new ArrayList<Manuscript>();
		assertEquals(0, myPC.showAllManuscripts(aListOfManuscripts).size());	
		aListOfManuscripts.add(theFirstManuscript);
		aListOfManuscripts.add(theSecondManuscript);
		assertEquals(theFirstManuscript, myPC.showAllManuscripts(aListOfManuscripts).get(0));	
		assertEquals(theSecondManuscript, myPC.showAllManuscripts(aListOfManuscripts).get(1));
		
		
	}
	
	@Test
	public void equalsTest() {		
		ProgramChair otherPc = new ProgramChair(myConference, "UserName");
		assertEquals(true, myPC.equals(otherPc));					
	}
	
	@Test
	public void hashCodeTest() {		
		ProgramChair otherPc = myPC;
		assertEquals(myPC.hashCode(), otherPc.hashCode());				
	}
	
}
