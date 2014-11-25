package model.entity.conn;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;

public class XMLParserFactory {

	
  
	private String network_cfg_file= "../Workspace/ThesaurusWorkBench/src/network_cfg.xml";
	//private String network_cfg_file=getClass().getResource("network_cfg.xml").toString();
	private static XMLParserFactory istanza=null;
	
	
	protected XMLParserFactory()
	{
	}
		
	public static XMLParserFactory getIstance()
	{
		if(istanza==null)
		{
			istanza= new XMLParserFactory();
		}
		 return istanza;
	}

	
	//metodi get
	
	public String getHost()
	{
		XMLParser.getIstance().setFileName(network_cfg_file);
		return XMLParser.getIstance().getNodeValue("host");
	}
	
	public void setHost(String Value)
	{   XMLParser.getIstance().setFileName(network_cfg_file);
		XMLParser.getIstance().setNodeValue("host", Value);
	}
	
	
}
