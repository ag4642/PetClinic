<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<html lang="en">

<jsp:include page="../fragments/staticFiles.jsp" />


<body>
	
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Visits</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Pet Name</th>
					<th>Type</th>
					<th>Owner</th>
					<th>Date</th>
					<th>Description</th>
				</tr>
			</thead>
			<tr>
				<td><c:out value="${visit.pet.name}" /></td>
				<td><c:out value="${visit.pet.type.name}" /></td>
				<td><joda:format
						value="${visit.pet.owner.firstName} ${visit.pet.owner.lastName}" /></td>
				<c:forEach var="visit" items="${visit.pet.visits}">
					<c:if test="${!visit['new']}">
						<tr>
							<td><joda:format value="${visit.date}" pattern="yyyy/MM/dd" /></td>
							<td><c:out value="${visit.description}" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</tr>
		</table>

	</div>
	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>