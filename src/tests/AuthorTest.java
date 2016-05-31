package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.*;

public class AuthorTest {
	
	private Author myAuthorThatHasNotSubmitedAManuscript;
	private Author myAuthorThatHasSubmitedOneManuscript;
	private Author myAuthorThatHasSubmitedAndDeletedOneManuscript;
	private Author myAuthorThatHasSubmitedAndEditedOneManuscript;
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor;
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor;
	private Manuscript myFirstManuscript;
	private Manuscript mySecondManuscript;
	private Manuscript myFirstManuscriptThatHasEditedFields;
	private List<Manuscript> myMasterManuscriptListForAllManuscripts;
	private List<Manuscript> myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript;
	private List<Manuscript> myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison;
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
		myConferenceThatHasASubmissionDeadlineInTheFuture = new Conference("Conference ID", "Program Chair ID", pastConferenceDate, pastManuscriptDueDate, 
				pastReviewDueDate, pastRecommendationDueDate, pastDecisionDueDate);
		
		myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript =  new ArrayList<Manuscript>();
		myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison =  new ArrayList<Manuscript>();
		myMasterManuscriptListForAllManuscripts =  new ArrayList<Manuscript>();
		
		myAuthorThatHasNotSubmitedAManuscript = new Author("Jim", myConferenceThatHasASubmissionDeadlineInThePast);
		
		myAuthorThatHasSubmitedOneManuscript = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedAndDeletedOneManuscript = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedAndEditedOneManuscript = new Author("Buckle Tros", myConferenceThatHasASubmissionDeadlineInTheFuture);	
		myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor = new Author("Tom Banks", myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor = new Author("Not Tom", myConferenceThatHasASubmissionDeadlineInTheFuture);
		
		myFirstManuscript = new Manuscript("Tom Banks", "Science", "Computer manuscript", "Some text");
		mySecondManuscript = new Manuscript("Buckle Tros", "Science", "Computer manuscript", "Some text");
		myFirstManuscriptThatHasEditedFields =  new Manuscript("Tom Banks", "Science", "Computer manuscript", "Some New text");
		myAuthorThatHasSubmitedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedAndDeletedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedAndEditedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);	
		
		myAuthorThatHasSubmitedAndDeletedOneManuscript.deleteManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);
		myAuthorThatHasSubmitedAndEditedOneManuscript.editManuscript(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript, myFirstManuscript, myFirstManuscriptThatHasEditedFields);
		myMasterManuscriptListForAllManuscripts.add(mySecondManuscript);
	}
	/// need to create a conference for this to work. Not being added because its failing to meet requirements
	@Test
	public void ShowAllMyManuscriptTest() {					
		assertSame(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript.get(0), 
				myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison.get(0));
	}
	
	@Test
	public void addManuscriptTest() {		
		assertSame(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript.get(0), myFirstManuscript);
	}
	
	@Test
	public void deleteManuscriptTest() {
		assertEquals(myAuthorThatHasSubmitedAndDeletedOneManuscript.showAllMyManuscript(myMasterManuscriptListForAllManuscripts,
				myAuthorThatHasSubmitedAndDeletedOneManuscript.getMyUsername()).size(), 0);
	}
	
	@Test
	public void editManuscriptTest() {
		assertEquals(myAuthorThatHasSubmitedAndDeletedOneManuscript.showAllMyManuscript(myMasterManuscriptListForAllManuscripts,
				myAuthorThatHasSubmitedAndDeletedOneManuscript.getMyUsername()).size(), 1);
	}
	/*
	// will have to make conference and assign a review.
	@Test
	public void getReviewsTest() {
		myManuscript.addReview(new Review("Bob", myManuscript.getTitle(), "All good!"));
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		
		System.out.println(myManuscriptList.get(0).getReviews());
		myManuscript.setStatus(1);
		List<Review> myReviews = new ArrayList<Review>();
		myReviews.addAll(myAuthor.getReviews());
		System.out.println(myReviews);
		assertEquals(myReviews.get(0).getReviewText(), myManuscriptList.get(0).getReviews().get(0).getReviewText());
	}
	
	@Test
	public void isPastSubmissionDeadlineTest() {
		Manuscript myNewManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some different text!");
		myAuthor.editManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript, myNewManuscript);
		assertEquals(myManuscriptList.size(), 1);
	}
	*/
}
