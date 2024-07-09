package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 서블릿 클래스를 만들기 위한 절차
 1. HttpServlet 클래스를 상속한다.
 2. 클라이언트의 요청을 받아 처리하기 위한 
 	doGet()/doPost()를 오버라이딩한다.
 3. 서블릿에 필수적인 패키지 임포트와 예외처리는 자동완성된다.
 4. request 내장객체와 response 내장객체는 매개변수를 통해 
 	서블릿에 이미 포함되어 있어 즉시 사용할 수 있다.
 */
public class HelloServlet extends HttpServlet {
	/*
	 서블릿 작성시 발생되는 경고(Warning)를 위해 추가한다.
	 없어도 실행에는 영향을 미치지 않는다.(교안에는 생략되어 있다)
	 */
	private static final long serialVersionUID =1L;
	
	//get방식의 요청을 처리하기 위한 doGet()메서드 오버라이딩
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//request영역에 속성을 저장한다.
		req.setAttribute("message", "Hello Servlet...!!!");
		//View 에 해당하는 JSP페이지로 포워드한다.
		req.getRequestDispatcher("/12Servlet/HelloServlet.jsp").forward(req, resp);
		/*리퀘스트 영역은 포워드 된 페이지까지 공유되므로 
		  서블릿에서 저장한 속서은 JSP에서 사용할 수 있다.*/
	}

}
