package model;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Joshua Meigs
 * @version 1.1
 */
public class Role {
	
	/** String to hold the current role of the user. */
	private String myRoleName;
	
	public Role() {
	    myRoleName = "User";	
	}
	
	/**
	 * Method to log out of the current role.
	 */
	public static void logOut() {
		
	}
	
	/**
	 * The method to get the role name.
	 * @return String The role type.
	 */
	public String getRole(){
		return myRoleName;
	}

	public Role(String roleType) {
		myRoleName =  roleType;
	}
	
	public String toString() {
		return myRoleName;
	}
	
}
