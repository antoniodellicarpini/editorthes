package model.entity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

public class thesaurusSet {
	
	
	
	public static ArrayList<String> elencoThes()  
	{ 
		
		
        connection conn= connection.getInstance();
        conn.open();
		// Request core list
		CoreAdminRequest request = new CoreAdminRequest();
		request.setAction(CoreAdminAction.STATUS);
		CoreAdminResponse cores = null;
		try {
			cores = request.process(conn.server);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// List of the cores
		ArrayList<String> coreList=new  ArrayList<String>();
		for (int i = 0; i < cores.getCoreStatus().size(); i++) {
			if(cores.getCoreStatus().getName(i).length()>5)
			{  
				String prefix=cores.getCoreStatus().getName(i).substring(0,4);
				if(prefix.equals("thes")){
					coreList.add(cores.getCoreStatus().getName(i));
				}
			}
		}
		
		return coreList;
	}
}
