package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.SubprogramChair;

public class SubprogramChairTest {

	private SubprogramChair mySubprogramChairThatContainsOneManuscript;
	private SubprogramChair mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame;
	private SubprogramChair mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent;
	private SubprogramChair mySubprogramChairThatContainsFourManuscripts;
	private SubprogramChair mySubprogramChairThatContainsNoManuscripts;
    private Conference myConferenceToTestForAllSPC;
    private Conference mySecondaryConferenceForADifferentManuscript;
    private Manuscript firstManuscript; 
    private Manuscript secondManuscript;
    private Manuscript thirdManuscript;
    private Manuscript fourthManuscript;
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
		fourthManuscript = new Manuscript("Carl Sargan", myConferenceToTestForAllSPC.getConferenceID(), "Where your money goes when it is deposited", "The Body");
		fifthManuscript = new Manuscript("Paula Menroe", myConferenceToTestForAllSPC.getConferenceID(), "How much your money will be worth in twenty years", "The Body");
		sixthManuscript =  new Manuscript("Zach Fair", mySecondaryConferenceForADifferentManuscript.getConferenceID(), "The New Family of today", "The Body");	
		mySubprogramChairThatContainsOneManuscript = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscript.assignManuscripts(firstManuscript);
		mySubprogramChairThatContainsOneManuscript.submitRecomendation(firstManuscript,"This Paper was alright. It seemed to lack substance.");	
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame.assignManuscripts(firstManuscript);	
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent.assignManuscripts(secondManuscript);
		mySubprogramChairThatContainsFourManuscripts =  new SubprogramChair("ImASPCWithFourManuscript", myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(firstManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(secondManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(thirdManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(fourthManuscript);	
		mySubprogramChairThatContainsNoManuscripts = new SubprogramChair("ImASPCWithNoManuscript", myConferenceToTestForAllSPC);
	}
	/**
	 * Tests if all of the manuscripts that are assigned will show in their assigned list.
	 */
	@Test
	public void showAllAssignedManuscriptsTest() {			
		assertSame(mySubprogramChairThatContainsOneManuscript.showAllAssignedManuscripts().get(0),firstManuscript);
		}
	/**
	 * Tests the Bus. Rule that if a reviewer already has been assigned Four Manuscripts, that it will throw an exception.
	 */
	@Test
	public void assignManuscriptsExceptionListMaxedTest() {
		try {
			   mySubprogramChairThatContainsFourManuscripts.assignManuscripts(fifthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionListManuscriptAlreadyAddedTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.assignManuscripts(firstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionWhenTryingToAddAManuscriptFromAnotherConferenceTest() {	
		try {
			   mySubprogramChairThatContainsOneManuscript.assignManuscripts(sixthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationTest() {
			    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"ImASPC");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"How To Manage Money in the new age");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"This Paper was alright. It seemed to lack substance.");
	}
	
	@Test
	public void submitRecomendationExceptionEmptyMaxedTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.submitRecomendation(secondManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptNotFoundTest() {
		
		try {
			   mySubprogramChairThatContainsOneManuscript.submitRecomendation(firstManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsEmptyTest() {		
		 assertEquals(mySubprogramChairThatContainsNoManuscripts.containsManuscriptAt(firstManuscript),-1);				
	}	
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsNotEmptyTest() {		
		assertEquals(mySubprogramChairThatContainsOneManuscript.containsManuscriptAt(secondManuscript),-1);				
	}
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsWithinTheSPCTest() {	
		assertEquals(mySubprogramChairThatContainsOneManuscript.containsManuscriptAt(firstManuscript),0);
	}	
	
	@Test
	public void getRecommendationTextTest() {		
		assertEquals(mySubprogramChairThatContainsOneManuscript.getRecommendationText(firstManuscript),"This Paper was alright. It seemed to lack substance.");
	}
	
	@Test
	public void getRecommendationTextExceptionMistakenManuTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.getRecommendationText(secondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextExceptionManuscriptNotFoundTest() {
		
		try {
			   mySubprogramChairThatContainsOneManuscript.getRecommendationText(secondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void editRecomendationTest() {
	    mySubprogramChairThatContainsOneManuscript.editRecomendation(firstManuscript, "New Text");
	    
	    assertEquals(firstManuscript.getRecommendation().getSubprogramChairID(),"ImASPC");
	    assertEquals(firstManuscript.getRecommendation().getManuscriptTitle(),"How To Manage Money in the new age");
		assertEquals(firstManuscript.getRecommendation().getRecommmendationText(),"New Text");
	}
	
	@Test
	public void equalsWhereBothSPCAreTheSameTest() {		
		assertTrue(mySubprogramChairThatContainsOneManuscript.equals(mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame));
	}
	
	@Test
	public void equalsWhereBothSPCAreTheDifferentByManuscript() {		
		assertFalse(mySubprogramChairThatContainsOneManuscript.equals(mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent));
	}
	
	@Test
	public void editRecomendationExceptionEmptyMaxedTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.editRecomendation(secondManuscript,"New Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	
	
	
	
	@Test
	public void editRecomendationExceptionManuscriptNotFoundTest() {	
		try {
			   mySubprogramChairThatContainsOneManuscript.editRecomendation(secondManuscript, "This Paper was alright. It seemed to lack substance.");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	
}
