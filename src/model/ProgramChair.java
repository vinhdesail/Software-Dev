package model;

public class ProgramChair extends Role {
	
	public ProgramChair() {
		super("Program Chair");//Formating can be changed to whatever is easiest to work with.
	}
	/*Might not be needed*/
	public void showAllManuscriptAssignedToSpc(SubprogramChair theSpc) {
		
	}
	/*Will have a subprogram chair passed in*/
	public void assignManuscript(SubprogramChair theSpc) {
		
	}
	
	/*Might end up being a private method that only PC uses*/
	public void showAllManuscripts() {
		
	}
	
	/*Both Methods might be in a single method, ie app/rej manu.*/
	
	public void approveManuscript(Manuscript theManuscript) {
		
	}
	
	public void rejectManuscript(Manuscript theManuscript) {
		
	}
	
	public void showAllRecommendation(Manuscript theManuscript) {
		
	}
	

}
