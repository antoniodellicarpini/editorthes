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
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
		<script>
		$(function()
			  {
			    var submitActor = null;
			    var $form = $( '#crudOperations' );
			    var $submitActors = $form.find( 'input[type=submit]' );
			    $form.submit( function( event )
			    {
			      if ( null === submitActor )
			      {
			        // If no actor is explicitly clicked, the browser will
			        // automatically choose the first in source-order
			        // so we do the same here
			        submitActor = $submitActors[0];
			      }
					if(submitActor.value=="delete")
					{
						if( confirm("sei sicuro di voler eliminare il concept corrente?"))
							{
								return true;
							}
						else return false;
						
					}
					if(submitActor.value=="create")
					{
						var person = prompt("Please enter description","Insert descr"); 
					    if (person!= null) {
					    	document.crudOperation.descrittoreHidden.value=person;
					    	return true;	
					    }
					    else
					    {
					    	return false;
					    }
						
					}

			      return false;
			    });

			    $submitActors.click( function( event )
			    {
			      submitActor = this;
			    });

			  } );
		
		$(function()
				  {
				    var submitActor = null;
				    var $form = $( '#editOperations' );
				    var $submitActors = $form.find( 'input[type=submit]' );
				    $form.submit( function( event )
				    {
				      if ( null === submitActor )
				      {
				        // If no actor is explicitly clicked, the browser will
				        // automatically choose the first in source-order
				        // so we do the same here
				        submitActor = $submitActors[0];
				      }
						if(submitActor.value=="edit")
						{
							if( confirm("sei sicuro di voler modificare il concept corrente?"))
								{
									return true;
								}
							else return false;
						}

				      return false;
				    });

				    $submitActors.click( function( event )
				    {
				      submitActor = this;
				    });

				  } );
	
	</script>
</head>
<body onload="errorFunction()">
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
	<input type ="submit" name="cmdAzione" value="loadThes">
</form>

<form method="post" name="crudOperation" id="crudOperations" action="/ThesaurusWorkBench/CtrlConcept" >
	<ul>
		<li><a href="#"><span><input type ="submit"  name="cmdAzione" value="create"></span></a></li>
		<li><a href="#"><span><input type ="submit"  name="cmdAzione" value="delete"></span></a></li>
	</ul>
		<input type="text" id="search" name="loadConcept"/><input type ="submit"  name="cmdAzione" value="showConcept">	
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

<div class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<!-- Column 1 start -->
			<form method="post" name="editOperation" id="editOperations" action="/ThesaurusWorkBench/CtrlConcept" >
			<p> <% if(request.getAttribute("descrittore")!=null)
					{
				 %>
				   <p>concept description for <%=request.getAttribute("concept")%> </p>
				   <input type="hidden" name="descrittoreHidden" value="<%=request.getAttribute("descrittore")%>"/>
				   <p>Display name</p>
				   <input type="text" name="displayName" value="<%=request.getAttribute("concept")%>"/>
				   <p>Hierarchy:</p>
				 <% 
					for (int j=0; j<((ArrayList)request.getAttribute("hierarchy")).size(); j++)
	   					{	
				%>        
						<p name="hierarchy"> <%=((ArrayList)request.getAttribute("hierarchy")).get(j)%> </p>
				
					<% }%>
					<p>Broader:</p>
					<%
					
					if(request.getAttribute("broader")!=null){
						for (int j=0; j<((ArrayList)request.getAttribute("broader")).size(); j++)
   						{
							%> 
							<input type="text" name="displayBroader" value="<%=((ArrayList)request.getAttribute("broader")).get(j)%>"/>      
							<%
   						}
						
					}
					else
					{
						%> 
						<input type="text" name="displayBroader" value=""/>      
						<%
						
					}
					
					
				 
					}
			else
			{
			%> Selezionare un descrittore
				<%
				}		%>
					</p>
					<input type ="submit"  name="cmdAzione" id="open" value="edit">
			</form>
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
	<script type="text/javascript">
	$(function() {
        $("#search").autocomplete({     
        source : function(request, response) {
        $.ajax({
                url : "/ThesaurusWorkBench/CtrlConcept",
                type : "GET",
                data : {
                        term : request.term
                },
                dataType : "json",
                success : function(data) {
                        response(data);
                }
        });
}
        });
	});
	</script>
</body>
</html>