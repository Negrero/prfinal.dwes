/**
 * 
 */
package ajaxmvc.modelo.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Time;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;

import org.apache.log4j.Logger;

import sun.security.krb5.internal.crypto.crc32;

/**
 * @author negrero
 *
 */
public class Archivo implements InterpretaArchivo{
	
	

	/** 
	 * @author negrero
	 */
	private InputStream fichero=null;
	
	private String nombre_fichero=null;
	
	private String identificacion=null;
	
	private String pais=null;
	
	private String tipo=null;
	
	private String fecha=null;
	
	private String hora=null;
	
	private final static String extension="TGD";
	

	private crc32 checksum=null;
	
	public Archivo(String nombre,InputStream is) {
		this.nombre_fichero=nombre;
		this.fichero=is;
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
		
	}
	
	public Archivo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the checksum
	 */
	public crc32 getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(crc32 checksum) {
		this.checksum=new crc32();
		
		this.checksum = checksum;
	}

	@Override
	public Object getInterpreta() {
		
		/** Convierte un array de bytes [] en una cadena de caracteres hexadecimal
		* @param bytes Array de bytes para ser convertidos a hexa
		* @return Cadena en hexadecimal
		*/
		StringBuffer retString=null;
		
		
		if (this.fichero!=null){
			try {
				byte[] bytes = new byte[this.fichero.available()];
				System.out.println("bytes aviles:"+this.fichero.available());
				this.fichero.read(bytes);
				retString = new StringBuffer();
			
				for (int i = 0; i < bytes.length; ++i) {
					retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1));
					if((i%2)==0)
						retString.append(" ");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retString.toString();
		
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * @return the extension
	 */
	public static String getExtension() {
		return extension;
	}

	/**
	 * @return the fichero
	 */
	public InputStream getFichero() {
		return fichero;
	}

	/**
	 * @param fichero the fichero to set
	 */
	public void setFichero(InputStream fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the nombre_fichero
	 */
	public String getNombre_fichero() {
		return nombre_fichero;
	}

	/**
	 * @param nombre_fichero the nombre_fichero to set
	 */
	public void setNombre_fichero(String nombre_fichero) {
		this.nombre_fichero = nombre_fichero;
	}
	
	public Archivo getFactoriaFichero(){
		if (this.tipo=="C"){
			return new FileVehiculo();
		}else{
			return new FileConductor();
		}
		
	}

	public boolean isArchivo() {
		// TODO Auto-generated method stub
		boolean ok=false;
		if (this.fichero!=null && this.tipo!=null && this.identificacion!=null && this.pais!=null && this.fecha!=null & this.hora!=null){
			ok=true;
		}
		
		return ok;
	}
	
	

}
