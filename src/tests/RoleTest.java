/*
 * TCSS Group 4
 * Software Development 
 */
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
import model.Manuscript;
import model.Role;

/**
 * A Test Class for the Role Class.
 * @author Vinh Vien
 * @version 2016.5.31
 */
public class RoleTest {
	
	private Conference myConference;
	Calendar conferenceDate = new GregorianCalendar(2016,10,17);
	Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
	Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
	Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
	Calendar decisionDueDate = new GregorianCalendar(2016,10,1);	
	private String programChairID = "Program Chair ID";
	private Role myRole;
	private Role mySameRole;
	private Role myOtherRole;
	private List<Manuscript> myEmptyList;
	private List<Manuscript> myOneConferenceList;
	private List<Manuscript> myMultiConferenceList;
	private static final String myUsername = "Alice";
	private static final String myRoleName = "Author";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		String conferenceID = "Conference ID";
		String conferenceID2 = "Conference ID2";
		myConference = new Conference(conferenceID, programChairID, conferenceDate, manuscriptDueDate, 
				reviewDueDate, recommendationDueDate, decisionDueDate);
		myRole = new Role(myRoleName, myUsername, myConference);
		mySameRole = new Role(myRoleName, myUsername, myConference);
		Conference otherConference = new Conference(conferenceID2, programChairID, conferenceDate, manuscriptDueDate, 
				reviewDueDate, recommendationDueDate, decisionDueDate);
		myOtherRole = new Role(myRoleName, "Oscar", otherConference);
		
		myEmptyList = new ArrayList<>();
		
		myOneConferenceList = new ArrayList<>();
		Manuscript manu1 = new Manuscript("Alice", conferenceID, "Title", "Source");
		Manuscript manu2 = new Manuscript("Bob", conferenceID, "Title2", "Source2");
		myOneConferenceList.add(manu1);
		myOneConferenceList.add(manu2);
		
		Manuscript manu3 = new Manuscript("Oscar", conferenceID2, "Title3", "Source3");
		myMultiConferenceList = new ArrayList<>();
		myMultiConferenceList.add(manu1);
		myMultiConferenceList.add(manu2);
		myMultiConferenceList.add(manu3);
	}

	@Test
	public void testConstructor(){
		assertEquals(myRole.getConference(), myConference);
		assertEquals(myRole.getMyUsername(), myUsername);
		assertEquals(myRole.getRole(), myRoleName);
	}
	
	@Test
	public void testConstructorExceptionWhereConferenceIsNull(){
		try {
			Role nullValueRole = new Role("RoleName", "UserName", null);
			fail("Exception Not Caught");
		}catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testConstructorExceptionWhereRoleNameIsNull(){
		try {
			Role nullValueRole = new Role(null, "UserName", myConference);
			fail("Exception Not Caught");
		}catch(IllegalArgumentException theError) {
			
		}
	}
	
	@Test
	public void testConstructorExceptionWhereUserNameIsNull(){
		try {
			Role nullValueRole = new Role("RoleName", null, myConference);
			fail("Exception Not Caught");
		}catch(IllegalArgumentException theError) {
			
		}
	}
	/**
	 * Test of equals for the same true.
	 */
	@Test
	public void testEqualsForSameRole(){
		assertEquals(myRole, mySameRole);
	}

	/**
	 * The Test of equals when the wrong class is pass in.
	 */
	@Test
	public void testEqualsForDifferentClass() {
		assertFalse(myRole.equals(new StringBuilder()));
	}
	
	/**
	 * Test when not valid different kind of role.
	 */
	@Test
	public void testEqualsForDifferentObject(){
		assertFalse(myRole.equals(myOtherRole));
	}
	
	/**
	 * Test when passing null into equals.
	 */
	@Test
	public void testEqualsForNullValue(){
		assertFalse(myRole.equals(null));
	}
	
	/**
	 * Test the hashcode for the Role. If they are equal, hashcode should be as well.
	 */
	@Test
	public void testHashCode() {
		assertEquals(myRole, mySameRole);
		assertTrue(myRole.hashCode() == mySameRole.hashCode());
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a null.
	 */
	@Test
	public void testGetAllManuscriptsForThisConferenceExceptionWhereTheListOfManuscriptsIsNull(){
		try {
			assertEquals(null, myRole.getAllManuscriptsForThisConference(null));
			fail("Exception Not Caught");
		}catch(IllegalArgumentException theError) {
			
		}
	}
	/**
	 * Test the method getAllManuscriptForThisConference for a empty list.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceEmptyList(){
		assertEquals(myRole.getAllManuscriptsForThisConference(myEmptyList), myEmptyList);
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a list with same conference for each manuscript.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceOneConference(){
		assertEquals(myRole.getAllManuscriptsForThisConference(myOneConferenceList), myOneConferenceList);
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a list with manuscript with difference conference.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceMultiConference(){
		assertEquals(myRole.getAllManuscriptsForThisConference(myMultiConferenceList), myOneConferenceList);
	}
	
	
}
