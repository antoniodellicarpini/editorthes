<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<title>Thesaurus Workbench</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="keywords" content="The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible." />
	<meta name="robots" content="index, follow" />
	<link rel="stylesheet" type="text/css" href="application/css/screen.css" media="screen" />
	<link rel="stylesheet" href="application/css/jquery-ui.css">
	<script src="application/js/jquery-1.10.2.js"></script>
	<script src="application/js/jquery-ui.js"></script>
	<script src="application/js/script.js"></script>	
</head>
<body onload="errorFunction()">

	<div id="dialog">
	    <div id="thesauri" class="content">
		
	  </div>
	</div>
	
	
	<div id="dialogImport">
		<h3>File Upload:</h3>
		Select a file to upload: <br />
		<form action="/ThesaurusWorkBench/CtrlImport" method="post" enctype="multipart/form-data">
			<input type="file" name="file" size="50" />
			<br />
			<input type="submit" value="Upload File" />
		</form>
	</div>


	<div id="dialogBroader">
	<form method="post" name="editBroader" id="editBroader" action="/ThesaurusWorkBench/CtrlConcept" >
	    <div class="content">
			<input type="text" id="inputBroader" name="displayBroader" value="" />	
			<input type ="submit" id="submitBroader" name="cmdAzione"  value="edit Broader"/>
			<input type="hidden" name="descrittoreHidden" value="<%=request.getAttribute("descrittore")%>"/>
			<input type="hidden" value="<%=request.getAttribute("concept")%>" name="displayName"/>
	  </div>
	 </form>
	</div>
	
	<div id="dialogAltLabel">
	<form method="post" name="editBroader" id="editBroader" action="/ThesaurusWorkBench/CtrlConcept" >
	    <div class="content">
	    
	    <% if(request.getAttribute("altLabel")!=null) 
			{	
				ArrayList<String> sinonimi = ((ArrayList)request.getAttribute("altLabel"));
				for(int i=0; i<sinonimi.size(); i++)
				{%>
					<input type="text" name="altlabels" value="<%= sinonimi.get(i) %>">
				<%}
									
			} %>
				
				<div class="input_fields_wrap">
    				<button class="add_field_button">Add More Fields</button>
    			<div><input type="text" name="altlabels"></div>
				</div>
				
			<input type ="submit" name="cmdAzione"  value="edit AltLabel"/>
			<input type="hidden" name="descrittoreHidden" value="<%=request.getAttribute("descrittore")%>"/>
	  </div>
	 </form>
	</div>


<div id="header">
<div id="thesaurus" class="vocimenu">THESAURUS</div>
<div id="import" class="vocimenu">IMPORT</div>
<div id="export" class="vocimenu">EXPORT</div>
</div>
<div id=searchBar>
<form method="post" name="searchOperation" id="searchOperations" action="/ThesaurusWorkBench/CtrlConcept" >
        <div id="contentSearch">
	 		<input type="text" id="search" name="loadConcept" value="search" />	
	 	</div>
		</form>
</div>

