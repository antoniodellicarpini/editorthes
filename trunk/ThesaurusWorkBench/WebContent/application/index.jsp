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
	<div id="dialogSettings">
		<h3>Settings:</h3>
		
		<form method="post" name="formsettings" id="formsettings" action="/ThesaurusWorkBench/CtrlSettings">
		<div class="content">
		<input type="text" id="address" name="address" value="" />
		<input type="submit" value="switch" />
		</div>
     	</form>

		
			</div>


	<div id="dialogBroader">
	<form method="post" name="editBroader" id="editBroader" action="/ThesaurusWorkBench/CtrlConcept" >
	    <div class="content">
			<label>Choose new broader:</label>
			<input type="text" id="inputBroader" name="displayBroader" value="" />
			<input Style="visibility:hidden" type ="submit" id="submitBroader" name="cmdAzione"  value="edit Broader"/>
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
					<div><input type="text" name="altlabels" value="<%= sinonimi.get(i) %>"></div>
			
				<%}
									
			} %>
				
				<div class="input_fields_wrap">
    			  <div>
    			   <input type="text" name="altlabels">
    			  </div>
    			  <button class="add_field_button">Add More Fields</button>
			 </div>
				
			<input type ="submit" name="cmdAzione"  value="edit AltLabel"/>
			<input type="hidden" name="descrittoreHidden" value="<%=request.getAttribute("descrittore")%>"/>
	  </div>
	 </form>
	</div>


<div id="header">
<div id="thesaurus" class="vocimenu">THESAURUS</div>

<% if(request.getSession().getAttribute("beanThes")==null) { %>
	<div id="disableImport" class="vocimenu">IMPORT</div>
	<form method="post" name="formExport" id="formExport" action="/ThesaurusWorkBench/CtrlExport">
	<div id="disableExport" class="vocimenu">EXPORT</div>
	</form>
	<%
	}
else{%>
	
	<div id="import" class="vocimenu">IMPORT</div>
	<form method="post" name="formExport" id="formExport" action="/ThesaurusWorkBench/CtrlExport">
	<div id="export" class="vocimenu">EXPORT</div>
	</form>
    
   <% }%>

	<div id="settings" class="vocimenu">SETTINGS</div>
	
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
			<% int margin=10;
			if ((request.getAttribute("descrittore")==null)&&(request.getAttribute("elencoConcept")!=null))
					{ %>
					<input type ="submit" style="border-radius:0px; background-color:#0092ca;" class="concept" name="loadConcept" value="Root"/>
					<br/>
			
			<%}
							
			 if(request.getAttribute("descrittore")!=null)
					{ 
			         String[] s= ((String)(((ArrayList)request.getAttribute("hierarchy"))).get(0)).split("/");
				    %>
				<input type ="submit" style="border-radius:0px; background-color:#0092ca;" class="concept" name="loadConcept" value="Root"/>
				<br/> 
				<% 
				for(int i=0;i<s.length-1;i++)
				{ 
					margin=margin+10;
				%>
					
					<input style="margin-left:<%=margin%>px" type ="submit" class="concept" name="loadConcept" value="<%= s[i]%>"/> 
					<br/>
				<%}%>
				<input type ="submit" id="conceptB" style="margin-left:<%=margin+10%>px; background-color:#ff9900;" class="concept" value="<%= request.getAttribute("concept")%>" />
				<br/>
				
				<%	}
				 %>
			<% if( request.getAttribute("elencoConcept")!=null)
				{
				%>
		     	 <%	 ArrayList elencoConcept= (ArrayList) request.getAttribute("elencoConcept");
				 
				 for (int i=0; i<elencoConcept.size(); i++)
				   {  	 
				 %>
				 
        <input type ="submit" style="margin-left:<%=margin+10%>px; background-color:#76b0ab;" class="concept" name="loadConcept" value="<%= elencoConcept.get(i)%>"/> 
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
			 { %><div id="headerDescription"> Seleziona un Thesaurus </div>
			<%}	
				                          
		else{ %> 
				
			
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
		     			<% if(request.getAttribute("descrittore")!=null)
		     			{%> 
		     				<input type ="submit" class="buttonCD" id="delete"   name="cmdAzione" value="Delete Concept">
		     			<%}
		     			 else { %>
		     				 <input type ="submit" class="buttonCD" id="delete"   name="cmdAzione" value="Delete Concept" disabled>
		     				
		     			<% } %>
	                 		   
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
								<p class="labelSection">Preferred Label: <b> <%=request.getAttribute("concept")%></b></p>
									<!--  <div id="nomePrefLabel" class="element_of_predicate"><%=request.getAttribute("concept")%></div>-->
							</div>
							<div class="edit">	
										<input type="hidden" value="<%=request.getAttribute("concept")%>" name="displayName"/>
										<input type ="submit"  name="cmdAzione" id="open" class="buttonEdit" value="edit PrefLabel"/>
						    </div>
				    </div>
				    
				    <div id="broader" class="element">
							<div class="list_element">
								<p class="labelSection">Broader Concept:
									
			
							<% if(request.getAttribute("broader")!=null) 
								{	
									String[] gerarchia = ((String)((ArrayList)request.getAttribute("hierarchy")).get(0)).split("/");
									String broader = gerarchia[gerarchia.length-2];
									
								%>
									<b><%=broader%></b>	    
									<input type="hidden" value="<%=broader%>" name="displayBroader"/>
										    
										<%} 
									 else{%>
									       <b>Top Concept</b>
									       <input type="hidden" value="" name="displayBroader"/>
									     <%}%>
			
			</p>						
																		
							</div>
							<div class="edit">
										<input type ="submit"  name="cmdAzione" id="openBroader" class="buttonEdit" value="edit Broader"/>
									
									</div>
				    </div>
				    
				    
			<div id="altlabel" class="element">
							<div class="list_element">
								<p class="labelSection">Alternative Labels:
									
			
							<% if(request.getAttribute("altLabel")!=null) 
								{	
									ArrayList<String> sinonimi = ((ArrayList)request.getAttribute("altLabel"));
									
									for(int i=0; i<sinonimi.size(); i++)
									{%>
									<%if(i==0) 
									{
									%>
										<b><%= sinonimi.get(i) %></b>
									<%}
									else{%>
										<b>, <%= sinonimi.get(i) %></b>
									<%} %>	
									
								<%}
									
									    
								} 
								else{%>
									       
									       
									     <%}%>
								</p>
							</div>
							<div class="edit">
										<input type ="submit"  name="cmdAzione" id="openAltLabel" class="buttonEdit" value="edit AltLabel"/>
									
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
		else if(request.getAttribute("error").toString().equals("10"))
		{
			%>
			alert("connessione assente");
			<%
		}
	}
	%>
    
}
</script>

</body>
</html>