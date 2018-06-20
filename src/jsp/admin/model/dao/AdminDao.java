package jsp.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import jsp.admin.model.vo.AnswerVo;
import jsp.admin.model.vo.MemberLoginLogVo;
import jsp.board.model.vo.BoardVo;
import jsp.common.JDBCTemplate;
import jsp.main.model.vo.PensionPicTb;
import jsp.member.model.vo.MemberVo;
import jsp.member.model.vo.QuestionVo;
import jsp.reservation.model.vo.PensionVo;
import jsp.reservation.model.vo.ReservationVo;
import jsp.reservation.model.vo.SalesVo;

public class AdminDao {
	// TODO: properties에서 값을 불러오는 방식으로 변경해야 함.
	
	private PreparedStatement pstmt = null;
	// private ResultSet rset = null;
	private Properties prop = new Properties();
	
	// properties 쓰기 위해 만듬
	public AdminDao() {
		String path = AdminDao.class.getResource("").getPath();
		try {
			prop.load(new FileReader(path + "query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 객실 정보 불러오는 함수
	public ArrayList<PensionVo> AllRoomList(Connection conn) {
		ArrayList<PensionVo> list = new ArrayList<PensionVo>();
		Statement stmt = null;
		ResultSet rs = null;
		String query ="select * from PENSION_TB ";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				PensionVo pv = new PensionVo();
				
				pv.setPsName(rs.getString("ps_name"));
				pv.setPsPersonnel(rs.getInt("ps_personnel"));
				pv.setPsMaxPersonnel(rs.getInt("ps_max_personnel"));
				pv.setPsContents(rs.getString("ps_contents"));
				pv.setPsWeekend(rs.getInt("ps_weekend"));
				pv.setPsWeekday(rs.getInt("ps_weekday"));
				pv.setPsAddtionalPrice(rs.getInt("ps_additional_price"));
				
				list.add(pv);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}
	
	// 객실 정보를 등록하는 함수
	public boolean insertRoom(Connection conn, PensionVo pv) {
		PreparedStatement pstmt = null;
		boolean result = false;
		int row = 0;
		String query ="insert into Pension_tb values(?,?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pv.getPsName());
			pstmt.setInt(2, pv.getPsPersonnel());
			pstmt.setInt(3, pv.getPsMaxPersonnel());
			pstmt.setString(4, pv.getPsContents());
			pstmt.setInt(5, pv.getPsWeekend());
			pstmt.setInt(6, pv.getPsWeekday());
			pstmt.setInt(7, pv.getPsAddtionalPrice());
			row = pstmt.executeUpdate();
			if(row>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	// 회원 전체 보기 중에서... 
	// 1.
	public ArrayList<MemberVo> getMemberCurrentPage(Connection conn, int currentPage, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		// 시작 페이지 계산 
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
					// 만약 요청한 페이지가 1이라면 1		(1*10) - (10-1) = 10-9 =1
					// 만약 요청한 페이지가 2이라면 11 		(2*10) - 9	= 11
		
		// 끝 페이지 계산 
		int end = currentPage*recordCountPerPage;	// 숫자가딱 맞지 않아도 된다.
		
		String query = "select * from "
				+ "(select member_tb.* , row_number() over(order by MB_ID desc) as num from member_tb) "
				+"where num between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVo mv = new MemberVo();
				mv.setMbId(rs.getString(1));
				mv.setMbPwd(rs.getString(2));
				mv.setMbBirth(rs.getDate(3));
				mv.setMbEmail(rs.getString(4));
				mv.setMbPhone(rs.getString(5));
				mv.setMbGender(rs.getString(6));
				mv.setMbName(rs.getString(7));
				mv.setMbEntDate(rs.getDate(8));
				mv.setMbAddress(rs.getString(9));
				list.add(mv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}	
		return list;
	}
	
	// 회원 전체 보기 중에서
	// 2.
	public String getMemberPageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 게시물의 total갯수를 구해야 함.
		//  (total/recordCountPerPage)+1 이 페이지 갯수
		
		int recordTotalCount = 0; // 총 게시물 개수 저장 변수 (정보가 없으므로 초기값은 0)
		String query = "select count(*) as totalCount from member_tb";
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recordTotalCount = rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		int pageTotalCount = 0;	// navi 토탈 카운트
		// 페이지당 10개씩 보이게 만들어서 navi list를 만드려면?
		// 만약 나머지가 0이면 
		if(recordTotalCount%recordCountPerPage != 0){
			pageTotalCount = recordTotalCount/recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount/recordCountPerPage	;
		}
		// 1 페이지 보다 더 아래의 페이지를 요청하거나, 
		// 전체 페이지보다 큰 페이지를 요청했을 경우를 에러 방지.
		
		if(currentPage < 1) {
			currentPage =1;
		}
		if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		// 현재 페이지를 기점으로 시작 navi와 끝 navi를 만들어줘야 한다.
		// 현재 페이지가 1이라면... 1~5 
		// 현재 페이기자 3이라면 ... 1~5 
		// 현재 페이지가 7이라면 ... 6 ~ 10 
		// 현재 페이지가 11 이라면 ... 11 ~ 15 
		// ((현재페이지-1)/리스트개수)*리스트 개수 +1 -> 몫으로 게산을 한다...
		// 전제 조건 
		int startNavi = ((currentPage-1)/naviCountPerPage)*naviCountPerPage+1;
		
		// 끝 navi 구하는 공식 (쉬움)
		int endNavi = startNavi + naviCountPerPage -1;
		
		// 끝 navi를 구할 때 주의해야 할 점 
		// 마지막 navi 부분은 한줄 추가를 해야 한다.
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
	
		// page navi에서 사용할 '<' 모양과 '>' 모양을 사용하기 위해
		// 필요한 변수 2개 생성 (시작과 끝은 필요없으므로 !)
		// if- else 로 쓰지 않는 이유는 게시물의 갯수가 적을 경우 둘다 쓸 수 있기 때문에
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi ==1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		// 여기까지가 기본적인 구조 
		StringBuilder sb = new StringBuilder();
		if(needPrev) { // 시작이 1페이지가 아니라면
			sb.append("<a href='/adminMemberList?currentPage="+1+"'> << </a>");
			sb.append("<a href='/adminMemberList?currentPage="+(startNavi-1)+"'> < </a>");
	
		}
		for(int i = startNavi ; i <=endNavi ; i++) {
			if(i==currentPage) {
				sb.append("<a href='/adminMemberList?currentPage="+(i)+"' ><B> "+i+" </B></a>");
			}else {
				sb.append("<a href='/adminMemberList?currentPage="+(i)+"' > "+i+" </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/adminMemberList?currentPage="+(endNavi+1)+"'> > </a>");
			sb.append("<a href='/adminMemberList?currentPage="+pageTotalCount+"'> >> </a>");
		}
		// 문자열 만들어짐.
		return sb.toString();
	}
	
	// 1. 게시판
	public ArrayList<BoardVo> getBoardCurrentPage(Connection conn, int currentPage, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		// 시작 페이지 계산 
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
					// 만약 요청한 페이지가 1이라면 1		(1*10) - (10-1) = 10-9 =1
					// 만약 요청한 페이지가 2이라면 11 		(2*10) - 9	= 11
		
		// 끝 페이지 계산 
		int end = currentPage*recordCountPerPage;	// 숫자가딱 맞지 않아도 된다.
		
		String query = "select * from "
				+ "(select board_tb.* , row_number() over(order by bd_no desc) as num from board_tb where category=?) "
				+"where num between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "공지사항");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo bv = new BoardVo();
				bv.setBdNo(rs.getInt("BD_NO"));
				bv.setBdName(rs.getString("BD_NAME"));
				bv.setBdContents(rs.getString("BD_CONTENTS"));
				bv.setBdWriter(rs.getString("BD_WRITER"));
				bv.setBdWriteDate(rs.getTimestamp("BD_WRITE_DATE"));
				bv.setBdViewCount(rs.getInt("BD_VIEW_COUNT"));
				bv.setBdRecommendCount(rs.getInt("BD_RECOMMEND_COUNT"));
				bv.setBdCategory(rs.getString("BD_CATEGORY"));
				list.add(bv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}
	// 2. 게시판
	public String getBoardPageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 게시물의 total갯수를 구해야 함.
		//  (total/recordCountPerPage)+1 이 페이지 갯수
		
		int recordTotalCount = 0; // 총 게시물 개수 저장 변수 (정보가 없으므로 초기값은 0)
		String query = "select count(*) as totalCount from board_tb where category=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "공지사항");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recordTotalCount = rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		int pageTotalCount = 0;	// navi 토탈 카운트
		// 페이지당 10개씩 보이게 만들어서 navi list를 만드려면?
		// 만약 나머지가 0이면 
		if(recordTotalCount%recordCountPerPage != 0){
			pageTotalCount = recordTotalCount/recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount/recordCountPerPage	;
		}
		// 1 페이지 보다 더 아래의 페이지를 요청하거나, 
		// 전체 페이지보다 큰 페이지를 요청했을 경우를 에러 방지.
		
		if(currentPage < 1) {
			currentPage =1;
		}
		if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		// 현재 페이지를 기점으로 시작 navi와 끝 navi를 만들어줘야 한다.
		// 현재 페이지가 1이라면... 1~5 
		// 현재 페이기자 3이라면 ... 1~5 
		// 현재 페이지가 7이라면 ... 6 ~ 10 
		// 현재 페이지가 11 이라면 ... 11 ~ 15 
		// ((현재페이지-1)/리스트개수)*리스트 개수 +1 -> 몫으로 게산을 한다...
		// 전제 조건 
		int startNavi = ((currentPage-1)/naviCountPerPage)*naviCountPerPage+1;
		
		// 끝 navi 구하는 공식 (쉬움)
		int endNavi = startNavi + naviCountPerPage -1;
		
		// 끝 navi를 구할 때 주의해야 할 점 
		// 마지막 navi 부분은 한줄 추가를 해야 한다.
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
	
		// page navi에서 사용할 '<' 모양과 '>' 모양을 사용하기 위해
		// 필요한 변수 2개 생성 (시작과 끝은 필요없으므로 !)
		// if- else 로 쓰지 않는 이유는 게시물의 갯수가 적을 경우 둘다 쓸 수 있기 때문에
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi ==1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		// 여기까지가 기본적인 구조 
		StringBuilder sb = new StringBuilder();
		if(needPrev) { // 시작이 1페이지가 아니라면
			sb.append("<a href='/adminBoardList?currentPage="+1+"'> << </a>");
			sb.append("<a href='/adminBoardList?currentPage="+(startNavi-1)+"'> < </a>");
	
		}
		for(int i = startNavi ; i <=endNavi ; i++) {
			if(i==currentPage) {
				sb.append("<a href='/adminBoardList?currentPage="+(i)+"' ><B> "+i+" </B></a>");
			}else {
				sb.append("<a href='/adminBoardList?currentPage="+(i)+"' > "+i+" </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/adminBoardList?currentPage="+(endNavi+1)+"'> > </a>");
			sb.append("<a href='/adminBoardList?currentPage="+pageTotalCount+"'> >> </a>");
		}
		// 문자열 만들어짐.
		return sb.toString();
	}
	
	// 예약 정보
	public ArrayList<ReservationVo> getReserveCurrentPage(Connection conn, int currentPage, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ReservationVo> list = new ArrayList<ReservationVo>();
		// 시작 페이지 계산 
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
					// 만약 요청한 페이지가 1이라면 1		(1*10) - (10-1) = 10-9 =1
					// 만약 요청한 페이지가 2이라면 11 		(2*10) - 9	= 11
		
		// 끝 페이지 계산 
		int end = currentPage*recordCountPerPage;	// 숫자가딱 맞지 않아도 된다.
		
		String query = "select * from "
				+ "(select reservation_tb.* , row_number() over(order by res_no desc) as num from reservation_tb) "
				+"where num between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReservationVo rv = new ReservationVo();
				rv.setResNo(rs.getInt("res_no"));
				rv.setResRoomName(rs.getString("res_room_name"));
				rv.setResId(rs.getString("res_id"));
				rv.setResPersonnel(rs.getInt("res_personnel"));
				rv.setResReservationDate(rs.getTimestamp("res_reservation_date"));
				rv.setResInDate(rs.getDate("res_in_date"));
				rv.setResOutDate(rs.getDate("res_out_date"));
				rv.setResPeriod(rs.getInt("res_period"));
				rv.setResPrice(rs.getInt("res_price"));
				rv.setResPaymentDate(rs.getTimestamp("res_payment_date"));
				list.add(rv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}
	// 예약 정보2.
	public String getReservePageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 게시물의 total갯수를 구해야 함.
		//  (total/recordCountPerPage)+1 이 페이지 갯수
		
		int recordTotalCount = 0; // 총 게시물 개수 저장 변수 (정보가 없으므로 초기값은 0)
		String query = "select count(*) as totalCount from reservation_tb";
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recordTotalCount = rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		int pageTotalCount = 0;	// navi 토탈 카운트
		// 페이지당 10개씩 보이게 만들어서 navi list를 만드려면?
		// 만약 나머지가 0이면 
		if(recordTotalCount%recordCountPerPage != 0){
			pageTotalCount = recordTotalCount/recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount/recordCountPerPage	;
		}
		// 1 페이지 보다 더 아래의 페이지를 요청하거나, 
		// 전체 페이지보다 큰 페이지를 요청했을 경우를 에러 방지.
		
		if(currentPage < 1) {
			currentPage =1;
		}
		if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		// 현재 페이지를 기점으로 시작 navi와 끝 navi를 만들어줘야 한다.
		// 현재 페이지가 1이라면... 1~5 
		// 현재 페이기자 3이라면 ... 1~5 
		// 현재 페이지가 7이라면 ... 6 ~ 10 
		// 현재 페이지가 11 이라면 ... 11 ~ 15 
		// ((현재페이지-1)/리스트개수)*리스트 개수 +1 -> 몫으로 게산을 한다...
		// 전제 조건 
		int startNavi = ((currentPage-1)/naviCountPerPage)*naviCountPerPage+1;
		
		// 끝 navi 구하는 공식 (쉬움)
		int endNavi = startNavi + naviCountPerPage -1;
		
		// 끝 navi를 구할 때 주의해야 할 점 
		// 마지막 navi 부분은 한줄 추가를 해야 한다.
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
	
		// page navi에서 사용할 '<' 모양과 '>' 모양을 사용하기 위해
		// 필요한 변수 2개 생성 (시작과 끝은 필요없으므로 !)
		// if- else 로 쓰지 않는 이유는 게시물의 갯수가 적을 경우 둘다 쓸 수 있기 때문에
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi ==1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		// 여기까지가 기본적인 구조 
		StringBuilder sb = new StringBuilder();
		if(needPrev) { // 시작이 1페이지가 아니라면
			sb.append("<a href='/adminReserveManager?currentPage="+1+"'> << </a>");
			sb.append("<a href='/adminReserveManager?currentPage="+(startNavi-1)+"'> < </a>");
	
		}
		for(int i = startNavi ; i <=endNavi ; i++) {
			if(i==currentPage) {
				sb.append("<a href='/adminReserveManager?currentPage="+(i)+"' ><B> "+i+" </B></a>");
			}else {
				sb.append("<a href='/adminReserveManager?currentPage="+(i)+"' > "+i+" </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/adminReserveManager?currentPage="+(endNavi+1)+"'> > </a>");
			sb.append("<a href='/adminReserveManager?currentPage="+pageTotalCount+"'> >> </a>");
		}
		// 문자열 만들어짐.
		return sb.toString();
	}
	// 매출 정ㅂ조
	public ArrayList<SalesVo> getSalesCurrentPage(Connection conn, int currentPage, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<SalesVo> list = new ArrayList<SalesVo>();
		// 시작 페이지 계산 
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
					// 만약 요청한 페이지가 1이라면 1		(1*10) - (10-1) = 10-9 =1
					// 만약 요청한 페이지가 2이라면 11 		(2*10) - 9	= 11
		
		// 끝 페이지 계산 
		int end = currentPage*recordCountPerPage;	// 숫자가딱 맞지 않아도 된다.
		
		String query = "select * from "
				+ "(select sales_tb.* , row_number() over(order by sales_no desc) as num from sales_tb) "
				+"where num between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SalesVo sv = new SalesVo();
				sv.setSalesNo(rs.getInt("SALES_NO"));
				sv.setSalesId(rs.getString("SALES_ID"));
				sv.setSalesPaymentPrice(rs.getInt("SALES_PAYMENT_PRICE"));
				sv.setSalesReservationNo(rs.getInt("SALES_RESERVATION_NO"));
				list.add(sv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public String getSalesPageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 게시물의 total갯수를 구해야 함.
		//  (total/recordCountPerPage)+1 이 페이지 갯수
		
		int recordTotalCount = 0; // 총 게시물 개수 저장 변수 (정보가 없으므로 초기값은 0)
		String query = "select count(*) as totalCount from sales_tb";
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recordTotalCount = rs.getInt("totalCount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		int pageTotalCount = 0;	// navi 토탈 카운트
		// 페이지당 10개씩 보이게 만들어서 navi list를 만드려면?
		// 만약 나머지가 0이면 
		if(recordTotalCount%recordCountPerPage != 0){
			pageTotalCount = recordTotalCount/recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount/recordCountPerPage	;
		}
		// 1 페이지 보다 더 아래의 페이지를 요청하거나, 
		// 전체 페이지보다 큰 페이지를 요청했을 경우를 에러 방지.
		
		if(currentPage < 1) {
			currentPage =1;
		}
		if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		// 현재 페이지를 기점으로 시작 navi와 끝 navi를 만들어줘야 한다.
		// 현재 페이지가 1이라면... 1~5 
		// 현재 페이기자 3이라면 ... 1~5 
		// 현재 페이지가 7이라면 ... 6 ~ 10 
		// 현재 페이지가 11 이라면 ... 11 ~ 15 
		// ((현재페이지-1)/리스트개수)*리스트 개수 +1 -> 몫으로 게산을 한다...
		// 전제 조건 
		int startNavi = ((currentPage-1)/naviCountPerPage)*naviCountPerPage+1;
		
		// 끝 navi 구하는 공식 (쉬움)
		int endNavi = startNavi + naviCountPerPage -1;
		
		// 끝 navi를 구할 때 주의해야 할 점 
		// 마지막 navi 부분은 한줄 추가를 해야 한다.
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
	
		// page navi에서 사용할 '<' 모양과 '>' 모양을 사용하기 위해
		// 필요한 변수 2개 생성 (시작과 끝은 필요없으므로 !)
		// if- else 로 쓰지 않는 이유는 게시물의 갯수가 적을 경우 둘다 쓸 수 있기 때문에
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi ==1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		// 여기까지가 기본적인 구조 
		StringBuilder sb = new StringBuilder();
		if(needPrev) { // 시작이 1페이지가 아니라면
			sb.append("<a href='/adminSalesManager?currentPage="+1+"'> << </a>");
			sb.append("<a href='/adminSalesManager?currentPage="+(startNavi-1)+"'> < </a>");
	
		}
		for(int i = startNavi ; i <=endNavi ; i++) {
			if(i==currentPage) {
				sb.append("<a href='/adminSalesManager?currentPage="+(i)+"' ><B> "+i+" </B></a>");
			}else {
				sb.append("<a href='/adminSalesManager?currentPage="+(i)+"' > "+i+" </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/adminSalesManager?currentPage="+(endNavi+1)+"'> > </a>");
			sb.append("<a href='/adminSalesManager?currentPage="+pageTotalCount+"'> >> </a>");
		}
		// 문자열 만들어짐.
		return sb.toString();
	}

	// 메인 템플릿들 변경 함수
	public int mainupdateSelect(Connection conn, String value, String update) {
		String query = "";
		switch(value) {
		case "1":query = prop.getProperty("mainupdate1");break;
		case "2":query = prop.getProperty("mainupdate2");break;
		case "3":query = prop.getProperty("mainupdate3");break;
		case "4":query = prop.getProperty("mainupdate4");break;
		case "5":query = prop.getProperty("mainupdate5");break;
		case "6":query = prop.getProperty("mainupdate6");break;
		}
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, update);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	// 객실 등록

	public boolean roomInsert(Connection conn, PensionVo pv) {
		boolean result = false;
		PreparedStatement pstmt = null;
		int row = 0;
		String query = "insert into PENSION_TB values(?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pv.getPsName());
			pstmt.setInt(2, pv.getPsPersonnel());
			pstmt.setInt(3, pv.getPsMaxPersonnel());
			pstmt.setString(4, pv.getPsContents());
			pstmt.setInt(5, pv.getPsWeekend());
			pstmt.setInt(6, pv.getPsWeekday());
			pstmt.setInt(7, pv.getPsAddtionalPrice());
			
			row = pstmt.executeUpdate();
			
			if(row>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	// 객실의 사진 등록

	public boolean picInsert(Connection conn, PensionPicTb picVo) {
		boolean result = false;
		PreparedStatement pstmt = null;
		int row = 0;
		String query = "insert into PENSION_PIC_TB values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, picVo.getpsPicName());
			pstmt.setString(2, picVo.getPsPicPath());
			pstmt.setString(3, picVo.getPsPicMain());
			
			
			row = pstmt.executeUpdate();
			
			if(row>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	// 객실 정보 가져오기
	public PensionVo selectRoomInfo(Connection conn, String psName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PensionVo pv = null;
		String query = "select * from pension_tb where ps_name=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, psName);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pv = new PensionVo(rs.getString("PS_NAME"), rs.getInt("PS_PERSONNEL"), rs.getInt("PS_MAX_PERSONNEL"), rs.getString("PS_CONTENTS"), rs.getInt("PS_WEEKEND"), rs.getInt("PS_WEEKDAY"), rs.getInt("PS_ADDITIONAL_PRICE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		
		return pv;
	}

	public ArrayList<QuestionVo> questionList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from QUESTION_TB order by Q_NO DESC";
		ArrayList<QuestionVo> list = new ArrayList<QuestionVo>();
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QuestionVo qv = new QuestionVo();
				qv.setqNo(rs.getInt("Q_NO"));
				qv.setqName(rs.getString("Q_NAME"));
				qv.setqContents(rs.getString("Q_CONTENTS"));
				qv.setqWriter(rs.getString("Q_WRITER"));
				qv.setqWriteDate(rs.getTimestamp("Q_WRITE_DATE"));
				qv.setqAnswerCheck(rs.getString("Q_ANSWER_ChECK"));
				list.add(qv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public QuestionVo selectOneQuestion(Connection conn, int qNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from QUESTION_TB where Q_NO=?";
		QuestionVo qv = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, qNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				qv = new QuestionVo();
				qv.setqNo(rs.getInt("Q_NO"));
				qv.setqName(rs.getString("Q_NAME"));
				qv.setqContents(rs.getString("Q_CONTENTS"));
				qv.setqWriter(rs.getString("Q_WRITER"));
				qv.setqWriteDate(rs.getTimestamp("Q_WRITE_DATE"));
				qv.setqAnswerCheck(rs.getString("Q_ANSWER_ChECK"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return qv;
	}
	// 1:1 문의 답변 생성
	public boolean insertAnswer(Connection conn, AnswerVo asv) {
		PreparedStatement pstmt = null;
		int row = 0;
		boolean result = false;
		String query ="insert into ANSWER_TB VALUES(A_NO_SEQ.NEXTVAL,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, asv.getaQNo());
			pstmt.setString(2, asv.getaContents());
			
			row = pstmt.executeUpdate();
			if(row>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
	
		return result;
	}
	// 답변 했음으로 표시.
	public boolean answerRegistration(Connection conn, int qNo) {
		PreparedStatement pstmt = null;
		int row = 0 ;
		boolean result = false;
		String query = "UPDATE QUESTION_TB SET Q_ANSWER_CHECK='Y' WHERE Q_NO = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, qNo);
			
			row = pstmt.executeUpdate();
			if(row>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	// 전체 로그 기록 불러오기
	public ArrayList<MemberLoginLogVo> selectAllMemberLog(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from MEMBER_LOG_TB order by MB_LOG_TIME DESC";
		ArrayList<MemberLoginLogVo> list = new ArrayList<MemberLoginLogVo>();
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberLoginLogVo mllv = new MemberLoginLogVo();
				mllv.setMbLogId(rs.getString("MB_LOG_ID"));
				mllv.setMbLogTime(rs.getTimestamp("MB_LOG_TIME"));
				mllv.setMbLogBrowser(rs.getString("MB_LOG_BROWSER"));
				mllv.setMbLogIp(rs.getString("MB_LOG_IP"));
				mllv.setMbLogLocale(rs.getString("MB_LOG_LOCALE"));
				list.add(mllv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public AnswerVo selectAnswer(Connection conn, int qNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from ANSWER_TB where A_Q_NO=?";
		AnswerVo asv = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, qNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				asv = new AnswerVo();
				asv.setaNo(rs.getInt("A_NO"));
				asv.setaQNo(rs.getInt("A_Q_NO"));
				asv.setaContents(rs.getString("A_CONTENTS"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return asv;
	}

	public int mainInsert(Connection conn, String mainPicPath) {
		PreparedStatement pstmt = null;
		String query = prop.getProperty("mainImageInsert");
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mainPicPath);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateImage(Connection conn, String afterPath, String beforeFileName) {
		PreparedStatement pstmt = null;
		String query = prop.getProperty("mainupdate7");
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, afterPath);
			pstmt.setString(2, beforeFileName);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
