package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.Review;
import model.Reviewer;

public class ReviewerTest {
	
	private Conference testCon = new Conference("Conference ID", "Program Chair's Name", "01-06-2017",
			"01-01-2017", "01-02-2017", "01-03-2017", "01-04-2017");
	
	private Reviewer reviewer1;
	private Reviewer reviewer2;
	private String reviewer1ID = "Reviewer 1's Name";
	private String reviewer2ID = "Reviewer 2's Name";
	
	private String review1Text = "I just loved it.";
	private String review2Text = "I just hated it.";
	private String review1Edit = "Acutally, I hated it.";
	private String review2Edit = "Actually, I loved it.";
	
	private Manuscript manuscript;
	private String authorID = "Author Name";
	private String conferenceID = testCon.getConferenceID();
	private String title = "Title of Manuscript";
	private String text = "Begining of Text.\nMiddle of Text.\nEnd of Text.";
	
	@Before
	public void setup() {
		
		manuscript = new Manuscript(authorID, conferenceID, title, text);
		reviewer1 = new Reviewer(reviewer1ID, testCon);
		reviewer2 = new Reviewer(reviewer2ID, testCon);
		
		reviewer1.assignReview(manuscript);
		reviewer2.assignReview(manuscript);
		
	}
	
	
	/**
	 * Tests that the submitReview method successfully adds a given Review to the official
	 * list of Reviews for the appropriate Manuscript. Tests for the case when this is the
	 * first Review being submitted and for the case when this is not the first Review being
	 * submitted.
	 */
	@Test
	public void submitReviewTest() {

		assertTrue("Manuscript didn't start with an empty Reviews list!", 
							manuscript.getReviews().isEmpty());
		
		reviewer1.submitReview(manuscript, review1Text);
		
		assertEquals("Manuscript doesn't have the 1 Review!", 1, manuscript.getReviews().size());
		
		Review review1 = manuscript.getReviews().get(0);
		
		assertEquals("Manuscript Title incorrect for R1!", title, review1.getManuscriptTitle());
		assertEquals("Reviewer1 ID incorrect!", reviewer1ID, review1.getReviewerID());
		assertEquals("Review1 text incorrect!", review1Text, review1.getReviewText());
		
		reviewer2.submitReview(manuscript, review2Text);
		
		assertEquals("Manuscript doesn't have the 2 Reviews!", 2, manuscript.getReviews().size());
		assertEquals("Order of Reviews is wrong!", review1, manuscript.getReviews().get(0));
		
		Review review2 = manuscript.getReviews().get(1);
		
		assertEquals("Manuscript Title incorrect for R2!", title, review2.getManuscriptTitle());
		assertEquals("Reviewer2 ID incorrect!", reviewer2ID, review2.getReviewerID());
		assertEquals("Review2 text incorrect!", review2Text, review2.getReviewText());
		
	}
	
	
	/**
	 * Tests that the editReview method completely replaces the old review submitted by this
	 * Reviewer for one with a new text body.
	 */
	@Test
	public void editReviewTest() {
		reviewer1.submitReview(manuscript, review1Text);
		Review originalReview = reviewer1.getMyReview(manuscript);
		reviewer2.submitReview(manuscript, review2Text);
		//reviewer1.editReview(reviewer1.getMyReview(manuscript), manuscript, review1Edit);
		assertEquals("Edit Review didn't delete old review1!", 2, manuscript.getReviews().size());
		assertEquals("Review1 text didn't get edited!!", review1Edit, 
				reviewer1.getMyReview(manuscript).getReviewText());
		assertFalse("Old review1 is still the review!", 
				originalReview.equals(reviewer1.getMyReview(manuscript)));
		
		//reviewer2.editReview(reviewer2.getMyReview(manuscript), manuscript, review2Edit);
		assertEquals("Edit Review didn't delete old review2!", 2, manuscript.getReviews().size());
		assertEquals("Review2 text didn't get edited!!", review2Edit, 
				reviewer2.getMyReview(manuscript).getReviewText());
		assertFalse("Old review2 is still the review!", 
				originalReview.equals(reviewer2.getMyReview(manuscript)));
		
		
	}

}
