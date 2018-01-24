<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>스도쿠 프로젝트</title>
		<tiles:insertAttribute name="meta" />
		<tiles:insertAttribute name="scripts" />
	</head>
	<body>
			<tiles:insertAttribute name="header" />
			<hr/>
			<tiles:insertAttribute name="contents" />
			<tiles:insertAttribute name="contents-js" />
			<hr/>
			<tiles:insertAttribute name="footer" />
			
			
			
			
			<c:set var="tilesLayer"><tiles:getAsString name="contents-popup"/></c:set>
			<c:set var="existLayer" value="${layer:exist(req, tilesLayer)}"/>
			<c:if test="${existLayer}">
				<tiles:insertAttribute name="contents-popup"/>
			</c:if>
	</body>
</html>