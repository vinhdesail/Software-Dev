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

public class SubprogramChairGUI {

	/** The main console */
	private Scanner myConsole;	
	/** The user current selected */
	private User myUser;	
	/** The role. */
	private SubprogramChair myRole;	
	/** The List of users. */
	private final Map<String, User> myListOfUser;	
	/** The Master List. */
	private final List<Manuscript> myMasterList;	
	/** The helper GUI */
	private final HelperGUI myHelper;
	/** */
	private static final int ASSIGN_MANUSCRIPT_TO_REVIEWER = 1;
	/** */
	private static final int SUBMIT_A_RECOMMENDATION = 2;
	/** */
	private static final int LOGOUT = 3;
	/**
	 * The constructor for the gui.
	 * @param Scanner The main console.
	 * @param User The user using the program.
	 */
	public SubprogramChairGUI(Scanner theConsole, User theUser, Map<String, User> theListOfUser, List<Manuscript> theMasterList){
		if(theConsole == null){
			throw new IllegalArgumentException("theConsole Cannot be null");
		} else if( theUser == null ) {
			throw new IllegalArgumentException("theUse Cannot be null");
		} else if(theListOfUser == null) {
			throw new IllegalArgumentException("theListOfUser Cannot be null");
		} else if(theMasterList == null) {
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
	
	public boolean loop() {	
		boolean logout = false;
		do {
			printMainMenu();
			int select = HelperGUI.getSelect(myConsole);			
			if(select == ASSIGN_MANUSCRIPT_TO_REVIEWER){		
				assignManuscriptToReviewerMenu();
			} else if(select == SUBMIT_A_RECOMMENDATION) { 
				submitARecommendationMenu(select);
			} else if(select == LOGOUT) {
				logout = true;
			} else if(select == -2){
				HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
			}
		} while(!logout);	
		return logout;
	}	

	public void submitARecommendationMenu(int select) {
		System.out.println("Please Select a Manuscript for your recommendation");			
		List<Manuscript> tempList = myRole.showAllAssignedManuscripts();
		for(int i  = 0; i < tempList.size();i++) {
			System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
		}
		System.out.println("--end of manuscript list--");
		select = HelperGUI.getSelect(myConsole);
		Manuscript tempManu = tempList.get(select-1);
		myConsole.nextLine();
		System.out.print("Write a recommendation: ");
		String recText = myConsole.nextLine();
		Recommendation rec = new Recommendation(myRole.getMyUsername(), tempManu.getTitle(), recText);
		tempManu.setRecommendation(rec);
		System.out.println("Success!\n\n\n\n\n");
	}
	
	public void assignManuscriptToReviewerMenu() {
		myHelper.setMyActivity("Please Select a Manuscript to be assigned");					
		Manuscript tempManu = manuscriptSelectionMenu();
		if(Objects.nonNull(tempManu)) {
			Reviewer tempReviewer = reviewerSelectionMenu();
			if(Objects.isNull(tempReviewer)) {
				assignManuscriptToReviewerMenu();
			} else {
				myRole.AssignReviewer(tempReviewer, tempManu);				
				System.out.println("Success!\n\n\n\n\n");	
			}
		}				
	}
	
	public Manuscript manuscriptSelectionMenu() {
		Manuscript manuscriptThatHasBeenSelected;
		int selection = 0;
		List<Manuscript> tempList = myRole.showAllAssignedManuscripts();
		for(int i = 0; i < tempList.size(); i++){
			System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
		}
		System.out.println("--end of manuscript list--");
		System.out.println("0.  Back to previous Menu");
		selection = HelperGUI.getSelect(myConsole);
		if(selection == 0) {
			return null;
		}
		manuscriptThatHasBeenSelected = tempList.get(selection-1);	
		return manuscriptThatHasBeenSelected;
	}
	
	public Reviewer reviewerSelectionMenu() {
		Reviewer reviewerThatHasBeenSelected;
		int select = 0;
		List<Reviewer> listOfReviewers = new ArrayList<>();
		System.out.println("Please Select a reviewer to be assigned");
		int indexToDisplay = 1;
		for(String userName: myListOfUser.keySet()) {
			indexToDisplay = checkThroughAllRolesOfAGivenUser(userName,indexToDisplay,listOfReviewers);
		}
		System.out.println("0. Back to previous Menu");
		select = HelperGUI.getSelect(myConsole);
		if(select == 0) {
			return null;
		}
		reviewerThatHasBeenSelected = listOfReviewers.get(select-1);
		return reviewerThatHasBeenSelected;
		
	}
	
	public int checkThroughAllRolesOfAGivenUser(String theUserName,int theIndexToDisplay, List<Reviewer> theListOfReviewers){
		Role roleToBeCheckedIfReviewer;
		for(int i = 0; i < myListOfUser.get(theUserName).getListOfAllRoles().size(); i++) {
			roleToBeCheckedIfReviewer = myListOfUser.get(theUserName).getListOfAllRoles().get(i);
			if(roleToBeCheckedIfReviewer instanceof Reviewer) {
				System.out.println((theIndexToDisplay++) + ". " + ((Reviewer)roleToBeCheckedIfReviewer).getMyUsername());
				theListOfReviewers.add((Reviewer)roleToBeCheckedIfReviewer);
			}
		}
		return theIndexToDisplay;
	}
	
	public void printMainMenu() {
		System.out.println("\n---------------\n");
		myHelper.setMyActivity("Subprogram Chair Menu");
		System.out.println(myHelper);			
		System.out.println("\n---------------\n\nWhat Do you want to do?");
		System.out.println("1. Assign A Reviewer A Manuscript");
		System.out.println("2. Submit a Recommendation");
		System.out.println("3. Logout");		
		System.out.println("-2. Submit Manuscript to Conference");
	}
}

