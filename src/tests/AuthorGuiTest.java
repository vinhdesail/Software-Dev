package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Review;
import model.User;
import view.AuthorGUI;

public class AuthorGuiTest {

	private AuthorGUI myAuthorGUIForTestingAssignment;
	private AuthorGUI myAuthorGUIForTestingReviews;
	private Conference myConferenceToBeUsedForAllTests;
	private User myUserThatHasBeenAssignedAsAnAuthor;
	private User myUserToBeAssignedToBeAnAuthor;
	private Manuscript myManuscriptToUseWithAllTests;
	private Review myReviewToBeUsedWithTheTestWhereTheReviewIsLinkedWithTheMainManuscript;
	private Review myReviewToBeUsedWithTheTestWhereTheReviewIsNotLinkedWithTheMainManuscript;
	private List<Manuscript> myMasterListOfManuscriptsForAssignmentAuthorGui;
	private List<Manuscript> myMasterListOfManuscriptsForReviewerAuthorGui;
	
	@Before
	public void setUp() throws Exception {
		Calendar conferenceDate = new GregorianCalendar(2016,10,17);
		Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
		Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
		Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
		Calendar decisionDueDate = new GregorianCalendar(2016,10,1);
		
		myConferenceToBeUsedForAllTests = new Conference("Conference ID", "Program Chair ID", conferenceDate, manuscriptDueDate, 
				reviewDueDate, recommendationDueDate, decisionDueDate);
		Scanner console = new Scanner(System.in);
						
		myUserThatHasBeenAssignedAsAnAuthor = new User("UserName");	
		myUserToBeAssignedToBeAnAuthor = new User();
		myUserToBeAssignedToBeAnAuthor.switchConference(myConferenceToBeUsedForAllTests);
		myUserThatHasBeenAssignedAsAnAuthor.switchConference(myConferenceToBeUsedForAllTests);
		
		myMasterListOfManuscriptsForAssignmentAuthorGui = new ArrayList<Manuscript>();
		myMasterListOfManuscriptsForReviewerAuthorGui = new ArrayList<Manuscript>();
		
		myAuthorGUIForTestingReviews = new AuthorGUI(console, myUserThatHasBeenAssignedAsAnAuthor, myMasterListOfManuscriptsForReviewerAuthorGui, false);
		myAuthorGUIForTestingAssignment = new AuthorGUI(console, myUserToBeAssignedToBeAnAuthor,
				myMasterListOfManuscriptsForAssignmentAuthorGui, false);	
		
		myManuscriptToUseWithAllTests = new Manuscript("Jim West", myConferenceToBeUsedForAllTests.getConferenceID(), "theTitle", "theURI");
		
		myReviewToBeUsedWithTheTestWhereTheReviewIsLinkedWithTheMainManuscript = new Review("TheReviewer", myManuscriptToUseWithAllTests.getTitle(), "reviewText");
		myReviewToBeUsedWithTheTestWhereTheReviewIsNotLinkedWithTheMainManuscript = new Review("TheReviewer", "otherManuscript", "reviewText");				
		
		myUserToBeAssignedToBeAnAuthor.submitManuscript(myManuscriptToUseWithAllTests, myMasterListOfManuscriptsForAssignmentAuthorGui);
		myAuthorGUIForTestingAssignment.assignRoleToAuthor(myUserToBeAssignedToBeAnAuthor);
		
		myUserThatHasBeenAssignedAsAnAuthor.submitManuscript(myManuscriptToUseWithAllTests, myMasterListOfManuscriptsForReviewerAuthorGui);
		myAuthorGUIForTestingReviews.assignRoleToAuthor(myUserThatHasBeenAssignedAsAnAuthor);
		
		myManuscriptToUseWithAllTests.addReview(myReviewToBeUsedWithTheTestWhereTheReviewIsLinkedWithTheMainManuscript);
	}

	@Test
	public void assignRoleToAuthorTest() {
		assertTrue(myUserToBeAssignedToBeAnAuthor.getAllRoles().get(0) instanceof Author);
	}

	@Test
	public void getManuConnectedWithReviewWhereTheManuscriptIsConnectedWithTheReviewTest() {
		assertSame(myManuscriptToUseWithAllTests,myAuthorGUIForTestingReviews.getManuConnectedWithReview(myReviewToBeUsedWithTheTestWhereTheReviewIsLinkedWithTheMainManuscript));
	}
	
	@Test
	public void getManuConnectedWithReviewWhereTheManuscriptIsNotConnectedWithTheReviewTest() {
		assertEquals(null,myAuthorGUIForTestingReviews.getManuConnectedWithReview(myReviewToBeUsedWithTheTestWhereTheReviewIsNotLinkedWithTheMainManuscript));
	}
}
