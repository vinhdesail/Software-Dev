package model;

import java.util.List;

public class Reviewer extends Role {
	
	private List<Manuscript> myAssignedManuscripts;
	
	public Reviewer(String theUserName) {
		super("Reviewer", theUserName);
	}
	
	
	public void submitReview(Review rev) {//will need to pass a review obj
		
	}
	
	public void assignReview(Manuscript theManuscript) {
		myAssignedManuscripts.add(theManuscript);
	}
	
	public void editReview(Review rev) {
		
	}
	
	
	public void deleteReview(Review rev) {
		
	}
	
	/*Might End up being private, due to only the reviewer internally needing to give a call.*/
	public void viewAllMyReview() {
		
	}
}
