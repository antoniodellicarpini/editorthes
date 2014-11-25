package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Stemmer;
import model.entity.connection;
import model.session.concept;
import model.session.thesaurus;

import com.google.gson.*;
/**
 * Servlet implementation class CtrlConcept
 */
@WebServlet("/CtrlConcept")
public class CtrlConcept extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CtrlConcept() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("term")!=null)
		{
			autosuggest(request,response);
			return;
		}
		if(!connection.getInstance().checkConnection())
		{
			request.setAttribute("error",10);
			ServletContext oContesto = getServletContext();
			//Visualizzare la pagina 
			 RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			 oDispatcher.forward(request, response);
			 return;
			
		}
		
		
		String azione=request.getParameter("cmdAzione");
		

		
		if(request.getParameter("loadConcept")!=null)
		{
			showConcept(request,response);
			return;
		}
		if(azione==null)
		{
			showTopTerm(request, response);
			return;
		}
		else if(azione.equals("Create Concept"))
		{
		   createConcept(request,response);
		   return;
		}
		else if(azione.equals("Delete Concept"))
		{
			deleteConcept(request,response);
			return;
		}
		else if(azione.equals("edit Broader")||azione.equals("edit PrefLabel")||azione.equals("edit AltLabel"))
		{
			editConcept(request,response);
			return;
		}
		else {
			showTopTerm(request,response);
			return;
			}
	}
	
	
	private void showTopTerm(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException, IOException {
			  // TODO Auto-generated method stub
		
		      ArrayList<String> elenco=null;
		      
		      if(request.getAttribute("selectedValue")==null)
		      {
		    	  elenco = concept.TopTerm((String)request.getSession().getAttribute("beanThes"));
		      }
		      
		      else{
		    	  elenco = concept.TopTerm((String) request.getAttribute("selectedValue"));
		    	  HttpSession oSessione=request.getSession();
				  oSessione.setAttribute("beanThes",request.getAttribute("selectedValue"));
		         }
		        
			  //Condivisione Elenco de concept
			  request.setAttribute("elencoConcept", elenco);
			  //Visualizzare la pagina 
			  ServletContext oContesto = getServletContext();
			  RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			  oDispatcher.forward(request, response);
			 
	}
	
	private void showConcept(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException, IOException {
			  // TODO Auto-generated method stub
			
				thesaurus.setThes((String)request.getSession().getAttribute("beanThes"));
		
		       concept c=null;
				if(request.getParameter("loadConcept")==null)
				{
					 c=new concept((String)request.getSession().getAttribute("beanConcept"),false);	
				}
				else{
					if(request.getParameter("loadConcept").equals("Root"))
					{
						showTopTerm(request,response);
						return;
					}
					else{
						HttpSession oSessione=request.getSession();
						oSessione.setAttribute("beanConcept",request.getParameter("loadConcept"));
						c=new concept(request.getParameter("loadConcept"),false);
					}
				}
						
		      
			  //Condivisione Elenco de concept
			  request.setAttribute("elencoConcept", c.getNarrower());
			  request.setAttribute("altLabel", c.getAltLabel());
			  request.setAttribute("broader", c.getBroader());
			  request.setAttribute("hierarchy", c.getHierarchy());
			  request.setAttribute("descrittore", c.getDescrittore());
			  request.setAttribute("concept", c.getConcept());
			  request.setAttribute("note", c.getNote());
			  request.setAttribute("related", c.getRelated());
			  
				
			  ServletContext oContesto = getServletContext();
			//Visualizzare la pagina 
			  RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			  oDispatcher.forward(request, response);
			 }
	
	private void createConcept(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	{	    
		thesaurus.setThes((String)request.getSession().getAttribute("beanThes"));
		
		concept c=new concept();
		c.setDescrittore(request.getParameter("descrittoreHidden"));
		ArrayList<String> h=new ArrayList<>();
		if (request.getParameter("hierarchy")!=null){
			String hs=request.getParameter("hierarchy")+"/"+request.getParameter("descrittoreHidden");
				h.add(hs);
				if (request.getParameter("descrittore")!=null)
				{
					ArrayList<String> b= new ArrayList<>();
					b.add(request.getParameter("descrittore"));
					c.setBroader(b);
				}
			}
		else
		{
			h.add(request.getParameter("descrittoreHidden"));
			
		}
		c.setHierarchy(h);
		
		int errore=c.insert();
		
		request.setAttribute("error", errore);
		
		
		if(request.getParameter("descrittore")==null)
		{
			this.showTopTerm(request, response);  
		}
		
		else
		{
			this.showConcept(request, response);
		}
		
		
	}
	
 }
	
	private void deleteConcept(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	    {
		  thesaurus.setThes((String)request.getSession().getAttribute("beanThes"));
		  
		  concept c=new concept(request.getParameter("descrittore"), true);
		  c.delete();
		  if(c.getBroader()!=null)
		  {
			  request.getSession().setAttribute("beanConcept",c.getBroader().get(0)); 
			  this.showConcept(request, response);
		  }
		  else
		  {
			  this.showTopTerm(request, response);
		  }
		}
	
	private void editConcept(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
		thesaurus.setThes((String)request.getSession().getAttribute("beanThes"));

		concept c=new concept(request.getParameter("descrittoreHidden"), true);
		if(request.getParameter("cmdAzione").equals("edit AltLabel"))
		{
			if(request.getParameterValues("altlabels")!=null)
			{
				ArrayList<String> a = new ArrayList<String>(Arrays.asList(request.getParameterValues("altlabels")));
				ArrayList<String> b = new ArrayList<>();
				for(int i=0; i<a.size(); i++)
				{
					if(!a.get(i).equals(""))
					{
						b.add(a.get(i));
					}
				}
				c.setAltLabel(b);
				c.editAltLabel();
			}
		}
		else
		{
		
			ArrayList<String> broader=new ArrayList<>();
			
			String exdescrittore=c.getConcept();
			
			c.setConcept(request.getParameter("displayName"));
			c.setDescrittore(request.getParameter("displayName"));
			
			String exbroader=null;
			if(c.getBroader()!=null)
			{
				exbroader=c.getBroader().get(0);
			}
			
			if(request.getParameter("displayBroader").equals(""))
			{
				broader=null;
			}
			else
			{
				broader.add(request.getParameter("displayBroader"));
			}
			
			c.setBroader(broader);
		 
			String errore=c.edit(exbroader,exdescrittore);
			request.setAttribute("error", errore);
			if(!errore.equals("-1")&&!errore.equals("-2")&&!errore.equals("-3")&&!errore.equals("-4"))
			{
				request.getSession().setAttribute("beanConcept",errore);
			}
			
		}	
		
		showConcept(request, response);	
		
    }
	
	private void autosuggest(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		thesaurus.setThes((String)request.getSession().getAttribute("beanThes"));
		response.setContentType("application/json");
		
		String q = request.getParameter("term");
		
		
		PrintWriter writer = response.getWriter();
		ArrayList<String> a=new ArrayList<>();
		String s=null;
		if(!checkConnection())
		{
			a.add("connessione assente");
			s=new Gson().toJson(a);
			writer.write(s);
		}
		else{
			a=model.session.concept.getSuggest(q,"5");
			 s=new Gson().toJson(a);
			writer.write(s);
			}
		return;
	}
	
	public boolean checkConnection()
	{
		if(!connection.getInstance().checkConnection())
		{    
			 return false;
		}
		return true;
		
	}

	
}
