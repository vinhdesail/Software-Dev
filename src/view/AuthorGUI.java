/* 
 * TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Author;
import model.Manuscript;
import model.Review;
import model.Role;
import model.User;

/**
 * 
 * @author Vinh Vien
 * @version 2016.05.31
 */
public class AuthorGUI {
	
	/* The main console */
	private Scanner myConsole;
	
	/* The user current selected */
	private User myUser;
	
	/* The role of the User. */
	private Author myRole;
	
	/* The Master List of all submitted Manuscripts. */
	private final List<Manuscript> myMasterList;
	
	/* The helper GUI */
	private final HelperGUI myHelper;
	
	/* Represents the option for viewing all Manuscripts within the Menu */
	private static final int VIEW_ALL_MANUSCRIPT = 1;
	
	/* Represents the option for submitting Manuscripts within the Menu */
	private static final int SUBMIT_MANUSCRIPT = 2;
	
	/* Represents the option for Un-submitting Manuscripts within the Menu */
	private static final int UNSUBMIT_MANUSCRIPT = 3;
	
	/* Represents the option for editing a Manuscripts within the Menu */
	private static final int EDIT_MANUSCRIPT = 4;
	
	/* Represents the option for viewing all Reviews within the Menu */
	private static final int VIEW_ALL_REVIEWS = 5;
	
	/* Represents the option for logging out within the Menu */
	private static final int LOGOUT = 0;
	
	/* Represents the option for returning to the previous menu */
	private static final int BACK = 0;
	
	/* Represents the option for switching roles within the Menu */
	private static final int SWITCH_ROLE = -1;
	
	/* Represents an Offset for various Checks */
	private static final int OFFSET = 1;
	
	/* Represents the fact that there is at least one instance within certain lists */
	private static final int AT_LEAST_ONE_INSTANCE = 1;
	
	/* Represents the Acceptance Status of "Accepted" */
	private static final int ACCEPTED = 1;
	
	/* Represents whether or not the User is an Author */
	private boolean myIsAuthor;
	
	/**
	 * @param theConsole - The main console.
	 * @param theUser - User that is using the program.
	 * @param theMasterList - The Master List of all submitted Manuscripts
	 * @param theIsAuthor - boolean indicating whether or not theUser is an author
	 * @throws IllegalArgumentException if any parameter is null
	 * @throws InputMismatchException if role is not currently correct.
	 */
	public AuthorGUI(Scanner theConsole, User theUser, List<Manuscript> theMasterList, boolean theIsAuthor){
		if(theConsole == null || theUser == null || theMasterList == null){
			throw new IllegalArgumentException("Cannot accept null");
		}
		myConsole = theConsole;
		myUser = theUser;
		myMasterList = theMasterList;
		myIsAuthor = theIsAuthor;
		if(myIsAuthor){
			if(!(myUser.getCurrentRole() instanceof Author)){
				throw new InputMismatchException();
			}
			myRole = (Author)myUser.getCurrentRole();
			myHelper = new HelperGUI(myUser.getName(), myRole.getRole(), myUser.getConference().getConferenceID(), "Author Menu");
		} else {
			myRole = null;
			myHelper = new HelperGUI(myUser.getName(), "Submit a paper to become an Author", myUser.getConference().getConferenceID(), "User Menu");
		}
		myHelper.parseDate(myUser.getConference().getManuscriptDueDate());
	}
	
