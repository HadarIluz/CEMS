package entity;

import java.io.Serializable;

public class QuestionRow  implements Serializable {
	

	private String QuestionID;
	private String profession;
	private String Question;
	
	
	
	public QuestionRow() {
		
	}
	
	
	public String getQuestionID() {
		return QuestionID;
	}

	

	public void setQuestionID(String QuestionID) {
		this.QuestionID = QuestionID;
	}

	

	public String getProfession() {
		return profession;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}


	public String getQuestion() {
		return Question;
	}


	public void setQuestion(String question) {
		Question = question;
	}



	
	
	
	
	
	
	
	
	

}
