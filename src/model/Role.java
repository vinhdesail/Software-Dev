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
	public Role() {
	    myRoleName = "User";	
	}
	public Role(String roleType) {
		myRoleName =  roleType;
	}
	public String toString() {
		return myRoleName;
	}
}
