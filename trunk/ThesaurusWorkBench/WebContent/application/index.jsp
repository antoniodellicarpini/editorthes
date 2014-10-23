<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<title>The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible.</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="keywords" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="robots" content="index, follow" />
	<link rel="stylesheet" type="text/css" href="application/css/screen.css" media="screen" />
</head>
<body>
<jsp:useBean id="beanConcept" scope="session" class="model.session.concept">
 <jsp:setProperty name="beanConcept" property="*" />
</jsp:useBean>

<div id="header">

<form method="post" action="/ThesaurusWorkBench/CtrlMain">
	<select name="optionThes">	
	<option value="default" selected=true> seleziona un thes </option>
	<% if ( request.getAttribute("elenco_Thes")!=null) 
		{		ArrayList elencoThes= (ArrayList) request.getAttribute("elenco_Thes"); %>
				
	<%   		for (int i=0; i<elencoThes.size(); i++)
	   			{  
	   				%>
	      		<option value="<%= elencoThes.get(i)%>"> <%=elencoThes.get(i) %> </option>    
	  <% }
	}%>
	</select>
	<input type ="submit" name="loadThes" value="Invio">
</form>

	<ul>
		<li><a href="#">3 Column <span>prima voce</span></a></li>
		<li><a href="#">3 Column <span>seconda voce</span></a></li>
		<li><a href="#">3 Column <span>terza voce</span></a></li>
		<li><a href="#">3 Column <span>quarta voce</span></a></li>
		<li><a href="#">3 Column <span>quinta voce</span></a></li>
	</ul>
</div>
<div class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<!-- Column 1 start -->
			
			<p> <% if(beanConcept.getDescrittore()!=null)
					{
				%>  <%=beanConcept.getDescrittore() %>
					<% }
			else
			{
			%> Selezionare un descrittore
				<%
				}		%>
					</p>
		
			
			
				<!-- Column 1 end -->
		</div>
		<div class="col2">
		
		<form method="post" action="/ThesaurusWorkBench/CtrlConcept">
			<!-- Column 2 start -->
			<% if( request.getAttribute("elencoConcept")!=null) 
				{
				 ArrayList elencoConcept= (ArrayList) request.getAttribute("elencoConcept");
				 
				 for (int i=0; i<elencoConcept.size(); i++)
				   {  
				 %>
				 
		<input type ="submit" name="loadConcept" value="<%= elencoConcept.get(i)%>"> 
		<br/> 
				 <%
					} 
				 }%>
			
			</form>
		   
			<!-- Column 2 end -->
		</div>
	</div>
</div>
<div id="footer">
	<p>copyright</p>
</div>

</body>
</html>