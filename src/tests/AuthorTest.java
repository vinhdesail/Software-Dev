package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.*;

public class AuthorTest {
	
	private Author myAuthor;
	private Manuscript myManuscript;
	private List<Manuscript> myManuscriptList;
	private String myAuthorID;
	
	@Before
	public void setUp() {
		myAuthor = new Author(myAuthorID);
		myManuscriptList = new ArrayList<Manuscript>();
	}

	//not needed because it just prints the list
//	@Test
//	public void ShowAllMyManuscriptTest(ArrayList<Manuscript> theManuscripts) {
//	
//	}
	
	@Test
	public void addManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		assertSame(myManuscriptList.get(0), myManuscript);
	}
	
	@Test
	public void deleteManuscriptTest() {
		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		myAuthor.deleteManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
		assertEquals(myManuscriptList.size(), 0);
	}
//	
//	@Test
//	public void editManuscriptTest() {
//		myManuscript = new Manuscript("John", "Science", "Computer manuscript", "Some text");
//		myAuthor.addManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
//		myAuthor.editManuscript((ArrayList<Manuscript>) myManuscriptList, myManuscript);
//		assertEquals(myManuscriptList.size(), 1);
//	}
}
