package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Stemmer;
import model.session.concept;
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
		response.setContentType("application/json");

		String q = request.getParameter("term");
		
		PrintWriter writer = response.getWriter();
		ArrayList<String> a = model.session.concept.getSuggest(q, "5");
		String s=new Gson().toJson(a);
		writer.write(s);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("query: ");
		String azione=request.getParameter("cmdAzione");
		System.out.println("ctr:"+azione);
		
		if(request.getParameter("loadConcept")!=null)
		{
			showConcept(request,response);
		}
		
		else if(azione.equals("Create Concept"))
		{
		   createConcept(request,response);	
		}
		else if(azione.equals("Delete Concept"))
		{
			deleteConcept(request,response);
		}
		else if(azione.equals("edit Broader")||azione.equals("edit PrefLabel"))
		{
			editConcept(request,response);
		}
		else {
			showTopTerm(request,response);
			}
	}
	
	
	private void showTopTerm(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException, IOException {
			  // TODO Auto-generated method stub
			  //Invocare il metodo elenco della classe docente
		      ArrayList<String> elenco=null;
		      System.out.println(request.getAttribute("selectedValue"));
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
			  System.out.println("Shoe"+elenco.size());
			  //Visualizzare la pagina 
			  ServletContext oContesto = getServletContext();
			  RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			  oDispatcher.forward(request, response);
			 
	}
	
	private void showConcept(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException, IOException {
			  // TODO Auto-generated method stub
			  //Invocare il metodo elenco della classe docente
			
		       concept c=null;
				if(request.getParameter("loadConcept")==null)
				{
					 c=new concept((String)request.getSession().getAttribute("beanConcept"),false);	
				}
				else{
						HttpSession oSessione=request.getSession();
						oSessione.setAttribute("beanConcept",request.getParameter("loadConcept"));
						 c=new concept(request.getParameter("loadConcept"),false);
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
		concept c=new concept(request.getParameter("descrittoreHidden"), true);
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
		
		showConcept(request, response);	
		
    }
}
