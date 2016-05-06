/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Manuscript;
import model.Role;

/**
 * @author Vinh Vien
 *
 */
public class LogIn {
	
	private List<Manuscript> myMasterList;
	
	
	private Map<String, List<Role>> myUsers;
	
	/**
	 * The constructor.
	 */
	public LogIn(){
		
		initializeFields();
		//restorePreviousState();
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("Log-In");
		
		console.close();
	}
	
	/**
	 * Get Serializable objects from memory.
	 */
	public void restorePreviousState(){
		
	}
	
	/**
	 * Login prompt.
	 * @param Scanner The console. 
	 * @return List<Role> The list of roles related to the user.
	 */
	public List<Role> login(Scanner theConsole){
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
		myMasterList = new ArrayList<Manuscript>();
		myUsers = new HashMap<>();
		
		
		Manuscript temp = new Manuscript("Bob", "Conference 10", "How To Increase Sales", 
				"BLAH BLAH BLAH BLAH BLAH BOB IS AWESOME BLAH BLAH BLAH BLAH BLAH");
		
		
	}
	

}
