package ajaxmvc.modelo.beans;

public class Errores extends Exception {
	
	/**
*	Exception()
*Constructs a new exception with null as its detail message.
* 	Exception(String message)
*Constructs a new exception with the specified detail message.
* 	Exception(String message, Throwable cause)
*Constructs a new exception with the specified detail message and cause.
*protected	Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
*Constructs a new exception with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
* 	Exception(Throwable cause)
*Constructs a new exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
**/
	public Errores(){
		super();
	}
	public Errores(String mensaje){
		super(mensaje);
	}
	public Errores(String mensaje, Throwable causa){
		super(mensaje,causa);
	}
	public Errores(Throwable causa){
		super(causa);
	}
}
