package jsp.admin.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import jsp.admin.model.dao.AdminDao;
import jsp.admin.model.vo.AnswerVo;
import jsp.admin.model.vo.BoardAdminPageVo;
import jsp.admin.model.vo.MemberLoginLogVo;
import jsp.admin.model.vo.MemberPageVo;
import jsp.admin.model.vo.QnAVo;
import jsp.admin.model.vo.ReservePageVo;
import jsp.admin.model.vo.RoomTotalInfoVo;
import jsp.admin.model.vo.SalesPageVo;
import jsp.board.model.vo.BoardVo;
import jsp.common.JDBCTemplate;
import jsp.main.model.dao.MainDao;
import jsp.main.model.vo.MainPicTb;
import jsp.main.model.vo.PensionPicTb;
import jsp.member.model.vo.MemberVo;
import jsp.member.model.vo.QuestionVo;
import jsp.reservation.model.vo.PensionVo;
import jsp.reservation.model.vo.ReservationVo;
import jsp.reservation.model.vo.SalesVo;

public class AdminService {
	public AdminService() {
	}

	// AdminDao 연결
	private AdminDao aDao = new AdminDao();

	public ArrayList<PensionVo> AllRoomList() {

		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		ArrayList<PensionVo> list = new AdminDao().AllRoomList(conn);
		JDBCTemplate.close(conn);

		return list;
	}

	// 객실 정보 등록
	public boolean insertRoom(PensionVo pv) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		boolean result = new AdminDao().insertRoom(conn, pv);
		if (result) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public MemberPageVo memberList(int currentPage) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// Service 에서는 2가지 값을 정해야 함
		// 1. 한 페이지 당 보이는 리스트의 개수(게시물의 개수)
		// 2. 현재 위치를 중심으로 시작 navi에서부터 끝 navi개수
		int recordCountPerPage = 10; // 한 페이지당 갯수
		int naviCountPerPage = 10; // 페이지가 몇개씩 보일지

		// 1. 현재 페이지 리스트
		ArrayList<MemberVo> list = new AdminDao().getMemberCurrentPage(conn, currentPage, recordCountPerPage);

