package model.session;


import java.util.ArrayList;



import model.entity.thesaurusSet;

public class thesaurus {
	
	 
	public static ArrayList<String> elenco() 
	{
		return thesaurusSet.elencoThes();
	}

	public static void setThes(String thes) 
	{
		thesaurusSet.setThes(thes);
	}
}