	/**
	 * The main control method for the AuthorGUI. 
	 * @return boolean True if the User wants to logout, false otherwise.
	 */
	public boolean loop(){
		boolean logout = false;
		boolean switchRole = false;
		do{
			myHelper.setMyActivity("Author Menu");
			System.out.println(myHelper);
			
			System.out.println("\nWhat Do you want to do?");
			System.out.println("1. View my manuscipts");
			System.out.println("2. Submit A Manuscript");
			System.out.println("3. Unsubmit A Manuscript");
			System.out.println("4. Edit a Manuscript");
			System.out.println("5. View All my Reviews");
			System.out.println("0. Logout");
			System.out.println("-1. Switch Role");
			int select = HelperGUI.getSelect(myConsole);
			
			switch (select){
			case VIEW_ALL_MANUSCRIPT:
				optionToViewYourManuscript();
				break;
			case SUBMIT_MANUSCRIPT:
				optionToSubmitAManuscript();
				HelperGUI.stopForASecond(myConsole, false);
				break;
			case UNSUBMIT_MANUSCRIPT:
				optionToUnsubmitAManuscript();
				HelperGUI.stopForASecond(myConsole, true);
				break;
			case EDIT_MANUSCRIPT:
				optionToEditAManuscript();
				HelperGUI.stopForASecond(myConsole, true);
				break;
			case VIEW_ALL_REVIEWS:
				optionToViewAllReviews();
				HelperGUI.stopForASecond(myConsole, true);
				break;
			case LOGOUT:
				System.out.println();
				logout = true;
				break;
			case SWITCH_ROLE:
				if(myUser.hasRole()){
					switchRole = myHelper.selectRole(myConsole, myUser);
					myHelper.setMyRoleName("Author");
					if(myUser.getCurrentRole() instanceof Author){
						myRole = (Author) myUser.getCurrentRole();
					}
				} else {
					System.out.println("Submit a manuscript to get a role - Author");
				}
				break;
			}
			
		} while(!logout && !switchRole);
		return switchRole;
	}
	
	/**
	 * Allows the User to pick a manuscript and view it.
	 */
	private void optionToViewYourManuscript() {
		
		myHelper.setMyActivity("Viewing my Manuscripts");
		//System.out.println(myHelper);
		
		if(myIsAuthor){
			boolean back = false;
			while(!back){
				System.out.println(myHelper);
				List<Manuscript> listOfManuscript = myRole.showAllMyManuscripts();
				System.out.println("Select a number for more Detail");
				HelperGUI.displayManuscripts(listOfManuscript, true);
				
				int userSelect = HelperGUI.getSelect(myConsole);
		
				if(userSelect == BACK){
					System.out.println(HelperGUI.BACK);
					back = true;
				} else {
					System.out.println(listOfManuscript.get(userSelect - OFFSET));
					HelperGUI.stopForASecond(myConsole, true);
				}
			}
			
		} else {
			System.out.println("You have no manuscript");
			System.out.println("Submit a paper to become an Author");
			HelperGUI.stopForASecond(myConsole, true);
		}
	}
	
	/**
	 * Allows the User to submit a manuscript.
	 */
	private void optionToSubmitAManuscript() {
		
		myHelper.setMyActivity("Submiting a Manuscript");
		System.out.println(myHelper);
		if(myIsAuthor){
			List<Manuscript> listOfManuscript = myRole.showAllMyManuscripts();
			System.out.println("Showing all Manuscripts");
			HelperGUI.displayManuscripts(listOfManuscript, false);
			System.out.println();
			HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
		} else {
			HelperGUI.submitManuscript(myConsole, myUser, myMasterList);
			
			assignRoleToAuthor(myUser);
		}
		
	}

	/**
	 * Assigns the Role of Author to a non-Author.
	 */
	public void assignRoleToAuthor(User theUser){
		
		List<Role> listOfRole = theUser.getMyConferenceRoles();
		Author toAssign = null;
		for(Role iterRole : listOfRole) {
			if(iterRole instanceof Author){
				toAssign = (Author) iterRole;
			}
		}
		
		if(toAssign != null){
			theUser.switchRole(toAssign);
			myRole = toAssign;
			myHelper.setMyRoleName(myRole.getRole());
			myIsAuthor = true;
		}
	}
	
	/**
	 * Allows the User to unsubmit a Manuscript
	 */
	private void optionToUnsubmitAManuscript() {
		myHelper.setMyActivity("Unsubmitting a Manuscript");
		System.out.println(myHelper);
		
		List<Manuscript> listOfManuscript = myRole.showAllMyManuscripts();
		HelperGUI.displayManuscripts(listOfManuscript, true);
		int selectedManu = HelperGUI.getSelect(myConsole);
		
		if(selectedManu == BACK){
			System.out.println(HelperGUI.BACK);
		} else {
			myRole.deleteManuscript(myMasterList, listOfManuscript.get(selectedManu - OFFSET));
			System.out.println("Manuscript Unsubmitted Successful\n--Displaying All Your Manuscript--");
			listOfManuscript = myRole.showAllMyManuscripts();
			HelperGUI.displayManuscripts(listOfManuscript, false);
		}
	}
	
