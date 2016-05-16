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
import model.Role;
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
	
	/**
	 * The constructor for the gui.
	 * @param Scanner The main console.
	 * @param User The user using the program.
	 */
	public ProgramChairGUI(Scanner theConsole, User theUser, Map<String, User> theListOfUser, List<Manuscript> theMasterList){
		myConsole = theConsole;
		myUser = theUser;
		myListOfUser = theListOfUser;
		myMasterList = theMasterList;
		if(!(myUser.getCurrentRole() instanceof ProgramChair)){
			throw new InputMismatchException();
		}
		myRole = (ProgramChair)myUser.getCurrentRole();
	}
	
	/**
	 * The method to run the main gui for program chair.
	 */
	public boolean loop(){
		
		
		boolean logout = false;
		do{
			System.out.println("\n---------------\n\nWhat Do you want to do?");
			System.out.println("1. View a list of all submitted manuscripts");
			System.out.println("2. Make acceptance decision");
			System.out.println("3. See which papers are assigned to which Subprogram chairs");
			System.out.println("4. Designate a Subprogram Chair for a manuscript");
			System.out.println("5. Logout");
			
			int select = getSelect(myConsole);
			///////////////////////// OPTION 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(select == 1){
				System.out.println("\nSelect a Manuscript to view");
				List<Manuscript> tempList = myRole.showAllManuscripts(myMasterList);
				for(int i = 0; i < tempList.size(); i++){
					System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
				}
				System.out.println("--end of manuscript list--");
				System.out.println(tempList.size() + 1 + ". Back");
				int select2 = getSelect(myConsole);
				if(select2 == tempList.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println(tempList.get(select2 - 1).toString());
				}
				
			//////////////////////////OPTION 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 2){
				System.out.println("\nPick a manuscript to accept and reject");
				List<Manuscript> tempList = myRole.showAllManuscripts(myMasterList);
				for(int i = 0; i < tempList.size(); i++){
					System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
				}
				System.out.println("--end of manuscript list--");
				System.out.println(tempList.size() + 1 + ". Back");
				int select2 = getSelect(myConsole);
				
				if(select2 == tempList.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println("Accept (1) or Reject (2) or Back (3)? :");
					int select3 = getInt(myConsole);
					if(select3 == 3){
						System.out.println("\n Back \n");
					} else if(select3 == 1){
						myRole.approveManuscript(tempList.get(select2 - 1));
						System.out.println(tempList.get(select2 - 1).getStatus());
					} else if(select3 == 2){
						myRole.rejectManuscript(tempList.get(select2 - 1));
					}
				}
				
			////////////////////////////////// OPTION 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 3){
				System.out.println("\n---Pick a subprogram chair---");
				List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
				StringBuilder tempString = new StringBuilder();
				for(int i = 0; i < tempArr.size(); i++){
					tempString.append(i + 1);
					tempString.append(". ");
					tempString.append(tempArr.get(i).getMyUsername());
					tempString.append('\n');
				}
				System.out.print(tempString);
				System.out.println("---end of Subprogram Chair list---");
				System.out.println(tempArr.size() + 1 + ". Back");
				int select2 = getSelect(myConsole);
				if(select2 == tempArr.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println("You selected : " + tempArr.get(select2 - 1).getMyUsername());
					System.out.println("--Showing Related Manuscripts--");
					List<Manuscript> tempList = myRole.showAllManuscriptAssignedToSpc(tempArr.get(select2 - 1));
					for(int i = 0; i < tempList.size(); i++){
						System.out.println(i + 1 + ". " + tempList.get(i).getTitle());
					}
					System.out.println("--end of manuscript list--");
				}
				
			///////////////////////////////// OPTION 4 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 4){
				System.out.println("\n---Pick a manuscript to assign---");
				StringBuilder tempString = new StringBuilder();
				for(int i = 0; i < myMasterList.size(); i++){
					tempString.append(i + 1);
					tempString.append(". ");
					tempString.append(myMasterList.get(i).getTitle());
					tempString.append('\n');
				}
				System.out.print(tempString);
				System.out.println("--end manuscripts--");
				System.out.println(myMasterList.size() + 1 + ". Back");
				int select2 = getSelect(myConsole);
				if(select2 == myMasterList.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println("You pick: " + myMasterList.get(select2 - 1).getTitle());
					List<SubprogramChair> tempArr = myRole.getAllSubprogramChair(myListOfUser);
					System.out.println("-Select a Subprogram to assign too-");
					StringBuilder tempString2 = new StringBuilder();
					for(int i = 0; i < tempArr.size(); i++){
						tempString2.append(i + 1);
						tempString2.append(". ");
						tempString2.append(tempArr.get(i).getMyUsername());
						tempString2.append('\n');
					}
					System.out.print(tempString2);
					System.out.println("---end of Subprogram Chair list---");
					System.out.println(tempArr.size() + 1 + ". Back");
					int select3 = getSelect(myConsole);
					if(select3 == tempArr.size() + 1){
						System.out.println("\n Back \n");
					} else {
						tempArr.get(select3 - 1).assignManuscripts(myMasterList.get(select2 - 1));
					}
					
				}
				
				
			} else if (select == 5){
				System.out.println();
				logout = true;
			}
		} while(!logout);
		
		return logout;
		
	}
	
	
	/**
	 * Get a int.
	 * @param scanner the Console scanner.
	 * @return int The integer.
	 */
	public int getInt(Scanner theConsole){
		while(!theConsole.hasNextInt()){
			theConsole.next();
			System.out.print("Please enter an integer : ");
		} 
		return theConsole.nextInt();
	}
	
	/**
	 * Get select.
	 * @param Scanner The scanner.
	 * @return int The selected.
	 */
	public int getSelect(Scanner theConsole){
		System.out.print("\nSelect: ");
		return getInt(theConsole);
	}
	
	
}
