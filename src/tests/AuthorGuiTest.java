package tests;

import static org.junit.Assert.*;

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
import model.Role;
import model.User;
import view.AuthorGUI;

public class AuthorGuiTest {

	AuthorGUI myAuthorGUIForTestingAssignment;
	AuthorGUI myAuthorGUIForTestingReviews;
	Conference myConference;
	User myUser;
	User myUserToBeAssignedToBeAnAuthor;
	Manuscript myManuscript;
	Review myReview;
	Review myOtherReview;
	List<Manuscript> myMasterListOfManuscriptsForAssignmentAuthorGui;
	List<Manuscript> myMasterListOfManuscriptsForReviewerAuthorGui;
	
	@Before
	public void setUp() throws Exception {
		Calendar conferenceDate = new GregorianCalendar(2016,10,17);
		Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
		Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
		Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
		Calendar decisionDueDate = new GregorianCalendar(2016,10,1);
		
		myConference = new Conference("Conference ID", "Program Chair ID", conferenceDate, manuscriptDueDate, 
				reviewDueDate, recommendationDueDate, decisionDueDate);
		Scanner console = new Scanner(System.in);
						
		myUser = new User("UserName");	
		myUserToBeAssignedToBeAnAuthor = new User();
		myUserToBeAssignedToBeAnAuthor.switchConference(myConference);
		myUser.switchConference(myConference);
		
		myMasterListOfManuscriptsForAssignmentAuthorGui = new ArrayList<Manuscript>();
		myMasterListOfManuscriptsForReviewerAuthorGui = new ArrayList<Manuscript>();
		
		myAuthorGUIForTestingReviews = new AuthorGUI(console, myUser, myMasterListOfManuscriptsForReviewerAuthorGui, false);
		myAuthorGUIForTestingAssignment = new AuthorGUI(console, myUserToBeAssignedToBeAnAuthor,
				myMasterListOfManuscriptsForAssignmentAuthorGui, false);	
		
		myManuscript = new Manuscript("Jim West", myConference.getConferenceID(), "theTitle", "theURI");
		
		myReview = new Review("TheReviewer", myManuscript.getTitle(), "reviewText");
		myOtherReview = new Review("TheReviewer", "otherManuscript", "reviewText");				
		
		myUserToBeAssignedToBeAnAuthor.submitManuscript(myManuscript, myMasterListOfManuscriptsForAssignmentAuthorGui);
		myAuthorGUIForTestingAssignment.assignRoleToAuthor(myUserToBeAssignedToBeAnAuthor);
		
		myUser.submitManuscript(myManuscript, myMasterListOfManuscriptsForReviewerAuthorGui);
		myAuthorGUIForTestingReviews.assignRoleToAuthor(myUser);
		
		myManuscript.addReview(myReview);
	}

	@Test
	public void assignRoleToAuthorTest() {
		assertTrue(myUserToBeAssignedToBeAnAuthor.getAllRoles().get(0) instanceof Author);
	}

	@Test
	public void getManuConnectedWithReviewWhereTheManuscriptIsConnectedWithTheReviewTest() {
		assertSame(myManuscript,myAuthorGUIForTestingReviews.getManuConnectedWithReview(myReview));
	}
	
	@Test
	public void getManuConnectedWithReviewWhereTheManuscriptIsNotConnectedWithTheReviewTest() {
		assertNotEquals(myManuscript,myAuthorGUIForTestingReviews.getManuConnectedWithReview(myOtherReview));
	}
}
