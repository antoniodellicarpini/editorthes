package model.session;

import java.util.ArrayList;

import model.entity.conceptSet;


public class concept {

	private conceptSet conceptSet ;
	private String id;
	private String descrittore;
	private ArrayList<String> narrower;
	private ArrayList<String> hierarchy;
	private ArrayList<String> broader;
	private ArrayList<String> related;
	private ArrayList<String> altLabel;
	private ArrayList<String> note;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrittore() {
		return descrittore;
	}

	public void setDescrittore(String descrittore) {
		this.descrittore = descrittore;
	}

	public ArrayList<String> getNarrower() {
		return narrower;
	}

	public void setNarrower(ArrayList<String> narrower) {
		this.narrower = narrower;
	}

	public ArrayList<String> getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(ArrayList<String> hierarchy) {
		this.hierarchy = hierarchy;
	}

	public ArrayList<String> getBroader() {
		return broader;
	}

	public void setBroader(ArrayList<String> broader) {
		this.broader = broader;
	}

	public ArrayList<String> getRelated() {
		return related;
	}

	public void setRelated(ArrayList<String> related) {
		this.related = related;
	}

	public ArrayList<String> getAltLabel() {
		return altLabel;
	}

	public void setAltLabel(ArrayList<String> altLabel) {
		this.altLabel = altLabel;
	}

	public ArrayList<String> getNote() {
		return note;
	}

	public void setNote(ArrayList<String> note) {
		this.note = note;
	}
	
	public concept(){}
	
	public concept(String concept)
	{
		this.conceptSet=new conceptSet(concept);
		this.setId(this.conceptSet.id);
		this.setAltLabel(this.conceptSet.altLabel);
		this.setBroader(this.conceptSet.broader);
		this.setDescrittore(this.conceptSet.descrittore);
		this.setHierarchy(this.conceptSet.hierarchy);
		this.setNarrower(this.conceptSet.narrower);
		this.setNote(this.conceptSet.note);
		this.setRelated(this.conceptSet.related);
	}

	public static ArrayList<String> TopTerm(String Thes) 
	{
		return model.entity.conceptSet.getTopTerm(Thes);
	}
}
