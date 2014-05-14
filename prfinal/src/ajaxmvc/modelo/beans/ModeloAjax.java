package ajaxmvc.modelo.beans;

/** 
 * Modelo para peticiones AJAX
 * @author Andres Carmona Gil
 * @version  1
 *  Para procesar peticiones AJAX que seran casi siempre con formato json
 */
public class ModeloAjax {
	/**
	 * Tipo de contenido devuelto como respuesta
	 * @uml.property  name="contentType"
	 */
	private String contentType;
	/**
	 * La respuesta devuelta mediante AJAX
	 * @uml.property  name="respuesta"
	 */
  //es de tipo object para meter cualquier tipo de respuesta.
	//la accion correspondiente crea la respuesta
	private Object respuesta;
	
	/**
	 * Constructor sin parámetros
	 */
	public ModeloAjax() {
		
	}
	/**
	 * Constructor que crea un modelo para respuesta AJAX
	 * @param contentType Tipo de contenido
	 * @param respuesta Datos devueltos como respuesta, en este caso string pero puede ser cualquier tipo porq esta definido como object
	 */
	public ModeloAjax(String contentType, String respuesta) {
		this.contentType = contentType;
		this.respuesta = respuesta;
	}
	/**
	 * @param contentType  the contentType to set
	 * @uml.property  name="contentType"
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return  the contentType
	 * @uml.property  name="contentType"
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param respuesta  the respuesta to set
	 * @uml.property  name="respuesta"
	 */
	public void setRespuesta(Object respuesta) {
		this.respuesta = respuesta;
	}
	/**
	 * @return  the respuesta
	 * @uml.property  name="respuesta"
	 */
	public Object getRespuesta() {
		return respuesta;
	}
	
}
