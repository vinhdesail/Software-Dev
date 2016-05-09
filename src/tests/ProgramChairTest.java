package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.io.File;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Date;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;

public class ProgramChairTest {
	private ProgramChair pc;
    private Conference conference;
    
	@Before
	public void setUp() throws Exception {
		conference = new Conference("First Conference", "UserName", (new Date(1,1,1)), (new Date(1,1,1)), (new Date(1,1,1)), (new Date(1,1,1)), new Date(1,1,1));
		pc = new ProgramChair(conference, "UserName");
	}

	@Test
	public void showAllManuscriptAssignedToSpcTest() {
		SubprogramChair theSpc =  new SubprogramChair("SubUser");
		
		Manuscript theFirstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		pc.assignManuscript(theSpc, theFirstManuscript);
		pc.assignManuscript(theSpc, theSecondManuscript);		
		assertEquals(theFirstManuscript, pc.showAllManuscriptAssignedToSpc(theSpc).get(0));	
		assertEquals(theSecondManuscript, pc.showAllManuscriptAssignedToSpc(theSpc).get(1));
		
		
	}
	
	@Test
	public void showAllManuscriptsTest() {
		
		
		Manuscript theFirstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript theSecondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");		
		List<Manuscript> aListOfManuscripts = new ArrayList<Manuscript>();
		assertEquals(0, pc.showAllManuscripts(aListOfManuscripts).size());	
		aListOfManuscripts.add(theFirstManuscript);
		aListOfManuscripts.add(theSecondManuscript);
		assertEquals(theFirstManuscript, pc.showAllManuscripts(aListOfManuscripts).get(0));	
		assertEquals(theSecondManuscript, pc.showAllManuscripts(aListOfManuscripts).get(1));
		
		
	}
	
	@Test
	public void equalsTest() {		
		ProgramChair otherPc = new ProgramChair(conference, "UserName");
		assertEquals(true, pc.equals(otherPc));					
	}
	
	@Test
	public void hashCodeTest() {		
		ProgramChair otherPc = pc;
		assertEquals(pc.hashCode(), otherPc.hashCode());				
	}
	
}
