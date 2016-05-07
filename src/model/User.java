package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * Default constructor
	 */
	public User(){
		myName = "Default Name";
		myRole = new ArrayList<>();
		myCurrentRole = null;
	}
	
	/**
	 * The constructor with parameters.
	 * @param theName Username also their unique id.
	 */
	public User(String theName){
		myName = theName;
		myRole = new ArrayList<>();
		myCurrentRole = null;
	}
	
	/**
	 * Method that allows a user to submit a file to a manuscript list.
	 * Then promote them to author.
	 */
	public void submitManuscript(Manuscript theManu, Map<String, List<Manuscript>> theMasterList) {
		boolean isAuthor = false;
		for(Role temp : myRole){
			if(temp.getRole().equals("Author")){
				isAuthor = true;
			}
		}
		
		if(!isAuthor){
			final Author toAdd = new Author();
			final List<Manuscript> tempList = new ArrayList<>();
			myRole.add(toAdd);
			tempList.add(theManu);
			theMasterList.put(myName, tempList);
		} else {
			final List<Manuscript> tempList = theMasterList.get(myName);
			tempList.add(theManu);
		}
		
	}
	
	/**
	 * The method that gives a the current role back.
	 * @return Role The current Role.
	 */
	public Role getCurrentRole(){
		return myCurrentRole;
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
	public boolean switchRole(int theIndex){
		
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
	
	
}
