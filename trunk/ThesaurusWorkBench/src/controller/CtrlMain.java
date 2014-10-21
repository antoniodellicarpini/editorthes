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

import org.apache.solr.client.solrj.SolrServerException;

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
		
		
			viewPage(request, response);
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	}
	
	
	private void viewPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
         
		ArrayList<String> elenco_Thes=new ArrayList<>();
		elenco_Thes=thesaurus.elenco();
		request.setAttribute("elenco_Thes", elenco_Thes);
		    // TODO Auto-generated method stub
			ServletContext oContesto = getServletContext();
			RequestDispatcher oDispatcher = oContesto.getRequestDispatcher("/application/index.jsp");
			oDispatcher.forward(request, response);		
	}

}
