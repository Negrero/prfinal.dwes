package ajaxmvc.modelo.acciones;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.BeanError;
import ajaxmvc.modelo.beans.ModeloAjax;
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

public class AccionAjaxLogin implements Accion {
	/** 
	 * Si no hay errores en el procesamiento de la acción y no es una peticion ajax
	 * @uml.property name="vistaOk"
	 */
	private String vistaOk="principalUsuario.jsp";
	/** 
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property name="vista"
	 */
	private String vista=null;
	/** 
	 * Objeto que encapsula el modelo que procesará para la respuesta de ajax.
	 * @uml.property name="modelo"
	 */
	private Object Modelo=null;
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

		ProcesaDAOUsuario procesaBD = new ProcesaDAOUsuario(this.ds,this.sql);
		ModeloAjax modelo = new ModeloAjax();
		
		String login, clave;
		
		login = request.getParameter("login");
		clave = request.getParameter("clave");
		modelo.setContentType("text/plain; charset=UTF-8");
		try {
			if (procesaBD.existeUsr(login,clave)){
				request.getSession().setAttribute("login", login);
				modelo.setRespuesta(this.vistaOk);
				
			}else{
				modelo.setRespuesta("loginincorrecto");
				request.getSession().removeAttribute("login");
				request.getSession().invalidate();
			}
		} catch (BeanError e) {
			
			this.error=e;
			return false;
		}
		System.out.println(modelo.getRespuesta());
		this.setModelo(modelo);
		return true;
		
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
	public Object getModelo() {
		return Modelo;
	}

	@Override
	public void setSc(ServletContext sc) {
	

	}

	@Override
	public Exception getError() {
		return (Exception) this.error;
	}

	@Override
	public void setDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public void setSQL(String archivoSql) {
			this.sql=archivoSql;
	}

	/** 
	 * @uml.property  name="modelo"
	 */
	public void setModelo(Object modelo) {
		Modelo = modelo;
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
