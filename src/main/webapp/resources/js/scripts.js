function onLoginButtonClick(){
	var usernameValue=$("#usernameId").val();
	var passwordValue=$("#passwordId").val();
	$.ajax({
		    url : "oauth/token",
		    type : "GET",
		    data : {            	
		            "grant_type" : "password",
		            "client_id":"my-trusted-client",
		            "username":usernameValue,
		            "password":passwordValue
		            },
		    success : function(data) { 
		            $.ajax({
		            url : "oauth/token",
				    type : "GET",
				    data : {            	
				            "client_id":"my-trusted-client",
				            "grant_type":"refresh_token",
				            "refresh_token":data.refresh_token
				            }, 
				            success : function(data){
				            	 window.location = 'home.html?access_token='+data.access_token;
				            },
				            error : function() {
				            	 $("#loginMessage").html("<font color='red'>Error Found.Please logIn again </font>");
				            	 console.log("There was an error");
				            } 
		        	 });		        
		        	 
		         },
		    error : function() {
		        	 $("#loginMessage").html("<font color='red'>Error Found with username or password.Please try again </font>");
		                console.log("There was an error");
		            } 

		        });
	} 
function onUploadClick() {
	var data = new FormData();
	data.append("file", csvfile.files[0]);
	var filename="";
	var fileNameValue = $("#csvfile").val();
	if(!fileNameValue)
		alert('Please select file.');
	else{
	$.ajax({
        xhr: function(){
        	//Get XmlHttpRequest object
        	var xhr = new window.XMLHttpRequest();
			xhr.upload.addEventListener('progress',function(e){
				 if(e.lengthComputable){			    
		   		 var percent=Math.round((e.loaded/e.total)*100);
		   		 if (percent > 50) {
	   				$("#message").html("<font color='#0033cc'>File Upload is in progress .. </font>");
		   		 			}
			        	 }
			       	});
			         return xhr;
				},
			    beforeSend: function( xhr ) {
			    //Reset alert message
			    $("#message").empty();
			     },
 				url:'uploadFile',
				data: data,
		    	cache: false,
		    	contentType: false,
		    	processData: false,
		    	type:'POST',
				success: function(response){
				if(response.status == "200"){
					filename=response.filename;
					$('input[type=file]').val('');	
					$("#message").html("<font color='#d9ac26'>"+response.filename+" "+response.message+ "</font>");
					$("#searchFormDiv").show();
						}					
					},				
				error : function(response) {
					$("#message").html("<font color='red'> ERROR: unable to upload file</font>");
					}	
      			  });
       	} 
	}

