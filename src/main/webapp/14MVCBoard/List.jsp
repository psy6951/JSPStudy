<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<style>a{text-decoration:none;}</style>
</head>
<body>
    <h2>파일 첨부형 게시판 - 💚목록 보기(List)💚</h2>

    <!-- 검색 폼 -->
    <form method="get">  
    <table border="1" width="90%">
    <tr>
        <td align="center">
            <select name="searchField">
                <option value="title">제목</option>
                <option value="content">내용</option>
                <option value="name">작성자</option>
            </select>
            <input type="text" name="searchWord" />
            <input type="submit" value="검색하기" />
        </td>
    </tr>
    </table>
    </form>

    <!-- 목록 테이블 -->
    <table border="1" width="90%">
        <tr>
            <th width="10%">번호</th>
            <th width="*">제목</th>
            <th width="15%">작성자</th>
            <th width="10%">조회수</th>
            <th width="15%">작성일</th>
            <th width="8%">첨부</th>
        </tr>
        
<!-- 검색어 조건에 따라 게시물이 있는지 확인하기 위해 choose태그 사용 -->
<c:choose>
	<c:when test="${ empty boardLists }">
	<!-- List에 저장된 레코드가 없는 경우 -->
        <tr>
            <td colspan="6" align="center">
                등록된 게시물이 없습니다^^*
            </td>
        </tr>
    </c:when>
    
    
	<c:otherwise>
<!-- List에 저장된 레코드가 있다면 개수만큼 반복해서 출력한다.
	items 속성에는 반복가능한 객체를 기술하고, 
	순서대로 추출된 데이터는 var속성에 지정한 변수로 저장된다. -->
		<c:forEach items="${ boardLists }" var="row" varStatus="loop">
        <tr align="center">
        	<!-- 가상번호 -->
            <td>
            	${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index) }
            </td>
            <td align="left"> 
                <a href="../mvcboard/view.do?idx=${ row.idx }">
                	${ row.title }
                </a>
            </td> 
            <td>${ row.name }</td>
            <td>${ row.visitcount }</td>
            <td>${ row.postdate }</td>
            <td>
            <!-- 다운로드 링크는 첨부파일이 있을 때만 표시한다. -->
            <c:if test="${ not empty row.ofile }">
            	<a href="../mvcboard/download.do?ofile=${ row.ofile }&sfile=${ row.sfile }&idx=${ row.idx }">[Down]</a>
            </c:if>
            </td>
        </tr>
        </c:forEach>
    </c:otherwise>
</c:choose>
    </table>
   
   <!-- 하단메뉴(바로가기, 글쓰기) -->
    <table border="1" width="90%">
        <tr align="center">
            <td>
            <!-- 페이지 바로가기 링크 출력 -->
                ${ map.pagingImg }
            </td>
            <td width="100"><button type="button"
                onclick="location.href='../mvcboard/write.do';">글쓰기</button></td>
        </tr>
    </table>
</body>
</html>

