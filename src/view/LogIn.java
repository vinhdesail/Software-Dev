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
		
		initializeFields();
		//restorePreviousState();
		Scanner console = new Scanner(System.in);
		System.out.println("Log-In");
		
		boolean exit = false;

		// MAIN PROGRAM LOOP
		do{
			User currentUser = login(console);
			
			boolean backToLogin = false;
			if(currentUser == null){
				exit = true;
				backToLogin = true;
			} else {
				// SELECT A CONFERENCE
				selectConference(console, currentUser);
				currentUser.autoSetRole();
			}
			
			boolean loopAgain =  false;
			do {
				if(!backToLogin){
					Role currentRole = currentUser.getCurrentRole();
					
					if(currentRole == null){
						AuthorGUI authorUI = new AuthorGUI(console, currentUser, myMasterList, false);
						loopAgain = authorUI.loop();
					} else if(currentRole instanceof Author){
						AuthorGUI authorUI = new AuthorGUI(console, currentUser, myMasterList, true);
						loopAgain = authorUI.loop();
					} else if(currentRole instanceof ProgramChair){
						ProgramChairGUI pcUI = new ProgramChairGUI(console, currentUser, myUsers, myMasterList);
						loopAgain = pcUI.loop();
					} else if(currentRole instanceof SubprogramChair){
						SubprogramChairGUI spcUI = new SubprogramChairGUI(console, currentUser, myUsers, myMasterList);
						loopAgain = spcUI.loop();
						//returnToNoRole = subprogramChairBranch(console, (SubprogramChair) currentRole);
					} else if(currentRole instanceof Reviewer){
						ReviewerGUI reviewUI = new ReviewerGUI(console, currentUser, myMasterList);
						loopAgain = reviewUI.loop();
					}
				}//end inner if
			} while (loopAgain);
		}while(!exit);
		
		console.close();
		logout();
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
		
		User toReturn = null;
		System.out.print("Username (Type \"EXIT\" to quit): ");
		String username = theConsole.next();
		while(!username.equalsIgnoreCase("EXIT") && !myUsers.containsKey(username)){
			System.out.println("Sorry, there was no user with that name!");
			System.out.print("Please Enter another one (\"EXIT\" to quit): ");
			username = theConsole.next();
		}
		if(!username.equalsIgnoreCase("EXIT")){
			toReturn = myUsers.get(username);
		}
		return toReturn;
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
		sally.switchConference(testConference);
		sally.submitManuscript(new Manuscript(testProgramChairName, testConferenceName, "Boss idea to Increase Sale", "C:/dc.txt"), myMasterList);
		
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
		tempManu.setStatus(1);
		
		
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
		myUsers.put("Pat", pat);
		
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
		int select = HelperGUI.getSelect(theConsole);
		theUser.switchConference(myConferences.get(select - 1));
		System.out.println("You selected: " + myConferences.get(select - 1).getConferenceID());
		
	}
	
}