function onSearchByGenderClick() {	
	var totalPassengers=0;
	var records="";
	var genderValue=$("#searchByGender").val();
	 var count1=0;
     var count2=0;
     var count3=0;
     var gridData=null;
    
	if(genderValue.length==0)
		{
			alert('Please enter valid Gender value that can be male or female');
		}
	else{
     $.ajax({
    	 url : "search",
         type : "GET",
         data : {            	
                "searchByGender" : genderValue
            },
         success : function(data) { 
            totalPassengers=data.data.length;
            $('#jqxWidget').show();
            var generaterow = function() {
            	var row = {};
                var classIndex = Math.floor(Math.random() *data.data.length);
                var nameIndex = Math.floor(Math.random() * data.data.length);
                var ageIndex = Math.floor(Math.random() * data.data.length);
                var sibspIndex = Math.floor(Math.random() * data.data.length);
                var parchIndex = Math.floor(Math.random() * data.data.length);
                var ticketIndex = Math.floor(Math.random() * data.data.length);
                var fareIndex = Math.floor(Math.random() * data.data.length);
                var cabinIndex = Math.floor(Math.random() * data.data.length);
                var embarkedIndex = Math.floor(Math.random() * data.data.length);
                row["passenger_id"] = Math.floor(Math.random() * data.data.length)
                row["passenger_class"] = data.data[classIndex].passenger_class;
                row["name"] = data.data[nameIndex].name;
                row["sex"] = genderValue;
                row["age"] = data.data[ageIndex].age;
                row["sib_sp"] = data.data[sibspIndex].sib_sp;
                row["parch"] = data.data[parchIndex].parch;
                row["ticket"] = data.data[ticketIndex].ticket;
                row["fare"] = data.data[fareIndex].fare;
                row["cabin"] = data.data[cabinIndex].cabin;
                row["embarked"] = data.data[embarkedIndex].embarked;             
                return row;
             }
             var addfilter = function (value) {
            	 var filtergroup = new $.jqx.filter();
            	 var filter_or_operator = 1;
            	 var filtercondition = 'contains';
            	 var filtervalue1 = value;
            	 var filter1 = filtergroup.createfilter('stringfilter', filtervalue1, filtercondition);
            	 filtergroup.addfilter(filter_or_operator, filter1);
            	  // add the filters.
            	 $("#jqxgrid").jqxGrid('addfilter', 'name', filtergroup);
            	  // apply the filters.
            	 $("#jqxgrid").jqxGrid('applyfilters');
            };
             var source =
             {
        		 datatype: "json",
        		 datafields: [
        			 { name: 'passenger_id',type: 'int'},
        		     { name: 'passenger_class' , type: 'string'},
        		     { name: 'name', type: 'string' },
        		     { name: 'sex' , type: 'string'},
        		     { name: 'age', type: 'string' },
        		     { name: 'sib_sp' , type: 'string'},
        		     { name: 'parch', type: 'string' },
        		     { name: 'ticket' , type: 'string'},
        		     { name: 'fare' , type: 'string'},
        		     { name: 'cabin' , type: 'string'},
        		     { name: 'embarked', type: 'string' }
        		        
        		    ],
        		 cache: false,
        		 root: 'rows',
        		 url: 'getPassengers?searchByGender='+genderValue,
        		 async: true,
        		 filter: function () {
                        // update the grid and send a request to the server.
                   $("#jqxgrid").jqxGrid('updatebounddata', 'filter');  
                  var filter = $("#jqxgrid").jqxGrid('getfilterinformation');
    			  if(filter.length>0){
    				  var filter0 = filter[0];
        			  var value = filter0.filter.getfilters()[0].value;
        			  var  condition = filter0.filter.getfilters()[0].condition;
        			  var datafield = filter0.filtercolumn;
    				  $.ajax({
                    	  type: "GET",
                          url: 'getFilteredPassengers',
                          data : { 
                        	  "searchByGender":genderValue,
                              "filterValue" : value,
                              "filterDataField":datafield,
                              "filterCondition":condition
                          },
                          success: function (gridResult) {
                               gridData=gridResult.data;
                               drawPieChart(gridData,genderValue);
                              
                            },
                            error: function () {
                                
                            }
                        });
    			  }else{
    				  
    				  drawPieChart(data.data,genderValue);        				
    			  }
                   
                 }, 	
                 sort: function() {
        		   	// update the grid and send a request to the server.
                	 $("#jqxgrid").jqxGrid('updatebounddata', 'sort');	
                 },        		   	        		 
                 sortcolumn: 'passenger_id',
                 sortdirection: 'asc',  
                 beforeprocessing: function (data) {
                	 if (data != null)
                	 {
                		 source.totalrecords = data.totalRows;
                	 }	        		    		  
                 },	                   
                 addrow: function(rowid, rowdata, position, commit) {
                	 var filter = $("#jqxgrid").jqxGrid('getfilterinformation');
	     			 var filter0 = null;
	     			 var value = null;
	     			 var condition =null;
	     			 var datafield = null;
	    			 if(filter.length>0){
	    				   filter0 = filter[0];
	        			   value = filter0.filter.getfilters()[0].value;
	        			   condition = filter0.filter.getfilters()[0].condition;
	        			   datafield = filter0.filtercolumn;
	    			 }
	        			  
                	 var data = $.extend({}, rowdata);
                     $.ajax({
                    	 url: 'addPassenger',
                         data: {
                        	 data:data,
                        	  "searchByGender":genderValue,
                        	  filterValue:value,
                        	  filterCondition:condition,
                        	  filterDataField:datafield
                         },
                         type: 'POST',
                         success: function(data, textStatus, jqXHR) {
                        	 var newRowId = data.newId != undefined ? parseInt(data.newId) : 0;
                        	 if (!newRowId)
                        		 commit(false);
                        	 else{			
                        		 	commit(true, newRowId);	
                        		 	$('#jqxgrid').jqxGrid('updatebounddata');
    								alert(data.message+' with passenger id-'+newRowId);
    								if(data.data!=null && data.data.length>0){
    									drawPieChart(data.data,genderValue);
    								}
    							}
                         },
                         error: function(jqXHR, textStatus, errorThrown) {
                                commit(false);
                            }
                          });
                 },
                 deleterow: function(rowid, commit) {
                	 var filter = $("#jqxgrid").jqxGrid('getfilterinformation');
	     			 var filter0 = null;
	     			 var value = null;
	     			 var condition =null;
	     			 var datafield = null;
	    			 if(filter.length>0){
	    				   filter0 = filter[0];
	        			   value = filter0.filter.getfilters()[0].value;
	        			   condition = filter0.filter.getfilters()[0].condition;
	        			   datafield = filter0.filtercolumn;
	    			 }
                	 $.ajax({
                		 url: 'deletePassenger',
                		 data: {
                			 row: rowid,
                			 "searchByGender":genderValue,
                			  filterValue:value,
                        	  filterCondition:condition,
                        	  filterDataField:datafield
                         },
                         type: 'POST',
                         success: function(data, textStatus, jqXHR) {
                                commit(true);
                                $('#jqxgrid').jqxGrid('updatebounddata');
                            	alert(data.message);
								if(data.data!=null && data.data.length>0){
									drawPieChart(data.data,genderValue);
								}
                         },
                         error: function(jqXHR, textStatus, errorThrown) {
                                commit(false);
                                alert(data.message);
                          }
                        });
                  },
                  updaterow: function (rowid, rowdata, commit) {
                        // synchronize with the server - send update command
                	  var data = "update=true&passenger_id=" + rowdata.passenger_id+ "&name=" + rowdata.name + "&age=" + rowdata.age;
                      $.ajax({
                    	  dataType: 'json',
                          url: 'updatePassenger',
                          data: data,
                          success: function (data, status, xhr) {
                                // update command is executed.
                                commit(true);
                                $('#jqxgrid').jqxGrid('updatebounddata');
                                alert(data.message);
                            },
                            error: function () {
                                // cancel changes.
                                commit(false);
                                alert(data.message);
                            }
                        });
                    }
                    
        		};
        		var dataAdapter = new $.jqx.dataAdapter(source, {	
        			
        			      formatData: function (data) {
        			    	  if(data.filterscount==0){
        			            $.extend(data, {
        			            	filtervalue0: null,
        			            	filtercondition0: null,
        			            	filterdatafield0: null,
        			            	filteroperator0:null
        			            });	        			           
        			    	  }
        			    	  return data;
        			        },
        			    
        			loadError: function(xhr, status, error)
        			{
        				alert(error);
        			}
        		}
				);
        		$("#jqxgrid").jqxGrid(
        				{
        				    width: 900,
        				    theme: 'energyblue',
        				    source: dataAdapter,
        				    sortable: true,
        				    autoheight: true,
        				    pageable: true,
        				    columnsresize: true,
        				    virtualmode: true,
        				    editable: true,
        				    sorttogglestates:1,
        				    filterable: true,
        				   	showfilterrow: true,
        				    rendergridrows: function (params) {
        	                    return params.data;
        	                },	        	             
        				    columns: [
        				        { text: 'Passenger ID',editable: false, datafield: 'passenger_id', width: 120 },
        				        { text: 'Class',editable: false, datafield: 'passenger_class', width: 120 },
        				        { text: 'Name', datafield: 'name', columntype: 'textbox',width: 250 },
        				        { text: 'Gender',editable: false, sortable: false,datafield: 'sex', width: 120},
        				        { text: 'Age',datafield: 'age', columntype: 'textbox',minwidth: 120 },
        				        { text: 'Sib_sp', editable: false,datafield: 'sib_sp', minwidth: 120 },
        				        { text: 'Parch',editable: false, datafield: 'parch', minwidth: 120 },
        				        { text: 'Ticket',editable: false, datafield: 'ticket', minwidth: 120 },
        				        { text: 'Fare', editable: false,datafield: 'fare', minwidth: 120 },
        				        { text: 'Cabin',editable: false, datafield: 'cabin', minwidth: 120 },
        				        { text: 'Embarked', editable: false,datafield: 'embarked', minwidth: 120 }
        				    ]
        				}); 
        		  $("#addrowbutton, #deleterowbutton").jqxButton({
                      width: 150
                  });
        		  
        		  $('#addrowbutton').click(function() {
                      var datarow = generaterow();
                      var commit = $("#jqxgrid").jqxGrid('addrow', null, datarow);
                  });  
        		  $('#deleterowbutton').click(function() {
                     
                      var selectedrowindex = $('#jqxgrid').jqxGrid('getselectedrowindex');
                      var data = $('#jqxgrid').jqxGrid('getrowdata', selectedrowindex);
                      var id = data.passenger_id;
                      var commit = $("#jqxgrid").jqxGrid('deleterow', id);
                  });     
        		  
        		  drawPieChart(data.data,genderValue);   
           } ,
            error : function() {
                console.log("There was an error");
            } 

        });
	}
	
}

