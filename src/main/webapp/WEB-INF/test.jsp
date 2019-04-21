<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.scrollable {
		max-height: 500px;
		overflow-y: scroll;
		overflow-x: hidden;
	}
</style>
</head>
<body>
	<h1>Posted JSON data: </h1>
	<div class="scrollable">
	<c:out value="${data}"/>
	</div>
</body>
</html>