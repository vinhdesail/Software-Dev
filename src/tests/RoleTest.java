/*
 * TCSS Group 4
 * Software Development 
 */
package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Date;
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
		
		myConference = new Conference("Conference ID", "Program Chair ID", new Date(2016, 11, 5), new Date(2016, 7, 5), 
				new Date(2016, 8, 5), new Date(2016, 9, 5), new Date(2016, 10, 5));
		myRole = new Role(myRoleName, myUsername, myConference);
		
	}

	@Test	
	public void testDefaultCon() {
		Role temp = new Role();
		Date tempDate = new Date(2016, 10, 18);
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
