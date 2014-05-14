/**
 * 
 */
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
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

/** 
 * @author Andres Carmona Gil
 * @ version 1
 *  Listado de archivos de la base de datos de un usuario
 *  identificado en el sistema
 */
public class AccionAjaxListaArchivos implements Accion {
	/** 
	 * Si no hay errores en el procesamiento de la acción y no es una peticion ajax
	 * @uml.property name="vistaOk"
	 */
	@SuppressWarnings("unused")
	private String vistaOk="principalUsuario.jsp";
	/** 
	 * vista del mvc
	 * @uml.property name="vista"
	 */
	private String vista=null;
	/** 
	 * Objeto que encapsula el modelo que procesará para la respuesta de ajax.
	 * @uml.property name="modelo"
	 */
	private ModeloAjax Modelo=null;
	
	/** 
	 * nombre del fichero de la sentencias sql
	 * @uml.property name="sql"
	 */
	private String sql;
	/** 
	 * Errores de capturados en la clase
	 * @uml.property name="error"
	 */
	private BeanError error;
	/** 
	 * base de datos configurado en el datasource
	 * @uml.property name="ds"
	 */
	private DataSource ds;

		
		/** 
		 * Ejecuta una consulta a la base de datos para recoger los nombre de archivos y el tipo (C=conductor, V=vehiculo) de un usuario identificado
		 */
		public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
				
				//leer base de datos
				ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
				this.Modelo=new ModeloAjax();
				
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Archivo> lista=db.getListaArchivo((String)request.getSession().getAttribute("login"));
					StringBuilder objJSON = new StringBuilder("[");
					
					for (int i = 0; i<lista.size(); i++) {
						objJSON.append("{nombre:'");
						objJSON.append(lista.get(i).getNombre_fichero());
						objJSON.append("',tipo:'");
						objJSON.append(lista.get(i).getTipo());
						objJSON.append("'},");
					}
					objJSON.replace(objJSON.length()-1, objJSON.length(), "]");
				
					this.Modelo.setRespuesta(objJSON.toString());
					this.Modelo.setContentType("text/html; charset=UTF-8");	
					
			
				} catch (BeanError e) {
				
					e.printStackTrace();
				}
				
				
				return true;
			 }

	/** 
	 * @uml.property  name="modelo"
	 */
	public void setModelo(ModeloAjax modelo) {
		Modelo = modelo;
	}

	/** 
	 * @uml.property  name="vista"
	 */
	@Override
	public String getVista() {
		return vista;
	}

	/** 
	 * @uml.property  name="modelo"
	 */
	@Override
	public ModeloAjax getModelo() {
		return Modelo;
	}

		

	/** 
	 * @uml.property  name="error"
	 */
	@Override
	public BeanError getError() {
		return error;
	}

		
		/** 
		 * datasource con el que vamos a trabajar para la base de datos
		 */
		public void setDS(DataSource ds){
		
			
				this.ds=ds;
			 }

		
		/** 
		 * Establece el nombre del archivo de las sentencias sql
		 */
		public void setSQL(String archivoSql){
		
			
					this.sql=archivoSql;
			 }

	/** 
	 * Setter of the property <tt>error</tt>
	 * @param error  The error to set.
	 * @uml.property  name="error"
	 */
	public void setError(BeanError error) {
		this.error = error;
	}

	/**
	 * Setter of the property <tt>vista</tt>
	 * @param vista  The vista to set.
	 * @uml.property  name="vista"
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}

	@Override
	public void setSc(ServletContext sc) {
	
		
	}

}
