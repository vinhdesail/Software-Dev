/*
 * TCSS Group 4
 * Software Development 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.Role;

/**
 * @author Vinh Vien
 *
 */
public class RoleTest {
	
	private Conference myConference;
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
		myConference = new Conference(conferenceID, "Program Chair ID", "05-11-2016", "05-07-2016", 
				"05-08-2016", "05-09-2016", "05-10-2016");
		myRole = new Role(myRoleName, myUsername, myConference);
		mySameRole = new Role(myRoleName, myUsername, myConference);
		Conference otherConference = new Conference(conferenceID2, "Program Chair ID2", "11-11-2016", "11-07-2016", 
				"11-08-2016", "11-09-2016", "11-10-2016");
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

	// must test COnstrucotr for null parameters
	@Test
	public void testConstructor(){
		assertEquals(myRole.getConference(), myConference);
		assertEquals(myRole.getMyUsername(), myUsername);
		assertEquals(myRole.getRole(), myRoleName);
	}
	
	/**
	 * Test of equals for the same true.
	 */
	@Test
	public void testEqualsForSameRole(){
		// test when valid
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
	public void testHashCode(){
		assertEquals(myRole, mySameRole);
		assertTrue(myRole.hashCode() == mySameRole.hashCode());
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a empty list.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceEmptyList(){
		assertEquals(myRole.getAllManuscriptForThisConference(myEmptyList), myEmptyList);
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a list with same conference for each manuscript.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceOneConference(){
		assertEquals(myRole.getAllManuscriptForThisConference(myOneConferenceList), myOneConferenceList);
	}
	
	/**
	 * Test the method getAllManuscriptForThisConference for a list with manuscript with difference conference.
	 */
	@Test
	public void testGetAllManuscriptForThisConferenceMultiConference(){
		assertEquals(myRole.getAllManuscriptForThisConference(myMultiConferenceList), myOneConferenceList);
	}
	
	
}
