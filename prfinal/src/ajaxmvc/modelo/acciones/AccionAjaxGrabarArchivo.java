/**
 * 
 */
package ajaxmvc.modelo.acciones;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.servlet.*;
import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.*;
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

/**
 * @author negrero
 *
 */
public class AccionAjaxGrabarArchivo implements Accion {

	private String vistaOk="principalUsuario.jsp";
	private String vista=null;
	private ModeloAjax Modelo=null;
	private String sql;
	
	private DataSource ds;
	private Exception error;

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		Collection<Part> partes = request.getParts();
		ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
		HashMap<String,String> listaarchivosubido=new HashMap();
		this.Modelo=new ModeloAjax();
		
		for (Part trozo:partes){
			if(trozo!=null){
				Archivo archivo=new Archivo(getNombreArchivo(trozo),trozo.getInputStream());
				if (archivo.isArchivo()){
					try {
						
						listaarchivosubido.put(archivo.getNombre_fichero(), db.setGrabaArchivo((String) request.getSession().getAttribute("login"),archivo));
						System.out.println("nombre fichero:"+getNombreArchivo(trozo));
					} catch (BeanError e) {
						// TODO Auto-generated catch block
						this.error=e;
						return false;
					}
				}else{
					listaarchivosubido.put(getNombreArchivo(trozo),"formato de archivo incorrecto(boe 105 del 3/5/2005 pag 15128)");
				}
			}	
		}
		
		this.Modelo.setRespuesta(preparerarModelo(listaarchivosubido));
		this.Modelo.setContentType("text/html; charset=UTF-8");	
		return true;
	}
	
	private Object preparerarModelo(HashMap<String, String> listaarchivosubido) {
		
		StringBuilder objJSON = new StringBuilder("[");
		
		for (String nombre:listaarchivosubido.keySet()) {
			
			objJSON.append("{nombre:'");
			objJSON.append(nombre);
			objJSON.append("',mensaje:'");
			objJSON.append(listaarchivosubido.get(nombre));
			objJSON.append("'},");
		}
		objJSON.replace(objJSON.length()-1, objJSON.length(), "]");
	
		return objJSON.toString();
	}

	private String getNombreArchivo(Part part){
		  Pattern p = Pattern.compile("filename=\"(.+?)\"");
		  Matcher m = p.matcher(part.getHeader("Content-Disposition"));
		  if(m.find()){
		   return m.group(1);
		  }else{
		   throw new IllegalStateException("Cannot fin filename in web request header.");
		  }
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
