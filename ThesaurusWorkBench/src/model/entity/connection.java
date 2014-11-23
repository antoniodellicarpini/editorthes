package model.entity;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class connection {
	public SolrServer server;
	public String Thes;
	
	private static connection instance = null;
	   protected connection() {
	      // Exists only to defeat instantiation.
	   }
	   
	   public static connection getInstance() {
	      if(instance == null) {
	    	  
	         instance = new connection();
	         
	      }
	      return instance;
	   }
	
	public void open()
	{
		this.server = new HttpSolrServer("http://localhost:8983/solr");
	}
	
	public void open(String Thes)
	{
		this.Thes=Thes;
		this.server = new HttpSolrServer("http://localhost:8983/solr/"+this.Thes);
	}

}