		// 2. 현재 중심으로 만들어지는 NAVI 리스트(문자열)
		String pageNavi = new AdminDao().getMemberPageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage);

		MemberPageVo mpv = null;
		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			mpv = new MemberPageVo(list, pageNavi);
		}
		JDBCTemplate.close(conn);
		return mpv;
	}

	// 게시판
	public BoardAdminPageVo boardList(int currentPage) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// Service 에서는 2가지 값을 정해야 함
		// 1. 한 페이지 당 보이는 리스트의 개수(게시물의 개수)
		// 2. 현재 위치를 중심으로 시작 navi에서부터 끝 navi개수
		int recordCountPerPage = 10; // 한 페이지당 갯수
		int naviCountPerPage = 10; // 페이지가 몇개씩 보일지

		// 1. 현재 페이지 리스트
		ArrayList<BoardVo> list = new AdminDao().getBoardCurrentPage(conn, currentPage, recordCountPerPage);

		// 2. 현재 중심으로 만들어지는 NAVI 리스트(문자열)
		String pageNavi = new AdminDao().getBoardPageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage);

		BoardAdminPageVo bpv = null;
		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			bpv = new BoardAdminPageVo(list, pageNavi);
		}
		JDBCTemplate.close(conn);
		return bpv;
	}

	// 메인 템플릿들 변경
	public int mainupdateSelect(String value, String update) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		int result = aDao.mainupdateSelect(conn, value, update);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	// 예약 정보 시작

	public ReservePageVo reserveList(int currentPage) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// Service 에서는 2가지 값을 정해야 함
		// 1. 한 페이지 당 보이는 리스트의 개수(게시물의 개수)
		// 2. 현재 위치를 중심으로 시작 navi에서부터 끝 navi개수
		int recordCountPerPage = 10; // 한 페이지당 갯수
		int naviCountPerPage = 10; // 페이지가 몇개씩 보일지

		// 1. 현재 페이지 리스트
		ArrayList<ReservationVo> list = new AdminDao().getReserveCurrentPage(conn, currentPage, recordCountPerPage);

		// 2. 현재 중심으로 만들어지는 NAVI 리스트(문자열)
		String pageNavi = new AdminDao().getReservePageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage);

		ReservePageVo rpv = null;
		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			rpv = new ReservePageVo(list, pageNavi);
		}
		JDBCTemplate.close(conn);
		return rpv;
	}

	// 매출 정보
	public SalesPageVo salesList(int currentPage) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// Service 에서는 2가지 값을 정해야 함
		// 1. 한 페이지 당 보이는 리스트의 개수(게시물의 개수)
		// 2. 현재 위치를 중심으로 시작 navi에서부터 끝 navi개수
		int recordCountPerPage = 10; // 한 페이지당 갯수
		int naviCountPerPage = 10; // 페이지가 몇개씩 보일지

		// 1. 현재 페이지 리스트
		ArrayList<SalesVo> list = new AdminDao().getSalesCurrentPage(conn, currentPage, recordCountPerPage);

		// 2. 현재 중심으로 만들어지는 NAVI 리스트(문자열)
		String pageNavi = new AdminDao().getSalesPageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage);

		SalesPageVo spv = null;
		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			spv = new SalesPageVo(list, pageNavi);
		}
		JDBCTemplate.close(conn);
		return spv;

	}

	// 전체 질문 보기
	public ArrayList<QuestionVo> questionList() {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		ArrayList<QuestionVo> list = new AdminDao().questionList(conn);
		JDBCTemplate.close(conn);
		return list;
	}

	public QnAVo selectOneQuestion(int qNo) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// 질문 가져오기
		QuestionVo qv = new AdminDao().selectOneQuestion(conn, qNo);
		// 답변 가져오기
		AnswerVo asv = new AdminDao().selectAnswer(conn, qNo);
		QnAVo qnaVo = new QnAVo(qv, asv);

		JDBCTemplate.close(conn);
		return qnaVo;

	}

	public boolean insertAnswer(AnswerVo asv) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		// 1. 답변 생성
		boolean result1 = new AdminDao().insertAnswer(conn, asv);
		// 2. 문의 테이블의 답변 여부 y 로 변경
		boolean result2 = new AdminDao().answerRegistration(conn,asv.getaQNo());
		boolean result = result1&&result2;
		if (result) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);

		return result;
	};

	//

	public boolean roomInsert(RoomTotalInfoVo ptlv) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		boolean totalResult = false;

		// 1. 객실 등록
		boolean roomResult = new AdminDao().roomInsert(conn, ptlv.getPv());
		// 2. 객실 사진 등록
		boolean picResult[] = new boolean[ptlv.getList().size()];
		boolean picResultFianl = true;
		for (int i = 0; i < ptlv.getList().size(); i++) {
			picResult[i] = new AdminDao().picInsert(conn, ptlv.getList().get(i));
			System.out.println("picResult[i] : " + picResult[i]);
			if (!picResult[i]) {
				picResultFianl = false;
			}
		}

		totalResult = roomResult && picResultFianl;
		// 최종 결과
		System.out.println("total : " + totalResult);
		if (totalResult) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		return totalResult;
	}

	// 객실 정보 사진과 함께 모두 가져오기
	public RoomTotalInfoVo selectRoomInfo(String psName) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);

		PensionVo pv = new AdminDao().selectRoomInfo(conn, psName);
		ArrayList<PensionPicTb> list = new MainDao().roomInfo(conn, psName);

		RoomTotalInfoVo rtiv = new RoomTotalInfoVo(pv, list);

		JDBCTemplate.close(conn);
		return rtiv;
	}

	public ArrayList<MemberLoginLogVo> selectAllMemberLog() {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		ArrayList<MemberLoginLogVo> list = new AdminDao().selectAllMemberLog(conn);
		JDBCTemplate.close(conn);
		return list;
	}

	public int mainInsert(ArrayList<MainPicTb> mptlist) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		int result = 0;

		for(int i=0; i<mptlist.size(); i++)
		{
			result += aDao.mainInsert(conn,mptlist.get(i).getMainPicPath());
		}
		if(result==mptlist.size())
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollBack(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
		
	}

	public int updateImage(String afterPath, String beforeFileName) {
		Connection conn = null;
		conn = JDBCTemplate.getConnect(conn);
		int result = aDao.updateImage(conn, afterPath, beforeFileName);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
		JDBCTemplate.rollBack(conn);	
		}
		return result;
	}
}
