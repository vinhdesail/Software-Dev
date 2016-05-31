package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Class that represents a user in the system.
 * 
 * @author Justin A. Clark
 * @author Vinh Vien
 * @version 1.0
 */
public class User implements Serializable {
	
	/**
	 * Serial Number.  
	 */
	private static final long serialVersionUID = -1010666887890962915L;
	
	private static final int EMPTY = 0;
	
	private static final int FIRST_INDEX = 0;
	
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
		Author toAuthorAdd = null;
		for(Role iterRole : myRole){
			if(iterRole.getRole().equals("Author") && iterRole.getConference().equals(myConference)){
				isAuthor = true;
				toAuthorAdd = (Author) iterRole;
			}
		}
		if(!isAuthor){
			final Author toAdd = new Author(myName, myConference);
			myRole.add(toAdd);
			toAdd.addManuscript(theMasterList, theManu);
		} else {
			toAuthorAdd.addManuscript(theMasterList, theManu);
		}	
	}
	
	/**
	 * Set the default role for this conference.
	 * Priority Author > Others.
	 */
	public void autoSetRole(){
		if(myRole.isEmpty()){
			myCurrentRole = null;
		} else {
			for(Role iterRole : myRole){
				if(iterRole.getRole().equals("Author") && iterRole.getConference().equals(myConference)){
					myCurrentRole = iterRole;
				}
			}
			// IF author not found just pick the first role.
			if(myCurrentRole == null){
				myCurrentRole = myRole.get(FIRST_INDEX);
			}
		}
	}
	
	/**
	 * Check if they have a role.
	 * @return boolean If they have roles.
	 */
	public boolean hasRole(){
		return myRole.size() != EMPTY;
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