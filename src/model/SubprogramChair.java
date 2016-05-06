package model;

public class SubprogramChair extends Role{
	
	public SubprogramChair() {
		super("SubProgram Chair");//Formating can be changed to whatever is easiest to work with.
	}
	/*Might not be needed*/
	public void AssignReviewer(Reviewer theReviewer, Manuscript theManuscript) {//will need to pass a reviewer obj
		
	}

	
	/*Might end up being a private method that only SPC uses,replaces showAllMyReview*/
	public void showAllAssignedManuscripts() {
		
	}
	
	
	
	public void submitRecomendation() {
		
	}
	
	public void editRecomendation() {
		
	}
	/*Might Not be needed*/
	public void deleteRecomendation() {
		
	}
}
