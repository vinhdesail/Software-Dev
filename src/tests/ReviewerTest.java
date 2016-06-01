package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
//github.com/vinhdesail/Software-Dev.git
import model.Manuscript;
import model.Review;
import model.Reviewer;

/**
 * Tests the Reviewer Class (Model)
 * @author Edie Megan Campbell, Joshua Meigs
 * @version 2016.05.31
 */
public class ReviewerTest {

	private Conference testCon;
	private String conferenceID = "Conference ID";
	private String programChairID = "Program Chair ID";
	private Calendar conferenceDate = new GregorianCalendar(2016,10,17);
	private Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
	private Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
	private Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
	private Calendar decisionDueDate = new GregorianCalendar(2016,10,1);
	
	private Reviewer reviewerAssignedToManuscript;
	private Reviewer reviewerNotAssignedToManuscript;
	private Reviewer reviewerAssignedToSingleManuscript;
	private Reviewer reviewerAssignedToMaxNumberManuscripts;
	private Review expectedReview1;
	private Review expectedReview2;
	private Review expectedEdit1;
	
	private String reviewer1ID = "Reviewer 1's Name";
	private String reviewer2ID = "Reviewer 2's Name";
	
	private String review1FilePath = "File Path of Review where I loved it";
	private String review2FilePath = "File Path of Review where I hated it";
	
	private Manuscript manuscriptThatIsAuthoredByAReviewer;
	private Manuscript manuscriptThatIsNotAuthoredByAReviewer;
	private Manuscript manuscriptStartingWithNoReviews;
	private Manuscript otherManuscriptStartingWithNoReviews;
	private Manuscript manuscriptStartingWithOtherReview;
	private Manuscript manuscriptStartingWithMyReview;
	private Manuscript manuscriptStartingWithBothReviews;
	private List<Manuscript> listOfManuscriptsAssigned;
	private List<Manuscript> listOfSingleManuscript;
	private List<Manuscript> listOfMaxNumManuscripts;
	
	private List<Review> listWithSingleOtherReview;
	private List<Review> listWithMyReview;
	private List<Review> listWithMyReviewAndOtherReview;
	private String authorID = "Author Name";
	private String title = "Title of Manuscript";
	private String textURL = "URL of Manuscript Text";
	

	
	@Before
	public void setup() {
		
		expectedReview1 = new Review(reviewer1ID, title, review1FilePath);
		expectedReview2 = new Review(reviewer2ID, title, review2FilePath);
		expectedEdit1 = new Review(reviewer1ID, title, review2FilePath);
		
		testCon = new Conference(conferenceID, programChairID, conferenceDate, manuscriptDueDate, 
									reviewDueDate, recommendationDueDate, decisionDueDate);
		
		manuscriptStartingWithNoReviews = new Manuscript(authorID, conferenceID, title, textURL);
		manuscriptThatIsAuthoredByAReviewer = new Manuscript(reviewer1ID, conferenceID, title, textURL);
		manuscriptThatIsNotAuthoredByAReviewer = new Manuscript("notAnReviewer", conferenceID, title, textURL);
		otherManuscriptStartingWithNoReviews = new Manuscript(authorID, conferenceID, "DifferentTitle", textURL);
		listWithSingleOtherReview = new ArrayList<Review>();
		listWithSingleOtherReview.add(expectedReview2);
		
		listWithMyReview = new ArrayList<Review>();
		listWithMyReview.add(expectedReview1);
		
		listWithMyReviewAndOtherReview = new ArrayList<Review>();
		listWithMyReviewAndOtherReview.add(expectedReview1);
		listWithMyReviewAndOtherReview.add(expectedReview2);
		
		manuscriptStartingWithOtherReview = new Manuscript(authorID, conferenceID, title, textURL, 
				listWithSingleOtherReview);
		manuscriptStartingWithMyReview = new Manuscript(authorID, conferenceID, title, textURL, listWithMyReview);
		manuscriptStartingWithBothReviews = new Manuscript(authorID, conferenceID, title, textURL, 
				listWithMyReviewAndOtherReview);
		
		listOfManuscriptsAssigned = new ArrayList<Manuscript>();
		listOfManuscriptsAssigned.add(manuscriptStartingWithBothReviews);
		listOfManuscriptsAssigned.add(manuscriptStartingWithMyReview);
		listOfManuscriptsAssigned.add(manuscriptStartingWithNoReviews);
		listOfManuscriptsAssigned.add(manuscriptStartingWithOtherReview);
		reviewerAssignedToManuscript = new Reviewer(reviewer1ID, testCon, listOfManuscriptsAssigned);
		
		listOfSingleManuscript = new ArrayList<Manuscript>();
		listOfSingleManuscript.add(manuscriptStartingWithNoReviews);
		reviewerAssignedToSingleManuscript = new Reviewer(reviewer1ID, testCon, listOfSingleManuscript);
		
		listOfMaxNumManuscripts = new ArrayList<Manuscript>();
		for (int i = 0; i < Reviewer.MAX_MANUSCRIPTS; i++) {
			listOfMaxNumManuscripts.add(manuscriptStartingWithNoReviews);
		}
		reviewerAssignedToMaxNumberManuscripts = new Reviewer(reviewer1ID, testCon, listOfMaxNumManuscripts);
		
		reviewerNotAssignedToManuscript = new Reviewer(reviewer1ID, testCon);
	}

	
	// Partitions for submitReview():
	
