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

import javax.json.JsonArray;

import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.*;
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

/**
 * @author negrero
 * @param <GesError>
 *
 */
public class AccionAjaxListaArchivos<GesError> implements Accion {
	private String vistaOk="principalUsuario.jsp";
	private String vista=null;
	private ModeloAjax Modelo=null;
	private String sql;
	private GesError error;
	private DataSource ds;

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//leer base de datos
		ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
		this.Modelo=new ModeloAjax();
		
		try {
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
			
			//crear lista json
			
			
					   
			//enviar lista json
		} catch (BeanError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
