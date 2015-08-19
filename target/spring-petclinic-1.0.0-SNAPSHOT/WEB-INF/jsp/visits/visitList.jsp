<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../fragments/staticFiles.jsp" />
<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Visits</h2>

		<datatables:table id="visits" data="${visits.visitList}" row="visit"
			theme="bootstrap2" cssClass="table table-striped" pageable="false"
			info="false">
			<datatables:column title="Pet Name">
				<c:out value="${visit.pet.name}" />
			</datatables:column>
			<datatables:column title="Pet Type">
				<c:out value="${visit.pet.type.name}" />
			</datatables:column>
			<datatables:column title="Owner">
				<c:out
					value="${visit.pet.owner.firstName} ${visit.pet.owner.lastName}" />
			</datatables:column>
			<datatables:column title="Date">
				<joda:format value="${visit.date.plusDays(1)}" pattern="yyyy/MM/dd" />
			</datatables:column>
			<datatables:column title="Description">
				<c:out value="${visit.description}" />
			</datatables:column>
			<datatables:column title="Diagnosis">
				<spring:url value="/resources/images/add.png" var="add" />
				<spring:url value="/resources/images/update.png" var="update" />
				<c:out value="${visit.diagnosis}" />
				<c:choose>
					<c:when test="${visit.diagnosis!=''}">
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a style="float: right;" href="${fn:escapeXml(visitUrl)}"><img
							src="${update}" width="100" height="100" /></a>
					</c:when>
					<c:otherwise>
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${add}"
							width="30" height="30" /></a>
					</c:otherwise>
				</c:choose>
			</datatables:column>
			<datatables:column title="Vet(s)">
				<c:choose>
					<c:when test="${visit.vet2==''}">
						<c:out value="${visit.vet}" />
					</c:when>
					<c:when test="${visit.vet==''}">
						<c:out value="${visit.vet2}" />
					</c:when>
					<c:otherwise>
						<c:out value="${visit.vet}, ${visit.vet2}" />
					</c:otherwise>
				</c:choose>
			</datatables:column>
			<datatables:column title="Action(s)">
				<spring:url value="/resources/images/assign.png" var="assign" />
				<spring:url value="/resources/images/change.png" var="change" />
				<spring:url value="/resources/images/second.png" var="second" />
				<spring:url value="/resources/images/unassign.png" var="unassign" />
				<c:choose>
					<c:when test="${visit.vet==''&&visit.vet2==''}">
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${assign}"
							width="50" height="50" /></a>
					</c:when>
					<c:when test="${visit.vet2==''||visit.vet==''}">
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${second}"
							width="50" height="50" /></a>
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${change}"
							width="50" height="50" /></a>
						<spring:url value="/pets/{petId}/visits/{visitId}/unassign"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${unassign}"
							width="50" height="50" /></a>
					</c:when>
					<c:otherwise>
						<spring:url value="/pets/{petId}/visits/{visitId}/edit"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${change}"
							width="50" height="50" /></a>
						<spring:url value="/pets/{petId}/visits/{visitId}/unassign"
							var="visitUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(visitUrl)}"><img src="${unassign}"
							width="50" height="50" /></a>
					</c:otherwise>
				</c:choose>
			</datatables:column>
		</datatables:table>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>