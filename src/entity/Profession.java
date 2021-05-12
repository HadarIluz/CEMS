package entity;

import java.io.Serializable;

//Entity class - define Profession in the CEMS system.
@SuppressWarnings("serial")
public class Profession implements Serializable {
	private String professionID;
	private String professionName;
	
	public Profession(String professionID) {
		super();
		this.professionID = professionID;
	}
	
	public Profession(String professionID, String professionName) {
		super();
		this.professionID = professionID;
		this.professionName = professionName;
	}
	
	public String getProfessionID() {
		return professionID;
	}
	
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
}