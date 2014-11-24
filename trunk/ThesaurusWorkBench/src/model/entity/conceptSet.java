package model.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.TermsParams;
import org.xml.sax.SAXException;

import utils.Stemmer;

public class conceptSet {
	
	public String id;
	public String descrittore;
	public String concept;
	public ArrayList<String> narrower;
	public ArrayList<String> hierarchy;
	public ArrayList<String> broader;
	public ArrayList<String> related;
	public ArrayList<String> altLabel;
	public ArrayList<String> note;
	
	public conceptSet(){}
	
	public conceptSet(String concept,boolean stemmer)
    { 
		
		SolrQuery query = new SolrQuery();
		if(!stemmer){
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
	    }
	    query.setQuery("descrittore:\""+concept+"\"");
	    query.addSort("descrittore", ORDER.asc);
	   
	    query.setParam("rows","10000");
	    QueryResponse response = null;
	    try {
		     response = connection.getInstance().server.query(query);
		    } catch (SolrServerException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
	    SolrDocumentList results = response.getResults();
	    
	    this.id=((String) results.get(0).getFieldValue("id"));
	    this.altLabel=(((ArrayList)results.get(0).getFieldValues("altlabel")));
	    this.broader=(((ArrayList)results.get(0).getFieldValues("broader")));
	    this.descrittore=((String) results.get(0).getFieldValue("descrittore"));
	    this.hierarchy=(((ArrayList)results.get(0).getFieldValues("hierarchy")));
	    this.narrower=(((ArrayList)results.get(0).getFieldValues("narrower")));
	    this.note=(((ArrayList)results.get(0).getFieldValues("note")));
	    this.related=(((ArrayList)results.get(0).getFieldValues("related")));  
	    this.concept=((String) results.get(0).getFieldValue("concept"));
    }
	
	
	
	
	
	public static ArrayList<String> getTopTerm(String thes)
	{
		
		connection conn=connection.getInstance();
		
		conn.open(thes);
		SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.addSort("descrittore", ORDER.asc);
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
	
	public int insert()
	{
		
		SolrInputDocument doc = new SolrInputDocument();
		Stemmer s=null;
		try {
			s = new Stemmer(this.descrittore);
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
		
		if(s.getIndexS().equals("-1"))
		{// se stemmer risulta essere vuoto
			return -1;
		}
		
		//controllo se il descrittore esiste già
		SolrQuery query = new SolrQuery();
	    query.setQuery("descrittore:\""+s.getIndexS()+"\"");
	    QueryResponse response = null;

	    try {
	     response = connection.getInstance().server.query(query);
	    } catch (SolrServerException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	    
	    SolrDocumentList results = response.getResults();
	   
	    
	    if(results.size()!=0)
	    {
	    	return -2;
	    }
	    
		doc.addField("descrittore", s.getIndexS());
		doc.addField("concept",this.descrittore);
		if(this.altLabel!=null)
			{for(int i=0; i<this.altLabel.size();i++){
				doc.addField("altlabel", this.altLabel.get(i));
			}
			}
		if (this.broader!=null){
		for(int i=0; i<this.broader.size();i++){
			doc.addField("broader", this.broader.get(i));
		}
		}
		
		
		for(int i=0; i<this.hierarchy.size();i++){
			doc.addField("hierarchy", this.hierarchy.get(i));
		}
		if(this.narrower!=null){
		for(int i=0; i<this.narrower.size();i++){
			doc.addField("narrower", this.narrower.get(i));
		}
		}
		if(this.note!=null){
		for(int i=0; i<this.note.size();i++){
			doc.addField("note", this.note.get(i));
		}
		}
		if(this.related!=null){
		for(int i=0; i<this.related.size();i++){
			doc.addField("related", this.related.get(i));
		}
		}
		
		
		try {
			connection.getInstance().server.add(doc);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (this.broader!=null)
		{	
			conceptSet father=new conceptSet(this.broader.get(0),true);
			ArrayList<String> narrower=null;
			if(father.narrower!=null){
				 narrower=father.narrower;
				}
			else{
				narrower=new ArrayList<>();
			 }
			narrower.add(this.descrittore);
			father.narrower=narrower;
		
			update(father);
		}
		
		try {
			connection.getInstance().server.optimize();
			connection.getInstance().server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public void update(conceptSet father)
	{
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("descrittore", father.descrittore);
		doc.addField("concept", father.concept);
		doc.addField("id", father.id);
		
		if(father.altLabel!=null)
		{for(int i=0; i<father.altLabel.size();i++){
			doc.addField("altlabel", father.altLabel.get(i));
		}
		}
		if(father.broader!=null){
		for(int i=0; i<father.broader.size();i++){
			doc.addField("broader", father.broader.get(i));
		}
		}
		
		for(int i=0; i<father.hierarchy.size();i++){
			doc.addField("hierarchy", father.hierarchy.get(i));
		}
		if (father.narrower!=null){
		for(int i=0; i<father.narrower.size();i++){
			doc.addField("narrower", father.narrower.get(i));
		}
		}
		if(father.note!=null){
		for(int i=0; i<father.note.size();i++){
			doc.addField("note", father.note.get(i));
		}}
		if(father.related!=null){
		for(int i=0; i<father.related.size();i++){
			doc.addField("related", father.related.get(i));
		}}
		
		try {
			connection.getInstance().server.add(doc);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.getInstance().server.optimize();
			connection.getInstance().server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete()
	{
		if(this.narrower!=null){
			
			for(int i=0;i<this.narrower.size();i++)
			{
				conceptSet c=new conceptSet(this.narrower.get(i),false);
				if(this.broader!=null)
					c.broader=this.broader;
				else
					c.broader=null;
			    update(c);
			}
			for(int i=0;i<this.narrower.size();i++)
			{
				
				changeHierarchy(this.narrower.get(i),this.concept);
				
			}
		  	
		
		}
		
		if(this.broader!=null)
		{
			conceptSet father=new conceptSet(this.broader.get(0),true);
			
			if(father.narrower.size()==1)
			{
				//il padre ha solo un narrower che è l'elemento da eliminare
				//effettuo direttamente l'assegnazione dei nuovi narrower (oppure è null se non ce ne sono)
				father.narrower=this.narrower;
			}
			else
			{
				//il padre ha piu di un narrower
				//cerco il narrower da eliminare
				for(int i=0;i<father.narrower.size();i++)
				{
					if(father.narrower.get(i).equals(this.concept))
					{
						father.narrower.remove(i);
					}
				}
				
				//aggiungo i nuovi narrower (se ce ne sono)
				if(this.narrower!=null)
				{
					for(int i=0;i<this.narrower.size();i++)
					{
						father.narrower.add(this.narrower.get(i));
					}
				}
			}
			
			update(father);
		}
		try {
			connection.getInstance().server.deleteById(this.id);
			connection.getInstance().server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void changeHierarchy(String concept,String hs)
	{
		conceptSet c=new conceptSet(concept,false);
		String s[]= c.hierarchy.get(0).split("/");
		//qui mi vado a prendere la stringa che mi serve
		String out="";
		for(int j=0;j<s.length;j++)
		{
			if(!s[j].equals(hs))
			{
				if(j!=s.length-1)
					out=out+s[j]+"/";
				else
					out=out+s[j];
			}
			
		}
		ArrayList<String> h=new ArrayList<>();
		h.add(out);
		c.hierarchy=h;
		update(c);
		
		if(c.narrower!=null)
		{
			for(int i=0;i<c.narrower.size();i++)
			{
				changeHierarchy(c.narrower.get(i),hs);	
			}
		}			
	}
	
	public String edit(String exBroader, String exdescrittore)
	{
		
		if(!checkRel(exdescrittore))
			return "-3";
		
		Stemmer s=null;
		Stemmer s2=null;
		try {
			s = new Stemmer(this.descrittore);
			if(this.broader!=null){
				s2 = new Stemmer(this.broader.get(0));
				this.broader.set(0, s2.getIndexS());
			}
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
		
		if(s.getIndexS().equals("-1"))
		{// se stemmer risulta essere vuoto
			return "-1";
		}
		
		this.descrittore=s.getIndexS();
		if(this.broader!=null){
		if(this.descrittore.equals(this.broader.get(0)))
		{
			return "-4";
		}
		}
		// se ho cambiato il descrittore devo controllare che quello nuovo nn sia presente nell'indice
		if(!this.concept.equals(exdescrittore))
		{
			SolrQuery query = new SolrQuery();
		    query.setQuery("descrittore:\""+this.descrittore+"\"");
		    QueryResponse response = null;

		    try {
		     response = connection.getInstance().server.query(query);
		    } catch (SolrServerException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		    
		    SolrDocumentList results = response.getResults();
		   
		    if(results.size()!=0)
		    {
		    	return "-2";
		    }
		}
		
		
		// modifico la gerarchia se è cambiato il broader
		if(this.broader!=null)
			{
				conceptSet father=new conceptSet(this.broader.get(0),false);
				ArrayList<String> h=new ArrayList<>();
				h.add(father.hierarchy.get(0)+"/"+this.concept);
				this.hierarchy=h;
				
				if(!this.broader.get(0).equals(exBroader))
				{
					if(father.narrower!=null)
						father.narrower.add(this.concept);
					else
					{
						ArrayList<String> a = new ArrayList<>();
						a.add(this.concept);
						father.narrower=a;
					}
					
				}
				else
				{
					if(father.narrower!=null)
					{
						for(int i=0;i<father.narrower.size();i++)
						{
							if(father.narrower.get(i).equals(exdescrittore))
							{
								father.narrower.set(i, concept);
							}
						}
					}
				}
				
				update(father);
			}
		
		// rimuoviamo o modifichiamo all'ex padre il narrower
		if(exBroader!=null)
		{
			conceptSet exFather=new conceptSet(exBroader,true);
			
			if(exFather.narrower!=null)
			{
				for(int i=0;i<exFather.narrower.size();i++)
				{
					if(exFather.narrower.get(i).equals(exdescrittore))
					{
						if(this.broader!=null)
						{		if(this.broader.get(0).equals(exBroader))
								{
								exFather.narrower.set(i, this.concept);
								}
						
							else
								{
								exFather.narrower.remove(i);	
								}
						}
						else
							exFather.narrower.remove(i);
						
					}	
				}
			}
			
			update(exFather);
		}
		
		if(this.broader==null)
		{
			this.hierarchy.set(0, this.concept);
		}
		
		update(this);
		
		// modifica dei narrower
		
		if(this.narrower!=null)
		{
			for(int i=0;i<this.narrower.size();i++)
			{
				conceptSet c = new conceptSet(this.narrower.get(i),false);
				if(!this.descrittore.equals(exdescrittore))
				{
					c.broader.set(0, this.descrittore);
				}
				
				c.hierarchy.set(0, this.hierarchy.get(0)+"/"+c.concept);
				
				update(c);
				
				changeHierarchy(c);
			}
		}		
		return this.descrittore;	
	}
	

	public void changeHierarchy(conceptSet c)
	{
		
		if(c.narrower!=null)
		{
			for(int i=0;i<c.narrower.size();i++)
			{
				conceptSet n = new conceptSet(c.narrower.get(i),false);
				n.hierarchy.set(0, c.hierarchy.get(0)+"/"+n.concept);
				update(n);
				changeHierarchy(n);
				
			}
		}
	}
	
	//funzione che controlla se stiamo spostando un concept sotto uno dei suoi figli
	public boolean checkRel(String concept)
	{
		//se lo stiamo spostando come TopTerm allora nn serve eseguire il controllo
		if(this.broader==null)
		{
			return true;
		}
		conceptSet c = new conceptSet(concept, false);
		if(c.narrower!=null)
		 {
			
			for(int i=0; i<c.narrower.size();i++)
			{  
				conceptSet cnar=new conceptSet(c.narrower.get(i),false);
				if(cnar.concept.equals(this.broader.get(0)))
					return false;
				if(!checkRel(c.narrower.get(i))){
					return false;
				}
			}
		 }
		return true;
		
	}
	
	
	public static ArrayList<String> getSuggest(String q, String limit){
		
		SolrQuery query = new SolrQuery();
		query.set("qt", "/terms");
		query.set("terms.fl", "concept");
		query.set("terms.prefix", q);
	    QueryResponse response = null;
	    try {
	     response = connection.getInstance().server.query(query);
	     
	    } catch (SolrServerException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	    TermsResponse tr = response.getTermsResponse();
	    List<Term> termList = tr.getTerms("concept");
	   
	    ArrayList<String> a = new ArrayList<>(); 
	    for(Term t : termList)
	    {
	    	a.add(t.getTerm());
	    }
	    
	    return a;
	    
	}
	
	public void editAltLabel(){
		update(this);
	}
	
}
