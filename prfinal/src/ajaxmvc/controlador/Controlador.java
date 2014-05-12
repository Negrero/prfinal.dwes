package ajaxmvc.controlador;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;



import ajaxmvc.modelo.beans.*;

/** 
 * Implementación del servlet Controlador
 * @author Andres Carmona Gil
 * @version  1
 */
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	/* Es recomendable establecer como propiedades del servlet aquellos objetos
	 * que encapsulan fuentes de datos.
	*/
	private  org.apache.log4j.Logger log =  org.apache.log4j.Logger.getRootLogger();
	/**
	 * Cualquier fuente de datos que requiera acceso desde la aplicación deberá contemplarse como propiedad del servlet. En este caso, la fuente de datos es la base de datos bdfotogramas.
	 * @uml.property  name="dsBdfotogramas"
	 */
  private DataSource dspffinal;
	/**
     * Este objeto es precisamente el objeto que encapsula toda la información
     * a nivel de aplicación. Se corresponde con el objeto application generado
     * por el contenedor web al generar los servlets de las páginas JSP.
     */
  
  	private String archivoPropiedades;
  	
  	private String archivoSql;
  	
	private ServletContext sc; 

	/**
     * Inicializa el servlet, y le proporciona un objeto, ServletConfig con
     * informaciï¿½n de nivel de aplicaciï¿½n sobre el contexto de datos que rodea
     * al servlet en el contenedor web.
     * @see Servlet#init(ServletConfig)
     */

	public void init(ServletConfig config) throws ServletException {
	// Imprescindible llamar a super.init(config) para tener acceso a la configuraciÃ³n
	// del servlet a nivel de contenedor web.
		
		inicializaLog4j(config);
		
	    super.init(config);
	    
	    try {
	    	sc = config.getServletContext();
	    	Context contexto = new InitialContext();
	    	setDsPffinal((DataSource) contexto.lookup(sc.getInitParameter("ds")));
	    	
	    	if (getDspffinal()==null)
	    		System.out.println("dspffinal es null.");
	    	// El datasource se almacena a nivel de aplicaciï¿½n.
	    	sc.setAttribute("dspffinal", getDspffinal());
	    	
	    	archivoPropiedades=config.getInitParameter("archivoPropiedades");
	    	
	    	this.archivoSql=config.getInitParameter("archivoSql");
	    } 
	    catch(NamingException ne)
	    {
	        System.out.println("Error: Método init(). tipo NamingException.");
	        ne.printStackTrace();
	    } 
	}

	private void inicializaLog4j(ServletConfig config) {

		
		// TODO Auto-generated method stub
			ServletContext sc = config.getServletContext();
		    String location = config.getInitParameter("archivo-config-log4j");
		    if (location != null) {
		      location = sc.getRealPath(location);

			// Write log message to server log.
			sc.log("Inicializan Log4J de archivo de configuracion \n [" + location + "]");
			
			File file = new File(location);
			if (!file.exists()) {
				System.err.println("*** " +location+ " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();	
				System.out.println("Log4J config file [" + location + "] not found");
			}else{
			
			      // Initialize with refresh interval, i.e. with Log4J's watchdog thread,
			      // checking the file in the background.
			    try {
			        
			        if (location.toLowerCase().endsWith(".xml")) {
			          DOMConfigurator.configure(file.getAbsolutePath());
			        } else {
			          PropertyConfigurator.configure(file.getAbsolutePath());
			        }
			       
			   } catch (NumberFormatException ex) {
			        throw new IllegalArgumentException("Invalid 'log4jRefreshInterval' parameter: " + ex.getMessage());
			   }
			}
		    }
		  }
	

	/**
     * Lo último que se debe hacer antes de que se elimine la instancia del
     * servlet.
     * @see Servlet#destroy()
     */

	public void destroy() {
      // Elimina el datasource del ï¿½mbito de aplicaciï¿½n, liberando todos los
      // recursos que tuviera asignados.
      sc.removeAttribute("dspffinal");
      // Elimina el ï¿½mbito de aplicaciï¿½n.
      sc = null;
	}

	/**
     * Procesa las peticiones que vienen por la vía GET.
     * @param request La petición.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Error al ejecutar doPost()
     * @throws java.io.IOException Error de E/S proviniente de doPost()
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se reenvï¿½a hacia el mï¿½todo doPost(), ya que tanto las peticiones GET como
	    // las POST se procesarï¿½n igual, y de esta manera, se evita cï¿½digo redundante.
	    doPost(request,response);		
	}

	/**
     * Procesa las peticiones que vienen por la vï¿½a POST.
     * @param request La peticiï¿½n.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser lanzada por forward().
     * @throws java.io.IOException Puede ser lanzada por forward().
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Se obtiene el objeto de ambito sesion
		// no se usa, se comenta
		// HttpSession sesion = request.getSession();

		// Obtener referencia archivo propiedades acciones-clases asociadas

		String archivoProp = sc.getRealPath("/WEB-INF/" + archivoPropiedades);
		String archivoSql = sc.getRealPath("/WEB-INF/" + this.archivoSql);
		log.info("archivo acciones cargado:" + archivoProp);
		log.info("archivo sql cargado:" + archivoSql);

		// Obtener un objeto de ayuda para la solicitud
		AyudaSolicitud ayudaSol;

		ayudaSol = new AyudaSolicitud(request);

		// Crear un objeto de acciï¿½n partiendo de los parï¿½metros asociados a
		// la solicitud
		Accion accion;
		try {
		
				accion = ayudaSol.getAccion(archivoProp);

				// Se proporciona el contexto del servlet (ï¿½mbito aplicaciï¿½n) a
				// la
				// acciï¿½n
				accion.setSc(sc);
				// Le establece el nombre del archivo con las sentencias sql desde
				// conexto

				accion.setSQL(archivoSql);
				// Se proporciona el DataSource asociado al servlet a la acciï¿½n
				accion.setDS(this.dspffinal);
				// Se procesa la solicitud (lï¿½gica de empresa)
				log.info("1-accion ejecutada:  " + accion.getClass().getSimpleName());
				if (accion.ejecutar(request, response)) {
					// Si es correcto, obtener el componente relativo a la vista
					String vista = accion.getVista();
					System.out.println("la vista de la accion en el controlador es:"+accion.getVista());
					Object modelo = accion.getModelo();
					log.info("2-vista (si es null es una peticion ajax):" + vista);
					if (vista != null) {
						// Aï¿½adir en la peticiï¿½n el modelo a visualizar
						log.info("3-modelo de la accion("+accion.getClass().getSimpleName()+"):");
						request.setAttribute("modelo", accion.getModelo());
						// Enviar la respuesta a la solicitud
						RequestDispatcher rd = request.getRequestDispatcher(vista);
						rd.forward(request, response);
					} else {
						// modelo especifico para los beans de modelo tipo ajax
						// ver ModeloAjax.java
						ejecutarAjax(request, response, (ModeloAjax) modelo);
					}
				} else {
					// Si la ejecuciï¿½n ha generado un error, procesarlo mediante
					// el
					// gestor centralizado de errores
					log.error(accion.getError());
					gesError(accion.getVista(), accion.getError(), request,
							response);
				}
			
		} catch (BeanError e) {
			// TODO Auto-generated catch block
			log.error("accion no existe es null"+e.getStackTrace()[0].toString());
			gesError(null,e, request,	response);
		}catch (NullPointerException npe){
			log.error("NullPointerException "+npe.getStackTrace()[0].toString());
			npe.printStackTrace();
			
			
		}

	}
	
	/**
     * Reenvï¿½a el proceso hacia una vista de gestiï¿½n de errores.
     * @param vistaError Pï¿½gina que gestionarï¿½ el error.
     * @param excepcion Objeto encapsulador de la excepciï¿½n.
     * @param request La peticiï¿½n.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser generada por forward().
     * @throws java.io.IOException Puede ser generada por forward().
     */
  private void gesError(String vistaError, Exception excepcion, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
	if (vistaError==null)
		vistaError="gesError.jsp";
	
    RequestDispatcher rd = request.getRequestDispatcher(vistaError);
    request.setAttribute("error",excepcion);
    rd.forward(request,response);
    log.error(excepcion+" "+excepcion.getStackTrace()[0].toString());
    //para lanzar error 500 para capturarlo con ajax
    throw new ServletException(excepcion);
    
  }
  
  private void ejecutarAjax (HttpServletRequest request, HttpServletResponse response, ModeloAjax modelo) {
	  PrintWriter out = null ;

	  try {
		  out = response.getWriter();
		  response.setContentType(modelo.getContentType());
		  out.println(modelo.getRespuesta());
		 log.info("3-modelo ajax: "+modelo.getRespuesta());
	  }
	  catch(IOException ioe) {
		  //Este error hay que canalizarlo hacia gesError()
		  System.out.println("Error en respuesta AJAX");
		  log.error("Error en respuesta AJAX");
	  }
	  finally{
		  //se cierra el flujo
		  out.close();
	  }
  }

  /**
 * Establece la fuente de datos para la aplicaciï¿½n.
 * @param dspffinal  Acceso a la base de datos bdfotogramas.
 * @uml.property  name="dsBdfotogramas"
 */
	public void setDsPffinal(DataSource dspffinal) {
		this.dspffinal = dspffinal;
	}

    /**
	 * Obtiene la referencia a la fuente de datos de la aplicaciï¿½n.
	 * @return  La fuente de datos asociada a la base de datos bdfotogramas.
	 * @uml.property  name="dsBdfotogramas"
	 */
	public DataSource getDspffinal() {
		return dspffinal;
	}

}
