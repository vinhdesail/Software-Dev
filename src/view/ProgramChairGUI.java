/**
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
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
	
	/** The helper GUI */
	private final HelperGUI myHelper;
	
	/**
	 * The constructor for the GUI.
	 * @param Scanner The main console.
	 * @param User The user using the program.
	 */
	public ProgramChairGUI(Scanner theConsole, User theUser, Map<String, User> theListOfUser, List<Manuscript> theMasterList){
		if(theConsole == null || theUser == null || theListOfUser == null || theMasterList == null){
			throw new IllegalArgumentException("Cannot accept null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myListOfUser = theListOfUser;
		myMasterList = theMasterList;
		if(!(myUser.getCurrentRole() instanceof ProgramChair)){
			throw new InputMismatchException();
		}
		myRole = (ProgramChair)myUser.getCurrentRole();
		myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Program Chair Menu");
	}
	
	/**
	 * The method to run the main GUI for program chair.
	 */
	public boolean loop(){
		
		
		boolean logout = false;
		do{
			myHelper.setMyActivity("Program Chair Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat Do you want to do?");
			System.out.println("1. View a list of all submitted manuscripts");
			System.out.println("2. Make acceptance decision");
			System.out.println("3. See which papers are assigned to which Subprogram chairs");
			System.out.println("4. Designate a Subprogram Chair for a manuscript");
			System.out.println("5. Logout of Program Chair");
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
				///////////////////////// OPTION 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				case 1:
					optionViewAListOfSubmittedManuscript();
					break;
				//////////////////////////OPTION 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				case 2:
					optionAcceptOrRejectManuscript();
					break;
				////////////////////////////////// OPTION 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!
				case 3:
					optionShowPaperAssignToSPC();
					break;
				///////////////////////////////// OPTION 4 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				case 4:
					optionToDesignateASPCForAManuscript();
					break;
				///////////////////////////////// OPTION 5 !!!!!!!!!!!!!!!!!!!!!!!
				default:
					System.out.println();
					logout = true;
					break;
			}
		} while(!logout);
		
		return logout;
	}
	
	/**
	 * The method to display all manuscript.
	 * @return Manuscript The manuscript.
	 */
	public Manuscript displayAllManuscript(){
		
		Manuscript toReturn = null;
		System.out.println(myHelper);
		
		List<Manuscript> listOfManu = myRole.showAllManuscripts(myMasterList);
		displayManuscripts(listOfManu, true);
		
		int select2 = HelperGUI.getSelect(myConsole);
		if(select2 == listOfManu.size() + 1){
			System.out.println(HelperGUI.BACK);
		} else {
			toReturn = listOfManu.get(select2 - 1);
		}
		return toReturn;
	}
	
	/**
	 * The method to display a list of manuscript.
	 * @param theList The List.
	 * @param theDisplayBack See if you want to display the back option.
	 */
	public void displayManuscripts(List<Manuscript> theList, boolean theDisplayBack){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\nSelect a Manuscript\n");
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
	 * The method to display all subprogram chair.
	 * @param theList The List.
	 */
	public void displayAllSubprogramChair(List<SubprogramChair> theList){
		StringBuilder toDisplay = new StringBuilder();
		toDisplay.append("\nSelect a Program Chair\n");
		for(int i = 0; i < theList.size(); i++){
			toDisplay.append((i + 1) + ". " + theList.get(i).getMyUsername());
			toDisplay.append("\n");
		}
		toDisplay.append("--end of Program Chair list--\n");
		toDisplay.append("0. Back");
		
		System.out.println(toDisplay.toString());
	}
	
	
	/**
	 * The method to view for the GUI of submitted manuscript.
	 */
	public void optionViewAListOfSubmittedManuscript(){
		myHelper.setMyActivity("View a list of all submitted manuscripts");
		Manuscript manu = displayAllManuscript();
		if(manu != null){
			System.out.println(manu.toString());
		}
	}
	
	/**
	 * The method for the GUI of Accept or Reject paper.
	 */
	public void optionAcceptOrRejectManuscript(){
		myHelper.setMyActivity("\nMake acceptance decision");
		Manuscript manu = displayAllManuscript();
		
		if(manu != null){
			System.out.println("Accept (1) or Reject (2) or Back PC Menu (3)? :");
			int select3 = HelperGUI.getInt(myConsole);
			if(select3 == 3){
				System.out.println(HelperGUI.BACK);
			} else if(select3 == 1){
				myRole.approveManuscript(manu);
				//System.out.println(manu.getStatus());
			} else if(select3 == 2){
				myRole.rejectManuscript(manu);
			}
		}
	}
	
	
	/**
	 * The method for the option of see paper assigned to SPC.
	 */
	public void optionShowPaperAssignToSPC(){
		
		myHelper.setMyActivity("See which papers are assigned to which Subprogram chairs");
		System.out.println(myHelper);
		
		System.out.println("\n---Pick a subprogram chair---");
		List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
		displayAllSubprogramChair(tempArr);
		
		int select2 = HelperGUI.getSelect(myConsole);
		if(select2 == 0){
			System.out.println(HelperGUI.BACK);
		} else {
			System.out.println("You selected : " + tempArr.get(select2 - 1).getMyUsername());
			System.out.println("--Showing Related Manuscripts--");
			List<Manuscript> tempList = myRole.showAllManuscriptAssignedToSpc(tempArr.get(select2 - 1));
			displayManuscripts(tempList, false);
		}
	}
	
	/**
	 * The methof for the option to designate a spc for a manuscript.
	 */
	public void optionToDesignateASPCForAManuscript(){
		myHelper.setMyActivity("Designate a Subprogram Chair for a manuscript");
		System.out.println(myHelper);
		
		List<Manuscript> listManuscript = myRole.getAllManuscriptForThisConference(myMasterList);
		displayManuscripts(listManuscript, true);
		int select2 = HelperGUI.getSelect(myConsole);
		
		
		if(select2 == myMasterList.size() + 1){
			System.out.println(HelperGUI.BACK);
		} else {
			System.out.println("You pick: " + myMasterList.get(select2 - 1).getTitle());
			
			List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
			System.out.println("-Select a Subprogram to assign too-");
			displayAllSubprogramChair(tempArr);
			int select3 = HelperGUI.getSelect(myConsole);
			if(select3 == 0){
				System.out.println(HelperGUI.BACK);
			} else {
				try{
					tempArr.get(select3 - 1).assignManuscripts(myMasterList.get(select2 - 1));
					System.out.println("Success!");
				} catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
					System.out.println("Unable to assign Manuscript");
				}
			}
		}
	}
	
	
}
