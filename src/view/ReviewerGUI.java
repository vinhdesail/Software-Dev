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
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
			case 1:
				optionToViewManuscriptThatAreBeingReview();
				break;
			case 2:
				optionToSubmitAReview();
				break;
			case 0:
				System.out.println();
				logout = true;
				break;
			case -1:
				switchRole = myHelper.selectRole(myConsole, myUser);
				break;
			}
			
		} while(!logout && !switchRole);
		
		return switchRole;
	}

	
	private void optionToViewManuscriptThatAreBeingReview() {
		myHelper.setMyActivity("View my List");
		System.out.println(myHelper);
		
		List<Manuscript> listOfManu = myRole.getMyManuscripts();
		List<Manuscript> completed = myRole.getAlreadyReviewManuscript();
		
		displayManuscriptWithStatusOfReview(listOfManu, completed);
		
	}
	
	private void displayManuscriptWithStatusOfReview(List<Manuscript> theMainList, List<Manuscript> theCompletedList){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\n---Manuscripts---\n");
		String add = String.format(HelperGUI.FORMAT_TABLE, "Manuscript Name", "Completed");
		toDisplay.append("   ");
		toDisplay.append(add);
		toDisplay.append('\n');
		for(int i = 0; i < theMainList.size(); i++){
			toDisplay.append((i + 1) + ". ");
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
		System.out.println(toDisplay.toString());
	}
	
	private void optionToSubmitAReview() {
		// TODO Auto-generated method stub
		
	}
	
}
