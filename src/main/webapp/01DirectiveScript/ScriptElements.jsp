<%@page import="test.HelloJava"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 
선언부(Declaration)
: 스크립트렛이나 표현식에서 사용할 메서드를 선언할 때 사용한다.
선언부에 선언된 메서드는 해당 JSP가 서블릿으로 변환될 때 
멤버메서드 형태로 선언된다.
 -->    
<%!
//선언부는 !로 시작한다. 2개의 정수의 합을 반환하는 메서드 선언.
public int add(int num1, int num2) {
	return num1+ num2;
}
/*
Java에서는 메서드내에 또 다른 메서드를 선언할 수 없다. 
JSP는 자바의 문법을 따르므로 선언부에서 !를 제거하는 즉시 에러발생.
제거하는 순간 _jspService() 내에 메서드를 선언하는 것이 되기 때문.
*/
%>
<!DOCTYPE html>
<html>
<head><title>스크립트 요소</title></head>
<body>
<%
/*
스크립트렙(Scriptlet)
: 주로 JSP코드를 작성하고 실행할 때 사용한다. 스크립트렛은 <body>, <head> 
어디서든 사용가능. 또한 <script>, <style> 태그 내부에서도 사용가능.
여기에 기술된 콬드는 _jspService() 메서드 내에 포함됨. 그러므로 
해당 영역에서는 메서드를 선언할 수 없다.
*/
int result = add(10,20);
%>
<!-- 
표현식(Expression)
: 변수를 웹브라우저상에 출력할 때 사용한다. JS의 document.write()와 
동일하다. 표현식 사용시 주의할 점은 문장 끝에 ;(세미콜론)을 붙이지 않음
 -->
덧셈결과1: <%= result %> <br/>
덧셈결과2: <%= add(30, 40) %> 

<h2>내가 만든 Java클래스의 메서드 호출하기</h2>

<%
	/*JSP 사용시 선언부는 자주 사용되지 않음. 주로 Java로 클래스를 만든 후 
	아래와 같이 사용하는 경우가 더 많음*/
	HelloJava helloJava = new HelloJava();
	int sum = helloJava.myFn(1,100);
	out.println("1~100까지의 합은: "+ sum);
%>

</body>
</html>