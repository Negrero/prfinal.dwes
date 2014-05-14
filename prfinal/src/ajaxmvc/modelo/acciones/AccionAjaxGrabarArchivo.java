/**
 * 
 */
package ajaxmvc.modelo.acciones;

import java.io.IOException;
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
import ajaxmvc.controlador.Accion;
import ajaxmvc.modelo.beans.*;
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

/** 
 * @author Andres Carmona Gil	
 * @version 1
 *  Accion que graba los ficheros si tienen un nombre correcto en la base de datos
 */
public class AccionAjaxGrabarArchivo implements Accion {

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
	/** 
	 * Objeto que encapsula el modelo que procesará para la respuesta de ajax.
	 * @uml.property name="modelo"
	 */
	private ModeloAjax Modelo=null;
	/** 
	 * Nombre del archivo de sentencias sql
	 * @uml.property name="sql"
	 */
	private String sql;
	
	/** 
	 * datasource de la base de datos
	 * @uml.property name="ds"
	 */
	private DataSource ds;
	/** 
	 * Bean de error para situaciones en los que el método ejecutar() devuelve false.
	 * @uml.property name="error"
	 */
	private Exception error;

		
		/** 
		 * Graba los archivos de un usuario en la base de datos
		 */
		public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
			
				
				
				Collection<Part> partes = request.getParts();
				ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
				@SuppressWarnings({ "unchecked", "rawtypes" })
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

		
		/** 
		 * Obtiene el nombre del archivo de los datos enviado desde el cliente
		 */
		private String getNombreArchivo(Part part){
		
				  Pattern p = Pattern.compile("filename=\"(.+?)\"");
				  Matcher m = p.matcher(part.getHeader("Content-Disposition"));
				  if(m.find()){
				   return m.group(1);
				  }else{
				   throw new IllegalStateException("Cannot fin filename in web request header.");
				  }
				  }
	/** 
	 * @uml.property  name="modelo"
	 */
	public void setModelo(ModeloAjax modelo) {
		Modelo = modelo;
	}

		
		/** 
		 * obtiene vista que sera null para las peticiones ajax
		 * @uml.property  name="vista"
		 */
		public String getVista(){
		
			
			return this.vista;
		}

	/** 
	 * @uml.property  name="modelo"
	 */
	@Override
	public ModeloAjax getModelo() {
		return Modelo;
	}

		
		/** 
		 * Contexto de la aplicacion
		 */
		public void setSc(ServletContext sc){
		
	
		
			 }

	/** 
	 * @uml.property  name="error"
	 */
	@Override
	public Exception getError() {
		return error;
	}

		
		/** 
		 * establece el datasource de la aplicacion
		 */
		public void setDS(DataSource ds){
		
				this.ds=ds;
			 }

		
		/** 
		 * establece el nombre del archivo de sentencias sql
		 */
		public void setSQL(String archivoSql){
		
					this.sql=archivoSql;
			 }

	/** 
	 * Setter of the property <tt>error</tt>
	 * @param error  The error to set.
	 * @uml.property  name="error"
	 */
	public void setError(Exception error) {
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

}
