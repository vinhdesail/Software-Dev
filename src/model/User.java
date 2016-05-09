package model;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	/**
	 * Method that allows a user to submit a file to a manuscript list.
	 * Then promote them to author.
	 */
	public void submitManuscript(Manuscript theManu, List<Manuscript> theMasterList) {
		boolean isAuthor = false;
		for(Role temp : myRole){
			if(temp.getRole().equals("Author")){
				isAuthor = true;
			}
		}
		
		if(!isAuthor){
			final Author toAdd = new Author(myName);
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
	
	public List<Role> getListOfAllRoles() {
		List<Role> allRoles = new ArrayList<>();
		
		for(int i  = 0; i < myRole.size();i++) {
			allRoles.add(myRole.get(i));
		}
		return allRoles;
	}
	
	/**
	 * Method that allows the user to switch to another role.
	 * @return String All roles for the user. 
	 */
	public String getAllRoles() {
		final StringBuilder toReturn = new StringBuilder();
		for(int i = 0; i < myRole.size(); i++){
			toReturn.append(i + 1);
			toReturn.append(". ");
			toReturn.append(myRole.get(i).getRole());
			toReturn.append('\n');
		}
		return toReturn.toString();
	}
	
	/**
	 * The method accepts an index that the user can switch role too.
	 * @return boolean If the role switch properly. 
	 */
	public boolean switchRole(final int theIndex){
		
		boolean toReturn = false;
		try{
			myCurrentRole = myRole.get(theIndex - 1);
			toReturn = true;
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Wrong index!");
			System.out.println(e);
		}
		return toReturn;
	}
	
	/**
	 * The method to switch a conference.
	 * @param Conference The new conference to switch to.
	 */
	public void switchConference(final Conference theCon){
		myConference = theCon;
	}
	
	
	
}