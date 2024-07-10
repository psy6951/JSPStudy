package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

//커넥션풀을 통한 DB 연결을 위해 클래스 상속
public class MVCBoardDAO extends DBConnPool {
	
	//디폴트생성자(직접 정의하지 않아도 자동으로 삽입된다)
	public MVCBoardDAO() {
		super();
	}
	
	//게시물의 게수를 카운트. 검색어가 있는 경우 where절을 동적으로 추가한다.
	public int selectCount(Map<String, Object>map) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM mvcboard ";
		if(map.get("searchWord") != null) {
			query += " WHERE " +map.get("searchField") + " "
					+ " LIKE '%"+map.get("searchWord") + "%' ";
		}
		
		try {
			//인파라미터가 없는 정적쿼리문을 실행하므로 Statement 인스턴스를 생성
			stmt = con.createStatement();
			//쿼리문 실행 후 결과 반환
			rs= stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외발생");
			e.printStackTrace();
		}
		return totalCount;
	}
	
	
	//목록에 출력할 게시물을 페이지 단위로 얻어오기 위한 메서드
	public List<MVCBoardDTO> selectListPage(Map<String, Object>map) {
		
		//mvcboard 테이블을 대상으로 하므로 타입매개변수 확인 필요함.
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		
		/*
		 페이징 처리를 위한 서브쿼리문 작성. 게시물의 순차적인 일련번호를 부여하는 
		 rownum을 통해 게시물을 구간에 맞게 인출한다.
		 */
		String query = " "
				+"SELECT * FROM ( "
				+"	SELECT Tb.*, ROWNUM rNum FROM ( "
				+"	SELECT * FROM mvcboard ";
		
		if(map.get("searchWord")!= null) {
			query += " WHERE " + map.get("searchField")	
					+ " LIKE '%"+ map.get("searchWord") + "%' ";
		}
		query +=" ORDER BY idx DESC"
				+"	)Tb "
				+" ) "
				+" WHERE rNum BETWEEN ? AND ?";
		try {
			//인파라미터가 있는 동적쿼리문 실행
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs= psmt.executeQuery();
			
			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5)); //날짜이므로 getDate() 사용
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8)); //숫자이므로 getInt() 사용
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10)); //숫자이므로 getInt() 사용
				
				//List에 추가한다.
				board.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외발생");
			e.printStackTrace();
		}
		return board;
	}
	
	

	
