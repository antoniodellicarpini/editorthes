package utils;
import org.xml.sax.SAXException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import utils.org.semanticweb.skos.SKOSAnnotation;
import utils.org.semanticweb.skos.SKOSConcept;
import utils.org.semanticweb.skos.SKOSCreationException;
import utils.org.semanticweb.skos.SKOSDataFactory;
import utils.org.semanticweb.skos.SKOSDataset;
import utils.org.semanticweb.skos.SKOSEntity;
import utils.org.semanticweb.skos.SKOSLiteral;
import utils.org.semanticweb.skos.SKOSTypedLiteral;
import utils.org.semanticweb.skos.SKOSUntypedLiteral;
import utils.org.semanticweb.skosapibinding.SKOSManager;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

public class Import {

	private SKOSDataset dataSet;
	private SKOSDataFactory df;
	
    public Import(SolrServer server, String filePath)  {
           
    	   try {
			server.deleteByQuery("*:*");
			 server.commit();
		} catch (SolrServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	  
            SKOSManager man = null;
			try {
				man = new SKOSManager();
			} catch (SKOSCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            df = man.getSKOSDataFactory();
            try {
				dataSet = man.loadDataset(URI.create("file:"+filePath));
			} catch (SKOSCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
            // posso prendere tutti i concetti mediante il for
            for (SKOSConcept concepts : dataSet.getSKOSConcepts()) {
                System.out.println("*********************************************************************");
                SolrInputDocument doc = new SolrInputDocument();
                
//*************************************************************************************************************************
            	// Pref Label
                for (SKOSAnnotation con : concepts.getSKOSAnnotationsByURI(dataSet, df.getSKOSPrefLabelProperty().getURI())) {
                    String prefLabel=null;
                	if (con.getAnnotationValueAsConstant() instanceof SKOSUntypedLiteral) {
                        SKOSUntypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                        System.out.println("\t\tPrefLabel: " + unCon.getLiteral() + " lang: " + unCon.getLang());
                        prefLabel=unCon.getLiteral();
                    }
                    else if (con.getAnnotationValueAsConstant() instanceof SKOSTypedLiteral) {
                        SKOSTypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                        System.out.println("\t\tPrefLabel: " + unCon.getLiteral());
                        prefLabel=unCon.getLiteral();
                    }

                	doc.addField("concept",prefLabel);
                	try {
						Stemmer a=new Stemmer(prefLabel);
						doc.addField("descrittore",a.getIndexS());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                   
                }

                // Alt Label
                for (SKOSAnnotation con : concepts.getSKOSAnnotationsByURI(dataSet, df.getSKOSAltLabelProperty().getURI())) {
                    if (con.getAnnotationValueAsConstant() instanceof SKOSUntypedLiteral) {
                        SKOSUntypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                        System.out.println("\t\tAltLabel: " + unCon.getLiteral() + " lang: " + unCon.getLang());
                        doc.addField("altlabel", unCon.getLiteral());
                    }
                    else if (con.getAnnotationValueAsConstant() instanceof SKOSTypedLiteral) {
                        SKOSTypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                        System.out.println("\t\tAltLabel: " + unCon.getLiteral());
                        doc.addField("altlabel", unCon.getLiteral());
                    }
                }	       

                
                // Scope Note
                for (SKOSAnnotation con : concepts.getSKOSAnnotationsByURI(dataSet, df.getSKOSScopeNoteDataProperty().getURI())) {
                    if (con.getAnnotationValueAsConstant() instanceof SKOSUntypedLiteral) {
                        SKOSUntypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                        System.out.println("\t\tScopeNote: " + unCon.getLiteral() + " lang: " + unCon.getLang());
                        doc.addField("note", unCon.getLiteral());
                    }
                    else if (con.getAnnotationValueAsConstant() instanceof SKOSTypedLiteral) {
                        SKOSTypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                        System.out.println("\t\tScopeNote: " + unCon.getLiteral());
                        doc.addField("note", unCon.getLiteral());
                    }
                }
                
                
                // Broader
                for (SKOSEntity c : concepts.getSKOSRelatedEntitiesByProperty(dataSet, df.getSKOSBroaderProperty())) {
                    System.out.println("\t\thasBroader: " + c.getURI());
                    
                    for (SKOSLiteral BR :df.getSKOSConcept(c.getURI()).getSKOSRelatedConstantByProperty(dataSet, df.getSKOSPrefLabelProperty())) 
            		{
                        System.out.println("Broader: " + BR.getLiteral());
                        doc.addField("broader", BR.getLiteral());
                    }
                    
                }
                
                // Narrower
                for (SKOSEntity c : concepts.getSKOSRelatedEntitiesByProperty(dataSet, df.getSKOSNarrowerProperty())) {
                    System.out.println("\t\thasNarrower: " + c.getURI());
                    for (SKOSLiteral NR :df.getSKOSConcept(c.getURI()).getSKOSRelatedConstantByProperty(dataSet, df.getSKOSPrefLabelProperty())) 
            		{
                        System.out.println("Narrower: " + NR.getLiteral());
                        doc.addField("narrower", NR.getLiteral());
                    }
                }
                
                
                //related
                for (SKOSEntity c : concepts.getSKOSRelatedEntitiesByProperty(dataSet, df.getSKOSRelatedProperty())) {
                    System.out.println("\t\thasRelated: " + c.getURI());
                    for (SKOSLiteral RL :df.getSKOSConcept(c.getURI()).getSKOSRelatedConstantByProperty(dataSet, df.getSKOSPrefLabelProperty())) 
            		{
                        System.out.println("Related: " + RL.getLiteral());
                        doc.addField("related", RL.getLiteral());
                    }
                }

 
            	//*********************************
                 
                ArrayList<String> broaders = new ArrayList<String>();
            	broaders = getBroader(concepts);
            	String nuova="";
            	for(int i=0; i<broaders.size();i++)
            	{
            		System.out.println("gerarchia: "+broaders.get(i));
            		String[] parts= broaders.get(i).split("/");
            		
            		for(int j=parts.length-1; j>=0; j--)
            		{
            			if(j==0)
            			nuova+=parts[j];
            			else{
            				nuova+=parts[j]+"/";
            			}
            			
            		}
            		
            		//nuova= MicTh+"/"+nuova;
            		System.out.println("gerarchiaNuova: "+nuova);
            		doc.addField("hierarchy", nuova);
            		nuova="";
            		
            	}
            	
            	try {
     			   server.add(doc);
     			} catch (SolrServerException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
            	
             }
            
            try {
            	server.optimize();
    			server.commit();
    		} catch (SolrServerException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
    


    public ArrayList<String> getBroader(SKOSConcept concept)
    {
    	String prefLabel=null;

    	for (SKOSAnnotation con : concept.getSKOSAnnotationsByURI(dataSet, df.getSKOSPrefLabelProperty().getURI())) {
        	if (con.getAnnotationValueAsConstant() instanceof SKOSUntypedLiteral) {
                SKOSUntypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                System.out.println("\t\tPrefLabel: " + unCon.getLiteral() + " lang: " + unCon.getLang());
                prefLabel=unCon.getLiteral();
            }
            else if (con.getAnnotationValueAsConstant() instanceof SKOSTypedLiteral) {
                SKOSTypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                System.out.println("\t\tPrefLabel: " + unCon.getLiteral());
                prefLabel=unCon.getLiteral();
            }
    	}

    	ArrayList<String> result = new ArrayList<String>();
    	
    	
    	// Broader
        for (SKOSEntity c : concept.getSKOSRelatedEntitiesByProperty(dataSet, df.getSKOSBroaderProperty())) {
            System.out.println("\t\thasBroader: " + c.getURI());
            
            for (SKOSLiteral BR :df.getSKOSConcept(c.getURI()).getSKOSRelatedConstantByProperty(dataSet, df.getSKOSPrefLabelProperty())) 
    		{
                System.out.println("Broader: " + BR.getLiteral());
                ArrayList<String> aux = new ArrayList<String>();
                aux = getBroader(df.getSKOSConcept(c.getURI()));
                
                for(int i = 0; i<aux.size(); i++)
    			{
    				String h = prefLabel+"/"+aux.get(i);
    				result.add(h);
    			}
            }
            }
        
        if(result.size()==0)
    		result.add(prefLabel);
    	return result;
    }
}