/* TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.Scanner;

/**
 * Helper class to help display some common gui.
 * 
 * @author Vinh Vien
 *
 */
public class HelperGUI {
	
	/** A 'back' display to help display */
	public static final String BACK = "\n Back \n";
	
	/** The Username. */
	private String myUsername;
	
	/** The Role. */
	private String myRoleName;
	
	/** The Conference name. */
	private String myConferenceName;
	
	/** The Activity At the Time. */
	private String myActivity;
	
	/**
	 * The constructor for the class.
	 */
	public HelperGUI(){
		setMyUsername("Blank");
		setMyRoleName("Blank");
		setMyConferenceName("Blank");
		setMyActivity("Blank");
		
	}
	
	/**
	 * The overloaded constructor for the class.
	 */
	public HelperGUI(String theUsername, String theRoleName, String theConferenceName, String theActivity){
		setMyUsername(theUsername);
		setMyRoleName(theRoleName);
		setMyConferenceName(theConferenceName);
		setMyActivity(theActivity);
		
	}
	
	/**
	 * Get the user name.
	 * @return String The name.
	 */
	public String getMyUsername() {
		return myUsername;
	}
	
	/**
	 * Set the User name. 
	 * @param myUsername The Username.
	 */
	public void setMyUsername(String myUsername) {
		this.myUsername = myUsername;
	}

	/**
	 * Get the role name.
	 * @return String The name.
	 */
	public String getMyRoleName() {
		return myRoleName;
	}
	
	/**
	 * Set the Role name.
	 * @param myRoleName The Role name.
	 */
	public void setMyRoleName(String myRoleName) {
		this.myRoleName = myRoleName;
	}
	
	/**
	 * Get the conference.
	 * @return String The conference.
	 */
	public String getMyConferenceName() {
		return myConferenceName;
	}
	
	/**
	 * Set a conference. 
	 * @param myConferenceName The conference.
	 */
	public void setMyConferenceName(String myConferenceName) {
		this.myConferenceName = myConferenceName;
	}

	/**
	 * Return an activity.
	 * @return String The activity.
	 */
	public String getMyActivity() {
		return myActivity;
	}
	
	/**
	 * Set an activity.
	 * @param myActivity The current activity.
	 */
	public void setMyActivity(String myActivity) {
		this.myActivity = myActivity;
	}

	/**
	 * A method to print and scan an integer.
	 * 
	 * @param theConsole The console pass in.
	 * @return int The integer selected.
	 */
	public static int getSelect(Scanner theConsole){
		System.out.print("\nSelect: ");
		return getInt(theConsole);
	}
	
	/**
	 * Get a int from user.
	 * 
	 * @param scanner the Console scanner.
	 * @return int The integer.
	 */
	public static int getInt(Scanner theConsole){
		while(!theConsole.hasNextInt()){
			theConsole.next();
			System.out.print("Please enter an integer : ");
		} 
		return theConsole.nextInt();
	}
	
	/**
	 * {@inheritDoc}
	 * The toString method to return the formatted string.
	 */
	@Override
	public String toString(){
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("----------INFO---------\n");
		toReturn.append("User: ");
		toReturn.append(myUsername);
		toReturn.append('\n');
		toReturn.append("Conference: ");
		toReturn.append(myConferenceName);
		toReturn.append('\n');
		toReturn.append("Role: ");
		toReturn.append(myRoleName);
		toReturn.append('\n');
		toReturn.append("Current Task: ");
		toReturn.append(myActivity);
		toReturn.append('\n');
		toReturn.append("----------INFO---------\n");
		return toReturn.toString();
	}
	
	
}
