package ajaxmvc.modelo.acciones;


import java.io.FileNotFoundException;
import java.io.IOException;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ajaxmvc.modelo.beans.BeanError;

import ajaxmvc.modelo.beans.ModeloAjax;
import ajaxmvc.modelo.procesos.*;
import ajaxmvc.controlador.Accion;

/** 
 * Acción Ajax Validar devuelve si el usuario es correcto
 * @author Andres Carmona Gil
 * @version  1
 *  Comprueba si el login introducido existe para cuando un usuario se registra
 */
public class AccionAjaxExisteLogin implements Accion {
	/**
	 * Nombre del archivo de sentencias sql
	 * @uml.property  name="sql"
	 */
	private String sql = null;

	/** 
	 * Datasource que se empleará para acceder a la base de datos.
	 * @uml.property name="dS"
	 */
	private DataSource DS;
	
	/**
     * Bean de error para situaciones en los que el método ejecutar() devuelve false.
     * @uml.property name="error"
     * @uml.associationEnd
     * @aggregation composite
     */
	private BeanError error;
	/** 
	 * Objeto que encapsula el modelo que procesará para la respuesta de ajax.
	 * @uml.property name="modelo"
	 */
	private Object modelo;
	/** 
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property name="vista"
	 */
	private String vista = null;
	/** 
	 * Contexto de aplicación.
	 * @uml.property name="sc"
	 */
	@SuppressWarnings("unused")
	private ServletContext Sc;
	
		
		/** 
		 * Ejecuta una consulta a la base de datos mediante una clase DAO 
		 * que nos devolvera si existe o no ese login
		 */
		public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
				
				boolean estado = true;
				
				ProcesaDAOUsuario procesaBD;
				try {
					procesaBD = new ProcesaDAOUsuario(this.getDS(),this.sql);
				} catch (FileNotFoundException e) {
		
					e.printStackTrace();
					
					throw new Error(e.getMessage(),e.getCause());
					
				} 
				ModeloAjax modelo = new ModeloAjax();
				
				String login;
				
				login = request.getParameter("login");
				modelo.setContentType("text/plain; charset=UTF-8");
				try {
					if (procesaBD.existeUsr(login)){
						modelo.setRespuesta("existelogin");	
					}else{
						modelo.setRespuesta("noexistelogin");
					}
				} catch (BeanError e) {
					
					this.error=e;
					return false;
				}
				System.out.println(modelo.getRespuesta());
				this.setModelo(modelo);
				return estado;
			 }

	/**
	 * @return
	 * @uml.property  name="error"
	 */
	@Override
	/**
	 * Devuelve el error asociado a la acción, si lo hubiera.
	 */
	public Exception getError() {
		return error;
	}

	/**
	 * Getter of the property <tt>modelo</tt>
	 * @return  Returns the modelo.
	 * @uml.property  name="modelo"
	 */
	
	/** 
	 * Getter of the property <tt>modelo</tt>
	 * @return  Returns the modelo.
	 * @uml.property  name="modelo"
	 */
	@Override
	public Object getModelo() {
		return modelo;
	}
	/** 
	 * Método setter para la propiedad modelo.
	 * @param modelo  El modelo a establecer.
	 * @uml.property  name="modelo"
	 */
	public void setModelo(Object modelo) {
		this.modelo = modelo;
	}	

	/**
	 * Getter of the property <tt>vista</tt>
	 * @return  Returns the vista.
	 * @uml.property  name="vista"
	 */
	
	/** 
	 * Getter of the property <tt>vista</tt>
	 * @return  Returns the vista.
	 * @uml.property  name="vista"
	 */
	@Override
	public String getVista() {
		return vista;
	}
	/** 
	 * Método setter para la propiedad vista.
	 * @param vista  La vista a establecer.
	 * @uml.property  name="vista"
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}
	/** 
	 * Método getter para la propiedad DS (datasource).
	 * @return  El datasource DS.
	 * @uml.property  name="dS"
	 */
	private DataSource getDS() {
		return DS;
	}	
	/**
	 * Setter of the property <tt>dS</tt>
	 * @param dS  The DS to set.
	 * @uml.property  name="dS"
	 */
	

	

		/**
		 * Getter of the property <tt>sql</tt>
		 * @return  Returns the sql.
		 * @uml.property  name="sql"
		 */
		public String getSql() {
			return sql;
		}

		/**
		 * Setter of the property <tt>sql</tt>
		 * @param sql  The sql to set.
		 * @uml.property  name="sql"
		 */
		public void setSql(String sql) {
			this.sql = sql;
		}

		@Override
		public void setSc(ServletContext sc) {
			
		}

		@Override
		public void setDS(DataSource ds) {

			this.DS=ds;
			
		}

		@Override
		public void setSQL(String archivoSql) {
			
		}

}
