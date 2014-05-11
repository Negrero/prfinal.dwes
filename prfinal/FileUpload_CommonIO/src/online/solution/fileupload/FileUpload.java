package online.solution.fileupload;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {

    public static void multiUploadFile(HttpServletRequest request, String path) {

	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	if (isMultipart) {
	    FileItemFactory factory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    try {
		List items = upload.parseRequest(request);
		Iterator iterator = items.iterator();
		while (iterator.hasNext()) {
		    FileItem item = (FileItem) iterator.next();

		    if (!item.isFormField()) {
			String fileName = item.getName();

			File directory = new File(path);
			if (!directory.exists()) {
			    boolean status = directory.mkdirs();
			}

			File uploadedFile = new File(directory + "/" + fileName);
			System.out.println(uploadedFile.getAbsolutePath());
			item.write(uploadedFile);
		    }
		}
	    } catch (FileUploadException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		System.out.println("DataUploadServlet.doGet() Exception -> "
			+ e.getMessage());
		e.printStackTrace();
	    }
	}
    }

}
