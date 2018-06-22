package jsp.admin.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.admin.model.service.AdminService;

/**
 * Servlet implementation class RoomPicDeleteServlet
 */
@WebServlet(name = "RoomPicDelete", urlPatterns = { "/roomPicDelete" })
public class RoomPicDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomPicDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String deleteImage = request.getParameter("image_path");
		
		int result = new AdminService().roomDeleteImage(deleteImage);
		if (result > 0) {
			 File file = new File(getServletContext().getRealPath("/")+deleteImage);
			 file.delete();
			response.sendRedirect("/adminRoomManager");
		} else {
			response.sendRedirect("/View/error/error.jsp");
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