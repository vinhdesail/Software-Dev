/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Author;
import model.Manuscript;
import model.ProgramChair;
import model.User;

/**
 * 
 * @author Vinh Vien
 * @version 1.0
 */
public class AuthorGUI {
	
	/** The main console */
	private Scanner myConsole;
	
	/** The user current selected */
	private User myUser;
	
	/** The role. */
	private Author myRole;
	
	/** The Master List. */
	private final List<Manuscript> myMasterList;
	
	/** The helper GUI */
	private final HelperGUI myHelper;
	
	/**
	 * 
	 * @param theConsole The console.
	 * @param theUser User that is using the program.
	 * @param theListOfUser The List of all users.
	 * @param theMasterList The Master List of manuscript
	 */
	public AuthorGUI(Scanner theConsole, User theUser, List<Manuscript> theMasterList){
		if(theConsole == null || theUser == null || theMasterList == null){
			throw new IllegalArgumentException("Cannot accept null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myMasterList = theMasterList;
		if(!(myUser.getCurrentRole() instanceof Author)){
			throw new InputMismatchException();
		}
		myRole = (Author)myUser.getCurrentRole();
		myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Author Menu");
	}
	
	/**
	 * The main method for the GUI. Controls everything.
	 * @return boolean True if they want to logout.
	 */
	public boolean loop(){
		boolean logout = false;
		
		do{
			myHelper.setMyActivity("Author Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat Do you want to do?");
			System.out.println("1. View my manuscipt");
			System.out.println("2. Submit A Manuscript");
			System.out.println("3. Unsubmit A Manuscript");
			System.out.println("4. Edit a Manuscript");
			System.out.println("5. View All my Reviews");
			System.out.println("0. Logout");
		
		} while(!logout);
		return logout;
	}
	
}
