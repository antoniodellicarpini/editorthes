package model.entity;

import java.io.IOException;

import model.entity.conn.XMLParserFactory;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

public class connection {
	public SolrServer server;
	public String Thes;
	public String solrServer;
	
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
		this.server = new HttpSolrServer(XMLParserFactory.getIstance().getHost());
			
	}
	
	public void open(String Thes)
	{
		this.Thes=Thes;
		this.server = new HttpSolrServer(XMLParserFactory.getIstance().getHost()+this.Thes);
		this.solrServer=XMLParserFactory.getIstance().getHost()+this.Thes;
	}

	
	public boolean checkConnection()
	{
		SolrServer s= new HttpSolrServer(XMLParserFactory.getIstance().getHost());
		CoreAdminRequest request = new CoreAdminRequest();
		request.setAction(CoreAdminAction.STATUS);
		CoreAdminResponse cores = null;
		try {
			cores = request.process(s);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
	}

}
