/**
 * Instancia objetos de tipo Acci�n.
 * Es una clase abstracta que impide que se puedan instanciar objetos de ella,
 * pero permite que se obtengan clases derivadas.
 * Se encarga de obtener los objetos Acci�n espec�ficos para una determinada acci�n.
 */
package ajaxmvc.controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import ajaxmvc.modelo.acciones.*;
import ajaxmvc.modelo.beans.BeanError;

/** 
 * Factor�a de acciones. Debe ser abstracta para que no se creen instancias de ellas, dado
 * que s�lo dispone de un m�todo est�tico, creaAccion(), encargado de devolver un objeto 
 * de tipo Accion.
 * @author Andres Carmona Gil
 * @version  1
 */
public abstract class FactoriaAcciones {
	/**
	 * Devuelve objetos de tipo Accion en funci�n del par�metro de acci�n proporcionado.
	 * @param accion Cadena que representa la acci�n que se desea llevar a cabo
	 * @return Objeto de tipo Accion, que encapsula el proceso a llevar a cabo. Devolver� null
	 * si la acci�n solicitada no est� contemplada en la aplicaci�n.
	 * @throws BeanError 
	 */

	public static Accion creaAccion(String accion, String archivoProp) throws BeanError {
		Properties propAcciones = new Properties();
    	Class<Accion> claseAccion = null;
    	Accion objetoAccion = null;
    	String accionPedida = null;
    	
    	try {
			propAcciones.load(new FileInputStream(archivoProp));
			accionPedida = propAcciones.getProperty(accion);
			if (accionPedida!=null)
				
				try {
					//Class<Accion> tipo de clase parametrizada, es decir, q la clase es de tipo accion
					//si las clases definidas no son de ese tipo da un error
					claseAccion = (Class<Accion>) Class.forName(accionPedida);
					//instanciamos la clase
					objetoAccion = claseAccion.newInstance();
				} catch (ClassNotFoundException excepcion) {
					 throw new BeanError(2,excepcion.getMessage()+" en clase"+excepcion.getClass().getCanonicalName(),excepcion);
					
				} catch (InstantiationException excepcion) {
					excepcion.printStackTrace();
				} catch (IllegalAccessException excepcion) {
					excepcion.printStackTrace();
				}				
		   } catch (FileNotFoundException excepcion) {
			   throw new BeanError(2,excepcion.getMessage()+" en clase"+excepcion.getClass().getCanonicalName(),excepcion);
		   } catch (IOException excepcion) {
			    excepcion.printStackTrace();
		   } catch (NullPointerException npe){
			   throw new BeanError(2,"accion es null",npe);
		   }
	    //este return se ejecuta si o si porq los catch no echan la excepcion para fuera
    	//tenia puesto  un finally para q se ejecutara siempre, pero no hace falta por lo q hemos
    	//puesto arriba, q las excepciones no paran la ejecucion
		return objetoAccion;
		
	}

}
