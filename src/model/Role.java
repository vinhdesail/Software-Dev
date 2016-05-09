package model;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Justin A. Clark
 * @author Joshua Meigs
 * @version 1.1
 */
public class Role {
	
	/** String to hold the current role of the user. */
	private String myRoleName;
	private String myUsername;
	public Role() {
	    myRoleName = "User";	
	}
	public Role(String roleType, String username) {
		myRoleName =  roleType;
		myUsername = username;
	}
	public String getMyUsername() {
		return myUsername;
	}
	public String toString() {
		return myRoleName;
	}
}
