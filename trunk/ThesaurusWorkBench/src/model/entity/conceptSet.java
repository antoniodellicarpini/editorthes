package model.entity;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class conceptSet {

	
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
	    	
	    	String aux[]=results.get(i).getFieldValues("hierarchy").toString().split("/");
	    	elencoTopTerm.add(aux[0]);
	    }
	    ArrayList<String> list = new ArrayList<String>(elencoTopTerm);
	    
	    return list;
	    
	}
	
}
