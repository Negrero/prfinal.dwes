package ajaxmvc.modelo.acciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import ajaxmvc.modelo.beans.BeanError;
import ajaxmvc.modelo.beans.Errores;
import ajaxmvc.modelo.beans.ModeloAjax;
import ajaxmvc.modelo.procesos.*;
import ajaxmvc.controlador.Accion;

/** 
 * Acción Ajax Validar devuelve si el usuario es correcto
 * @author Andres Carmona Gil
 * @version  1
 */
public class AccionAjaxExisteLogin implements Accion {
	/**
	 * Datasource que se empleará para acceder a la base de datos.
	 * @uml.property  name="dS"
	 */
	private DataSource DS;
	
	private String archivoSql=null;
	/**
     * Bean de error para situaciones en los que el método ejecutar() devuelve false.
     * @uml.property name="error"
     * @uml.associationEnd
     * @aggregation composite
     */
	private BeanError error;
	/**
	 * Objeto que encapsula el modelo que procesará la vista.
	 * @uml.property  name="modelo"
	 */
	private Object modelo;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = null;
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc;

	@Override
	/**
	 * Ejecuta la acción de validar. Recupera el datasource y comprueba 
	 * si existe el usuario, en cuyo caso se devuelve true, en caso
	 * contrario, se devuelve false. Se trata de una respuesta ante una petición
	 * AJAX.
	 */
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException{
		
		boolean estado = true;
		System.out.println(this.archivoSql);
		ProcesaDAOUsuario procesaBD;
		try {
			procesaBD = new ProcesaDAOUsuario(this.getDS(),this.archivoSql);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new Error(e.getMessage(),e.getCause());
			
		} 
		ModeloAjax modelo = new ModeloAjax();
		
		String login, clave;
		
		login = request.getParameter("login");
		modelo.setContentType("text/plain; charset=UTF-8");
		try {
			if (procesaBD.existeUsr(login)){
				modelo.setRespuesta("existelogin");	
			}else{
				modelo.setRespuesta("noexistelogin");
			}
		} catch (BeanError e) {
			// TODO Auto-generated catch block
			
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
	 * @return
	 * @uml.property  name="modelo"
	 */
	@Override
	/**
	 * Devuelve el objeto modelo
	 */
	public Object getModelo() {
		return modelo;
	}
	/**
	 * Método setter para la propiedad modelo.
	 * @param modelo  El modelo a establecer.
	 * @uml.property  name="modelo"
	 */
	private void setModelo(Object modelo) {
		this.modelo = modelo;
	}	

	/**
	 * @return
	 * @uml.property  name="vista"
	 */
	@Override
	/**
	 * Devuelve la vista que debe procesar el modelo. En caso de ser
	 * una petición AJAX, la vista deberá ser null.
	 */
	public String getVista() {
		// La vista devuelta por una petición AJAX es null
		return vista;
	}
	/**
	 * Método setter para la propiedad vista.
	 * @param vista  La vista a establecer.
	 * @uml.property  name="vista"
	 */
	private void setVista(String vista) {
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
	 * @param ds
	 * @uml.property  name="dS"
	 */
	@Override
	/**
	 * Establece el valor del datasource
	 */
	public void setDS(DataSource ds) {
		this.DS = ds;
	}

	/**
	 * @param sc
	 * @uml.property  name="sc"
	 */
	@Override
	/**
	 * Establece el contexto de aplicación
	 */
	public void setSc(ServletContext sc) {
		this.Sc = sc;
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

	@Override
	public void setSQL(String archivoSql) {
		// TODO Auto-generated method stub
		this.archivoSql=archivoSql;
		
	}

}
