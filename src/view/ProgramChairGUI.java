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
	 * The constructor for the gui.
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
	 * The method to run the main gui for program chair.
	 */
	public boolean loop(){
		
		
		boolean logout = false;
		do{
			System.out.println("\n---------------\n");
			myHelper.setMyActivity("Program Chair Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat Do you want to do?");
			System.out.println("1. View a list of all submitted manuscripts");
			System.out.println("2. Make acceptance decision");
			System.out.println("3. See which papers are assigned to which Subprogram chairs");
			System.out.println("4. Designate a Subprogram Chair for a manuscript");
			System.out.println("5. Logout of Program Chair");
			
			int select = HelperGUI.getSelect(myConsole);
			///////////////////////// OPTION 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(select == 1){
				
				myHelper.setMyActivity("View a list of all submitted manuscripts");
				Manuscript manu = displayAllManuscript();
				if(manu != null){
					System.out.println(manu.toString());
				}
				
				
			//////////////////////////OPTION 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 2){
				myHelper.setMyActivity("\nMake acceptance decision");
				Manuscript manu = displayAllManuscript();
				
				if(manu != null){
					System.out.println("Accept (1) or Reject (2) or Back PC Menu (3)? :");
					int select3 = HelperGUI.getInt(myConsole);
					if(select3 == 3){
						System.out.println("\n Back \n");
					} else if(select3 == 1){
						myRole.approveManuscript(manu);
						//System.out.println(manu.getStatus());
					} else if(select3 == 2){
						myRole.rejectManuscript(manu);
					}
				}
				
				
			////////////////////////////////// OPTION 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 3){
				
				optionPaperAssignToSPC();
				
			///////////////////////////////// OPTION 4 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 4){
				
				optionToDesignateASPCForAManuscript();
			
			/////////////////////////////////////// OPTION 5 //////////////////////////////////
			} else if (select == 5){
				System.out.println();
				logout = true;
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
			System.out.println("\n Back \n");
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
			toDisplay.append(theList.size() + 1 + ". Back");
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
		toDisplay.append(theList.size() + 1 + ". Back");
		
		System.out.println(toDisplay.toString());
	}
	
	
	/**
	 * The method for the option of see paper assigned to SPC.
	 */
	public void optionPaperAssignToSPC(){
		
		myHelper.setMyActivity("See which papers are assigned to which Subprogram chairs");
		System.out.println(myHelper);
		
		System.out.println("\n---Pick a subprogram chair---");
		List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
		displayAllSubprogramChair(tempArr);
		
		int select2 = HelperGUI.getSelect(myConsole);
		if(select2 == tempArr.size() + 1){
			System.out.println("\n Back \n");
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
			System.out.println("\n Back \n");
		} else {
			System.out.println("You pick: " + myMasterList.get(select2 - 1).getTitle());
			
			List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
			System.out.println("-Select a Subprogram to assign too-");
			displayAllSubprogramChair(tempArr);
			int select3 = HelperGUI.getSelect(myConsole);
			if(select3 == tempArr.size() + 1){
				System.out.println("\n Back \n");
			} else {
				tempArr.get(select3 - 1).assignManuscripts(myMasterList.get(select2 - 1));
				System.out.println("Success!");
			}
		}
	}
	
//	/**
//	 * Get a int.
//	 * @param scanner the Console scanner.
//	 * @return int The integer.
//	 */
//	public int getInt(Scanner theConsole){
//		while(!theConsole.hasNextInt()){
//			theConsole.next();
//			System.out.print("Please enter an integer : ");
//		} 
//		return theConsole.nextInt();
//	}
//	
//	/**
//	 * Get select.
//	 * @param Scanner The scanner.
//	 * @return int The selected.
//	 */
//	public int getSelect(Scanner theConsole){
//		System.out.print("\nSelect: ");
//		return getInt(theConsole);
//	}
	
	
}
