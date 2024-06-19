package com.javalab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalab.vo.BoardVO;

/*
 * 게시물 관련 DAO (Data Access Object) 
 * - 게시물 등록
 * - 게시물 목록 / 상세조회
 * - 게시물 수정 / 삭제
 */

public class BoardDAO {
	
	private DataSource dataSource;
	private Connection conn = null; // 데이터베이스 접속을 위한 커넥션 객체
	private PreparedStatement pstmt = null; // 쿼리문을 만들고 실행해주는 객체
	private ResultSet rs = null; // 쿼리 결과를 받아오는 객체

	/* 생성자 */
	public BoardDAO () {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * insertBoard(...) :
	 * 게시물 등록 처리 메소드
	 */
	public int insertBoard(BoardVO boardVO) {
		// 디버깅 문자열
		System.out.println("boardDAO의 insertBoard(...) 메소드가 호출되었습니다.");
		
		// 반환할 값 일단 선언
		int row = 0;

		try {

			conn = dataSource.getConnection();

			// 멤버 추가 쿼리
			String sql = "INSERT INTO board(bno, title, content, member_id, reg_date) ";
			sql += " VALUES(seq_board.nextval, ?, ?, ?, sysdate)";
			
			// PrepareStatement 객체 얻기(쿼리문 전달)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVO.getTitle()); // 제목
			pstmt.setString(2, boardVO.getContent()); // 내용
			pstmt.setString(3, boardVO.getMemberId()); // 작성자

			row = pstmt.executeUpdate(); // 실행한 행의 갯수가 담김

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("게시물 등록 중 오류가 발생했습니다.");
		} finally {
			closeResource(); //자원 해제 메소드 호출
		}
		// 메소드 결과 값 반환
		return row;
	}
	
	
	/*
	 * getBoardList() :
	 * 게시물 목록 조회 메소드
	 */
	public List<BoardVO> getBoardList() {
		// 디버깅 문자열
		System.out.println("boardDAO의 getBoardList() 메소드가 호출되었습니다.");
				
		//반환할 객체 일단 선언
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			
			conn = dataSource.getConnection();
			
			String sql = "SELECT bno, title, content, member_id, reg_date, hit_no FROM board ORDER BY bno";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 게시물 목록 반환

			while (rs.next()) {
				// 전달받은 값을 바로 객체로 저장
				BoardVO boardVO = new BoardVO();
				boardVO.setBno(rs.getInt("bno"));
				boardVO.setTitle(rs.getString("title"));
				// content는 가져오지 않음. setter메소드로 속성을 넣고 있기 때문에 따로 생성자 설정할 필요는 x
				boardVO.setMemberId(rs.getString("member_id"));
				boardVO.setRegDate(rs.getDate("reg_date"));
				boardVO.setHitNo(rs.getInt("hit_no"));
				boardList.add(boardVO); // 게시물 목록에 추가
			}
		// '|' : 자바7에서 도입된 멀티캐치 구문에서 여러 예외처리를 한번에 할 때 사용하는 문법 '||'(or)는 사용할 수 없다
		} catch (SQLException e) {
			System.out.println("getBoardList() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return boardList;
	}
	
	/*
	 * getBoard(int bno) :
	 * 게시물 내용 보기 메소드
	 */
	public BoardVO getBoard(int bno) {
		//디버깅 문자열
		System.out.println("getBoard()메소드가 실행되었습니다.");
		//보드 객체 선언
		BoardVO boardVO = null;	
		
		try {
			
			conn = dataSource.getConnection();
			
			// 게시물 조회 쿼리
			String sql = "SELECT bno, title, content, member_id, reg_date, hit_no FROM board WHERE bno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
		
			rs = pstmt.executeQuery(); // 해당 게시물 반환
		
			while (rs.next()) { // 게시물 1건 반환
				// 전달받은 값을 바로 boardVO 객체에 저장
				boardVO = new BoardVO();
				boardVO.setBno(rs.getInt("bno"));
				boardVO.setTitle(rs.getString("title"));
				boardVO.setContent(rs.getString("content"));
				boardVO.setMemberId(rs.getString("member_id"));
				boardVO.setRegDate(rs.getDate("reg_date"));
				boardVO.setHitNo(rs.getInt("hit_no"));
			}
		} catch (SQLException e) {
			System.out.println("getBoard() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		// 게시물 객체 반환
		return boardVO;
	}

	/*
	 * incrementHitNO(int bno) :
	 * 게시물 조회 시 조회수 증가 메소드
	 */
	public void incrementHitNo(int bno) {
		System.out.println("incrementHitNo() 메소드가 실행되었습니다.");
		try {
			
			conn = dataSource.getConnection();
			
			// 게시물의 조회수 증가 쿼리
		    String updateHitSql = "UPDATE board SET hit_no = hit_no + 1 WHERE bno = ?";
		    pstmt = conn.prepareStatement(updateHitSql);
		    pstmt.setInt(1, bno);
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("incrementHitNo() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
	}

	/*
	 * updateBoard(int bno) :
	 * 게시물 수정 쿼리
	 */
	public int updateBoard(BoardVO boardVO) {
		// 디버깅 문자열
		System.out.println("updateBoard(int bno)를 실행합니다.");
		// 반환값 일단 선언
		int row = 0;
		
		try {
			
			conn = dataSource.getConnection();
			
			// 게시물 업데이트 쿼리
			String sql = "UPDATE board SET title = ?, content = ? WHERE bno = ?";
			pstmt = conn.prepareStatement(sql); // PrepareStatement 객체 얻기(쿼리문 전달)
			pstmt.setString(1, boardVO.getTitle()); // 첫번째 ?에 파라미터 세팅
			pstmt.setString(2, boardVO.getContent()); // 두번째 ?에 파라미터 세팅
			pstmt.setInt(3, boardVO.getBno()); // 세번째 ?에 파라미터 세팅
			
			row =  pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateBoard() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return row;
	}
	
	/*
	 * deleteBoard(int bno) :
	 * 게시물 삭제 쿼리
	 */
	public int deleteBoard(int bno) {
		// 디버깅 문자열
		System.out.println("deleteBoard(int bno) 메소드를 실행합니다.");
		// 반환값 선언
		int row = 0;
		
		// 파라미터 가져오기
		try {
			
			conn = dataSource.getConnection();
			
			// 게시물 삭제 쿼리문
			String sql = "DELETE board WHERE bno = ?";
		    pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
		    row = pstmt.executeUpdate(); // 쿼리문 실행 영향 받은 행수 반환
		    
		} catch (SQLException e) {
			System.out.println("deleteBoard() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return row;
	}
	
	
	
	/*
	 * closeResource() :
	 * 데이터베이스 관련 자원 반납(해제) 메소드
	 */
	public void closeResource() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			System.out.println("closeResource() ERR : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
