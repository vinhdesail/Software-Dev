package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.Reviewer;
import model.Role;
import model.User;

/**
 * Tests for the User class in model.
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class UserTest {
	
	private User user1;
	private String userName1 = "User 1";
	
	private String conferenceID = "Name of Conference";
	
	private Manuscript manuscript1;
	private Manuscript manuscript2;
	private String title1 = "Title 1";
	private String title2 = "Title 2";
	private String filePath1 = "Manuscript.txt";
	
	private List<Manuscript> masterList;
	private List<Role> myListOfRoles;
	private Role myReviewer;
	private Role myAuthor;
	private Role myIncorrectRole;
	
	private User userNotAnAuthor;
	private User userIsAnAuthor;
	private User userIsAnAuthorWithTwoManuscript;
	private User userWithoutConference;
	private User userWithNoRole;
	private User userWithMultipleRole;
	private Author userAuthorRole;
	private Author userAuthorRole2;
	
	private Conference myConference;
	
	@Before
	public void setup() {
		Calendar conferenceDate = new GregorianCalendar(2018,10,17);
		Calendar manuscriptDueDate = new GregorianCalendar(2018,9,1);
		Calendar reviewDueDate = new GregorianCalendar(2018,9,19);
		Calendar recommendationDueDate = new GregorianCalendar(2018,10,1);
		Calendar decisionDueDate = new GregorianCalendar(2018,10,1);	
		
		myConference = new Conference("Conference ID", "Program Chair ID", conferenceDate, manuscriptDueDate, 
				reviewDueDate, recommendationDueDate, decisionDueDate);
		
		masterList = new ArrayList<Manuscript>();
		
		user1 = new User(userName1);
		manuscript1 = new Manuscript(userName1, conferenceID, title1, filePath1);
		manuscript2 = new Manuscript(userName1, conferenceID, title2, filePath1);
		
		userNotAnAuthor = new User("User");
		userNotAnAuthor.switchConference(myConference);
		userIsAnAuthor = new User("User");
		userIsAnAuthor.switchConference(myConference);
		userAuthorRole = new Author("User", myConference);
		userAuthorRole.addManuscript(masterList, manuscript1);
		userIsAnAuthor.addRole(userAuthorRole);
		
		userWithoutConference = new User("User");
		
		userIsAnAuthorWithTwoManuscript = new User("User");
		userIsAnAuthorWithTwoManuscript.switchConference(myConference);
		userAuthorRole2 = new Author("User", myConference);
		userAuthorRole2.addManuscript(masterList, manuscript1);
		userAuthorRole2.addManuscript(masterList, manuscript2);
		userIsAnAuthorWithTwoManuscript.addRole(userAuthorRole2);
		
		userWithNoRole = new User("User");
		userWithNoRole.switchConference(myConference);
		userWithMultipleRole = new User("User");
		userWithMultipleRole.switchConference(myConference);
		myListOfRoles = new ArrayList<>();
		myReviewer = new Reviewer("User", myConference);
		myAuthor = new Author("User", myConference);
		myIncorrectRole = new Role("NotARealRole", "User", myConference);
		userWithMultipleRole.addRole(myReviewer);
		userWithMultipleRole.addRole(myAuthor);
		myListOfRoles.add(myReviewer);
		myListOfRoles.add(myAuthor);
	}
	
	@Test
	public void testConstructorNullUserNameExceptionTest() {
		try {
			new User(null);
			fail("null User Name in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testCubmitManuscriptNullManuscriptExceptionTest() {
		try {
			user1.submitManuscript(null, masterList);
			fail("null Manuscript in submitManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testSubmitManuscriptNullMasterListExceptionTest() {
		try {
			user1.submitManuscript(manuscript1, null);
			fail("null Master List in submitManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testSubmitManuscriptNullConferenceExceptionTest() {
		try {
			user1.submitManuscript(manuscript1, masterList);
			fail("null conference in submitManuscript did not throw exception");
		} catch (IllegalStateException theException) {	}
	}
	
	@Test
	public void testSubmitManuscriptUserIsNotAuthor(){
		userNotAnAuthor.submitManuscript(manuscript1, masterList);
		assertEquals(userNotAnAuthor, userIsAnAuthor);
	}
	
	@Test
	public void testSubmitManuscriptUserIsAnAuthor(){
		userIsAnAuthor.submitManuscript(manuscript2, masterList);
		assertEquals(userIsAnAuthorWithTwoManuscript, userIsAnAuthor);
	}
	
	// for autoSetRole need to test:
	// myRoles is empty
	// they are an author
	// they are not an author
	
	@Test
	public void testAddRoleNullRoleExceptionTest() {
		try {
			user1.addRole(null);
			fail("null Role in addRole did not throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	
	@Test
	public void testSwitchRoleNullException(){
		try {
			userWithMultipleRole.switchRole(null);
			fail("null Role switchRole did not throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void testSwitchRoleNotAvailableRole(){
		try {
			userWithMultipleRole.switchRole(myIncorrectRole);
			fail("null Role switchRole did not throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}

	@Test
	public void testSwitchRoleSuccess(){
		userWithMultipleRole.switchRole(myReviewer);
		assertEquals(userWithMultipleRole.getCurrentRole(), myReviewer);	
	}

	@Test
	public void testGetMyConferenceRoles(){
		assertEquals(userWithMultipleRole.getMyConferenceRoles(), myListOfRoles);
	}
	
	@Test
	public void testSwitchConferenceIsNull(){
		try{
			userIsAnAuthor.switchConference(null);
		} catch (IllegalArgumentException theException){}
	}

	@Test
	public void testSwitchConferenceIsCorrect(){
		userWithoutConference.switchConference(myConference);
		assertEquals(userWithoutConference.getConference(), userNotAnAuthor.getConference());
	}
	
	@Test
	public void testReturnToNoRoleSuccess(){
		userIsAnAuthor.autoSetRole();
		assertEquals(userIsAnAuthor.getCurrentRole(), userAuthorRole);
		userIsAnAuthor.returnToNoRole();
		assertEquals(userIsAnAuthor.getCurrentRole(), null);
	}

}
