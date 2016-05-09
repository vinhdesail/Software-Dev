/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import model.Author;
import model.Conference;
import model.Date;
import model.Manuscript;
import model.ProgramChair;
import model.Reviewer;
import model.Role;
import model.SubprogramChair;
import model.User;

/**
 * @author Vinh Vien
 *
 */
public class LogIn {
	
	/**
	 * The master list of manuscript.
	 */
	private List<Manuscript> myMasterList;
	
	/**
	 * The hash of users and there names.
	 */
	private Map<String, User> myUsers;
	
	/**
	 * The list of all conferences.
	 */
	private List<Conference> myConferences;
	
	/**
	 * The constructor.
	 */
	public LogIn(){
		
		initializeFields();
		//restorePreviousState();
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("Log-In");
		
		boolean exit = false;
		
		do{
			
			User currentUser = login(console);
			
			// SELECT A CONFERENCE
			selectConference(console, currentUser);
			
			// Current user and get role
			if(currentUser.hasRole()){
				System.out.println("Select a Role!");
				System.out.println(currentUser.getAllRoles());
				System.out.print("Your selection: ");
				int select = getInt(console);
				while(!currentUser.switchRole(select)){
					System.out.print("Select a valid role : ");
					select = getInt(console);
				}
			}
			
			Role currentRole = currentUser.getCurrentRole();
			
			boolean logout =  false;
			boolean backToLogin = false;
			do{
				if(currentRole == null){
					//System.out.println("I have no role");
					noRole(console, currentUser);
					if(currentUser.hasRole()){
						System.out.println("Select a Role!");
						System.out.println(currentUser.getAllRoles());
						System.out.print("Your selection: ");
						int select = getInt(console);
						while(!currentUser.switchRole(select)){
							System.out.print("Select a valid role : ");
							select = getInt(console);
						}
					}
				} else if(currentRole instanceof Author){
					//System.out.println("IT WORKS! I AM AUTHOR!");
					authorBranch(console, (Author) currentRole);
				} else if(currentRole instanceof ProgramChair){
					//System.out.println("IT WORKS! I AM ProgramChair!");
					programChairBranch(console, (ProgramChair) currentRole);
				} else if(currentRole instanceof SubprogramChair){
					subprogramChairBranch(console, (SubprogramChair) currentRole);
				} else if(currentRole instanceof Reviewer){
					reviewerBranch(console, (Reviewer) currentRole);
				}
				
				
				// Ask to make sure if they are login out.
				System.out.println("Are you sure about logout? (1 for yes, any integer for no): ");
				int tempLogout = getInt(console);
				if(tempLogout == 1){
					backToLogin = true;
				}
				
			}while(!backToLogin);
			
			System.out.print("Exit Program? (yes or no): ");
			String answer = console.next();
			if(answer.toLowerCase().charAt(0) == 'y'){
				exit = true;
			}
			
		}while(!exit);
		
		
		console.close();
		
		//logout();
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
	 * Get Serializable objects from memory.
	 */
	public void restorePreviousState(){
		try{
			InputStream file = new FileInputStream("output.ser");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			
			myUsers = (Map<String, User>) input.readObject();
			myMasterList = (List<Manuscript>) input.readObject();
			
			input.close();
			
		} catch(IOException e){
			System.out.println("ERROR IN DESERIALIZING!!!!!! IO");
		} catch(ClassNotFoundException e){
			System.out.println("ERROR IN DESERIALIZING!!!!!! Class not found");
			//fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", e);
		}
	}
	
	/**
	 * Login prompt.
	 * @param Scanner The console. 
	 * @return List<Role> The list of roles related to the user.
	 */
	public User login(Scanner theConsole){
		System.out.print("Username: ");
		String username = theConsole.next();
		while(!myUsers.containsKey(username)){
			System.out.println("Sorry, there was no user with that name!");
			System.out.print("Please Enter another one: ");
			username = theConsole.next();
		}
		return myUsers.get(username);
	}
	
	
	
	/**
	 * Test initialization for debuging.
	 */
	private void initializeFields(){
		myMasterList = new ArrayList<>();
		myUsers = new HashMap<>();
		myConferences = new ArrayList<>();
		
		String testProgramChairName = "Sally";
		String testConferenceName = "Conference 1";
		Date testDateCon = new Date(2016, 11, 2);
		Date testDateMan = new Date(2016, 7, 2);
		Date testDateRev = new Date(2016, 8, 2);
		Date testDateRec = new Date(2016, 9, 2);
		Date testDateDec = new Date(2016, 10, 2);
		Conference testConference = new Conference(testConferenceName, testProgramChairName, testDateCon, 
				testDateMan, testDateRev, testDateRec, testDateDec);
		myConferences.add(testConference);
		
		User sally = new User();
		ProgramChair pc = new ProgramChair(testConference, testProgramChairName);
		sally.addRole(pc);
		myUsers.put(testProgramChairName, sally);
		
		//Test Author
		String testAuthorName = "Bob";
		User bob = new User(testAuthorName);

		Manuscript tempManu = new Manuscript(testAuthorName, testConferenceName, "How To Increase Sales", 
				"C:/nothing.txt");
		Manuscript tempManu2 = new Manuscript(testAuthorName, testConferenceName, "How To Increase Sales 2.0", 
				"C:/nothing2.txt");
		
		bob.submitManuscript(tempManu, myMasterList);
		bob.submitManuscript(tempManu2, myMasterList);
		myUsers.put(testAuthorName, bob);
		
		//Test Subprogram Chair
		User tom = new User("Tom");
		SubprogramChair subP = new SubprogramChair("Tom");
		subP.assignManuscripts(tempManu2);
		tom.addRole(subP);
		myUsers.put("Tom", tom);
		
		
		
		
	}
	
	/**
	 * Create serialize stuff.
	 */
	private void initializeFields2(){
		myMasterList = new ArrayList<>();//all Manuscripts
		myUsers = new HashMap<>();
		myConferences = new ArrayList<>();

	}
	
	/**
	 * The method to store serializable objects.
	 */
	public void logout(){
		try{
			OutputStream file = new FileOutputStream("output.ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			
			output.writeObject(myUsers);
			output.writeObject(myMasterList);
			
			output.flush();
			output.close();
		} catch(IOException e){
			System.out.println("ERROR IN SERIALIZING!!!!!!");
		}
	}
	
	/**
	 * The method to select a conference.
	 */
	public void selectConference(final Scanner theConsole, final User theUser){
		
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < myConferences.size(); i++){
			temp.append(i + 1);
			temp.append(". ");
			temp.append(myConferences.get(i).getConferenceID());
			temp.append('\n');
		}
		
		System.out.println("What conference?");
		System.out.println(temp.toString());
		int select = getSelect(theConsole);
		theUser.switchConference(myConferences.get(select - 1));
		System.out.println("You selected: " + myConferences.get(select - 1).getConferenceID());
		
	}
	
	
	/**
	 * All the Author options.
	 * @param Scanner The input scanner.
	 */
	public boolean authorBranch(Scanner theConsole, Author theRole){
		
		return false;
	}
	
	/**
	 * All the program chair options.
	 * @param Scanner The input scanner.
	 */
	public boolean programChairBranch(Scanner theConsole, ProgramChair theRole){
		boolean logout = false;
		do{
			System.out.println("\n---------------\n\nWhat Do you want to do?");
			System.out.println("1. View a list of all submitted manuscripts");
			System.out.println("2. Make acceptance decision");
			System.out.println("3. See which papers are assigned to which Subprogram chairs");
			System.out.println("4. Designate a Subprogram Chair for a manuscript");
			System.out.println("5. Logout");
			
			int select = getSelect(theConsole);
			///////////////////////// OPTION 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(select == 1){
				System.out.println("\nSelect a Manuscript to view");
				List<Manuscript> tempList = theRole.showAllManuscripts(myMasterList);
				for(int i = 0; i < tempList.size(); i++){
					System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
				}
				System.out.println("--end of manuscript list--");
				System.out.println(tempList.size() + 1 + ". Back");
				int select2 = getSelect(theConsole);
				if(select2 == tempList.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println(tempList.get(select2 - 1).toString());
				}
				
			//////////////////////////OPTION 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 2){
				System.out.println("\nPick a manuscript to accept and reject");
				List<Manuscript> tempList = theRole.showAllManuscripts(myMasterList);
				for(int i = 0; i < tempList.size(); i++){
					System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
				}
				System.out.println("--end of manuscript list--");
				System.out.println(tempList.size() + 1 + ". Back");
				int select2 = getSelect(theConsole);
				
				if(select2 == tempList.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println("Accept (1) or Reject (2) or Back (3)? :");
					int select3 = getInt(theConsole);
					if(select3 == 3){
						System.out.println("\n Back \n");
					} else if(select3 == 1){
						theRole.approveManuscript(tempList.get(select2 - 1));
						System.out.println(tempList.get(select2 - 1).getStatus());
					} else if(select3 == 2){
						theRole.rejectManuscript(tempList.get(select2 - 1));
					}
				}
				
			////////////////////////////////// OPTION 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 3){
				System.out.println("\n---Pick a subprogram chair---");
				List<SubprogramChair> tempArr = theRole.getAllSubprogramChair(myUsers);
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
				int select2 = getSelect(theConsole);
				if(select2 == tempArr.size() + 1){
					System.out.println("\n Back \n");
				} else {
					System.out.println("You selected : " + tempArr.get(select2 - 1).getMyUsername());
					System.out.println("--Showing Related Manuscripts--");
					List<Manuscript> tempList = theRole.showAllManuscriptAssignedToSpc(tempArr.get(select2 - 1));
					for(int i = 0; i < tempList.size(); i++){
						System.out.println(i + 1 + ". " + tempList.get(i).getTitle());
					}
					System.out.println("--end of manuscript list--");
				}
				
			///////////////////////////////// OPTION 4 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			} else if (select == 4){
				
			} else if (select == 5){
				System.out.println();
				logout = true;
			}
		} while(!logout);
		
		return false;
	}
	