	/**
	 * Allows the User to edit a submitted Manuscript
	 */
	private void optionToEditAManuscript() {
		
		myHelper.setMyActivity("Editing a Manuscript");
		System.out.println(myHelper);
		
		List<Manuscript> listOfManuscript = myRole.showAllMyManuscripts();
		System.out.println("Select a manuscript to edit");
		HelperGUI.displayManuscripts(listOfManuscript, true);
		int selectedManu = HelperGUI.getSelect(myConsole);
		
		if(selectedManu == BACK){
			System.out.println(HelperGUI.BACK);
		} else {
			String title = askForTitle();
			if(!title.equalsIgnoreCase("EXIT")){
				myRole.editManuscript(myMasterList, listOfManuscript.get(selectedManu - OFFSET), title);
				System.out.println("SUCCESS!!!\n--Displaying All Your Manuscript--");
				listOfManuscript = myRole.showAllMyManuscripts();
				HelperGUI.displayManuscripts(listOfManuscript, false);
			}
		}
	}
	
	/**
	 * Gets the title of a Manuscript as input from the User and returns that title as a String
	 * @return String title of the Manuscript
	 */
	private String askForTitle(){
		myConsole.nextLine();
		System.out.println("Please enter the Title of this Manuscript or \"EXIT\" to quit");
		String manuscriptName = myConsole.nextLine();
		int correctTitle = 0;
		while(!manuscriptName.equalsIgnoreCase("EXIT") && correctTitle != AT_LEAST_ONE_INSTANCE){
			System.out.println("Is this the name you want? (1 for yes, any integer for no): " + manuscriptName);
			correctTitle = HelperGUI.getSelect(myConsole);
			if(correctTitle != AT_LEAST_ONE_INSTANCE){
				myConsole.nextLine();
				System.out.println("Please enter the Title of this Manuscript or \"EXIT\" to quit");
				manuscriptName = myConsole.nextLine();
			}
		}
		
		return manuscriptName;
	}
	
	/**
	 * Allows the User to view all Reviews associated with a Manuscript
	 */
	private void optionToViewAllReviews() {
		
		myHelper.setMyActivity("Editing a Manuscript");
		System.out.println(myHelper);
		
		List<Review> listOfReview = myRole.getReviews();
		HelperGUI.displayReviews(listOfReview, true);
		int selectedReview = HelperGUI.getInt(myConsole);
		
		if(selectedReview == BACK){
			System.out.println(HelperGUI.BACK);
		} else {
			Manuscript relatedManuscript = this.getManuConnectedWithReview(listOfReview.get(selectedReview - OFFSET));
			System.out.println(relatedManuscript.getTitle());
			System.out.print("Status: ");
			if(relatedManuscript.getStatus() == ACCEPTED){
				System.out.print("Accepted\n");
				//System.out.println("Text: " + listOfReview.get(selectedReview - OFFSET).getReviewFilePath());
			} else {
				System.out.print("Rejected\n");
			}
			//System.out.println(listOfReview.get(selectedReview - 1));
		}
		
	}
	
	/**
	 * Method that gets the Manuscript connected to a particular Review.
	 * @param theReview - Review on a Manuscript
	 * @return Manuscript associated with theReview, or null if none exists
	 */
	public Manuscript getManuConnectedWithReview(Review theReview){
		Manuscript toReturn = null;
		
		List<Manuscript> listOfManuscript = myRole.showAllMyManuscripts();
		for(int i = 0; i < listOfManuscript.size(); i++){
			if(listOfManuscript.get(i).getTitle().equals(theReview.getManuscriptTitle())){
				toReturn = listOfManuscript.get(i);
			}
		}
		return toReturn;
	}
}
