package model;

import java.io.Serializable;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Joshua Meigs
 * @version 1.1
 */
public class Role implements Serializable {
	
	/**
	 * Serialize number.
	 */
	private static final long serialVersionUID = 1419088120307170425L;
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
	
	/**
	 * The method to get the role name.
	 * @return String The role type.
	 */
	public String getRole(){
		return myRoleName;
	}

	public String getMyUsername() {
		return myUsername;
	}
	
	public String toString() {
		return myRoleName;
	}
	
}