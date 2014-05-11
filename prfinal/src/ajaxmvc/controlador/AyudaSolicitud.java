/**
 * Permite crear un objeto de ayuda que acepta el objeto de solicitud
 * en su constructor. Proporciona funcionalidad adicional a la solicitud.
 */
package ajaxmvc.controlador;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import ajaxmvc.modelo.beans.BeanError;

/** 
 * @author Andres Carmona Gil
 * @version  1
 */
public class AyudaSolicitud {
	
	/**
	 * La petición del usuario, que incorporará el parámetro acción
	 * que determina el proceso a llevar a cabo.
	 */
	HttpServletRequest request;
	
	/**
	 * Constructor que prepara el objeto para procesar la acción de la
	 * petición.
	 * @param request La petición.
	 * @throws ServletException Excepción grave a nivel de Servlet
	 */
	public AyudaSolicitud(HttpServletRequest request)
	    throws ServletException
	  {
	    this.request = request;
	  }
	/**
	 * Devuelve el objeto de tipo Accion que deberá procesar la petición.
	 * @return Obtejo de tipo Accion
	 * @throws BeanError 
	 */
	public Accion getAccion(String archivoProp) throws BeanError 
	  {
	    String accion = (String) request.getParameter("accion");
	    //System.out.println("Accion: "+accion);
	    // Antes de crear la acci�n puede haber procesamiento previo de la
	    // solicitud, para dejarla preparada para el m�todo creaAccion().
	   
			return FactoriaAcciones.creaAccion(accion, archivoProp);
		
	  }

}