function drawPieChart(gridData,genderValue){
	 var count1=0;
     var count2=0;
     var count3=0;
     for(prop in gridData){
      	if(gridData[prop].passenger_class === '1')
      		{
      		count1=count1+1;
      		}
      	else if(gridData[prop].passenger_class === '2')
      		{count2=count2+1;
      		}
      	else if(gridData[prop].passenger_class === '3')
      		{
      		count3=count3+1;
      		}
      	
      } 
    var passenger={chart1:{data:null}};
 	var percentTotal=0;
 	passenger.chart1.data={
 			chart:{
 				plotBackgroundColor: null,
 		        plotBorderWidth: null,
 		        plotShadow: false,
 		        type: 'pie'
 			},
 		    title: {
 		        text: genderValue+ ' % of total Titanic Passenger as per Class'
 		    },
 		   tooltip: {
 		        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
 		    },
 		   plotOptions: {
 		        pie: {
 		            allowPointSelect: true,
 		            cursor: 'pointer',
 		            dataLabels: {
 		                enabled: false
 		            },
 		            showInLegend: true
 		        }
 			},
 			series: [{
 		        name: 'Passengers',
 		        colorByPoint: true,
 		        data: [{
 		            name: 'Class 1',
 		            y: count1
 		           
 		        }, {
 		            name: 'Class 2',
 		            y: count2
 		        }, {
 		            name: 'Class 3',
 		            y: count3
 		        }]
 		    }]
 	};
 	var series_obj=passenger.chart1.data.series[0]['data'];
 	for(prop in series_obj ){
 		if(typeof series_obj[prop]==='number'){
	    	  percentTotal+=series_obj[prop];
			 }else if(typeof series_obj[prop]==='object'){
				 for(prop2 in series_obj[prop]){
					if(typeof series_obj[prop][prop2]==='number'){
						percentTotal+=series_obj[prop][prop2];
					}
				 }
			 }
 	}
    if(percentTotal === gridData.length){
      		passenger.chart1.piechart1=new Highcharts.chart('pieContainer',passenger.chart1.data);
      	}
}