/**
 * Encapsula la información de error que pueda producirse mientras
 * se ejecuta la aplicación.
 * Códigos de error y descripción
 * Código   Descripción
 * 1 -      El login y clave de usuario buscados no se han encontrado o son erróneos.
 * 2 -      Error en acceso de archivo de datos de usuarios.
 * 3 -      El login ya está registrado. Inténtelo con otro.
 */
package ajaxmvc.modelo.beans;

import java.io.*;

/** 
 * Clase que encapsula un error en la aplicación y que se emplea conjuntamente con una ejecución "false" del método ejecutar() de las acciones.
 * @author  Andres Carmona Gil
 * @version  Ajax-MVC2
 */
public class BeanError extends Exception implements Serializable{
	/**
	 * Código de error.
	 * @uml.property  name="codError"
	 */
	private int codError;
	/**
	 * Mensaje asociado al mensaje de error.
	 * @uml.property  name="mensError"
	 */
	private String mensError;
	/**
	 * Excepción si el error está asociado a un objeto Exception.
	 * @uml.property  name="excepcion"
	 */
	private Exception excepcion = null;
	  
	/**
	 * Constructor.
	 * @param codError Código de error.
	 * @param mensError Mensaje de error.
	 */
	  public BeanError(int codError, String mensError)
	  {
	    super(mensError);
	    this.setCodError(codError);
	    this.setMensError(mensError);
	  }
	  
	  /**
	   * Constructor para el caso en que el error posee una Exception asociada.
	   * @param codError Código de error.
	   * @param mensError Mensaje de error.
	   * @param excepcion Objeto de tipo Exception asociada al error.
	   */
	  public BeanError(int codError, String mensError, Exception excepcion)
	  {
	    super(mensError);
	    this.setCodError(codError);
	    this.setMensError(mensError);
	    this.setExcepcion(excepcion);
	  }
	/**
	 * Método setter para la propiedad codError.
	 * @param codError  Código de error a establecer.
	 * @uml.property  name="codError"
	 */
	public void setCodError(int codError) {
		this.codError = codError;
	}
	/**
	 * Método getter para la propiedad codError.
	 * @return  El código de error.
	 * @uml.property  name="codError"
	 */
	public int getCodError() {
		return codError;
	}
	/**
	 * Método setter para la propiedad mensError.
	 * @param mensError  El mensaje de error a establecer.
	 * @uml.property  name="mensError"
	 */
	public void setMensError(String mensError) {
		this.mensError = mensError;
	}
	/**
	 * Método getter para la propiedad mensError.
	 * @return  El mensaje de error.
	 * @uml.property  name="mensError"
	 */
	public String getMensError() {
		return mensError;
	}
	/**
	 * Método setter para la propiedad excepcion.
	 * @param excepcion  La excepción a establecer.
	 * @uml.property  name="excepcion"
	 */
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	/**
	 * Método getter para la propiedad excepcion.
	 * @return  La excepción devuelta.
	 * @uml.property  name="excepcion"
	 */
	public Exception getExcepcion() {
		return excepcion;
	}
}
