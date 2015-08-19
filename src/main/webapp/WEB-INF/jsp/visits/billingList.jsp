<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../fragments/staticFiles.jsp" />
<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Billing</h2>

		<datatables:table id="visits" data="${visits.visitList}" row="visit"
			theme="bootstrap2" cssClass="table table-striped" pageable="false"
			info="false">
			<datatables:column title="Owner">
				<c:out
					value="${visit.pet.owner.firstName} ${visit.pet.owner.lastName}" />
			</datatables:column>
			<datatables:column title="Telephone">
				<c:out value="${visit.pet.owner.telephone}" />
			</datatables:column>
			<datatables:column title="Pet Name">
				<c:out value="${visit.pet.name}" />
			</datatables:column>
			<datatables:column title="Description">
				<c:out value="${visit.description}" />
			</datatables:column>
			<datatables:column title="Amount Owed">
				<c:out value="$5.00" />
			</datatables:column>
			<datatables:column title="Visit Date">
				<joda:format value="${visit.date.plusDays(1)}" pattern="yyyy/MM/dd" />
			</datatables:column>
			<datatables:column title="Due Date">
				<joda:format value="${visit.date.plusDays(16)}" pattern="yyyy/MM/dd" />
			</datatables:column>
			<datatables:column title="Status">
				<spring:url value="/resources/images/paid.png" var="banner" />
				<spring:url value="/resources/images/due.png" var="banne" />
				<spring:url value="/resources/images/pastdue.png" var="bann" />
				<c:choose>
					<c:when test="${visit.paid==true}">
						<img id="paid" src="${banner}" style="float: right;" width="50"
							height="50" />
					</c:when>
					<c:when test="${visit.date.plusDays(16)<=status}">
						<img id="pastdue" src="${bann}" style="float: right;" width="50"
							height="50" />
						<spring:url value="/pets/{petId}/visits/{visitId}/paid"
							var="paidUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(paidUrl)}" style="float: right;"><c:out
								value="Mark as Paid" /></a>
					</c:when>
					<c:when test="${visit.date.plusDays(16)>status}">
						<img id="due" src="${banne}" style="float: right;" width="50"
							height="50" />
						<spring:url value="/pets/{petId}/visits/{visitId}/paid"
							var="paidUrl">
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${visit.pet.id}" />
						</spring:url>
						<a href="${fn:escapeXml(paidUrl)}" style="float: right;"><c:out
								value="Mark as Paid" /></a>
					</c:when>
				</c:choose>

			</datatables:column>
		</datatables:table>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>