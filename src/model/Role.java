package model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.crypto.Data;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Joshua Meigs, Vinh Vien
 * @version 1.1
 */
public class Role implements Serializable {
	

	/**
	 * Serialize number.
	 */
	private static final long serialVersionUID = 1419088120307170425L;
	/** String to hold the current role of the user. */
	private String myRoleName;
	
	/** The username of the role.  */
	private String myUsername;
	
	/** The conference for that role. */
	protected Conference myConference;
	

//	/**
//	 * The constructor to take roletype and username.
//	 * @param roleType The role type.
//	 * @param username The user name.
//	 * @deprecated No longer used.
//	 */
//	public Role(String roleType, String username) {
//		Date tempDate = Conference.stringToDate("18-10-2016");
//		myRoleName =  roleType;
//		myUsername = username;
//		myConference = new Conference("Default", "Default", tempDate, tempDate, tempDate, tempDate, tempDate);
//	}
	
	/**
	 * A constructor to take in conference as a field also.
	 * @param roleType The role type.
	 * @param username The username.
	 * @param theConference The conference. 
	 */
	public Role(String roleType, String username, Conference theConference) {
		myRoleName =  roleType;
		myUsername = username;
		myConference = theConference;
	}
	
	/**
	 * The method to get the role name.
	 * @return String The role type.
	 */
	public String getRole(){
		return myRoleName;
	}
	
	/**
	 * The method to get Username.
	 * @return String The username. 
	 */
	public String getMyUsername() {
		return myUsername;
	}
	
	/**
	 * The method to get role name.
	 * @return String The role name.
	 */
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("This is user: ");
		toReturn.append(myUsername);
		toReturn.append(".\n With role: ");
		toReturn.append(myRoleName);
		toReturn.append(".\n In conference: ");
		toReturn.append(myConference);
		toReturn.append(".\n");
		return toReturn.toString();
	}
	
	/**
	 * The getter for the conferences.
	 * @return Conference The conference.
	 */
	public Conference getConference(){
		return myConference;
	}
	
	/**
	 * {@inheritDoc}
	 * The equals method to compare roles.
	 */
	@Override
	public boolean equals(Object theOther) {
		
		if (!(theOther instanceof Role)) {
			return false;
		}

		Role theRole = (Role) theOther;
		
		return this.myConference.equals(theRole.myConference) && 
				this.myRoleName.equals(theRole.myRoleName) && 
				this.myUsername.equals(theRole.myUsername);
		
	}
	
	
}