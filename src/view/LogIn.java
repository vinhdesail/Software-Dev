/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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

import model.Author;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;
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
	
	private static final int OFFSET = 1;
	
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
			temp.append(i + OFFSET);
			temp.append(". ");
			temp.append(myConferences.get(i).getConferenceID());
			temp.append('\n');
		}
		
		System.out.println("What conference?");
		System.out.println(temp.toString());
		int select = HelperGUI.getSelect(theConsole);
		theUser.switchConference(myConferences.get(select - OFFSET));
		System.out.println("You selected: " + myConferences.get(select - OFFSET).getConferenceID());
		
	}
	
}