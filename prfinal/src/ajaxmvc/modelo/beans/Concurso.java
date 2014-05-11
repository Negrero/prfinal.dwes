/**
 * 
 */
package ajaxmvc.modelo.beans;

/** 
 * Encapsula los datos del concurso por fotograma
 * (idfotogrma,login,puntos)
 * @author negrero
 */
public class Concurso {

	/** 
	 * login del concursante
	 * @uml.property name="login"
	 */
	private String login;

	/** 
	 * Getter of the property <tt>login</tt>
	 * @return  Returns the login.
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}

	/** 
	 * Setter of the property <tt>login</tt>
	 * @param login  The login to set.
	 * @uml.property  name="login"
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/** 
	 * id del fotograma que hemos elegido
	 * @uml.property name="idfotograma"
	 */
	private String idfotograma;

	/** 
	 * Getter of the property <tt>idfotograma</tt>
	 * @return  Returns the idfotograma.
	 * @uml.property  name="idfotograma"
	 */
	public String getIdfotograma() {
		return idfotograma;
	}

	/** 
	 * Setter of the property <tt>idfotograma</tt>
	 * @param idfotograma  The idfotograma to set.
	 * @uml.property  name="idfotograma"
	 */
	public void setIdfotograma(String idfotograma) {
		this.idfotograma = idfotograma;
	}

	/** 
	 * punto 1 si hemos acertado el fotograma o -0,5 si no lo  hemos acertado
	 * @uml.property name="puntos"
	 */
	private String puntos;

	/** 
	 * Getter of the property <tt>puntos</tt>
	 * @return  Returns the puntos.
	 * @uml.property  name="puntos"
	 */
	public String getPuntos() {
		return puntos;
	}

	/** 
	 * Setter of the property <tt>puntos</tt>
	 * @param puntos  The puntos to set.
	 * @uml.property  name="puntos"
	 */
	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

}
