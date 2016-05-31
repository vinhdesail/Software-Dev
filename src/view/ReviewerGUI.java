/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
	
	private static final int VIEW_MY_ASSIGNED_MANUSCRIPT = 1;
	private static final int SUBMIT_A_REVIEW = 2;
	private static final int LOGOUT = 0;
	private static final int SWITCH_ROLES = -1;
	private static final int SUBMIT_MANUSCRIPT_TO_MY_CURRENT_CONFERENCE = -2;
	private static final int OFFSET = 1;
	private static final int BACK = 0;
	private static final int START_POSISTION = -1;
	private static final int SELECT_MANUSCRIPT = 1;
	private static final int IS_CORRECT = 1;
	/** The helper GUI */
	private final HelperGUI myHelper;
	
	/**
	 * The gui for Reviewer.
	 * @param theConsole
	 * @param theUser
	 * @param theMasterList
	 * @throws IllegalArgumentException if null was pass in.
	 * @throws InputMismatchException if role is not currently correct.
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
		} else {
			throw new InputMismatchException();
		}
		myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Reviewer Menu");
		myHelper.parseDate(myUser.getConference().getManuscriptDueDate());
	}
	
	/**
	 * The main method for the GUI. Controls everything.
	 * @return boolean True if they want to logout.
	 */
	public boolean loop(){
		boolean switchRole = false;
		boolean logout = false;
		do{
			myHelper.setMyActivity("Reviewer Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat Do you want to do?");
			System.out.println("1. View manuscripts I am Reviewing");
			System.out.println("2. Submit A Review");
			System.out.println("0. Logout");
			System.out.println("-1. Switch Role");
			System.out.println("-2. Submit Manuscript to Conference");
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
			case VIEW_MY_ASSIGNED_MANUSCRIPT:
				optionToViewManuscriptThatAreBeingReview(true);
				break;
			case SUBMIT_A_REVIEW:
				optionToSubmitAReview();
				break;
			case LOGOUT:
				System.out.println();
				logout = true;
				break;
			case SWITCH_ROLES:
				switchRole = myHelper.selectRole(myConsole, myUser);
				break;
			case SUBMIT_MANUSCRIPT_TO_MY_CURRENT_CONFERENCE:
				HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
				break;
			}
			
		} while(!logout && !switchRole);
		
		return switchRole;
	}

	
	private void optionToViewManuscriptThatAreBeingReview(boolean theAskToView) {
		
		if(theAskToView)
			myHelper.setMyActivity("View my List of Manuscript I been assigned");
		System.out.println(myHelper);
		
		List<Manuscript> listOfManu = myRole.getMyManuscripts();
		List<Manuscript> completed = myRole.getMyReviewedManuscripts();
		
		displayManuscriptWithStatusOfReview(listOfManu, completed, theAskToView);
		
	}
	
	private void displayManuscriptWithStatusOfReview(List<Manuscript> theMainList, List<Manuscript> theCompletedList, boolean theAskToView){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\n---Manuscripts---\n");
		String add = String.format(HelperGUI.FORMAT_TABLE, "Manuscript Name", "Completed");
		toDisplay.append("   ");
		toDisplay.append(add);
		toDisplay.append('\n');
		for(int i = 0; i < theMainList.size(); i++){
			toDisplay.append((i + OFFSET) + ". ");
			if(theCompletedList.contains(theMainList.get(i))){
				String toAppend = String.format(HelperGUI.FORMAT_TABLE, theMainList.get(i).getTitle(), "-True");
				toDisplay.append(toAppend);
			} else {
				String toAppend = String.format(HelperGUI.FORMAT_TABLE, theMainList.get(i).getTitle(), "-False");
				toDisplay.append(toAppend);
			}
			toDisplay.append("\n");
		}
		toDisplay.append("--end of reviews list--\n");
		toDisplay.append("0. Back");
		System.out.println(toDisplay.toString());
		
		if(theAskToView){
			selectManuscriptToView(theMainList);
		}
	}
	
	private void selectManuscriptToView(List<Manuscript> theManuscripts){
		
		System.out.println("Pick a manuscript to View");
		int manuscriptPick = HelperGUI.getSelect(myConsole);
		if(manuscriptPick == BACK){
			System.out.println(HelperGUI.BACK);
		} else {
			System.out.println(theManuscripts.get(manuscriptPick - OFFSET).toString());
		}
	}
	
	
	private void optionToSubmitAReview() {
		myHelper.setMyActivity("Submit a Review");
		System.out.println(myHelper);
		
		int select = START_POSISTION;
		boolean quit = false;
		String reviewURI = "";
		int recheck = START_POSISTION; 
		int selectedManuscript = START_POSISTION;
		
		List<Manuscript> listOfManu = myRole.getMyManuscripts();
		do{
			System.out.println("Pick a Manuscript to review");
			optionToViewManuscriptThatAreBeingReview(false);
		
			System.out.println("Please Pick a Manuscript");
			selectedManuscript = HelperGUI.getSelect(myConsole);
			
			if(selectedManuscript == BACK){
				quit = true;
			} else {
				
				System.out.println("Are you sure you want this manuscript(1 for Yes, any integer for no)? \n" + listOfManu.get(selectedManuscript - OFFSET));
				recheck = HelperGUI.getSelect(myConsole);
			}
		}while (recheck != SELECT_MANUSCRIPT && !quit);
		
		while (select != IS_CORRECT && !quit) {
			myConsole.nextLine();
			System.out.println("Please enter the File Path for the Review (Type \"EXIT\" to Exit)");
			reviewURI = myConsole.nextLine();		
			
			if(reviewURI.equalsIgnoreCase("EXIT")){
				quit = true;
			} else {
				System.out.println("The Filed you entered is: " + reviewURI + "\nIs This correct? Press 1 for yes, or any integer to try again");
				select = HelperGUI.getSelect(myConsole);	
			}
		}	
		
		if(!quit){
			myRole.submitReview(listOfManu.get(selectedManuscript - OFFSET), reviewURI);
		}
		
	}
	
}
