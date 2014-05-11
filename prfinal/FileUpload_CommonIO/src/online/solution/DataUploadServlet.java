package online.solution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import online.solution.fileupload.FileUpload;

@WebServlet("/DataUploadServlet")
public class DataUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DataUploadServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String path = "D:\\";
	FileUpload.multiUploadFile(request, path);
	response.sendRedirect("show.jsp");
    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

}
