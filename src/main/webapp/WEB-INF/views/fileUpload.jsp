<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" name="viewport" content="width=device-width, initial-scale=1">
<title>Titanic Passengers Detail</title>
<link rel="stylesheet" href="<c:url value="/resources/css/jqx.base.css"/>">
<!-- Include css styles here -->
<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/jqx.energyblue.css"/>">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<!-- Include jqxgrid supporting js files -->
<script src="<c:url value="/resources/js/jqxcore.js"/>"></script>
<script src="<c:url value="/resources/js/jqxdata.js"/>"></script>
<script src="<c:url value="/resources/js/jqxbuttons.js"/>"></script>
<script src="<c:url value="/resources/js/jqxscrollbar.js"/>"></script>
<script src="<c:url value="/resources/js/jqxmenu.js"/>"></script>
<script src="<c:url value="/resources/js/jqxlistbox.js"/>"></script>
<script src="<c:url value="/resources/js/jqxdropdownlist.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.selection.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.columnsresize.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.filter.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.sort.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.pager.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.grouping.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.columnsreorder.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.edit.js"/>"></script>
<script src="<c:url value="/resources/js/jqxnumberinput.js"/>"></script>
<script src="<c:url value="/resources/js/jqxdata.export.js"/>"></script>
<script src="<c:url value="/resources/js/jqxgrid.export.js"/>"></script>
<script src="<c:url value="/resources/js/scripts.js"/>"></script>
<!-- Include Hi Chart library -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

</head>
<body>
	<div class="main-container">
	<div class="header">
		<h1>Titanic Passenger Details</h1>
	</div>
	
	<div class="fileuploadandsearch-container">
		<div class="fileupload-container">
			<form id="uploadFile" method="post" onsubmit="return false;" enctype="multipart/form-data">
				 <input type="file" accept=".csv" name="csvfile" id="csvfile" required>
				<br/>
				<br />
				<button id="uploadBtn" value="Submit" onclick="onUploadClick()">Upload</button>
				<div id="progressBox">
					<div id="percent"></div>
				</div>
				<br />
				<div id="message"></div>
			</form>
		</div>
		<div id="searchFormDiv" class="search-container" style="display:none;">
		<form id="searchForm" method="post" onsubmit="return false;">
			   <input type="text" name="searchField"
				placeholder="Search By Gender" class="form-control" id="searchByGender">
				<br />
				<br/>
				<button type="button" id="searchButton" value="Submit" onclick="onSearchByGenderClick()">Search</button>
		</form>
	</div>
	</div>
	
	<div id='jqxWidget' class="details-container" style="display: none;">
		<div class="jqxgrid-container">
			<div id="jqxgrid"></div>
			<div id="jqxCrud">
				<div>
					<input id="addrowbutton" type="button" value="Add New Row" />
					<input id="deleterowbutton" type="button"
						value="Delete Selected Row" />
				</div>
			</div>
		</div>
		<div class="piesection">
		<div id="pieContainer" ></div>
		</div>
	</div>
</div>
</body>
</html>