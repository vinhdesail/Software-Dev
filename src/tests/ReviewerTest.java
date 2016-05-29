package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
//github.com/vinhdesail/Software-Dev.git
import model.Manuscript;
import model.Review;
import model.Reviewer;

public class ReviewerTest {

	private Conference testCon;
	private String conferenceID = "Conference ID";
	private String programChairID = "Program Chair ID";
	private Reviewer reviewerAssignedToManuscript;
	private Reviewer reviewerNotAssignedToManuscript;
	private Reviewer reviewerAssignedToSingleManuscript;
	private Reviewer reviewerAssignedToMaxNumberManuscripts;
	private Review expectedReview1;
	private Review expectedReview2;
	private Review expectedEdit1;
	
	private Reviewer reviewer1;
	private Reviewer reviewer2;
	private String reviewer1ID = "Reviewer 1's Name";
	private String reviewer2ID = "Reviewer 2's Name";
	
	private String review1Text = "I just loved it.";
	private String review2Text = "I just hated it.";
	private String review1Edit = "Acutally, I hated it.";
	private String review2Edit = "Actually, I loved it.";
	
	private Manuscript manuscriptStartingWithNoReviews;
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
		
		expectedReview1 = new Review(reviewer1ID, title, review1Text);
		expectedReview2 = new Review(reviewer2ID, title, review2Text);
		expectedEdit1 = new Review(reviewer1ID, title, review1Edit);
		
		testCon = new Conference(conferenceID, programChairID, "01-12-2016", "01-06-2016", 
									"01-08-2016", "01-09-2016", "01-10-2016");
		
		manuscriptStartingWithNoReviews = new Manuscript(authorID, conferenceID, title, textURL);
		
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
		for (int i = 0; i < Reviewer.MAX_PAPERS; i++) {
			listOfMaxNumManuscripts.add(manuscriptStartingWithNoReviews);
		}
		reviewerAssignedToMaxNumberManuscripts = new Reviewer(reviewer1ID, testCon, listOfMaxNumManuscripts);
		
		reviewerNotAssignedToManuscript = new Reviewer(reviewer1ID, testCon);
	}

	
	// Partitions for submitReview():
	
	// successful submit, first review submitted for manuscript
	@Test
	public void submitFirstReviewForManuscriptTest() {
		
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Text);
		// because manuscript started with no submitted reviews, this should be the only Review
		assertEquals("Manuscript's Review is not equal to Review submitted!",
				expectedReview1, manuscriptStartingWithNoReviews.getReviews().get(0));
		assertEquals("Manuscript's number of Reviews was not 1!", 1, 
				manuscriptStartingWithNoReviews.getReviews().size());
	}
	
	// successful submit, not the first review submitted for manuscript
	@Test
	public void submitNotFirstReviewForManuscriptTest() {
		
		assertEquals("Manuscript did not start with 1 Review!", 1, manuscriptStartingWithOtherReview.getReviews().size());
		
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithOtherReview, review1Text);
		
		assertTrue("Review submitted was not found in Manuscript's Review List!", 
				manuscriptStartingWithOtherReview.getReviews().contains(expectedReview1));
		assertEquals("Manuscript's number of Reviews was not 2!", 2, 
				manuscriptStartingWithOtherReview.getReviews().size());
		
		
	}

	// Reviewer hasn't been assigned to this Manuscript - IllegalArgumentException
	@Test
	public void submitReviewForUnassignedManuscriptExceptionTest() {
		
		try {
			   reviewerNotAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Text);
			   fail("Exception for submitting a review on a manuscript not assigned wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 		
		
		try {
			   reviewerNotAssignedToManuscript.submitReview(manuscriptStartingWithOtherReview, review1Text);
			   fail("Exception for submitting a review on a manuscript not assigned wasn't caught");			   
		} catch (IllegalArgumentException theException) {
			   
		} 
	}
	
	// Reviewer has already submitted a Review for this Manuscript - IllegalArgumentException
	@Test
	public void resubmitReviewForManuscriptExceptionTest() {
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Text);

		try {
			reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Text);
			fail("Exception for submitting a review when one has already been submitted wasn't caught");
		} catch (IllegalArgumentException theException) {
			
		}
		
	}
	
	// Reviewer has submitted a Review for this Manuscript and is attempting to edit - IllegalArgumentException
	@Test
	public void submitReviewWhenTryingToEditReviewExceptionTest() {
		reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Text);
		
		try {
			reviewerAssignedToManuscript.submitReview(manuscriptStartingWithNoReviews, review1Edit);
			fail("Exception for submitting a review when one has already been submitted wasn't caught");
		} catch (IllegalArgumentException theException) {
			
		}
	}
	
	// Partitions for editReview():
	
	// successful edit, no other Reviews for this Manuscript besides this Reviewer
	@Test
	public void editReviewForManuscriptWithNoOtherReviewsTest() {
		
		assertEquals("Manuscript did not start out with expected Review from this Reviewer", expectedReview1, 
				manuscriptStartingWithMyReview.getReviews().get(0));
		
		reviewerAssignedToManuscript.editReview(manuscriptStartingWithMyReview, review1Edit);
		assertEquals("Manuscript did not have 1 Review", 1, manuscriptStartingWithMyReview.getReviews().size());
		assertEquals("Review from this Manuscript not equal to edited Review", expectedEdit1, 
				manuscriptStartingWithMyReview.getReviews().get(0));
	}
	
	// successful edit, with other Reviews for this Manuscript besides this Reviewer
	@Test
	public void editReviewForManuscriptWithOtherReviewsTest() {
		
		assertTrue("Intially: Original Review not part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedReview1));
		assertTrue("Initially: Other Review not part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedReview2));
		
		reviewerAssignedToManuscript.editReview(manuscriptStartingWithBothReviews, review1Edit);
		assertEquals("Manuscript did not have 2 Reviews", 2, manuscriptStartingWithBothReviews.getReviews().size());
		assertTrue("Edited Review not part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedEdit1));
		assertTrue("Other Review no longer part of Manuscript's Review List", 
				manuscriptStartingWithBothReviews.getReviews().contains(expectedReview2));
	}
	
	// unsuccessful: editing a Review for a Manuscript this Reviewer has not been assigned to
	@Test
	public void editReviewForUnnassignedManuscriptExceptionTest() {
		try {
			reviewerNotAssignedToManuscript.editReview(manuscriptStartingWithMyReview, review1Edit);
			fail("Exception for editing a Review on a Manuscript not assigned to this Review not Caught");
		} catch (IllegalArgumentException theException) {
			
		}
		
	}
	
	// unsuccessful: editing a Review for a Manuscript which this Reviewer has not yet submitted a Review for
	@Test
	public void editReviewForManuscriptNotYetReviewedExceptionTest() {
		try {
			reviewerAssignedToManuscript.editReview(manuscriptStartingWithNoReviews, review1Text);
			fail("Exception for editing a Review when this Reviewer has not yet submitted one not Caught");
		} catch (IllegalArgumentException theException) {
			
		}
		
	}
	
	// Paritions for assignReview():
	// successfully assign first paper to Reviewer
	// successfully assign not-first paper to Reviewer
	// unsuccessful: assign more than the max number of papers to Reviewer

}