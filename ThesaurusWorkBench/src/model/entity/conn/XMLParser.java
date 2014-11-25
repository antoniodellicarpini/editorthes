package model.entity.conn;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	private String fileName=null;
	private Document document;
	private static XMLParser istanza=null;
	
	protected XMLParser()
	{
		
	}	
	
	public static XMLParser getIstance()
	{
		if(istanza==null)
		{
			istanza= new XMLParser();
		}
		 return istanza;
	}

	//metodi get e set
	public String getNodeValue(String nameNode)
	{
		// TODO Auto-generated method stub
				String role1=null;
		        // Make an  instance of the DocumentBuilderFactory
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        try {
		            // use the factory to take an instance of the document builder
		            DocumentBuilder db = dbf.newDocumentBuilder();
		            // parse using the builder to get the DOM mapping of the    
		            // XML file
		            document = db.parse(fileName);    
		            Element doc = document.getDocumentElement(); 
		            NodeList nl;
		            nl=doc.getElementsByTagName(nameNode);
		            if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
		    	        role1 = nl.item(0).getFirstChild().getNodeValue();
		    	    }
		            return role1;  
		        } catch (ParserConfigurationException pce) {
		            System.out.println(pce.getMessage());
		        } catch (SAXException se) {
		            System.out.println(se.getMessage());
		        } catch (IOException ioe) {
		            System.err.println(ioe.getMessage());
		        }		
				return "";
	}
	public void setFileName(String path)
	{
		this.fileName=path;
	}
	
	public void setNodeValue(String nameNode, String Value)
	{
		// TODO Auto-generated method stub
		String role1=null;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            document = db.parse(fileName);    
            Element doc = document.getDocumentElement(); 
            NodeList nl;
            nl=doc.getElementsByTagName(nameNode);
            if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
    	        nl.item(0).getFirstChild().setNodeValue(Value);
    	    }
            
            
         // write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(document);
    		StreamResult result = new StreamResult(new File(fileName));
    		transformer.transform(source, result);
            
            
            
           
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
