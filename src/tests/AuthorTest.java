package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.*;
/**
 * Class that tests the Author Class.
 * @author Joshua Meigs, Justin Clark
 * @version 2016.5.31
 */
public class AuthorTest {
	
	private Author myAuthorThatIsInAConferenceThatIsInThePast;
	private Author myAuthorThatHasSubmittedNoManuscripts;
	private Author myAuthorThatHasSubmitedOneManuscript;
	private Author myAuthorThatHasSubmitedAndDeletedOneManuscript;
	private Author myAuthorThatHasSubmitedAndEditedOneManuscript;
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor;
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor;
	private Manuscript myFirstManuscript;
	private Manuscript mySecondManuscript;
	private List<Manuscript> myMasterManuscriptListForAllManuscripts;
	private List<Manuscript> myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript;
	private Conference myConferenceThatHasASubmissionDeadlineInThePast;
	private Conference myConferenceThatHasASubmissionDeadlineInTheFuture;
	
	@Before
	public void setUp() {
		Calendar futureConferenceDate = new GregorianCalendar(2018,10,17);
		Calendar futureManuscriptDueDate = new GregorianCalendar(2018,9,1);
		Calendar futureReviewDueDate = new GregorianCalendar(2018,9,19);
		Calendar futureRecommendationDueDate = new GregorianCalendar(2018,10,1);
		Calendar futureDecisionDueDate = new GregorianCalendar(2018,10,1);	
		Calendar pastConferenceDate = new GregorianCalendar(2014,10,17);
		Calendar pastManuscriptDueDate = new GregorianCalendar(2014,9,1);
		Calendar pastReviewDueDate = new GregorianCalendar(2014,9,19);
		Calendar pastRecommendationDueDate = new GregorianCalendar(2014,10,1);
		Calendar pastDecisionDueDate = new GregorianCalendar(2014,10,1);	
		myConferenceThatHasASubmissionDeadlineInTheFuture = new Conference("Conference ID", "Program Chair ID", futureConferenceDate, futureManuscriptDueDate, 
				futureReviewDueDate, futureRecommendationDueDate, futureDecisionDueDate);
		myConferenceThatHasASubmissionDeadlineInThePast = new Conference("Conference ID", "Program Chair ID", pastConferenceDate, pastManuscriptDueDate, 
				pastReviewDueDate, pastRecommendationDueDate, pastDecisionDueDate);	
		myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript =  new ArrayList<Manuscript>();
		myMasterManuscriptListForAllManuscripts =  new ArrayList<Manuscript>();
		
		myAuthorThatIsInAConferenceThatIsInThePast =  new Author("Jim", myConferenceThatHasASubmissionDeadlineInThePast); 
		myAuthorThatHasSubmittedNoManuscripts =  new Author("Tom Beth", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedOneManuscript = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedAndDeletedOneManuscript = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedAndEditedOneManuscript = new Author("Buckle Tros", myConferenceThatHasASubmissionDeadlineInTheFuture);	
		myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor = new Author("Not Tom", myConferenceThatHasASubmissionDeadlineInTheFuture);		
		myFirstManuscript = new Manuscript("Tom Banks", "Science", "Computer manuscript", "Some text");
		mySecondManuscript = new Manuscript("Buckle Tros", "Science", "Computer manuscript", "Some text");
		myAuthorThatHasSubmitedOneManuscript.addManuscript(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript, myFirstManuscript);
		myAuthorThatHasSubmitedAndDeletedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedAndEditedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);		
		myAuthorThatHasSubmitedAndDeletedOneManuscript.deleteManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedAndEditedOneManuscript.editManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript, "The New Title");
		myMasterManuscriptListForAllManuscripts.add(mySecondManuscript);
	}
	
	@Test
	public void testAddManuscript() {		
		assertSame(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript.get(0), myFirstManuscript);
	}
	
	@Test
	public void testAddManuscriptExceptionIfTheDeadlineHasBeenPassed() {	
		try {
			myAuthorThatIsInAConferenceThatIsInThePast.addManuscript(myMasterManuscriptListForAllManuscripts, mySecondManuscript);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test

	public void testAddManuscriptExceptionIfTheManuscriptHasAlreadyBeenSubmited() {	
		try {
			myAuthorThatHasSubmitedOneManuscript.addManuscript(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript, myFirstManuscript);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	public void testDeleteManuscriptNullListException() {
		try {
			myAuthorThatHasSubmitedOneManuscript.deleteManuscript(null, mySecondManuscript);
			fail("Null Manuscript in deleteManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testDeleteManuscriptNullManuscriptException() {
		try {
			myAuthorThatHasSubmitedOneManuscript.deleteManuscript(myMasterManuscriptListForAllManuscripts, null);
			fail("Null Manuscript in deleteManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void deleteManuscriptWhereAuthorHasNoManuscriptsTest() {
		
	}
	
	@Test
	public void testAddManuscriptExpectionWhereTheGivenListIsNull() {		
		try {
			myAuthorThatHasSubmitedOneManuscript.addManuscript(null, myFirstManuscript);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testAddManuscriptExpectionWhereTheGivenManuscriptIsNull() {		
		try {
			myAuthorThatHasSubmitedOneManuscript.addManuscript(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript, null);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}			
	
	@Test
	public void testDeleteManuscript() {
		assertEquals(myAuthorThatHasSubmitedAndDeletedOneManuscript.showAllMyManuscripts().size(), 0);
	}
	
	@Test
	public void testEditManuscriptToVerifyThatTheTextHasChanged() {
		assertEquals(myAuthorThatHasSubmitedAndEditedOneManuscript.showAllMyManuscripts().get(0).getTitle(), "The New Title");
	}
	
	@Test
	public void testEditManuscriptExceptionWhereAuthorDoesNotContainTheGivenManuscript() {
		try {
			myAuthorThatHasSubmitedOneManuscript.editManuscript(myMasterManuscriptListForAllManuscripts, mySecondManuscript, "New Title");
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testEditManuscriptExceptionWhereTheGivenListIsNull() {
		try {
			myAuthorThatHasSubmitedOneManuscript.editManuscript(null, mySecondManuscript, "New Title");
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testEditManuscriptExceptionWhereTheGivenManuscriptIsNull() {
		try {
			myAuthorThatHasSubmitedOneManuscript.editManuscript(myMasterManuscriptListForAllManuscripts, null, "New Title");
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testEditManuscriptExceptionWhereTheGivenTitleIsNull() {
		try {
			myAuthorThatHasSubmitedOneManuscript.editManuscript(myMasterManuscriptListForAllManuscripts, mySecondManuscript, null);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testDeleteManuscriptWhereAuthorHasNoManuscripts() {
		try {
			myAuthorThatHasSubmittedNoManuscripts.deleteManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
			fail("Exception not Caught.");
		} catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testIsPastSubmissionDeadlineForAAuthorThatIsWorkingInAConferenceThatStillHasTheAbilityToSubmitAManuscript() {
		assertFalse(myAuthorThatHasSubmitedOneManuscript.isPastSubmissionDeadline());
	}
	
	@Test
	public void testIsPastSubmissionDeadlineForAAuthorThatIsWorkingInAConferenceThatDoesNotHaveTheAbilityToSubmitAManuscript() {
		assertTrue(myAuthorThatIsInAConferenceThatIsInThePast.isPastSubmissionDeadline());
	}
	
	@Test
	public void testEqualsWhereBothAuthorsAreTheSame() {
		assertTrue(myAuthorThatHasSubmitedOneManuscript.equals(myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor));
	}
	
	@Test
	public void testEqualsWhereBothAuthorsAreDifferent() {
		assertFalse(myAuthorThatHasSubmitedOneManuscript.equals(myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor));
	}
	
	
}
