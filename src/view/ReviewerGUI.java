/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.List;
import java.util.Scanner;

import model.Author;
import model.Manuscript;
import model.Reviewer;
import model.User;

/**
 * The gui helper for Reviewer.
 * @author Vinh Vien
 *
 */
public class ReviewerGUI {
	
	/** The main console */
	private Scanner myConsole;
	
	/** The user current selected */
	private User myUser;
	
	/** The role. */
	private Reviewer myRole;
	
	/** The Master List. */
	private final List<Manuscript> myMasterList;
	
	/** The helper GUI */
	private final HelperGUI myHelper;
	
	/**
	 * The gui for Reviewer.
	 * @param theConsole
	 * @param theUser
	 * @param theMasterList
	 */
	public ReviewerGUI(Scanner theConsole, User theUser, List<Manuscript> theMasterList){
		if(theConsole == null || theUser == null || theMasterList == null){
			throw new IllegalArgumentException("Cannot accept null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myMasterList = theMasterList;
		if(myUser.getCurrentRole() instanceof Reviewer){
			myRole = (Reviewer)myUser.getCurrentRole();
		}
		
	}
	
}
