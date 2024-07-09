<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LifeCycle.jsp</title>
</head>
<body>
	<script>
	/* 
	자바스크립트에서 전송방식을 부여한 후 서버로 요청한다.
	get방식과 post방식을 구분하여 부여할 수 있다.
	*/
	function requestAction(frm, met) {
		if(met ==1){
			frm.method= 'get';
		}
		else{
			frm.method= 'post';
		}
		frm.submit();
	}
	</script>
	
	
	<h2>서블릿 수명주기(Life Cycle) 메서드</h2>
	<!-- 
	자바스크립트를 통해 폼값을 전송하므로 input태그는 button 타입으로 선언하고 
	onclick 이벤트 리스너에서 함수를 호출한다.
	또한 form태그의 속성도 action만 설정되어 있다.
	 -->
	<form action="./LifeCycle.do">
	    <!-- 함수 호출시 form태그의 DOM을 인수로 전달한다.  -->
		<input type="button" value="Get방식 요청하기"
			onclick="requestAction(this.form, 1);" />
		<input type="button" value="Post방식 요청하기"
			onclick="requestAction(this.form, 2);" />
	</form>
</body>
</html>