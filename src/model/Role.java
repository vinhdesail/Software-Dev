package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a general role that a user is.
 * 
 * @author Joshua Meigs, Vinh Vien, Edie Megan Campbell
 * @version 2016.05.31
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1419088120307170425L;
	
	/* String name of this Role. */
	private String myRoleName;
	
	/* The username of the User acting as this Role.  */
	private String myUsername;
	
	/* The Conference associated with this Role. */
	private Conference myConference;

	/**
	 * @param roleName - the name of the Role being constructed as a String
	 * @param username - the username of the User acting as this Role
	 * @param theConference - the Conference associated with this Role
	 * @throws IllegalArgumentException if any of the parameters are null
	 */
	public Role(final String roleName, final String username, final Conference theConference) {
		if (Objects.isNull(roleName)) {
			throw new IllegalArgumentException("Role name cannot be null.");
		} else if (Objects.isNull(username)) {
			throw new IllegalArgumentException("User name cannot be null.");
		} else if (Objects.isNull(theConference)) {
			throw new IllegalArgumentException("Conference cannot be null.");
		}
		myRoleName =  roleName;
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
	 * The method to get all manuscript for this conference.
	 * @param theMaserList The main list.
	 */
	public List<Manuscript> getAllManuscriptForThisConference(final List<Manuscript> theMasterList){
		List<Manuscript> toReturn = new ArrayList<>();
		for(int i = 0; i < theMasterList.size(); i++){
			if(theMasterList.get(i).getConference().equals(myConference.getConferenceID())){
				toReturn.add(theMasterList.get(i));
			}
		}
		return toReturn;
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
	
	/**
	 * {@inheritDoc}
	 * The hashcode method for role.
	 */
	@Override
	public int hashCode(){
		return myRoleName.hashCode() + myUsername.hashCode() + myConference.hashCode();
	}
}