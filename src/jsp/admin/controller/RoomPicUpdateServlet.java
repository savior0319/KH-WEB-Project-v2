package jsp.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import jsp.admin.model.service.AdminService;
import jsp.common.MyFileRenamePolicy;

/**
 * Servlet implementation class RoomPicUpdateServlet
 */
@WebServlet(name = "RoomPicUpdate", urlPatterns = { "/roomPicUpdate" })
public class RoomPicUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomPicUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int fileSizeLimit = 10 * 1024 * 1024;
		String uploadFilePath = getServletContext().getRealPath("/") + "Image" + "\\" + "room";
		String encType = "UTF-8";
		
		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, fileSizeLimit, encType,
				new MyFileRenamePolicy());
		
		Enumeration parameter = multi.getParameterNames();

		String value = parameter.nextElement().toString();

		String afterFileName = multi.getFilesystemName("img"); // 새로운 사진 이름
		String afterPath = "/Image/room/" + afterFileName;
		
		String beforeFileName = multi.getParameter(value); // 전 사진 경로
		
		int result = new AdminService().roomUpdateImage(afterPath, beforeFileName);
		if (result > 0) {
			 File file = new File(getServletContext().getRealPath("/")+beforeFileName);
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
