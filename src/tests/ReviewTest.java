package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Review;

/**
 * Tests for the Review class (model)
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class ReviewTest {
	
	private String reviewerID = "Name of Reviewer";
	private String manuscriptTitle = "Title of Manuscript";
	private String reviewFilePath = "Review.txt";
	
	@Test
	public void testConstructorNullReviewerIDException() {
		try {
			new Review(null, manuscriptTitle, reviewFilePath);
			fail("null Reviewer ID in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testConstructorNullTitleException() {
		try {
			new Review(reviewerID, null, reviewFilePath);
			fail("null manuscript title in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testConstructorNullFilePathException() {
		try {
			new Review(reviewerID, manuscriptTitle, null);
			fail("null file path in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
}
