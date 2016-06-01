package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.Manuscript;
import model.Recommendation;
import model.Reviewer;
import model.Role;
import model.SubprogramChair;
import model.User;

/**
 * 
 * @author Josh Meigs
 * @author Vinh Vien
 *
 */
public class SubprogramChairGUI {

	/* */
	private static final int ASSIGN_MANUSCRIPT_TO_REVIEWER = 1;
	/* */
	private static final int SUBMIT_A_RECOMMENDATION = 2;
	/* */
	private static final int LOGOUT = 0;
	/* */
	private static final int SWITCH_ROLES = -1;
	/* */
	private static final int BACK = 0;
	/* */
	private static final int OFFSET = 1;
	/* */
	private static final int STARTING_POSISTION = -1;
	/* */
	private static final int SUBMIT_MANUSCRIPT_FOR_THIS_CONFERENCE = -2;
	/* The main console */
	private Scanner myConsole;	
	/* The user current selected */
	private User myUser;	
	/* The role. */
	private SubprogramChair myRole;	
	/* The List of users. */
	private final Map<String, User> myListOfUser;	
	/* The Master List. */
	private final List<Manuscript> myMasterList;	
	/* The helper GUI */
	private final HelperGUI myHelper;

	/**
	 * The constructor for the gui.
	 * @param Scanner The main console.
	 * @param User The user using the program.
	 */
	public SubprogramChairGUI(Scanner theConsole, User theUser, Map<String, User> theListOfUser, List<Manuscript> theMasterList){
		if(Objects.isNull(theConsole)){
			throw new IllegalArgumentException("theConsole Cannot be null");
		} else if( Objects.isNull(theUser) ) {
			throw new IllegalArgumentException("theUse Cannot be null");
		} else if(Objects.isNull(theListOfUser)) {
			throw new IllegalArgumentException("theListOfUser Cannot be null");
		} else if(Objects.isNull(theMasterList)) {
			throw new IllegalArgumentException("theMasterList Cannot be null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myListOfUser = theListOfUser;
		myMasterList = theMasterList;
		if(!(myUser.getCurrentRole() instanceof SubprogramChair)){
			throw new InputMismatchException();
		}
		myRole = (SubprogramChair)myUser.getCurrentRole();
		myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Subprogram Chair Menu");
		myHelper.parseDate(myUser.getConference().getManuscriptDueDate());
	}
	
	
	private void printMainMenu() {
		myHelper.setMyActivity("Subprogram Chair Menu");
		System.out.println(myHelper);			
		System.out.println("\nWhat Do you want to do?");
		System.out.println("1. Assign A Reviewer A Manuscript");
		System.out.println("2. Submit a Recommendation");
		System.out.println("0. Logout");		
		System.out.println("-1. Switch to a Different Role");
		System.out.println("-2. Submit Manuscript to Conference");
	}
	
	public boolean loop() {	
		boolean logout = false;
		boolean switchRole = false;
		do {
			printMainMenu();
			int select = HelperGUI.getSelect(myConsole);
			switch (select){
				case ASSIGN_MANUSCRIPT_TO_REVIEWER:
					optionAssignManuscriptToReviewerMenu();
					break;
				case SUBMIT_A_RECOMMENDATION:
					optionSubmitARecommendationMenu();
					break;
				case LOGOUT:
					System.out.println();
					logout = true;
					break;
				case SWITCH_ROLES:
					switchRole = myHelper.selectRole(myConsole, myUser);
					break;
				case SUBMIT_MANUSCRIPT_FOR_THIS_CONFERENCE:
					HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
					break;
			}
		} while(!logout && !switchRole);	
		return switchRole;
	}	

	private void optionSubmitARecommendationMenu() {
		
		myHelper.setMyActivity("Submit a recommendation for a manuscript");
		System.out.println(myHelper);
		boolean exit = false;
		do{
			int select = STARTING_POSISTION;
			System.out.println("Please Select a Manuscript for your recommendation");			
			List<Manuscript> tempList = myRole.showAllAssignedManuscripts();
			HelperGUI.displayManuscripts(tempList, true);
			select = HelperGUI.getSelect(myConsole);
			
			if(select == BACK){
				System.out.println(HelperGUI.BACK);
				exit = true;
			} else {
				Manuscript editManu = tempList.get(select - OFFSET); 
				myConsole.nextLine();
				System.out.println("Enter a file path of your recomendation \n(or \"EXIT\" to quit or \"BACK\" to select a different manuscript) ");
				System.out.println("Example: C:/RecommendationForMagicSounds.txt");
				System.out.println("--: ");
				String recText = myConsole.nextLine();
				if(recText.equalsIgnoreCase("EXIT")){
					exit = true;
				} else if(recText.equalsIgnoreCase("BACK")){
					exit = false;
				} else {
					Recommendation rec = new Recommendation(myRole.getMyUsername(), editManu.getTitle(), recText);
					editManu.setRecommendation(rec);
					System.out.println("Success!");
					exit = true;
				}
			}
		} while(!exit);
		
	}
	
	private void optionAssignManuscriptToReviewerMenu() {
		myHelper.setMyActivity("Please Select a Manuscript to be assigned");
		Reviewer tempReviewer = null;
		Manuscript tempManu = null;
		boolean exit = false;
		do{
			tempManu = manuscriptSelectionMenu();
			if(Objects.nonNull(tempManu)) {
				
				tempReviewer = reviewerSelectionMenu();
			} else {
				exit = true;
			}
		} while(tempReviewer == null && !exit);
		
		if(tempReviewer != null && !exit){
			try{
				myRole.assignReviewer(tempReviewer, tempManu);	
				System.out.println("Success!");	
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	/**
	 * Display a list of manuscript and get a selection.
	 * @return
	 */
	private Manuscript manuscriptSelectionMenu() {
		Manuscript manuscriptThatHasBeenSelected;
		int selection = 0;
		List<Manuscript> listOfManu = myRole.showAllAssignedManuscripts();
		HelperGUI.displayManuscripts(listOfManu, true);
		selection = HelperGUI.getSelect(myConsole);
		if(selection == BACK) {
			return null;
		}
		manuscriptThatHasBeenSelected = listOfManu.get(selection-1);	
		return manuscriptThatHasBeenSelected;
	}
	
	/**
	 * Display a list of reviewer and get a selection.
	 * @return
	 */
	private Reviewer reviewerSelectionMenu() {
		Role reviewerThatHasBeenSelected;
		int select = STARTING_POSISTION;
		List<Role> listOfReviewers = myRole.getAllReviewer(myListOfUser);
		System.out.println("Please Select a reviewer to be assigned");
		StringBuilder toPrint = new StringBuilder();
		
		String add = String.format(HelperGUI.FORMAT_TABLE, "Reviewer Name", "# of Manuscript Assigned");
		toPrint.append("   ");
		toPrint.append(add);
		toPrint.append('\n');
		
		for(int i = 0; i < listOfReviewers.size(); i++) {
			toPrint.append((i + OFFSET) + ". ");
			Reviewer rev = (Reviewer) listOfReviewers.get(i);
			String toAppend = String.format(HelperGUI.FORMAT_TABLE, rev.getMyUsername(), rev.getAssignmentSize());
			toPrint.append(toAppend);
			toPrint.append('\n');
		}
		System.out.println(toPrint);
		System.out.println("0. Back");
		select = HelperGUI.getSelect(myConsole);
		if(select == BACK) {
			return null;
		}
		reviewerThatHasBeenSelected = listOfReviewers.get(select-OFFSET);
		return (Reviewer) reviewerThatHasBeenSelected;
		
	}

}

