/*
 * TCSS Group 4
 * Software Development 
 */
package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Role;

/**
 * @author Vinh Vien
 *
 */
public class RoleTest {
	
	private Conference myConference;
	private Role myRole;
	private static final String myUsername = "Alice";
	private static final String myRoleName = "Author";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		myConference = new Conference("Conference ID", "Program Chair ID", "05-11-2016", "05-07-2016", 
				"05-08-2016", "05-09-2016", "05-10-2016");
		myRole = new Role(myRoleName, myUsername, myConference);
		
	}

	
	@Test
	public void testConstructor(){
		assertEquals(myRole.getConference(), myConference);
		assertEquals(myRole.getMyUsername(), myUsername);
		assertEquals(myRole.getRole(), myRoleName);
	}
	
	@Test
	public void testEqualsForSameRole(){
		// test when valid
		assertEquals(myRole, new Role(myRoleName, myUsername, myConference));
	}

	// test when not valid (wrong class)
	@Test
	public void testEqualsForDifferentClass() {
		// need to implement
	}
	
	// test when not valid (different object)
	// test against null
}
