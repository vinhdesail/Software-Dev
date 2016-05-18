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
	private Conference myConference;
	
	@Before
	public void setUp() {
		myConference = new Conference("Conference ID", "Program Chair ID", "05-11-2016", "05-07-2016", 
				"05-08-2016", "05-09-2016", "05-10-2016");
		myAuthor = new Author(myAuthorID, myConference);
		myManuscriptList = new ArrayList<Manuscript>();
	}
	/// need to create a conference for this to work. Not being added because its failing to meet requirements
	@Test
	public void ShowAllMyManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);	
		List<Manuscript> myManuscriptList2 = new ArrayList<Manuscript>();
		myManuscriptList2 = myAuthor.showAllMyManuscript(myManuscriptList, "John");
		assertSame(myManuscriptList2.get(0).getAuthor(), myManuscriptList.get(0).getAuthor());
	}
	
	@Test
	public void addManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		assertSame(myManuscriptList.get(0), myManuscript);
	}
	
	@Test
	public void deleteManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		myAuthor.deleteManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		assertEquals(myManuscriptList.size(), 0);
	}
	
	@Test
	public void editManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		Manuscript myNewManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some different text!");
		myAuthor.editManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript, myNewManuscript);
		assertEquals(myManuscriptList.size(), 1);
	}
	
	// will have to make conference and assign a review.
	@Test
	public void getReviewsTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myManuscript.addReview(new Review("Bob", myManuscript.getTitle(), "All good!"));
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		
		System.out.println(myManuscriptList.get(0).getReviews());
		myManuscript.setStatus(1);
		List<Review> myReviews = new ArrayList<Review>();
		myReviews.addAll(myAuthor.getReviews());
		System.out.println(myReviews);
		assertEquals(myReviews.get(0).getReviewText(), myManuscriptList.get(0).getReviews().get(0).getReviewText());
	}
}
