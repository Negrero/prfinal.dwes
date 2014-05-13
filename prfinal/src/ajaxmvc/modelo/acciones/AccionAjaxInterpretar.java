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
	private String vistaOk="principalUsuario.jsp";
	private String vista=null;
	private ModeloAjax Modelo=null;
	private String sql;
	
	private BeanError error;
	
	private DataSource ds;
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
		this.Modelo=new ModeloAjax();
		
	
		String[] listaselec=request.getParameterValues("nombrearchivo");
		
		try {
		ArrayList<Archivo> lista=db.getListaArchivoSelect((String)request.getSession().getAttribute("login"),listaselec);
		StringBuilder objJSON = new StringBuilder("[");
		
		for (int i = 0; i<lista.size(); i++) {
			objJSON.append("{nombre:'");
			objJSON.append(lista.get(i).getNombre_fichero());
			objJSON.append("',datosInterpretados:'");
			objJSON.append(lista.get(i).getInterpreta());
			objJSON.append("'},");
		}
		objJSON.replace(objJSON.length()-1, objJSON.length(), "]");
	
		this.Modelo.setRespuesta(objJSON.toString());
		this.Modelo.setContentType("text/html; charset=UTF-8");	
		
		this.Modelo.setRespuesta("respuesta procesada");
		//crear lista json
			   
	} catch (BeanError e) {
		this.error= e;
		e.printStackTrace();
		return false;
		// TODO Auto-generated catch block
		
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
}
