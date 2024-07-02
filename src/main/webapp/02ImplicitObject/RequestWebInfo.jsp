<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>내장 객체 - request</title>
</head>
<body>
	<h2>1. 클라이언트와 서버의 환경정보 읽기</h2>
	<ul>
		<!-- 전송방식을 get 혹은 post로 출력 -->
		<li>데이터 전송 방식: <%=request.getMethod() %></li>
		<!-- 현재 접속한 전체경로를 반환 -->
		<li>URL: <%=request.getRequestURL() %></li>
		<!-- 전체경로에서 호스트(Host)를 제외한 나머지 경로를 반환 -->
		<li>URI: <%=request.getRequestURI() %></li>
		<li>프로토콜: <%=request.getProtocol() %></li>
		<li>서버명: <%=request.getServerName() %></li>
		<li>서버포트: <%=request.getServerPort() %></li>
		<!-- localhost는 루프백 주소이므로 0:0:과 같이 표현됨 -->
		<li>클라이언트 IP주소: <%=request.getRemoteAddr() %></li>
		<!-- 
		get방식으로 전송할 경우 경로명 뒤에 클라이언트가 전송한 값을 출력함.
		즉 get방식일 때마 출력됨.
		 -->
		<li>쿼리스트링: <%=request.getQueryString() %></li>
		<!-- 
		클라이언트가 get 혹은 post 방식으로 전송한 값을 받을 때 사용.		
		 -->
		<li>전송된 값1:<%=request.getParameter("eng") %></li>
		<li>전송된 값2:<%=request.getParameter("han") %></li>
	</ul>
</body>
</html>