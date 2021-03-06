package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a user in the system.
 * 
 * @author Justin A. Clark
 * @author Vinh Vien
 * @author Edie Megan Campbell
 * @version 2016.05.31
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = -1010666887890962915L;

	/* The unique username identifying this User.*/
	private final String myName;
	
	/* The list of Roles to this User.*/
	private final List<Role> myRoles;
	
	/* The Role currently in use by this User.*/
	private Role myCurrentRole; 
	
	/* The Conference selected by the User.*/
	private Conference myConference;

	/**
	 * Default constructor with no parameters
	 */
	public User(){
		myName = "Default Name";
		myRoles = new ArrayList<>();
		myCurrentRole = null;
		myConference = null;
	}
	
	/**
	 * Constructs a User with the provided username String.
	 * @param theName - the User's unique identifying username
	 * @throws IllegalArgumentException if theName is null
	 */
	public User(String theName){
		if (Objects.isNull(theName)) {
			throw new IllegalArgumentException("User name cannot be null.");
		}
		myName = theName;
		myRoles = new ArrayList<>();
		myCurrentRole = null;
		myConference = null;
	}
	
	/**
	 * Getter method for the unique username of this User
	 * @return String - the User's identifying username as a String
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * Getter method for the Conference selected by this User
	 * @return Conference - the Conference this User is currently participating in
	 */
	public Conference getConference() {
		return myConference;
	}
	
	/**
	 * Method that allows a user to submit a Manuscript to the master Manuscript list. The User
	 *  is then "promoted" to the role of Author for myConference if they were not already one.
	 * @param theManu Manuscript this User is submitting
	 * @param theMasterList -  the list of all Manuscripts submitted to any Conference
	 * @throws IllegalArgumentException if theManu or theMasterList is null
	 * @throws IllegalStateException if this User's Conference is null (if no Conference has 
	 * been selected)
	 */
	public void submitManuscript(Manuscript theManu, List<Manuscript> theMasterList) {
		if (Objects.isNull(theManu)) {
			throw new IllegalArgumentException("Manuscript cannot be null.");
		} else if (Objects.isNull(theMasterList)) {
			throw new IllegalArgumentException("Master list of Manuscripts cannot be null.");
		} else if (Objects.isNull(myConference)) {
			throw new IllegalStateException("No conference has been selected.");
		}
		
		// find out if this User is already an Author for myConference
		boolean isAuthor = false;
		Author userAsAuthor = null;
		for (Role role : myRoles){
			if (role.getRole().equals("Author") && role.getConference().equals(myConference)){
				isAuthor = true;
				userAsAuthor = (Author) role;
			}
		}
		// if not an Author, create a new Author to associate with this User
		if(!isAuthor){
			final Author newAuthor = new Author(myName, myConference);
			myRoles.add(newAuthor);
			// Author is expected to test the exceptions thus Client (User class) does not need to check these
			try{
				newAuthor.addManuscript(theMasterList, theManu);
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		} else {
			// else, simply add the Manuscript using the already existing Author object
			try{
				userAsAuthor.addManuscript(theMasterList, theManu);
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}	
	}
	
	/**
	 * Sets the default role for this User in their selected Conference according to the 
	 * priority of the roles available to this User. Author is the highest priority.
	 */
	public void autoSetRole(){
		if (myRoles.isEmpty()) {
			myCurrentRole = null;
		} else {
			boolean found = false;
			for(Role role : myRoles){
				if (role.getRole().equals("Author") && role.getConference().equals(myConference)){
					myCurrentRole = role;
					found = true;
				}
			}
			// if Author was not found in myRoles and myCurrentRole is null, pick the first role
			boolean changed = false;
			if(!found){
				for(int i = 0; i < myRoles.size(); i++){
					if(myRoles.get(i).getConference().equals(myConference)){
						myCurrentRole = myRoles.get(i);
						changed = true;
					}
				}
				if(!changed){
					myCurrentRole = null;
				}
			}
			// if Author was not found in myRoles and myCurrentRole is non-null, it will stay as is
		}
	}
	
	/**
	 * Verifies whether of not this user has any available Role.
	 * @return boolean true if they have available roles, false otherwise
	 */
	public boolean hasRole(){
		return !myRoles.isEmpty();
	}
	
	/**
	 * Adds a Role to the list of Roles available to this User.
	 * @param Role - the new Role available to this User
	 * @throws IllegalArgumentException if theRole is null or if this Role is already available
	 * to the User
	 */
	public void addRole(Role theRole){
		if (Objects.isNull(theRole)) {
			throw new IllegalArgumentException("Role cannot be null.");
		} else if (myRoles.contains(theRole)) {
			throw new IllegalArgumentException("Role has already been added.");
		}
		myRoles.add(theRole);
	}
	
	/**
	 * Getter method for the current Role being used by this User.
	 * @return Role - this User's current Role, or null if this User has no Roles available
	 */
	public Role getCurrentRole(){
		return myCurrentRole;
	}

	
	/**
	 * Method that returns all of this User's available Roles that are related to current 
	 * Conference. 
	 * @return List<Role> of all Roles for the user. 
	 */
	public List<Role> getMyConferenceRoles() {
		List<Role> toReturn = new ArrayList<>();
		for(int i = 0; i < myRoles.size(); i++){
			if (myRoles.get(i).getConference().equals(myConference)){
				toReturn.add(myRoles.get(i));
			}
		}
		return toReturn;
	}
	
	/**
	 * Switches this User's current Role to theRole, assuming that theRole is available
	 * to them.
	 * @param Role- the role the user wants to select as current role.
	 * @throws IllegalArgumentException if theRole is null or is not one of this User's 
	 * available Roles
	 */
	public void switchRole(Role theRole){
		if (Objects.isNull(theRole)) {
			throw new IllegalArgumentException("Role cannot be null.");
		}
		if (myRoles.contains(theRole)) {
			myCurrentRole = theRole;
		} else {
			throw new IllegalArgumentException("Role not available to this User.");
		}
	}
	
	/**
	 * Switches the current conference for this User.
	 * @param Conference theCon - the new conference to switch to
	 * @throws IllegalArgumentException if theCon is null
	 */
	public void switchConference(final Conference theCon){
		if (Objects.isNull(theCon)) {
			throw new IllegalArgumentException("Conference cannot be null.");
		}
		myConference = theCon;
	}
	
	/**
	 * {@inheritDoc}
	 * Users are identified by their unique username only
	 */
	@Override
	public boolean equals(Object theOther){
		if (!(theOther instanceof User)) {
			return false;
		}
		User otherUser = (User) theOther;
		return myName.equals(otherUser.myName) && myRoles.equals(otherUser.myRoles);
	}
	
	/**
	 * {@inheritDoc}
	 * The hashcode method for User.
	 */
	@Override
	public int hashCode(){
		return myName.hashCode();
	}
	
	/**
	 * Method to change the current role back to null.
	 */
	public void returnToNoRole() {
		myCurrentRole = null;
	}
}