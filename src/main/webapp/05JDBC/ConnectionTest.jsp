<%@page import="common.DBConnPool"%>
<%@page import="common.JDBConnect"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JDBC</title>
</head>
<body>
	<h2>JDBC 테스트1</h2>
	<%
	//기본생성자를 통한 인스턴스 생성
	JDBConnect jdbc1 = new JDBConnect();
	jdbc1.close();
	%>
	
	<h2>JDBC 테스트2</h2>
	<%
	/* web.xml에 설정한 컨텍스트 초기화 파라미터를 
	application 내장객체를 통해 읽어온다.*/
	String driver =application.getInitParameter("OracleDriver");
	String url =application.getInitParameter("OracleURL");
	String id =application.getInitParameter("OracleId");
	String pwd =application.getInitParameter("OraclePwd");
	//인자생성자1을 통해 인스턴스를 생성한다.
	JDBConnect jdbc2 = new JDBConnect(driver, url, id, pwd);
	jdbc2.close();	
	%>
	
	<h2>JDBC 테스트3</h2>
	<%
	/*Java클래스에 정의된 생성자(혹은 메서드)에서 내장객체를 사용하기 
	위해서는 반드시 JSP에서 내장객체를 맴개변수로 전달해야 한다.*/
	JDBConnect jdbc3 = new JDBConnect(application);
	jdbc3.close();
	%>
	
	<h2>커넥션 풀 테스트</h2>
	<%
	DBConnPool pool = new DBConnPool();
	pool.close();
	%>
	
</body>
</html>