<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><c:out value="${exile.name}"/></title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
		<link rel="stylesheet" href="/css/main.css">
		<link rel="stylesheet" href="/css/sheet.css">
	</head>
	<body>
		<div id="portrait">
			<c:if test="${exile.ascendancy == 'None' }">
				<img id="portrait-thumbnail" src="/img/portrait/portrait-${exile.baseClass}.png">
				<span id="exile-name">
					<c:out value="${exile.name}"/>
				</span>
				<span id="exile-class">
					Level <c:out value="${exile.level}"/> <c:out value="${exile.baseClass}"/>
				</span>
			</c:if>
			<c:if test="${exile.ascendancy != 'None' }">
				<img id="portrait-thumbnail" src="/img/portrait/portrait-${exile.ascendancy}.png">
				<span id="exile-name">
					<c:out value="${exile.name}"/>
				</span>
				<span id="exile-class">
					Level <c:out value="${exile.level}"/> <c:out value="${exile.ascendancy}"/>
				</span>
			</c:if>
		</div>
		<a class="small" href="/exileList">SWITCH CHARACTER</a>
		<div id="passive-tree" class="col-3">
			Passive Tree Link:<br/>
			(to official site)<br/>
			<form:form class="form-group" action="/passives" method="get" modelAttribute="passiveTree">
				<form:input path="exile" type="hidden" value="${exile.id}"/>
				<form:select path="level">
					<c:forEach items="${exile.passiveTrees}" var="tree">
						<option value="${tree.key}"><c:out value="${tree.key}"/></option>
					</c:forEach>
				</form:select>
				<input type="submit" value="Go" class="btn btn-secondary btn-small">
			</form:form>
		</div>
		
		<div id="body" class="col-6 mx-auto">
			<div class="sub-head text-center py-1 mt-5 mb-5 bg-dark border border-secondary">
				<p>
					Tracking <span class="font-weight-bold"><c:out value="${exile.name}"/></span>.
					Started tracking this exile on <c:out value="${exile.createdAt}"/>.
				</p>
			</div>
	
			<a href="#" class="pane-toggle"><img src="/img/level-up.png"></a>		
			(Initial) Level: <c:out value="${exile.level}"/>
			<div class="equip-div">
				<div class="inventory-pane">
					<c:forEach items="${exile.items}" var="item">
						<c:if test="${item.key != 'Weapon2' && item.key != 'Offhand2'}">
							<img data-toggle="popover" data-content="${item.value.htmlData}" class="gear" id="${item.key}" src="${item.value.image}"/>
						</c:if>
					</c:forEach>
				</div>
			</div>
			
			<c:forEach items="${milestoneLevels}" var="milestone">
				<a href="#" class="pane-toggle"><img src="/img/level-up.png"></a>		
				Level: <c:out value="${milestone}"/>    $('[data-toggle="popover"]').popover({
        html: true
    });
				<div class="equip-div d-flex justify-content-center align-items-center">
					<c:forEach items="${gearSwaps[milestone]}" var="gearSwap">
						<div class="gear-swap">
							<c:if test="${gearSwap.oldGear != null}">
								<img data-toggle="popover" data-content="${gearSwap.oldGear.htmlData}" class="gear" src="${gearSwap.oldGear.image}">
								<img class="arrow" src="/img/arrow.png">
							</c:if>
							<c:if test="${gearSwap.oldGear == null}">
								First equipped:
							</c:if>
							<img data-toggle="popover" data-content="${gearSwap.newGear.htmlData}" class="gear" src="${gearSwap.newGear.image}">
						</div>
					</c:forEach>
				</div>
			</c:forEach>
			
		</div>
		<script src="https://code.jquery.com/jquery-3.4.0.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
		<script src="/js/sheet.js"></script>
	</body>
</html>



















