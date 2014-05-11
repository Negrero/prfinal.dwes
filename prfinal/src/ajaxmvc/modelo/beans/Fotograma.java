/**
 * 
 */
package ajaxmvc.modelo.beans;

/** 
 * Encapsula los datos de un fotograma como son: (año de estreno, archivo, director, genero, idfotograma, titpelicula)
 * @author negrero
 */
public class Fotograma {

	/**
	 * id de la pelicula
	 * @uml.property  name="idfotograma"
	 */
	private int idfotograma;

	/**
	 * Getter of the property <tt>idfotograma</tt>
	 * @return  Returns the idfotograma.
	 * @uml.property  name="idfotograma"
	 */
	public int getIdfotograma() {
		return idfotograma;
	}

	/**
	 * Setter of the property <tt>idfotograma</tt>
	 * @param idfotograma  The idfotograma to set.
	 * @uml.property  name="idfotograma"
	 */
	public void setIdfotograma(int idfotograma) {
		this.idfotograma = idfotograma;
	}

	/**
	 * nombre del archivo que contiene la imagen de la pelicula
	 * @uml.property  name="archivo"
	 */
	private String archivo;

	/**
	 * Getter of the property <tt>archivo</tt>
	 * @return  Returns the archivo.
	 * @uml.property  name="archivo"
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * Setter of the property <tt>archivo</tt>
	 * @param archivo  The archivo to set.
	 * @uml.property  name="archivo"
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	/**
	 * Titulo de la pelicula
	 * @uml.property  name="titPelicula"
	 */
	private String titPelicula;

	/**
	 * Getter of the property <tt>titPelicula</tt>
	 * @return  Returns the titPelicula.
	 * @uml.property  name="titPelicula"
	 */
	public String getTitPelicula() {
		return titPelicula;
	}

	/**
	 * Setter of the property <tt>titPelicula</tt>
	 * @param titPelicula  The titPelicula to set.
	 * @uml.property  name="titPelicula"
	 */
	public void setTitPelicula(String titPelicula) {
		this.titPelicula = titPelicula;
	}

	/**
	 * Año de estreno de la pelicula
	 * @uml.property  name="anyoEstreno"
	 */
	private int anyoEstreno;

	/**
	 * Getter of the property <tt>anyoEstreno</tt>
	 * @return  Returns the anyoEstreno.
	 * @uml.property  name="anyoEstreno"
	 */
	public int getAnyoEstreno() {
		return anyoEstreno;
	}

	/**
	 * Setter of the property <tt>anyoEstreno</tt>
	 * @param anyoEstreno  The anyoEstreno to set.
	 * @uml.property  name="anyoEstreno"
	 */
	public void setAnyoEstreno(int anyoEstreno) {
		this.anyoEstreno = anyoEstreno;
	}

	/**
	 * Director de la pelicula
	 * @uml.property  name="director"
	 */
	private String director;

	/**
	 * Getter of the property <tt>director</tt>
	 * @return  Returns the director.
	 * @uml.property  name="director"
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Setter of the property <tt>director</tt>
	 * @param director  The director to set.
	 * @uml.property  name="director"
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * genero de la pelicula
	 * @uml.property  name="genero"
	 */
	private String genero;

	/**
	 * Getter of the property <tt>genero</tt>
	 * @return  Returns the genero.
	 * @uml.property  name="genero"
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Setter of the property <tt>genero</tt>
	 * @param genero  The genero to set.
	 * @uml.property  name="genero"
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

		
		/**
		 */
		public Fotograma(){
		}

}
