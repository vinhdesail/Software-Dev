package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.Reviewer;
import model.Role;
import model.SubprogramChair;
import model.User;

public class SubprogramChairTest {

	private SubprogramChair mySubprogramChairThatContainsOneManuscript;
	private SubprogramChair mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame;
	private SubprogramChair mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent;
	private SubprogramChair mySubprogramChairThatContainsFourManuscripts;
	private SubprogramChair mySubprogramChairThatContainsNoManuscripts;
    private Conference myConferenceToTestForAllSPC;
    private Conference mySecondaryConferenceForADifferentManuscript;
    private Manuscript myFirstManuscript; 
    private Manuscript mySecondManuscript;
    private Manuscript myThirdManuscript;
    private Manuscript myFourthManuscript;
    private Manuscript myFifthManuscript;
    private Manuscript mySixthManuscript;
    private Map<String, User> myUsers;
    private List<Role> myReviewers;
    private List<Role> myReviewersToBeComparedWithOtherReviewers;
	@Before
	public void setUp() throws Exception {
		Calendar conferenceDate = new GregorianCalendar(2016,10,17);
		Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
		Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
		Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
		Calendar decisionDueDate = new GregorianCalendar(2016,10,1);		
		myConferenceToTestForAllSPC = new Conference("ANDESCON", "doeJ", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);		
		mySecondaryConferenceForADifferentManuscript = new Conference("Family Troubles 2016", "foxeT", conferenceDate,
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);	
		myFirstManuscript = new Manuscript("Jane Foster", myConferenceToTestForAllSPC.getConferenceID(), "How To Manage Money in the new age", "The Body");
		mySecondManuscript = new Manuscript("Tim Allen", myConferenceToTestForAllSPC.getConferenceID(), "What your savings should go to", "The Body");
		myThirdManuscript = new Manuscript("Bill Free", myConferenceToTestForAllSPC.getConferenceID(), "How Bankers have been misleading your time", "The Body");
		myFourthManuscript = new Manuscript("Carl Sargan", myConferenceToTestForAllSPC.getConferenceID(), "Where your money goes when it is deposited", "The Body");
		myFifthManuscript = new Manuscript("Paula Menroe", myConferenceToTestForAllSPC.getConferenceID(), "How much your money will be worth in twenty years", "The Body");
		mySixthManuscript =  new Manuscript("Zach Fair", mySecondaryConferenceForADifferentManuscript.getConferenceID(), "The New Family of today", "The Body");	
		mySubprogramChairThatContainsOneManuscript = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscript.assignManuscripts(myFirstManuscript);
		mySubprogramChairThatContainsOneManuscript.submitRecomendation(myFirstManuscript,"This Paper was alright. It seemed to lack substance.");	
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame.assignManuscripts(myFirstManuscript);	
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent = new SubprogramChair("ImASPC",myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent.assignManuscripts(mySecondManuscript);
		mySubprogramChairThatContainsFourManuscripts =  new SubprogramChair("ImASPCWithFourManuscript", myConferenceToTestForAllSPC);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(myFirstManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(mySecondManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(myThirdManuscript);
		mySubprogramChairThatContainsFourManuscripts.assignManuscripts(myFourthManuscript);	
		mySubprogramChairThatContainsNoManuscripts = new SubprogramChair("ImASPCWithNoManuscript", myConferenceToTestForAllSPC);
		
		myUsers = new HashMap<String, User>();
		myReviewers = new ArrayList<>();
		User bob = new User("Bob");
		Reviewer bobR = new Reviewer("Bob", myConferenceToTestForAllSPC);
		bob.addRole(bobR);
		bob.switchConference(myConferenceToTestForAllSPC);
		bob.autoSetRole();
		User alice = new User("Alice");
		Reviewer aliceR = new Reviewer("Alice", myConferenceToTestForAllSPC);
		alice.addRole(aliceR);
		alice.switchConference(myConferenceToTestForAllSPC);
		alice.autoSetRole();
		myUsers.put("Bob", bob);
		myUsers.put("Alice", alice);
		myReviewers.add(bobR);
		myReviewers.add(aliceR);
		myReviewersToBeComparedWithOtherReviewers = mySubprogramChairThatContainsOneManuscript.getAllReviewer(myUsers);
	}
	
	/**
	 * Test for getting a list of reviewers.
	 */
	@Test
	public void getAllReviewersTest(){
		assertEquals(myReviewersToBeComparedWithOtherReviewers, myReviewers);
	}
		
	/**
	 * Tests if all of the manuscripts that are assigned will show in their assigned list.
	 */
	@Test
	public void assignManuscriptTest() {			
		assertSame(mySubprogramChairThatContainsOneManuscript.showAllAssignedManuscripts().get(0),myFirstManuscript);	
	}
	
	@Test
	public void assignManuscriptsExceptionWhenTryingToAddAManuscriptThatIsNullTest() {	
		try {
			   mySubprogramChairThatContainsOneManuscript.assignManuscripts(null);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	/**
	 * Tests the Bus. Rule that if a reviewer already has been assigned Four Manuscripts, that it will throw an exception.
	 */
	@Test
	public void assignManuscriptsExceptionListMaxedTest() {
		try {
			   mySubprogramChairThatContainsFourManuscripts.assignManuscripts(myFifthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionListManuscriptAlreadyAddedTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.assignManuscripts(myFirstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void assignManuscriptsExceptionWhenTryingToAddAManuscriptFromAnotherConferenceTest() {	
		try {
			   mySubprogramChairThatContainsOneManuscript.assignManuscripts(mySixthManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	@Test
	public void submitRecomendationTest() {	
		assertEquals(mySubprogramChairThatContainsOneManuscript.getRecommendationText(myFirstManuscript),"This Paper was alright. It seemed to lack substance.");			
	}
	@Test
	public void submitRecomendationExceptionWhereManuscriptIsNotWithinThisSubprogramChairTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.submitRecomendation(mySecondManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptAlreadyHasRecommendation() {	
		try {
			   mySubprogramChairThatContainsOneManuscript.submitRecomendation(myFirstManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptWhereThisSubprogramChairHasNoAssignedManuscript() {	
		try {
			   mySubprogramChairThatContainsNoManuscripts.submitRecomendation(myFirstManuscript,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptWhereTheGivenManuscriptIsNull() {		
		try {
			mySubprogramChairThatContainsOneManuscript.submitRecomendation(null,"Test Text");
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void submitRecomendationExceptionManuscriptWhereTheGivenRecommendationTextIsNull() {
		
		try {
			mySubprogramChairThatContainsOneManuscript.submitRecomendation(myFirstManuscript,null);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	/**
	 * This test is to check the rule for a Subprogram Chair that checks if it has been assigned the maximum number of Manuscripts. In this test there is only one
	 * Manuscript that has been assigned to the Subprogram Chair, so the expected result is false.
	 */
	@Test
	public void containsMaxAmmountOfManuscriptsWhereThereIsOnlyOneManuscriptAssignedTest() {		
		 assertFalse(mySubprogramChairThatContainsOneManuscript.containsMaxAmmountOfManuscripts());			
	}	
	
	/**
	 * This test is to check the rule for a Subprogram Chair that checks if it has been assigned the maximum number of Manuscripts. In this test there are four
	 * Manuscripts that has been assigned to the Subprogram Chair, so the expected result is True.
	 */
	@Test
	public void containsMaxAmmountOfManuscriptsWhereTheSubprogramChairHasBeenAssignedTheMaxAmountOfManuscriptsTest() {		
		 assertTrue(mySubprogramChairThatContainsFourManuscripts.containsMaxAmmountOfManuscripts());			
	}	
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsEmptyTest() {		
		 assertEquals(mySubprogramChairThatContainsNoManuscripts.containsManuscriptAt(myFirstManuscript),-1);				
	}	
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsNotWithinTheSPCWhereTheListIsNotEmptyTest() {		
		assertEquals(mySubprogramChairThatContainsOneManuscript.containsManuscriptAt(mySecondManuscript),-1);				
	}
	
	@Test
	public void containsManuscriptAtMethodWhereManuscriptIsWithinTheSPCTest() {	
		assertEquals(mySubprogramChairThatContainsOneManuscript.containsManuscriptAt(myFirstManuscript),0);
	}	
	
	@Test
	public void getRecommendationTextTest() {		
		assertEquals(mySubprogramChairThatContainsOneManuscript.getRecommendationText(myFirstManuscript),"This Paper was alright. It seemed to lack substance.");
	}
	
	@Test
	public void getRecommendationTextExceptionWhereTheGivenManuscriptIsNotAssignedToThisInstanceOfASubprogramChairTest() {
		try {
			   mySubprogramChairThatContainsOneManuscript.getRecommendationText(mySecondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextExceptionManuscriptNotFoundTest() {
		
		try {
			   mySubprogramChairThatContainsOneManuscript.getRecommendationText(mySecondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void getRecommendationTextExceptionWhereTheGivenManuscriptIsNull() {
		
		try {
			   mySubprogramChairThatContainsOneManuscript.getRecommendationText(null);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 				
	}
	
	@Test
	public void containsManuscriptAtExceptionWhereTheGivenManuscriptIsNullTest() {
		try {
			mySubprogramChairThatContainsOneManuscript.containsManuscriptAt(null);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 	
	}
	
	@Test
	public void getAllReviewerExceptionWhereTheGivenMaMapOfUsersIsNullTest() {
		try {
			mySubprogramChairThatContainsOneManuscript.getAllReviewer(null);
		    fail("Exception wasn't caught");
		} catch (IllegalArgumentException theException) {
			   
		} 	
	}
	
	@Test
	public void equalsWhereBothSPCAreTheSameTest() {		
		assertTrue(mySubprogramChairThatContainsOneManuscript.equals(mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsTheSame));
	}
	
	@Test
	public void assignReviewerExceptionWhereTheGivenManuscriptIsNull() {
		try {
			mySubprogramChairThatContainsOneManuscript.assignReviewer((Reviewer)myReviewers.get(0), null);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 
	}
	
	@Test
	public void assignReviewerExceptionWhereTheGivenReviewerIsNull() {
		try {
			mySubprogramChairThatContainsOneManuscript.assignReviewer(null, myFirstManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 
	}
	
	@Test
	public void assignReviewerExceptionWhereTheGivenManuscriptHasNotBeenAssignedToThisInstanceOfASubprogramChairNull() {
		try {
			mySubprogramChairThatContainsOneManuscript.assignReviewer((Reviewer)myReviewers.get(0), mySecondManuscript);
			   fail("Exception wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 
	}
	
	@Test
	public void equalsWhereBothSPCAreTheDifferentByManuscript() {		
		assertFalse(mySubprogramChairThatContainsOneManuscript.equals(mySubprogramChairThatContainsOneManuscriptToCompareToOtherSPCWithOneManuscriptThatIsDifferent));
	}	
}
