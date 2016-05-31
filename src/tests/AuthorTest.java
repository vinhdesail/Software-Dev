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
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor;
	private Author myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor;
	private Manuscript myFirstManuscript;
	private Manuscript mySecondManuscript;
	private List<Manuscript> myMasterManuscriptListForAllManuscripts;
	private List<Manuscript> myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript;
	private List<Manuscript> myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison;
	
	private String myAuthorID;
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
		
		myAuthorThatHasNotSubmitedAManuscript = new Author(myAuthorID, myConferenceThatHasASubmissionDeadlineInThePast);
		myAuthorThatHasSubmitedOneManuscript = new Author(myAuthorID, myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedOneManuscriptThatIsTheSameAsOtherAuthor = new Author(myAuthorID, myConferenceThatHasASubmissionDeadlineInTheFuture);
		myAuthorThatHasSubmitedOneManuscriptThatIsDifferentAsOtherAuthor = new Author(myAuthorID, myConferenceThatHasASubmissionDeadlineInTheFuture);
		myFirstManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript = new ArrayList<Manuscript>();
		myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison = new ArrayList<Manuscript>();
		myAuthorThatHasSubmitedOneManuscript.addManuscript(myMasterManuscriptListForAllManuscripts, myFirstManuscript);	
		
	}
	/// need to create a conference for this to work. Not being added because its failing to meet requirements
	@Test
	public void ShowAllMyManuscriptTest() {					
		assertSame(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscript.get(0).getAuthor(), myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison.get(0).getAuthor());
	}
	
	@Test
	public void addManuscriptTest() {		
		assertSame(myManuscriptListForAnAuthorThatHasSubmittedTheFirstManuscriptThatIsIdenticalToTheOtherListForComparison.get(0), myFirstManuscript);
	}
	
	@Test
	public void deleteManuscriptTest() {
		myAuthor.deleteManuscript((ArrayList<Manuscript>) myMasterManuscriptListForAllManuscripts, myManuscript);
		assertEquals(myManuscriptList.size(), 0);
	}
	
	@Test
	public void editManuscriptTest() {
		Manuscript myNewManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some different text!");
		myAuthor.editManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript, myNewManuscript);
		assertEquals(myManuscriptList.size(), 1);
	}
	
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
}
