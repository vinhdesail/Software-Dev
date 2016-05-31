package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import model.Manuscript;
import model.Recommendation;
import model.Review;

/**
 * Tests for Manuscript class.
 * @author Edie Megan Campbell
 * @version 2016.05.08
 */
public class ManuscriptTest {
	
	String authorID = "Author Name";
	String conferenceID = "Conference Name";
	String title1 = "Title 1";
	String title2 = "Title 2";
	String filePath1 = "Manuscript1.txt";
	
	Manuscript basicManuscript;
	Manuscript manuscriptWithOneReview;
	Manuscript manuscriptAssignedToSPC;
	
	String reviewer1ID = "Reviewer1 Name";
	String reviewFilePath = "Review1.txt";
	Review review1;
	Review review2;
	
	String subprogramChairID = "Subprogram Chair Name";
	String recommendationFilePath = "Recommendation1.txt";
	Recommendation recommendation1;
	Recommendation recommendation2;
	
	int invalidStatus;
	
	@Before
	public void setup() {
		basicManuscript = new Manuscript(authorID, conferenceID, title1, filePath1);
		
		review1 = new Review(reviewer1ID, title1, reviewFilePath);
		review2 = new Review(reviewer1ID, title2, reviewFilePath);
		List<Review> listOfOneReview = new ArrayList<Review>();
		listOfOneReview.add(review1);
		manuscriptWithOneReview = new Manuscript(authorID, conferenceID, title1, filePath1, 
				listOfOneReview);
		manuscriptAssignedToSPC = new Manuscript(authorID, conferenceID, title1, filePath1);
		manuscriptAssignedToSPC.setAssignedASubprogramChair();
		recommendation1 = new Recommendation(subprogramChairID, title1, recommendationFilePath);
		recommendation2 = new Recommendation(subprogramChairID, title2, recommendationFilePath);
		
		Random rand = new Random();
		do {
			invalidStatus = rand.nextInt();
		} while (invalidStatus == Manuscript.ACCEPT || invalidStatus == Manuscript.REJECT 
				|| invalidStatus == Manuscript.UNDECIDED);
	}
	
	// test all possible null parameter exceptions for Manuscript constructor
	
