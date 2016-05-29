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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.Recommendation;
import model.Review;
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
		
		//TODO
		
		//initializeFields();
		restorePreviousState();
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("Log-In");
		
		boolean exit = false;
		
		
		// MAIN PROGRAM LOOP
		do{
			
			User currentUser = login(console);
			
			// SELECT A CONFERENCE
			selectConference(console, currentUser);
			
			boolean logout =  false;
			boolean backToLogin = false;
			boolean returnToNoRole = false;
			do{
				
				Role currentRole = currentUser.getCurrentRole();
				
				if(currentRole == null){
					logout = noRole(console, currentUser);
				} else if(currentRole instanceof Author){
					returnToNoRole = authorBranch(console, (Author) currentRole, currentUser);
				} else if(currentRole instanceof ProgramChair){
					ProgramChairGUI tempGui = new ProgramChairGUI(console, currentUser, myUsers, myMasterList);
					returnToNoRole = tempGui.loop();
				} else if(currentRole instanceof SubprogramChair){
					returnToNoRole = subprogramChairBranch(console, (SubprogramChair) currentRole);
				} else if(currentRole instanceof Reviewer){
					returnToNoRole = reviewerBranch(console, (Reviewer) currentRole);
				}
				
				if(returnToNoRole){
					currentUser.returnToNoRole();
					returnToNoRole = false;
				}
				
				if(logout){
					// Ask to make sure if they want to log out.
					System.out.println("Are you sure about logout? (1 for yes, any integer for no): ");
					int tempLogout = getInt(console);
					if(tempLogout == 1){
						backToLogin = true;
					}
				}
				
			}while(!backToLogin);
			
			System.out.print("Exit Program? (yes or no :go back to login): ");
			String answer = console.next();
			if(answer.toLowerCase().charAt(0) == 'y'){
				exit = true;
			}
			
		}while(!exit);
		
		
		console.close();
		
		logout();
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
			myConferences = (List<Conference>) input.readObject();
			
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
	 * Test initialization for debugging.
	 */
	private void initializeFields(){
		myMasterList = new ArrayList<>();
		myUsers = new HashMap<>();
		myConferences = new ArrayList<>();
		
		//CONFERENCE AND PROGRAM CHAIRS
		String testProgramChairName = "Sally";
		String testConferenceName = "Conference 1";
		String testDateCon = "02-11-2016";
		String testDateMan = "02-07-2016";
		String testDateRev = "02-08-2016";
		String testDateRec = "02-09-2016";
		String testDateDec = "02-10-2016";
		Conference testConference = new Conference(testConferenceName, testProgramChairName, testDateCon, 
				testDateMan, testDateRev, testDateRec, testDateDec);
		myConferences.add(testConference);
		
		User sally = new User(testProgramChairName);
		ProgramChair pc = new ProgramChair(testConference, testProgramChairName);
		sally.addRole(pc);
		myUsers.put(testProgramChairName, sally);
		
		/*
		String testProgramChairName2 = "Sherry";
		String testConferenceName2 = "Conference 2";
		String testDateCon2 = "05-11-2016";
		String testDateMan2 = "05-07-2016;
		String testDateRev2 = "05-08-2016";
		String testDateRec2 = "06-09-2016";
		String testDateDec2 = "06-10-2016";
		Conference testConference2 = new Conference(testConferenceName2, testProgramChairName2, testDateCon2, 
				testDateMan2, testDateRev2, testDateRec2, testDateDec2);
		myConferences.add(testConference2);
		
		User sherry = new User();
		ProgramChair pc2 = new ProgramChair(testConference2, testProgramChairName2);
		sherry.addRole(pc2);
		myUsers.put(testProgramChairName2, sherry);
		*/
		
		//Test Author
		String testAuthorName = "Bob";
		User bob = new User(testAuthorName);
		bob.switchConference(testConference);

		Manuscript tempManu = new Manuscript(testAuthorName, testConferenceName, "How To Increase Sales", 
				"C:/Manuscript1.txt");
		Manuscript tempManu2 = new Manuscript(testAuthorName, testConferenceName, "How To Increase Sales 2.0", 
				"C:/Manuscript2.txt");
		
		bob.submitManuscript(tempManu, myMasterList);
		bob.submitManuscript(tempManu2, myMasterList);
		myUsers.put(testAuthorName, bob);
		
		// another author
		User bobby = new User("Bobby");
		bobby.switchConference(testConference);
		Manuscript tempManu3 = new Manuscript("Bobby", testConferenceName, "How To Make more Money", 
				"C:/Bobby_Manu.txt");
		Manuscript tempManu4 = new Manuscript("Bobby", testConferenceName, "How To Make more Money 2.0", 
				"C:/Bobby_Manu_B.txt");
		bobby.submitManuscript(tempManu3, myMasterList);
		bobby.submitManuscript(tempManu4, myMasterList);
		myUsers.put("Bobby", bobby);
		
		
		
		//ADD some review
		Review review = new Review("Pat", "How To Increase Sales", "I really like his points");
		tempManu.addReview(review);
		
		
		//Test Subprogram Chair
		User tom = new User("Tom");
		tom.switchConference(testConference);
		SubprogramChair subP = new SubprogramChair("Tom", testConference);
		subP.assignManuscripts(tempManu2);
		tom.addRole(subP);
		myUsers.put("Tom", tom);
		
		User john = new User("John");
		john.switchConference(testConference);
		SubprogramChair subP2 = new SubprogramChair("John", testConference);
		subP2.assignManuscripts(tempManu);
		john.addRole(subP2);
		myUsers.put("John", john);
		
		
		//Test just a user
		User tim = new User("Tim");
		tim.switchConference(testConference);
		myUsers.put("Tim", tim);
		User kim = new User("Kim");
		kim.switchConference(testConference);
		myUsers.put("Kim", kim);
		
		
		//Test a reviewers
		User jerry = new User("Jerry");
		jerry.switchConference(testConference);
		Reviewer rev = new Reviewer("Jerry", testConference);
		rev.assignReview(tempManu4);
		jerry.addRole(rev);
		
		myUsers.put("Jerry", jerry);
		
		//Test a reviewers
		User harry = new User("Harry");
		harry.switchConference(testConference);
		Reviewer rev2 = new Reviewer("Harry", testConference);
		harry.addRole(rev2);
		myUsers.put("Harry", harry);
		
		//Test a reviewers
		User pat = new User("Pat");
		pat.switchConference(testConference);
		Reviewer rev3 = new Reviewer("Pat", testConference);
		pat.addRole(rev3);
		myUsers.put("Harry", pat);
		
		
		
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
			output.writeObject(myConferences);
			
			output.flush();
			output.close();
		} catch(IOException e){
			System.out.println("ERROR IN SERIALIZING!!!!!!");
		}
	}
	
	/**
	 * The method to select a conference.
	 * @param theConsole The console input.
	 * @param theUser The user pass in.
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
	 * The method to select Role.
	 * @param theConsole The console input.
	 * @param theUser The user pass in.
	 * @deprecated
	 */
	public void selectRole(final Scanner theConsole, final User theUser){
		
		Role toReturn = null;
		boolean validRole = false;
		StringBuilder tempString = new StringBuilder();
		List<Role> tempRole = theUser.getAllRoles();
		
		// IF USER ACTUALLY HAVE ROLE
		if(theUser.hasRole()){
			
			tempString.append("\n--------Showing all Role for this Conference--------\n");
			for(int i = 0; i < tempRole.size(); i++){
				tempString.append(i + 1);
				tempString.append(". ");
				tempString.append(tempRole.get(i).getRole());
				tempString.append("\n");
			}
			tempString.append("--------End of List--------\n");
			
			// ADD IN BACK OPTION
			tempString.append(tempRole.size() + 1);
			tempString.append(". BACK/EXIT\n");
			
			// Print input and try to get selected option
			while(!validRole){
				
				System.out.println(tempString);
				int selected = this.getSelect(theConsole);
				
				if(selected == (tempRole.size() + 1)){
					validRole = true;
				} else {
					try{
						toReturn = tempRole.get(selected - 1);
						theUser.switchRole(toReturn);
						validRole = true;
						System.out.println("Role Change Successful");
					} catch (InputMismatchException e){
						System.out.println("FAILED TO SWITCH ROLES! TRY AGAIN!");
					} catch (IndexOutOfBoundsException e){
						System.out.println("DID NOT SWITCH ROLE! TYPE A NUMBER IN RANGE PLEASE!");
					}//end try
				}//end if
			}//end while
		} else {
			System.out.println("You have no available roles, please submit a paper to become an Author.");
		}
		
		//return toReturn;
	}
	
	
	/**
	 * All the Author options.
	 * @param Scanner The input scanner.
	 */
	public boolean authorBranch(Scanner theConsole, Author theRole , User theUser){
		
		
		
		
		int select = getSelect(theConsole);
		if(select == 1) {
			String manuscriptFile;
			do {
				theConsole.nextLine();
				System.out.println("Please enter the File Path for the Manuscript");
				manuscriptFile = theConsole.nextLine();					
				System.out.println("The Filed you entered is: " + manuscriptFile + "\nIs This correct? Press 1 for yes, or 0 to try again");
				select = getSelect(theConsole);				
			} while (select == 0);			
			if(select == 1) {
				theConsole.nextLine();
				System.out.println("Please enter the Title of this Manuscript");
				String manuscriptName = theConsole.nextLine();				
				
				File file = new File(manuscriptFile);				
				Manuscript newManu = new Manuscript(theRole.getMyUsername(), theUser.getConference().getConferenceID() , manuscriptName, file.getAbsolutePath());
				theUser.submitManuscript(newManu, myMasterList);
				System.out.println("Manuscript Submited!");
			}
		} else if(select == 2) {
			System.out.println("Please Select the Manuscript you wish to unsubmit");
			List<Manuscript> tempManuscriptList = new ArrayList<>();
			for(int i = 0; i < myMasterList.size(); i++) {
				if(myMasterList.get(i).getAuthor().equals(theRole.getMyUsername())) {
					tempManuscriptList.add(myMasterList.get(i));
					System.out.println((i + 1) + ". " + myMasterList.get(i));
				}
			}
			if(tempManuscriptList.isEmpty()){
				System.out.println("There is no existing manuscript left for delete!\n\n");
			} else {
				select = getSelect(theConsole);
				
				theRole.deleteManuscript(myMasterList, myMasterList.get(select - 1));	
				
				System.out.println("Success!!\n\n\n");
			}
		} else if(select == 3) {
			System.out.println("Please Select the Manuscript you wish to edit");
			List<Manuscript> tempManuscriptList = new ArrayList<>();
			for(int i = 0; i < myMasterList.size(); i++) {
				if(myMasterList.get(i).getAuthor().equals(theRole.getMyUsername())) {
					tempManuscriptList.add(myMasterList.get(i));
					System.out.println((i + 1) + ". " + myMasterList.get(i));
				}
			}
			select = getSelect(theConsole);
			Manuscript tempManuscript = new Manuscript(theRole.getMyUsername(), 
					 theUser.getConference().getConferenceID(), myMasterList.get(select - 1).getTitle(), 
					 myMasterList.get(select - 1).getText());
			
			theConsole.nextLine();
			System.out.println("Please enter the new Title for the Manuscript");
			String manuscriptTitle = theConsole.nextLine();	
			Manuscript newManu = new Manuscript(theRole.getMyUsername(), theUser.getConference().getConferenceID(), manuscriptTitle, tempManuscript.getText());
			theRole.editManuscript(myMasterList,tempManuscript ,newManu );		
		} else if(select == 4) {
			System.out.println("Please Select the Manuscript you wish to see the reviews for");
			List<Manuscript> tempManuscriptList = new ArrayList<>();
			for(int i = 0; i < myMasterList.size(); i++) {
				if(myMasterList.get(i).getAuthor().equals(theRole.getMyUsername())) {
					tempManuscriptList.add(myMasterList.get(i));
					System.out.println((i + 1) + ". " + myMasterList.get(i));
				}
			}
			select = getSelect(theConsole);
			
			Manuscript manToReview =  myMasterList.get(select - 1);
			
			List<Review> reviews = theRole.getReviews();
			if(!reviews.isEmpty()) {
				System.out.println("Title:  " + manToReview.getTitle()+"\n\n");
				for(int i = 0; i < reviews.size(); i++) {
					System.out.println("Review:  " + reviews.get(i).getReviewText()+"\n\n");
				}
				
			} else {
				System.out.println("No Reviews have been made for this Manuscript!\n\n");
			}
		} else if(select == 5) {
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * All the subprogram chair options.
	 * @param Scanner The input scanner.
	 */
	public boolean subprogramChairBranch(Scanner theConsole, SubprogramChair theRole){
		System.out.println("\n---------------\n\nWhat Do you want to do?");
		System.out.println("1. Assign A Reviewer A Manuscript");
		System.out.println("2. Submit a Recommendation");
		System.out.println("4. Logout");
		
		int select = getSelect(theConsole);
		
		if(select == 1){		
				System.out.println("Please Select a Manuscript to be assigned");
				List<Manuscript> tempList = theRole.showAllAssignedManuscripts();
				for(int i = 0; i < tempList.size(); i++){
					System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
				}
				System.out.println("--end of manuscript list--");
				select = getSelect(theConsole);	
				Manuscript tempManu = tempList.get(select-1);
				
				
				List<Reviewer> tempReviewerList = new ArrayList<>();
				System.out.println("Please Select a reviewer to be assigned");
				int p = 1;
				for(String key: myUsers.keySet()) {
					for(int i = 0; i < myUsers.get(key).getListOfAllRoles().size(); i++) {
						if(myUsers.get(key).getListOfAllRoles().get(i) instanceof Reviewer) {
							System.out.println((p++) + ". " + ((Reviewer)myUsers.get(key).getListOfAllRoles().get(i)).getMyUsername());
							tempReviewerList.add((Reviewer)myUsers.get(key).getListOfAllRoles().get(i));
						}
					}
				}
				select = getSelect(theConsole);	
				Reviewer tempReviewer = tempReviewerList.get(select-1);				
				theRole.AssignReviewer(tempReviewer, tempManu);
				
				System.out.println("Success!\n\n\n\n\n");
		} else if(select == 2) { 
			System.out.println("Please Select a Manuscript for your recommendation");
			
			List<Manuscript> tempList = theRole.showAllAssignedManuscripts();
			for(int i  = 0; i < tempList.size();i++) {
				System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
			}
			System.out.println("--end of manuscript list--");
			select = getSelect(theConsole);	
			Manuscript tempManu = tempList.get(select-1);
			theConsole.nextLine();
			System.out.print("Write a recommendation: ");
			String recText = theConsole.nextLine();
			Recommendation rec = new Recommendation(theRole.getMyUsername(), tempManu.getTitle(), recText);
			tempManu.setRecommendation(rec);
			System.out.println("Success!\n\n\n\n\n");
		} else if(select == 4) {
			return true;
		}
		
		return false;
		
	}

	
	/**
	 * All the reviewer options.
	 * @param Scanner The input scanner.
	 */
	public boolean reviewerBranch(Scanner theConsole, Reviewer theRole){
		System.out.println("\n---------------\n\nWhat Do you want to do?");
		System.out.println("1. Assign A Review to a Manuscript");
		System.out.println("2. Logout");
		
		int select = getSelect(theConsole);
		if(select == 1) {
//			if() {
//				
//			}
			System.out.println("Please Select Which Manuscript you are reviewing.");
			List<Manuscript> tempList = theRole.viewMyPapers();
			for(int i  = 0; i < tempList.size();i++) {
				System.out.println((i + 1) + ". " + tempList.get(i).getTitle());
			}
			System.out.println("--end of manuscript list--");
			select = getSelect(theConsole);	
			Manuscript tempManu = tempList.get(select-1);
			
			theConsole.nextLine();
			System.out.println("Please enter the Review for this Manuscript");
			String reviewText = theConsole.nextLine();
			
			theRole.submitReview(tempManu, reviewText);
			System.out.println("Success!!");
		} else if(select == 2) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * No Role.
	 * @param theConsole The input scanner.
	 * @param theUser The current User.
	 * @return boolean If the user wants to logout or not.
	 */
	public boolean noRole(Scanner theConsole, User theUser){
		
		boolean toReturn = false;
		
		HelperGUI helper = new HelperGUI(theUser.getName(), "No Role", theUser.getConference().getConferenceID(), "Main Menu for No Role");
		System.out.println(helper);
		
		System.out.println("\nWhat Do you want to do?");
		System.out.println("1. Submit a Manuscript");
		System.out.println("2. Select a Different Conference");
		System.out.println("3. Select a Role");
		System.out.println("0. Logout");
		
		int select = getSelect(theConsole);
		
		// CHOSE TO SUBMIT MANUSCRIPT
		if(select == 1){
			
			String manuscriptFile;
			boolean quit = false;
			do {
				theConsole.nextLine();
				System.out.println("Please enter the File Path for the Manuscript (Type \"EXIT\" to Exit)");
				manuscriptFile = theConsole.nextLine();		
				
				if(manuscriptFile.equals("EXIT")){
					quit = true;
				} else {
					System.out.println("The Filed you entered is: " + manuscriptFile + "\nIs This correct? Press 1 for yes, or anything to try again");
					select = getSelect(theConsole);	
				}
			} while (select != 1 && !quit);			
			
			if(!quit){
				// ASK FOR TITLE
				theConsole.nextLine();
				System.out.println("Please enter the Title of this Manuscript");
				String manuscriptName = theConsole.nextLine();				
				
				File file = new File(manuscriptFile);				
				Manuscript newManu = new Manuscript(theUser.getName(), theUser.getConference().getConferenceID(), manuscriptName, manuscriptFile);
				theUser.submitManuscript(newManu, myMasterList);
				System.out.println("!!!SUCCESS!!!");
				System.out.println("Manuscript: " + manuscriptName + " Submited to conference: " + theUser.getConference().getConferenceID() + " !\n");
			}
			
		} else if (select == 2){
			selectConference(theConsole, theUser);
		
		} else if(select == 3){
			helper.selectRole(theConsole, theUser);
		
		} else if (select == 0){
			toReturn = true;
		} 
		return toReturn;
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