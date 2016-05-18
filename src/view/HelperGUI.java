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
		setMyUsername("Empty Username");
		setMyRoleName("Empty Role");
		setMyConferenceName("Empty Conference");
		setMyActivity("Empty Activity");
		
	}
	
	/**
	 * The overloaded constructor for the class.
	 */
	public HelperGUI(String theUsername, String theRoleName, String theConferenceName, String theActivity){
		setMyUsername(theUsername);
		setMyRoleName(theRoleName);
		setMyConferenceName(theConferenceName);
		setMyActivity("Empty Activity");
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMyUsername() {
		return myUsername;
	}
	
	/**
	 * 
	 * @param myUsername
	 */
	public void setMyUsername(String myUsername) {
		this.myUsername = myUsername;
	}

	/**
	 * 
	 * @return
	 */
	public String getMyRoleName() {
		return myRoleName;
	}
	
	/**
	 * 
	 * @param myRoleName
	 */
	public void setMyRoleName(String myRoleName) {
		this.myRoleName = myRoleName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMyConferenceName() {
		return myConferenceName;
	}
	
	/**
	 * 
	 * @param myConferenceName
	 */
	public void setMyConferenceName(String myConferenceName) {
		this.myConferenceName = myConferenceName;
	}

	/**
	 * 
	 * @return
	 */
	public String getMyActivity() {
		return myActivity;
	}
	
	/**
	 * 
	 * @param myActivity
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
	
	
}
