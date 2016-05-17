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
	public void testDefaultCon() {
		Role temp = new Role();
		java.util.Date tempDate = Conference.stringToDate("18-10-2016");	// 18-10-2016 tempdate
		assertEquals(temp.getRole(), "User");
		assertEquals(temp.getMyUsername(), "");
		assertEquals(temp.getConference(), new Conference("Default", "Default", tempDate, tempDate, tempDate, tempDate, tempDate));
		
	}
	
	@Test
	public void testCon(){
		assertEquals(myRole.getConference(), myConference);
		assertEquals(myRole.getMyUsername(), myUsername);
		assertEquals(myRole.getRole(), myRoleName);
	}
	
	@Test
	public void testEqual(){
		assertEquals(myRole, new Role(myRoleName, myUsername, myConference));
	}

}
