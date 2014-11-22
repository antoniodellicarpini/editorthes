package model.session;

import java.util.ArrayList;

import model.entity.conceptSet;


public class concept {

	private conceptSet conceptSet ;
	private String id;
	private String descrittore;
	private String concept;
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
	public String getConcept() {
		return this.concept; 
	}
	public void setConcept(String concept) {
		this.concept = concept;
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
	
	public concept(){
		init();
	}
	public concept(String concept,boolean broader)
	{
		init(concept,broader);
	}
	
	
	public void init()
	{
		this.conceptSet=new conceptSet();
	}
	public void init(String concept, boolean broader)
	{
		this.conceptSet=new conceptSet(concept,broader);
		this.setId(this.conceptSet.id);
		this.setAltLabel(this.conceptSet.altLabel);
		this.setBroader(this.conceptSet.broader);
		this.setDescrittore(this.conceptSet.descrittore);
		this.setHierarchy(this.conceptSet.hierarchy);
		this.setNarrower(this.conceptSet.narrower);
		this.setNote(this.conceptSet.note);
		this.setRelated(this.conceptSet.related);
		this.setConcept(this.conceptSet.concept);
	}

	
	public static ArrayList<String> TopTerm(String Thes) 
	{
		return model.entity.conceptSet.getTopTerm(Thes);
	}
	
	public int insert()
	{   if(this.getAltLabel()!=null)
			this.conceptSet.altLabel=this.getAltLabel();
		if(this.getBroader()!=null)
			this.conceptSet.broader=this.getBroader();
		this.conceptSet.descrittore=this.getDescrittore();
		this.conceptSet.concept=this.getConcept();
		this.conceptSet.hierarchy=this.getHierarchy();
		if(this.getNarrower()!=null)
			this.conceptSet.narrower=this.getNarrower();
		if(this.getNote()!=null)
			this.conceptSet.note=this.getNote();
		if(this.getRelated()!=null)
			this.conceptSet.related=this.getRelated();
		
		return this.conceptSet.insert();
	
	}
	
	public void delete()
	{
		this.conceptSet.delete();
	}
	public String edit(String exBroader, String exdescrittore)
	{
		this.conceptSet.concept=this.getConcept();
		this.conceptSet.descrittore=this.getDescrittore();
		this.conceptSet.broader=this.getBroader();
		
		return this.conceptSet.edit(exBroader, exdescrittore);
		
	}
	
	public void editAltLabel()
	{
		this.conceptSet.altLabel=this.altLabel;
		this.conceptSet.editAltLabel();
	}
	
	
	public static ArrayList<String> getSuggest(String q, String limit){
		return model.entity.conceptSet.getSuggest(q, limit);
	}
}
