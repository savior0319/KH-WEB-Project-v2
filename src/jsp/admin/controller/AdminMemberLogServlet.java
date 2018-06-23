package jsp.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.admin.model.service.AdminService;
import jsp.admin.model.vo.LoginLogPageVo;

/**
 * Servlet implementation class AdminMemberLogServlet
 */
@WebServlet(name = "AdminMemberLog", urlPatterns = { "/adminMemberLog" })
public class AdminMemberLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberLogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int currentPage =0;
		String searchData = request.getParameter("searchData");
		String searchOption = request.getParameter("searchOption");
		
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//ArrayList<MemberLoginLogVo> list = new AdminService().selectAllMemberLog();
		LoginLogPageVo llpv =null;
		if(searchData != null && searchOption != null) {
			llpv = new AdminService().selectAllMemberLog(currentPage,searchData,searchOption);
		}else {
			llpv = new AdminService().selectAllMemberLog(currentPage);
		}
		RequestDispatcher view = request.getRequestDispatcher("/View/admin/member/memberLog.jsp");
		//request.setAttribute("allMemberLog", list);
		request.setAttribute("allMemberLog", llpv);
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