<div id="contenitore">
	<div id="contenitoreSinistro">

		
		<form method="post" action="/ThesaurusWorkBench/CtrlConcept">
			<!-- Column 2 start -->
			<% if(request.getAttribute("descrittore")!=null)
					{ %>
				
				<div id="conceptB" class="concept"><%= request.getAttribute("concept")%></div>
				<br/>
				
				<%	}
				 %>
			<% if( request.getAttribute("elencoConcept")!=null) 
				{
				 ArrayList elencoConcept= (ArrayList) request.getAttribute("elencoConcept");
				 
				 for (int i=0; i<elencoConcept.size(); i++)
				   {  	 
				 %>
				 
        <input type ="submit" class="concept" name="loadConcept" value="<%= elencoConcept.get(i)%>"/> 
		<br/> 
				 <%
					} 
				 }%>
			
			</form>
		   
			<!-- Column 2 end -->
		</div>
		<div id="contenitoreDestro">
		
		<form method="post" action="/ThesaurusWorkBench/CtrlConcept">
		
		<% if(request.getSession().getAttribute("beanThes")==null)
			 { %><div id="headerDescription"> Seleziona un Thesaurus			                                 
					                            </div>
			<%}	
				                          
		else{ %> 
				
			<%
			if(request.getAttribute("descrittore")!=null)
			{
				String[] s= ((String)(((ArrayList)request.getAttribute("hierarchy"))).get(0)).split("/");
				%>
				<input type ="submit" class="concept" name="loadConcept" value="Root"/> 
				<%
				for(int i=0;i<s.length;i++)
				{%>
					<input type ="submit" class="concept" name="loadConcept" value="<%= s[i]%>"/> 
				<%}
			}%>
			</form>
			
			
		<form method="post" name="crudOperation" id="crudOperations" action="/ThesaurusWorkBench/CtrlConcept" >

		
		     <div id=headContenitoreDestro>
		     
		     
		     
		     	<div id="headerDescription"><% if(request.getAttribute("descrittore")!=null)
					                            { %>  
					                            <%=	request.getAttribute("concept") %>
					                            <%} else{ %>
					                                 <%=	request.getSession().getAttribute("beanThes") %>
					                                <%}%>
					                                 
					                            
				</div>
		     	<div id="cmdCreaDelete"> 
		     	
		     			<input type ="submit" class="buttonCD"  id="create" name="cmdAzione" value="Create Concept">
	                                <input type ="submit" class="buttonCD" id="delete"   name="cmdAzione" value="Delete Concept">
	            </div>
		     </div>
			
	
	
	 <% if(request.getAttribute("descrittore")!=null)
					{
					for (int j=0; j<((ArrayList)request.getAttribute("hierarchy")).size(); j++)
	   					{	
				%>  
				<input type="hidden" value="<%=((ArrayList)request.getAttribute("hierarchy")).get(j)%>" name="hierarchy" />      
					<% }
							%>        
							<input type="hidden" value="<%=request.getAttribute("descrittore")%>" name="descrittore" />
							<%
					
					}%>
			<input type="hidden" value="" name="descrittoreHidden">
		</form>
		
			<form method="post" name="editOperation" id="editOperations" action="/ThesaurusWorkBench/CtrlConcept" >
			<p> <% if(request.getAttribute("descrittore")!=null)
					{
				 %>
				   <input type="hidden" name="descrittoreHidden" value="<%=request.getAttribute("descrittore")%>"/>
				   
				   <div id="prefLabel" class="element">
							<div class="list_element">
								<h1>Preferred Label</h1>
									<div id="nomePrefLabel" class="element_of_predicate"><%=request.getAttribute("concept")%></div>
							</div>
							<div class="edit">	
										<input type="hidden" value="<%=request.getAttribute("concept")%>" name="displayName"/>
										<input type ="submit"  name="cmdAzione" id="open" class="buttonEdit" value="edit PrefLabel"/>
						    </div>
				    </div>
				    
				    <div id="broader" class="element">
							<div class="list_element">
								<h1>Broader Concept</h1>
									
			<div id="nomeBroader" class="element_of_predicate">
							<% if(request.getAttribute("broader")!=null) 
								{	
									String[] gerarchia = ((String)((ArrayList)request.getAttribute("hierarchy")).get(0)).split("/");
									String broader = gerarchia[gerarchia.length-2];
									
								%>
									<h5><%=broader%></h5>	    
									<input type="hidden" value="<%=broader%>" name="displayBroader"/>
										    
										<%} 
									 else{%>
									       Top Concept
									       <input type="hidden" value="" name="displayBroader"/>
									     <%}%>
									</div>
									
									<div class="edit">
										<input type ="submit"  name="cmdAzione" id="openBroader" class="buttonEdit" value="edit Broader"/>
									
									</div>
									
							</div>
				    </div>
				    
				    
			<div id="altlabel" class="element">
							<div class="list_element">
								<h1>Alternative Labels</h1>
									
			<div id="sinonimi" class="element_of_predicate">
							<% if(request.getAttribute("altLabel")!=null) 
								{	
									ArrayList<String> sinonimi = ((ArrayList)request.getAttribute("altLabel"));
									for(int i=0; i<sinonimi.size(); i++)
									{%>
										<p><%= sinonimi.get(i) %></p>
									<%}
									
									    
								} 
								else{%>
									       
									       
									     <%}%>
									</div>
									
									<div class="edit">
										<input type ="submit"  name="cmdAzione" id="openAltLabel" class="buttonEdit" value="edit AltLabel"/>
									
									</div>
									
							</div>
				    </div>
				   
				   
				
				 
				<%}
				%>
				
			</form>
			
		<%}%> <!-- chisusa else -->
		
		
		
		</div>
		</div>
	

<div id="footer">
	
</div>

<script>
function errorFunction() {
		
	<% if(request.getAttribute("error")!=null)
	{
		if(request.getAttribute("error").toString().equals("-1"))
		{
			%>
			alert("non è un termine");
			<%
		}
		else if(request.getAttribute("error").toString().equals("-2"))
		{
			%>
			alert("il concept inserito è già esistente nel thesaurus");
			<%
		}
		else if(request.getAttribute("error").toString().equals("-3"))
		{
			%>
			alert("non puoi inserire un padre in un figlio");
			<%
		}
		else if(request.getAttribute("error").toString().equals("-4"))
		{
			%>
			alert("non puoi spostare te stesso sotto te stesso");
			<%
		}
	}
	%>
    
}
</script>

</body>
</html>