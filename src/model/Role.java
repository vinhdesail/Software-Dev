package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Role class represents the role a User can act as for a particular Conference
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
	 * Getter method for the role name.
	 * @return String - the role name
	 */
	public String getRole(){
		return myRoleName;
	}
	
	/**
	 * Getter method for the unique username of the User acting as this Role.
	 * @return String - the username. 
	 */
	public String getMyUsername() {
		return myUsername;
	}
	
	/**
	 * Overridden toString method that formats the User's name, the name of the Role, and the
	 * Conference as a single String
	 * @return String the Role with all fields
	 */
	@Override
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
	 * Getter method for the Conference associated with this Role.
	 * @return Conference The conference.
	 */
	public Conference getConference() {
		return myConference;
	}
	
	/**
	 * Getter method for all manuscripts submitted to this Conference.
	 * @param theMasterList - List<Manuscript> with all Manuscripts submitted to any Conference
	 * @return List<Manuscript> - only those manuscripts associated with this Role's Conference
	 * @throws IllegalArgumentException if theMasterList is null
	 */
	public List<Manuscript> getAllManuscriptsForThisConference(final List<Manuscript> theMasterList) {
		if (Objects.isNull(theMasterList)) {
			throw new IllegalArgumentException("Master list of Manuscripts cannot be null.");
		}
		List<Manuscript> toReturn = new ArrayList<>();
		for (int i = 0; i < theMasterList.size(); i++){
			if (theMasterList.get(i).getConference().equals(myConference.getConferenceID())){
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