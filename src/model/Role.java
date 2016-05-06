package model;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Justin A. Clark
 * @version 1.0
 */
public class Role {
	
	/** String to hold the current role of the user. */
	private String myRoleName;
	
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

}
