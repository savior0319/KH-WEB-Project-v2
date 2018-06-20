package jsp.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jsp.member.model.service.MemberService;
import jsp.member.model.vo.PageDataVo;
import jsp.member.model.vo.QuestionVo;

@SuppressWarnings("all")
@WebServlet(name = "Question", urlPatterns = { "/question" })
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QuestionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		ArrayList<QuestionVo> aList = new MemberService().questionList(id);

		// AJAX 페이징
		// int currentPage;
		//
		// if (request.getParameter("currentPage") == null) {
		// currentPage = 1;
		// } else {
		// currentPage = Integer.parseInt(request.getParameter("currentPage"));
		// }
		//
		// PageDataVo pd = new MemberService().question(currentPage);
		//
		// ArrayList<QuestionVo> aList = pd.getaList();
		// String navi = pd.getPageNavi();
		//
		// response.setContentType("application/json");
		// response.setCharacterEncoding("utf-8");
		// new Gson().toJson(pd, response.getWriter());

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		new Gson().toJson(aList, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
