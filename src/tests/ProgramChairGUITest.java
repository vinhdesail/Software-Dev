package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.SubprogramChair;
import model.User;
import view.ProgramChairGUI;

/**
 * Testing the logic inside program chair gui.
 * @author Vinh Vien
 * @version 2016.5.27
 */
public class ProgramChairGUITest {
	
	private List<Manuscript> myManuscriptList;
	private Manuscript myFirstManuscript;
	private Manuscript mySecondManuscript;
	
	private List<SubprogramChair> myListOfSubprogramChair;
	private SubprogramChair mySubprogramChairWithOnePaper;
	private SubprogramChair mySubprogramChairWithNoPaper;
	
	private String programChairID = "Sally";
	
	private Conference myConference;
	private Calendar conferenceDate = new GregorianCalendar(2016,10,17);
	private Calendar manuscriptDueDate = new GregorianCalendar(2016,9,1);
	private Calendar reviewDueDate = new GregorianCalendar(2016,9,19);
	private Calendar recommendationDueDate = new GregorianCalendar(2016,10,1);
	private Calendar decisionDueDate = new GregorianCalendar(2016,10,1);	
	
	private String CONFERENCE_NAME = "Work Environment";
	
	private ProgramChairGUI myGUI;
	private SubprogramChair mySubprogramChairWithFirstPaper;
	
	private User noConferenceUser;
	private User wrongRoleUser;
	
	/**
	 * Create a list of subprogram chair, a couple manuscripts, and conference to test.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Made conference
		myConference = new Conference(CONFERENCE_NAME, programChairID, conferenceDate, 
				manuscriptDueDate, reviewDueDate, recommendationDueDate, decisionDueDate);
		// Make List of Manuscript
		myManuscriptList = new ArrayList<>();
		myFirstManuscript = new Manuscript("Bob", CONFERENCE_NAME, "Ways to Increase Productivity", "C:\\Document\\WayIncreaseProductivity.txt");
		mySecondManuscript = new Manuscript("Alice", CONFERENCE_NAME, "Ways to reduce laziness", "C:\\Document\\WayReduceLaziness.txt");
		
		myManuscriptList.add(myFirstManuscript);
		myManuscriptList.add(mySecondManuscript);
		// Make List of Array
		myListOfSubprogramChair = new ArrayList<>();
		mySubprogramChairWithOnePaper = new SubprogramChair("Jerry", myConference);
		mySubprogramChairWithNoPaper = new SubprogramChair("Tom", myConference);
		
		mySubprogramChairWithOnePaper.assignManuscripts(myFirstManuscript);
		
		// Assign first paper
		mySubprogramChairWithFirstPaper = new SubprogramChair("Tom", myConference);
		mySubprogramChairWithFirstPaper.assignManuscripts(myFirstManuscript);
		
		myListOfSubprogramChair.add(mySubprogramChairWithOnePaper);
		myListOfSubprogramChair.add(mySubprogramChairWithNoPaper);
		
		User sally = new User("Sally");
		ProgramChair role = new ProgramChair(myConference, "Sally");
		sally.switchConference(myConference);
		sally.addRole(role);
		sally.switchRole(role);
		
		
		myGUI = new ProgramChairGUI(new Scanner(System.in), sally, new HashMap<String, User>(), myManuscriptList);
		
		// WRONG ROLE & NO CONFERENCE USER
		wrongRoleUser = new User("Wrong Role");
		Author wrongRole = new Author("Wrong Role", myConference);
		wrongRoleUser.addRole(wrongRole);
		wrongRoleUser.switchRole(wrongRole);
		
		noConferenceUser = new User("No Conference");
		ProgramChair noConference = new ProgramChair(myConference, "No Conference");
		noConferenceUser.addRole(noConference);
		noConferenceUser.switchRole(noConference);
	}
	
	
	/**
	 * Test constructor of ProgramChairGUI should throw exceptions for null Scanner.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorExceptionNullScanner(){
		new ProgramChairGUI(null, new User("Sally"), new HashMap<String, User>(), myManuscriptList);
	}
	
	/**
	 * Test constructor of ProgramChairGUI should throw exceptions for null user.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorExceptionNullUser(){
		new ProgramChairGUI(new Scanner(System.in), null, new HashMap<String, User>(), myManuscriptList);
	}
	
	/**
	 * Test constructor of ProgramChairGUI should throw exceptions for null list of user.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorExceptionNullUserList(){
		new ProgramChairGUI(new Scanner(System.in), new User("Sally"), null, myManuscriptList);
	}
	
	/**
	 * Test constructor of ProgramChairGUI should throw exceptions for .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorExceptionNullManuscript(){
		new ProgramChairGUI(new Scanner(System.in), new User("Sally"), new HashMap<String, User>(), null);
	}
	
	/**
	 * Test constructor if myRole is null.
	 */
	@Test(expected = InputMismatchException.class)
	public void testConstructorExceptionWrongRole(){
		new ProgramChairGUI(new Scanner(System.in), wrongRoleUser, new HashMap<String, User>(), myManuscriptList);
		
	}
	
	/**
	 * Test constructor if user does not have conference. 
	 */
	@Test(expected = InputMismatchException.class)
	public void testConstructorExceptionNoConference(){
		new ProgramChairGUI(new Scanner(System.in), noConferenceUser, new HashMap<String, User>(), myManuscriptList);
	}
	
	
	/**
	 * This test if the submission was successful.
	 */
	@Test
	public void testAssigningAManuscriptToSubprogramChairWithNoScript() {
//		System.out.println(mySubprogramChairWithNoPaper);
//		System.out.println(myListOfSubprogramChair.get(2-1));
//		System.out.println(myManuscriptList.get(2 - 1));
//		System.out.println(mySubprogramChairWithFirstPaper);
//		List<Manuscript> a = mySubprogramChairWithFirstPaper.showAllAssignedManuscripts();
//		
//		System.out.println(a);
		
		myGUI.assignManuscriptToSubprogramChair(2, myListOfSubprogramChair, 1,myManuscriptList);
		
//		System.out.println(mySubprogramChairWithFirstPaper);
//		List<Manuscript> b = mySubprogramChairWithFirstPaper.showAllAssignedManuscripts();
//		System.out.println(b);
//		System.out.println(mySubprogramChairWithNoPaper);
//		List<Manuscript> c = mySubprogramChairWithNoPaper.showAllAssignedManuscripts();
//		System.out.println(c);
		assertEquals(mySubprogramChairWithFirstPaper, mySubprogramChairWithNoPaper);
	}
	
	/**
	 * This test if the submission fails which throws an exception that get catch by the GUI.
	 * Thus the manuscript did not change. 
	 */
	@Test
	public void testAssigningAManuscriptToSubprogramChairFail(){
		myGUI.assignManuscriptToSubprogramChair(1, myListOfSubprogramChair, 1,myManuscriptList);
		assertFalse(mySubprogramChairWithFirstPaper.equals(mySubprogramChairWithOnePaper));
	}

}
