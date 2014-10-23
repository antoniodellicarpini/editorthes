package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.session.concept;

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
        
		if(request.getParameter("loadConcept")!=null)
		{
			showConcept(request,response);
		}
		else
			showTopTerm(request,response);
		
	}
	
	
	private void showTopTerm(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException, IOException {
			  // TODO Auto-generated method stub
			  //Invocare il metodo elenco della classe docente
		    
			  ArrayList<String> elenco = concept.TopTerm((String) request.getAttribute("selectedValue"));
			  System.out.println(elenco.size());
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
			  //Invocare il metodo elenco della classe docente
		      concept c=new concept(request.getParameter("loadConcept"));
			  //Condivisione Elenco de concept
		      
			  request.setAttribute("elencoConcept", c.getNarrower());
			  
			  HttpSession oSessione = request.getSession();
			  oSessione.setAttribute("beanConcept", c);
			  
			  ServletContext oContesto = getServletContext();
			//Visualizzare la pagina 
			  RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			  oDispatcher.forward(request, response);
			 }
}
