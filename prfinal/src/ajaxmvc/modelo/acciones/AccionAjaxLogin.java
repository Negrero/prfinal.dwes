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

public class AccionAjaxLogin<GesError> implements Accion {
	private String vistaOk="principalUsuario.jsp";
	private String vista=null;
	private Object Modelo=null;
	private String sql;
	private GesError error;
	private DataSource ds;
	
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			this.error=(GesError) e;
			return false;
		}
		System.out.println(modelo.getRespuesta());
		this.setModelo(modelo);
		return true;
		
	}

	private void setModelo(ModeloAjax modelo) {
		// TODO Auto-generated method stub
		this.Modelo=modelo;
	}

	@Override
	public String getVista() {
		// TODO Auto-generated method stub
		return this.vista;
	}

	@Override
	public Object getModelo() {
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
		return (Exception) this.error;
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

}
