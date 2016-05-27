package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Class that represents a user in the system.
 * 
 * @author Justin A. Clark & Vinh Vien
 * @version 1.0
 */
public class User implements Serializable {
	
	/**
	 * Serial Number.  
	 */
	private static final long serialVersionUID = -1010666887890962915L;
	
	/**
	 * The username.
	 */
	private final String myName;
	
	/**
	 * The array of roles user can have.
	 */
	private final List<Role> myRole;
	
	/**
	 * The roles currently use.
	 */
	private Role myCurrentRole; 
	
	/**
	 * The conference involve in.
	 */
	private Conference myConference;

	/**
	 * Default constructor
	 */
	public User(){
		myName = "Default Name";
		myRole = new ArrayList<>();
		myCurrentRole = null;
		myConference = null;
	}
	
	/**
	 * The constructor with parameters.
	 * @param theName Username also their unique id.
	 */
	public User(String theName){
		myName = theName;
		myRole = new ArrayList<>();
		myCurrentRole = null;
		myConference = null;
	}
	
	public String getName() {
		return myName;
	}
	
	public Conference getConference() {
		return myConference;
	}
	
	/**
	 * Method that allows a user to submit a file to a manuscript list.
	 * Then promote them to author.
	 */
	public void submitManuscript(Manuscript theManu, List<Manuscript> theMasterList) {
		boolean isAuthor = false;
		for(Role temp : myRole){
			if(temp.getRole().equals("Author") && temp.getConference().equals(myConference)){
				isAuthor = true;
			}
		}
		if(!isAuthor){
			final Author toAdd = new Author(myName, myConference);
			myRole.add(toAdd);
			theMasterList.add(theManu);
		} else {
			theMasterList.add(theManu);
		}	
	}
	
	/**
	 * Check if they have a role.
	 * @return boolean If they have roles.
	 */
	public boolean hasRole(){
		return myRole.size() != 0;
	}
	
	/**
	 * Add a role.
	 * @param Role The added role.
	 */
	public void addRole(Role theRole){
		myRole.add(theRole);
	}
	
	/**
	 * The method that gives a the current role back.
	 * @return Role The current Role.
	 */
	public Role getCurrentRole(){
		return myCurrentRole;
	}
	
	/**
	 * Get the List all Roles.
	 * @return The list of all roles.
	 */
	public List<Role> getListOfAllRoles() {
		List<Role> allRoles = new ArrayList<>();
		for(int i  = 0; i < myRole.size();i++) {
			allRoles.add(myRole.get(i));
		}
		return allRoles;
	}
	
	/**
	 * Method that returns all roles that are related to current confernce. 
	 * @return List All roles for the user. 
	 */
	public List<Role> getAllRoles() {
		List<Role> toReturn = new ArrayList<>();
		for(int i = 0; i < myRole.size(); i++){
			if(myRole.get(i).getConference().equals(myConference)){
				toReturn.add(myRole.get(i));
			}
		}
		return toReturn;
	}
	
	/**
	 * The method accepts an index that the user can switch role too.
	 * @param Role The role the user want to select as current role.
	 * @return boolean If the role switch properly. 
	 */
	public void switchRole(Role theRole){
		boolean flag = false;
		for(int i = 0; i < myRole.size(); i++){
			if(myRole.get(i).equals(theRole)){
				flag = true;
				myCurrentRole = myRole.get(i);
			}
		}
		if(!flag){
			throw new InputMismatchException();
		}
	}
	
	/**
	 * The method to switch a conference.
	 * @param Conference The new conference to switch to.
	 */
	public void switchConference(final Conference theCon){
		myConference = theCon;
	}
	
	/**
	 * {@inheritDoc}
	 * The equals method to users.
	 */
	@Override
	public boolean equals(Object theOther){
		User theUser = (User)theOther;
		return myName.equals(theUser.myName);
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