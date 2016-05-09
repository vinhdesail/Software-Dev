package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Date;
import model.Manuscript;
import model.SubprogramChair;

public class SubprogramChairTest {

	private SubprogramChair mySpc;
    private Conference conference;
	
	@Before
	public void setUp() throws Exception {
		conference = new Conference("First Conference", "UserName", (new Date(1,1,1)), (new Date(1,1,1)), (new Date(1,1,1)), (new Date(1,1,1)), new Date(1,1,1));		
		mySpc = new SubprogramChair("UserName");
	}
	/**
	 * Tests if all of the manuscripts that are assigned will show in their assigned list.
	 */
	@Test
	public void showAllAssignedManuscriptsTest() {
		
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript secondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		mySpc.assignManuscripts(firstManuscript);
		mySpc.assignManuscripts(secondManuscript);
		assertSame(mySpc.showAllAssignedManuscripts().get(0),firstManuscript);
		assertSame(mySpc.showAllAssignedManuscripts().get(1),secondManuscript);
	}
	/**
	 * Tests the Bus. Rule that if a reviewer already has been assigned Four Manuscripts, that it will throw an exception.
	 */
	@Test
	public void assignManuscriptsExceptionListMaxedTest() {
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript secondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		Manuscript thirdManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleThree", "The Body");
		Manuscript fourManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleFour", "The Body");
		Manuscript fifthManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleFive", "The Body");
		
		mySpc.assignManuscripts(firstManuscript);
		mySpc.assignManuscripts(secondManuscript);
		mySpc.assignManuscripts(thirdManuscript);
		mySpc.assignManuscripts(fourManuscript);
		try {
			   mySpc.assignManuscripts(fifthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionListManuscriptAlreadyAddedTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.assignManuscripts(firstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "The Text");	   
	    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"UserName");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"TitleOne");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"The Text");
	}
	
	@Test
	public void submitRecomendationExceptionEmptyMaxedTest() {
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript secondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.submitRecomendation(secondManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptNotFoundTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		
		try {
			   mySpc.submitRecomendation(firstManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "The Text");	   
	    
		assertEquals(mySpc.getRecommendationText(firstManuscript),"The Text");
	}
	
	@Test
	public void getRecommendationTextExceptionMistakenManuTest() {
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript secondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.getRecommendationText(secondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextExceptionManuscriptNotFoundTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		
		try {
			   mySpc.getRecommendationText(firstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void editRecomendationTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "Old Text");
	    mySpc.editRecomendation(firstManuscript, "New Text");
	    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"UserName");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"TitleOne");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"New Text");
	}
	
	
	@Test
	public void editRecomendationExceptionEmptyMaxedTest() {
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		Manuscript secondManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleTwo", "The Body");
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.editRecomendation(secondManuscript,"New Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void editRecomendationExceptionManuscriptNotFoundTest() {
		
		Manuscript firstManuscript = new Manuscript("Author", conference.getConferenceID(), "TitleOne", "The Body");
		
		try {
			   mySpc.editRecomendation(firstManuscript, "New Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	
}
