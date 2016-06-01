/* TCSS 360
 * Group 4 Ever
 * 
 */
package view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.Review;
import model.Reviewer;
import model.SubprogramChair;
import model.User;

/**
 * @author Vinh Vien, Edie Megan Campbell
 * @version 2016.05.31
 */
public class DataForConference {
	
	/* Map of all registered Users, key = username, value = User object*/
	private final Map<String, User> myUsers;
	
	/* List of all Manuscripts submitted to any Conference. */
	private final List<Manuscript> myMasterList;
	
	/* List of all Conferences.*/
	private final List<Conference> myConferenceList;
	
	/**
	 * @param theUsers - Map of all 
	 * @param theMasterList
	 * @param theConferenceList
	 */
	public DataForConference(Map<String, User> theUsers, List<Manuscript> theMasterList, 
			List<Conference> theConferenceList) {
		myUsers = theUsers;
		myMasterList = theMasterList;
		myConferenceList = theConferenceList;
	}
	
	/**
	 * The first conference.
	 */
	public void firstConference(){
		//CONFERENCE AND PROGRAM CHAIRS
		String firstConferenceProgramChairName = "Sally";
		String firstConferenceName = "IEEE Conf. on Acoustics Speech";
		Calendar dateConference = new GregorianCalendar(2016, 11, 2);
		Calendar dateManuscript = new GregorianCalendar(2016, 7, 2);
		Calendar dateReview = new GregorianCalendar(2016, 8, 2);
		Calendar dateRecommendation = new GregorianCalendar(2016, 9, 2);
		Calendar dateDecision = new GregorianCalendar(2016, 10, 2);
		
		Conference firstConference = new Conference(firstConferenceName, firstConferenceProgramChairName, dateConference, 
				dateManuscript, dateReview, dateRecommendation, dateDecision);
		myConferenceList.add(firstConference);
		
		//////////////////// PROGRAM CHAIR ///////////////////////
		User sally = new User(firstConferenceProgramChairName);
		ProgramChair pc = new ProgramChair(firstConference, firstConferenceProgramChairName);
		sally.addRole(pc);
		myUsers.put(firstConferenceProgramChairName, sally);
		sally.switchConference(firstConference);
		
		//CALL OTHER METHODS
		firstConferenceUser(firstConference);
		firstConferenceAuthor(firstConference);
	}
	
	private void firstConferenceUser(Conference theConference){
		User tim = new User("Tim");
		tim.switchConference(theConference);
		myUsers.put("Tim", tim);
		User kim = new User("Kim");
		kim.switchConference(theConference);
		myUsers.put("Kim", kim);
	}
	
	private void firstConferenceAuthor(Conference theConference){
		String authorName = "Bob";
		User bob = new User(authorName);
		bob.switchConference(theConference);

		Manuscript manuscript1 = new Manuscript(authorName, theConference.getConferenceID(), "Echo of a Room", 
				"C:/EchoRoom.txt");
		Manuscript manuscript2 = new Manuscript(authorName, theConference.getConferenceID(), "Echo of a Amphitheater", 
				"C:/EchoAmphitheater.txt");
		
		bob.submitManuscript(manuscript1, myMasterList);
		bob.submitManuscript(manuscript2, myMasterList);
		myUsers.put(authorName, bob);

		User bobby = new User("Bobby");
		bobby.switchConference(theConference);
		Manuscript manuscript3 = new Manuscript("Bobby", theConference.getConferenceID(), "The magic of Sound Waves", 
				"C:/MagicSound.txt");
		Manuscript manuscript4 = new Manuscript("Bobby", theConference.getConferenceID(), "Difference frequency of voices", 
				"C:/DifferenceFrequency.txt");
		bobby.submitManuscript(manuscript3, myMasterList);
		bobby.submitManuscript(manuscript4, myMasterList);
		myUsers.put("Bobby", bobby);
		
		String robertName = "Robert";
		User robert = new User(robertName);
		robert.switchConference(theConference);
		Manuscript manuscript5 = new Manuscript(robertName, theConference.getConferenceID(), "Human Speech", 
				"C:/HumanSpeech.txt");
		Manuscript manuscript6 = new Manuscript(robertName, theConference.getConferenceID(), "Speech Processing", 
				"C:/SpeechProcessing.txt");
		robert.submitManuscript(manuscript5, myMasterList);
		robert.submitManuscript(manuscript6, myMasterList);
		myUsers.put(robertName, robert);
		
		firstConferenceSubprogramChair(theConference, manuscript1, manuscript2);
		firstConferenceReviewer(theConference, manuscript1, manuscript2);
		
	}
	
	private void firstConferenceSubprogramChair(Conference theConference, Manuscript manuscript1, Manuscript manuscript2){
		User tom = new User("Tom");
		tom.switchConference(theConference);
		SubprogramChair subP = new SubprogramChair("Tom", theConference);
		subP.assignManuscripts(manuscript2);
		tom.addRole(subP);
		myUsers.put("Tom", tom);
		
		User john = new User("John");
		john.switchConference(theConference);
		SubprogramChair subP2 = new SubprogramChair("John", theConference);
		subP2.assignManuscripts(manuscript1);
		john.addRole(subP2);
		myUsers.put("John", john);
	}
	