	// successful submit, first review submitted for manuscript
	@Test
	public void testSubmitFirstReviewForManuscript() {
		
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1FilePath);
		
		// because manuscript started with no submitted reviews, this should be the only Review
		assertEquals("Manuscript's number of Reviews was not 1!", 1, 
				manuscriptStartingWithNoReviews.getReviews().size());
		
		// verify that the Review associated with the Manuscript is the expected Review
		assertEquals("Manuscript's Review is not equal to expected submitted Review!",
				expectedReview1, manuscriptStartingWithNoReviews.getReviews().get(0));
	}
	
	// successful submit, not the first review submitted for manuscript
	@Test
	public void testSubmitNotFirstReviewForManuscript() {
		assertEquals("Manuscript did not start with 1 Review!", 1, 
						manuscriptStartingWithOtherReview.getReviews().size());
		
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithOtherReview, review1FilePath);
		
		// now the number of Reviews must equal 2
		assertEquals("Manuscript did not finish with 2 Reviews!", 2, 
				manuscriptStartingWithOtherReview.getReviews().size());
		
		// verify that the expected Review is now associated with the Manuscript
		assertTrue("Expected submitted Review was not found in Manuscript's Review List!", 
				manuscriptStartingWithOtherReview.getReviews().contains(expectedReview1));
	}

	/* Reviewer hasn't been assigned to this Manuscript, must throw an IllegalArgumentException
	 * for both cases:
	 * 1. with a manuscript with no reviews
     * 2. with a manuscript with some reviews
     */
	@Test
	public void testSubmitReviewForUnassignedManuscriptException() {
		
		// test for a manuscript with no reviews
		try {
			   reviewerNotAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1FilePath);
			   fail("Exception for submitting a review on a manuscript not assigned wasn't caught");			   
		} catch (IllegalArgumentException theException) {   } 		
		
		// test for a manuscript with a review
		try {
			   reviewerNotAssignedToManuscript.submitReview(manuscriptStartingWithOtherReview, review1FilePath);
			   fail("Exception for submitting a review on a manuscript not assigned wasn't caught");			   
		} catch (IllegalArgumentException theException) {   } 
	}
	
	/* Reviewer has already submitted a Review for this Manuscript, must throw an
	 * IllegalArgumentException
	 * This tests for attempts to resubmit the exact same Review for a manuscript twice.
	 */
	@Test
	public void testResubmitReviewForManuscriptException() {
		try {
			reviewerAssignedToManuscript.submitReview(manuscriptStartingWithMyReview, review1FilePath);
			fail("Exception for submitting a review when one has already been submitted wasn't caught");
		} catch (IllegalArgumentException theException) {	}
	}
	
	 /*  Reviewer has submitted a Review for this Manuscript and is attempting to "edit", 
	  *  must throw an IllegalArgumentException.
	  *  This tests for attempts to submit a new review for a manuscript that this Reviewer has
	  *  already reviewed. 
	  */
	@Test
	public void testSubmitReviewWhenTryingToEditReviewException() {
		try {
			reviewerAssignedToManuscript.submitReview(manuscriptStartingWithMyReview, review2FilePath);
			fail("Exception for submitting a review when one has already been submitted wasn't caught");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/*
	 * Null argument passed as Manuscript must throw IllegalArgumentException.
	 */
	@Test
	public void testSubmitReviewForNullManuscriptException() {
		try {
			reviewerAssignedToManuscript.submitReview(null, review1FilePath);
			fail("Null Manuscript did not throw exception.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/*
	 * Null argument passed as File Path must throw IllegalArgumentException.
	 */
	@Test
	public void testSubmitReviewForNullFilePathException() {
		try {
			reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, null);
			fail("Null File Path did not throw exception.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	// Partitions for editReview():
	
	/* Successful edit for the case when this Manuscript has no other Reviews besides the one
	 * written by this Reviewer.
	 */
	@Test
	public void testEditReviewForManuscriptWithNoOtherReviews() {
		
		assertEquals("Manuscript did not start out with expected Review from this Reviewer!", 
					expectedReview1, manuscriptStartingWithMyReview.getReviews().get(0));
		
		reviewerAssignedToManuscript.editReview(manuscriptStartingWithMyReview, review2FilePath);
		
		assertEquals("Manuscript did not finish with 1 Review!", 1, 
						manuscriptStartingWithMyReview.getReviews().size());
		assertEquals("Manuscript's Review not equal to expected edited Review!", expectedEdit1, 
						manuscriptStartingWithMyReview.getReviews().get(0));
	}
	
	/* Successful edit, for a Manuscript with other Reviews besides the one written and
	 * edited by this Reviewer. The test verifies that the other Reivew is not affected
	 * by the call to editReview().
	 */
	@Test
	public void testEditReviewForManuscriptWithOtherReviews() {
		
		assertTrue("Intially: Original Review not part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedReview1));
		assertTrue("Initially: Other Review not part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedReview2));
		
		reviewerAssignedToManuscript.editReview(manuscriptStartingWithBothReviews, review2FilePath);
		
		assertEquals("Manuscript did not finish with 2 Reviews", 2, 
					manuscriptStartingWithBothReviews.getReviews().size());
		assertTrue("Expected edited Review not part of Manuscript's Review List", 
					manuscriptStartingWithBothReviews.getReviews().contains(expectedEdit1));
		assertTrue("Other Review no longer part of Manuscript's Review List", 
					manuscriptStartingWithBothReviews.getReviews().contains(expectedReview2));
	}
	
	/* Unsuccessful edit: editing a Review for a Manuscript this Reviewer has not been assigned to.
	 * Must throw an IllegalArgumentException.
	 */
	@Test
	public void testEditReviewForUnnassignedManuscriptException() {
		try {
			reviewerNotAssignedToManuscript.editReview(manuscriptStartingWithMyReview, review2FilePath);
			fail("Exception for editing a Review on a Manuscript not assigned to this Review not Caught");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful: editing a Review for a Manuscript which this Reviewer has not yet submitted a 
	 * Review for. Must throw an IllegalArgumentException.
	 */
	@Test
	public void testEditReviewForManuscriptNotYetReviewedException() {
		try {
			reviewerAssignedToManuscript.editReview(manuscriptStartingWithNoReviews, review1FilePath);
			fail("Exception for editing a Review when this Reviewer has not yet submitted one not Caught");
		} catch (IllegalArgumentException theException) {	}	
	}
	
	/*
	 * Null argument passed as Manuscript must throw IllegalArgumentException.
	 */
	@Test
	public void testEditReviewForNullManuscriptException() {
		try {
			reviewerAssignedToManuscript.editReview(null, review1FilePath);
			fail("Null Manuscript did not throw exception.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/*
	 * Null argument passed as File Path must throw IllegalArgumentException.
	 */
	@Test
	public void testEditReviewForNullFilePathException() {
		try {
			reviewerAssignedToManuscript.editReview(manuscriptStartingWithNoReviews, null);
			fail("Null File Path did not throw exception.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	// Paritions for assignReview():
	
	/* Successfully assign first paper to Reviewer, where that paper currently has no reviews.
	 */
	@Test
	public void testAssignFirstPaperWithNoReviewsToReviewer() {
		assertEquals("Reviewer did not start unassigned to any Manuscripts!", 0, 
				reviewerNotAssignedToManuscript.getMyManuscripts().size());
		
		reviewerNotAssignedToManuscript.assignReview(manuscriptStartingWithNoReviews);
		
		assertEquals("Reviewer did not finish assigned to 1 Manuscript!", 1, 
				reviewerNotAssignedToManuscript.getMyManuscripts().size());
		assertEquals("Reviewer not assigned to expected Manuscript!", manuscriptStartingWithNoReviews, 
				reviewerNotAssignedToManuscript.getMyManuscripts().get(0));
	}
	
	/* Successfully assign first paper to Reviewer, where that paper currently has a review.
	 */
	@Test
	public void testAssignFirstPaperWithOtherReviewToReviewer() {
		assertEquals("Reviewer did not start unassigned to any Manuscripts!", 0, 
				reviewerNotAssignedToManuscript.getMyManuscripts().size());
		
		reviewerNotAssignedToManuscript.assignReview(manuscriptStartingWithOtherReview);
		
		assertEquals("Reviewer did not finish assigned to 1 Manuscript!", 1, 
				reviewerNotAssignedToManuscript.getMyManuscripts().size());
		assertEquals("Reviewer not assigned to expected Manuscript!", manuscriptStartingWithOtherReview, 
				reviewerNotAssignedToManuscript.getMyManuscripts().get(0));
	}
	
	/* Successfully assign a paper with no reviews to a Reviewer already assigned to other 
	 * papers.
	 */
	@Test
	public void testAssignSecondPaperWithNoReviewsToReviewer() {
		assertEquals("Reviewer did not start assigned to 1 Manuscript!", 1, 
				reviewerAssignedToSingleManuscript.getMyManuscripts().size());
		
		reviewerAssignedToSingleManuscript.assignReview(otherManuscriptStartingWithNoReviews);
		
		assertEquals("Reviewer did not finish assigned to 2 Manuscripts!", 2, 
				reviewerAssignedToSingleManuscript.getMyManuscripts().size());
		assertTrue("Reviewer not assigned to expected Manuscript!",
		reviewerAssignedToSingleManuscript.getMyManuscripts().contains(otherManuscriptStartingWithNoReviews));
	}
	
	/* Successfully assign a paper with other review to a Reviewer already assigned to other 
	 * papers.
	 */
	@Test
	public void testAssignSecondPaperWithOtherReviewToReviewer() {
		assertEquals("Reviewer did not start assigned to 1 Manuscript!", 1, 
				reviewerAssignedToSingleManuscript.getMyManuscripts().size());
		
		reviewerAssignedToSingleManuscript.assignReview(manuscriptStartingWithOtherReview);
		
		assertEquals("Reviewer did not finish assigned to 2 Manuscripts!", 2, 
				reviewerAssignedToSingleManuscript.getMyManuscripts().size());
		assertTrue("Reviewer not assigned to expected Manuscript!",
		reviewerAssignedToSingleManuscript.getMyManuscripts().contains(manuscriptStartingWithOtherReview));
	}
	
	/* Unsuccessful: assign more than the max number of papers to a Reviewer
	 */
	@Test
	public void testAssignMoreThanMaxNumberOfPapersToReviewer() {
		assertEquals("Reviewer did not start assigned to max number of Manuscripts!", 
			Reviewer.MAX_MANUSCRIPTS, reviewerAssignedToMaxNumberManuscripts.getMyManuscripts().size());
		try {
			reviewerAssignedToMaxNumberManuscripts.assignReview(manuscriptStartingWithNoReviews);
			fail("Exception for assigning more than max number of manuscripts to reviewer wasn't thrown!");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful: null manuscript passed to assignReview
	 */
	@Test
	public void testAssignReviewNullManuscriptException() {
		try {
			reviewerAssignedToManuscript.assignReview(null);
			fail("Null Manuscript in assignReview didn't throw exception.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful: manuscript already assigned to this reviewer
	 */
	@Test
	public void testAssignReviewAlreadyAssignedException() {
		try {
			reviewerAssignedToManuscript.assignReview(manuscriptStartingWithNoReviews);
			fail("Already assigned manuscript exception not thrown.");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful: manuscript was authored by this reviewer -- Bus. Rule
	 */
	@Test
	public void testAssignReviewAuthoredByReviewerException() {
		try {
			reviewerAssignedToManuscript.assignReview(manuscriptThatIsAuthoredByAReviewer);
			fail("Already assigned manuscript exception not thrown.");
		} catch (IllegalArgumentException theException) {	}
	}
	// partitions for getMyReviewedManuscripts():
	
	// no manuscripts assigned
	@Test
	public void testGetMyReviewedManuscriptsNoneAssigned() {
		assertTrue("No reviews submitted by list was nonempty", 
				reviewerNotAssignedToManuscript.getMyReviewedManuscripts().isEmpty());
	}
	
	// 1 assigned, 0 reviewed
	@Test
	public void testGetMyReviewedManuscriptsOneAssignedNoneReviewed() {
		assertTrue("No reviews submitted but list was nonempty",
				reviewerAssignedToSingleManuscript.getMyReviewedManuscripts().isEmpty());
	}
	
	// 1 assigned, 1 reviewed
	@Test
	public void testGetMyReviewedManscriptsOneAssignedOneReviewed() {
		reviewerAssignedToSingleManuscript.submitReview(manuscriptStartingWithNoReviews, 
				review1FilePath);
		assertEquals("getMyReviewedManuscripts size is not 1", 1, 
				reviewerAssignedToSingleManuscript.getMyReviewedManuscripts().size());
		assertEquals("Manuscript did not match one reviewed", manuscriptStartingWithNoReviews, 
				reviewerAssignedToSingleManuscript.getMyReviewedManuscripts().get(0));
	}
	
	// 4 assigned, 2 reviewed
	@Test 
	public void testGetMyReviewedManuscriptsTwoAssignedOneReviewed() {
		List<Manuscript> myReviewedManuscripts = 
						reviewerAssignedToManuscript.getMyReviewedManuscripts();
		assertEquals("getMyReviewedManuscripts size is not 2", 2, myReviewedManuscripts.size());
		assertTrue("Manuscript did not match one reviewed only by me", 
				myReviewedManuscripts.contains(manuscriptStartingWithMyReview));
		assertTrue("Manuscript did not match one reviewed by me and 1 one other",
				myReviewedManuscripts.contains(manuscriptStartingWithBothReviews));
	}
	// partitions for isManuscriptsAuthorTheSameAsMyUserName():
	/*
	 * Successful: manuscript was authored by this reviewer -- Bus. Rule
	 */
	@Test
	public void testIsManuscriptsAuthorTheSameAsMyUserNameWhereTheGivenManuscriptIsAuthoredByTheReviewer(){
		assertTrue(reviewerNotAssignedToManuscript.isAuthorOf(manuscriptThatIsAuthoredByAReviewer));
	}
	/*
	 * Successful: manuscript was not authored by this reviewer -- Bus. Rule
	 */
	@Test
	public void testIsManuscriptsAuthorTheSameAsMyUserNameWhereTheGivenManuscriptIsNotAuthoredByTheReviewer(){
		assertFalse(reviewerNotAssignedToManuscript.isAuthorOf(manuscriptThatIsNotAuthoredByAReviewer));
	}
	/* Unsuccessful: manuscript is null this method throws an IllegalArgumentException
	 */
	@Test
	public void testIsManuscriptsAuthorTheSameAsMyUserNameWhereTheGivenManuscriptIsNullException(){
		try {
			reviewerNotAssignedToManuscript.isAuthorOf(null);
			fail("Null manuscript did not throw exception in isManuscriptsAuthorTheSameAsMyUserName.");
		} catch(IllegalArgumentException theError) {
			
		}
		
	}
	
	// partitions for getMyReview():
	
	/* Reviewer is assigned to the manuscript, but has not submitted a review for this manuscript, 
	 * so getMyReview() must return null.
	 */
	@Test
	public void getMyReviewNullForNoSubmittedReview() {
		assertEquals("getMyReview did not return Null for Reviewer with no Reviews submitted!",
			null, reviewerAssignedToManuscript.getMyReview(manuscriptStartingWithNoReviews));
	}
	
	/* Reviewer has submitted a review for this manuscript, so getMyReview must return their 
	 * review.
	 */
	@Test
	public void getMyReviewForSubmittedReview() {
		assertEquals("getMyReview did not return my Review on manuscript with my Review submitted!",
			expectedReview1, reviewerAssignedToManuscript.getMyReview(manuscriptStartingWithMyReview));
	}
	
	/* Unsuccessful: Reviewer not assigned to this Manuscript, must throw an 
	 * IllegalArgumentException.
	 */
	@Test
	public void getMyReviewForManuscriptNotAssignedException() {
		try {
			reviewerAssignedToManuscript.getMyReview(otherManuscriptStartingWithNoReviews);
			fail("Exception for not being assigned to Manuscript not thrown!");
		} catch (IllegalArgumentException theException) {	}
	}
	
	/* Unsuccessful: null Manuscript passed as argument must throw IllegalArgumentException
	 */
	@Test
	public void testGetMyReviewForNullManuscriptException() {
		try {
			reviewerAssignedToManuscript.getMyReview(null);
			fail("Null manuscript did not throw exception in getMyReview.");
		} catch (IllegalArgumentException theException) {	}
	}

}