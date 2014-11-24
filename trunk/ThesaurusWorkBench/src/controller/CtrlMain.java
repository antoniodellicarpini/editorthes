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

import org.apache.solr.client.solrj.SolrServerException;

import com.google.gson.Gson;

import model.entity.connection;
import model.session.thesaurus;

/**
 * Servlet implementation class CtrlMain
 */
@WebServlet("/CtrlMain")
public class CtrlMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CtrlMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		if(request.getSession().getAttribute("beanThes")!=null)
		{
			request.getSession().removeAttribute("beanThes");
		} 
			viewPage(request, response);
			
		
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("term")!=null && request.getParameter("term").equals("parametro"))
		{   
			PrintWriter writer = response.getWriter();
			ArrayList<String> elenco_Thes=new ArrayList<>();
			String s=null;
			if(!checkConnection())
			{
				elenco_Thes.add("connessione assente");
				s=new Gson().toJson(elenco_Thes);
				writer.write(s);
			}
			else{
				elenco_Thes=thesaurus.elenco();
				 s=new Gson().toJson(elenco_Thes);
				writer.write(s);
				}
			return;
		}
		
		String selectedValue=request.getParameter("cmdAzione");
		if(selectedValue!=null)
        {
			if(!checkConnection())
			{
				//Definizione di un oggetto della classe ServletContext
				   ServletContext oContesto = getServletContext();
				   //Definizione di un oggetto per la pubblicazione della JSP
				   request.setAttribute("error",10);
				   RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
				   oDispatcher.forward(request,response);
				   return;
			}
			   //Definizione di un oggetto della classe ServletContext
			   ServletContext oContesto = getServletContext();
			   //Definizione di un oggetto per la pubblicazione della JSP
			   request.setAttribute("selectedValue",selectedValue);
			   RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/CtrlConcept");
			   oDispatcher.forward(request,response);
        }
		
	}
	
	
	private void viewPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> elenco_Thes=new ArrayList<>();
		
		// qui vado a riprendere il server che ho dalla mia configurazione e controllo se c'è la connessione
		//se non c'è la connessione allora mando il segnale di errore
	
		
		// TODO Auto-generated method stub
			ServletContext oContesto = getServletContext();
			RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			oDispatcher.forward(request, response);		
	
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
