/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;
import model.User;

/**
 * @author Vinh Vien
 *
 */
public class ProgramChairGUI {
	
	/** The main console */
	private Scanner myConsole;
	
	/** The user current selected */
	private User myUser;
	
	/** The role. */
	private ProgramChair myRole;
	
	/** The List of users. */
	private final Map<String, User> myListOfUser;
	
	/** The Master List. */
	private final List<Manuscript> myMasterList;
	private static final int VIEW_ALL_SUBMITTED_MANUSCRIPTS = 1;
	private static final int MAKE_A_ACCEPTANCE_DECISION = 2;
	private static final int SEE_PAPERS_ASSIGNED_TO_SUBPROGRAM_CHAIR = 3;
	private static final int DESIGNATE_A_SUBPROGRAM_CHAIR_FOR_A_MANUSCRIPT = 4;
	private static final int LOGOUT = 0;
	private static final int SWITCH_TO_A_DIFFERENT_ROLE = -1;
	private static final int SUBMIT_A_MANUSCRIPT_TO_THIS_CONFERENCE = -2;
	private static final int OFFSET = 1;
	private static final int BACK = 0;
	private static final int APPROVED = 1;
	private static final int REJECTED = -1;
	private static final int BACK_TO_MAIN_MENU = 3;
	private static final int ACCEPT = 1;
	private static final int REJECT = 2;
	
	/** The helper GUI */
	private final HelperGUI myHelper;
	
