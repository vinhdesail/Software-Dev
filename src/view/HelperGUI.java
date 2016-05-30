/* TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.io.Console;
import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Manuscript;
import model.Review;
import model.Role;
import model.User;

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
	 * The method to display a list of manuscript.
	 * @param theList The List.
	 * @param theDisplayBack See if you want to display the back option.
	 */
	public static void displayManuscripts(List<Manuscript> theList, boolean theDisplayBack){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\n---Manuscripts---\n");
		for(int i = 0; i < theList.size(); i++){
			toDisplay.append((i + 1) + ". " + theList.get(i).getTitle());
			toDisplay.append("\n");
		}
		toDisplay.append("--end of manuscript list--\n");
		if(theDisplayBack){
			toDisplay.append("0. Back");
		}
		System.out.println(toDisplay.toString());
	}
	
	/**
<<<<<<< HEAD
=======
	 * The method to display a list of review for manuscripts.
	 * @param theList The List.
	 * @param theDisplayBack See if you want to display the back option.
	 */
	public static void displayReviews(List<Review> theList, boolean theDisplayBack){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\n---Reviews---\n");
		for(int i = 0; i < theList.size(); i++){
			toDisplay.append((i + 1) + ". " + theList.get(i).getManuscriptTitle());
			toDisplay.append("\n");
		}
		toDisplay.append("--end of reviews list--\n");
		if(theDisplayBack){
			toDisplay.append("0. Back");
		}
		System.out.println(toDisplay.toString());
	}
	
	/**
>>>>>>> refs/heads/Vinh
	 * {@inheritDoc}
	 * The toString method to return the formatted string.
	 */
	@Override
	public String toString(){
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("\n----------INFO---------\n");
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
	
	/**
	 * The method to select Role.
	 * @param theConsole The console input.
	 * @param theUser The user pass in.
	 */
	public void selectRole(final Scanner theConsole, final User theUser){
		
		Role toReturn = null;
		boolean validRole = false;
		StringBuilder tempString = new StringBuilder();
		List<Role> tempRole = theUser.getAllRoles();
		
		// IF USER ACTUALLY HAVE ROLE
		if(theUser.hasRole()){
			tempString.append("\n--------Showing all Role for this Conference--------\n");
			for(int i = 0; i < tempRole.size(); i++){
				tempString.append(i + 1);
				tempString.append(". ");
				tempString.append(tempRole.get(i).getRole());
				tempString.append("\n");
			}
			tempString.append("--------End of List--------\n");
			
			// ADD IN BACK OPTION
			tempString.append("0");
			tempString.append(". BACK/EXIT\n");
			
			// Print input and try to get selected option
			while(!validRole){
				System.out.println(tempString);
				int selected = getSelect(theConsole);
				
				if(selected == 0){
					validRole = true;
				} else {
					try{
						toReturn = tempRole.get(selected - 1);
						theUser.switchRole(toReturn);
						validRole = true;
						System.out.println("Role Change Successful");
					} catch (InputMismatchException e){
						System.out.println("FAILED TO SWITCH ROLES! TRY AGAIN!");
					} catch (IndexOutOfBoundsException e){
						System.out.println("DID NOT SWITCH ROLE! TYPE A NUMBER IN RANGE PLEASE!");
					}//end try
				}//end if
			}//end while
		} else {
			System.out.println("You have no available roles, please submit a paper to become an Author.");
		}
		//return toReturn;
	}
	
	/**
	 * Allows any user to submit a manuscript.
	 * 
	 */
	public static void submitManuscript(Scanner theConsole, User theUser, List<Manuscript> theMasterList){
		String manuscriptFile;
		int select = 0;
		boolean quit = false;
		do {
			theConsole.nextLine();
			System.out.println("Please enter the File Path for the Manuscript (Type \"EXIT\" to Exit)");
			manuscriptFile = theConsole.nextLine();		
			
			if(manuscriptFile.equalsIgnoreCase("EXIT")){
				quit = true;
			} else {
				System.out.println("The Filed you entered is: " + manuscriptFile + "\nIs This correct? Press 1 for yes, or any integer to try again");
				select = getSelect(theConsole);	
			}
		} while (select != 1 && !quit);			
		
		if(!quit){
			// ASK FOR TITLE
			theConsole.nextLine();
			System.out.println("Please enter the Title of this Manuscript");
			String manuscriptName = theConsole.nextLine();				
			
			if(!manuscriptName.equalsIgnoreCase("Exit")){
				//File file = new File(manuscriptFile);				
				Manuscript newManu = new Manuscript(theUser.getName(), theUser.getConference().getConferenceID(), manuscriptName, manuscriptFile);
				theUser.submitManuscript(newManu, theMasterList);
				System.out.println("!!!SUCCESS!!!");
				System.out.println("Manuscript: " + manuscriptName + " Submited to conference: " + theUser.getConference().getConferenceID() + " !\n");
			}
		}
	}
	
	
}

