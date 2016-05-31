package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Recommendation;

/**
 * Tests for the Recommendation class (model)
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class RecommendationTest {
	
	private String subprogramChairID = "Name of SubprogramChair";
	private String manuscriptTitle = "Title of Manuscript";
	private String recommendationFilePath = "Recommendation.txt";
	
	@Test
	public void testConstructorNullReviewerIDException() {
		try {
			new Recommendation(null, manuscriptTitle, recommendationFilePath);
			fail("null Reviewer ID in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testConstructorNullTitleException() {
		try {
			new Recommendation(subprogramChairID, null, recommendationFilePath);
			fail("null manuscript title in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testConstructorNullFilePathException() {
		try {
			new Recommendation(subprogramChairID, manuscriptTitle, null);
			fail("null file path in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
}

