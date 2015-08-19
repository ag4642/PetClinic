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
		<h2>Supplies</h2>

		<datatables:table id="supplies" data="${supplies.supplyList}"
			row="supply" theme="bootstrap2" cssClass="table table-striped"
			pageable="false" info="false">
			<datatables:column title="Item">
				<c:out value="${supply.item}" />
			</datatables:column>
			<datatables:column title="Purpose">
				<c:out value="${supply.purpose}" />
			</datatables:column>
			<datatables:column title="Quantity">
				<c:out value="${supply.quantity}" />
				<spring:url value="/resources/images/warning.png" var="warning" />
				<c:if test="${supply.quantity<5}">
					<img src="${warning}" width="20" height="20" />
				</c:if>
			</datatables:column>
			<datatables:column title="Quantity Ordered">
				<c:out value="${supply.ordered}" />
				<spring:url value="/resources/images/edit.png" var="edit" />
				<spring:url value="/resources/images/order.png" var="order" />
				<spring:url value="/resources/images/delivered.png" var="delivered" />
				<spring:url value="/supplies/{supplyId}/delivered" var="supplyUrl">
					<spring:param name="supplyId" value="${supply.id}" />
				</spring:url>
				<c:if test="${supply.ordered>0}">
					<a href="${fn:escapeXml(supplyUrl)}"><img src="${delivered}"
						width="25" height="25" /></a>
				</c:if>
				<spring:url value="/supplies/{supplyId}/edit" var="supplyUrl">
					<spring:param name="supplyId" value="${supply.id}" />
				</spring:url>
				<a href="${fn:escapeXml(supplyUrl)}"><img src="${edit}"
					style="float: right;" width="40" height="40" /></a>
				<spring:url value="/supplies/{supplyId}/order" var="supplyUrl">
					<spring:param name="supplyId" value="${supply.id}" />
				</spring:url>
				<a href="${fn:escapeXml(supplyUrl)}"><img src="${order}"
					style="float: right;" width="100" height="100" /></a>
			</datatables:column>
		</datatables:table>
		<spring:url value="/supplies/{supplyId}/new" var="supplyUrl">
			<spring:param name="supplyId" value="${supply.id}" />
		</spring:url>
		<a class="btn btn-primary" href="${fn:escapeXml(supplyUrl)}">Order
			New Supply</a>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>