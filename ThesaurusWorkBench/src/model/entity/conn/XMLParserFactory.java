package model.entity.conn;

public class XMLParserFactory {

	private String network_cfg_file="../Workspace/ThesaurusWorkBench/src/network_cfg.xml";
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
	
	
	
}
