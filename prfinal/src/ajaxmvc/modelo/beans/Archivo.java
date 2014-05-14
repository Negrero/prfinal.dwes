/**
 * 
 */
package ajaxmvc.modelo.beans;



import java.io.IOException;
import java.io.InputStream;

import java.util.NoSuchElementException;

import java.util.StringTokenizer;


import sun.security.krb5.internal.crypto.crc32;

/** 
 * @author Andres Carmona Gil
 *  Clase que encapsula al archivo tgd del tacografo o de la tarjeta del conductor
 *  segun boe num 105 del 3-5-2005 pag 15128
 */
public class Archivo implements InterpretaArchivo{
	
	

	/** 
	 * El fichero binario
	 * @uml.property name="fichero"
	 */
	private InputStream fichero=null;
	
	/** 
	 * nombre del fichero completo
	 * @uml.property name="nombre_fichero"
	 */
	private String nombre_fichero=null;
	
	/** 
	 * identificacion que puede ser la de conductor para archivos de tarjeta 
	 * o la matricula del vehiculo tambien en el nombre del archivo
	 * @uml.property name="identificacion"
	 */
	private String identificacion=null;
	
	/** 
	 * letra que corresponde al pais del los datos incluido en el
	 * nombre del archivo
	 * @uml.property name="pais"
	 */
	private String pais=null;
	
	/** 
	 * dos tipos:
	 * V vehiculo
	 * C conductor
	 * incluido en el nombre del archivo
	 * @uml.property name="tipo"
	 */
	private String tipo=null;
	
	/** 
	 * fecha que esta incluida en el nombre del fichero
	 * @uml.property name="fecha"
	 */
	private String fecha=null;
	
	/** 
	 * Hora que esta en el nombre del fichero
	 * @uml.property name="hora"
	 */
	private String hora=null;
	
	/** 
	 * extension del archivo TGD (solo para algunos paises)
	 * @uml.property name="extension"
	 */
	@SuppressWarnings("unused")
	private static final String extension="TGD";
	

	/** 
	 * codigo crc32 para seguridad (no implementado en esta aplicacion)
	 * pero se implementara para futuras
	 * @uml.property name="checksum"
	 */
	private crc32 checksum=null;
	
		
		/** 
		 * constructor que recoge el nombre del archivo y el fichero biniario,
		 * para poder sacar los datos del nombre del archivo
		 */
		public Archivo(String nombre, InputStream is){
		
				this.nombre_fichero=nombre;
				StringTokenizer st=new StringTokenizer(nombre,"_.");
				try{
					this.tipo=st.nextToken().trim();
					this.identificacion=st.nextToken().trim();
					this.pais=st.nextToken().trim();
					this.fecha=st.nextToken().trim();
					this.hora=st.nextToken().trim();
				}catch(NoSuchElementException nsee){
					nsee.printStackTrace();
					
				}
				if(this.isArchivo()){
					this.fichero=is;
				}
				
			 }
	
	

	/** 
	 * Obtiene el codigo crc32 del fichero
	 * @return the checksum
	 * @uml.property  name="checksum"
	 */
	public crc32 getChecksum() {
		return checksum;
	}

	/** 
	 * Implementata pero no usada, para un futuro
	 * @param checksum the checksum to set
	 * @uml.property  name="checksum"
	 */
	public void setChecksum(crc32 checksum) {
		this.checksum = new crc32();
	
		this.checksum = checksum;
	}

		
		/** 
		 * devuelve el fichero contenido que esta en binario a hexadecimal
		 */
		public Object getInterpreta(){
		
				
				
				StringBuffer retString=null;
				
				
				if (this.fichero!=null){
					try {
						byte[] bytes = new byte[this.fichero.available()];
						
						this.fichero.read(bytes);
						retString = new StringBuffer();
					
						for (int i = 0; i < bytes.length; ++i) {
							retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1));
							if((i%2)==0)
								retString.append(" ");
						}
					} catch (IOException e) {
					
						e.printStackTrace();
					}
				}
				
				return retString.toString();
				
			 }

	/** 
	 * Obtiene el nombre del archivo
	 * @return the fecha
	 * @uml.property  name="fecha"
	 */
	public String getFecha() {
		return fecha;
	}

	/** 
	 * Establece la fecha desde el nombre del archivo
	 * @param fecha the fecha to set
	 * @uml.property  name="fecha"
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/** 
	 * Obtiene la hora del nombre del archivo
	 * @return the hora
	 * @uml.property  name="hora"
	 */
	public String getHora() {
		return hora;
	}

	/** 
	 * Establece la hora desde el nombre del archivo
	 * @param hora the hora to set
	 * @uml.property  name="hora"
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/** 
	 * Obtiene el fichero binario
	 * @return the fichero
	 * @uml.property  name="fichero"
	 */
	public InputStream getFichero() {
		return fichero;
	}

	/** 
	 * Establece el fichero binario
	 * @param fichero the fichero to set
	 * @uml.property  name="fichero"
	 */
	public void setFichero(InputStream fichero) {
		this.fichero = fichero;
	}

	/** 
	 * Obtiene la identicacion del nombre del archivo
	 * @return the identificacion
	 * @uml.property  name="identificacion"
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/** 
	 * Establece la identificacion del fichero
	 * @param identificacion the identificacion to set
	 * @uml.property  name="identificacion"
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/** 
	 * Obtiene la letra del pais
	 * @return the pais
	 * @uml.property  name="pais"
	 */
	public String getPais() {
		return pais;
	}

	/** 
	 * Establece la letra del pais
	 * @param pais the pais to set
	 * @uml.property  name="pais"
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/** 
	 * Obtiene el tipo de archivo (c o v)
	 * @return the tipo
	 * @uml.property  name="tipo"
	 */
	public String getTipo() {
		return tipo;
	}

	/** 
	 * Establece el tipo de archivo
	 * @param tipo the tipo to set
	 * @uml.property  name="tipo"
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/** 
	 * Devuelve el string con el nombre del fichero
	 * @return the nombre_fichero
	 * @uml.property  name="nombre_fichero"
	 */
	public String getNombre_fichero() {
		return nombre_fichero;
	}

	/** 
	 * Establece nombre del fichero
	 * @param nombre_fichero the nombre_fichero to set
	 * @uml.property  name="nombre_fichero"
	 */
	public void setNombre_fichero(String nombre_fichero) {
		this.nombre_fichero = nombre_fichero;
	}
	/**
	 * No operativo pero con implementacion en un futuro
	 */
	public void getFactoriaFichero(){
		if (this.tipo=="C"){
			//return new FileVehiculo();
		}else{
			//return new FileConductor();
		}
		
	}

		
		/** 
		 * devuelve true si el nombre de fichero sigue la nomenglatura segun la normativa de archivo tgd
		 */
		public boolean isArchivo(){
		
				boolean ok=false;
				if (this.tipo!=null && this.identificacion!=null && this.pais!=null && this.fecha!=null & this.hora!=null){
					ok=true;
				}
				
				return ok;
			 }
	
	

}
