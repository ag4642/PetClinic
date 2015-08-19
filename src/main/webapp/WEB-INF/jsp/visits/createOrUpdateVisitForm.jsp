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
	<script>
		$(function() {
			$("#date").datepicker({
				dateFormat : 'yy/mm/dd'
			});
		});
	</script>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>
			<c:if test="${visits['new']}">New </c:if>
			<c:if test="${!visits['new']}">Edit </c:if>
			Visit
		</h2>
		<c:choose>
			<c:when test="${visits['new']}">
				<c:set var="method" value="post" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="put" />
			</c:otherwise>
		</c:choose>

		<b>Pet</b>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Birth Date</th>
					<th>Type</th>
					<th>Owner</th>
				</tr>
			</thead>
			<tr>
				<td><c:out value="${visits.pet.name}" /></td>
				<td><joda:format value="${visits.pet.birthDate}"
						pattern="yyyy/MM/dd" /></td>
				<td><c:out value="${visits.pet.type.name}" /></td>
				<td><c:out
						value="${visits.pet.owner.firstName} ${visits.pet.owner.lastName}" /></td>
			</tr>
		</table>

		<form:form modelAttribute="visits" method="${method}">

			<petclinic:inputField label="Date" name="date" />
			<petclinic:inputField label="Description" name="description" />
			<petclinic:inputField label="Diagnosis" name="diagnosis" />
			<div class="control-group">
				<petclinic:selectField name="vet" label="Vets" names="${vets}"
					size="6" />
				<petclinic:selectField name="vet2" label="Vets" names="${vets}"
					size="6" />
			</div>
			<div class="form-actions">
				<input type="hidden" name="petId" value="${visits.pet.id}" />
				<c:if test="${!visits['new']}">
					<button type="submit">Update Visit</button>
				</c:if>
				<c:if test="${visits['new']}">
					<button type="submit">Add Visit</button>
				</c:if>
			</div>
		</form:form>

		<br /> <b>Previous Visits</b>
		<table style="width: 333px;">
			<tr>
				<th>Date</th>
				<th>Description</th>
			</tr>
			<c:forEach var="visits" items="${visits.pet.visits}">
				<c:if test="${!visits['new']}">
					<tr>
						<td><joda:format value="${visits.date.plusDays(1)}"
								pattern="yyyy/MM/dd" /></td>
						<td><c:out value="${visits.description}" /></td>
						<td><c:out value="${visits.diagnosis}" /></td>
						<td><c:out value="${visits.vet}" /></td>
					</tr>
				</c:if>
			</c:forEach>
		</table>

	</div>
	<jsp:include page="../fragments/footer.jsp" />
</body>

</html>
