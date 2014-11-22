$(function () {
		    $("#dialog").dialog({
		        autoOpen: false,
		         modal: true,
		        height: 500,
		        width: 500,
		        show: {
		            
		            duration: 400,
		        },
		        
		       
		        open: function (event, ui) {
		            $('#content');
		        }
		    });

		    $("#thesaurus").click(function () {
		        $("#dialog").dialog('open');
		    });
		    
		    $("#dialogBroader").dialog({
		        autoOpen: false,
		         modal: true,
		        height: 500,
		        width: 500,
		        show: {
		            
		            duration: 400,
		        },
		        
		       
		        open: function (event, ui) {
		            $('#content');
		        }
		    });

		    $("#openBroader").click(function () {
		        $("#dialogBroader").dialog('open');
		    });
		});
	
	//la seguente funzione è per la form name=CRUDOperation	
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
					if(submitActor.value=="Delete Concept")
					{
						if( confirm("sei sicuro di voler eliminare il concept corrente?"))
							{
								return true;
							}
						else return false;
						
					}
					if(submitActor.value=="Create Concept")
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
					
					
					
					if(submitActor.value=="showConcept")
					{
						return true;
					}

			      return false;
			    });

			    $submitActors.click( function( event )
			    {
			      submitActor = this;
			    });

			  } );
		
	//la seguente funzione è per la form search operation
	$(function()
			  {
			    var submitActor = null;
			    var $form = $( '#searchOperations' );
			    var $submitActors = $form.find( 'input[type=submit]' );
			    $form.submit( function( event )
			    {
						return true;
			    });

			  } );
	
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
	
	
	$(function() {
        $("#inputBroader").autocomplete({     
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
	
	//funzoione per la form di editOperation
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
				      
				      
				      if(submitActor.value=="edit PrefLabel")
						{
							var person = prompt("Modifica la prefLabel",document.editOperation.displayName.value); 
						    if (person!= null) {
						    	document.editOperation.displayName.value=person;
						    	return true;	
						    }
						    else
						    {
						    	return false;
						    }
		
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
		
	
		
		
		
		function selectThes(str) {
			
			method = "post"; // Set method to post by default if not specified.
		    // The rest of this code assumes you are not using a library.
		    // It can be made less wordy if you use one.
		    var form = document.createElement("form");
		    form.setAttribute("method", method);
		    form.setAttribute("action", "/ThesaurusWorkBench/CtrlMain");
		    
		    var hiddenField = document.createElement("input");
		    hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "cmdAzione");
            hiddenField.setAttribute("value", str);
            form.appendChild(hiddenField);
		   
		    document.body.appendChild(form);
		    form.submit();
		}
		