//	
//	
//
//	//게시물 입력을 위한 메서드
//	public int insertWrite(BoardDTO dto) {
//		//사용자가 작성한 내용은 DTO에 저장한 후 전달한다.
//		int result=0;
//		
//		try {
//			//인파라미터가 있는 insert쿼리문 작성
//			String query = "INSERT INTO board ( "
//					+ " num, title, content, id, visitcount) "
//					+ " VALUES ( "
//					+ " seq_board_num.NEXTVAL, ?, ?, ?, 0)";
//			//일련번호의 경우 시퀀스를 통해 입력한다.
//			
//			//prepared 인스턴스 생성 및 인파라미터 설정
//			psmt=con.prepareStatement(query);
//			psmt.setString(1, dto.getTitle());
//			psmt.setString(2, dto.getContent());
//			psmt.setString(3, dto.getId());
//			//쿼리문 실행
//			result = psmt.executeUpdate();
//
//		}
//		catch (Exception e) {
//			System.out.println("게시물 입력 중 예외발생");
//			e.printStackTrace();
//		}
//		return result;
//	}
////매개변수로 전달된 게시물의 일련번호로 게시물을 인출한다.	
//public BoardDTO selectView(String num) {
//	//하나의 레코드를 저장하기 위한 DTO 인스턴스 생성
//	BoardDTO dto = new BoardDTO();
//	
//	/* 내부조인(inner join)을 통해 member테이블의 name컬럼까지 select한다.*/
//	String query = "SELECT B.*, M.name "
//			+" FROM member M INNER JOIN board B "
//			+" ON M.id=B.id "
//			+" WHERE num =?";
//	
//	try {
//		//쿼리문의 인파라미터 설정 후 쿼리문 실행
//		psmt = con.prepareStatement(query);
//		psmt.setString(1,num);
//		rs=psmt.executeQuery();
//		
//		/*
//		 일련번호는 중복되지 않으므로 단 한개의 게시물만 인출한다.
//		 따라서 while문이 아닌 if문으로 처리한다. next()는 ResultSet으로 
//		 반환된 레코드를 확인해서 존재하면 true를 반환해준다. 
//		*/
//		if(rs.next()) {
//			/* 각 컬럼의 값을 추출할 때 1부터 시작하는 인덱스와 컬럼명 
//			 둘다 사용할 수 있다. 날짜인 경우에는 getDate()로 인출 가능하다 */
//			dto.setNum(rs.getString(1));
//			dto.setTitle(rs.getString(2));
//			dto.setContent(rs.getString("content"));
//			dto.setPostdate(rs.getDate("postdate"));
//			dto.setId(rs.getString("id"));
//			dto.setVisitcount(rs.getString(6));
//			dto.setName(rs.getString("name"));
//			//인출된 데이터는 DTO인스턴스에 저장한다.
//		}
//	}
//	catch (Exception e) {
//		System.out.println("게시물 조회수 증가 중 예외발생");
//		e.printStackTrace();
//	}
//	return dto;
//}
//
////게시물의 조회수를 1 증가시킨다.
//public void updateVisitCount(String num) {
//	/* 게시물의 일련번호를 통해 visitcount를 1 증가시킨다. 
//	 * 이 컬럼은 number타입이므로 사칙연산 가능하다.*/
//	String query="UPDATE board SET "
//			+" visitcount=visitcount+1 "
//			+" WHERE num=?";
//	try {
//		psmt = con.prepareStatement(query);
//		psmt.setString(1,num);
//		rs=psmt.executeQuery();
//	}
//	catch (Exception e) {
//		System.out.println("게시물 상세보기 중 예외발생");
//		e.printStackTrace();
//	}
//	
//}
//
////게시물 수정하기
//public int updateEdit(BoardDTO dto) {
//	int result =0;
//	
//	try {
//		//일련번호와 일치하는 게시물의 제목과 내용을 수정하는 update쿼리문
//		String query = "UPDATE board SET "
//					+" title=?, content=? "
//					+" WHERE num=?";
//		
//		//쿼리문의 인파라미터 설정 및 실행
//		psmt=con.prepareStatement(query);
//		psmt.setString(1, dto.getTitle());
//		psmt.setString(2, dto.getContent());
//		psmt.setString(3, dto.getNum());
//		
//		//수정된 레코드의 개수 반환
//		result = psmt.executeUpdate();
//	}
//	catch (Exception e) {
//		System.out.println("게시물 수정 중 예외발생");
//		e.printStackTrace();
//	}
//	return result;
//}
//	
//	
////게시물 삭제
//public int deletePost(BoardDTO dto) {
//	int result =0;
//	
//	try {
//		//게시물 삭제를 위한 delete 쿼리문 작성
//		String query = "DELETE FROM board WHERE num=?";
//		psmt=con.prepareStatement(query);
//		psmt.setString(1, dto.getNum());
//		result = psmt.executeUpdate();
//	}
//	catch (Exception e) {
//		System.out.println("게시물 삭제 중 예외발생");
//		e.printStackTrace();
//	}
//	return result;
//}
//	
//	
////페이징 기능이 있는 서브쿼리문으로 변경한 메서드
//public List<BoardDTO> selectListPage(Map<String, Object>map) {
//	List<BoardDTO> bbs = new Vector<BoardDTO>();
//	
//	String query = "SELECT * FROM ( "
//					+" 	SELECT Tb.*, ROWNUM rNum FROM ( "
//					+ " 	SELECT*FROM board";
//	
//	
//	if(map.get("searchWord") != null) {
//		query += " WHERE " + map.get("searchField")	
//				+ " LIKE '%"+ map.get("searchWord") + "%' ";
//	}
//	query += " ORDER BY num DESC"
//			+ " ) Tb "
//			+ " ) "
//			/*+ "WHERE rNum BETWEEN ? AND ?";*/
//			+ "WHERE rNum >= ? AND rNum <=?";
//
//	try {
//		//prepared 인스턴스 생성 및 인파라미터 설정
//		psmt = con.prepareStatement(query);
//		psmt.setString(1,map.get("start").toString());
//		psmt.setString(2,map.get("end").toString());
//		rs= psmt.executeQuery();
//		
//		while(rs.next()) {
//			BoardDTO dto = new BoardDTO();
//			dto.setNum(rs.getString("num"));
//			dto.setTitle(rs.getString("title"));
//			dto.setContent(rs.getString("content"));
//			dto.setPostdate(rs.getDate("postdate"));
//			dto.setId(rs.getString("id"));
//			dto.setVisitcount(rs.getString("visitcount"));
//			
//			bbs.add(dto);
//		}
//	}
//	catch (Exception e) {
//		System.out.println("게시물 조회 중 예외발생");
//		e.printStackTrace();
//	}
//	
//	//인출한 레코드를 저장한 List를 JSP로 반환한다.
//	return bbs;
//}
//
//	
//	
//	
//	
//	
//	
//	
//	
}
