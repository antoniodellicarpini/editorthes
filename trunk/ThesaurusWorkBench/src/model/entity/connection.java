package model.entity;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class connection {
	
	SolrServer server;	
	public void open()
	{
		this.server = new HttpSolrServer("http://localhost:8983/solr");
	}

}
