package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.*;

public class AuthorTest {
	
	private Author myAuthor;
	private Manuscript myManuscript;
	private List<Manuscript> myManuscriptList;
	private String myAuthorID;
	private Conference myConferenceThatHasASubmissionDeadlineInTheFuture;
	
	@Before
	public void setUp() {
		myConferenceThatHasASubmissionDeadlineInTheFuture = new Conference("Conference ID", "Program Chair ID", "05-11-2016", "05-07-2016", 
				"05-08-2016", "05-09-2016", "05-10-2016");
		myAuthor = new Author(myAuthorID, myConferenceThatHasASubmissionDeadlineInTheFuture);
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);	
		myManuscriptList = new ArrayList<Manuscript>();
	}
	/// need to create a conference for this to work. Not being added because its failing to meet requirements
	@Test
	public void ShowAllMyManuscriptTest() {
		
		
		List<Manuscript> myManuscriptList2 = new ArrayList<Manuscript>();
		myManuscriptList2 = myAuthor.showAllMyManuscript(myManuscriptList, "John");
		assertSame(myManuscriptList2.get(0).getAuthor(), myManuscriptList.get(0).getAuthor());
	}
	
	@Test
	public void addManuscriptTest() {		
		assertSame(myManuscriptList.get(0), myManuscript);
	}
	
	@Test
	public void deleteManuscriptTest() {
		myAuthor.deleteManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
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
