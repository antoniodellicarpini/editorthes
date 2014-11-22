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
		    	
		    	
		    	$.ajax({
	                url : "/ThesaurusWorkBench/CtrlMain",
	                type : "POST",
	                data : {
	                        term : "parametro"
	                },
	                
	                success : function(data) {
	                	
	                	jsonData = JSON.parse(data);
	            
	                	for (var i = 0; i < jsonData.length; i++) {
	                		
	                	    $("#thesauri").append('<div class="thes" onClick=selectThes("'+jsonData[i]+'")> <div class="nameThes">'+jsonData[i]+'</div></div>');
	                	}
	                	
	                	
	                	
	                	
	                        
	                }
		    	});
		    	
		    	
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
		    
		    
		    
		    $("#dialogAltLabel").dialog({
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

		    $("#openAltLabel").click(function () {
		        $("#dialogAltLabel").dialog('open');
		    });
		    
		    
		    $("#dialogImport").dialog({
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

		    $("#import").click(function () {
		        $("#dialogImport").dialog('open');
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
                type : "POST",
                data : {
                        term : request.term
                },
                dataType : "json",
                success : function(data) {
                        response(data);
                }
        });
		},
		select: function(event, ui) {
		   
		    document.getElementById("searchOperations").submit();
		    var originalEvent = event;
	        while (originalEvent) {
	            if (originalEvent.keyCode == 13)
	                originalEvent.stopPropagation();

	            if (originalEvent == event.originalEvent)
	                break;

	            originalEvent = event.originalEvent;
	        }
		}
        });
	});
	
	
	$(function() {
        $("#inputBroader").autocomplete({     
        source : function(request, response) {
        $.ajax({
                url : "/ThesaurusWorkBench/CtrlConcept",
                type : "POST",
                data : {
                        term : request.term
                },
                dataType : "json",
                success : function(data) {
                        response(data);
                }
        });
		},
		select: function(event, ui) {
		   
		    document.getElementById("submitBroader").click();
		    var originalEvent = event;
	        while (originalEvent) {
	            if (originalEvent.keyCode == 13)
	                originalEvent.stopPropagation();

	            if (originalEvent == event.originalEvent)
	                break;

	            originalEvent = event.originalEvent;
	        }
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
		
		
		$(document).ready(function() {
		    var max_fields      = 10; //maximum input boxes allowed
		    var wrapper         = $(".input_fields_wrap"); //Fields wrapper
		    var add_button      = $(".add_field_button"); //Add button ID
		    
		    var x = 1; //initlal text box count
		    $(add_button).click(function(e){ //on add input button click
		        e.preventDefault();
		        if(x < max_fields){ //max input box allowed
		            x++; //text box increment
		            $(wrapper).append('<div><input type="text" name="altlabels"/><a href="#" class="remove_field">Remove</a></div>'); //add input box
		        }
		    });
		    
		    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
		        e.preventDefault(); $(this).parent('div').remove(); x--;
		    });
		});
		
		
		
		
		
		$(document).on("keypress", 'form', function (e) {
		    var code = e.keyCode || e.which;
		    console.log(code);
		    if (code == 13) {
		        console.log('Inside');
		        e.preventDefault();
		        return false;
		    }
		});
		
		
		