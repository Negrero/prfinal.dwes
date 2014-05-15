/**
 * 
 */
package ajaxmvc.modelo.acciones;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;


import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.BeanError;

/** 
 * @author negrero
 * @param <MultipartParse >
 *  Clase accion para las pruebas de la subida de ficheros
 */
public class AccionArchivo implements Accion {

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
	@SuppressWarnings("unused")
	private BeanError error = null;
	/** 
	 * Objeto que encapsula el modelo que procesará para la respuesta de ajax.
	 * @uml.property name="modelo"
	 */
	private Object modelo = null;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = null;
	
	/** 
	 * Si no hay errores en el procesamiento de la acción y no es una peticion ajax
	 * @uml.property name="vistaOk"
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
		
		this.vista=this.vistaOk;
		
		

		Collection<Part> partes = request.getParts();
		System.out.println(partes.size());
		for (Part trozo:partes){
			System.out.println(getFileName(trozo));
			
		}
		 @SuppressWarnings("unused")
		String resp = "";
		
	        
		
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
     * Method used to get uploaded file name.
     * This will parse following string and get filename
     * Content-Disposition: form-data; name="content"; filename="a.txt"
     **/
    @SuppressWarnings("unused")
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

		return this.vista;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#getModelo()
	 */
	/** 
	 * @uml.property  name="modelo"
	 */
	@Override
	public Object getModelo() {
		return modelo;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setSc(javax.servlet.ServletContext)
	 */
	@Override
	public void setSc(ServletContext sc) {

		this.Sc=sc;

	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#getError()
	 */
	@Override
	public Exception getError() {

		return null;
	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setDS(javax.sql.DataSource)
	 */
	@Override
	public void setDS(DataSource ds) {

	}

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#setSQL(java.lang.String)
	 */
	@Override
	public void setSQL(String archivoSql) {

	}

	/** 
	 * @return the vistaOk
	 * @uml.property  name="vistaOk"
	 */
	public String getVistaOk() {
		return vistaOk;
	}

	/** 
	 * @param vistaOk the vistaOk to set
	 * @uml.property  name="vistaOk"
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
	 * @uml.property  name="modelo"
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
