<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Titanic Passenger Details</title>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/scripts.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/indexStyle.css"/>">
</head>
<body>
	<div id="loginFormDiv" class="login-container">
		<form id="loginForm" method="get" onsubmit="return false;">
			<h2>Login</h2>
			<label for="uname"><b>Username</b></label> <input type="text"
				name="usernameField" placeholder="Enter username"
				class="form-control" id="usernameId"> <br /> <br /> <label
				for="psw"><b>Password</b></label> <input type="password"
				name="passwordField" placeholder="Enter Password"
				class="form-control" id="passwordId"> <br /> <br />
			<button type="button" id="loginButton" value="Submit"
				onclick="onLoginButtonClick()">Login</button>
		</form>
		<div id="loginMessage"></div>
	</div>
</body>
</html>
