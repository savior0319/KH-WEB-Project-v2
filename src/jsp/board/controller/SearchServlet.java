package jsp.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.board.model.service.BoardService;
import jsp.board.model.vo.Page;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet(name = "Search", urlPatterns = { "/search" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String search = request.getParameter("search");
		int currentPage;
		if (request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		Page page = new BoardService().searchNotice(currentPage, search);
		if (page != null) {

			RequestDispatcher view = request.getRequestDispatcher("/View/board/board.jsp");
			request.setAttribute("Page", page);
			view.forward(request, response);
		} else {
			response.sendRedirect("/View/error/searchError.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