	private void firstConferenceReviewer(Conference theConference, Manuscript firstManu, Manuscript secondManu){
		User jerry = new User("Jerry");
		jerry.switchConference(theConference);
		Reviewer rev = new Reviewer("Jerry", theConference);
		rev.assignReview(secondManu);
		jerry.addRole(rev);
		myUsers.put("Jerry", jerry);
		
		User harry = new User("Harry");
		harry.switchConference(theConference);
		Reviewer rev2 = new Reviewer("Harry", theConference);
		harry.addRole(rev2);
		myUsers.put("Harry", harry);
		
		String pattyName = "Patty";
		User pat = new User(pattyName);
		pat.switchConference(theConference);
		Reviewer rev3 = new Reviewer(pattyName, theConference);
		rev3.assignReview(firstManu);
		pat.addRole(rev3);
		myUsers.put(pattyName, pat);
		Review review = new Review(pattyName, firstManu.getTitle(), "Needs more sources to backup statements");
		firstManu.addReview(review);
		firstManu.setStatus(1);
	}
	
	/**
	 * The second conference for the class.
	 */
	public void secondConference(){
		String secondConferenceProgramChairName = "Michael";
		String secondConferenceName = "IEEE Image Processing Conference";

		Calendar dateConference = new GregorianCalendar(2017, 11,2);
		Calendar dateManuscript = new GregorianCalendar(2017, 7, 2);
		Calendar dateReview = new GregorianCalendar(2017, 8, 2);
		Calendar dateRecommendation = new GregorianCalendar(2017, 9, 2);
		Calendar dateDecision = new GregorianCalendar(2017, 10, 2);
		Conference secondConference = new Conference(secondConferenceName, secondConferenceProgramChairName, dateConference, 
				dateManuscript, dateReview, dateRecommendation, dateDecision);
		myConferenceList.add(secondConference);
		
		////////////////////PROGRAM CHAIR ///////////////////////
		User michael = new User(secondConferenceProgramChairName);
		ProgramChair pc = new ProgramChair(secondConference, secondConferenceProgramChairName);
		michael.addRole(pc);
		myUsers.put(secondConferenceProgramChairName, michael);
		michael.switchConference(secondConference);
		
		//CALL OTHER METHODS
		secondConferenceUser(secondConference);
		secondConferenceAuthor(secondConference);
	}
	
	private void secondConferenceUser(Conference theConference){
		User kevin = new User("Kevin");
		kevin.switchConference(theConference);
		myUsers.put("Kevin", kevin);
	}
	
	private void secondConferenceAuthor(Conference theConference){
		String bobName = "Bob";
		User bob = myUsers.get(bobName);
		bob.switchConference(theConference);
		Manuscript manuscript1 = new Manuscript(bobName, theConference.getConferenceID(), "3D Imaging", 
				"C:/3DImaging.txt");
		Manuscript manuscript2 = new Manuscript(bobName, theConference.getConferenceID(), "3D processing", 
				"C:/3DProcessing.txt");
		bob.submitManuscript(manuscript1, myMasterList);
		bob.submitManuscript(manuscript2, myMasterList);

		String bobbyName = "Bobby";
		User bobby = myUsers.get(bobbyName);
		bobby.switchConference(theConference);
		Manuscript manuscript3 = new Manuscript(bobbyName, theConference.getConferenceID(), "The computer eye", 
				"C:/ComputerEye.txt");
		Manuscript manuscript4 = new Manuscript(bobbyName, theConference.getConferenceID(), "Increasing Image throughput", 
				"C:/ImageThroughput.txt");
		bobby.submitManuscript(manuscript3, myMasterList);
		bobby.submitManuscript(manuscript4, myMasterList);
		
		String sallyName = "Sally";
		User sally = myUsers.get(sallyName);
		sally.switchConference(theConference);
		Manuscript manuscript5 = new Manuscript(sallyName, theConference.getConferenceID(), "BitMap Images", 
				"C:/BitMapImages.txt");
		Manuscript manuscript6 = new Manuscript(sallyName, theConference.getConferenceID(), "Facial Processing", 
				"C:/FacialProcessing.txt");
		sally.submitManuscript(manuscript5, myMasterList);
		sally.submitManuscript(manuscript6, myMasterList);
		
		secondConferenceSubprogramChair(theConference, manuscript4, manuscript5, manuscript6);
		secondConferenceReviewer(theConference, manuscript1, manuscript2, manuscript3);
		
	}
	
	private void secondConferenceSubprogramChair(Conference theConference, Manuscript manuscript1, Manuscript manuscript2, Manuscript manuscript3){
		String timName = "Tim";
		User tim = myUsers.get(timName);
		tim.switchConference(theConference);
		SubprogramChair subP = new SubprogramChair(timName, theConference);
		subP.assignManuscripts(manuscript2);
		subP.assignManuscripts(manuscript3);
		tim.addRole(subP);
		
		User kim = myUsers.get("Kim");
		kim.switchConference(theConference);
		SubprogramChair subP2 = new SubprogramChair(kim.getName(), theConference);
		subP2.assignManuscripts(manuscript1);
		kim.addRole(subP2);

	}
	
	private void secondConferenceReviewer(Conference theConference, Manuscript firstManu, Manuscript secondManu, Manuscript thirdManu){
		User tom = myUsers.get("Tom");
		tom.switchConference(theConference);
		Reviewer rev = new Reviewer(tom.getName(), theConference);
		rev.assignReview(secondManu);
		tom.addRole(rev);
		
		User john = myUsers.get("John");
		john.switchConference(theConference);
		Reviewer rev2 = new Reviewer(john.getName(), theConference);
		john.addRole(rev2);
		
		String pattyName = "Patty";
		User pat = myUsers.get(pattyName);
		pat.switchConference(theConference);
		Reviewer rev3 = new Reviewer(pattyName, theConference);
		rev3.assignReview(firstManu);
		pat.addRole(rev3);
		Review review = new Review(pattyName, firstManu.getTitle(), "Computer Eyes is a manuscript worthy of this conference. Great Review");
		firstManu.addReview(review);
		firstManu.setStatus(1);
	}

}
