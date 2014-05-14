package ajaxmvc.modelo.acciones;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.*;
import ajaxmvc.modelo.procesos.*;

public class AccionAjaxInterpretar implements Accion {
	/** 
	 * Si no hay errores en el procesamiento de la acción y no es una peticion ajax
	 * @uml.property name="vistaOk"
	 */
	@SuppressWarnings("unused")
	private String vistaOk="principalUsuario.jsp";
	/** 
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property name="vista"
	 */
	private String vista=null;
	private ModeloAjax Modelo=null;
	/** 
	 * Nombre del archivo de sentencias sql
	 * @uml.property name="sql"
	 */
	private String sql;
	
	private BeanError error;
	
	/** 
	 * datasource de la base de datos
	 * @uml.property name="ds"
	 */
	private DataSource ds;
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
		this.Modelo=new ModeloAjax();
		
	
		String[] listaselec=request.getParameterValues("nombrearchivo");
		
		try {
		@SuppressWarnings("unchecked")
		ArrayList<Archivo> lista=db.getListaArchivoSelect((String)request.getSession().getAttribute("login"),listaselec);
		StringBuilder objJSON = new StringBuilder("[");
		
		for (int i = 0; i<lista.size(); i++) {
			
			objJSON.append("{nombre:'");
			objJSON.append(lista.get(i).getNombre_fichero());
			objJSON.append("',tipo:'");
			objJSON.append(lista.get(i).getTipo());
			objJSON.append("',datosInterpretados:'");
			objJSON.append(lista.get(i).getInterpreta());
			objJSON.append("'},");
		}
		objJSON.replace(objJSON.length()-1, objJSON.length(), "]");
	
		this.Modelo.setRespuesta(objJSON.toString());
		this.Modelo.setContentType("text/html; charset=UTF-8");	
		
		
			   
	} catch (BeanError e) {
		this.error= e;
		e.printStackTrace();
		return false;
		// TODO Auto-generated catch block
		
	}
	
	return true;
		
	}

	@SuppressWarnings("unused")
	private void setModelo(ModeloAjax modelo) {
		// TODO Auto-generated method stub
		this.Modelo=modelo;
	}

	/** 
	 * @uml.property  name="vista"
	 */
	@Override
	public String getVista() {
		return vista;
	}

	@Override
	public ModeloAjax getModelo() {
		// TODO Auto-generated method stub
		return this.Modelo;
	}

	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Exception getError() {
		// TODO Auto-generated method stub
		return  error;
	}

	@Override
	public void setDS(DataSource ds) {
		// TODO Auto-generated method stub
		this.ds=ds;
	}

	@Override
	public void setSQL(String archivoSql) {
		// TODO Auto-generated method stub
			this.sql=archivoSql;
	}

	/**
	 * Setter of the property <tt>vista</tt>
	 * @param vista  The vista to set.
	 * @uml.property  name="vista"
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}
}
