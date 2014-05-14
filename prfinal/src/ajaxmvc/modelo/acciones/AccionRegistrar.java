package ajaxmvc.modelo.acciones;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import ajaxmvc.modelo.beans.*;
import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.procesos.*;

/** 
 * Acción registrar. Registra un usuario
 * @author Andres Carmona Gil
 * @version  1
 *  Accion que lleva a cabo el grabado de datos de un usuario
 */
public class AccionRegistrar implements Accion {

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
	@SuppressWarnings("unused")
	private ServletContext Sc;
	/** 
	 * Nombre del archivo de las sentencias sql
	 * @uml.property name="archivoSql"
	 */
	private String sql;
	
		
			
				
				/** 
				 * Este ejecutar comprueba de que el usuario no esta registrado ya
				 * y ponemos atributo login en session
				 */
				public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
				
							
									
											ProcesaDAOUsuario procesaBD;
											Usuario user;
											String login, clave,email;
											boolean estado = true;
											HttpSession sesion = request.getSession();
													
											login = request.getParameter("login");
											clave = request.getParameter("clave");
											email = request.getParameter("email");
											
											if ((login!=null) && (login!="") && (clave!=null) && (clave!=""))
											{
												user = new Usuario(login,clave,email);
												user.setNombre(request.getParameter("nombre"));
												try {
													procesaBD = new ProcesaDAOUsuario(this.getDS(),this.sql);
												
													if (procesaBD.registrarUsuario(user))
													{
														this.setVista(vistaOk);
														// para que nadie pueda entrar en otras paginas reservadas a los usuario registrados
														// ya que luego preguntaremos por el login si existe en sesion
													
														request.getSession().setAttribute("login", login);
													}
													else 
													{
														
														sesion.invalidate();
														this.setVista(vistaNoOk);
													}
												} catch (BeanError e) {
											
													this.error=e;
													return false;
												}	
											}
											else
												this.setVista(vistaNoOk);
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
	@SuppressWarnings("unused")
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

	@Override
	public void setSQL(String archivoSql) {
		this.sql=archivoSql;
	}

}
