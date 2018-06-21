package jsp.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsp.common.SHA256Hash;
import jsp.member.model.service.MemberService;
import jsp.member.model.vo.MemberVo;

@WebServlet(name = "MemberWithDraw", urlPatterns = { "/withdraw" })
public class MemberWithDrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MemberWithDrawServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String id = request.getParameter("id");
		String pwd = new SHA256Hash().enctyptSHA256(request.getParameter("pwd"));

		MemberVo mv = new MemberService().login(id, pwd);
		
		
		HttpSession session = request.getSession(false);
		session.getAttribute("member");
		response.setCharacterEncoding("utf-8");
		
		
		if (mv != null) {
			// 탈퇴할 회원의 정보
			String delId = mv.getMbId();
			String delEmail = mv.getMbEmail();
			Date delEntDate = mv.getMbEntDate();
			String delName = mv.getMbName();
			Date delBirth = mv.getMbBirth();
			
			int result = new MemberService().withDraw(id, pwd);
			if (result > 0) {
				// 탈퇴 테이블로 이동
				new MemberService().memberDel(delId, delEmail, delEntDate, delName, delBirth);
				session.invalidate();
				response.getWriter().print("탈퇴가 완료되었습니다");
			} else {
				response.getWriter().print("탈퇴에 실패했습니다");
			}
		} else {
			response.getWriter().print("비밀번호를 잘못 입력했습니다");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
