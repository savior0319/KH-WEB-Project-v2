package jsp.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.admin.model.service.AdminService;
import jsp.admin.model.vo.MemberPageVo;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet(name = "AdminMemberList", urlPatterns = { "/adminMemberList" })
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		// page정보, pageper정보 없으면 1로 줄까?
		int currentPage =0;
		
		//String searchData = request.getParameter("searchData");
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//ArrayList<MemberVo> list = new AdminService().memberList(currentPage,searchData);
		MemberPageVo mpv = new AdminService().memberList(currentPage);
		// 
		if(mpv != null) {
			RequestDispatcher view = request.getRequestDispatcher("/View/admin/member/memberManager.jsp");
			request.setAttribute("MemberPage",mpv );
			view.forward(request, response);
		}else {
			response.sendRedirect("/View/error/errorPage.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
