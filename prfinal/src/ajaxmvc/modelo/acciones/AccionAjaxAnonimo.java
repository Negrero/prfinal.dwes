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
import ajaxmvc.modelo.beans.Archivo;
import ajaxmvc.modelo.beans.BeanError;
import ajaxmvc.modelo.beans.ModeloAjax;
import ajaxmvc.modelo.procesos.ProcesaDAOUsuario;

/** 
 * @author Andres Carmona Gil
 *  accion encargada de procesar los archivos enviados por un cliente no identificado
 *  en el sistema
 */
public class AccionAjaxAnonimo implements Accion {

	/* (non-Javadoc)
	 * @see ajaxmvc.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
	/** 
	 * Si no hay errores en el procesamiento de la acción y no es una peticion ajax
	 * @uml.property name="vistaOk"
	 */
	@SuppressWarnings("unused")
	private String vistaOk="principalAnonimo.jsp";
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
	 * Bean de error para situaciones en los que el método ejecutar() devuelve false.
	 * @uml.property name="error"
	 */
	private BeanError error;
	/** 
	 * datasource de la base de datos
	 * @uml.property name="ds"
	 */
	private DataSource ds;

		
		/** 
		 * prepara los archivos enviados desde el clienta para su interpretacion (maximo de cinco)
		 */
		public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
				// TODO Auto-generated method stub
				//leer base de datos
				Collection<Part> partes = request.getParts();
				@SuppressWarnings("unused")
				ProcesaDAOUsuario db=new ProcesaDAOUsuario(this.ds,this.sql);
				@SuppressWarnings({ "unchecked", "rawtypes" })
				HashMap<Archivo,String> listaarchivosubido=new HashMap();
				this.Modelo=new ModeloAjax();
				for (Part trozo:partes){
					if(trozo!=null){
						Archivo archivo=new Archivo(getNombreArchivo(trozo),trozo.getInputStream());
						if (archivo.isArchivo()){
								listaarchivosubido.put(archivo,"correcto" );
						
						}else{
							listaarchivosubido.put(archivo,"formato de archivo incorrecto(boe 105 del 3/5/2005 pag 15128)");
						}
					}	
				}
				
				this.Modelo.setRespuesta(preparerarModelo(listaarchivosubido));
				this.Modelo.setContentType("text/html; charset=UTF-8");	
				return true;
			 }
	
	private Object preparerarModelo(HashMap<Archivo, String> listaarchivosubido) {
		
		StringBuilder objJSON = new StringBuilder("[");
		
		for (Archivo archivo:listaarchivosubido.keySet()) {
			
			if(listaarchivosubido.get(archivo).equals("correcto")){
				objJSON.append("{nombre:'");
				objJSON.append(archivo.getNombre_fichero());
				objJSON.append("',mensaje:'");
				objJSON.append(archivo.isArchivo());
				objJSON.append("',datosInterpretados:'");
				objJSON.append(archivo.getInterpreta());
				objJSON.append("',tipo:'");
				objJSON.append(archivo.getTipo());
				objJSON.append("'},");
			}else{
				objJSON.append("{nombre:'");
				objJSON.append(archivo.getNombre_fichero());
				objJSON.append("',mensaje:'");
				objJSON.append("formato de archivo incorrecto(boe 105 del 3/5/2005 pag 15128)");
				objJSON.append("'},");
			}
		
		}
		objJSON.replace(objJSON.length()-1, objJSON.length(), "]");
	
		return objJSON.toString();
	}
		
			
			/** 
			 * Obtine el nombre del archivo que esta dentro de los archivos enviados con la peticion.
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

	@Override
	public void setSc(ServletContext sc) {
		// TODO Auto-generated method stub

	}

	/** 
	 * @uml.property  name="error"
	 */
	@Override
	public BeanError getError() {
		return error;
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

}
