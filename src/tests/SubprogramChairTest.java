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
    private Conference myConferenceToTestForAllSPC;
    private Conference mySecondaryConferenceForADifferentManuscript;
    private Manuscript firstManuscript; 
    private Manuscript secondManuscript;
    private Manuscript thirdManuscript;
    private Manuscript fourManuscript;
    private Manuscript fifthManuscript;
    private Manuscript sixthManuscript;
	
	@Before
	public void setUp() throws Exception {
		Date conferenceDate = new Date(2016,10,17);
		Date manuscriptDueDate =  new Date(2016,9,1);
		Date reviewDueDate =  new Date(2016,9,19);
		Date recommendationDueDate =  new Date(2016,10,1);
		Date decisionDueDate =  new Date(2016,10,1);
		
		
		myConferenceToTestForAllSPC = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		mySecondaryConferenceForADifferentManuscript = new Conference("Family Troubles 2016", "foxeT", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		
		
		firstManuscript = new Manuscript("Jane Foster", myConferenceToTestForAllSPC.getConferenceID(), "How To Manage Money in the new age", "The Body");
		secondManuscript = new Manuscript("Tim Allen", myConferenceToTestForAllSPC.getConferenceID(), "What your savings should go to", "The Body");
		thirdManuscript = new Manuscript("Bill Free", myConferenceToTestForAllSPC.getConferenceID(), "How Bankers have been misleading your time", "The Body");
		fourManuscript = new Manuscript("Carl Sargan", myConferenceToTestForAllSPC.getConferenceID(), "Where your money goes when it is deposited", "The Body");
		fifthManuscript = new Manuscript("Paula Menroe", myConferenceToTestForAllSPC.getConferenceID(), "How much your money will be worth in twenty years", "The Body");
		sixthManuscript =  new Manuscript("Zach Fair", mySecondaryConferenceForADifferentManuscript.getConferenceID(), "The New Family of today", "The Body");
		
		mySpc = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
	}
	/**
	 * Tests if all of the manuscripts that are assigned will show in their assigned list.
	 */
	@Test
	public void showAllAssignedManuscriptsTest() {
			
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
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.assignManuscripts(firstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionWhenTryingToAddAManuscriptFromAnotherConferenceTest() {	
		try {
			   mySpc.assignManuscripts(sixthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationTest() {
			
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "This Paper was alright. It seemed to lack substance.");	   
	    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"ImASPC");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"How To Manage Money in the new age");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"This Paper was alright. It seemed to lack substance.");
	}
	
	@Test
	public void submitRecomendationExceptionEmptyMaxedTest() {
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.submitRecomendation(secondManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptNotFoundTest() {
		
		try {
			   mySpc.submitRecomendation(firstManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsEmptyTest() {		
		 assertEquals(mySpc.containsManuscriptAt(firstManuscript),-1);				
	}	
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsNotEmptyTest() {		
		mySpc.assignManuscripts(firstManuscript);
		assertEquals(mySpc.containsManuscriptAt(secondManuscript),-1);				
	}
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsWithinTheSPCTest() {	
		mySpc.assignManuscripts(firstManuscript);
		assertEquals(mySpc.containsManuscriptAt(firstManuscript),0);
	}	
	
	@Test
	public void getRecommendationTextTest() {
		
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "The Text");	   
	    
		assertEquals(mySpc.getRecommendationText(firstManuscript),"The Text");
	}
	
	@Test
	public void getRecommendationTextExceptionMistakenManuTest() {
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.getRecommendationText(secondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextExceptionManuscriptNotFoundTest() {
		
		try {
			   mySpc.getRecommendationText(firstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void editRecomendationTest() {
		
		mySpc.assignManuscripts(firstManuscript);		
	    mySpc.submitRecomendation(firstManuscript, "Old Text");
	    mySpc.editRecomendation(firstManuscript, "New Text");
	    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"ImASPC");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"How To Manage Money in the new age");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"New Text");
	}
	
	
	@Test
	public void editRecomendationExceptionEmptyMaxedTest() {
		mySpc.assignManuscripts(firstManuscript);
		try {
			   mySpc.editRecomendation(secondManuscript,"New Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	
	
	@Test
	public void editRecomendationExceptionManuscriptNotFoundTest() {
		
		try {
			   mySpc.editRecomendation(firstManuscript, "New Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	
}
