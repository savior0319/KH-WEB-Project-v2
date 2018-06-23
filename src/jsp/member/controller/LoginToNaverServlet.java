package jsp.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsp.common.SHA256Hash;
import jsp.member.model.service.MemberService;
import jsp.member.model.vo.MemberVo;

@WebServlet(name = "LoginToNaver", urlPatterns = { "/loginToNaver" })
public class LoginToNaverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginToNaverServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			/* String age = request.getParameter("age"); */
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String birth = request.getParameter("birth");
			String birthSplit[] = birth.split("-");
			String birthAppend = birthSplit[0] + birthSplit[1];
			String birthEncrypt = new SHA256Hash().enctyptSHA256(birthAppend);
			String address = "네이버회원";

			MemberVo mv = new MemberVo();
			mv.setMbAddress(address);
			mv.setMbEmail(email);
			mv.setMbGender(gender);
			mv.setMbId(id);
			mv.setMbName(name);
			mv.setMbPwd(birthEncrypt);

			int result = new MemberService().signUp(mv);

			HttpSession session = request.getSession();
			session.setAttribute("member", mv);

			if (result > 0) {
				response.getWriter().print("1");
				response.getWriter().close();
			}
		} catch (Exception e) {
			System.out.println("");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
