package jsp.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import jsp.board.model.dao.BoardDao;
import jsp.board.model.vo.BoardVo;
import jsp.board.model.vo.Comment;
import jsp.board.model.vo.Page;
import jsp.common.JDBCTemplate;

public class BoardService {
	int listCount; // 목록 보기 개수
	Connection conn = null;

	public BoardService() {

	}

	public Page selectAll(int currentPage) {
		conn = JDBCTemplate.getConnect(conn);
		listCount = 5;
		int ListPerCountPage = listCount; // 얘는 페이지당 리스트 개수를 불러오기 위해서 직접 입력이 아니라 listCount로 초기화

		int CountPerPage = 5; // 페이지 수 5개씩

		ArrayList<BoardVo> list = new BoardDao().getCurrentPage(conn, currentPage, ListPerCountPage);
		String pageCount = new BoardDao().getPageCount(conn, currentPage, ListPerCountPage, CountPerPage);

		Page page = null;
		if (!list.isEmpty() && !pageCount.isEmpty()) {
			page = new Page();
			page.setList(list);
			page.setPageCount(pageCount);
		}
		JDBCTemplate.close(conn);

		return page;
	}

	public Page searchNotice(int currentPage, String search) {
		conn = JDBCTemplate.getConnect(conn);

		listCount = 5;

		int ListPerCountPage = listCount; // 얘는 페이지당 리스트 개수를 불러오기 위해서 직접 입력이 아니라 listCount로 초기화

		int CountPerPage = 5; // 페이지 수 5개씩

		ArrayList<BoardVo> list = new BoardDao().getSearchCurrentPage(conn, currentPage, ListPerCountPage, search);
		String pageCount = new BoardDao().getSearchpageCount(conn, currentPage, ListPerCountPage, CountPerPage, search);

		Page page = null;

		if (!list.isEmpty() && !pageCount.isEmpty()) {
			page = new Page();
			page.setList(list);
			page.setPageCount(pageCount);

		}

		JDBCTemplate.close(conn);

		return page;
	}

	public BoardVo noticeSelect(int bdNo) {
		conn = JDBCTemplate.getConnect(conn);
		BoardVo board = new BoardDao().noticeSelect(conn, bdNo);
		JDBCTemplate.close(conn);
		return board;
	}

	public ArrayList<Comment> Comment(int cmNo) {
		conn = JDBCTemplate.getConnect(conn);
		ArrayList<Comment> list = new BoardDao().Comment(conn, cmNo);

		return list;
	}

	public int insertComment(Comment c) {
		conn = JDBCTemplate.getConnect(conn);
		int result = new BoardDao().insertComment(conn, c);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int updateComment(String CM_CONTENTS, int CM_NO) {
		conn = JDBCTemplate.getConnect(conn);
		int result = new BoardDao().updateComment(conn, CM_CONTENTS, CM_NO);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int deleteComment(int CM_NO) {
		conn = JDBCTemplate.getConnect(conn);
		int result = new BoardDao().deleteComment(conn, CM_NO);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);

		return result;
	}

	public int insertNotice(BoardVo board) {

		conn = JDBCTemplate.getConnect(conn);

		int result = new BoardDao().insertNotice(conn, board);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);

		return result;
	}

	public int updateNotice(BoardVo board) {
		conn = JDBCTemplate.getConnect(conn);
		int result = new BoardDao().updateNotice(conn, board);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}

		JDBCTemplate.close(conn);

		return result;
	}

	public int deleteNotice(int bdNo) {
		conn = JDBCTemplate.getConnect(conn);

		int result = new BoardDao().deleteNotice(conn, bdNo);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int deleteCommentNotice(int bdNo) {
		conn = JDBCTemplate.getConnect(conn);

		int result1 = new BoardDao().deleteCommentNotice(conn, bdNo);

		if (result1 > 0) {
			JDBCTemplate.commit(conn);
		} else if (result1 < -2) {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result1;
	}

	public int hitsCount(int bdNo) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		int result = new BoardDao().hitsCount(conn, bdNo);
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollBack(conn);
		JDBCTemplate.close(conn);
		return result;
	}

}
