package jsp.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jsp.board.model.vo.BoardVo;
import jsp.board.model.vo.Comment;
import jsp.common.JDBCTemplate;

public class BoardDao {
	public BoardDao() {
	}

	public ArrayList<BoardVo> getCurrentPage(Connection conn, int currentPage, int listPerCountPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		// 시작 ,끝 게시물
		int startTotalBoard = currentPage * listPerCountPage - (listPerCountPage - 1);
		int endTotalBoard = currentPage * listPerCountPage;

		String query = "select * from"
				+ " (select Board_tb.*, row_number() over(order by bd_no desc) as num from board_tb) "
				+ "where num between ? and ?";

		ArrayList<BoardVo> list = new ArrayList<BoardVo>();

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startTotalBoard);
			pstmt.setInt(2, endTotalBoard);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				BoardVo board = new BoardVo();
				board.setBdNo(rset.getInt("BD_NO"));
				board.setBdName(rset.getString("BD_NAME"));
				board.setBdCategory(rset.getString("BD_CATEGORY"));
				board.setBdContents(rset.getString("BD_CONTENTS"));
				board.setBdWriter(rset.getString("BD_WRITER"));
				board.setBdWriteDate(rset.getTimestamp("BD_WRITE_DATE"));
				board.setBdViewCount(rset.getInt("BD_VIEW_COUNT"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public String getPageCount(Connection conn, int currentPage, int listPerCountPage, int countPerPage) {
		// 게시물 총개수
		int TotalCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "select count(*) as TotalCount from board_tb";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				TotalCount = rset.getInt("TotalCount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		int pageTotalCount = 0;

		if (TotalCount % listPerCountPage != 0) {
			pageTotalCount = TotalCount / listPerCountPage + 1;
		} else {
			pageTotalCount = TotalCount / listPerCountPage;
		}

		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > TotalCount) {
			currentPage = TotalCount;
		}
		// 시작 navi 구하기
		int startCount = ((currentPage - 1) / countPerPage) * countPerPage + 1;
		int endCount = startCount + countPerPage - 1;
		if (endCount > pageTotalCount) {
			endCount = pageTotalCount;
		}

		boolean gotoPrev = true;
		boolean gotoNext = true;
		if (startCount == 1) {
			gotoPrev = false;
		}
		if (endCount == pageTotalCount) {
			gotoNext = false;
		}

		StringBuilder sb = new StringBuilder();
		if (gotoPrev) {
			sb.append("<a href='/board?currentPage=" + (startCount - 1) + "'> ◀ </a>'");

		}

		for (int i = startCount; i <= endCount; i++) {
			if (i == currentPage) {
				sb.append("<a href='/board?currentPage=" + i + "'><b> " + i + " </b></a>");

			} else {
				sb.append("<a href='/board?currentPage=" + i + "'> " + i + " </a>");
			}
		}
		if (gotoNext) {
			sb.append("<a href='/board?currentPage=" + (endCount + 1) + "'> ▶ </a>'");
		}

		return sb.toString();
	}

	public ArrayList<BoardVo> getSearchCurrentPage(Connection conn, int currentPage, int listPerCountPage,
			String search) {
		// PreparedStatement pstmt =null;
		Statement stmt = null;
		ResultSet rset = null;

		// 시작 ,끝 게시물
		int startTotalBoard = currentPage * listPerCountPage - (listPerCountPage - 1);
		int endTotalBoard = currentPage * listPerCountPage;

		String query = "select * from"
				+ " (select Board_tb.*, row_number() over(order by bd_no desc) as num from board_tb where BD_NAME like '%"
				+ search + "%') " + "where num between " + startTotalBoard + " and " + endTotalBoard + "";

		ArrayList<BoardVo> list = new ArrayList<BoardVo>();

		try {
			stmt = conn.prepareStatement(query);
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				BoardVo board = new BoardVo();
				board.setBdNo(rset.getInt("BD_NO"));
				board.setBdName(rset.getString("BD_NAME"));
				board.setBdCategory(rset.getString("BD_CATEGORY"));
				board.setBdContents(rset.getString("BD_CONTENTS"));
				board.setBdWriter(rset.getString("BD_WRITER"));
				board.setBdWriteDate(rset.getTimestamp("BD_WRITE_DATE"));
				board.setBdViewCount(rset.getInt("BD_VIEW_COUNT"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return list;
	}

	public String getSearchpageCount(Connection conn, int currentPage, int listPerCountPage, int countPerPage,
			String search) {
		int TotalCount = 0; // 게시물

		ResultSet rset = null;
		Statement stmt = null;

		String query = "select count(*) as totalCount from Board_TB where BD_NAME like '%" + search + "%'";

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				TotalCount = rset.getInt("totalCount");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		int pageTotalCount = 0;

		if (TotalCount % listPerCountPage != 0) {
			pageTotalCount = TotalCount / listPerCountPage + 1;
		} else {
			pageTotalCount = TotalCount / listPerCountPage;
		}

		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		int startCount = ((currentPage - 1) / countPerPage) * countPerPage + 1;
		int endCount = startCount + countPerPage - 1;

		if (endCount > pageTotalCount) {
			endCount = pageTotalCount;
		}

		boolean gotoPrev = true;
		boolean gotoNext = true;
		if (startCount == 1) {
			gotoPrev = false;
		}
		if (endCount == pageTotalCount) {
			gotoNext = false;
		}

		StringBuilder sb = new StringBuilder();
		if (gotoPrev) {
			sb.append("<a href='/search?search=" + search + "&currentPage=" + (startCount - 1) + "'> ◀  </a>");

		}

		for (int i = startCount; i <= endCount; i++) {
			if (i == currentPage) {
				sb.append("<a href='/search?search=" + search + "&currentPage=" + i + "'><b>" + i + "</b></a>");

			} else {
				sb.append("<a href='/search?search=" + search + "&currentPage=" + (endCount + 1) + "'> ▶ </a>");
			}
		}
		if (gotoNext) {
			sb.append("<a href='/search?search=" + search + "&currentPage=" + (endCount + 1) + "'> ▶ </a>");
		}

		return sb.toString();
	}

	public BoardVo noticeSelect(Connection conn, int bdNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		BoardVo board = null;
		String query = "select * from board_TB where BD_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bdNo);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				board = new BoardVo();
				board.setBdNo(rset.getInt("BD_NO"));
				board.setBdName(rset.getString("BD_NAME"));
				board.setBdContents(rset.getString("BD_CONTENTS"));
				board.setBdWriter(rset.getString("BD_WRITER"));
				board.setBdWriteDate(rset.getTimestamp("BD_WRITE_DATE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return board;
	}

	public ArrayList<Comment> Comment(Connection conn, int cmNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from comment_tb where CM_BD_NO=?";
		ArrayList<Comment> list = new ArrayList<Comment>();

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cmNo);

			rset = pstmt.executeQuery();
			while (rset.next()) {
				Comment c = new Comment();
				c.setCmNo(rset.getInt("CM_NO"));
				c.setCmBdNo(rset.getInt("CM_BD_NO"));
				c.setCmContents(rset.getString("CM_CONTENTS"));
				c.setCmWriter(rset.getString("CM_WRITER"));
				c.setCmWriteDate(rset.getTimestamp("CM_WRITE_DATE"));
				c.setCmRecCount(rset.getInt("CM_RECOMMEND_COUNT"));
				list.add(c);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int insertComment(Connection conn, Comment c) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "insert into comment_tb values (CM_NO_SEQ.nextval,?,?,?,sysdate,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, c.getCmBdNo());
			pstmt.setString(2, c.getCmWriter());
			pstmt.setString(3, c.getCmContents());
			pstmt.setInt(4, c.getCmRecCount());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateComment(Connection conn, String CM_CONTENTS, int CM_NO) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update comment_tb set CM_CONTENTS=? where CM_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CM_CONTENTS);
			pstmt.setInt(2, CM_NO);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int deleteComment(Connection conn, int CM_NO) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from comment_tb where CM_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, CM_NO);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;

	}

	public int insertNotice(Connection conn, BoardVo board) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "insert into Board_TB values(BD_NO_SEQ.NEXTVAL,?,?,?,sysdate,0,0,'공지사항')";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBdName());
			pstmt.setString(2, board.getBdContents());
			pstmt.setString(3, board.getBdWriter());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateNotice(Connection conn, BoardVo board) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "update board_tb set BD_NAME=?, BD_CONTENTS=? where BD_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBdName());
			pstmt.setString(2, board.getBdContents());
			pstmt.setInt(3, board.getBdNo());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int deleteNotice(Connection conn, int bdNo) { // 게시판삭제
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "delete from Board_tb where BD_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bdNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int deleteCommentNotice(Connection conn, int bdNo) { // 게시판 삭제하기전 게시판에 달려있는 모든 댓글 삭제
		PreparedStatement pstmt = null;
		int result1 = -1;

		String query = "delete from Comment_tb where CM_BD_NO=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bdNo);
			result1 = pstmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result1;
	}

}
