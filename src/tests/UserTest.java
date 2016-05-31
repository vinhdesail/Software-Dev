package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Manuscript;
import model.User;

/**
 * Tests for the User class in model.
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class UserTest {
	
	User user1;
	String userName1 = "User 1";
	
	String conferenceID = "Name of Conference";
	
	Manuscript manuscript1;
	String title1 = "Title 1";
	String filePath1 = "Manuscript.txt";
	
	List<Manuscript> masterList = new ArrayList<Manuscript>();
	
	@Before
	public void setup() {
		user1 = new User(userName1);
		manuscript1 = new Manuscript(userName1, conferenceID, title1, filePath1);
	}
	
	@Test
	public void constructorNullUserNameExceptionTest() {
		try {
			new User(null);
			fail("null User Name in constructor did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void submitManuscriptNullManuscriptExceptionTest() {
		try {
			user1.submitManuscript(null, masterList);
			fail("null Manuscript in submitManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void submitManuscriptNullMasterListExceptionTest() {
		try {
			user1.submitManuscript(manuscript1, null);
			fail("null Master List in submitManuscript did not throw exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	@Test
	public void submitManuscriptNullConferenceExceptionTest() {
		try {
			user1.submitManuscript(manuscript1, masterList);
			fail("null conference in submitManuscript did not throw exception");
		} catch (IllegalStateException theException) {	}
	}
	
	// for submit Manuscript, need to test at least 1 where User is not an Author
	// and 1 where User is an Author
	
	// for autoSetRole need to test:
	// myRoles is empty
	// they are an author
	// they are not an author
	
	@Test
	public void addRoleNullRoleExceptionTest() {
		try {
			user1.addRole(null);
			fail("null Role in addRole did not throw an exception");
		} catch (IllegalArgumentException theException) {	}
	}
	
	// switch role:
	// null Role exception
	// not available Role exception
	// success
	
	// getMyConferenceRoles
	
	//switch conference:
	// null Conference exception
	// success
	
	// return to noRole success

}
