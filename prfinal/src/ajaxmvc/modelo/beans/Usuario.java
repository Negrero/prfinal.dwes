package ajaxmvc.modelo.beans;

import java.io.Serializable;
/** 
 * Encapsula los datos (login y clave) de un usuario
 * @author  Andres Carmona Gil
 */
public class Usuario implements Serializable{
	/**
	 * login de usuario
	 * @uml.property  name="login"
	 */
	private String login = null;
	/**
	 * clave de usuario
	 * @uml.property  name="clave"
	 */
	private String clave = null;
	
	private String email=null;
	
	/**
	 * Constructor sin parámetros
	 */
	public Usuario() {}
	/**
	 * Constructor con parámetros (login y clave)
	 * @param login Login de usuario
	 * @param clave Clave de usuario
	 */
	public Usuario(String login, String clave,String email) {
		this.setLogin(login);
		this.setClave(clave);
		this.setEmail(email);
	}
	/**
	 * @param login  the login to set
	 * @uml.property  name="login"
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return  the login
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param clave  the clave to set
	 * @uml.property  name="clave"
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	/**
	 * @return  the clave
	 * @uml.property  name="clave"
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * Nombre del usuario
	 * @uml.property  name="nombre"
	 */
	private String nombre;

	/**
	 * Getter of the property <tt>nombre</tt>
	 * @return  Returns the nombre.
	 * @uml.property  name="nombre"
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Setter of the property <tt>nombre</tt>
	 * @param nombre  The nombre to set.
	 * @uml.property  name="nombre"
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
