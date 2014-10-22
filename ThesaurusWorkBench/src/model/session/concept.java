package model.session;

import java.util.ArrayList;

import model.entity.conceptSet;


public class concept {

	public static ArrayList<String> TopTerm(String Thes) 
	{
		return conceptSet.getTopTerm(Thes);
	}
}