	/**
	 * The constructor for the GUI.
	 * @param Scanner The main console.
	 * @param User The user using the program.
	 * @throws IllegalArgumentException if null was passed in for any of the parameters.
	 * @throws InputMismatchException if role is not currently correct.
	 */
	public ProgramChairGUI(Scanner theConsole, User theUser, Map<String, User> theListOfUser, List<Manuscript> theMasterList){
		if(Objects.isNull(theConsole)){
			throw new IllegalArgumentException("The Console cannot be null");
		} else if(Objects.isNull(theUser)) {
			throw new IllegalArgumentException("The User Object cannot be null");
		} else if(Objects.isNull(theListOfUser)) {
			throw new IllegalArgumentException("The Map Of Users cannot be null");
		} else if(Objects.isNull(theMasterList)) {
			throw new IllegalArgumentException("The List of Manuscripts cannot be null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myListOfUser = theListOfUser;
		myMasterList = theMasterList;
		if(!(myUser.getCurrentRole() instanceof ProgramChair)){
			throw new InputMismatchException("User need current Role of Program Chair");
		}
		if(myUser.getConference() == null){
			throw new InputMismatchException("User need conference");
		}
		myRole = (ProgramChair)myUser.getCurrentRole();
		myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Program Chair Menu");
		myHelper.parseDate(myUser.getConference().getManuscriptDueDate());
	}
	
	/**
	 * The method to run the main GUI for program chair.
	 * @return boolean True if they want to logout.
	 */
	public boolean loop(){
		boolean switchRole = false;
		boolean logout = false;
		do{
			myHelper.setMyActivity("Program Chair Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat do you want to do?");
			System.out.println("1. View a list of all submitted manuscripts");
			System.out.println("2. Make acceptance decision on a manuscript");
			System.out.println("3. See which papers are assigned to which Subprogram Chairs");
			System.out.println("4. Designate a Subprogram Chair for a Manuscript");
			System.out.println("0. Logout of Program Chair");
			System.out.println("-1. Switch to a Different Role");
			System.out.println("-2. Submit Manuscript to Conference");
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
				case VIEW_ALL_SUBMITTED_MANUSCRIPTS:
					optionViewAListOfSubmittedManuscript();
					break;
				case MAKE_A_ACCEPTANCE_DECISION:
					optionAcceptOrRejectManuscript();
					HelperGUI.stopForASecond(myConsole, true);
					break;
				case SEE_PAPERS_ASSIGNED_TO_SUBPROGRAM_CHAIR:
					optionShowPaperAssignToSPC();
					HelperGUI.stopForASecond(myConsole, true);
					break;
				case DESIGNATE_A_SUBPROGRAM_CHAIR_FOR_A_MANUSCRIPT:
					optionToDesignateASPCForAManuscript();
					HelperGUI.stopForASecond(myConsole, true);
					break;
				case LOGOUT:
					System.out.println();
					logout = true;
					break;
				case SWITCH_TO_A_DIFFERENT_ROLE:
					switchRole = myHelper.selectRole(myConsole, myUser);
					break;
				case SUBMIT_A_MANUSCRIPT_TO_THIS_CONFERENCE:
					HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
					break;
			}
		} while(!logout && !switchRole);
		
		return switchRole;
	}
	
	/**
	 * The method to display all manuscript.
	 * @return Manuscript The manuscript.
	 */
	private Manuscript displayAllManuscript(boolean accept){
		
		Manuscript toReturn = null;
		boolean back = false;
		do{
			System.out.println(myHelper);
			
			List<Manuscript> listOfManu = myRole.showAllManuscripts(myMasterList);
			displayManuscriptWithStatus(listOfManu);
			
			if(accept){
				System.out.println("Select a manuscript to accept or reject");
			} else {
				System.out.println("Select a manuscript to view in detail");
			}
			int select2 = HelperGUI.getSelect(myConsole);
			if(select2 == BACK){
				System.out.println(HelperGUI.BACK);
				back = true;
			} else {
				toReturn = listOfManu.get(select2 - OFFSET);
				System.out.println(toReturn.toString());
				HelperGUI.stopForASecond(myConsole, true);
				
			}
		}while(!back && !accept);
		return toReturn;
	}
	
	/**
	 * Display all manuscript with status.
	 */
	private void displayManuscriptWithStatus(List<Manuscript> theListOfManu){
		
		StringBuilder toDisplay = new StringBuilder();
		String toAppend = "";
		toDisplay.append("\n---Manuscripts---\n");
		String add = String.format(HelperGUI.FORMAT_TABLE, "Manuscript Name", "Status");
		toDisplay.append("   ");
		toDisplay.append(add);
		toDisplay.append("Recommendation");
		toDisplay.append('\n');
		for(int i = 0; i < theListOfManu.size(); i++){
			toDisplay.append((i + OFFSET) + ". ");
			if(theListOfManu.get(i).getStatus() == APPROVED){
				toAppend = String.format(HelperGUI.FORMAT_TABLE, theListOfManu.get(i).getTitle(), "Accepted");
				toDisplay.append(toAppend);
			} else if(theListOfManu.get(i).getStatus() == REJECTED){
				toAppend = String.format(HelperGUI.FORMAT_TABLE, theListOfManu.get(i).getTitle(), "Rejected");
				toDisplay.append(toAppend);
			} else {
				toAppend = String.format(HelperGUI.FORMAT_TABLE, theListOfManu.get(i).getTitle(), "Neutral");
				toDisplay.append(toAppend);
			}
			if(!Objects.isNull(theListOfManu.get(i).getRecommendation())){
				toDisplay.append(theListOfManu.get(i).getRecommendation().getRecommmendationText());
			}
			toDisplay.append("\n");
		}
		toDisplay.append("--end of reviews list--\n");
		toDisplay.append("0. Back");
		System.out.println(toDisplay.toString());
	}
	
	/**
	 * The method to display all subprogram chair.
	 * @param theList The List.
	 */
	private void displayAllSubprogramChair(List<SubprogramChair> theList){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\nSelect a subprogram Chair\n");
		String add = String.format(HelperGUI.FORMAT_TABLE, "Subprogram Chair Name", "# of Manuscript Assigned");
		toDisplay.append("   ");
		toDisplay.append(add);
		toDisplay.append("\n");
		for(int i = 0; i < theList.size(); i++){	
				toDisplay.append((i + OFFSET) + ". ");
				String toAdd = String.format(HelperGUI.FORMAT_TABLE, theList.get(i).getMyUsername(), theList.get(i).getNumberOfAssignedManuscripts());
				toDisplay.append(toAdd);
				toDisplay.append("\n");		
		}
		toDisplay.append("--end of subprogram Chair list--\n");
		toDisplay.append("0. Back");
		
		System.out.println(toDisplay.toString());
	}
	
	
	/**
	 * The method to view for the GUI of submitted manuscript.
	 */
	private void optionViewAListOfSubmittedManuscript(){
		myHelper.setMyActivity("View a list of all submitted manuscripts");
		Manuscript manu = displayAllManuscript(false);
		
	}
	
	/**
	 * The method for the GUI of Accept or Reject paper.
	 */
	private void optionAcceptOrRejectManuscript(){
		myHelper.setMyActivity("\nMake acceptance decision");
		Manuscript manu = displayAllManuscript(true);
		
		if(manu != null){
			System.out.println("Accept (1) or Reject (2) or Back PC Menu (3)? :");
			int select3 = HelperGUI.getInt(myConsole);
			if(select3 == BACK_TO_MAIN_MENU){
				System.out.println(HelperGUI.BACK);
			} else if(select3 == ACCEPT){
				myRole.approveManuscript(manu);
				//System.out.println(manu.getStatus());
			} else if(select3 == REJECT){
				myRole.rejectManuscript(manu);
			}
			System.out.println("Success");
		}
	}
	
	
	/**
	 * The method for the option of see paper assigned to SPC.
	 */
	private void optionShowPaperAssignToSPC(){
		
		myHelper.setMyActivity("See which papers are assigned to which Subprogram chairs");
		//System.out.println(myHelper);
		
		//System.out.println("\n---Pick a subprogram chair---");
		List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
		boolean back = false;
		do{
			System.out.println(myHelper);
			displayAllSubprogramChair(tempArr);
			int select2 = HelperGUI.getSelect(myConsole);
			if(select2 == BACK){
				System.out.println(HelperGUI.BACK);
				back = true;
			} else {
				System.out.println("You selected : " + tempArr.get(select2 - OFFSET).getMyUsername());
				System.out.println("--Showing Related Manuscripts--");
				List<Manuscript> tempList = myRole.showAllManuscriptAssignedToSpc(tempArr.get(select2 - OFFSET));
				HelperGUI.displayManuscripts(tempList, false);
				HelperGUI.stopForASecond(myConsole, true);
			}
		} while(!back);
	}
	
	/**
	 * The method for the option to designate a spc for a manuscript.
	 */
	private void optionToDesignateASPCForAManuscript(){
		myHelper.setMyActivity("Designate a Subprogram Chair for a manuscript");
		System.out.println(myHelper);
		
		boolean back = false;
		do{
			List<Manuscript> listManuscript = myRole.getAllManuscriptsForThisConference(myMasterList);
			HelperGUI.displayManuscripts(listManuscript, true);
			int userSelectedManuscriptNumber = HelperGUI.getSelect(myConsole);
		
			back = false;
			if(userSelectedManuscriptNumber == BACK){
				System.out.println(HelperGUI.BACK);
			} else {
				System.out.println("You pick: " + listManuscript.get(userSelectedManuscriptNumber - 1).getTitle());
				
				List<SubprogramChair> listOfSubprogramChair = myRole.getAllSubprogramChair(myListOfUser);
				System.out.println("-Select a Subprogram to assign too-");
				displayAllSubprogramChair(listOfSubprogramChair);
				int userSelectedSubprogramChairNumber = HelperGUI.getSelect(myConsole);
				
				if(userSelectedSubprogramChairNumber == BACK){
					back = true;
				}
				// LOGIC STATEMENT
				assignManuscriptToSubprogramChair(userSelectedSubprogramChairNumber, listOfSubprogramChair, userSelectedManuscriptNumber, listManuscript);
			}
		} while(back);
	}
	
	/**
	 * The logic for assigning the manuscript for sub-program chair. Mostly use for testing.
	 */
	public void assignManuscriptToSubprogramChair(final int theSelectedNumber, List<SubprogramChair> theListOfSubProgramChair, int theManuscriptNumber, List<Manuscript> theListOfManuscripts){
		if(theSelectedNumber == 0){
			System.out.println(HelperGUI.BACK);
		} else {
			try{
				theListOfSubProgramChair.get(theSelectedNumber - OFFSET).assignManuscripts(theListOfManuscripts.get(theManuscriptNumber - OFFSET));
				System.out.println("Success!");
			} catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				System.out.println("Unable to assign Manuscript");
			}
		}
	}
	
	
}
