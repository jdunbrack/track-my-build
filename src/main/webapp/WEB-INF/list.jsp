<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Choose Exile</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="/css/main.css">
		<link rel="stylesheet" href="/css/list.css">
	</head>
	<body>
		<a href="/logout">SWITCH POESESSID</a>
		<div class="container py-2 mt-4 col-8 mx-auto bg-dark border border-secondary">
			<h3 class="text-center">Which Exile are we tracking?</h3>
			<p>
				If you don't see the exile you want to track on this list, make sure you've
				created the character in Path of Exile! Otherwise, please wait a few moments and
				refresh the page.
			</p>
		</div>
		<div id="exiles-list" class="d-flex flex-row flex-wrap justify-content-around align-items-center p-5 mt-4 col-8 mx-auto bg-dark border border-secondary">
			<c:forEach items="${exiles}" var="exile">
				<div class="portrait my-1">
					<a href="/setExile/${exile.name}">
						<c:if test="${exile.ascendancy == 'None' }">
							<img src="/img/portrait/portrait-${exile.baseClass}.png">
							<span class="exile-name">
								<c:out value="${exile.name}"/>
							</span>
							<span class="exile-class">
								Level <c:out value="${exile.level}"/> <c:out value="${exile.baseClass}"/>
							</span>
						</c:if>
						<c:if test="${exile.ascendancy != 'None' }">
							<img src="/img/portrait/portrait-${exile.ascendancy}.png">
							<span class="exile-name">
								<c:out value="${exile.name}"/>
							</span>
							<span class="exile-class">
								Level <c:out value="${exile.level}"/> <c:out value="${exile.ascendancy}"/>
							</span>
						</c:if>

					</a>
				</div>
			</c:forEach>
		</div>
	</body>
</html>