	@Test
	public void constructorNullAuthorIDExceptionTest() {
		try {
			new Manuscript(null, conferenceID, title1, filePath1);
			fail("null Manuscript in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullConferenceIDExceptionTest() {
		try {
			new Manuscript(authorID, null, title1, filePath1);
			fail("null Conference in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	public void constructorNullTitleExceptionTest() {
		try {
			new Manuscript(authorID, conferenceID, null, filePath1);
			fail("null Manuscript in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void constructorNullFilePathExceptionTest() {
		try {
			new Manuscript(authorID, conferenceID, title1, null);
			fail("null Conference in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	// partitions for addReview():
	
	/* Unsuccessful addReview: null Review must throw IllegalArgumentException
	 */
	@Test
	public void addReviewNullReviewExceptionTest() {
		try {
			basicManuscript.addReview(null);
			fail("null Review in addReview did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful addReview: Review that is not for this Manuscript must throw 
	 * IllegalArgumentException
	 */
	@Test
	public void addReviewWrongManuscriptExceptionTest() {
		try {
			basicManuscript.addReview(review2);
			fail("Review on wrong manuscript in addReview did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Successful addReview: add a non-null Review that is for this Manuscript
	 */
	@Test
	public void addReviewSuccessTest() {
		assertTrue("Manuscript did not start with empty Review list", basicManuscript.getReviews().isEmpty());
		basicManuscript.addReview(review1);
		assertEquals("Manuscript did not finish with 1 Review", 1, basicManuscript.getReviews().size());
		assertEquals("Manuscript's Review does not match the Review added", review1, 
				basicManuscript.getReviews().get(0));
	}
	
	// partitions for removeReview():
	
	/* Unsuccessful removeReview: null Review must throw IllegalArgumentException
	 */
	@Test
	public void removeReviewNullReviewExceptionTest() {
		try {
			basicManuscript.removeReview(null);
			fail("null Review in removeReview did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful removeReview: Review that is not for this Manuscript must throw an
	 * IllegalArgumentException
	 */
	@Test
	public void removeReviewWrongManuscriptExceptionTest() {
		try {
			basicManuscript.removeReview(review2);
			fail("Review on wrong manuscript in removeReview did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful removeReivew: Review was not found in this Manuscript's list of Reviews,
	 * must throw an IllegalArgumentException
	 */
	@Test
	public void removeReviewNotFoundExceptionTest() {
		try {
			basicManuscript.removeReview(review1);
			fail("Review not found in removeReview did not throw exception");
		} catch (IllegalArgumentException theException) {	
			assertEquals("Wrong exception", "Review was not found in Manuscript's list.", 
					theException.getMessage());
		}
	}
	
	/* Successful removeReview: remove a non-null Review that is for this Manuscript and that
	 * was part of the Manuscript's review list
	 */
	@Test
	public void removeReviewSuccessTest() {
		
		assertEquals("Manuscript did not start with 1 review.", 1, 
				manuscriptWithOneReview.getReviews().size());
		assertTrue("Review was not in Manuscript's list", 
				manuscriptWithOneReview.getReviews().contains(review1));
		
		manuscriptWithOneReview.removeReview(review1);
		
		assertTrue("Manuscript did not end with no reviews.", 
				manuscriptWithOneReview.getReviews().isEmpty());
	}
	
	// partitions for setRecommendation():
	
	/* Unsuccessful setRecommendation: null Recommendation must throw  an IllegalArgumentException
	 */
	@Test
	public void setRecommendationNullRecommendationExceptionTest() {
		try {
			basicManuscript.setRecommendation(null);
			fail("Null recommendation didn't throw an exception in setRecommendation");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful setRecommendation: wrong manuscript for recommendation must throw an
	 * IllegalArgumentException
	 */
	@Test
	public void setRecommendationWrongManuscriptExceptionTest() {
		try {
			basicManuscript.setRecommendation(recommendation2);
			fail("Recommendation for wrong manuscript didn't throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful setRecommendation: this manuscript has not assigned to subprogram chair,
	 * must throw an IllegalArgumentException
	 */
	@Test
	public void setRecommendationNotAssignedToSPCExceptionTest() {
		try {
			basicManuscript.setRecommendation(recommendation1);
			fail("Manuscript not assigned to SPC didn't throw an exception");
		} catch (IllegalArgumentException theException) {
			assertEquals("Wrong exception thrown", "Manuscript has not been assigned to a Subprogram "
					+ "Chair for recommendation.", theException.getMessage());
		}
	}
	
	/* Successful setRecommendation
	 */
	public void setRecommendationSuccessTest() {
		assertEquals("Recommendation did not start as null", null, 
				manuscriptAssignedToSPC.getRecommendation());
		manuscriptAssignedToSPC.setRecommendation(recommendation1);
		assertEquals("Recommendation did not end as expected recommendation", recommendation1, 
				manuscriptAssignedToSPC.getRecommendation());
	}
	
	/* Null title in setTitle must throw an IllegalArgumentException
	 */
	public void setTitleNullTitleExceptionTest() {
		try {
			basicManuscript.setTitle(null);
			fail("Null title did not throw exception in setTitle");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Null file path in setFilePath must throw an IllegalArgumentException
	 */
	public void setFilePathNullFilePathExceptionTest() {
		try {
			basicManuscript.setFilePath(null);
			fail("Null file path did not throw exception in setFilePath");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Invalid status code in setStatus must throw an IllegalArgumentException
	 */
	public void setStatusInvalidStatusExceptionTest() {
		try {
			basicManuscript.setStatus(invalidStatus);
			fail("Invalid status code " + invalidStatus + " did not throw exception in setFilePath");
		} catch (IllegalArgumentException theException) {	}
	}
}
