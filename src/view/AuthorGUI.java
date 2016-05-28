/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Author;
import model.Manuscript;
import model.Role;
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
	
	/** */
	private boolean myIsAuthor;
	
	/**
	 * 
	 * @param theConsole The console.
	 * @param theUser User that is using the program.
	 * @param theListOfUser The List of all users.
	 * @param theMasterList The Master List of manuscript
	 */
	public AuthorGUI(Scanner theConsole, User theUser, List<Manuscript> theMasterList, boolean theIsAuthor){
		if(theConsole == null || theUser == null || theMasterList == null){
			throw new IllegalArgumentException("Cannot accept null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myMasterList = theMasterList;
		myIsAuthor = theIsAuthor;
		if(myIsAuthor){
			if(!(myUser.getCurrentRole() instanceof Author)){
				throw new InputMismatchException();
			}
			myRole = (Author)myUser.getCurrentRole();
			myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Author Menu");
		} else {
			myRole = null;
			myHelper = new HelperGUI(myUser.getName(), "Submit a paper to become an Author", myUser.getConference().getConferenceID(), "User Menu");
		}
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
			System.out.println("1. View my manuscipts");
			System.out.println("2. Submit A Manuscript");
			System.out.println("3. Unsubmit A Manuscript");
			System.out.println("4. Edit a Manuscript");
			System.out.println("5. View All my Reviews");
			System.out.println("0. Logout");
			System.out.println("-1. Switch Role");
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
			case 1:
				optionToViewYourManuscript();
				break;
			case 2:
				optionToSubmitAManuscript();
				break;
			case 3:
				optionToUnsubmitAManuscript();
				break;
			case 4:
				optionToEditAManuscript();
				break;
			case 5:
				optionToViewAllReviews();
				break;
			case 0:
				System.out.println();
				logout = true;
				break;
			case -1:
				if(myIsAuthor){
					myHelper.selectRole(myConsole, myUser);
				} else {
					System.out.println("Submit a manuscript to get a role - Author");
				}
				break;
		}
			
		} while(!logout);
		return logout;
	}
	
	/**
	 * Let user pick a manuscript and show it.
	 */
	private void optionToViewYourManuscript() {
		
		myHelper.setMyActivity("Viewing my Manuscripts");
		System.out.println(myHelper);
		
		if(myIsAuthor){
			
			List<Manuscript> listOfManuscript = myRole.showAllMyManuscript(myMasterList, myUser.getName());
			
			HelperGUI.displayManuscripts(listOfManuscript, true);
			
			int userSelect = HelperGUI.getSelect(myConsole);
	
			if(userSelect == 0){
				System.out.println(HelperGUI.BACK);
			} else {
				System.out.println(listOfManuscript.get(userSelect - 1));
			}
			
		} else {
			System.out.println("You have no manuscript");
			System.out.println("Submit a paper to become an Author");
		}
	}
	
	/**
	 * Option when to submit a manuscript.
	 */
	private void optionToSubmitAManuscript() {
		
		myHelper.setMyActivity("Submiting a Manuscript");
		System.out.println(myHelper);
		if(myIsAuthor){
			List<Manuscript> listOfManuscript = myRole.getAllManuscriptForThisConference(myMasterList);
			System.out.println("Showing all Manuscripts");
			HelperGUI.displayManuscripts(listOfManuscript, false);
			System.out.println();
			HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
		} else {
			HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
			
			assignToAuthor();
		}
		
	}
	
	/**
	 * Assign role author to a non author.
	 * Use for testing this logic.
	 */
	public void assignToAuthor(){
		
		List<Role> listOfRole = myUser.getAllRoles();
		Author toAssign = null;
		for(Role iterRole : listOfRole){
			if(iterRole instanceof Author){
				toAssign = (Author) iterRole;
			}
		}
		
		if(toAssign != null){
			myUser.switchRole(toAssign);
			myRole = toAssign;
			myHelper.setMyRoleName(myRole.getRole());
			myIsAuthor = true;
		}
	}
	
	
	private String askForTitle(){
		System.out.println("Please enter the Title of this Manuscript or \"EXIT\" to quit");
		String manuscriptName = myConsole.nextLine();				
		
		return manuscriptName;
	}
	
	private void optionToUnsubmitAManuscript() {
		// TODO Auto-generated method stub
		
	}
	
	private void optionToEditAManuscript() {
		// TODO Auto-generated method stub
		
	}
	
	private void optionToViewAllReviews() {
		// TODO Auto-generated method stub
		
	}
}