	/**
	 * All the subprogram chair options.
	 * @param Scanner The input scanner.
	 */
	public boolean subprogramChairBranch(Scanner theConsole, SubprogramChair theRole){
		System.out.println("\n---------------\n\nWhat Do you want to do?");
		System.out.println("1. Submit a Manuscript");
		System.out.println("2. Select a Different Conference");
		System.out.println("3. Logout");
		
		int select = getSelect(theConsole);
		
		
		
		return false;
		
	}
	
	/**
	 * All the reviewer options.
	 * @param Scanner The input scanner.
	 */
	public boolean reviewerBranch(Scanner theConsole, Reviewer theRole){
		return false;
	}
	
	/**
	 * No Role.
	 * @param Scanner The input scanner.
	 */
	public boolean noRole(Scanner theConsole, User theUser){
		System.out.println("\n---------------\n\nWhat Do you want to do?");
		System.out.println("1. Submit a Manuscript");
		System.out.println("2. Select a Different Conference");
		System.out.println("3. Logout");
		
		int select = getSelect(theConsole);
		if(select == 1){
			String manuscriptFile;
			do {
				System.out.println("Please enter the File Path for the Manuscript");
				manuscriptFile = theConsole.nextLine();				
				System.out.println("The Filed you entered is: " + manuscriptFile + "\nIs This correct? Press 1 for yes, or 0 to try again");
				select = getSelect(theConsole);				
			} while (select == 0);			
			if(select == 1) {
				System.out.println("Please enter the Title of this Manuscript");
				String manuscriptName = theConsole.nextLine();				
				
				File file = new File(manuscriptFile);				
				Manuscript newManu = new Manuscript(theUser.getName(), theUser.getConference().getConferenceID(), manuscriptName, file.getAbsolutePath());
				theUser.submitManuscript(newManu, myMasterList);
				System.out.println("Manuscript Submited!");
			}
			System.out.println();
		} else if (select == 2){
			selectConference(theConsole, theUser);
		} else if (select == 3){
			return true;
		} 
		return false;
	}
	
	/**
	 * Get select.
	 * @param Scanner The scanner.
	 * @return int The selected.
	 */
	public int getSelect(Scanner theConsole){
		System.out.print("Select: ");
		return getInt(theConsole);
	}
	
	
	
	

}