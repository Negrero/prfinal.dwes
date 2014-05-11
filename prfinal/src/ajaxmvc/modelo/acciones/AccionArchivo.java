/**
 * 
 */
package ajaxmvc.modelo.acciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.BeanError;

/**
 * @author negrero
 * @param <MultipartParse>
 *
 */
public class AccionArchivo implements Accion {

	
	
	private static final String UPLOAD_DIRECTORY = "/tmp";
	/**
	 * Datasource que se empleará para acceder a la base de datos.
	 * @uml.property  name="dS"
	 */
	private DataSource DS = null;
	/**
     * Bean de error para situaciones en los que el método ejecutar() devuelve false.
     * @uml.property name="error"
     * @uml.associationEnd
     * @aggregation composite
     */
	private BeanError error = null;
	/**
	 * Objeto que encapsula el modelo que procesará la vista.
	 * @uml.property  name="modelo"
	 */
	private Object modelo = null;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = null;
	
	/**
	 * Si no hay errores en el procesamiento de la acción
	 */
	private String vistaOk = "principalUsuario.jsp";
	
	/**
	 * Si se precisa reintentar el registro.
	 */
	private String vistaNoOk = "registro.jsp";
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc;
	private String archivoSql;
	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.vista=this.vistaOk;
		
		//this.uploadFile(request, response);
		System.out.println("uri"+request.getContentLength());
		System.out.println("uri"+request.getPart("archivos").getSize());
		System.out.println("uri"+request.getParts().toArray());
		System.out.println("uri"+request.getContextPath());
		 Object[] lista = request.getParts().toArray();
		System.out.println(lista.length);
		
		 String resp = "";
		
	        int i = 1;
	        resp += "<br>Here is information about uploaded files.<br>";
	        try {
	            MultipartParser parser = new MultipartParser(request, 1024 * 1024 * 1024);  /* file limit size of 1GB*/
	            Part _part;
	            System.out.println("size de parte");
	            while ((_part = (Part) parser.readNextPart()) != null) {
	            
	                if (((File) _part).isFile()) {
	                    FilePart fPart = (FilePart) _part;  // get some info about the file
	                    String name = fPart.getFileName();
	                    if (name != null) {
	                       
	                    	
	                        System.out.println("nombre archivo:"+name);
	                    } else {
	                        resp = "<br>The user did not upload a file for this part.";
	                    }
	                }
	            }// end while 
	        } catch (java.io.IOException ioe) {
	            resp = ioe.getMessage();
	        }
	        
	        System.out.println("respuesta:"+resp);
		
		return true;
	}
	private String getFileName(Part part){
		  Pattern p = Pattern.compile("filename=\"(.+?)\"");
		  Matcher m = p.matcher(part.getHeader("Content-Disposition"));
		  if(m.find()){
		   return m.group(1);
		  }else{
		   throw new IllegalStateException("Cannot fin filename in web request header.");
		  }
		 }
	 /**
     * uploadFile method used to upload file to server.
     *
     ***/
    private void uploadFile(HttpServletRequest request,
      HttpServletResponse response)
            throws ServletException, IOException {
        
        for(Part part : request.getParts()) {
        	System.out.println("nombre de la parte:"+part.getName());
        	System.out.println("tamaoño de la parte:"+part.getSize());
            String name = part.getName();
            InputStream is = request.getPart(name).getInputStream();
            String fileName = getUploadedFileName(part);
            FileOutputStream fos = new FileOutputStream(this.Sc.getRealPath("/")+"/WEB-INF/tmp/"+fileName);
            int data = 0;
            while((data = is.read()) != -1) {
                fos.write(data);
            }
            fos.close();
            is.close();
            
        }
    
    }
    
    /**
     * Method used to get uploaded file name.
     * This will parse following string and get filename
     * Content-Disposition: form-data; name="content"; filename="a.txt"
     **/
    private String getUploadedFileName(Part p) {
        String file = "", header = "Content-Disposition";
        String[] strArray = p.getHeader(header).split(";");
        for(String split : strArray) {
            if(split.trim().startsWith("filename")) {
                file = split.substring(split.indexOf('=') + 1);
                file = file.trim().replace("\"", "");
                System.out.println("File name : "+file);
            }
        }
        return file;
    }
    

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#getVista()
	 */
	@Override
	public String getVista() {
		// TODO Auto-generated method stub
		return this.vista;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#getModelo()
	 */
	@Override
	public Object getModelo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setSc(javax.servlet.ServletContext)
	 */
	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub
		this.Sc=sc;

	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#getError()
	 */
	@Override
	public Exception getError() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setDS(javax.sql.DataSource)
	 */
	@Override
	public void setDS(DataSource ds) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setSQL(java.lang.String)
	 */
	@Override
	public void setSQL(String archivoSql) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the vistaOk
	 */
	public String getVistaOk() {
		return vistaOk;
	}

	/**
	 * @param vistaOk the vistaOk to set
	 */
	public void setVistaOk(String vistaOk) {
		this.vistaOk = vistaOk;
	}

	/**
	 * @return the vistaNoOk
	 */
	public String getVistaNoOk() {
		return vistaNoOk;
	}

	/**
	 * @param vistaNoOk the vistaNoOk to set
	 */
	public void setVistaNoOk(String vistaNoOk) {
		this.vistaNoOk = vistaNoOk;
	}

	/**
	 * @return the archivoSql
	 */
	public String getArchivoSql() {
		return archivoSql;
	}

	/**
	 * @param archivoSql the archivoSql to set
	 */
	public void setArchivoSql(String archivoSql) {
		this.archivoSql = archivoSql;
	}

	/**
	 * @return the dS
	 */
	public DataSource getDS() {
		return DS;
	}

	/**
	 * @return the sc
	 */
	public ServletContext getSc() {
		return Sc;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(BeanError error) {
		this.error = error;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(Object modelo) {
		this.modelo = modelo;
	}

	/**
	 * @param vista the vista to set
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}

}
