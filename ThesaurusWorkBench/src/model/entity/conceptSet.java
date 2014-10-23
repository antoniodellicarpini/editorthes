package model.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.xml.sax.SAXException;

import utils.Stemmer;

public class conceptSet {
	
	public String id;
	public String descrittore;
	public ArrayList<String> narrower;
	public ArrayList<String> hierarchy;
	public ArrayList<String> broader;
	public ArrayList<String> related;
	public ArrayList<String> altLabel;
	public ArrayList<String> note;
	
	public conceptSet(){}
	public conceptSet(String concept)
    {
		SolrQuery query = new SolrQuery();
		try {
			Stemmer s=new Stemmer(concept);
			concept=s.getIndexS();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    query.setQuery("descrittore:\""+concept+"\"");
	    query.setParam("rows","10000");
	    QueryResponse response = null;
	    try {
		     response = connection.getInstance().server.query(query);
		    } catch (SolrServerException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
	    SolrDocumentList results = response.getResults();
	    System.out.println(results.size());
	    this.id=((String) results.get(0).getFieldValue("id"));
	    this.altLabel=(((ArrayList)results.get(0).getFieldValues("altlabel")));
	    this.broader=(((ArrayList)results.get(0).getFieldValues("broader")));
	    this.descrittore=((String) results.get(0).getFieldValue("descrittore"));
	    this.hierarchy=(((ArrayList)results.get(0).getFieldValues("hierarchy")));
	    this.narrower=(((ArrayList)results.get(0).getFieldValues("narrower")));
	    this.note=(((ArrayList)results.get(0).getFieldValues("note")));
	    this.related=(((ArrayList)results.get(0).getFieldValues("related")));   
    }
	
	public static ArrayList<String> getTopTerm(String thes)
	{
		connection conn=connection.getInstance();
		conn.open(thes);
		SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.setParam("rows","10000");
	    QueryResponse response = null;
	    try {
	     response = conn.server.query(query);
	    } catch (SolrServerException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	    SolrDocumentList results = response.getResults();
	    HashSet<String> elencoTopTerm=new HashSet<>();
	    for (int i=0; i<results.size();i++)
	    {
	    	
	    	ArrayList<String> aux=((ArrayList)results.get(i).getFieldValues("hierarchy"));
            
	    	for(int j=0; j<aux.size();j++){
	    		String aux2[]= aux.get(j).split("/"); 
	    		elencoTopTerm.add(aux2[0]);
	    	}
	    }
	    ArrayList<String> list = new ArrayList<String>(elencoTopTerm);
	    
	    return list;
	    
	}

	
	
}
