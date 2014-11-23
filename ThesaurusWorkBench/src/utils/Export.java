package utils;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import model.entity.connection;
import model.session.concept;

import org.apache.solr.client.solrj.SolrServer;

import utils.org.semanticweb.skos.AddAssertion;
import utils.org.semanticweb.skos.SKOSAnnotation;
import utils.org.semanticweb.skos.SKOSAnnotationAssertion;
import utils.org.semanticweb.skos.SKOSChange;
import utils.org.semanticweb.skos.SKOSChangeException;
import utils.org.semanticweb.skos.SKOSConcept;
import utils.org.semanticweb.skos.SKOSCreationException;
import utils.org.semanticweb.skos.SKOSDataFactory;
import utils.org.semanticweb.skos.SKOSDataset;
import utils.org.semanticweb.skos.SKOSEntityAssertion;
import utils.org.semanticweb.skos.SKOSObjectRelationAssertion;
import utils.org.semanticweb.skos.SKOSStorageException;
import utils.org.semanticweb.skosapibinding.SKOSFormatExt;
import utils.org.semanticweb.skosapibinding.SKOSManager;

public class Export {

	public static String baseURI = "http://kdm.it/";
	SKOSManager man=null;
	SKOSDataset vocab=null;
	SKOSDataFactory factory=null;
	List<SKOSChange> addList = new ArrayList<SKOSChange>();
	int uri=0;
	String path;
	
    public Export(){
    	
    	
		try {
			man = new SKOSManager();
			vocab = man.createSKOSDataset(URI.create(baseURI));
		} catch (SKOSCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		factory = man.getSKOSDataFactory();
		
		
		ArrayList<String> elenco = concept.TopTerm(connection.getInstance().Thes);
		
		for (int i=0;i<elenco.size();i++)
		{
			concept c = new concept(elenco.get(i), false);
			//creazione concept
			uri++;
			String cont = "concept#"+uri;
	    	SKOSConcept concept1 = factory.getSKOSConcept(URI.create(baseURI + cont));
	    	SKOSEntityAssertion conAss1 = factory.getSKOSEntityAssertion(concept1);
	    	addList.add(new AddAssertion(vocab,conAss1));
	    	//assegnazione PrefLabel
	    	SKOSAnnotation labelAnno = factory.getSKOSAnnotation(factory.getSKOSPrefLabelProperty().getURI(), c.getConcept());
	    	SKOSAnnotationAssertion assertion1 = factory.getSKOSAnnotationAssertion(concept1, labelAnno);
	    	addList.add(new AddAssertion(vocab,assertion1));
	    
	    	//assegnazione AltLabel
	    	if(c.getAltLabel()!=null){
		    	for(int j=0;j<c.getAltLabel().size();j++)
		    	{
		    		SKOSAnnotation altLabelAnno = factory.getSKOSAnnotation(factory.getSKOSAltLabelProperty().getURI(), c.getAltLabel().get(j));
			    	SKOSAnnotationAssertion assertion2 = factory.getSKOSAnnotationAssertion(concept1, altLabelAnno);
			    	addList.add(new AddAssertion(vocab,assertion2));
		    	}
	    	}
	    	
	    	//assegnazione narrower
	    	if(c.getNarrower()!=null){
		    	for(int j=0;j<c.getNarrower().size();j++)
		    	{
		    		propagation(c.getNarrower().get(j), cont);
		    	}
	    	}
	    	
		}
		
	  
        try {
			man.applyChanges(addList);
		} catch (SKOSChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.err.println("writing ontology");
        
        try {
        	
			man.save(vocab, SKOSFormatExt.RDFXML, URI.create("file:/Users/Public/Mytestskos.rdf"));
			path="C:/Users/Public/Mytestskos.rdf";
		} catch (SKOSStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    public void propagation(String cp, String cont)
    {
    	concept c = new concept(cp, false);
		//creazione concept
    	uri++;
    	String cont2="concept#"+uri;
    	SKOSConcept concept1 = factory.getSKOSConcept(URI.create(baseURI + cont2));
    	SKOSEntityAssertion conAss1 = factory.getSKOSEntityAssertion(concept1);
    	addList.add(new AddAssertion(vocab,conAss1));
    	//assegnazione PrefLabel
    	SKOSAnnotation labelAnno = factory.getSKOSAnnotation(factory.getSKOSPrefLabelProperty().getURI(), c.getConcept());
    	SKOSAnnotationAssertion assertion1 = factory.getSKOSAnnotationAssertion(concept1, labelAnno);
    	addList.add(new AddAssertion(vocab,assertion1));
    
    	//assegnazione AltLabel
    	if(c.getAltLabel()!=null){
	    	for(int j=0;j<c.getAltLabel().size();j++)
	    	{
	    		SKOSAnnotation altLabelAnno = factory.getSKOSAnnotation(factory.getSKOSAltLabelProperty().getURI(), c.getAltLabel().get(j));
		    	SKOSAnnotationAssertion assertion2 = factory.getSKOSAnnotationAssertion(concept1, altLabelAnno);
		    	addList.add(new AddAssertion(vocab,assertion2));
	    	}
    	}
    	//assegnazione narrower
    	if(c.getNarrower()!=null){
	    	for(int j=0;j<c.getNarrower().size();j++)
	    	{
	    		propagation(c.getNarrower().get(j), cont2);
	    	}
    	}
    	
    	//assegnazione broader
    	SKOSObjectRelationAssertion assertion3 = factory.getSKOSObjectRelationAssertion(concept1, factory.getSKOSBroaderProperty(), factory.getSKOSConcept(URI.create(baseURI + cont)));
    	addList.add(new AddAssertion(vocab,assertion3));
    	
    	
    	SKOSObjectRelationAssertion assertion4 = factory.getSKOSObjectRelationAssertion(factory.getSKOSConcept(URI.create(baseURI + cont)), factory.getSKOSNarrowerProperty(), concept1);
		addList.add(new AddAssertion(vocab,assertion4));
    	
    	if(c.getNarrower()!=null){
	    	for(int j=0;j<c.getNarrower().size();j++)
	    	{
	    		SKOSObjectRelationAssertion assertion5 = factory.getSKOSObjectRelationAssertion(factory.getSKOSConcept(URI.create(baseURI + cont)), factory.getSKOSNarrowerProperty(), concept1);
	    		addList.add(new AddAssertion(vocab,assertion5));
	    	}
    	}
    }
    
    public String getPath()
    {
    	return path;
    }
    
    
}
